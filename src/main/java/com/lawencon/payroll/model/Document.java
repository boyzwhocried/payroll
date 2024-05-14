package com.lawencon.payroll.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "t_r_document")
public class Document {

    @ManyToOne
    private File fileId;
    private DocumentType documentTypeId;
    private Boolean isSignedBySender;
    private Boolean isSignedByReceiver;
}
