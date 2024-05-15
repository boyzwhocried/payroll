package com.lawencon.payroll.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.lawencon.payroll.model.Schedule;
import com.lawencon.payroll.repository.ScheduleRepository;
import com.lawencon.payroll.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Override
    public Schedule loadById(String id) {
        final Optional<Schedule> schedule = scheduleRepository.findById(id);
        return schedule.get();
    }

}
