package com.vosouq.bookkeeping.model.journalizing;

import com.vosouq.bookkeeping.model.BookkeepingRequest;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class JournalEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    private Long id;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "bookkeeping_request_id")
    private BookkeepingRequest bookkeepingRequest;

    /**
     * number of JournalizingPage table which is generated a new value for everyday and also saved by JournalEntry table.
     */
    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "journalizing_page_no", nullable = false, updatable = false)
    private JournalizingPage journalizingPage;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "subsidiary_ledger_code", nullable = false, updatable = false)
    private SubsidiaryLedger subsidiaryLedger;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "debit_account_id", updatable = false)
    private Account debitAccount;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "credit_account_id", updatable = false)
    private Account creditAccount;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_subsidiary_ledger_code", updatable = false)
    private SubsidiaryLedger creditSubsidiaryLedger;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debit_subsidiary_ledger_code", updatable = false)
    private SubsidiaryLedger debitSubsidiaryLedger;

    /**
     * debit amount of journal entry
     */
    @Column(updatable = false)
    private BigDecimal debit;

    /**
     * credit amount of journal entry
     */
    @Column(updatable = false)
    private BigDecimal credit;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date insertDateTime;

    private String narrative1;

    private String narrative2;

    private String narrative3;

    private String description;
}
