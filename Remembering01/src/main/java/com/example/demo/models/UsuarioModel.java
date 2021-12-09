package com.example.demo.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "usuario")
public class UsuarioModel {

   // Recuerda que para enlazar a una DB hay que definir la cadena de conexión en el application.properties.Hay una propiedad obligatoria en la que tengo que especificar si la db existe o no,asinto

   @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(unique = true,nullable = false)
   private Long id;

   @Column(name = "nombre")
   private String nombre;
   
   @Column(name="email")
   private String email;

   @Column(columnDefinition = "integer default 0")
   private Integer prioridad;


   public String getEmail() {
      return email;
   }

   public Integer getPrioridad() {
      return prioridad;
   }

   public void setPrioridad(Integer prioridad) {
      this.prioridad = prioridad;
   }

   public String getNombre() {
      return nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public void setEmail(String email) {
      this.email = email;
   }

}
