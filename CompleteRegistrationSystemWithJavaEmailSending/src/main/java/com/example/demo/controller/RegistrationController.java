package com.example.demo.controller;

import com.example.demo.registration.RegistrationRequest;
import com.example.demo.registration.RegistrationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping("api/v1/registration")
@AllArgsConstructor
public class RegistrationController {

  @Autowired
  private RegistrationService registrationService;
 
  @PostMapping
  public String register(@RequestBody RegistrationRequest request) {
    return registrationService.register(request);
  }
  
}


