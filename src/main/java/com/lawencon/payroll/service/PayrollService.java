package com.lawencon.payroll.service;

import java.util.List;

import com.lawencon.payroll.dto.payroll.PayrollResDto;

public interface PayrollService {
  List<PayrollResDto> getClientPayrolls();
}
