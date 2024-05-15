package com.lawencon.payroll.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.lawencon.payroll.dto.generalResponse.InsertResDto;
import com.lawencon.payroll.dto.user.LoginReqDto;
import com.lawencon.payroll.dto.user.LoginResDto;
import com.lawencon.payroll.dto.user.UserReqDto;

public interface UserService extends UserDetailsService {
    LoginResDto loginUser(LoginReqDto data);

    InsertResDto createUser(UserReqDto data);
}
