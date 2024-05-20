package com.lawencon.payroll.dto.document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DocumentReqDto {
    private String scheduleId;
    private DocumentsReqDto documentsReqDto;
}
