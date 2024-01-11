package com.myblog7.controller;

import com.myblog7.payload.LoginDto;
import com.myblog7.entity.Role;
import com.myblog7.entity.User;
import com.myblog7.payload.JWTAuthResponse;
import com.myblog7.payload.SignupDto;
import com.myblog7.repository.RolesRepository;
import com.myblog7.repository.UserRepository;
import com.myblog7.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?>authenticateUser(@RequestBody LoginDto loginDto){
        System.out.println("\n\n dsfsd ");
         Authentication authentication=authenticationManager.authenticate(
                 new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                         loginDto.getPassword())
         );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //get token from tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return  ResponseEntity.ok(new JWTAuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<?>registerUser(@RequestBody SignupDto signupDto){
        Boolean emailExists=userRepository.existsByEmail(signupDto.getEmail());
        if(emailExists){
            return new ResponseEntity<>("Email Already exists ",HttpStatus.BAD_REQUEST);
        }
        Boolean usernameExists=userRepository.existsByUsername(signupDto.getUsername());
        if(usernameExists){
            return new ResponseEntity<>("Username Already exists ",HttpStatus.BAD_REQUEST);
        }
        User user=new User();
        user.setName(signupDto.getName());
        user.setUsername(signupDto.getUsername());
        user.setEmail(signupDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        Role roles=rolesRepository.findByName("ROLE_ADMIN").get();
        user.setRoles(Collections.singleton(roles));
        User save = userRepository.save(user);

        return new ResponseEntity<>("user is saved Successfully ", HttpStatus.CREATED);
    }

}
