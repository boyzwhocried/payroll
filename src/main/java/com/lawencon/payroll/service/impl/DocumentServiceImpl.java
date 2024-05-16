package com.lawencon.payroll.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.payroll.dto.document.DocumentReqDto;
import com.lawencon.payroll.dto.generalResponse.InsertResDto;
import com.lawencon.payroll.model.Document;
import com.lawencon.payroll.repository.DocumentRepository;
import com.lawencon.payroll.repository.DocumentTypeRepository;
import com.lawencon.payroll.service.DocumentService;
import com.lawencon.payroll.service.PrincipalService;
import com.lawencon.payroll.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final PrincipalService principalService;

    private final ScheduleService scheduleService;

    @Override
    public InsertResDto createDocuments(List<DocumentReqDto> data) {
        final var insertRes = new InsertResDto();

        data.forEach(documentReq -> {
            final var documentType = documentTypeRepository.findById(documentReq.getDocumentTypeId());
            final var schedule = scheduleService.loadById(documentReq.getScheduleId());

		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            var document = new Document();
            document.setDocumentType(documentType.get());
            document.setDocumentName(documentReq.getDocumentName());
            document.setDocumentDeadline(LocalDateTime.parse(documentReq.getDocumentDeadline(), formatter));
            document.setActivity(documentReq.getActivity());
            document.setDocumentDirectory(documentReq.getDocumentDirectory());
            document.setSchedule(schedule);
            document.setIsSignedBySender(false);
            document.setIsSignedByReceiver(false);
            document.setCreatedBy(principalService.getUserId());

            document = documentRepository.save(document);
        });
        
        insertRes.setId(null);
        insertRes.setMessage("Document(s) have been made!");
        return insertRes;
    }

}
