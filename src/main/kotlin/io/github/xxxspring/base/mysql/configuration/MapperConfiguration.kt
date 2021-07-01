package io.github.xxxspring.base.mysql.configuration

import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.EnvironmentAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import tk.mybatis.spring.mapper.MapperScannerConfigurer
import java.util.*

/**
 * mybatis mapper 扫描配置类
 *
 * @date 2016年12月15日
 * @since 1.7
 */
@Configuration
@AutoConfigureAfter(MybatisConfiguration::class)
@ConfigurationProperties(prefix = "mybatis")
open class MapperConfiguration : EnvironmentAware {
    private var basepackage: String? = null

    @Bean
    open fun mapperScannerConfigurer(): MapperScannerConfigurer {
        val mapperScannerConfigurer = MapperScannerConfigurer()
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory")
        mapperScannerConfigurer.setBasePackage(basepackage)
        val properties = Properties()
        properties.setProperty("enumAsSimpleType", "true")
        mapperScannerConfigurer.setProperties(properties)
        return mapperScannerConfigurer
    }

    override fun setEnvironment(environment: Environment) {
        basepackage = environment.resolvePlaceholders("\${mybatis.basepackage}")
    }
}
