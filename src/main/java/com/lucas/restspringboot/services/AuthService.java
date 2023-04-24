package com.lucas.restspringboot.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lucas.restspringboot.data.dto.v1.security.AccountCreationDTO;
import com.lucas.restspringboot.data.dto.v1.security.AccountCredentialDTO;
import com.lucas.restspringboot.data.dto.v1.security.AccountTokenDTO;
import com.lucas.restspringboot.exceptions.BadRequestBusinessException;
import com.lucas.restspringboot.exceptions.NotFoundBusinessException;
import com.lucas.restspringboot.models.Permission;
import com.lucas.restspringboot.models.User;
import com.lucas.restspringboot.repositories.PermissionRepository;
import com.lucas.restspringboot.repositories.UserRepository;
import com.lucas.restspringboot.security.jwt.JwtTokenProvider;

@Service
public class AuthService {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PermissionRepository permissionRepository;

    public ResponseEntity signin(AccountCredentialDTO data) {
        try {
            var username = data.getUsername();
            var password = data.getPassword();
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password));
            var user = userRepository.findByUsername(username);
            var tokenResponse = new AccountTokenDTO();
            if (user != null) {
                tokenResponse = tokenProvider.createAccessToken(username, user.getRoles());
            } else {
                throw new UsernameNotFoundException("Username " + username + " not found!");
            }
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            throw new BadRequestBusinessException("Invalid username/password supplied!");
        }
    }

    public ResponseEntity signup(AccountCreationDTO data) {
        var username = data.getUsername();
        var fullName = data.getFullName();
        var password = data.getPassword();
        var confirmPassword = data.getConfirmPassword();

        if (!password.equals(confirmPassword)) {
            throw new BadRequestBusinessException("Password and confirm password doesn't match!");
        }

        var hasUser = userRepository.findByUsername(username);
        if (hasUser == null) {
            var encodedPassword = passwordEncoder.encode(password);
            var permission = permissionRepository.findById(1L);
            var listPermission = new ArrayList<Permission>();
            listPermission.add(permission.get());

            User user = new User();
            user.setUserName(username);
            user.setFullName(fullName);
            user.setPassword(encodedPassword);
            user.setPermissions(listPermission);

            userRepository.save(user);
        } else {
            throw new BadRequestBusinessException("User with username: " + username + " already existe!");
        }
        return ResponseEntity.ok("User created successfully!");
    }

    public ResponseEntity refreshToken(String username, String refreshToken) {
        var user = userRepository.findByUsername(username);
        var tokenResponse = new AccountTokenDTO();

        if (user != null) {
            tokenResponse = tokenProvider.refreshToken(refreshToken);
        } else {
            throw new NotFoundBusinessException("User with username: " + username + " not found!");
        }
        return ResponseEntity.ok(tokenResponse);
    }
}
