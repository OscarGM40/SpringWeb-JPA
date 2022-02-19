package com.example.demo.email;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmailService implements EmailSender {

  private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
  private final JavaMailSender javaMailSender;

  @Override
  @Async // this method will be executed in a different thread
  public void send(String to, String email) {
    try {
      MimeMessage mimeMessage = javaMailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
      /* true si quiero que sea un HTML */
      helper.setText(email,true);
      helper.setTo(to);
      helper.setSubject("Confirmación de registro");
      helper.setFrom("hello@amigoscode.com");

    } catch (MessagingException e) {
      LOGGER.error("Failed sending email", e);
      throw new IllegalStateException("Failed to send email");
    }
  }

}
