// package com.bdcga.pbdpp.config

package com.bdcga.pbdpp.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
class WebFluxConfig : WebFluxConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins("*") // Update with your frontend's origin
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
    }
}