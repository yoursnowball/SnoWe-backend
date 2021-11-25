package com.snowman.project.config.configurations

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.servers.Server
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.oas.annotations.EnableOpenApi
import springfox.documentation.oas.web.OpenApiTransformationContext
import springfox.documentation.oas.web.WebMvcOpenApiTransformationFilter
import springfox.documentation.service.ApiKey
import springfox.documentation.service.AuthorizationScope
import springfox.documentation.service.SecurityReference
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spi.service.contexts.SecurityContext
import springfox.documentation.spring.web.plugins.Docket
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.servlet.http.HttpServletRequest

@Configuration
@EnableOpenApi
class SwaggerConfig(
    @Value("\${app.url}")
    private val url: String
) : WebMvcOpenApiTransformationFilter {

    @Bean
    fun autApi(): Docket {
        return Docket(DocumentationType.OAS_30)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.snowman.project.controller"))
            .build()
            .securitySchemes(listOf(apiKey()))
            .securityContexts(listOf(securityContext()))
            .directModelSubstitute(LocalTime::class.java, String::class.java)
            .directModelSubstitute(LocalDate::class.java, String::class.java)
            .directModelSubstitute(LocalDateTime::class.java, String::class.java)
    }

    private fun securityContext(): SecurityContext {
        return SecurityContext.builder().securityReferences(defaultAuth()).forPaths(PathSelectors.any()).build()
    }

    private fun apiKey(): ApiKey {
        return ApiKey("Authorization", "Bearer", "header")
    }

    private fun defaultAuth(): List<SecurityReference> {
        val authorizationScope = AuthorizationScope("global", "access All")
        return listOf(SecurityReference("Authorization", arrayOf(authorizationScope)))
    }

    override fun supports(delimiter: DocumentationType): Boolean {
        return delimiter == DocumentationType.OAS_30;
    }

    override fun transform(context: OpenApiTransformationContext<HttpServletRequest>?): OpenAPI {
        val openAPI = context!!.specification
        val server = Server()

        server.url = url
        server.description = "snowe-api-server"

        openAPI.servers(listOf(server))
        return openAPI
    }
}