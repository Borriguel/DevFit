package dev.borriguel.devfit.controller;

import dev.borriguel.devfit.model.dtos.AuthenticationDto;
import dev.borriguel.devfit.model.dtos.LoginResponseDto;
import dev.borriguel.devfit.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody @Valid AuthenticationDto data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken(auth);
        return new LoginResponseDto(token);
    }
}

