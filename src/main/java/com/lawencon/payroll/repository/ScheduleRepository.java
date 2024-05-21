package com.lawencon.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String>
{
  List<Schedule> findByClientAssignmentId(String clientAssignmentId);

  @Query(value = "SELECT sch FROM Schedule sch "
                + "WHERE sch.clientAssignment.id = :clientAssignmentId ")
  Schedule findLatestSchedule(@Param("clientAssignmentId") String clientAssignmentId);
}