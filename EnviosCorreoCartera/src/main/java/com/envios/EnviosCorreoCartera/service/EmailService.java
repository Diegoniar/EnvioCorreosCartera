package com.envios.EnviosCorreoCartera.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public void enviarCorreoConAdjuntos(String para, String asunto, String cuerpo, String nombreAdjunto1, byte[] adjunto1, String nombreAdjunto2, byte[] adjunto2) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true); // true indica que es multipart

            helper.setFrom(fromEmail);
            helper.setTo(para);
            helper.setSubject(asunto);
            helper.setText(cuerpo);

            // Añadir los adjuntos
            helper.addAttachment(nombreAdjunto1.trim(), new ByteArrayResource(adjunto1));
            helper.addAttachment(nombreAdjunto2.trim(), new ByteArrayResource(adjunto2));

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            // Manejar la excepción
            throw new RuntimeException("Error al enviar correo con adjuntos: " + e.getMessage());
        }
    }
}
