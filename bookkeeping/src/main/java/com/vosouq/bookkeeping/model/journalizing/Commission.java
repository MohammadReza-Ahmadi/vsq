package com.vosouq.bookkeeping.model.journalizing;

import com.vosouq.bookkeeping.constant.NumberConstants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Commission {
    private BigDecimal rate = BigDecimal.valueOf(0.01);

    public BigDecimal getCommissionAmount(BigDecimal amount) {
        return (amount.multiply(rate, MathContext.DECIMAL32)).setScale(NumberConstants.ZERO_INT, RoundingMode.HALF_UP);
    }
}
