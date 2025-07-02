package com.envios.EnviosCorreoCartera.controller;

import com.envios.EnviosCorreoCartera.model.Empresa;
import com.envios.EnviosCorreoCartera.model.ResultadoEnvio;
import com.envios.EnviosCorreoCartera.model.SubsiRad;
import com.envios.EnviosCorreoCartera.repository.EmpresaRepository;
import com.envios.EnviosCorreoCartera.repository.SubsiRadRepository;
import com.envios.EnviosCorreoCartera.service.EmailService;
import com.envios.EnviosCorreoCartera.service.SftpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
    public class EnvioController {

        @Autowired
        private SubsiRadRepository subsiRadRepository;
        @Autowired
        private SftpService sftpService;
        @Autowired
        private EmailService emailService;

        @Autowired
        private EmpresaRepository empresaRepository;

        // Muestra la página inicial con el formulario
        @GetMapping("/")
        public String mostrarFormulario() {
            return "index";
        }

    @PostMapping("/enviar-correos")
    public String procesarEnvio(
            @RequestParam("fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam("unidadNegocio") String unidadNegocio,
            @RequestParam("asunto") String asunto,
            @RequestParam("cuerpo") String cuerpo,
            @RequestParam("sftpPath") String sftpPath,
            Model model) {

        List<SubsiRad> registros = subsiRadRepository.findByFechaAndEnviado(fecha, "NO");
        List<ResultadoEnvio> resultados = new ArrayList<>();

        if (registros.isEmpty()) {
            model.addAttribute("mensaje", "No se encontraron registros pendientes de envío para la fecha: " + fecha);
            return "index";
        }

        for (SubsiRad registro : registros) {
            String nitLimpio = registro.getNit().trim();
            String emailDestino = "N/A"; // Valor por defecto en caso de error temprano
            try {
                Optional<Empresa> empresaOptional = empresaRepository.findByNitTrimmed(nitLimpio); //

                if (empresaOptional.isEmpty()) {
                    throw new RuntimeException("NIT no encontrado en la tabla de empresas 'subsi02'.");
                }

                String emailOriginal = empresaOptional.get().getEmail();

                String emailDestinoLimpio = emailOriginal.replaceAll("[^a-zA-Z0-9\\.\\-_@]", "").trim();

                System.out.println("Intentando enviar a la dirección limpia: '" + emailDestinoLimpio + "'");

                System.out.println("\n----------------------------------------------------");

                emailDestino = empresaOptional.get().getEmail().trim();

                System.out.println(">>>>>> Intentando enviar al correo: '" + emailDestino.trim() + "'");


                byte[] archivo1 = sftpService.descargarArchivo(registro.getNomarchivo(), sftpPath);
                byte[] archivo2 = sftpService.descargarArchivo(registro.getNomarchivo2(), sftpPath);

                String asuntoPersonalizado = asunto + " - Radicado: " + registro.getNrorad().trim();

                emailService.enviarCorreoConAdjuntos(
                        emailDestino,
                        asuntoPersonalizado,
                        cuerpo,
                        registro.getNomarchivo(),
                        archivo1,
                        registro.getNomarchivo2(),
                        archivo2,
                        unidadNegocio
                );

                registro.setEnviado("SI");
                subsiRadRepository.save(registro);

                resultados.add(new ResultadoEnvio(nitLimpio, emailDestino, "ENVIADO", "OK y marcado en BD"));

            } catch (Exception e) {
                // Este bloque ahora capturará tanto los fallos de envío como los NIT no encontrados.
                System.err.println("FALLO el envío para NIT " + nitLimpio + ": " + e.getMessage());
                resultados.add(new ResultadoEnvio(nitLimpio, emailDestino, "ERROR", e.getMessage()));
            }
        }

        model.addAttribute("resultados", resultados);
        model.addAttribute("mensaje", "Proceso de envío finalizado.");
        return "index";
    }
}
