package br.com.estudo.imageliteapi.config;

import br.com.estudo.imageliteapi.application.jwt.JwtService;
import br.com.estudo.imageliteapi.config.filter.JwtFilter;
import br.com.estudo.imageliteapi.domain.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtFilter jwtFilter(JwtService jwtService, UserService userService){
        return  new JwtFilter(jwtService, userService);
    }

    //Implementação de Encriptador para a senha
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception{
        return http
                //desabilitando o csrf que configura páginas HTML no contexto do Spring, como aqui o front é React, é desativado
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configure(http))
                .authorizeHttpRequests(auth -> {

                    //libera pra acesso os caminhos de users, que são login e cadastro
                    auth.requestMatchers("v1/users/**").permitAll();

                    auth.requestMatchers(HttpMethod.GET, "v1/images/**").permitAll();
                    //fecha pra todas as o;utras páginas
                    auth.anyRequest().authenticated();
                })
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();

        UrlBasedCorsConfigurationSource cors = new UrlBasedCorsConfigurationSource();

        //Com "/**" libera tudo, para liberar só específicos precisa botar endereço da página
        cors.registerCorsConfiguration("/**", config);

        return cors;
    }
}
