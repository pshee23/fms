package com.shp.fms.common.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import com.shp.fms.auth.CustomBCryptPasswordEncoder;
import com.shp.fms.auth.LoginFailHandler;
import com.shp.fms.auth.LoginSuccessHandler;
import com.shp.fms.auth.MemberAuthenticatorProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private final MemberAuthenticatorProvider memberAuthenticatorProvider;
	
//	@Bean
//	public BCryptPasswordEncoder encodePwd() {
//		return new BCryptPasswordEncoder();
//	}
	@Autowired
	CustomBCryptPasswordEncoder encodePwd;
	 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        
        // h2 임시 허용
        http.headers().frameOptions().disable();
        
        
        http
        	.authorizeHttpRequests(
        		request -> request
	        		.antMatchers("/user/**").authenticated()
//	        		.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
	        		.antMatchers("/manager/**").hasAnyRole("ADNIN", "MANAGER")
	        		.antMatchers("/admin/**").hasRole("ADMIN")
        			.antMatchers("/login", "/sign-up","/branch", "/employee").permitAll()
        			.anyRequest().permitAll()
        	)
        	.formLogin(
        		login -> login	// form 방식 로그인 사용
//        			.loginPage("/loginForm")
                	.loginProcessingUrl("/login")
                	.successHandler(new LoginSuccessHandler())
                	.failureHandler(new LoginFailHandler())
        	)
        	.logout(withDefaults());	// 로그아웃은 기본설정으로 (/logout으로 인증해제)
        return http.build();
    }
 
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }

    public void configure(AuthenticationManagerBuilder auth) {
    	auth.authenticationProvider(memberAuthenticatorProvider);
    }
    	
}
