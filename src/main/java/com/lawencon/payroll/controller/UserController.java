package com.lawencon.payroll.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.payroll.dto.user.LoginReqDto;
import com.lawencon.payroll.dto.user.LoginResDto;
import com.lawencon.payroll.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @PostMapping("login")
    public ResponseEntity<LoginResDto> login(@RequestBody LoginReqDto data) {
        final String email = data.getEmail();
        final String password = data.getPassword();

        final Authentication auth = new UsernamePasswordAuthenticationToken(email, password);

        authenticationManager.authenticate(auth);

        final LoginResDto loginRes = userService.loginUser(data);
        return new ResponseEntity<>(loginRes, HttpStatus.OK);
    }
}
