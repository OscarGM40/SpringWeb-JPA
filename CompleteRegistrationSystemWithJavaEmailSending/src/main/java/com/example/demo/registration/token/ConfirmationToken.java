package com.example.demo.registration.token;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ConfirmationToken {

  @Id
  @SequenceGenerator(name = "confirmation_token_sequence", sequenceName = "confirmation_token_sequence", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_token_sequence")
  private Long id;
  @Column(nullable=false)
  private String token;
  @Column(nullable=false)
  private LocalDateTime issuedAt;
  
  @Column(nullable=false)
  private LocalDateTime expiresAt;
  private LocalDateTime confirmedAt;

  public ConfirmationToken(String token,
      LocalDateTime issuedAt,
      LocalDateTime expiresAt,
      LocalDateTime confirmedAt) {
    this.token = token;
    this.issuedAt = issuedAt;
    this.expiresAt = expiresAt;
    this.confirmedAt = confirmedAt;
  }

}
