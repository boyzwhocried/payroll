package com.lawencon.payroll.service;

import java.util.List;

import com.lawencon.payroll.dto.document.DocumentReqDto;
import com.lawencon.payroll.dto.document.DocumentResDto;
import com.lawencon.payroll.dto.document.UpdateDocumentReqDto;
import com.lawencon.payroll.dto.document.UpdateDocumentScheduleReqDto;
import com.lawencon.payroll.dto.generalResponse.InsertResDto;
import com.lawencon.payroll.dto.generalResponse.UpdateResDto;

public interface DocumentService {
    InsertResDto createDocuments(List<DocumentReqDto> data);

    UpdateResDto rescheduleDocuments(List<UpdateDocumentScheduleReqDto> data);

    UpdateResDto uploadFile(UpdateDocumentReqDto updateDocumentReqDto);

    List<DocumentResDto> getDocumentsByScheduleId(String scheduleId);
}
