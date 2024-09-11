package com.projects.sallesregister.controller;

import com.projects.sallesregister.model.Cargo;
import com.projects.sallesregister.model.Usuario;
import com.projects.sallesregister.model.UsuarioLogin;
import com.projects.sallesregister.repository.UsuarioRepository;
import com.projects.sallesregister.service.AuthService;
import com.projects.sallesregister.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private AuthController authController;

    @Autowired
    private AuthService authService;

    @BeforeAll
    void start() {

        usuarioRepository.deleteAll();
        Cargo cargo = new Cargo("ADMINISTRADOR");
        usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", "sem_foto", cargo));
    }

    @Test
    @DisplayName("Gerar token de recuperação de senha")
    public void deveGerarTokenDeRecuperacao() {
        // Simular requisição para gerar o token de recuperação de senha
        HttpEntity<String> corpoRequisicao = new HttpEntity<>("root@root.com");

        ResponseEntity<Void> corpoResposta = testRestTemplate.exchange("/auth/recover-password",
                HttpMethod.POST, corpoRequisicao, Void.class);

        assertEquals(HttpStatus.NO_CONTENT, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Gerar token para e-mail não encontrado")
    public void naoDeveGerarTokenSeEmailNaoEncontrado() {
        // Simular um e-mail inexistente e verificar se gera erro 404
        String emailInexistente = "naoexiste@gmail.com";
        HttpEntity<String> corpoRequisicao = new HttpEntity<>(emailInexistente);

        ResponseEntity<Void> corpoResposta = testRestTemplate.exchange("/auth/recover-password",
                HttpMethod.POST, corpoRequisicao, Void.class);

        assertEquals(HttpStatus.NOT_FOUND, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Validar OTP e gerar token JWT")
    public void deveValidarOtpEGerarToken() {
        // Dado um OTP válido, deve validar e gerar token JWT
        String otp = "123456";
        HttpEntity<String> corpoRequisicao = new HttpEntity<>("");

        ResponseEntity<UsuarioLogin> corpoResposta = testRestTemplate
                .withBasicAuth("root@root.com", otp)
                .exchange("/auth/validate-otp", HttpMethod.POST, corpoRequisicao, UsuarioLogin.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Validar OTP inválido ou expirado")
    public void naoDeveValidarOtpInvalido() {
        // Simular OTP inválido
        String otpInvalido = "000000";
        HttpEntity<String> corpoRequisicao = new HttpEntity<>("");

        ResponseEntity<Void> corpoResposta = testRestTemplate
                .withBasicAuth("root@root.com", otpInvalido)
                .exchange("/auth/validate-otp", HttpMethod.POST, corpoRequisicao, Void.class);

        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
    }
}
