package io.github.xxxspring.base.elasticsearch.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "spring.data.elasticsearch.custom")
open class EsProperties(
        var host: String? = null,
        var port: Int? = null,
        var scheme: String? = null,
        var username: String? = null,
        var password: String? = null,
        // 证书路径
        var certpath: String? = null,
        var ssl: Boolean? = null
)
