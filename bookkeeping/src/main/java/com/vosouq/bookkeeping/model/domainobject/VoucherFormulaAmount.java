package com.vosouq.bookkeeping.model.domainobject;

import com.vosouq.bookkeeping.model.posting.VoucherFormula;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoucherFormulaAmount {
    private Map<VoucherFormula, BigDecimal> amountMap = new HashMap<>();

    public BigDecimal getAmount() {
        return amountMap.get(VoucherFormula.AMOUNT);
    }

    public BigDecimal setAmount(BigDecimal amount) {
        return amountMap.put(VoucherFormula.AMOUNT, amount);
    }

    public BigDecimal getCommissionAmount() {
        return amountMap.get(VoucherFormula.COMMISSION);
    }

    public BigDecimal setCommissionAmount(BigDecimal amount) {
        return amountMap.put(VoucherFormula.COMMISSION, amount);
    }

    public BigDecimal getVATAmount() {
        return amountMap.get(VoucherFormula.VAT);
    }

    public BigDecimal setVATAmount(BigDecimal amount) {
        return amountMap.put(VoucherFormula.VAT, amount);
    }

    public BigDecimal getRemainAmount() {
        return amountMap.get(VoucherFormula.REMAIN_AMOUNT);
    }

    public BigDecimal setRemainAmount(BigDecimal amount) {
        return amountMap.put(VoucherFormula.REMAIN_AMOUNT, amount);
    }
}
