package com.lawencon.payroll.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.payroll.dto.document.DocumentDownloadReqDto;
import com.lawencon.payroll.dto.document.DocumentReqDto;
import com.lawencon.payroll.dto.document.DocumentResDto;
import com.lawencon.payroll.dto.document.UpdateDocumentReqDto;
import com.lawencon.payroll.dto.document.UpdateDocumentScheduleReqDto;
import com.lawencon.payroll.dto.generalResponse.InsertResDto;
import com.lawencon.payroll.dto.generalResponse.UpdateResDto;
import com.lawencon.payroll.model.Document;
import com.lawencon.payroll.repository.DocumentRepository;
import com.lawencon.payroll.repository.DocumentTypeRepository;
import com.lawencon.payroll.service.DocumentService;
import com.lawencon.payroll.service.PrincipalService;
import com.lawencon.payroll.service.ScheduleService;
import com.lawencon.payroll.util.FtpUtil;

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
            document.setDocumentDeadline(LocalDateTime.parse(documentReq.getDocumentDeadline(), formatter));
            document.setActivity(documentReq.getActivity());
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

    @Override
    public List<DocumentResDto> getDocumentsByScheduleId(String scheduleId) {
        final var documentsRes = new ArrayList<DocumentResDto>();
        final var documents = documentRepository.getByScheduleId(scheduleId);
        
        documents.forEach(document -> {
            final var documentRes = new DocumentResDto();

            final var id = document.getId();
            final var activity = document.getActivity();
            final var deadline = document.getDocumentDeadline().toString();
            final var directory = document.getDocumentDirectory();
            final var name = document.getDocumentName();

            final var documentTypeId = document.getDocumentType().getId();
            
            documentRes.setDocumentId(id);
            documentRes.setActivity(activity);
            documentRes.setDocumentDeadline(deadline);
            documentRes.setDocumentDirectory(directory);
            documentRes.setDocumentName(name);
            documentRes.setDocumentTypeId(documentTypeId);

            documentsRes.add(documentRes);
        });

        return documentsRes;
    }

    @Override
    public UpdateResDto rescheduleDocuments(List<UpdateDocumentScheduleReqDto> data) {
        final var updateRes = new UpdateResDto();

        data.forEach(documentReq -> {
            var oldDoc = documentRepository.findById(documentReq.getDocumentId()).get();
	    	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            oldDoc.setDocumentDeadline(LocalDateTime.parse(documentReq.getDocumentDeadline(), formatter));
            oldDoc.setUpdatedBy(principalService.getUserId());

            oldDoc = documentRepository.saveAndFlush(oldDoc); 
        });
        
        updateRes.setVersion(null);
        updateRes.setMessage("Document(s) have been rescheduled!");
        return updateRes;
    }

    @Override
    public UpdateResDto uploadDocument(UpdateDocumentReqDto documentReq) {
        final var updateRes = new UpdateResDto();

        var oldDoc = documentRepository.findById(documentReq.getDocumentId()).get();

        final var directory = "/Files";

        final var base64 = documentReq.getBase64();
        final var documentExtension = documentReq.getExtension();
        final var userName = documentReq.getUserName();
        final var documentName = documentReq.getDocumentName();
        final var remoteFile = directory+"/"+userName+"/"+documentName+documentExtension;

        FtpUtil.sendFile(base64, remoteFile);

        oldDoc.setDocumentName(documentName);
        oldDoc.setDocumentDirectory(remoteFile);
        oldDoc.setUpdatedBy(principalService.getUserId());

        oldDoc = documentRepository.saveAndFlush(oldDoc); 

        updateRes.setMessage("Document Has Been Uploaded!");
        updateRes.setVersion(oldDoc.getVer());

        return updateRes;
    }

    @Override
    public void downloadDocument(DocumentDownloadReqDto data) {
        final var remoteFile = data.getRemoteDocument();
        final var downloadFile = data.getDownloadDocument();

        FtpUtil.downloadFile(remoteFile, downloadFile);
    }
}
