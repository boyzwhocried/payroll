package com.lawencon.payroll.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.lawencon.payroll.service.PrincipalService;

@Service
public class PrincipalServiceImpl implements PrincipalService {

	@Override
	public Long getUserId() {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		final Object userAuth = auth.getPrincipal();
		
		final Long userId = (Long.valueOf(userAuth.toString()));
				
		return userId;
	}
}
