package com.lawencon.payroll.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResDto {
    private String id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String roleName;
}
