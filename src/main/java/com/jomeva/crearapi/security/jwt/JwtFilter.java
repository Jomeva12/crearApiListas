/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.security.jwt;

import com.jomeva.crearapi.security.UsuarioDetailService;
import com.jomeva.crearapi.security.UsuarioDetailService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author johan
 */
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

  @Autowired
  private JwtUtil jwtUtil;
  @Autowired
  private UsuarioDetailService usuarioDetailService;

  Claims claims = null;

  private String userName = null;

  /**
 * Este método se encarga de realizar la autenticación y autorización de las solicitudes entrantes.
 *
 * @param request       La solicitud HTTP entrante.
 * @param response      La respuesta HTTP que se enviará al cliente.
 * @param filterChain   La cadena de filtros que se utilizará para continuar el procesamiento de la solicitud.
 * @throws ServletException Si ocurre una excepción relacionada con el servlet.
 * @throws IOException      Si ocurre una excepción relacionada con la entrada/salida.
 */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    // Registra información de la ruta de la solicitud para propósitos de registro,login u otro acceso.
    log.info("pathhh {}", request.getServletPath());
    if (request.getServletPath().matches("/user/login|/user/forgotPassword|/user/registrar")) {
      // Las rutas públicas no requieren autenticación, por lo que se permite el acceso sin procesar el token JWT.
      filterChain.doFilter(request, response);

    } else {
      // Procesa solicitudes que no son rutas públicas y requieren autenticación.

        // Obtiene el token JWT de la cabecera de autorización si está presente.
      String authorizationHeader = request.getHeader("Authorization");
      String token = null;
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        token = authorizationHeader.substring(7);
        userName = jwtUtil.extraerUserName(token);
        claims = jwtUtil.extrarAllClaims(token);
      }
      // Verifica si el usuario ya está autenticado y si no, lo autentica.
      if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetail = usuarioDetailService.loadUserByUsername(userName);

        if (jwtUtil.validarToken(token, userDetail)) {
          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                  = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
           // Configura detalles de autenticación web y establece la autenticación en el contexto de seguridad.
          new WebAuthenticationDetailsSource().buildDetails(request);
          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

      }
      // Continúa el procesamiento de la solicitud en la cadena de filtros.
      filterChain.doFilter(request, response);

    }
    {

    }
  }

  public Boolean isAdmin() {
    return "admin".equalsIgnoreCase((String) claims.get("role"));
  }

  public Boolean isUser() {
    return "user".equalsIgnoreCase((String) claims.get("role"));
  }

  public String getCurrentUsuario() {
    return userName;
  }
}
