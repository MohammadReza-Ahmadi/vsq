package com.vosouq.paymentgateway.mellat.enumeration;

/**
 * @author <a href="mailto:m.reza79ahmadi@gmail.com">MohammadReza Ahmadi</a>
 * 01.09.20
 */


public enum PaymentStatus {
    PENDING,
    CANCEL,
    SUCCESS,
    PAY_SUCCESS,
    PAY_FAILED,
    VERIFY_SUCCESS,
    VERIFY_FAILED,
    SETTLE_SUCCESS,
    SETTLE_FAILED;

    public boolean isPaySuccess() {
        return this.equals(PAY_SUCCESS);
    }
}
