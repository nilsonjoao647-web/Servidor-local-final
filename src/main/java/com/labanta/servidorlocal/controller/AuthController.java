package com.labanta.servidorlocal.controller;

import com.labanta.servidorlocal.dto.LoginRequestDTO;
import com.labanta.servidorlocal.dto.RegistoRequestDTO;
import com.labanta.servidorlocal.model.Utilizador;
import com.labanta.servidorlocal.service.AuthService;
import com.labanta.servidorlocal.service.EmailService;
import com.labanta.servidorlocal.service.GeoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final GeoService geoService;
    private final EmailService emailService;

    public AuthController(
            AuthService authService,
            GeoService geoService,
            EmailService emailService
    ) {
        this.authService = authService;
        this.geoService = geoService;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    public Utilizador register(@RequestBody RegistoRequestDTO dados) {
        return authService.registarUtilizador(dados);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDTO dados) {
        return authService.login(dados);
    }

    @PostMapping("/alerta-login")
    public ResponseEntity<String> alertaLogin(
            @RequestParam String email,
            @RequestParam String ip
    ) {

        // 1. Passar o IP ao GeoService
        var localizacao = String.valueOf(geoService.localizarIp(ip));

        // 2. Enviar os dados da localização por email
        emailService.enviarAlertaSeguranca(
                email,
                localizacao,
                ip
        );

        // 3. Mensagem de sucesso
        return ResponseEntity.ok("Alerta de segurança processado!");
    }
}

