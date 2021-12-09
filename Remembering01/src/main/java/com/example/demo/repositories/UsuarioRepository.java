package com.example.demo.repositories;

import java.util.ArrayList;
import java.util.Optional;

import com.example.demo.models.UsuarioModel;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<UsuarioModel,Long>{
   // recuerda que el repo es una interfaz que hereda de ciertas clases para aumentar su funcionalidad.En este caso hereda de CrudRepository<Model,TipodelId>
   abstract public ArrayList<UsuarioModel> findByNombre(String nombre);
   abstract public Optional<UsuarioModel> findById(Long id);
}
