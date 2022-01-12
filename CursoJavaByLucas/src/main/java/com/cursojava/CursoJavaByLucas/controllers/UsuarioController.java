package com.cursojava.CursoJavaByLucas.controllers;

import com.cursojava.CursoJavaByLucas.dao.UsuarioDao;
import com.cursojava.CursoJavaByLucas.models.Usuario;
import com.cursojava.CursoJavaByLucas.utils.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

  @Autowired
  private UsuarioDao usuarioDao;

  @Autowired
  JWTUtil jwtUtil;

  private boolean validarToken(String token) {
    String usuarioId = jwtUtil.getKey(token);
    return usuarioId != null;
  }

  /*
   * Realmente no se extrae el token del header en cada petición.Se usa Spring
   * Security configurando un par de archivos que mapearán toda la aplicación con
   * middlewares.Es lo siguiente que masterizaré
   */
  @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
  public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization") String token) {

    System.out.println("token: " + token);
    String usuarioId = jwtUtil.getKey(token);
    System.out.println("usuarioId: " + usuarioId);

    if (validarToken(token)) {
      return usuarioDao.getUsuarios();
    } else {
      return List.of();
    }
  }

  /* Puedo ver que esa clase y encriptar la pass fue fácil. */
  @RequestMapping(value = "/api/usuarios/{id}", method = RequestMethod.DELETE)
  public void eliminarUsuario(
      @PathVariable Long id,
      @RequestHeader(value = "Authorization") String token) {
    if (!validarToken(token)) {
      return;
    }
    usuarioDao.eliminar(id);
  }

}
