package com.projects.sallesregister.repository;

import com.projects.sallesregister.model.RecuperadorSenha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface RecuperadorSenhaRepository extends JpaRepository<RecuperadorSenha, Long> {

    @Query("SELECT r FROM RecuperadorSenha r WHERE r.token = :token AND r.expiration > :now")
    List<RecuperadorSenha> findValidTokens(@Param("token") String token, @Param("now") Instant now);
}
