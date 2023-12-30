package com.vicheak.coreapi.api.transaction;

import com.vicheak.coreapi.api.account.Account;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"sender_act_id", "receiver_act_id"})
})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "sender_act_id")
    private Account sender;

    @ManyToOne
    @JoinColumn(name = "receiver_act_id")
    private Account receiver;

    private BigDecimal amount;
    private String remark;
    private Boolean isPayment;
    private String studentCardNo;
    private LocalDateTime transactionAt;

}
