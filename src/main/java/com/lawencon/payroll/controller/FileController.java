package com.lawencon.payroll.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.payroll.dto.file.FileDownloadReqDto;
import com.lawencon.payroll.dto.file.FileReqDto;
import com.lawencon.payroll.dto.generalResponse.InsertResDto;
import com.lawencon.payroll.service.FileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @PostMapping("")
    public ResponseEntity<InsertResDto> uploadFile(@RequestBody FileReqDto data) {
        final var insertRes = fileService.uploadFile(data);
        return new ResponseEntity<>(insertRes, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> downloadFile(@PathVariable String id) {
        final String fileName = "attachment.png";
        final byte[] fileBytes = fileService.downloadFile(id);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=" + fileName).body(fileBytes);
    }
}
