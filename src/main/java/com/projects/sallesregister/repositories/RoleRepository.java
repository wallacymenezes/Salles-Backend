package com.projects.sallesregister.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.sallesregister.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByAuthority(String authority);
}
