/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.repository;

import com.jomeva.crearapi.model.ListasDeReproduccion;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * Servicio que proporciona operaciones CRUD para la entidad "ListasDeReproduccion".
 */
@Component
public class ListaDeReproduccionService {

    @Autowired
    private ListasDeReproduccionRepository listasDeReproduccionRepository;
/**
     * Crea una nueva lista de reproducción en la base de datos.
     *
     * @param listasDeReproduccion La lista de reproducción que se va a crear.
     * @return La lista de reproducción creada.
     */
    public ListasDeReproduccion createListasDeReproduccion(ListasDeReproduccion listasDeReproduccion) {
        return listasDeReproduccionRepository.save(listasDeReproduccion);
    }
   /**
     * Obtiene una lista de reproducción por su ID.
     *
     * @param id El ID de la lista de reproducción que se busca.
     * @return La lista de reproducción con el ID especificado, o null si no se encuentra.
     */
    public ListasDeReproduccion getListasDeReproduccion(Long id) {
        Optional<ListasDeReproduccion> optionalListas = listasDeReproduccionRepository.findById(id);
        return optionalListas.orElse(null);
    }
    public boolean existeCancionEnLista( Long idCancion, Long idLista){
       // Obtén la lista de reproducción por su ID
    ListasDeReproduccion listaDeReproduccion = listasDeReproduccionRepository.getReferenceById(idLista);
     
    if (listaDeReproduccion == null) {
        // La lista de reproducción no existe
        return false;
    }

    // Verifica si la canción se encuentra en la lista
    return listaDeReproduccion.getCancion().stream()
            .anyMatch(c -> c.getId().equals(idCancion));
    }
 /**
     * Obtiene todas las listas de reproducción disponibles.
     *
     * @return Una lista de todas las listas de reproducción en la base de datos.
     */
    public List<ListasDeReproduccion> getAllListasDeReproduccion() {
        return listasDeReproduccionRepository.findAll();
    }
/**
     * Elimina una lista de reproducción por su ID.
     *
     * @param id El ID de la lista de reproducción que se desea eliminar.
     */
    public void deleteListasDeReproduccion(Long id) {
        listasDeReproduccionRepository.deleteById(id);
    }
    /**
     * Obtiene una lista de reproducción por su nombre.
     *
     * @param nombre El nombre de la lista de reproducción que se busca.
     * @return La lista de reproducción con el nombre especificado, o null si no se encuentra.
     */
public ListasDeReproduccion obtenerListaDeReproduccionPorNombre(String nombre) {
    return listasDeReproduccionRepository.findByNombre(nombre);
}

    /**
     * Elimina una lista de reproducción por su nombre.
     *
     * @param nombre El nombre de la lista de reproducción que se desea eliminar.
     * @return `true` si se eliminó correctamente, `false` si la lista no existe.
     */
public boolean deleteListaDeReproduccionPorNombre(String nombre) {
        // Busca la lista de reproducción por nombre
        ListasDeReproduccion lista = listasDeReproduccionRepository.findByNombre(nombre);

        if (lista != null) {
            listasDeReproduccionRepository.delete(lista); // Elimina la lista si se encuentra
            return true; // Indica que se eliminó correctamente
        } else {
            return false; // Indica que la lista no existe
        }
    }
   /**
     * Actualiza una lista de reproducción en la base de datos.
     *
     * @param listaDeReproduccion La lista de reproducción con los cambios.
     * @return La lista de reproducción actualizada o `null` si no existe.
     */
    public ListasDeReproduccion updateListasDeReproduccion(ListasDeReproduccion listaDeReproduccion) {
        // Verifica si la lista de reproducción existe en la base de datos
        ListasDeReproduccion listaExistente = listasDeReproduccionRepository.findById(listaDeReproduccion.getId()).orElse(null);

        if (listaExistente != null) {
            // Actualiza los campos de la lista existente con los valores de la lista proporcionada
            listaExistente.setNombre(listaDeReproduccion.getNombre());
            listaExistente.setDescripcion(listaDeReproduccion.getDescripcion());

            // Guarda la lista actualizada en la base de datos
            return listasDeReproduccionRepository.save(listaExistente);
        } else {
            return null; // La lista de reproducción no existe
        }
    }
}

