package com.projects.sallesregister.repository;

import com.projects.sallesregister.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    public List<Usuario> findAllByNomeContainingIgnoreCase(@Param("name") String name);

    public Optional<Usuario> findByUsuario(String usuario);
}
