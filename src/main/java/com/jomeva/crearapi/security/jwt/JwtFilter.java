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

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    log.info("pathhh {}", request.getServletPath());
    if (request.getServletPath().matches("/user/login|/user/forgotPassword|/user/registrar")) {
      filterChain.doFilter(request, response);

    } else {
      String authorizationHeader = request.getHeader("Authorization");
      String token = null;
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        token = authorizationHeader.substring(7);
        userName = jwtUtil.extraerUserName(token);
        claims = jwtUtil.extrarAllClaims(token);
      }
      if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetail = usuarioDetailService.loadUserByUsername(userName);

        if (jwtUtil.validarToken(token, userDetail)) {
          UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                  = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
          new WebAuthenticationDetailsSource().buildDetails(request);
          SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

      }
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
