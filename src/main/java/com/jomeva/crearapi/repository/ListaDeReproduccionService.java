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
 *
 * @author Jorge Melendez
 */
@Component
public class ListaDeReproduccionService {

    @Autowired
    private ListasDeReproduccionRepository listasDeReproduccionRepository;

    public ListasDeReproduccion createListasDeReproduccion(ListasDeReproduccion listasDeReproduccion) {
        return listasDeReproduccionRepository.save(listasDeReproduccion);
    }

    public ListasDeReproduccion getListasDeReproduccion(Long id) {
        Optional<ListasDeReproduccion> optionalListas = listasDeReproduccionRepository.findById(id);
        return optionalListas.orElse(null);
    }

    public List<ListasDeReproduccion> getAllListasDeReproduccion() {
        return listasDeReproduccionRepository.findAll();
    }

    public void deleteListasDeReproduccion(Long id) {
        listasDeReproduccionRepository.deleteById(id);
    }
public ListasDeReproduccion obtenerListaDeReproduccionPorNombre(String nombre) {
    return listasDeReproduccionRepository.findByNombre(nombre);
}
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

