package com.melhamra.api.dtos;


import lombok.Data;

import java.util.List;

@Data
public class UserDto {

    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
    private List<RoleDto> roles;

}
