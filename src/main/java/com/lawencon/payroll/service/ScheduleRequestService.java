package com.lawencon.payroll.service;

import java.util.List;

import com.lawencon.payroll.dto.scheduleRequestService.ScheduleRequestTypeResDto;

public interface ScheduleRequestService {
  List<ScheduleRequestTypeResDto> getAll();
  ScheduleRequestTypeResDto getById(String id);
}
