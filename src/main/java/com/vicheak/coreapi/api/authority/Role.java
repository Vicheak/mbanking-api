package com.vicheak.coreapi.api.authority;

import com.vicheak.coreapi.api.user.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "role")
    List<UserRole> userRoles;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities;

}
