package com.mohsintech.mohsin.pokemon_review_app.controller;

import com.mohsintech.mohsin.pokemon_review_app.dto.AuthResponseDto;
import com.mohsintech.mohsin.pokemon_review_app.dto.LoginDto;
import com.mohsintech.mohsin.pokemon_review_app.dto.RegisterDto;
import com.mohsintech.mohsin.pokemon_review_app.models.Role;
import com.mohsintech.mohsin.pokemon_review_app.models.UserEntity;
import com.mohsintech.mohsin.pokemon_review_app.repository.RoleRepository;
import com.mohsintech.mohsin.pokemon_review_app.repository.UserRepository;
import com.mohsintech.mohsin.pokemon_review_app.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtGenerator;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, PasswordEncoder passwordEncoder, JWTService jwtGenerator) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtGenerator = jwtGenerator;
    }

    @PostMapping("login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));

        String token = jwtGenerator.generateToken(authentication);
        AuthResponseDto authResponseDto = new AuthResponseDto(token);
        return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
    }


    @PostMapping("register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            return new ResponseEntity<>("User Already Exists", HttpStatus.BAD_REQUEST);
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Role role = roleRepository.findByName("USER").get();

        user.setRole(Collections.singletonList(role));

        userRepository.save(user);

        return new ResponseEntity<>("User Registered Successfully!", HttpStatus.OK);

    }

}
