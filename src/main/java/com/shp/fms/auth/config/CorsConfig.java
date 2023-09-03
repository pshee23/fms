package com.shp.fms.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CorsConfig {

	@Bean
	public CorsFilter corsFilter() {
		log.info("[corsFilter] 실행");
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("*");
		
		// TODO api root
		source.registerCorsConfiguration("/api/**", config);
		
		CorsFilter corsFilter = new CorsFilter(source);
		log.info("[corsFilter] 종료");
		return corsFilter;
	}
}
