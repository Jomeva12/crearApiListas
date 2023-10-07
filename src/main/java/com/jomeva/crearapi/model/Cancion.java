package com.jomeva.crearapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author Jorge Melendez
 */
@Entity
@Table(name = "Cancion")
public class Cancion {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String titulo;
  private String artista;
  private String album;
  private String anno;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getArtista() {
    return artista;
  }

  public void setArtista(String artista) {
    this.artista = artista;
  }

  public String getAlbum() {
    return album;
  }

  public void setAlbum(String album) {
    this.album = album;
  }

  public String getAnno() {
    return anno;
  }

  public void setAnno(String anno) {
    this.anno = anno;
  }

}
