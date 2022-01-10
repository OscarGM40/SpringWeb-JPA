package com.cursojava.CursoJavaByLucas.controllers;

import com.cursojava.CursoJavaByLucas.dao.UsuarioDao;
import com.cursojava.CursoJavaByLucas.models.Usuario;
import com.cursojava.CursoJavaByLucas.utils.JWTUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@RestController
public class AuthController {

    @Autowired
    UsuarioDao usuarioDao;

    @Autowired
    JWTUtil jwtUtil;

    /* CREAR USUARIO */
    @RequestMapping(value = "api/usuarios", method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario) {

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        /* encriptar la pass con argon2.hash() */
        String hash = argon2.hash(1, 1024, 1, usuario.getPassword());
        usuario.setPassword(hash);
        usuarioDao.registrar(usuario);
    }

    /* LOGEAR USUARIO */
    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    public String login(@RequestBody Usuario usuario) {
        Usuario usuarioLogeado = usuarioDao.obtenerUsuarioPorCredenciales(usuario);
        if (usuarioLogeado == null) {
            return "FAIL";
        } else {
            /* crear un token con jjwt es sencillo */
            return jwtUtil.create(String.valueOf(usuarioLogeado.getId()), usuarioLogeado.getEmail());
        }
    }
}
