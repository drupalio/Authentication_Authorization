package com.melhamra.api.controllers;


import com.melhamra.api.dtos.RoleDto;
import com.melhamra.api.repositories.RoleRepository;
import com.melhamra.api.responses.RoleResponse;
import com.melhamra.api.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    private ResponseEntity<List<RoleResponse>> getRoles(){
        List<RoleDto> rolesDto = roleService.getAllRoles();
        List<RoleResponse> roles = rolesDto.stream()
                                    .map(role -> new ModelMapper().map(role, RoleResponse.class))
                                    .collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }


}
