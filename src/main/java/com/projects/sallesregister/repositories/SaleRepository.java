package com.projects.sallesregister.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.projects.sallesregister.entities.Sale;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

	@Query("SELECT v FROM Sale v " +
		   "JOIN v.user u " + // Faz o join com a entidade User
		   "WHERE " +
		   "(LOWER(v.clientName) LIKE LOWER(CONCAT('%', :termo, '%')) " +
		   "OR LOWER(v.cpf) LIKE LOWER(CONCAT('%', :termo, '%')) " +
		   "OR LOWER(v.whatsapp) LIKE LOWER(CONCAT('%', :termo, '%')) " +
		   "OR LOWER(v.phone) LIKE LOWER(CONCAT('%', :termo, '%')) " +
		   "OR LOWER(v.cnpj) LIKE LOWER(CONCAT('%', :termo, '%')) " +
		   "OR LOWER(v.clientType) LIKE LOWER(CONCAT('%', :termo, '%')) " +
		   "OR LOWER(v.saleStatus) LIKE LOWER(CONCAT('%', :termo, '%')) " +
		   "OR LOWER(u.name) LIKE LOWER(CONCAT('%', :termo, '%')))")
	public List<Sale> findByTermo(@Param("termo") String termo);
}
