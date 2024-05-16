package com.lawencon.payroll.service.impl;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.payroll.dto.clientAssignment.ClientAssignmentReqDto;
import com.lawencon.payroll.dto.clientAssignment.ClientAssignmentResDto;
import com.lawencon.payroll.dto.generalResponse.InsertResDto;
import com.lawencon.payroll.model.ClientAssignment;
import com.lawencon.payroll.repository.ClientAssignmentRepository;
import com.lawencon.payroll.repository.UserRepository;
import com.lawencon.payroll.service.ClientAssignmentService;
import com.lawencon.payroll.service.PrincipalService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientAssignmentServiceImpl implements ClientAssignmentService  {

  private final ClientAssignmentRepository clientAssignmentRepository;
  private final UserRepository userRepository;
  private final PrincipalService principalService;

  @Override
  public ClientAssignmentResDto getById(String id) {
    final Optional<ClientAssignment> clientAssignment = clientAssignmentRepository.findById(id);

    final var clientAssignmentResDto = new ClientAssignmentResDto();

    final var payrollServiceId = clientAssignment.get().getPayrollService().getId();
    final var clientId = clientAssignment.get().getClient().getId();

    clientAssignmentResDto.setId(id);
    clientAssignmentResDto.setClientId(clientId);
    clientAssignmentResDto.setPsId(payrollServiceId);
    
    return clientAssignmentResDto;
  }

  @Override
  public InsertResDto saveClientAssignment(ClientAssignmentReqDto clientAssignmentReq) {
    final var insertRes = new InsertResDto();

    final var clientAssignment = new ClientAssignment();

    final var clientId = clientAssignmentReq.getClientId();
    final var payrollServiceId = clientAssignmentReq.getPsId(); 

    System.out.println("Client Id = "+clientId);
    System.out.println("Payroll Service Id = "+payrollServiceId);

    final var client = userRepository.findById(clientId);
    final var payrollService = userRepository.findById(payrollServiceId);

    System.out.println("WOW!");
    System.out.println("WOW!");
    System.out.println("WOW!");
    System.out.println("WOW!");
    System.out.println("WOW!");
    System.out.println("WOW!");
    System.out.println("WOW!");
    System.out.println("WOW!");
    System.out.println("WOW!");
    System.out.println("WOW!");

    clientAssignment.setClient(client.get());
    clientAssignment.setPayrollService(payrollService.get());
    clientAssignment.setCreatedBy(principalService.getUserId());

    final var savedClientAssignment = clientAssignmentRepository.save(clientAssignment);

    insertRes.setId(savedClientAssignment.getId());
    insertRes.setMessage("Insert Success");

    return insertRes;
  }
}