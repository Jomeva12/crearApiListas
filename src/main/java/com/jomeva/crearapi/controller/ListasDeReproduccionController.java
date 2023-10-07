  /*
   * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
   * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
   */
  package com.jomeva.crearapi.controller;

import com.jomeva.crearapi.model.Cancion;
  import com.jomeva.crearapi.model.ListasDeReproduccion;
  import com.jomeva.crearapi.repository.ListaDeReproduccionService;
  import com.jomeva.crearapi.repository.ListasDeReproduccionRepository;
  import java.net.URI;
  import java.util.List;
  import java.util.Optional;
  import org.springframework.beans.factory.annotation.Autowired;
  import org.springframework.http.ResponseEntity;
  import org.springframework.web.bind.annotation.CrossOrigin;
  import org.springframework.web.bind.annotation.DeleteMapping;
  import org.springframework.web.bind.annotation.GetMapping;
  import org.springframework.web.bind.annotation.PathVariable;
  import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
  import org.springframework.web.bind.annotation.RequestBody;
  import org.springframework.web.bind.annotation.RequestMapping;
  import org.springframework.web.bind.annotation.RestController;
  import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

  /**
   *
   * @author Jorge Melendez
   */
  @RestController
  @RequestMapping("/lists")
  public class ListasDeReproduccionController {

    @Autowired
    private ListaDeReproduccionService listaDeReproduccionService;

   @PostMapping
      public ResponseEntity<Object> crearListaDeReproduccion(@RequestBody ListasDeReproduccion listaDeReproduccion) {
          if (listaDeReproduccion.getNombre() == null || listaDeReproduccion.getNombre().isEmpty()) {
              return ResponseEntity.badRequest().body("El nombre de la lista no es válido");
          }

          ListasDeReproduccion nuevaLista = listaDeReproduccionService.createListasDeReproduccion(listaDeReproduccion);
          URI location = ServletUriComponentsBuilder
                  .fromCurrentRequest()
                  .path("/{id}")
                  .buildAndExpand(nuevaLista.getId())
                  .toUri();

          return ResponseEntity.created(location).body(nuevaLista);
      }

    @GetMapping
    public List<ListasDeReproduccion> getAllListasDeReproduccion() {
      return listaDeReproduccionService.getAllListasDeReproduccion();
    }


    @GetMapping("/get/{listName}")
      public ResponseEntity<Object> obtenerListaDeReproduccionPorNombre(@PathVariable String listName) {
          ListasDeReproduccion lista = listaDeReproduccionService.obtenerListaDeReproduccionPorNombre(listName);

          if (lista != null) {
              return ResponseEntity.ok(lista);
          } else {
              return ResponseEntity.notFound().build();
          }
      }


  @DeleteMapping("/delete/{listName}")
  public ResponseEntity<String> deleteListaDeReproduccionPorNombre(@PathVariable String listName) {
      // Llama al servicio para eliminar la lista de reproducción por nombre
      boolean deleted = listaDeReproduccionService.deleteListaDeReproduccionPorNombre(listName);

      if (deleted) {
          return ResponseEntity.noContent().build(); // Devuelve 204 No Content si se eliminó correctamente
      } else {    
          return ResponseEntity.notFound().build(); // Devuelve 404 Not Found si la lista no existe
      }
  }

      @GetMapping("/{id}")
    public void deleteListasDeReproduccionById(@PathVariable("id") Long id) {
       listaDeReproduccionService.deleteListasDeReproduccion(id);
    }
    
     @PutMapping("/lists/add/{playlistId}")
  public ResponseEntity<Object> agregarCancionALista(@PathVariable Long playlistId,@RequestBody Cancion cancion)
  {
    // Busca la lista de reproducción por su ID
    ListasDeReproduccion listaDeReproduccion = listaDeReproduccionService.getListasDeReproduccion(playlistId);

    if (listaDeReproduccion == null) {
      return ResponseEntity.notFound().build();
    }

    // Agrega la canción a la lista de reproducción
    listaDeReproduccion.getCanciones().add(cancion);

    // Guarda la lista de reproducción actualizada
    listaDeReproduccion = listaDeReproduccionService.createListasDeReproduccion(listaDeReproduccion);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(listaDeReproduccion.getId())
        .toUri();

    return ResponseEntity.created(location).body(listaDeReproduccion);
  }
  }
