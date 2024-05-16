package com.lawencon.payroll.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "t_m_notification_template")
public class NotificationTemplate {
  @Column(name = "notification_content", nullable = false)
  private String notificationContent;
}
