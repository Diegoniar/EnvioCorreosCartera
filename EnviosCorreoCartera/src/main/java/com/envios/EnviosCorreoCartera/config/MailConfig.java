package com.envios.EnviosCorreoCartera.config; // Asegúrate de que tu package sea el correcto

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    // --- BEANS PARA LA CONFIGURACIÓN DE CAJA ---

    @Bean("cajaMailProperties")
    @Primary
    @ConfigurationProperties(prefix = "mail.caja")
    public Properties cajaMailProperties() {
        return new Properties();
    }

    @Bean("cajaMailSender")
    @Primary
    public JavaMailSender cajaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties props = cajaMailProperties();

        mailSender.setHost(props.getProperty("host"));
        mailSender.setPort(Integer.parseInt(props.getProperty("port")));
        mailSender.setUsername(props.getProperty("username"));
        mailSender.setPassword(props.getProperty("password"));

        Properties mailProps = mailSender.getJavaMailProperties();
        mailProps.put("mail.transport.protocol", "smtp");
        mailProps.put("mail.smtp.auth", props.getProperty("properties.mail.smtp.auth", "true"));
        mailProps.put("mail.smtp.starttls.enable", props.getProperty("properties.mail.smtp.starttls.enable", "true"));

        // ---- ESTA ES LA LÍNEA NUEVA Y CRUCIAL ----
        // Guardamos el username para poder recuperarlo después en el servicio.
        mailProps.put("mail.username", props.getProperty("username"));

        return mailSender;
    }

    // --- BEANS PARA LA CONFIGURACIÓN DE EPS ---

    @Bean("epsMailProperties")
    @ConfigurationProperties(prefix = "mail.eps")
    public Properties epsMailProperties() {
        return new Properties();
    }

    @Bean("epsMailSender")
    public JavaMailSender epsMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        Properties props = epsMailProperties();

        mailSender.setHost(props.getProperty("host"));
        mailSender.setPort(Integer.parseInt(props.getProperty("port")));
        mailSender.setUsername(props.getProperty("username"));
        mailSender.setPassword(props.getProperty("password"));

        Properties mailProps = mailSender.getJavaMailProperties();
        mailProps.put("mail.transport.protocol", "smtp");
        mailProps.put("mail.smtp.auth", props.getProperty("properties.mail.smtp.auth", "true"));
        mailProps.put("mail.smtp.starttls.enable", props.getProperty("properties.mail.smtp.starttls.enable", "true"));

        // ---- ESTA ES LA LÍNEA NUEVA Y CRUCIAL ----
        mailProps.put("mail.username", props.getProperty("username"));

        return mailSender;
    }
}