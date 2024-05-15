package com.lawencon.payroll.service;

import com.lawencon.payroll.model.Schedule;

public interface ScheduleService {
    Schedule loadById(String id);
}
