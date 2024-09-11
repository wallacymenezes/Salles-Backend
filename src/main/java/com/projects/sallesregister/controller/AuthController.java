package com.projects.sallesregister.controller;

import com.projects.sallesregister.model.EmailRequest;
import com.projects.sallesregister.model.NovaSenha;
import com.projects.sallesregister.model.UsuarioLogin;
import com.projects.sallesregister.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Endpoint para solicitar o envio do otp de recuperação
    @PostMapping("/recover-token")
    public ResponseEntity<Void> createRecoverToken(@Valid @RequestBody EmailRequest emailRequest) {
        authService.createRecoverToken(emailRequest.getEmail());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<Optional<UsuarioLogin>> validateOtp(
            @RequestParam String otp,
            @RequestParam String email) {

        Optional<UsuarioLogin> usuarioLogin = authService.validateOtpAndGenerateToken(otp, email);
        return ResponseEntity.ok(usuarioLogin);
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody UsuarioLogin usuarioLogin) {
        authService.changePassword(usuarioLogin);
        return ResponseEntity.noContent().build();
    }
}