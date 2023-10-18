/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

//@NamedQuery(name = "Usuario.findByEmail",query = "select u from Usuario where u.email=:email")
@NamedQuery(name = "Usuario.findByEmail", query = "select u from Usuario u where u.email = :email")

@DynamicUpdate
@DynamicInsert
@Data
@Entity
@Table(name = "usuario")
public class Usuario {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "nombre")
  private String nombre;
  @Column(name = "userName")
  private String user_name;
  @Column(name = "password")
  private String password;
@Column(name = "role")
  private String role;

@Column(name = "status")
private String status;
@Column(name = "email")
private String email;
//  public Usuario(String nombre, String userName, String password) {
//    this.nombre = nombre;
//    this.userName = userName;
//    this.password = password;
//  }

  
}
