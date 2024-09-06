package com.projects.sallesregister.repository;

import com.projects.sallesregister.model.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Long> {

    @Query("SELECT v FROM Venda v WHERE " +
            "(LOWER(v.nomeCliente) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(v.cpf) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(v.telefone) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(v.vendedor) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(v.cnpj) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(v.tipoCliente) LIKE LOWER(CONCAT('%', :termo, '%')) " +
            "OR LOWER(v.statusVenda) LIKE LOWER(CONCAT('%', :termo, '%')))")
    public List<Venda> findByTermo(@Param("termo") String termo);
}
