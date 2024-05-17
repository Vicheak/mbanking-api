package com.vicheak.coreapi.api.authority;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vicheak.coreapi.api.user.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @OneToMany(mappedBy = "role")
    @JsonBackReference
    List<UserRole> userRoles;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Authority> authorities;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return "ROLE_" + name;
    }

}
