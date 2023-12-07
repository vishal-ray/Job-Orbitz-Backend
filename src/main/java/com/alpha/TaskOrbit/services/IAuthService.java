package com.alpha.TaskOrbit.services;

import com.alpha.TaskOrbit.dto.SignUpDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface IAuthService {
    public ResponseEntity<String> signUp(@RequestBody SignUpDTO signUpDTO);
    public ResponseEntity<String> verifyUser(@PathVariable String hashCode);
}