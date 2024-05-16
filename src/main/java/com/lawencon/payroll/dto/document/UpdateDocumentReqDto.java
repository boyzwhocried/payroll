package com.lawencon.payroll.dto.document;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateDocumentReqDto {
  private String documentId;
  private String documentDeadline;
}
