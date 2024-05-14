package com.lawencon.payroll.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginResDto {
    private Long userId;
    private String userName;
    private String roleCode;
    private Long fileId;
    private String token;
}
