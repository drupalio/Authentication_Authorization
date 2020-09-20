package com.melhamra.api.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity(name = "users")
@Data
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userId;
    @Column(length = 20, nullable = false)
    private String firstName;
    @Column(length = 20, nullable = false)
    private String lastName;
    @Column(length = 20, nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String encryptedPassword;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "users_id")},
            inverseJoinColumns = {@JoinColumn(name = "roles_id")})
    private Set<RoleEntity> roles = new HashSet<>();

}
