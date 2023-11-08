package com.ingress.bookstore.configs;

import com.ingress.bookstore.auth.AuthEntryPointJwt;
import com.ingress.bookstore.auth.AuthProvider;
import com.ingress.bookstore.filter.AuthFilter;
import com.ingress.bookstore.models.enums.Authority;
import com.ingress.bookstore.services.user.UserDetailsServiceImpl;
import com.ingress.bookstore.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.ingress.bookstore.constants.SecurityConstants.PUBLIC_URLS;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthEntryPointJwt authEntryPointJwt;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling().authenticationEntryPoint(authEntryPointJwt).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests((auth) -> auth
                        .antMatchers("/api/v1/auth/**").permitAll()

                        .antMatchers(HttpMethod.GET, "/api/v1/book/{id}").authenticated()
                        .antMatchers(HttpMethod.GET, "/api/v1/book/readers/{id}").authenticated()
                        .antMatchers(HttpMethod.POST, "/api/v1/book").hasAuthority(Authority.AUTHOR.name())

                        .antMatchers("/api/v1/student/**").hasAuthority(Authority.STUDENT.name())

                        .antMatchers(HttpMethod.POST, "/api/v1/book").hasAuthority(Authority.AUTHOR.name())
                        .antMatchers(HttpMethod.DELETE, "/api/v1/book/{id}").hasAnyRole(Authority.AUTHOR.name())
                )
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new AuthProvider(userDetailsService, passwordEncoder());
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthFilter authenticationTokenFilterBean() throws Exception {
        return new AuthFilter(userDetailsService, jwtUtil);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers(PUBLIC_URLS);
    }

}
