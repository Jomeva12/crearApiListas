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
 * Controlador para gestionar las operaciones relacionadas con las listas de reproducción.
 */
@RestController
@RequestMapping("/lists")
public class ListasDeReproduccionController {

  @Autowired
  private ListaDeReproduccionService listaDeReproduccionService;

   /**
     * Crea una nueva lista de reproducción en la base de datos.
     *
     * @param listaDeReproduccion La lista de reproducción que se va a crear.
     * @return ResponseEntity que contiene la lista de reproducción creada o un mensaje de error en caso de un nombre de lista no válido.
     */
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
/**
     * Obtiene todas las listas de reproducción disponibles.
     *
     * @return Una lista de todas las listas de reproducción en la base de datos.
     */
  @GetMapping
  public List<ListasDeReproduccion> getAllListasDeReproduccion() {
    return listaDeReproduccionService.getAllListasDeReproduccion();
  }

   /**
     * Obtiene una lista de reproducción por su nombre.
     *
     * @param listName El nombre de la lista de reproducción que se busca.
     * @return ResponseEntity que contiene la lista de reproducción encontrada o un mensaje de error si no se encuentra.
     */
  @GetMapping("/get/{listName}")
  public ResponseEntity<Object> obtenerListaDeReproduccionPorNombre(@PathVariable String listName) {
    ListasDeReproduccion lista = listaDeReproduccionService.obtenerListaDeReproduccionPorNombre(listName);

    if (lista != null) {
      return ResponseEntity.ok(lista);
    } else {
      return ResponseEntity.notFound().build();
    }
  }

   /**
     * Elimina una lista de reproducción por su nombre.
     *
     * @param listName El nombre de la lista de reproducción que se desea eliminar.
     * @return ResponseEntity que indica si se eliminó correctamente o si la lista no existe.
     */
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

   /**
     * Elimina una lista de reproducción por su ID.
     *
     * @param id El ID de la lista de reproducción que se desea eliminar.
     */
  @GetMapping("/{id}")
  public void deleteListasDeReproduccionById(@PathVariable("id") Long id) {
    listaDeReproduccionService.deleteListasDeReproduccion(id);
  }
  /**
   *mando dos id para comparar si en la lista existe la canción
   * 
   * @param playlistId El ID de la lista de reproducción a la que se agregará la canción.
   * @param idCancion    id de la canción que se agregará a la lista.
   */
@GetMapping("/getcancion/{playlistId}/{idCancion}")
public ResponseEntity<Boolean> existeCancionEnLista(@PathVariable Long playlistId, @PathVariable Long idCancion) {
    boolean cancionEnLista = listaDeReproduccionService.existeCancionEnLista(idCancion, playlistId);
    System.out.println("cancion de lista---> " + cancionEnLista);
    return ResponseEntity.ok(cancionEnLista);
}

  /**
     * Agrega una canción a una lista de reproducción.
     *
     * @param playlistId El ID de la lista de reproducción a la que se agregará la canción.
     * @param cancion    La canción que se agregará a la lista.
     * @return ResponseEntity que contiene la lista de reproducción actualizada o un mensaje de error si la lista no se encuentra.
     */
   
  @PutMapping("/add/{playlistId}")
  public ResponseEntity<Object> agregarCancionALista(@PathVariable Long playlistId, @RequestBody Cancion cancion) {
    // Busca la lista de reproducción por su ID
    System.out.println("-----> "+cancion);
    ListasDeReproduccion listaDeReproduccion = listaDeReproduccionService.getListasDeReproduccion(playlistId);
    Long idCancion = cancion.getId();
    System.out.println("-----> "+idCancion);
    if (listaDeReproduccion == null) {
      return ResponseEntity.notFound().build();
    }
     // Verifica si la relación entre la canción y la lista ya existe
    if (listaDeReproduccion.getCancion().stream().anyMatch(c -> c.getId().equals(idCancion))) {
        return ResponseEntity.badRequest().body("La canción ya está en la lista de reproducción.");
    }

    // Agrega la canción a la lista de reproducción
    listaDeReproduccion.getCancion().add(cancion);

    // Guarda la lista de reproducción actualizada
    listaDeReproduccion = listaDeReproduccionService.createListasDeReproduccion(listaDeReproduccion);

    URI location = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(listaDeReproduccion.getId())
            .toUri();

    return ResponseEntity.created(location).body(listaDeReproduccion);
    
//
//    // Agrega la canción a la lista de reproducción
//    listaDeReproduccion.getCancion().add(cancion);
//
//    // Guarda la lista de reproducción actualizada
//    listaDeReproduccion = listaDeReproduccionService.createListasDeReproduccion(listaDeReproduccion);
//
//    URI location = ServletUriComponentsBuilder
//            .fromCurrentRequest()
//            .path("/{id}")
//            .buildAndExpand(listaDeReproduccion.getId())
//            .toUri();
//
//    return ResponseEntity.created(location).body(listaDeReproduccion);
  }
}
