package com.lawencon.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String>
{
  List<Schedule> findByClientAssignmentId(String clientAssignmentId);
}