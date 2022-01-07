package com.cursojava.CursoJavaByLucas.controllers;


import com.cursojava.CursoJavaByLucas.dao.UsuarioDao;
import com.cursojava.CursoJavaByLucas.models.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    UsuarioDao usuarioDao;
    
    @RequestMapping(value="usuario/{id}",method = RequestMethod.GET)
    public Usuario getUsuario(@PathVariable String id){
        Usuario usuario = new Usuario();
        return usuario;
    }

    @RequestMapping(value="usuarios",method = RequestMethod.GET)
    public List<Usuario> getUsuarios(){
      return usuarioDao.getUsuarios();        
    }

    @RequestMapping(value="usuario",method = RequestMethod.PUT)
    public Usuario editarUsuario(){
        Usuario usuario = new Usuario();
        return usuario;
    }

    @RequestMapping(value = "usuario", method = RequestMethod.DELETE)
    public Usuario eliminarUsuario() {

        Usuario usuario = new Usuario();
        return usuario;
    }


}
