package com.lawencon.payroll.dto.document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DocumentReqDto {
    private String fileId;
    private String documentTypeId;
    private String activity;
    private String scheduleId;
    private Boolean isSignedBySender;
    private Boolean isSignedByReceiver;
}
