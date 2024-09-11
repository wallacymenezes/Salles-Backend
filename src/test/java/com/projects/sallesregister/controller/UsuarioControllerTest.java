package com.projects.sallesregister.controller;

import com.projects.sallesregister.model.Cargo;
import com.projects.sallesregister.model.Usuario;
import com.projects.sallesregister.repository.UsuarioRepository;
import com.projects.sallesregister.service.UsuarioService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UsuarioControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @BeforeAll
    void start() {

        usuarioRepository.deleteAll();
        Cargo cargo = new Cargo("ADMINISTRADOR");
        usuarioService.cadastrarUsuario(new Usuario(0L, "Root", "root@root.com", "rootroot", "sem_foto", cargo));
    }

    @Test
    @DisplayName("Cadastrar Um Usuario")
    public void deveCriarUmUsuario(){

        Cargo cargo = new Cargo("VENDEDOR");
        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L,
                "Lucas Carlos",
                "lucas@gmail.com",
                "12345678",
                "foto",
                cargo));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Não deve permitir duplicação do Usuário")
    public void naoDeveDuplicarUsuario() {

        Cargo cargo = new Cargo("VENDEDOR");
        Usuario usuario = new Usuario(0L,
                "Lucas Carlos",
                "lucas@gmail.com",
                "12345678",
                "foto",
                cargo);

        usuarioService.cadastrarUsuario(usuario);

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L,
                "Lucas Carlos",
                "lucas@gmail.com",
                "12345678",
                "foto",
                cargo));

        ResponseEntity<Usuario> corpoResposta = testRestTemplate.exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Atualizar um Usuario")
    public void deveAtualizarUmUsuario() {

        Cargo cargo = new Cargo("VENDEDOR");
        Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L,
                "Lucas Carlos",
                "lucas@gmail.com",
                "12345678",
                "foto",
                cargo));

        Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(),
                "Juliana Andrews Ramos",
                "lucas@gmail.com",
                "12345678",
                "foto",
                cargo);

        HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

        ResponseEntity<Usuario> corpoResposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios", HttpMethod.PUT, corpoRequisicao, Usuario.class);

        assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
    }

    @Test
    @DisplayName("Listar todos os Usuarios")
    public void deveMostrarTodosUsuarios() {

        Cargo cargo = new Cargo("VENDEDOR");
        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Lucas Carlos",
                "lucas@gmail.com",
                "12345678",
                "foto",
                cargo));

        usuarioService.cadastrarUsuario(new Usuario(0L,
                "Juliana Andrews Ramos",
                "juliana@gmail.com",
                "12345678",
                "foto",
                cargo));

        ResponseEntity<String> resposta = testRestTemplate
                .withBasicAuth("root@root.com", "rootroot")
                .exchange("/usuarios/all", HttpMethod.GET, null, String.class);

        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }
}
