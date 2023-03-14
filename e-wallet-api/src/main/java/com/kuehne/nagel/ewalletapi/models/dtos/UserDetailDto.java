package com.kuehne.nagel.ewalletapi.models.dtos;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Data
public class UserDetailDto implements UserDetails {

    /**
     * user id
     */
    private UUID id;

    /**
     * email
     */
    private String login;

    /**
     * password
     */
    private String password;

    /**
     * role
     */
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {

        return password;
    }

    @Override
    public String getUsername() {

        return login;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    @Override
    public boolean isEnabled() {

        return true;
    }
}
