package com.devstack.pos.service.impl;

import com.devstack.pos.entity.UserRole;
import com.devstack.pos.repo.UserRepo;
import com.devstack.pos.repo.UserRoleRepo;
import com.devstack.pos.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    private final UserRoleRepo userRoleRepo;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepo userRoleRepo) {
        this.userRoleRepo = userRoleRepo;
    }

    @Override
    public void initializeRoles() {
        if(userRoleRepo.findAll().isEmpty()){
            UserRole admin = new UserRole("POS-R-1","ADMIN","admin description",null);
            UserRole user = new UserRole("POS-R-2","USER","user description",null);
            UserRole manager = new UserRole("POS-R-3","MANAGER","manager description",null);

            userRoleRepo.saveAll(List.of(admin,user,manager));

        }

    }

}
