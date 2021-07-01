package io.github.xxxspring.base.mysql.configuration

import com.alibaba.druid.pool.DruidDataSource
import com.github.pagehelper.PageHelper
import org.apache.commons.lang3.StringUtils
import org.apache.ibatis.plugin.Interceptor
import org.apache.ibatis.session.SqlSessionFactory
import org.apache.ibatis.session.SqlSessionFactoryBuilder
import org.apache.ibatis.type.TypeHandler
import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.SqlSessionTemplate
import org.mybatis.spring.boot.configure.SpringBootVFS
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import java.sql.SQLException
import java.util.*
import javax.sql.DataSource

/**
 * mybatis 配置数据源类
 * @date 2016年12月15日
 * @since 1.7
 */
@Configuration
open class MybatisConfiguration {
    @Value("\${spring.datasource.driver-class-name}")
    private val driveClassName: String? = null

    @Value("\${spring.datasource.url}")
    private val url: String? = null

    @Value("\${spring.datasource.username}")
    private val userName: String? = null

    @Value("\${spring.datasource.password}")
    private val password: String? = null

    @Value("\${mybatis.xml-location:}")
    private val xmlLocation: String? = null

    @Value("\${mybatis.type-aliases-package:}")
    private val typeAliasesPackage: String? = null

    @Value("\${mybatis.type-handlers-package:}")
    private val typeHandlersPackage: String? = null

    @Value("\${mybatis.enum-handler:}")
    private val enumHandler: String? = null

    /////////////////////druid参数///////////////////////////////////////////////////
    @Value("\${spring.datasource.filters}")
    private val filters: String? = null

    @Value("\${spring.datasource.max-active}")
    private val maxActive: String? = null

    @Value("\${spring.datasource.initial-size}")
    private val initialSize: String? = null

    @Value("\${spring.datasource.max-wait}")
    private val maxWait: String? = null

    @Value("\${spring.datasource.min-idle}")
    private val minIdle: String? = null

    @Value("\${spring.datasource.time-between-eviction-runs-millis}")
    private val timeBetweenEvictionRunsMillis: String? = null

    @Value("\${spring.datasource.min-evictable-idle-time-millis}")
    private val minEvictableIdleTimeMillis: String? = null

    @Value("\${spring.datasource.validation-query}")
    private val validationQuery: String? = null

    @Value("\${spring.datasource.test-while-idle}")
    private val testWhileIdle: String? = null

    @Value("\${spring.datasource.test-on-borrow}")
    private val testOnBorrow: String? = null

    @Value("\${spring.datasource.test-on-return}")
    private val testOnReturn: String? = null

    @Value("\${spring.datasource.pool-prepared-statements}")
    private val poolPreparedStatements: String? = null

    @Value("\${spring.datasource.max-open-prepared-statements}")
    private val maxOpenPreparedStatements: String? = null

    //////////////////////////////////////////////////////////////////////////

    @Bean("druidDataSource")
    open fun druidDataSource(): DataSource {
        val druidDataSource = DruidDataSource()
        druidDataSource.url = url
        druidDataSource.username = userName
        druidDataSource.password = password
        druidDataSource.driverClassName = if (StringUtils.isNotBlank(driveClassName)) driveClassName else "com.mysql.jdbc.Driver"
        druidDataSource.maxActive = if (StringUtils.isNotBlank(maxActive)) Integer.parseInt(maxActive!!) else 10
        druidDataSource.initialSize = if (StringUtils.isNotBlank(initialSize)) Integer.parseInt(initialSize!!) else 1
        druidDataSource.maxWait = (if (StringUtils.isNotBlank(maxWait)) Integer.parseInt(maxWait!!) else 60000).toLong()
        druidDataSource.minIdle = if (StringUtils.isNotBlank(minIdle)) Integer.parseInt(minIdle!!) else 3
        druidDataSource.setConnectionInitSqls(listOf("SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci"))        // TODO 先写死
        druidDataSource.timeBetweenEvictionRunsMillis = (if (StringUtils.isNotBlank(timeBetweenEvictionRunsMillis))
            Integer.parseInt(timeBetweenEvictionRunsMillis!!)
        else
            60000).toLong()
        druidDataSource.minEvictableIdleTimeMillis = (if (StringUtils.isNotBlank(minEvictableIdleTimeMillis))
            Integer.parseInt(minEvictableIdleTimeMillis!!)
        else
            300000).toLong()
        druidDataSource.validationQuery = if (StringUtils.isNotBlank(validationQuery)) validationQuery else "select 'x'"
        druidDataSource.isTestWhileIdle = if (StringUtils.isNotBlank(testWhileIdle)) java.lang.Boolean.parseBoolean(testWhileIdle) else true
        druidDataSource.isTestOnBorrow = if (StringUtils.isNotBlank(testOnBorrow)) java.lang.Boolean.parseBoolean(testOnBorrow) else false
        druidDataSource.isTestOnReturn = if (StringUtils.isNotBlank(testOnReturn)) java.lang.Boolean.parseBoolean(testOnReturn) else false
        druidDataSource.isPoolPreparedStatements = if (StringUtils.isNotBlank(poolPreparedStatements)) java.lang.Boolean.parseBoolean(poolPreparedStatements) else true
        druidDataSource.maxOpenPreparedStatements = if (StringUtils.isNotBlank(maxOpenPreparedStatements)) Integer.parseInt(maxOpenPreparedStatements!!) else 20


        try {
            druidDataSource.setFilters(if (StringUtils.isNotBlank(filters)) filters else "stat, wall")
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return druidDataSource
    }

    @Bean(name = ["sqlSessionFactory"])
    open fun sqlSessionFactoryBean(@Qualifier("druidDataSource") dataSource: DataSource): SqlSessionFactory? {
        val bean = SqlSessionFactoryBean()
        bean.setDataSource(dataSource)
        bean.vfs = SpringBootVFS::class.java            //TODO 这里使用的是spring-mybatis-starter的替换实现, 可等待mybatis 后续版本fix DefaultVFS.isJar识别不到的问题, issue: https://github.com/mybatis/mybatis-3/issues/325

        if (StringUtils.isNotBlank(typeAliasesPackage)) {
            bean.setTypeAliasesPackage(typeAliasesPackage)
        }
        //分页插件
        val pageHelper = PageHelper()
        val properties = Properties()
        properties.setProperty("reasonable", "false")
        properties.setProperty("supportMethodsArguments", "true")
        properties.setProperty("returnPageInfo", "check")
        properties.setProperty("params", "count=countSql")
        pageHelper.setProperties(properties)
        //添加XML目录
        val resolver = PathMatchingResourcePatternResolver()
        val plugins = arrayOf<Interceptor>(pageHelper)
        bean.setPlugins(plugins)

        val configuration = org.apache.ibatis.session.Configuration()
        if (StringUtils.isNotBlank(enumHandler)) {
            configuration.setDefaultEnumTypeHandler(Class.forName(enumHandler) as Class<out TypeHandler<Any>>?)
        }
        configuration.isMapUnderscoreToCamelCase = true

        try {
            bean.setConfiguration(configuration)
            if (StringUtils.isNotBlank(typeHandlersPackage)) {
                bean.setTypeHandlersPackage(typeHandlersPackage)
            }
            bean.setMapperLocations(resolver.getResources(xmlLocation!!))
            return bean.`object`
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e)
        }

    }

    @Bean
    open fun sqlSessionTemplate(sqlSessionFactory: SqlSessionFactory): SqlSessionTemplate {
        return SqlSessionTemplate(sqlSessionFactory)
    }

}

