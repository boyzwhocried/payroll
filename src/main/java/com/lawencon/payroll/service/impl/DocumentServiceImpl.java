package com.lawencon.payroll.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.payroll.dto.document.DocumentReqDto;
import com.lawencon.payroll.dto.generalResponse.InsertResDto;
import com.lawencon.payroll.model.Document;
import com.lawencon.payroll.repository.DocumentRepository;
import com.lawencon.payroll.repository.DocumentTypeRepository;
import com.lawencon.payroll.service.DocumentService;
import com.lawencon.payroll.service.FileService;
import com.lawencon.payroll.service.ScheduleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final DocumentTypeRepository documentTypeRepository;

    private final FileService fileService;
    private final ScheduleService scheduleService;

    @Override
    public InsertResDto createDocument(List<DocumentReqDto> data) {
        final var insertRes = new InsertResDto();

        data.forEach(documentReq -> {
            final var file = fileService.loadById(documentReq.getFileId());

            final var documentType = documentTypeRepository.findById(documentReq.getDocumentTypeId());

            final var schedule = scheduleService.loadById(documentReq.getScheduleId());

            var document = new Document();
            document.setFileId(file);
            document.setDocumentTypeId(documentType.get());

            document.setActivity(documentReq.getActivity());

            document.setScheduleId(schedule);

            document.setIsSignedBySender(true);
            document.setIsSignedByReceiver(false);

            document = documentRepository.save(document);
        });
        
        insertRes.setId(null);
        insertRes.setMessage("Document(s) have been made!");
        return insertRes;
    }

}
