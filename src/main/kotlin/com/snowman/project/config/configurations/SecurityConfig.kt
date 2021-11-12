package com.snowman.project.config.configurations

import com.fasterxml.jackson.databind.ObjectMapper
import com.snowman.project.config.security.JwtAuthenticationFilter
import com.snowman.project.config.security.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
    private val objectMapper: ObjectMapper
) : WebSecurityConfigurerAdapter() {

    @Bean
    open fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity) {
        http.httpBasic().disable()
            .cors().and()
            .formLogin().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers("/auth/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenProvider, objectMapper),
                UsernamePasswordAuthenticationFilter::class.java
            )
    }

    override fun configure(web: WebSecurity) {
        web.ignoring()
            .mvcMatchers(HttpMethod.GET, "/v3/api-docs")
            .mvcMatchers(HttpMethod.GET, "/swagger-resources/**")
            .mvcMatchers(HttpMethod.GET, "/swagger-ui/**")
            .mvcMatchers(HttpMethod.GET, "/webjars/**")
            .mvcMatchers(HttpMethod.GET, "/swagger/**")
    }
}