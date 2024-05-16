package com.lawencon.payroll.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserReqDto {
    private String fullName;
    private String email;
    private String phoneNumber;
    private String roleId;
    private String fileContent;
    private String fileExtension;
}
