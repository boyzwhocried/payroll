package com.lawencon.payroll.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.lawencon.payroll.model.Schedule;
import com.lawencon.payroll.repository.ScheduleRepository;
import com.lawencon.payroll.service.DailySchedulerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailySchedulerServiceImpl implements DailySchedulerService {
  
  private final ScheduleRepository scheduleRepository;

  // @Scheduled(fixedRate = 2000)
  @Override
  public void addMonthlyScheduleJob() {
    // final var schedule = new Schedule();
    // schedule.setClientAssignmentId(null);
    // schedule.setScheduleRequestTypeId(null);
  
    // scheduleRepository.save(schedule);
    // System.out.println("Masok!");
  }
}