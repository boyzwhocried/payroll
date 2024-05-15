package com.lawencon.payroll.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lawencon.payroll.dto.user.LoginReqDto;
import com.lawencon.payroll.dto.user.LoginResDto;
import com.lawencon.payroll.model.File;
import com.lawencon.payroll.model.Role;
import com.lawencon.payroll.model.User;
import com.lawencon.payroll.repository.UserRepository;
import com.lawencon.payroll.service.JwtService;
import com.lawencon.payroll.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    @Override
    public LoginResDto loginUser(LoginReqDto data) {
        final LoginResDto loginRes = new LoginResDto();

        final String email = data.getEmail();
        final Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));

		final Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.HOUR_OF_DAY, 1);

        final Map<String, Object> claims = new HashMap<>();
		claims.put("exp", cal.getTime());
		claims.put("id", user.get().getId());

        final String token = jwtService.generateJwt(claims);

        final Role role = user.get().getRoleId();

        final File file = user.get().getProfilePictureId();

        loginRes.setUserId(user.get().getId());
        loginRes.setUserName(user.get().getFullName());
        loginRes.setRoleCode(role.getRoleCode());
        loginRes.setToken(token);

        if(file != null) {
            loginRes.setFileId(file.getId());
        }

        return loginRes;
    }

    @Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		final User user = userRepository.findByEmail(email);
		if (user != null) {
			return new org.springframework.security.core.userdetails.User(email, user.getPassword(),
					new ArrayList<>());
		}
		throw new UsernameNotFoundException("Invalid input!");
	}

}
