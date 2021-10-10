package com.vosouq.bookkeeping.model.journalizing;

import com.vosouq.bookkeeping.enumeration.GeneralLegerAccountCategory;
import com.vosouq.bookkeeping.enumeration.NormalBalance;
import com.vosouq.bookkeeping.model.journalizing.converter.GeneralLedgerAccountCategoryConverter;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class GeneralLedger {

    @Id
    @Column(unique = true, nullable = false)
    @Convert(converter = GeneralLedgerAccountCategoryConverter.class)
    private Integer code;

    @Enumerated(EnumType.STRING)
    private GeneralLegerAccountCategory type;

    /**
     * NormalBalance or nature of this General ledger. values are: CREDIT or DEBIT
     */
    @Enumerated(EnumType.STRING)
    private NormalBalance normalBalance;

    private BigDecimal balance;

    /**
     * sum of all debit amounts of Subsidiary Ledgers under this GeneralLeger
     */
    @Column(nullable = false)
    private BigDecimal debit;

    /**
     * sum of all credit amounts of Subsidiary Ledgers under this GeneralLeger
     */
    @Column(nullable = false)
    private BigDecimal credit;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date dateTime;

    private String description;

    @ToString.Exclude
    @OneToMany(mappedBy = "generalLedger")
    private Set<SubsidiaryLedger> subsidiaryLedgerSet;
}
