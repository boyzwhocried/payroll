package com.lawencon.payroll.dto.payroll;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PayrollResDto {
  private String id;
  private String clientName;
  private String scheduleStatus;
  private Integer payrollDate;
}