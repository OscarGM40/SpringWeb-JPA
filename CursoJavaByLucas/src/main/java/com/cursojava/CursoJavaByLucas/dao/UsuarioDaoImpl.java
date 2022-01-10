package com.cursojava.CursoJavaByLucas.dao;

import com.cursojava.CursoJavaByLucas.models.Usuario;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional //permite ejecutar transacciones
// @SuppressWarnings("unchecked")
public class UsuarioDaoImpl implements UsuarioDao {

  /*
   * EntityManager necesita del módulo SpringDataJPA.Me va a servir para hacer la
   * conexión con la base de datos
   */
  @PersistenceContext
  EntityManager entityManager;

  @Override
  public List<Usuario> getUsuarios() {
    // String query = "FROM Usuario";
    /* createQuery pide una query con HQL como primer argumento( y con SQL se puede?? ) */
    TypedQuery<Usuario> lQuery = entityManager.createQuery("FROM Usuario", Usuario.class);
    List<Usuario> resultado = lQuery.getResultList();
    return resultado;
  }

  @Override
  public void eliminar(Long id) {
    Usuario usuario = entityManager.find(Usuario.class, id);
    entityManager.remove(usuario);
  }

  @Override
  public void registrar(Usuario usuario) {
    entityManager.merge(usuario);
  }

  @Override
  public Usuario obtenerUsuarioPorCredenciales(Usuario usuario) {
    String query = "FROM Usuario WHERE email = :email";
    /* getResultList devuelve una unchecked List,hay que tratar eso con @SuppresWarnings("unchecked") o mejor con TypedQuery<T> para que tipe la resolución de la query */
    TypedQuery<Usuario> lQuery = entityManager.createQuery(query, Usuario.class)
      .setParameter("email", usuario.getEmail());
    List<Usuario> lista = lQuery.getResultList();

    if (lista.isEmpty()) {
      return null;
    }
    
    Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
    String passwordHashed = lista.get(0).getPassword();

    /* comprobar siempre las passwords con argon2.verify() */
    if (argon2.verify(passwordHashed, usuario.getPassword())) {
      return lista.get(0);
    } else {
      return null;
    }
  }

}
