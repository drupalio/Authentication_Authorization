package com.melhamra.api.controllers;


import com.melhamra.api.dtos.UserDto;
import com.melhamra.api.repositories.RoleRepository;
import com.melhamra.api.repositories.UserRepository;
import com.melhamra.api.requests.UserRequest;
import com.melhamra.api.responses.UserResponse;
import com.melhamra.api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping()
    public ResponseEntity<?> createUser(@RequestBody @Valid UserRequest userRequest){
        ModelMapper mapper = new ModelMapper();

        UserDto userDto = mapper.map(userRequest, UserDto.class);
        UserDto user = userService.createUser(userDto);
        UserResponse createdUser = mapper.map(user, UserResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }


}
