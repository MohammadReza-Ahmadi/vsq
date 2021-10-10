package com.vosouq.bookkeeping.model.journalizing;

import com.vosouq.bookkeeping.enumeration.NormalBalance;
import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.model.posting.VoucherRowModel;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class SubsidiaryLedger {

    @Id
    @NonNull
    private Integer code;

    private String title;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "general_ledger_code", nullable = false, referencedColumnName = "code")
    private GeneralLedger generalLedger;

    /**
     * NormalBalance or nature of this Subsidiary ledger. values are: CREDIT or DEBIT
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NormalBalance normalBalance;

    /**
     * This filed is number of journalizing page which will be the value of number filed in JournalizingPage table
     * when subsidiaryLedger instance is updated.
     */
    @Column(nullable = false)
    private Long journalizingPageNumber;

    private BigDecimal balance;

    /**
     * sum of all debit amounts of Journal Entries under this subsidiaryLedger
     */
    @Column(nullable = false)
    private BigDecimal debit;

    /**
     * sum of all credit amounts of Journal Entries under this subsidiaryLedger
     */
    @Column(nullable = false)
    private BigDecimal credit;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateTime;

    private String description;

    @ToString.Exclude
    @OneToMany(mappedBy = "creditSubsidiaryLedger")
    private Set<BookkeepingRequest> creditBookkeepingRequestSet;

    @ToString.Exclude
    @OneToMany(mappedBy = "debitSubsidiaryLedger")
    private Set<BookkeepingRequest> debitBookkeepingRequestSet;

    //todo: new
    @ToString.Exclude
    @OneToMany(mappedBy = "subsidiaryLedger")
    private Set<Account> accountSet;

    @ToString.Exclude
    @OneToMany(mappedBy = "subsidiaryLedger")
    private Set<JournalEntry> journalEntrySet;

    @ToString.Exclude
    @OneToMany(mappedBy = "subsidiaryLedger")
    private Set<VoucherRowModel> voucherRowModelSet;
}
