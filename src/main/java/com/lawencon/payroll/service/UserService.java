package com.lawencon.payroll.service;

import java.util.List;

import com.lawencon.payroll.dto.user.UserResDto;

public interface UserService {
  LoginResDto login(LoginReqDto loginReqDto);

  List<UserResDto> getAllUser();

  List<ClientResDto> getAllClient();

  InsertResDto addNewUser();

  UserResDto getUserById();
}
