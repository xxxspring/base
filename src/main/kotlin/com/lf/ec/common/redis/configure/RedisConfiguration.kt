package com.lf.ec.common.redis.configure

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer


@Configuration
open class RedisConfiguration {

    /**
     * 默认redisTemplate可以处理 Int/Long/Set<String>/List<String>/Map<String, String>等通用类型
     * 如果Entity需要使用此默认template，需要定义为open class，否则无法正确反序列化类型
     * 原则上，建议Entity定义具体的template
     */
    @Bean
    @Primary
    open fun redisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = factory

        val jackson2JsonRedisSerializer = GenericJackson2JsonRedisSerializer()// Jackson2JsonRedisSerializer(Any::class.java)

        template.defaultSerializer = jackson2JsonRedisSerializer
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = jackson2JsonRedisSerializer
        template.hashKeySerializer = StringRedisSerializer()
        template.hashValueSerializer = jackson2JsonRedisSerializer
        template.afterPropertiesSet()
        return template
    }

    @Bean
    @Qualifier("jdkRedisTemplate")
    open fun jdkRedisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = factory

        val jdkSerializationRedisSerializer = JdkSerializationRedisSerializer()// Jackson2JsonRedisSerializer(Any::class.java)

        template.defaultSerializer = jdkSerializationRedisSerializer
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = jdkSerializationRedisSerializer
        template.hashKeySerializer = StringRedisSerializer()
        template.hashValueSerializer = jdkSerializationRedisSerializer
        template.afterPropertiesSet()
        return template
    }

}
