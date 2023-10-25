/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.controller;

import com.jomeva.crearapi.model.Cancion;
import com.jomeva.crearapi.service.CancionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para gestionar las operaciones relacionadas con las canciones.
 */
@RestController
@RequestMapping("cancion") 
public class CancionController {
  @Autowired
  private CancionService cancionService;
  
  	/**
     * Crea una nueva canción en la base de datos.
     *
     * @param cancion La canción que se va a crear.
     * @return La canción creada.
     */
  @PostMapping
  public Cancion createCancion(@RequestBody Cancion cancion){   
    return cancionService.createCancion(cancion);
  }
   /**
     * Obtiene todas las canciones disponibles.
     *
     * @return Una lista de todas las canciones en la base de datos.
     */
  @GetMapping
  public List<Cancion> getAllCanciones(){
    return cancionService.getAllCanciones();
  }   
  
  /**
     * Obtiene una canción por su ID.
     *
     * @param id El ID de la canción que se busca.
     * @return La canción con el ID especificado, o null si no se encuentra.
     */
  @GetMapping("{id}")
  public Cancion buscarCancionporId(@PathVariable("id") Long id){   
    return cancionService.getCancion(id);
  }
     /**
     * Elimina una canción por su ID.
     *
     * @param id El ID de la canción que se desea eliminar.
     */
  @DeleteMapping("{id}")
  public void  borrarCancionporId(@PathVariable("id") Long id){
   
     cancionService.deleteCancion(id);
  }
}
