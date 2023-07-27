package com.devstack.pos.service;

import com.devstack.pos.dto.request.RequestUserDto;

public interface UserService {
    public void createUser(RequestUserDto dto, String role);
}
