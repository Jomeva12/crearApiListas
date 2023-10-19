/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.security;

import com.jomeva.crearapi.model.Usuario;
import com.jomeva.crearapi.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author johan
 */
@Slf4j
@Service
public class UsuarioDetailService implements UserDetailsService {

  @Autowired
  private UsuarioRepository usuarioRepository;
    
  private Usuario usuario;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("dentro del metodo loadByUserName {}", username);
    usuario=usuarioRepository.findByEmail(username);
    if (!Objects.isNull(usuario)) {
      
      return new User(usuario.getEmail(), usuario.getPassword(), new ArrayList<>());
    }else{
      throw new UsernameNotFoundException("Usuario no encontrado");
    }
  }

  public Usuario getUsuarioDetail(){
    return usuario;
  }
}
