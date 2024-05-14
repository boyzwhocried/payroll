package com.lawencon.payroll.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "t_r_schedule")
public class Schedule extends BaseModel {

    @ManyToOne
    @Column(name = "client_assignment_id", nullable = false)
    private ClientAssignment clientAssignmentId;

    @Column(name = "payroll_date", nullable = false)
    private LocalDateTime payrollDate;

    @Column(name = "schedule_request_type_id", nullable = false)
    private ScheduleRequestType scheduleRequestTypeId;
}
