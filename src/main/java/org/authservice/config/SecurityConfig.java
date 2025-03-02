package org.authservice.config;


import lombok.RequiredArgsConstructor;
import org.authservice.filter.JwtAuthFilter;
import org.authservice.repository.EmployeeRepository;
import org.authservice.service.KakaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final KakaoService kakaoService;
    private final EmployeeRepository employeeRepository;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    private static final String[] AUTH_ALLOWLIST = {
        "/swagger-ui/**", "/v3/**", "/login/**", "/images/**", "/kakao/**", "/app/token"
    };


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)  throws Exception{
        http.csrf((csrf) -> csrf.disable());
        http.cors(Customizer.withDefaults());

        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin((form) -> form.disable());

        http.addFilterBefore(new JwtAuthFilter(kakaoService, employeeRepository), UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests(authorize -> authorize
            .requestMatchers(AUTH_ALLOWLIST).permitAll()
            .requestMatchers("/admin/**").hasRole("ADMIN")
            .requestMatchers("/employees/**").hasRole("USER")
            .requestMatchers("/departments/**").hasRole("USER")
            .anyRequest().authenticated());

        http.exceptionHandling((handling) -> handling.authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(accessDeniedHandler));

        return http.build();
    }

}
