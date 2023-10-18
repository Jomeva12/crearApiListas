/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.service;

import com.jomeva.crearapi.model.Cancion;
import com.jomeva.crearapi.repository.CancionRepository;
import com.jomeva.crearapi.repository.CancionRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Servicio que proporciona operaciones CRUD para la entidad "Cancion".
 */ 
@Component
public class CancionService {
  @Autowired
  private CancionRepository cancionRepository;
  
  
   /**
   * Crea una nueva canción en la base de datos.
   *
   * @param cancion La canción que se va a crear.
   * @return La canción creada.
   */
  public Cancion createCancion(Cancion cancion){
    return cancionRepository.save(cancion);
  }
    /**
   * Obtiene una canción por su ID.
   *
   * @param id El ID de la canción que se busca.
   * @return La canción con el ID especificado, o null si no se encuentra.
   */
  public Cancion getCancion(Long id){
    Optional<Cancion> optionalCancion=cancionRepository.findById(id);
    return optionalCancion.get();
  }
  public List<Cancion> getAllCanciones(){
   
    return cancionRepository.findAll();
  }
  
  /**
   * Elimina una canción por su ID.
   *
   * @param id El ID de la canción que se desea eliminar.
   */
  public void deleteCancion(Long id){
    cancionRepository.deleteById(id);
  }
}
