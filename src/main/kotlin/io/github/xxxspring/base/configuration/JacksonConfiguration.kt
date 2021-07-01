package io.github.xxxspring.base.configuration

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.github.xxxspring.base.serializer.LongSerializer
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class JacksonConfiguration {

    @ConditionalOnMissingBean(Jackson2ObjectMapperBuilderCustomizer::class)
    @Bean
    open fun objectMapperBuilder(): Jackson2ObjectMapperBuilderCustomizer {
        return Jackson2ObjectMapperBuilderCustomizer { builder ->
            builder
                    .featuresToEnable(
                            SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
                    )
                    .featuresToDisable(
                            DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES
                    )
                    .serializationInclusion(JsonInclude.Include.NON_NULL)
                    .serializerByType(Long::class.java, LongSerializer.instance)
        }
    }
}
