package com.lawencon.payroll.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.Document;

@Repository
public interface DocumentRepository extends BaseRepository<Document, String> {
    List<Document> getByScheduleId(String id);
}
