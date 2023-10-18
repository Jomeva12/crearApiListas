/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.service;

import com.jomeva.crearapi.model.Usuario;
import com.jomeva.crearapi.repository.UsuarioRepository;
import com.jomeva.crearapi.util.CancionesUtil;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 *
 * @author johan
 */
@Component
@Slf4j
public class UsuarioService {

  @Autowired
  private UsuarioRepository usuarioRepository;

//  public Usuario crearUsuario(Usuario usuario){    
//    return usuarioRepository.save(usuario);
//  }
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
