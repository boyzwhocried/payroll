package com.lawencon.payroll.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateUserReqDto {
    private String id;
    private String userName;
    private String email;
    private String phoneNumber;
    private String roleId;
    private String fileContent;
    private String fileExtension;
}
