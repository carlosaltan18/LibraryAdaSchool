package org.ada.biblioteca.controller;

import lombok.RequiredArgsConstructor;
import org.ada.biblioteca.bo.User;
import org.ada.biblioteca.bo.postgres.UserPostgres;
import org.ada.biblioteca.dto.user.UserRequest;
import org.ada.biblioteca.dto.user.UserRequestLogin;
import org.ada.biblioteca.dto.user.UserResponseLogin;
import org.ada.biblioteca.service.auth.AuthenticationService;
import org.ada.biblioteca.service.auth.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserRequest registerUserDto) {
        User registeredUser = authenticationService.signup(registerUserDto);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponseLogin> authenticate(@RequestBody UserRequestLogin loginUserDto) {
        User authenticatedUser = authenticationService.login(loginUserDto);
        List<String> roles = authenticationService.getRolesName(authenticatedUser);
        String jwtToken = jwtService.generateToken(authenticatedUser, roles);
        UserResponseLogin loginResponse = new UserResponseLogin();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());
        return new ResponseEntity<>(loginResponse, HttpStatus.OK);
    }
}
