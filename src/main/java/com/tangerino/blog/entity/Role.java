package com.tangerino.blog.entity;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "tb_role")
@AllArgsConstructor
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private Long id;

    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    private String authority;
    private Date dataInsert = new Date();

    private Date dataUpdate;

    public Role(String auth) {
        authority = auth;
    }
}
