package com.stage.swift.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;
    private final JwtFilter jwtFilter;

    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtFilter jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtFilter = jwtFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .antMatchers("/api/auth/**").hasAnyRole("ADMIN", "SUPERVISEUR")
                // Administration: réservé à l'administrateur
                .antMatchers("/api/utilisateurs/**", "/api/roles/**", "/api/permissions/**").hasRole("ADMIN")
                // Référentiels système (création/modification/suppression): admin only
                .antMatchers(HttpMethod.POST, "/api/adresses/**", "/api/bics/**", "/api/statuts/**", "/api/type-messages/**", "/api/sops/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/adresses/**", "/api/bics/**", "/api/statuts/**", "/api/type-messages/**", "/api/sops/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/adresses/**", "/api/bics/**", "/api/statuts/**", "/api/type-messages/**", "/api/sops/**")
                .hasRole("ADMIN")
                // Messages MX: lecture pour SUPERVISEUR, écriture admin only
                .antMatchers(HttpMethod.GET, "/api/messages-recu/**", "/api/messages-emis/**")
                .hasAnyRole("ADMIN", "SUPERVISEUR")
                .antMatchers(HttpMethod.GET, "/api/messages-xml/**")
                .hasAnyRole("ADMIN", "SUPERVISEUR")
                .antMatchers(HttpMethod.POST, "/api/messages-recu/**", "/api/messages-emis/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/messages-recu/**", "/api/messages-emis/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/messages-recu/**", "/api/messages-emis/**")
                .hasRole("ADMIN")
                // Virements & rapprochement: lecture + validation (PUT) pour SUPERVISEUR
                .antMatchers(HttpMethod.GET, "/api/virements-recu/**", "/api/virements-emis/**",
                        "/api/virements-recu-histo/**", "/api/virements-emis-histo/**")
                .hasAnyRole("ADMIN", "SUPERVISEUR")
                .antMatchers(HttpMethod.PUT, "/api/virements-recu/**", "/api/virements-emis/**",
                        "/api/virements-recu-histo/**", "/api/virements-emis-histo/**")
                .hasAnyRole("ADMIN", "SUPERVISEUR")
                .antMatchers(HttpMethod.POST, "/api/virements-recu/**", "/api/virements-emis/**",
                        "/api/virements-recu-histo/**", "/api/virements-emis-histo/**")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/virements-recu/**", "/api/virements-emis/**",
                        "/api/virements-recu-histo/**", "/api/virements-emis-histo/**")
                .hasRole("ADMIN")
                // Dashboards/référentiels consultables
                .antMatchers(HttpMethod.GET, "/api/sops/**", "/api/adresses/**", "/api/bics/**", "/api/statuts/**", "/api/type-messages/**")
                .hasAnyRole("ADMIN", "SUPERVISEUR")
                // Ingestion MX: réservé admin
                .antMatchers("/api/mx/**").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("http://localhost:4200");
        configuration.addAllowedMethod(HttpMethod.GET);
        configuration.addAllowedMethod(HttpMethod.POST);
        configuration.addAllowedMethod(HttpMethod.PUT);
        configuration.addAllowedMethod(HttpMethod.DELETE);
        configuration.addAllowedMethod(HttpMethod.PATCH);
        configuration.addAllowedMethod(HttpMethod.OPTIONS);
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}

