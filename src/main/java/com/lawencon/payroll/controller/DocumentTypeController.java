package com.lawencon.payroll.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.payroll.dto.documentType.DocumentTypeResDto;
import com.lawencon.payroll.service.DocumentTypeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("document-types")
public class DocumentTypeController {
  private final DocumentTypeService documentTypeService;

  @GetMapping()
  public ResponseEntity<List<DocumentTypeResDto>> getDocumentType() {
    final var documentTypeRes = documentTypeService.getAll();

    return new ResponseEntity<>(documentTypeRes, HttpStatus.OK);
  }
}