    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.jomeva.crearapi.repository;

import com.jomeva.crearapi.model.ListasDeReproduccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 *
 * @author Jorge
 */

@Repository
public interface ListasDeReproduccionRepository extends JpaRepository<ListasDeReproduccion, Long> {
   @Query("SELECT lr FROM ListasDeReproduccion lr WHERE lr.nombre = :nombre")
    ListasDeReproduccion findByNombre(@Param("nombre") String nombre);
}
