package com.lawencon.payroll.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "t_m_role")
public class Role extends BaseModel {
    @Column(name = "role_code", unique = true, nullable = false)
	private String roleCode;
	
	@Column(name = "role_name", unique = true, nullable = false)
	private String roleName;
}
