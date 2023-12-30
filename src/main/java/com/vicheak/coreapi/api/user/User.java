package com.vicheak.coreapi.api.user;

import com.vicheak.coreapi.api.account.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String uuid;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String phoneNumber;
    private String oneSignalId;
    private Boolean isStudent;
    private String studentCardNo;
    private Boolean isVerified;
    private String verifiedCode;
    private Boolean isDeleted;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<UserRole> userRoles;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserAccount> userAccounts;

}
