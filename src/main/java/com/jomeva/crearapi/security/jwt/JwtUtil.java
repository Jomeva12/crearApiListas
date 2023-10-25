package com.jomeva.crearapi.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUtil {

  private final String secret = "springboot";

  // Extrae el username del token.
  public String extraerUserName(String token) {
    return extraerClaims(token, claims -> claims.getSubject());
  }

  public Date extraerExpiracion(String token) {
    return extraerClaims(token, Claims::getExpiration);
  }
  // Extrae los claims del payload del token.

  public <T> T extraerClaims(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extrarAllClaims(token);
    return claimsResolver.apply(claims);
  }

  // Extrae todos los claims del token.
  public Claims extrarAllClaims(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
  }

  //verificar si el token expiró
  private Boolean isTokenExpired(String token){
    return extraerExpiracion(token).before(new Date());
  }
  
  //generar el token
  
  public String generarToken(String userName, String role){
    Map<String,Object> claims=new HashMap<>();
    
    claims.put("role", role);
    return crearToken(claims,userName);
    
  }
  /**
 * Crea un token JWT con los (claims) y el nombre de usuario especificados.
 *
 * @param claims    Los datos que se incluirán en el token JWT.
 * @param userName  El nombre de usuario asociado con el token JWT.
 * @return          El token JWT generado.
 */
  private String crearToken(Map<String, Object> claims, String userName) {
log.info("Claims del token: {}", claims);
  // Crea un token JWT con las claims y configuraciones específicas.
    String token = Jwts.builder()
        .setClaims(claims)
        .setSubject(userName)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
log.info("rol: {}", claims);
    log.info("Token generado: {}", token);
    return token;
  }  
  /**
 * Valida un token JWT verificando si coincide con el nombre de usuario del usuario proporcionado
 * y si el token ha expirado.
 *
 * @param token       El token JWT que se va a validar.
 * @param userDetail  Los detalles del usuario para los que se va a realizar la validación.
 * @return            `true` si el token es válido, `false` en caso contrario.
 */
  public Boolean validarToken(String token, UserDetails userDetail){
      // Extrae el nombre de usuario del token.
    String userName=extraerUserName(token);
    // Comprueba si el nombre de usuario del token coincide con el nombre de usuario del usuario proporcionado.
    // Además, verifica si el token ha expirado.
    return (userName.equals(userDetail.getUsername())&& !isTokenExpired(token));
  }
  // Firma una clave secreta.
  public String firmarClaveSecreta(String claveSecreta) {
    return Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, claveSecreta.getBytes())
            .compact();
  }


}
