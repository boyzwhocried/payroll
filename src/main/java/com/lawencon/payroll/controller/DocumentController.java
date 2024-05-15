package com.lawencon.payroll.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.payroll.dto.document.DocumentReqDto;
import com.lawencon.payroll.dto.generalResponse.InsertResDto;
import com.lawencon.payroll.service.DocumentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("new")
    public ResponseEntity<InsertResDto> createDocuments(@RequestBody List<DocumentReqDto> data) {
        final var insertRes = documentService.createDocument(data);
        return new ResponseEntity<>(insertRes, HttpStatus.CREATED);
    }
}
