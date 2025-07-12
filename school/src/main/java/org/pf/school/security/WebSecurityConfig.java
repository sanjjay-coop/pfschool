package org.pf.school.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	private SchoolUserDetailsService schoolUserDetailsService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return  new BCryptPasswordEncoder();
    }
	
    @Bean
    AuthenticationProvider authProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(schoolUserDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

	@SuppressWarnings({ "removal", "deprecation" })
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
    	String [] publicUrls = new String [] {
                "/api/**"
        };
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers(publicUrls))
                .authorizeHttpRequests((authorize) -> authorize
                		//.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                		.requestMatchers(
                				"/",
                				"/assets/**",
                				"/css/**", 
                				"/home/**",
                				"/images/**",
                				"/logout", 
                				"/siteLogo", 
                				"/webjars/**").permitAll()
                		.requestMatchers("/accounts/**").hasAnyRole(
                				"STAFF_ACCOUNTS",
                				"STAFF_MANAGER",
                				"MANAGER")
                		.requestMatchers("/admin/**").hasAnyRole(
                				"STAFF_ADMIN",
                				"STAFF_MANAGER",
                				"MANAGER")
                		.requestMatchers("/dashboard/**").hasAnyRole(
                				"STAFF",
                				"STAFF_LIBRARY",
                				"STAFF_ACCOUNTS",
                				"STAFF_ADMIN",
                				"STUDENT",
                				"TEACHER",
                				"STAFF_TEACHER",
                				"STAFF_MANAGER",
                				"MANAGER")
                		.requestMatchers("/manager/**").hasAnyRole(
                				"STAFF_MANAGER",
                				"MANAGER")
                		.requestMatchers("/library/**").hasAnyRole(
                				"STAFF_LIBRARY",
                				"STAFF_MANAGER",
                				"MANAGER")
                        .anyRequest().denyAll()
                		)
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                		.accessDeniedPage("/home/accessDenied"))
                .formLogin(login -> login
                        .loginPage("/home/login").permitAll()
                        .defaultSuccessUrl("/dashboard", true))
                .logout(logout -> logout.invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/home/logout-success").permitAll());

        return http.build();
    }
	
}
