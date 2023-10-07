/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.controller;

import com.jomeva.crearapi.model.Cancion;
import com.jomeva.crearapi.repository.CancionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Jorge Melendez
 */
@RestController
@RequestMapping("canciones") 
public class CancionController {
  @Autowired
  private CancionService cancionService;
  
  	
  @PostMapping
  public Cancion createCancion(@RequestBody Cancion cancion){   
    return cancionService.createCancion(cancion);
  }
  
  @GetMapping
  public List<Cancion> getAllCanciones(){
    return cancionService.getAllCanciones();
  }   
  
  @GetMapping("{id}")
  public Cancion buscarCancionporId(@PathVariable("id") Long id){   
    return cancionService.getCancion(id);
  }
  @DeleteMapping("{id}")
  public void  borrarCancionporId(@PathVariable("id") Long id){
   
     cancionService.deleteCancion(id);
  }
}
