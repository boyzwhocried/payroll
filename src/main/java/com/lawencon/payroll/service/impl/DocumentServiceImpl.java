package com.lawencon.payroll.service.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.payroll.dto.document.DocumentDownloadResDto;
import com.lawencon.payroll.dto.document.DocumentReqDto;
import com.lawencon.payroll.dto.document.DocumentResDto;
import com.lawencon.payroll.dto.document.UpdateDocumentReqDto;
import com.lawencon.payroll.dto.document.UpdateDocumentScheduleReqDto;
import com.lawencon.payroll.dto.generalResponse.InsertResDto;
import com.lawencon.payroll.dto.generalResponse.UpdateResDto;
import com.lawencon.payroll.model.Document;
import com.lawencon.payroll.repository.DocumentRepository;
import com.lawencon.payroll.service.DocumentService;
import com.lawencon.payroll.service.PrincipalService;
import com.lawencon.payroll.service.ScheduleService;
import com.lawencon.payroll.util.FtpUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;
    private final PrincipalService principalService;

    private final ScheduleService scheduleService;

    @Override
    public InsertResDto createDocuments(DocumentReqDto data) {
        final var insertRes = new InsertResDto();

        final var schedule = scheduleService.loadById(data.getScheduleId());
        
        final var documentsReq = data.getDocumentsReqDto();

        documentsReq.forEach(documentReq -> {

		    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            final var deadline = LocalDateTime.parse(documentReq.getDocumentDeadline(), formatter);
            final var activity = documentReq.getActivity();

            var document = new Document();
            document.setDocumentDeadline(deadline);
            document.setActivity(activity);
            document.setSchedule(schedule);
            document.setIsSignedByClient(false);
            document.setIsSignedByPs(false);
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
            
            documentRes.setDocumentId(id);
            documentRes.setActivity(activity);
            documentRes.setDocumentDeadline(deadline);
            documentRes.setDocumentDirectory(directory);
            documentRes.setDocumentName(name);

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
    public UpdateResDto uploadDocument(UpdateDocumentReqDto data) {
        final var updateRes = new UpdateResDto();

        var oldDocument = documentRepository.findById(data.getDocumentId()).get();

        final var base64 = data.getBase64();
        final var userName = data.getUserName();
        final var documentName = data.getDocumentName();
        final var directory = "/Files/" + userName + "/";
        
        final var remoteFile = directory + documentName;

        FtpUtil.sendFile(base64, remoteFile);

        oldDocument.setDocumentName(documentName);
        oldDocument.setDocumentDirectory(directory);

        oldDocument.setUpdatedBy(principalService.getUserId());

        oldDocument = documentRepository.saveAndFlush(oldDocument); 

        updateRes.setVersion(oldDocument.getVer());
        updateRes.setMessage("Document Has Been Uploaded!");

        return updateRes;
    }

    @Override
    public DocumentDownloadResDto downloadDocument(String id) {
        final var downloadRes = new DocumentDownloadResDto();

        final var document = documentRepository.findById(id).get();

        final var documentName = document.getDocumentName();
        
        final var remoteFile = (document.getDocumentDirectory()) + documentName;

        downloadRes.setFileName(documentName);
        downloadRes.setFileBytes(FtpUtil.downloadFile(remoteFile));

        return downloadRes;
    }
}
