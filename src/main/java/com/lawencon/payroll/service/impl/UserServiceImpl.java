package com.lawencon.payroll.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lawencon.payroll.dto.InsertResDto;
import com.lawencon.payroll.dto.user.LoginReqDto;
import com.lawencon.payroll.dto.user.LoginResDto;
import com.lawencon.payroll.dto.user.UserReqDto;
import com.lawencon.payroll.model.File;
import com.lawencon.payroll.model.Role;
import com.lawencon.payroll.model.User;
import com.lawencon.payroll.repository.UserRepository;
import com.lawencon.payroll.service.EmailService;
import com.lawencon.payroll.service.FileService;
import com.lawencon.payroll.service.JwtService;
import com.lawencon.payroll.service.PrincipalService;
import com.lawencon.payroll.service.RoleService;
import com.lawencon.payroll.service.UserService;
import com.lawencon.payroll.util.GenerateUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final EmailService emailService;
    private final FileService fileService;
    private final JwtService jwtService;
    private final PrincipalService principalService;
    private final RoleService roleService;

    @Override
    public LoginResDto loginUser(LoginReqDto data) {
        final var loginRes = new LoginResDto();

        final var email = data.getEmail();
        final Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));

        final var cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, 1);

        final var claims = new HashMap<String, Object>();
        claims.put("exp", cal.getTime());
        claims.put("id", user.get().getId());

        final var token = jwtService.generateJwt(claims);

        final var role = user.get().getRoleId();

        final var file = user.get().getProfilePictureId();

        loginRes.setUserId(user.get().getId());
        loginRes.setUserName(user.get().getFullName());
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
		final var user = userRepository.findByEmail(email);
		if (user != null) {
			return new org.springframework.security.core.userdetails.User(email, user.getPassword(),
					new ArrayList<>());
		}
		throw new UsernameNotFoundException("Invalid input!");
	}

    @Override
    @Transactional
    public InsertResDto createUser(UserReqDto data) {
        final var insertRes = new InsertResDto();

        final var rawPassword = GenerateUtil.generateCode();
        final var password = passwordEncoder.encode(rawPassword);

        var user = new User();
        final var role = roleService.getById(data.getRoleId());

        final var email = data.getEmail();

        user.setFullName(data.getFullName());
        user.setEmail(email);
        user.setPassword(password);
        user.setRoleId(role);
        user.setProfilePictureId(fileService.saveFile(data.getFileDirectory()));
        user.setCreatedBy(principalService.getUserId());

        user = userRepository.save(user);
        
        final var subject = "New User Information";

        final var body = "Hello" + role.getRoleName() + "!\n"
                            + "Here's your email and password :"
                            + "Email : " + email + "\n"
                            + "Password : " + rawPassword + "\n";

        final Runnable runnable = () -> {
            emailService.sendEmail(email, subject, body);
        };

        final var mailThread = new Thread(runnable);
        mailThread.start();

        insertRes.setId(user.getId());
        insertRes.setMessage("User has been created");

        return insertRes;
    }

}
