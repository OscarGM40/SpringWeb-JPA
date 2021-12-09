package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringRest01Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringRest01Application.class, args);
		System.out.println("En cualquier framework Web todo empieza por el controlador,que recibe una petición de la UI.En Java se comunica con el servicio(aquí está la lógica de las acciones).El Service se comunica con el repositorio,que es donde hace las conexiones con las BD.El repositorio utiliza el Modelo para saber el tipado de la información que va a traer.");
		System.out.println("Fijate que es una convención usar UsuarioModel | UsuarioRepository | UsuarioController | UsuarioService en Javita");
		System.out.println("y recuerda que las 4 llevan su anotación");
	}

}
