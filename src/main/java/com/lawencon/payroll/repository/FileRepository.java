package com.lawencon.payroll.repository;

import org.springframework.stereotype.Repository;

import com.lawencon.payroll.model.File;

@Repository
public interface FileRepository extends BaseRepository<File, String> {

}
