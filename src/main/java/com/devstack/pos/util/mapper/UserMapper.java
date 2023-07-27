package com.devstack.pos.util.mapper;

import com.devstack.pos.dto.core.CustomerDto;
import com.devstack.pos.dto.core.UserDto;
import com.devstack.pos.dto.response.ResponseCustomerDto;
import com.devstack.pos.entity.Customer;
import com.devstack.pos.entity.User;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserDto dto);
}
