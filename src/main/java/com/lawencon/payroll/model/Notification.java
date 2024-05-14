package com.lawencon.payroll.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "t_r_notification")
public class Notification extends BaseModel {

    @Column(nullable = false)
    private String subject;

    @ManyToOne
    @Column(name = "user_id", nullable = false)
    private User userId;

    @Column(nullable = false)
    private String body;
}
