package com.projects.sallesregister.service;

import java.time.Instant;
import java.util.Optional;

import com.projects.sallesregister.model.RecuperadorSenha;
import com.projects.sallesregister.model.Usuario;
import com.projects.sallesregister.model.UsuarioLogin;
import com.projects.sallesregister.repository.RecuperadorSenhaRepository;
import com.projects.sallesregister.repository.UsuarioRepository;
import com.projects.sallesregister.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private Long otpExpirationMinutes = 15L;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecuperadorSenhaRepository recuperadorSenhaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtService jwtService;

    // Gera e envia o token de recuperação por e-mail
    public void createRecoverToken(String email) {

        if (usuarioRepository.findByUsuario(email).isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado", null);

        String otp = generateOtp();
        RecuperadorSenha entity = new RecuperadorSenha();
        entity.setEmail(email);
        entity.setToken(otp);
        entity.setExpiration(Instant.now().plusSeconds(otpExpirationMinutes * 60L));
        recuperadorSenhaRepository.save(entity);

        String text = "Seu código de redefinição de senha: \n\n"
                + otp + ". Validade de " + otpExpirationMinutes + " minutos";

        emailService.sendEmail(email, "Recuperação de Senha", text);
    }

    public Optional<UsuarioLogin> validateOtpAndGenerateToken(String otp, String email) {
        RecuperadorSenha recoveryRequest = recuperadorSenhaRepository.findValidTokens(otp, Instant.now())
                .stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid or expired OTP"));

        Optional<Usuario> usuario = Optional.ofNullable(usuarioRepository.findByUsuario(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")));

        Optional<UsuarioLogin> usuarioLogin = Optional.of(new UsuarioLogin());

        if(usuario.isPresent()) {

            usuarioLogin.get().setId(usuario.get().getId());
            usuarioLogin.get().setNome(usuario.get().getNome());
            usuarioLogin.get().setFoto(usuario.get().getFoto());
            usuarioLogin.get().setCargo(usuario.get().getCargo());
            usuarioLogin.get().setToken(generateToken(usuarioLogin.get().getUsuario()));
            usuarioLogin.get().setSenha("");

            return usuarioLogin;
        }
        return Optional.empty();
    }

    private String generateOtp() {
        return String.format("%06d", (int)(Math.random() * 1000000)); // OTP de 6 dígitos
    }

    private String generateToken(String usuario) {
        return "Bearer " + jwtService.generateToken(usuario);
    }
}
