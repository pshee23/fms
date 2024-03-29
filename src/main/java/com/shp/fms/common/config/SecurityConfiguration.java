package com.shp.fms.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.shp.fms.auth.RefreshRedisRepository;
import com.shp.fms.auth.config.CorsConfig;
import com.shp.fms.auth.filter.CustomFilter;
import com.shp.fms.auth.filter.JwtExceptionFilter;
import com.shp.fms.auth.jwt.JwtAuthenticationFilter;
import com.shp.fms.auth.jwt.JwtAuthorizationFilter;
import com.shp.fms.auth.jwt.JwtTokenProvider;
//import com.shp.fms.auth.filter.CustomFilter;
import com.shp.fms.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	@Autowired
	private MemberRepository memberRepository;
	
	@Autowired
	private CorsConfig corsConfig;
	
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	private final RefreshRedisRepository redisRepository;
	
	private final JwtExceptionFilter jwtExceptionFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.addFilterBefore(new CustomFilter(), BasicAuthenticationFilter.class);
        http.csrf().disable();
//        
//        // h2 임시 허용
//        http.headers().frameOptions().disable();        
//        
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .addFilterBefore(new CorsConfig().corsFilter(), UsernamePasswordAuthenticationFilter.class)
        .formLogin().disable()
        .httpBasic().disable()
        .apply(new CustomConfigurer())
        .and()
        .authorizeRequests()
        .antMatchers("/api/user/**").authenticated()
        .antMatchers("/api/manager/**").hasAnyRole("ADNIN", "MANAGER")
		.antMatchers("/api/admin/**").hasRole("ADMIN")
		.antMatchers("/index.html").permitAll()
//		.anyRequest().permitAll()
        ;
        
		return http.build();
    }
    
    public class CustomConfigurer extends AbstractHttpConfigurer<CustomConfigurer, HttpSecurity> {
		@Override
		public void configure(HttpSecurity http) throws Exception {
			AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
			http
					.addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
					.addFilter(corsConfig.corsFilter())
					.addFilter(new JwtAuthenticationFilter(authenticationManager, authenticationManagerBuilder, 
							jwtTokenProvider, redisRepository))
					.addFilter(new JwtAuthorizationFilter(authenticationManager, memberRepository, jwtTokenProvider));
					
		}
	}
}
