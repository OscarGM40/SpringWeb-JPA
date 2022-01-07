package com.cursojava.CursoJavaByLucas.dao;

import java.util.List;

import com.cursojava.CursoJavaByLucas.models.Usuario;



public interface UsuarioDao {
  
  List<Usuario> getUsuarios();
  
}