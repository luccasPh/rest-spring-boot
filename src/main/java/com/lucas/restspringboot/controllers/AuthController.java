package com.lucas.restspringboot.controllers;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lucas.restspringboot.data.dto.v1.security.AccountCreationDTO;
import com.lucas.restspringboot.data.dto.v1.security.AccountCredentialDTO;
import com.lucas.restspringboot.services.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication Endpoint")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Operation(summary = "Authenticates a user and returns a toke")
    @PostMapping(value = "/signin")
    public ResponseEntity signin(@RequestBody AccountCredentialDTO data) {
        if (checkIfParamsIsNotNull(data))
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid account credentials!");

        var token = authService.signin(data);
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid account credentials!");

        return token;
    }

    @Operation(summary = "Create a new user with permissions")
    @PostMapping(value = "/signup")
    public ResponseEntity signup(@RequestBody AccountCreationDTO data) {
        if (NullChecker.anyNull(data) || NullChecker.anyBlack(data))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid parameters!");

        return authService.signup(data);
    }

    @Operation(summary = "Refresh token for authenticated user")
    @PostMapping(value = "/refresh/{username}")
    public ResponseEntity refreshToken(@PathVariable("username") String username,
            @RequestHeader("Authorization") String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank() || username == null || username.isBlank()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        }

        var token = authService.refreshToken(username, refreshToken);
        if (token == null)
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid client request!");
        return token;
    }

    private boolean checkIfParamsIsNotNull(AccountCredentialDTO data) {
        return data == null || data.getUsername() == null || data.getUsername().isBlank() || data.getPassword() == null
                || data.getPassword().isBlank();
    }

    private class NullChecker {
        private static boolean anyNull(Object target) {
            return Arrays.stream(target.getClass()
                    .getDeclaredFields())
                    .peek(f -> f.setAccessible(true))
                    .map(f -> getFieldValue(f, target))
                    .anyMatch(Objects::isNull);
        }

        private static boolean anyBlack(Object target) {
            return Arrays.stream(target.getClass()
                    .getDeclaredFields())
                    .peek(f -> f.setAccessible(true))
                    .map(f -> getFieldValue(f, target))
                    .anyMatch(f -> f.toString().isBlank());
        }

        private static Object getFieldValue(java.lang.reflect.Field f, Object target) {
            try {
                return f.get(target);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
