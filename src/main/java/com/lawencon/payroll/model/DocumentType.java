package com.lawencon.payroll.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "t_m_document_type")
public class DocumentType extends BaseModel {
    @Column(name = "document_type_code", unique = true, nullable = false)
	private String documentTypeCode;
	
	@Column(name = "document_type_name", unique = true, nullable = false)
	private String documentTypeName;
}
