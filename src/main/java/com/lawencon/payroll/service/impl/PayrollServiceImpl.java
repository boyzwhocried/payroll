package com.lawencon.payroll.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.payroll.dto.payroll.PayrollResDto;
import com.lawencon.payroll.repository.ClientAssignmentRepository;
import com.lawencon.payroll.service.ClientAssignmentService;
import com.lawencon.payroll.service.PayrollService;
import com.lawencon.payroll.service.PrincipalService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PayrollServiceImpl implements PayrollService {
  
  private final PrincipalService principalService;
  private final ClientAssignmentRepository clientAssignmentRepository;

  @Override
  public List<PayrollResDto> getClientPayrolls() {
      final var psId = principalService.getUserId();
      final var payrollRes = new ArrayList<PayrollResDto>();
      final var clientAssignments = clientAssignmentRepository.getByPsId(psId);

      clientAssignments.forEach(clientAssignment -> {
          final var payroll = new PayrollResDto();
          
          final var clientId = clientAssignment.getClientId().getId();
          // final var clientName = ;
          // final var payrollDate = ;
          // final var scheduleStatus = ;

          payroll.setId(clientId);
          payroll.setClientName(null);
          payroll.setPayrollDate(null);
          payroll.setScheduleStatus(null);

          payrollRes.add(payroll);
      });

      return payrollRes;
  }
}
