package com.lawencon.payroll.repository;

import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.Document;

@Repository
public interface DocumentRepository extends BaseRepository<Document, String> {

}
