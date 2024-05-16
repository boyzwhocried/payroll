package com.lawencon.payroll.service;

import java.util.Base64;

import com.lawencon.payroll.dto.file.FileDownloadReqDto;
import com.lawencon.payroll.dto.file.FileReqDto;
import com.lawencon.payroll.dto.generalResponse.InsertResDto;
import com.lawencon.payroll.model.File;

public interface FileService {
    File saveFile(File file);

    File loadById(String id);

    File updateFile(File file);

    InsertResDto uploadFile(FileReqDto data);

    void downloadFile(FileDownloadReqDto data);
}
