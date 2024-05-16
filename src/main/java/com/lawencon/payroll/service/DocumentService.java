package com.lawencon.payroll.service;

import java.util.List;

import com.lawencon.payroll.dto.document.DocumentReqDto;
import com.lawencon.payroll.dto.generalResponse.InsertResDto;

public interface DocumentService {
    InsertResDto createDocuments(List<DocumentReqDto> data);
}
