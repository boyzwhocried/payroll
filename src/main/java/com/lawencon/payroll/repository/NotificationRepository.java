package com.lawencon.payroll.repository;

import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.Notification;

@Repository
public interface NotificationRepository extends BaseRepository<Notification, String> {

}
