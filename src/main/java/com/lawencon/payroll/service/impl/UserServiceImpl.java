package com.lawencon.payroll.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lawencon.payroll.constant.Roles;
import com.lawencon.payroll.dto.generalResponse.DeleteResDto;
import com.lawencon.payroll.dto.generalResponse.InsertResDto;
import com.lawencon.payroll.dto.generalResponse.UpdateResDto;
import com.lawencon.payroll.dto.user.LoginReqDto;
import com.lawencon.payroll.dto.user.LoginResDto;
import com.lawencon.payroll.dto.user.UpdateUserReqDto;
import com.lawencon.payroll.dto.user.UserReqDto;
import com.lawencon.payroll.dto.user.UserResDto;
import com.lawencon.payroll.model.File;
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
        loginRes.setUserName(user.get().getUserName());
        loginRes.setUserId(user.get().getId());
        loginRes.setUserName(user.get().getUserName());
        loginRes.setRoleCode(role.getRoleCode());
        loginRes.setToken(token);

        if (file != null) {
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

        var file = new File();
        file.setFileContent(data.getFileContent());
        file.setFileContent(data.getFileExtension());

        file = fileService.saveFile(file);

        user.setUserName(data.getFullName());
        user.setEmail(email);
        user.setPassword(password);
        user.setRoleId(role);
        user.setPhoneNumber(data.getPhoneNumber());
        user.setProfilePictureId(file);
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

    @Override
    public List<UserResDto> getAllUsers() {
        final var usersRes = new ArrayList<UserResDto>();

        final var users = userRepository.findAll();
        users.forEach(user -> {
            final var userRes = new UserResDto();

            userRes.setId(user.getId());
            userRes.setUserName(user.getUserName());
            userRes.setEmail(user.getEmail());
            userRes.setRoleName(user.getRoleId().getRoleName());
            userRes.setPhoneNumber(user.getPhoneNumber());

            usersRes.add(userRes);
        });

        return usersRes;
    }

    @Override
    public List<UserResDto> getAllUsersByCode(String code) {
        final var usersRes = new ArrayList<UserResDto>();

        final var users = userRepository.findByRoleRoleCode(code);

        users.forEach(user -> {
            final var userRes = new UserResDto();

            userRes.setId(user.getId());
            userRes.setUserName(user.getUserName());
            userRes.setEmail(user.getEmail());
            userRes.setRoleName(user.getRoleId().getRoleName());
            userRes.setPhoneNumber(user.getPhoneNumber());

            usersRes.add(userRes);
        });

        return usersRes;
    }

    @Override
    public List<UserResDto> getAllUsersByPsId(String id) {
        final var usersRes = new ArrayList<UserResDto>();

        final var users = userRepository.findAllById(id);

        users.forEach(user -> {
            final var userRes = new UserResDto();

            userRes.setId(user.getId());
            userRes.setUserName(user.getUserName());
            userRes.setEmail(user.getEmail());
            userRes.setRoleName(user.getRoleId().getRoleName());
            userRes.setPhoneNumber(user.getPhoneNumber());

            usersRes.add(userRes);
        });

        return usersRes;
    }

    @Override
    public List<UserResDto> getAllUsersByPsIdExcept(String id) {
        final var usersRes = new ArrayList<UserResDto>();

        final var users = userRepository.findAllByIdNot(id);

        users.forEach(user -> {
            final var userRes = new UserResDto();

            userRes.setId(user.getId());
            userRes.setUserName(user.getUserName());
            userRes.setEmail(user.getEmail());
            userRes.setRoleName(user.getRoleId().getRoleName());
            userRes.setPhoneNumber(user.getPhoneNumber());

            usersRes.add(userRes);
        });

        return usersRes;
    }

    @Override
    @Transactional
    public UpdateResDto updateUser(UpdateUserReqDto data) {
        var user = userRepository.findById(data.getId()).get();

        var name = Optional.ofNullable(data.getName());
        if (name.isPresent()) {
            user.setUserName(name.get());
        }

        var email = Optional.ofNullable(data.getEmail());
        if (email.isPresent()) {
            user.setEmail(email.get());
        }

        var password = Optional.ofNullable(data.getPassword());
        if (password.isPresent()) {
            user.setPassword(password.get());
        }

        var phoneNo = Optional.ofNullable(data.getPhoneNo());
        if (phoneNo.isPresent()) {
            user.setPhoneNumber(phoneNo.get());
        }

        var content = Optional.ofNullable(data.getFileContent());
        if (content.isPresent()) {
            var file = user.getProfilePictureId();
            file.setFileContent(content.get());
            file.setFileExtension(data.getFileExtension());

            file = fileService.updateFile(file);

            user.setProfilePictureId(file);
        }

        user = userRepository.saveAndFlush(user);

        final var updateRes = new UpdateResDto();
        updateRes.setVersion(user.getVer());
        updateRes.setMessage("User data has been updated");

        return updateRes;
    }

    @Override
    @Transactional
    public DeleteResDto deleteUserById(String id) {
        userRepository.deleteById(id);

        final var deleteRes = new DeleteResDto();

        deleteRes.setMessage("User has been deleted!");
        return deleteRes;
    }

}
