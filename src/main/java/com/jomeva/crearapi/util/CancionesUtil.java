/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.jomeva.crearapi.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author johan
 */
public class CancionesUtil {
  
  public static ResponseEntity<String> getResponseEntity(String mensaje, HttpStatus http){
    return new ResponseEntity<String>("mensaje:" + mensaje,http);
  }
}
