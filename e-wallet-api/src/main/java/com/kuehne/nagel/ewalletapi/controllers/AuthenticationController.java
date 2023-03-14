package com.kuehne.nagel.ewalletapi.controllers;

import com.kuehne.nagel.ewalletapi.models.requests.AuthRequest;
import com.kuehne.nagel.ewalletapi.models.response.ResponseApi;
import com.kuehne.nagel.ewalletapi.security.JwtTokenService;
import com.kuehne.nagel.ewalletapi.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;

    private final JwtTokenService tokenService;

    @PostMapping
    public ResponseApi<String> getToken(@RequestBody AuthRequest request) {

        log.info("#POST token {}", request);
        var authentication = new UsernamePasswordAuthenticationToken(request.login(), request.password());
        authenticationManager.authenticate(authentication);

        UserDetails userDetails = userService.loadUserByUsername(request.login());
        final String token = tokenService.generateToken(userDetails);

        return new ResponseApi<>(token);
    }
}
