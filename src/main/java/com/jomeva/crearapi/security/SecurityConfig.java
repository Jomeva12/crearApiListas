
package com.jomeva.crearapi.security;

import com.jomeva.crearapi.security.jwt.JwtFilter;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer{
  
  @Autowired
  private UsuarioDetailService usuarioDetailService;


  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  private JwtFilter jwtFilter;
  /**
 * Configura una cadena de filtros de seguridad para gestionar la seguridad y autorización de las solicitudes HTTP.
 *
 * @param http Configuración de seguridad de Spring Security.
 * @return Una instancia de SecurityFilterChain configurada.
 * @throws Exception Si se produce una excepción durante la configuración.
 */
    @Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
              
          http.csrf(AbstractHttpConfigurer::disable)
             .cors(AbstractHttpConfigurer::disable)
             .authorizeHttpRequests(request -> {
                 request.requestMatchers("user/login", "user/registrar", "user/forgotPassword").permitAll();// Rutas públicas sin requerir autenticación.
                 request.requestMatchers("/cancion").hasAuthority( "ADMIN");// Requiere autorización "ADMIN" para la ruta "/cancion".
 request.requestMatchers("/lists/**").hasAuthority( "ADMIN");
             })
                  .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);// Agrega el filtro JWT antes del filtro de autenticación por nombre de usuario y contraseña. 
                 
          http.cors(withDefaults());
      return http.build();   
          
     }
  
   
 /*@Bean
     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
              
         return http.csrf(AbstractHttpConfigurer::disable)
             .cors(AbstractHttpConfigurer::disable)
             .authorizeHttpRequests(request -> {
                 request.requestMatchers("user/login", "user/registrar", "user/forgotPassword").permitAll();// Rutas públicas sin requerir autenticación.
                 request.requestMatchers("/cancion").hasAuthority( "ADMIN");// Requiere autorización "ADMIN" para la ruta "/cancion".
 request.requestMatchers("/lists").hasAuthority( "ADMIN");
             }).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)// Agrega el filtro JWT antes del filtro de autenticación por nombre de usuario y contraseña. 
                 .build();
     }
  */
 
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
    return authenticationConfiguration.getAuthenticationManager();
  }
}
