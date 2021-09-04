package io.github.xxxspring.base.mongo.configuration

import com.mongodb.MongoClientOptions
import com.mongodb.MongoClientSettings
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.MongoDbFactory
import org.springframework.data.mongodb.core.MongoClientFactoryBean
import org.springframework.data.mongodb.core.SimpleMongoClientDbFactory

@Configuration
open class MongoConfiguration {

    @Autowired
    lateinit var properties: MongoProperties

    /**
     * https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo.mongo-db-factory-java
     */
    @Bean
    open fun mongoClient(): MongoClient {
        val serverAddresses = properties.hosts?.mapIndexed { index, s -> ServerAddress(s, properties.ports!![index]) }
        val clientSettings = MongoClientSettings.builder()
            .applyToClusterSettings { it.hosts(serverAddresses) }
            .applyToConnectionPoolSettings {
                it.maxSize(properties.connectionsPerHost!!).minSize(properties.minConnectionsPerHost!!)
            }

        if (!StringUtils.isEmpty(properties.username)) {
            clientSettings.credential(
                MongoCredential.createScramSha1Credential(
                    properties.username!!,
                    properties.authenticationDatabase ?: properties.database!!,
                    properties.password!!.toCharArray()
                )
            )
        }
        return MongoClients.create(clientSettings.build())
    }

    @Bean
    open fun mongoDbFactory(@Autowired client: MongoClient): MongoDbFactory {
        return SimpleMongoClientDbFactory(client, properties.database!!)
    }

    /**
     * https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo.mongo-db-factory-java
     */
    @Bean
    open fun mongoClientFactoryBean(): MongoClientFactoryBean {
        val serverAddresses = properties.hosts?.mapIndexed { index, s -> ServerAddress(s, properties.ports!![index]) }
        val credentials = if (!StringUtils.isEmpty(properties.username)) {
            listOf(
                MongoCredential.createScramSha1Credential(
                    properties.username!!,
                    properties.authenticationDatabase ?: properties.database!!,
                    properties.password!!.toCharArray()
                )
            )
        } else {
            listOf()
        }
        val options = MongoClientOptions.builder()
            .minConnectionsPerHost(properties.minConnectionsPerHost!!)
            .connectionsPerHost(properties.connectionsPerHost!!)
            .build()

        val mongo = MongoClientFactoryBean()
        mongo.setMongoClientOptions(options)
        mongo.setReplicaSetSeeds(serverAddresses!!.toTypedArray())
        if (credentials.isNotEmpty()) {
            mongo.setCredentials(credentials.toTypedArray())
        }
        mongo.afterPropertiesSet()
        return mongo
    }
}
