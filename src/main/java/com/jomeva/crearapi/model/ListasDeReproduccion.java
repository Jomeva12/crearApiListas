
package com.jomeva.crearapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Data;

/**
 *
 * @author Jorge Meléndez
 */
@Entity
@Table(name = "ListaDeReproduccion")
@Data
public class ListasDeReproduccion {
 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

 
  //@ManyToMany 
   // private List<Cancion> cancion;
 @ManyToMany
    @JoinTable(
        name = "lista_de_reproduccion_cancion",
        joinColumns = @JoinColumn(name = "lista_de_reproduccion_id"),
        inverseJoinColumns = @JoinColumn(name = "cancion_id"),
        uniqueConstraints = @UniqueConstraint(columnNames = { "lista_de_reproduccion_id", "cancion_id" })
    )
    private Set<Cancion> cancion = new HashSet<>();
   


 
}
