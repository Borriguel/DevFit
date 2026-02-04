package dev.borriguel.devfit.security;

import dev.borriguel.devfit.model.User;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {
    @Getter
    private final long id;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(User user) {
        List<GrantedAuthority> grantedAuthorities = List.of(new SimpleGrantedAuthority(user.getRole().name()));
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
