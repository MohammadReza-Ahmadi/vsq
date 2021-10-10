package com.vosouq.bookkeeping.model;

import com.vosouq.bookkeeping.enumeration.GatewayType;
import com.vosouq.bookkeeping.enumeration.RequestStatus;
import com.vosouq.bookkeeping.enumeration.RequestType;
import com.vosouq.bookkeeping.enumeration.RequesterType;
import com.vosouq.bookkeeping.model.journalizing.Account;
import com.vosouq.bookkeeping.model.journalizing.JournalEntry;
import com.vosouq.bookkeeping.model.journalizing.SubsidiaryLedger;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import static com.vosouq.bookkeeping.util.AppUtil.getZeroIfNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
public class BookkeepingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long requesterId;

    private BigDecimal amount;

    private BigDecimal commissionAmount;

    private BigDecimal vatAmount;

    private String gatewayRefId;

    private Long gatewayOrderId;

    @Enumerated(EnumType.STRING)
    private GatewayType gatewayType;

    @Enumerated(EnumType.STRING)
    private RequestType requestType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequesterType requesterType;

    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_account_id")
    private Account creditAccount;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debit_account_id")
    private Account debitAccount;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_subsidiary_ledger_code", nullable = false, updatable = false)
    private SubsidiaryLedger creditSubsidiaryLedger;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "debit_subsidiary_ledger_code", nullable = false, updatable = false)
    private SubsidiaryLedger debitSubsidiaryLedger;

    @ToString.Exclude
    @OneToMany(mappedBy = "bookkeepingRequest")
    private Set<JournalEntry> journalEntrySet;

    //todo: 6Aban
//    @ToString.Exclude
//    @OneToMany(mappedBy = "bookkeepingRequest")
//    private Set<Account> accountSet;

    @Temporal(TemporalType.TIMESTAMP)
    private Date requestDate;

    private String description;

    @Transient
    public BigDecimal getAmountWithoutCommissionAndVat() {
        return this.amount.subtract(getZeroIfNull(commissionAmount).add(getZeroIfNull(vatAmount)));
    }
}

