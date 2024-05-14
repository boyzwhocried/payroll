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
@Table(name = "t_r_activity")
public class Activity extends BaseModel {

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @Column(name = "schedule_id", nullable = false)
    private Schedule scheduleId;

    @ManyToOne
    @Column(name = "document_id", nullable = false)
    private Document documentId;
}
