package com.jomeva.crearapi.service;

import com.jomeva.crearapi.model.Usuario;
import com.jomeva.crearapi.repository.UsuarioRepository;
import com.jomeva.crearapi.security.UsuarioDetailService;
import com.jomeva.crearapi.security.jwt.JwtUtil;
import com.jomeva.crearapi.util.CancionesUtil;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UsuarioDetailService usuarioDetailService;

  @Autowired
  private JwtUtil jwtUtil;

  public ResponseEntity<String> login(Map<String, String> requestMap) {
    log.info("Intento de inicio de sesión. Datos: {}", requestMap);
    try {
     
      Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
  
      if (authentication.isAuthenticated()) {
 
        if (usuarioDetailService.getUsuarioDetail().getStatus().equalsIgnoreCase("true")) {
          
          
           String token = jwtUtil.generarToken(usuarioDetailService.getUsuarioDetail().getEmail(), usuarioDetailService.getUsuarioDetail().getRole());
      

                // Devuelve una respuesta JSON con el token
                return ResponseEntity.ok("{\"token\": \"" + token + "\"}");
       
        }
      }
    } catch (Exception e) {
      log.error("Error durante el inicio de sesión: {}", e.getMessage());
      e.printStackTrace();
      return CancionesUtil.getResponseEntity("No se pudo loguear", HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<String>("{credenciales incorrectas}", HttpStatus.BAD_REQUEST);
  }
/*"{toquen->:} " + jwtUtil.generarToken(
                  usuarioDetailService.getUsuarioDetail().getEmail(),
                  usuarioDetailService.getUsuarioDetail().getRole()),
                  HttpStatus.OK*/
  public Usuario getUsuario(Long id) {
    Optional<Usuario> usuario = usuarioRepository.findById(id);
    return usuario.get();
  }

  public ResponseEntity<String> registrar(Map<String, String> requestMap) {

    // try {
    if (validarRegistro(requestMap)) {
      Usuario usuario = usuarioRepository.findByEmail(requestMap.get("email"));
      if (Objects.isNull(usuario)) {
        //agregarlo si no existe
        usuario = getUsuarioFromMap(requestMap); // Obtén el usuario a partir del mapa

        // Encripta la contraseña antes de guardarla
        String rawPassword = requestMap.get("password");
        String hashedPassword = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        usuario.setPassword(hashedPassword);

        usuarioRepository.save(usuario); // Guarda el usuario en la base de datos

        return CancionesUtil.getResponseEntity("Usuario registrado con existo", HttpStatus.CREATED);
      } else {
        return CancionesUtil.getResponseEntity("El usuario con este email ya existe", HttpStatus.BAD_REQUEST);
      }
    } else {
      return CancionesUtil.getResponseEntity("Error en los datos", HttpStatus.BAD_REQUEST);
    }
    // } catch (Exception e) {
    //System.out.println("Error al crear usuario " + e.toString());
    // }

    //return CancionesUtil.getResponseEntity("Error en los datos", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private Usuario getUsuarioFromMap(Map<String, String> requestMap) {
    Usuario usuario = new Usuario();
    usuario.setUser_name(requestMap.get("user_name"));
    usuario.setEmail(requestMap.get("email"));
    usuario.setNombre(requestMap.get("nombre"));
    usuario.setRole("user");
    usuario.setStatus("false");
    usuario.setPassword(requestMap.get("password"));
    return usuario;
  }

  private boolean validarRegistro(Map<String, String> requestMap) {
    if (requestMap.containsKey("user_name")
            && requestMap.containsKey("email")
            && requestMap.containsKey("nombre")
            && requestMap.containsKey("password")) {
      return true;
    }
    return false;
  }

}
