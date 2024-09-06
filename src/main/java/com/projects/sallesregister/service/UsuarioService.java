package com.projects.sallesregister.service;


import java.util.Objects;
import java.util.Optional;

import com.projects.sallesregister.model.Usuario;
import com.projects.sallesregister.model.UsuarioLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.projects.sallesregister.repository.UsuarioRepository;
import com.projects.sallesregister.security.JwtService;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<Usuario> cadastrarUsuario(Usuario usuario) {

        if(usuarioRepository.findByUsuario(usuario.getUsuario()).isPresent())
            return Optional.empty();

        usuario.setSenha(encryptPassword(usuario.getSenha()));

        return Optional.of(usuarioRepository.save(usuario));
    }

    public Optional<Usuario> atualizarUsuario(Usuario usuario) {

        if (usuarioRepository.findById(usuario.getId()).isPresent()) {

            Optional<Usuario> searchUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

            if((searchUsuario.isPresent()) && (!Objects.equals(searchUsuario.get().getId(), usuario.getId())))
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario não existe!", null);

            usuario.setSenha(encryptPassword(usuario.getSenha()));

            return Optional.ofNullable(usuarioRepository.save(usuario));
        }

        return Optional.empty();
    }

    public Optional<UsuarioLogin> authenticateUsuarios(Optional<UsuarioLogin> usuarioLogin){

        var credentials = new UsernamePasswordAuthenticationToken(usuarioLogin.get().getUsuario(), usuarioLogin.get().getSenha());

        Authentication authentication = authenticationManager.authenticate(credentials);

        if (authentication.isAuthenticated()) {

            Optional<Usuario> usuario = usuarioRepository.findByUsuario(usuarioLogin.get().getUsuario());

            if(usuario.isPresent()) {

                usuarioLogin.get().setId(usuario.get().getId());
                usuarioLogin.get().setNome(usuario.get().getNome());
                usuarioLogin.get().setFoto(usuario.get().getFoto());
                usuarioLogin.get().setCargo(usuario.get().getCargo());
                usuarioLogin.get().setToken(generateToken(usuarioLogin.get().getUsuario()));
                usuarioLogin.get().setSenha("");

                return usuarioLogin;
            }
        }

        return Optional.empty();
    }

    private String encryptPassword(String password) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        return encoder.encode(password);
    }

    private String generateToken(String usuario) {
        return "Bearer " + jwtService.generateToken(usuario);
    }
}
