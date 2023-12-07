package com.alpha.TaskOrbit.controllers;

import com.alpha.TaskOrbit.dto.JwtRequest;
import com.alpha.TaskOrbit.dto.JwtResponse;
import com.alpha.TaskOrbit.dto.SignUpDTO;
import com.alpha.TaskOrbit.repositories.RepoUser;
import com.alpha.TaskOrbit.security.JwtHelper;
import com.alpha.TaskOrbit.services.ICustomUserDetailService;
import com.alpha.TaskOrbit.services.IAuthService;
import com.alpha.TaskOrbit.services.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private ICustomUserDetailService iCustomUserDetailService;
    @Autowired
    private IAuthService iAuthService;
    @Autowired
    private RepoUser repoUser;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private JwtHelper helper;
    @Autowired
    private AuthService authService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {
        this.doAuthenticate(request.getEmail(), request.getPassword());
        UserDetails userDetails = iCustomUserDetailService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);
        Long userId = iCustomUserDetailService.loadUserIdByUsername(request.getEmail());
        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).userId(userId).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<String> userSignUp(@RequestBody SignUpDTO signUpDTO) {
        return iAuthService.signUp(signUpDTO);
    }

    @GetMapping("/verify/{token}") // Confirm it with Bhaiya
    public ResponseEntity<String> verifyUser(@PathVariable String token)
    {
        System.out.println("Inside Verification Controller");
        return iAuthService.verifyUser(token);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }
    }
    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}