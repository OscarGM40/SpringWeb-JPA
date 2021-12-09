package com.example.demo.controllers;

import java.util.ArrayList;
import java.util.Optional;

import com.example.demo.models.UsuarioModel;
import com.example.demo.services.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequestMapping("/usuarios")
public class UsuarioController {

   @Autowired
   UsuarioService usuarioService;

   @GetMapping()
   public ArrayList<UsuarioModel> getUsuarios() {
      return usuarioService.obtenerUsuarios();
   }

   @PostMapping()
   public UsuarioModel guardarUsuario(@RequestBody UsuarioModel usuario) {
      return usuarioService.guardarUsuario(usuario);
   }

   @PutMapping()
   public UsuarioModel actualizarUsuario(@RequestBody UsuarioModel usuario) {
      return usuarioService.updateUsuario(usuario);
   }

   // fijate que fácil me puedo inventar subrutas(mas abajo) y subrutas dinámicas(la de este controller)
   @GetMapping(path = "/{id}")
   public Optional<UsuarioModel> obtenerUsuarioPorId(@PathVariable("id") Long id) {
      return usuarioService.obtenerPorId(id);
   }
   // es casi igual de facil usar params(además que usamos una subruta)(usuarios/query?nombre=xxx) Fijate que realmente no necesito la subruta query pero lógicamente si los queryparams dinamicos
   @GetMapping("/query")
   public ArrayList<UsuarioModel> obtenerPorNombre(@RequestParam("nombre") String nombre) {
      return usuarioService.obtenerPorNombre(nombre);
   }

   @DeleteMapping(path="/{id}")
   public String deleteUsuario(@PathVariable("id")Long id){
      return usuarioService.deleteById(id);
   }
   /* @XXXMapping(path='/{dinamicParam}
    accesModifier returnType name(@PathVariable("match with the name given")Type name){ logic...}  
   */
}
