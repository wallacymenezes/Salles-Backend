package com.projects.sallesregister.dto;

import java.util.HashSet;
import java.util.Set;

import com.projects.sallesregister.entities.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {
	
	private Long id;

	@NotBlank(message = "The atribute name is mandatory")
	@Size(min = 3, max = 255, message = "The name must have at least 3 characters to 255 characters")
	private String name;

	@Size(max = 1000, message = "The photo atribute has a limit of 1000 characters")
	private String photoUrl;
	
	@Email(message = "Favor entrar com email válido")
	private String email;
	
	Set<RoleDTO> roles = new HashSet<>();
	
	public UserDTO() {		
	}

	public UserDTO(Long id, String name, String photoUrl, String email) {
		
		this.id = id;
		this.name = name;
		this.photoUrl = photoUrl;
		this.email = email;
		
	}
	
	public UserDTO(User entity) {
		id = entity.getId();
		name = entity.getName();
		photoUrl = entity.getPhotoUrl();
		email = entity.getEmail();
		entity.getRoles().forEach(role -> this.roles.add(new RoleDTO(role)));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}
	
}
