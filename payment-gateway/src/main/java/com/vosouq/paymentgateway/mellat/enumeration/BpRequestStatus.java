package com.vosouq.paymentgateway.mellat.enumeration;

/**
 * @author <a href="mailto:m.reza79ahmadi@gmail.com">MohammadReza Ahmadi</a>
 * 01.09.20
 */


public enum BpRequestStatus {
    PENDING,
    CANCEL,
    FAILED,
    ERROR,
    SUCCESS;

    public boolean isSuccess(){
        return this.equals(SUCCESS);
    }

    public boolean isFailed(){
        return this.equals(FAILED);
    }
}
