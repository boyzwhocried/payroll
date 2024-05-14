package com.lawencon.payroll.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, String>
{
  List<Activity> getAll(ActivityRepository activityRepository);
  Optional<Activity> findById(String id);
}