package com.projects.sallesregister.projections;

public interface UserDetailsProjection {
	
	String getUsername();
	
	String getPassword();

	Long getRoleId();

	String getAuthority();	

}
