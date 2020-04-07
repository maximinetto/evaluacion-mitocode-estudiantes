package com.maximinetto.estudiante.security;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class User implements UserDetails{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    
    private String username;
    private String password;
    private boolean enabled;
    private Collection<String> roles;
    
    public User(String username, String password, boolean enabled, Collection<String> roles) {
	super();
	this.username = username;
	this.password = password;
	this.enabled = enabled;
	this.roles = roles;
    }
    
    

    public Collection<String> getRoles() {
        return roles;
    }



    public void setRoles(Collection<String> roles) {
        this.roles = roles;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
	return this.roles
		.stream()
		.map(rol -> new SimpleGrantedAuthority(rol))
		.collect(Collectors.toList());
    }

    @JsonIgnore
    @Override
    public String getPassword() {
	return password;
    }

    @Override
    public String getUsername() {
	return username;
    }

    @Override
    public boolean isAccountNonExpired() {
	return false;
    }

    @Override
    public boolean isAccountNonLocked() {
	return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return false;
    }

    @Override
    public boolean isEnabled() {
	return enabled;
    }

}
