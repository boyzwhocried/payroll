package com.lawencon.payroll.service.impl;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lawencon.payroll.constant.Roles;
import com.lawencon.payroll.constant.ScheduleRequestTypes;
import com.lawencon.payroll.model.Schedule;
import com.lawencon.payroll.repository.ClientAssignmentRepository;
import com.lawencon.payroll.repository.ScheduleRepository;
import com.lawencon.payroll.repository.ScheduleRequestTypeRepository;
import com.lawencon.payroll.repository.UserRepository;
import com.lawencon.payroll.service.DailySchedulerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailySchedulerServiceImpl implements DailySchedulerService {
  
  private final ScheduleRepository scheduleRepository;
  private final ClientAssignmentRepository clientAssignmentRepository;
  private final UserRepository userRepository;
  private final ScheduleRequestTypeRepository scheduleRequestTypeRepository;


  @Scheduled(fixedRate = 1000 * 60 * 30)
  @Override
  public void addMonthlyScheduleJob() {
    System.out.println("Masok!");
    final var schedule = new Schedule();
    final var clientAssignments = clientAssignmentRepository.findAll();
    final var system = userRepository.findByRoleIdRoleCode(Roles.RL000.name());
    final var scheduleRequestType = scheduleRequestTypeRepository.findByScheduleRequestCode(ScheduleRequestTypes.SQT01.name());

    clientAssignments.forEach(clientAssignment -> {
      final var latestSchedule = scheduleRepository.findFirstByClientAssignmentIdOrderByCreatedAtDesc(clientAssignment.getId());

      if(latestSchedule == null || latestSchedule.getCreatedAt().getMonthValue() < LocalDateTime.now().getMonthValue()) {
        schedule.setCreatedBy(system.getId());
        schedule.setClientAssignment(clientAssignment);
        schedule.setScheduleRequestType(scheduleRequestType);
    
        scheduleRepository.save(schedule);
        System.out.println("Payroll Schedule Created!");
      }
    });
  }
}