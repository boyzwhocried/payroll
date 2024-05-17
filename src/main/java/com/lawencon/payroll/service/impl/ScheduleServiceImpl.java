package com.lawencon.payroll.service.impl;

import org.springframework.stereotype.Service;

import com.lawencon.payroll.model.Schedule;
import com.lawencon.payroll.repository.ClientAssignmentRepository;
import com.lawencon.payroll.repository.ScheduleRepository;
import com.lawencon.payroll.repository.ScheduleRequestTypeRepository;
import com.lawencon.payroll.service.PrincipalService;
import com.lawencon.payroll.service.ScheduleService;
import com.lawencon.payroll.util.FtpUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ClientAssignmentRepository clientAssignmentRepository;
    private final ScheduleRequestTypeRepository scheduleRequestTypeRepository;
    private final PrincipalService principalService;


    @Override
    public Schedule loadById(String id) {
        final var schedule = scheduleRepository.findById(id);
        return schedule.get();
    }

    public Schedule addNewSchedule(String clientAssignmentId, String scheduleRequestTypeId) {
        final var schedule = new Schedule();

        final var clientAssignment = clientAssignmentRepository.findById(clientAssignmentId);
        final var scheduleRequestType = scheduleRequestTypeRepository.findById(clientAssignmentId);

        final var userId = clientAssignment.get().getClientId().getId();

        schedule.setClientAssignmentId(clientAssignment.get());
        schedule.setScheduleRequestTypeId(scheduleRequestType.get());
        schedule.setCreatedBy(principalService.getUserId());

        FtpUtil.createDirectory(userId+"/"+"test1");

        final var savedSchedule = scheduleRepository.save(schedule);

        return savedSchedule;
    };
}
