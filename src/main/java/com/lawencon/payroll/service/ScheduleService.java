package com.lawencon.payroll.service;

import java.util.List;

import com.lawencon.payroll.dto.schedule.ScheduleResDto;
import com.lawencon.payroll.model.Schedule;

public interface ScheduleService {
    List<ScheduleResDto> getByClientAssignmentId(String clientId);

    Schedule addNewSchedule(String clientAssignmentId, String scheduleRequestTypeId);
}
