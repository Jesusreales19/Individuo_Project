package com.project.web;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    @Bean
    public UserDetailsService users(PasswordEncoder passwordEncoder) {
        UserDetails usuario1 = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("1905"))
                .roles("ADMIN")
                .build();

        UserDetails usuario2 = User.builder()
            .username("user")
            .password(passwordEncoder.encode("0519"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(usuario1, usuario2);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/" ).authenticated()

                .requestMatchers("/nuevo", "/guardar", "/editar", "/eliminar", "/guardarDemo")
                        .hasRole("ADMIN")

                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                .requestMatchers("/login").permitAll()
                .anyRequest().authenticated())

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                                .permitAll())

                .logout(logout->logout
                    .logoutSuccessUrl("/login?logout")
                        .permitAll());

    return http.build();


    }

}