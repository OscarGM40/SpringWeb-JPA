package io.getarrays.server.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import io.getarrays.server.enumerations.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server {

  @Id @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(unique = true) // no queremos la misma IP dos veces
  @NotEmpty(message = "Ip Address cannot be empty or null")
  private String ipAddress;
  private String name;
  private String memory;
  private String type;
  private String imageUrl;
  private Status status; // enum
  
}