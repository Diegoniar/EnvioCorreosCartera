package com.envios.EnviosCorreoCartera.service; // Asegúrate de que tu package sea el correcto

import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value; // <-- Importante
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // Inyectamos los dos emisores de correo (esto está bien)
    @Autowired
    @Qualifier("cajaMailSender")
    private JavaMailSender cajaMailSender;

    @Autowired
    @Qualifier("epsMailSender")
    private JavaMailSender epsMailSender;

    // --- NUEVA SECCIÓN: Inyectamos los emails de los remitentes directamente ---
    @Value("${mail.caja.username}")
    private String cajaFromEmail;

    @Value("${mail.eps.username}")
    private String epsFromEmail;

    public void enviarCorreoConAdjuntos(String para, String asunto, String cuerpo,
                                        String nombreAdjunto1, byte[] adjunto1,
                                        String nombreAdjunto2, byte[] adjunto2,
                                        String unidadNegocio) {
        try {
            // --- LÓGICA SIMPLIFICADA ---
            JavaMailSender mailSender;
            String fromEmail;

            // 1. Elegimos qué emisor y qué email de remitente usar
            if ("eps".equalsIgnoreCase(unidadNegocio)) {
                mailSender = epsMailSender;
                fromEmail = epsFromEmail; // Usamos el valor inyectado
            } else {
                mailSender = cajaMailSender;
                fromEmail = cajaFromEmail; // Usamos el valor inyectado
            }

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            // 2. Ahora 'fromEmail' tendrá el valor correcto desde application.properties
            helper.setFrom(fromEmail);
            helper.setTo(para);
            helper.setSubject(asunto);
            helper.setText(cuerpo);

            helper.addAttachment(nombreAdjunto1.trim(), new ByteArrayResource(adjunto1));
            helper.addAttachment(nombreAdjunto2.trim(), new ByteArrayResource(adjunto2));

            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException("Error al enviar correo con adjuntos: " + e.getMessage(), e);
        }
    }
}