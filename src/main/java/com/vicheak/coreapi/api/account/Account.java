package com.vicheak.coreapi.api.account;

import com.vicheak.coreapi.api.accounttype.AccountType;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String actNo;
    private String actName;
    private Double transferLimit;
    private String pin;

    @ManyToOne
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<UserAccount> userAccounts;

}
