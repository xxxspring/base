package io.github.xxxspring.base.elasticsearch.configuration

import io.github.xxxspring.base.elasticsearch.configuration.EsProperties
import org.apache.http.Header
import org.apache.http.HttpHost
import org.apache.http.auth.AuthScope
import org.apache.http.auth.UsernamePasswordCredentials
import org.apache.http.impl.client.BasicCredentialsProvider
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.apache.http.message.BasicHeader
import org.apache.http.ssl.SSLContexts
import org.elasticsearch.client.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate
import org.springframework.data.elasticsearch.core.ResultsMapper
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter
import java.security.KeyStore
import java.security.cert.CertificateFactory


@Configuration
open class RestHighLevelClientConfig {

    @Autowired
    lateinit var properties: EsProperties

    @Bean
    open fun restHighLevelClient(restClientBuilder: RestClientBuilder): RestHighLevelClient {
        return RestHighLevelClient(restClientBuilder)
    }

    @Bean
    open fun restClientBuilder(): RestClientBuilder {
        if(properties.ssl == null || properties.ssl == false){
            // http 加密模式
            if(properties.username != null){
                return restClientBuilderHttpPassword()
            }
            return restClientBuilderHttp()
        }
        val credentialsProvider = BasicCredentialsProvider()
        credentialsProvider.setCredentials(AuthScope.ANY, UsernamePasswordCredentials(properties.username, properties.password))
        val resources = PathMatchingResourcePatternResolver().getResources(properties.certpath)
        val resource = resources.get(0).inputStream

        //用下面st的加载方式，会导致打包后，找不到文件的错误
//        val path = this::class.java.getClassLoader().getResource("tls.crt").getPath()
//        val caCertificatePath = Paths.get(path)
//        val caCertificatePath = Paths.get("/Users/mac/Workspace/dev_env/elasticsearch/etc/config/certs/tls.crt")
        val factory =
                CertificateFactory.getInstance("X.509")
//        val st = Files.newInputStream(caCertificatePath)
        val trustedCa = factory.generateCertificate(resource)

        val trustStore = KeyStore.getInstance("pkcs12")
        trustStore.load(null, null)
        trustStore.setCertificateEntry("ca", trustedCa)
        val sslContextBuilder = SSLContexts.custom()
                .loadTrustMaterial(trustStore, null)
        val sslContext = sslContextBuilder.build()

        try {
            val builder = RestClient.builder(
                    HttpHost(properties.host!!, properties.port!!, properties.scheme!!)
            )
            builder.setHttpClientConfigCallback(object : RestClientBuilder.HttpClientConfigCallback {
                @Override
                override fun customizeHttpClient(httpClientBuilder: HttpAsyncClientBuilder): HttpAsyncClientBuilder {
                    httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                    return httpClientBuilder.setSSLContext(sslContext);
                }
            })
            return builder
        } catch (e: Error) {
            System.err.println("restClientBuilder fail : $e")
            throw e
        }
    }

    @Bean
    open fun elasticsearchTemplate(@Qualifier("restHighLevelClient") client: RestHighLevelClient, converter: ElasticsearchConverter, resultsMapper: ResultsMapper): ElasticsearchRestTemplate? {
        return ElasticsearchRestTemplate(client, converter, resultsMapper)
    }

    // 普通版
//    @Bean("restHighLevelClient")
    fun restClientBuilderHttp(): RestClientBuilder {
        val restClientBuilder = RestClient.builder(
                HttpHost(properties.host!!, properties.port!!, properties.scheme!!)
        )
        val defaultHeaders = arrayOf<Header>(
                BasicHeader("Accept", "*/*")
        )
        restClientBuilder.setDefaultHeaders(defaultHeaders)
        restClientBuilder.setFailureListener(object : RestClient.FailureListener() {
            override fun onFailure(node: Node) {
                println("监听某个es节点失败")
            }
        })
        return restClientBuilder
    }

    // http 加密版
    fun restClientBuilderHttpPassword(): RestClientBuilder {
        val credentialsProvider = BasicCredentialsProvider()
        credentialsProvider.setCredentials(AuthScope.ANY, UsernamePasswordCredentials(properties.username, properties.password))

        try {
            val builder = RestClient.builder(
                    HttpHost(properties.host!!, properties.port!!, properties.scheme!!)
            )
            builder.setHttpClientConfigCallback(object : RestClientBuilder.HttpClientConfigCallback {
                @Override
                override fun customizeHttpClient(httpClientBuilder: HttpAsyncClientBuilder): HttpAsyncClientBuilder {
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                }
            })
            return builder
        } catch (e: Error) {
            System.err.println("restClientBuilderHttpPassword fail : $e")
            throw e
        }
    }


}
