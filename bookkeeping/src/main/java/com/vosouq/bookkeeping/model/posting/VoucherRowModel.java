package com.vosouq.bookkeeping.model.posting;

import com.vosouq.bookkeeping.enumeration.NormalBalance;
import com.vosouq.bookkeeping.model.journalizing.SubsidiaryLedger;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class VoucherRowModel implements Comparable<VoucherRowModel> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer code;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "voucher_model_code")
    private VoucherModel voucherModel;

    @Enumerated(EnumType.STRING)
    private NormalBalance normalBalance;

    @ToString.Exclude
    @ManyToOne(optional = false, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinColumn(name = "subsidiary_ledger_code")
    private SubsidiaryLedger subsidiaryLedger;

    @Enumerated(EnumType.STRING)
    private VoucherIdentifier identifier;

    private Character amountFormula;

    @Transient
    private BigDecimal amount;

    private String narrative1;

    private String narrative2;

    private String narrative3;

    @Override
    public int compareTo(VoucherRowModel that) {
        return this.getIdentifier().compareTo(that.getIdentifier());
    }
}
