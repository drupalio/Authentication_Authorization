package com.melhamra.api.requests;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;


@Data
public class UserRequest {

    @NotBlank(message = "First name should not be empty")
    @Size(min = 3, max = 20, message = "Size should be between 3 and 20 character")
    private String firstName;

    @NotBlank(message = "Last name should not be empty")
    @Size(min = 3, max = 20, message = "Size should be between 3 and 20 character")
    private String lastName;

    @Email(message = "Email format is incorrect")
    @NotNull(message = "Email should not be null")
    private String email;

    @NotNull(message = "Password name should not be null")
    @Size(min = 8, max = 20, message = "Size should be between 8 and 20 character")
    private String password;

    private List<RoleRequest> roles;

}
