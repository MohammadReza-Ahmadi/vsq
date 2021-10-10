package com.vosouq.bookkeeping.service.validator;

import com.vosouq.bookkeeping.constant.NumberConstants;
import com.vosouq.bookkeeping.exception.iban.IbanFormatNotCorrectException;
import com.vosouq.bookkeeping.exception.iban.IbanLengthNotCorrectException;
import com.vosouq.bookkeeping.exception.iban.IbanNumberNotCorrectException;
import com.vosouq.bookkeeping.exception.iban.IbanValueIsEmptyException;
import com.vosouq.bookkeeping.util.AppUtil;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.vosouq.bookkeeping.constant.NumberConstants.*;

@Service
public class IbanAccountValidator {

    public void validateIban(String iban) {
        try {
            innerValidateIban(iban);
        } catch (NumberFormatException e) {
            throw new IbanFormatNotCorrectException();
        }
    }

    public void innerValidateIban(String iban) throws NumberFormatException {
        if (AppUtil.isNullOrEmpty(iban)) {
            throw new IbanValueIsEmptyException();
        }

        if (iban.length() != NumberConstants.TWENTY_SIX_INT) {
            throw new IbanLengthNotCorrectException();
        }

        String cod = resolveCode(iban.charAt(NumberConstants.ZERO_INT)) + resolveCode(iban.charAt(NumberConstants.ONE_INT));
        int cd = Integer.parseInt(cod);
        if(cd < _1010 || cd > _3535){
            throw new IbanFormatNotCorrectException();
        }

        cod  = cod + iban.substring(NumberConstants.TWO_INT,NumberConstants.FOUR_INT);
        BigDecimal num = new BigDecimal (iban.substring(NumberConstants.FOUR_INT) + cod);
        if (num.remainder(NumberConstants.NINETY_SEVEN_BIG_DECIMAL).longValue() != NumberConstants.ONE_INT) {
            throw new IbanNumberNotCorrectException();
        }
    }

    private String resolveCode(Character chr) {
        for (int i = TEN_INT; i <= THIRTY_FIVE_INT; i++) {
            if ((char) (i + FIFTY_FIVE_INT) == chr)
                return i + "";
        }
        return MINUS_ONE_INT + "";
    }
}
