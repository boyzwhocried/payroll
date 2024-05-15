package com.lawencon.payroll.service.impl;

import java.util.Optional;

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
    public File saveFile(String directory) {
        final File file = new File();
        file.setFileDirectory(directory);
        System.out.println("\n\n\n\nID = " + file.getId() + "\n\n\n\n");
        file.setCreatedBy(principalService.getUserId());
        return fileRepository.save(file);
    }

    @Override
    public File loadById(String id) {
        final Optional<File> file = fileRepository.findById(id);
        return file.get();
    }

}
