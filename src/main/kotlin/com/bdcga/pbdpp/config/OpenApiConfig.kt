// package com.bdcga.pbdpp.config

package com.bdcga.pbdpp.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springdoc.core.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun publicApi(): GroupedOpenApi = GroupedOpenApi.builder()
        .group("v1-definition")
        .pathsToMatch("/api/**")
        .build()

    @Bean
    fun openAPI(): OpenAPI = OpenAPI()
        .info(
            Info()
                .title("Distributed Pair Programming API")
                .version("1.0")
                .description("API documentation for the BDCGA PBDPP backend")
        )
}