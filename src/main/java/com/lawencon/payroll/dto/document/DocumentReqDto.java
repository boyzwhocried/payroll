package com.lawencon.payroll.dto.document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DocumentReqDto {
    private String documentTypeId;
    private String activity;
    private String documentDeadline;
    private String scheduleId;
}
