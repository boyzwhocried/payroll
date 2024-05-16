package com.lawencon.payroll.service;

import com.lawencon.payroll.model.File;

public interface FileService {
    File saveFile(File file);

    File loadById(String id);

    File updateFile(File file);
}
