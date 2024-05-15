package com.lawencon.payroll.service;

import java.util.List;

import com.lawencon.payroll.dto.InsertResDto;
import com.lawencon.payroll.dto.document.DocumentReqDto;

public interface DocumentService {
    InsertResDto createDocument(List<DocumentReqDto> data);
}
