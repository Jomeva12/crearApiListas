/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.controller;

import com.jomeva.crearapi.security.UsuarioDetailService;
import com.jomeva.crearapi.service.UsuarioService;
import com.jomeva.crearapi.util.CancionesUtil;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author johan
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UsuarioController {
@Autowired
private UsuarioDetailService usuarioDetailService;
  @Autowired
  private UsuarioService usuarioService;

  @PostMapping("/registrar")
  public ResponseEntity<String> crearUsuario(@RequestBody(required = true) Map<String, String> requestMap) {
    log.info("registro de usuario-> {}", requestMap);

    try {
     return usuarioService.registrar(requestMap);
    }catch(Exception e){
     e.printStackTrace();
    }
      return CancionesUtil.getResponseEntity("No se pudo registrar", HttpStatus.BAD_REQUEST);
    }

   @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody(required = true) Map<String, String> requestMap) {
    log.info("Solicitud de inicio de sesión recibida. Datos: {}", requestMap);
    
    try {
        // Llama a tu servicio de autenticación aquí y agrega más mensajes de registro según sea necesario.
        return usuarioService.login(requestMap);
    } catch (Exception e) {
        log.error("Error durante el inicio de sesión: {}", e.getMessage());
        e.printStackTrace();
        return CancionesUtil.getResponseEntity("No se pudo loguear", HttpStatus.BAD_REQUEST);
    }
  }
}
