package com.lawencon.payroll.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.payroll.dto.document.DocumentReqDto;
import com.lawencon.payroll.dto.document.DocumentResDto;
import com.lawencon.payroll.dto.generalResponse.InsertResDto;
import com.lawencon.payroll.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping()
    public ResponseEntity<InsertResDto> createDocumentsSchedule(@RequestBody List<DocumentReqDto> data) {
        final var insertRes = documentService.createDocuments(data);
        return new ResponseEntity<>(insertRes, HttpStatus.CREATED);
    }

    // @PostMapping("new")
    // public ResponseEntity<InsertResDto> insertDocument(@RequestBody DocumentReqDto data) {
    //     final var insertRes = documentService.createDocuments(data);

    //     return new ResponseEntity<>(insertRes, HttpStatus.CREATED);
    // }

    @GetMapping("{scheduleId}")
    public ResponseEntity<List<DocumentResDto>> getDocumentSchedule(@PathVariable String scheduleId) {
        final var documentRes = documentService.getDocumentsByScheduleId(scheduleId);

        return new ResponseEntity<>(documentRes, HttpStatus.OK);
    }

    // @PatchMapping()
    // public ResponseEntity<UpdateResDto> rescheduleDocuments(@RequestBody List<DocumentReqDto> data) {

    // }
}
