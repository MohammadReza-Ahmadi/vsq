package com.vosouq.contract.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @CreatedDate
    @Column(name = "create_date")
    private Timestamp createDate;

    @LastModifiedDate
    @Column(name = "update_date")
    private Timestamp updateDate;

    @ManyToOne
    private Contract contract;

    @Column(name = "amount")
    private Long amount;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @Column(name = "due_date")
    private Timestamp dueDate;

    @Column(name = "payment_date")
    private Timestamp paymentDate;

    @Column(name = "uuid")
    private UUID uuid;

    @Column(name = "is_delayed")
    private Boolean isDelayed;

}
