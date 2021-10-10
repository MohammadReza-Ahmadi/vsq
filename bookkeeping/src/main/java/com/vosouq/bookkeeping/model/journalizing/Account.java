package com.vosouq.bookkeeping.model.journalizing;

import com.vosouq.bookkeeping.enumeration.AccountStatus;
import com.vosouq.bookkeeping.enumeration.AccountTransactionType;
import com.vosouq.bookkeeping.enumeration.AccountType;
import com.vosouq.bookkeeping.model.BookkeepingRequest;
import lombok.*;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Audited
public class Account {

    public final static String FLD_transactionType = "transactionType";
    public final static String FLD_bookkeepingRequestId = "bookkeepingRequestId";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @NotAudited
    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "currency_code", nullable = false)
    private Currency currency;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private AccountType type;

    @NotAudited
    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "subsidiary_ledger_code")
    private SubsidiaryLedger subsidiaryLedger;

    @NotNull
    @Column(nullable = false)
    private Long bookkeepingRequestId;

    @Column(nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    @Enumerated(EnumType.ORDINAL)
    private AccountTransactionType transactionType;

    @Column(nullable = false)
    private BigDecimal balance;

    @Column(nullable = false)
    private BigDecimal withdrawableBalance;

    @Column(nullable = false)
    private BigDecimal blockedBalance;

    @Column(nullable = false)
    private BigDecimal transactionAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createTimestamp;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTimestamp;

    private String description;

    @NotAudited
    @ToString.Exclude
    @OneToMany(mappedBy = "debitAccount")
    private Set<BookkeepingRequest> debitBookkeepingRequestSet;

    @NotAudited
    @ToString.Exclude
    @OneToMany(mappedBy = "creditAccount")
    private Set<BookkeepingRequest> creditBookkeepingRequestSet;

    @Transient
    public void increaseBalance(BigDecimal amount) {
        balance = balance.add(amount);
        transactionType = AccountTransactionType.DEPOSIT;
    }

    @Transient
    public void decreaseBalance(BigDecimal amount) {
/*        if ((balance.compareTo(BigDecimal.ZERO) == ZERO_INT) ||
                (balance.compareTo(amount) <= ZERO_INT)) {
            balance = BigDecimal.ZERO;
            return;
        }*/
        balance = balance.subtract(amount);
    }

    @Transient
    public void increaseWithdrawableBalance(BigDecimal amount) {
        withdrawableBalance = withdrawableBalance.add(amount);
    }

    @Transient
    public void decreaseWithdrawableBalance(BigDecimal amount) {
/*        if ((withdrawableBalance.compareTo(BigDecimal.ZERO) == ZERO_INT) ||
                (withdrawableBalance.compareTo(amount) <= ZERO_INT)) {
            withdrawableBalance = BigDecimal.ZERO;
            return;
        }*/
        withdrawableBalance = withdrawableBalance.subtract(amount);
    }

    @Transient
    public void increaseBlockedBalance(BigDecimal amount) {
        blockedBalance = blockedBalance.add(amount);
    }

    @Transient
    public void decreaseBlockedBalance(BigDecimal amount) {
        blockedBalance = blockedBalance.subtract(amount);
    }
}
