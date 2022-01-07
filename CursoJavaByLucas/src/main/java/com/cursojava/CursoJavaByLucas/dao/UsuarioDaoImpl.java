package com.cursojava.CursoJavaByLucas.dao;

import com.cursojava.CursoJavaByLucas.models.Usuario;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class UsuarioDaoImpl implements UsuarioDao{

  /* EntityManager necesita del módulo SpringDataJPA.Me va a servir para hacer la conexión con la base de datos */
  @PersistenceContext
  EntityManager  entityManager;
  
  @Override
  @SuppressWarnings("unchecked")
  public List<Usuario> getUsuarios() {
    String query = "FROM Usuario";
    List<Usuario> resultado = Collections.checkedList(entityManager.createQuery(query).getResultList(), Usuario.class);
    return resultado;
  }
  
}
