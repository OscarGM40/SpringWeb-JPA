package io.getarrays.server.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.getarrays.server.enumerations.Status;
import io.getarrays.server.implementations.ServerServiceImpl;
import io.getarrays.server.models.Response;
import io.getarrays.server.models.Server;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor // crea el constructor e inyecta la dependency
public class ServerController {

  // de nuevo usamos inyección por constructor
  private final ServerServiceImpl serverService;

  @GetMapping("/list")
  public ResponseEntity<Response> getServers() throws InterruptedException {
    TimeUnit.SECONDS.sleep(3);
    return ResponseEntity.ok(
        Response.builder()
            .timeStamp(LocalDateTime.now())
            .data(Map.of("servers", serverService.list(10)))
            .message("Servers retrieved")
            .status(HttpStatus.OK)
            .statusCode(HttpStatus.OK.value())
            .build());
  }

  @GetMapping("/ping/{ipAddress}")
  public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
    Server server = serverService.ping(ipAddress);
    return ResponseEntity.ok(
         Response.builder()
            .timeStamp(LocalDateTime.now())
            .data(Map.of("server", server))
            .message(server.getStatus() == Status.SERVER_UP ? "Ping success" : "Ping failed")
            .status(HttpStatus.OK) //200
            .statusCode(HttpStatus.OK.value())
            .build());
  }

  @PostMapping("/save")
  public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
    return ResponseEntity.ok(
        Response.builder()
            .timeStamp(LocalDateTime.now())
            .data(Map.of("server", serverService.create(server)))
            .message("Server created")
            .status(HttpStatus.CREATED) // 201
            .statusCode(HttpStatus.CREATED.value())
            .build());
  }

  @GetMapping("/get/{id}")
  public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
    Server server = serverService.get(id);
    return ResponseEntity.ok(
         Response.builder()
            .timeStamp(LocalDateTime.now())
            .data(Map.of("server", server))
            .message("Server retrieved")
            .status(HttpStatus.OK) //200
            .statusCode(HttpStatus.OK.value())
            .build());
  }

  @DeleteMapping("/delete/{id}")
  public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
    return ResponseEntity.ok(
         Response.builder()
            .timeStamp(LocalDateTime.now())
            .data(Map.of("server", serverService.delete(id)))
            .message("Server deleted successfully")
            .status(HttpStatus.OK) // 200
            .statusCode(HttpStatus.OK.value()) // 200
            .build());
  }

  @GetMapping(path = "/image/{fileName}",produces = MediaType.IMAGE_PNG_VALUE)
  public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
    return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Descargas/" + fileName));
  }

}
