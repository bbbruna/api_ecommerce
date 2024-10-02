package ecproducts.com.products.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ecproducts.com.products.services.TokenFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public TokenFilter tokenFilter() {
		return new TokenFilter();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, "/api/login").permitAll()
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers(HttpMethod.POST, "/api/register").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/products").permitAll()
						.requestMatchers(HttpMethod.GET, "/api/products/**").permitAll()
						 .requestMatchers(HttpMethod.GET, "/api/user/**").authenticated()
						.requestMatchers(HttpMethod.POST, "/api/products/**").authenticated()
						.requestMatchers(HttpMethod.PATCH, "/api/products/**").authenticated()
						.requestMatchers(HttpMethod.PUT, "/api/products/**").authenticated()
						.requestMatchers(HttpMethod.GET, "/api/cart/**").authenticated()
						.requestMatchers(HttpMethod.PUT, "/api/cart/**").authenticated()
						.requestMatchers(HttpMethod.POST, "/api/cart/**").authenticated()
						.requestMatchers(HttpMethod.DELETE, "/api/cart/**").authenticated().anyRequest()
						.authenticated())
				.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
