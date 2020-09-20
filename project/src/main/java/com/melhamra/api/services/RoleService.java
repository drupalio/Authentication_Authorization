package com.melhamra.api.services;

import com.melhamra.api.dtos.RoleDto;
import com.melhamra.api.entities.RoleEntity;
import com.melhamra.api.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<RoleDto> getAllRoles(){
        List<RoleEntity> roles = roleRepository.findAll();
        return roles.stream()
                .map(role -> new ModelMapper().map(role, RoleDto.class))
                .collect(Collectors.toList());
    }

}
