package com.lawencon.payroll.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.ScheduleRequestType;


@Repository
public interface ScheduleRequestTypeRepository extends JpaRepository<ScheduleRequestType, String>{
  @NonNull
  List<ScheduleRequestType> findAll();
}