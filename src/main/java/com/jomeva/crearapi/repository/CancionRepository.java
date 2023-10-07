/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jomeva.crearapi.repository;

import com.jomeva.crearapi.model.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jorge Melendez
 */
@Repository
public interface CancionRepository extends JpaRepository<Cancion,Long>{
  
}
