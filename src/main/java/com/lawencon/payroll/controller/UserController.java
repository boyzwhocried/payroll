package com.lawencon.payroll.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.payroll.dto.generalResponse.InsertResDto;
import com.lawencon.payroll.dto.user.LoginReqDto;
import com.lawencon.payroll.dto.user.LoginResDto;
import com.lawencon.payroll.dto.user.UserReqDto;
import com.lawencon.payroll.dto.user.UserResDto;
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
        final var email = data.getEmail();
        final var password = data.getPassword();

        final var auth = new UsernamePasswordAuthenticationToken(email, password);

        authenticationManager.authenticate(auth);

        final var loginRes = userService.loginUser(data);
        return new ResponseEntity<>(loginRes, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<InsertResDto> createUser(@RequestBody UserReqDto data) {
        final var insertRes = userService.createUser(data);
        return new ResponseEntity<>(insertRes, HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<UserResDto>> getAllUsers() {
        final var usersRes = userService.getAllUsers();
        return new ResponseEntity<>(usersRes, HttpStatus.OK);
    }

    @GetMapping("role/{code}")
    public ResponseEntity<List<UserResDto>> getPayrollServiceUsers(@PathVariable String code) {
        final var usersRes = userService.getAllUsersByCode(code);
        return new ResponseEntity<>(usersRes, HttpStatus.OK);
    }

    @GetMapping("assigned/{id}")
    public ResponseEntity<List<UserResDto>> getAssignedPayrollService(@PathVariable String id) {
        final var usersRes = userService.getAllUsersByPsId(id);
        return new ResponseEntity<>(usersRes, HttpStatus.OK);
    }

    @GetMapping("not-assigned/{id}")
    public ResponseEntity<List<UserResDto>> getNotAssignedPayrollService(@PathVariable String id) {
        final var usersRes = userService.getAllUsersByPsIdExcept(id);
        return new ResponseEntity<>(usersRes, HttpStatus.OK);
    }
}
