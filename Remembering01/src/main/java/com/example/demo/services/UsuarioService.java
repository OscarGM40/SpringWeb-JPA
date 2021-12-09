package com.example.demo.services;

import java.util.ArrayList;
import java.util.Optional;

import com.example.demo.models.UsuarioModel;
import com.example.demo.repositories.UsuarioRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

   // minuto 10 VSCode es Dios
   /*
    * Con @Autowired Spring traerá la instancia que metí en el Spring Container ,
    * lo hizo @Repository??
    */
   // diria que me traje una instancia y la marqué para Spring,es decir que sólo la
   // marqué aquí.
   @Autowired
   UsuarioRepository usuarioRepository;

   public ArrayList<UsuarioModel> obtenerUsuarios() {
      return (ArrayList<UsuarioModel>) usuarioRepository.findAll();
   }

   public UsuarioModel guardarUsuario(UsuarioModel usuario) {
      return usuarioRepository.save(usuario);
   }

   public UsuarioModel updateUsuario(UsuarioModel usuario) {
      // el metodo save tiene dos posiciones.sin Id crea un Modelo,con un Id por el
      // Body lo actualiza
      return usuarioRepository.save(usuario);
   }

   public Optional<UsuarioModel> obtenerPorId(Long id) {
      return usuarioRepository.findById(id);
   }

   public ArrayList<UsuarioModel> obtenerPorNombre(String nombre) {
      return usuarioRepository.findByNombre(nombre);
   }
   
   public String deleteById(Long id) {
      try {
         usuarioRepository.deleteById(id);
         return "Usuario borrado";
      } catch (Exception e) {
         return "No se ha podido borrar por nombre,peazo idiota";
      }
   }

}
