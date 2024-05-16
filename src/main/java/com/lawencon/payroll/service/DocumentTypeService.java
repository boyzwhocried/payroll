package com.lawencon.payroll.service;


import java.util.List;

import com.lawencon.payroll.dto.documentType.DocumentTypeResDto;

public interface DocumentTypeService {
  List<DocumentTypeResDto> getAll();
}