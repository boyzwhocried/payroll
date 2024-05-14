package com.lawencon.payroll.repository;

import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.Schedule;

@Repository
public interface ScheduleRepository extends BaseRepository<Schedule, String>
{
}