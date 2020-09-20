package com.melhamra.api.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.*;

@Data
@Entity(name = "roles")
public class RoleEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 10, nullable = false)
    private String role;

}
