package com.lawencon.payroll.repository;

import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.Activity;

@Repository
public interface ActivityRepository extends BaseRepository<Activity, String>
{
}