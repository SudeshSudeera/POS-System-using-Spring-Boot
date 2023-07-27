package com.devstack.pos.api;

import com.devstack.pos.dto.request.RequestUserDto;
import com.devstack.pos.service.UserService;
import com.devstack.pos.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/register", params = "role")
    public ResponseEntity<StandardResponse> createUser(@RequestBody RequestUserDto dto, @RequestParam String role){

        userService.createUser(dto,role);
        return new ResponseEntity<>(new StandardResponse(201,"",null), HttpStatus.CREATED);
    }
}
