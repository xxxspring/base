package io.github.xxxspring.base.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
open class SwaggerConfiguration {
    @Value("\${swagger.basepackage}")
    private lateinit var basePackage: String

    @Value("\${swagger.service.developer}")
    private lateinit var developer: String

    @Value("\${swagger.service.url}")
    private lateinit var url: String

    @Value("\${swagger.service.email}")
    private lateinit var email: String

    @Value("\${swagger.service.version}")
    private lateinit var version: String

    @Value("\${swagger.service.name}")
    private lateinit var serviceName: String

    @Value("\${swagger.service.description}")
    private lateinit var description: String

    @Bean
    open fun createRestApi(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.basePackage))
                .paths(PathSelectors.any()).build()
    }

    private fun apiInfo(): ApiInfo {
        return ApiInfoBuilder()
                .title(this.serviceName)
                .description(this.description)
                .contact(Contact(
                        this.developer,
                        this.url,
                        this.email
                ))
                .version(this.version).build()
    }
}
