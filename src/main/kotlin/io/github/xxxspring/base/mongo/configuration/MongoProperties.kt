package io.github.xxxspring.base.mongo.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.data.mongodb.custom")
open class MongoProperties(
        var database: String? = null,
        var hosts: List<String>? = null,
        var ports: List<Int>? = null,
        var username: String? = null,
        var password: String? = null,
        var authenticationDatabase: String? = null,
        var minConnectionsPerHost: Int? = 10,
        var connectionsPerHost: Int? = 2,
        var migrationPackage: String? = null
)
