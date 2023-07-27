package com.devstack.pos.service.impl;

import com.devstack.pos.dto.core.UserDto;
import com.devstack.pos.dto.request.RequestUserDto;
import com.devstack.pos.entity.User;
import com.devstack.pos.entity.UserRoleHasUser;
import com.devstack.pos.exception.DuplicateEntryException;
import com.devstack.pos.exception.EntryNotFoundException;
import com.devstack.pos.repo.UserRepo;
import com.devstack.pos.repo.UserRoleHasUserRepo;
import com.devstack.pos.repo.UserRoleRepo;
import com.devstack.pos.service.UserService;
import com.devstack.pos.util.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Transactional
@Service
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final UserRoleRepo userRoleRepo;
    private final UserMapper userMapper;
    private final UserRoleHasUserRepo userRoleHasUserRepo;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepo userRepo, UserRoleRepo userRoleRepo, UserMapper userMapper, UserRoleHasUserRepo userRoleHasUserRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.userRoleRepo = userRoleRepo;
        this.userMapper = userMapper;
        this.userRoleHasUserRepo = userRoleHasUserRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void createUser(RequestUserDto dto, String role) {

        var selectedUserRole = userRoleRepo.findByRoleName(role);
        if(selectedUserRole.isEmpty()){
            throw new EntryNotFoundException("User Role Not Found");
        }

        var selectedUser = userRepo.findUserByEmail(dto.getEmail());
        if(selectedUser.isPresent()){
            throw new DuplicateEntryException("User email already exists");
        }

        UserDto userDto = new UserDto(String.valueOf(new Random().nextInt(1001)),
                dto.getEmail(), dto.getFullName(),
                passwordEncoder.encode(dto.getPassword()));

        User user = userMapper.toUser(userDto);
        userRepo.save(user);

        UserRoleHasUser userRoleHasUser = new UserRoleHasUser();
        userRoleHasUser.setUser(user);
        userRoleHasUser.setUserRole(selectedUserRole.get());

        userRoleHasUserRepo.save(userRoleHasUser);

    }
}
