package com.cursojava.CursoJavaByLucas.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "usuarios")
@ToString @EqualsAndHashCode
public class Usuario {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    @Getter @Setter
    private Long id;
    
    @Getter @Setter @Column(name = "nombre")
    private String nombre;
    
    @Getter @Setter @Column(name="apellido")
    private String apellido;
    
    @Getter @Setter @Column(name="email")
    private String email;
    
    @Getter @Setter @Column(name="telefono")
    private String telefono;
    
    @Getter @Setter @Column(name="password")
    private String password;

}
