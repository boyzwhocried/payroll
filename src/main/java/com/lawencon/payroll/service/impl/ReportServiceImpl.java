package com.lawencon.payroll.service.impl;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import com.lawencon.payroll.repository.UserRepository;
import com.lawencon.payroll.service.ReportService;
import com.lawencon.payroll.service.UserService;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

  private final UserService userService;

  @Override
  public JasperPrint exportReport() throws FileNotFoundException, JRException {
    final var users = userService.getAllUsers();

    final var file = ResourceUtils.getFile("classPath:PayRollScheduleReport.jasper");
    
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(users);

		Map<String, Object> parameters = new HashMap<>();
		parameters.put("createdBy", "PSS");

    JasperPrint jasperPrint = JasperFillManager.fillReport(file.getAbsolutePath(), parameters, dataSource);

    return jasperPrint;
  }
}
