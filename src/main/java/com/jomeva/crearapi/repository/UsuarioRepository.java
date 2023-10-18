/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jomeva.crearapi.repository;

import com.jomeva.crearapi.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author johan
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
  Usuario findByEmail(@Param(("email")) String email);
}
