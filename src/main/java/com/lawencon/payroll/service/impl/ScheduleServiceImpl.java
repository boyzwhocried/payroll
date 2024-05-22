package com.lawencon.payroll.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.payroll.dto.schedule.ScheduleResDto;
import com.lawencon.payroll.model.Schedule;
import com.lawencon.payroll.repository.ClientAssignmentRepository;
import com.lawencon.payroll.repository.DocumentRepository;
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
    private final DocumentRepository documentRepository;

    @Override
    public Schedule addNewSchedule(String clientAssignmentId, String scheduleRequestTypeId) {
        final var schedule = new Schedule();

        final var clientAssignment = clientAssignmentRepository.findById(clientAssignmentId);
        final var scheduleRequestType = scheduleRequestTypeRepository.findById(clientAssignmentId);

        final var userId = clientAssignment.get().getClientId().getId();

        schedule.setClientAssignment(clientAssignment.get());
        schedule.setScheduleRequestType(scheduleRequestType.get());
        schedule.setCreatedBy(principalService.getUserId());

        FtpUtil.createDirectory(userId+"/"+"test1");

        final var savedSchedule = scheduleRepository.save(schedule);

        return savedSchedule;
    };

    @Override
public List<ScheduleResDto> getByClientAssignmentId(String clientAssignmentId) {
        final var schedulesRes = new ArrayList<ScheduleResDto>(); 
        final var schedules = scheduleRepository.findByClientAssignmentIdOrderByCreatedAtDesc(clientAssignmentId);

        schedules.forEach(schedule -> {
            final var scheduleRes = new ScheduleResDto();

            final var scheduleId = schedule.getId();
            final var scheduleStatusName = schedule.getScheduleRequestType().getScheduleRequestName();
            final var scheduleStatusCode = schedule.getScheduleRequestType().getScheduleRequestCode();
            final var payrollDate = schedule.getCreatedAt().toString();

            scheduleRes.setScheduleId(scheduleId);
            scheduleRes.setScheduleStatusName(scheduleStatusName);
            scheduleRes.setScheduleStatusCode(scheduleStatusCode);
            scheduleRes.setPayrollDate(payrollDate);
            scheduleRes.setCanBeRescheduled(true);

            final var documents = documentRepository.findByScheduleIdOrderByDocumentDeadlineAsc(scheduleId);

            if(documents.size() == 0) {
                scheduleRes.setCanBeRescheduled(false);
            }else {
                for(var document : documents) {
                    if(document.getDocumentDirectory().isEmpty()) {
                        scheduleRes.setCanBeRescheduled(false);
                        break;
                    }
                }
            }


            schedulesRes.add(scheduleRes);
        });

        return schedulesRes;
    }
}
