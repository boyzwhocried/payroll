package com.lawencon.payroll.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.lawencon.payroll.model.File;
import com.lawencon.payroll.repository.FileRepository;
import com.lawencon.payroll.service.FileService;
import com.lawencon.payroll.service.PrincipalService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final FileRepository fileRepository;
    private final PrincipalService principalService;

    @Override
    @Transactional
    public File saveFile(File file) {
        final var newFile = new File();

        file.setFileContent(file.getFileContent());
        file.setFileExtension(file.getFileExtension());
        file.setCreatedBy(principalService.getUserId());
        
        return fileRepository.save(newFile);
    }

    @Override
    public File loadById(String id) {
        final var file = fileRepository.findById(id);
        return file.get();
    }

}
