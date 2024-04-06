package com.iase24.springjunit.security.service;

import com.iase24.springjunit.dto.UserDataDTO;
import com.iase24.springjunit.entities.User;
import com.iase24.springjunit.exception.AppError;
import com.iase24.springjunit.security.dto.JwtRequest;
import com.iase24.springjunit.security.dto.JwtResponse;
import com.iase24.springjunit.security.dto.RegistrationUserDTO;
import com.iase24.springjunit.security.utils.JwtTokensUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthServiceController {

    private final AuthService authService;
    private final JwtTokensUtil jwtTokensUtil;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getUsername()
                    , authRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(
                    HttpStatus.UNAUTHORIZED.value()
                    , "invalid username or password")
                    , HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = authService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokensUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDTO registrationUserDTO) {

        if (!registrationUserDTO.getPassword().equals(registrationUserDTO.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(
                    HttpStatus.BAD_REQUEST.value()
                    , "Passwords don't match")
                    , HttpStatus.BAD_REQUEST);
        }
        if (authService.findUserByUsername(registrationUserDTO.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(
                    HttpStatus.BAD_REQUEST.value()
                    , "A user with the same name or email exists")
                    , HttpStatus.BAD_REQUEST);
        }
        User user = authService.registrationNewUser(registrationUserDTO);
        return ResponseEntity.ok(new UserDataDTO(user.getId(), user.getUsername(), user.getEmail()));
    }
}
