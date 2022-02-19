package com.example.demo.controller;

import com.example.demo.registration.RegistrationRequest;
import com.example.demo.registration.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController
@RequestMapping("api/v1/registration")
@AllArgsConstructor
@Slf4j
public class RegistrationController {

  @Autowired
  private final RegistrationService registrationService;
 
  @PostMapping
  public String register(@RequestBody RegistrationRequest request) {
    return registrationService.register(request);
  }

  @GetMapping(value="confirm")
  public String confirm(@RequestParam("token") String token) {
    log.info("Confirming token {}", token);
    return registrationService.confirmToken(token);
    // return "confirming";
  }
  
}


