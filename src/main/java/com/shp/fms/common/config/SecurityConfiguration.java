package com.shp.fms.common.config;

import static org.springframework.security.config.Customizer.withDefaults;

import javax.servlet.DispatcherType;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

import com.shp.fms.auth.MemberAuthenticatorProvider;
import com.shp.fms.auth.MemberPrincipalDetailService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private final MemberAuthenticatorProvider memberAuthenticatorProvider;
	
	private final MemberPrincipalDetailService memberprincipalDetailService;
	 
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable()
        	.authorizeHttpRequests(request -> request
//         	.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
        	.antMatchers("/auth/**", "/member/**").permitAll()
//                .anyRequest().authenticated()	// 어떠한 요청이라도 인증필요
        )
        .formLogin(login -> login	// form 방식 로그인 사용
                .defaultSuccessUrl("/view/dashboard", true)	// 성공 시 dashboard로
                .permitAll()	// 대시보드 이동이 막히면 안되므로 얘는 허용
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
