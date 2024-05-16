package com.lawencon.payroll.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.payroll.dto.documentType.DocumentTypeResDto;
import com.lawencon.payroll.repository.DocumentTypeRepository;
import com.lawencon.payroll.service.DocumentTypeService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeService {
  private final DocumentTypeRepository documentTypeRepository;
  
  @Override
  public List<DocumentTypeResDto> getAll() {
    final var documentTypes = documentTypeRepository.findAll();
    final var documentTypesRes = new ArrayList<DocumentTypeResDto>();

    documentTypes.forEach(documentType -> {
      final var documentTypeRes = new DocumentTypeResDto();

      final var id = documentType.getId();
      final var code = documentType.getDocumentTypeCode();
      final var name = documentType.getDocumentTypeName();

      documentTypeRes.setId(id);
      documentTypeRes.setCode(code);
      documentTypeRes.setName(name);

      documentTypesRes.add(documentTypeRes);
    });

    return documentTypesRes;
  }
}