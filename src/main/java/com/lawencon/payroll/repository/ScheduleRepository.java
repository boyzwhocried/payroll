package com.lawencon.payroll.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String>
{
}