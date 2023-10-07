/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.repository;

import com.jomeva.crearapi.model.Cancion;
import com.jomeva.crearapi.repository.CancionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Jorge Melendez
 */
@Component
public class CancionService {
  @Autowired
  private CancionRepository cancionRepository;
  
  public Cancion createCancion(Cancion cancion){
    return cancionRepository.save(cancion);
  }
  public Cancion getCancion(Long id){
    Optional<Cancion> optionalCancion=cancionRepository.findById(id);
    return optionalCancion.get();
  }
  public List<Cancion> getAllCanciones(){
   
    return cancionRepository.findAll();
  }
  public void deleteCancion(Long id){
    cancionRepository.deleteById(id);
  }
}
