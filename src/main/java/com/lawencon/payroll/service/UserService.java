package com.lawencon.payroll.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.lawencon.payroll.dto.user.LoginReqDto;
import com.lawencon.payroll.dto.user.LoginResDto;

public interface UserService extends UserDetailsService {
    LoginResDto loginUser(LoginReqDto data);
}
