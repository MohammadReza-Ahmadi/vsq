package com.vosouq.paymentgateway.mellat.util;

import com.vosouq.paymentgateway.mellat.enumeration.BpRequestStatus;
import com.vosouq.paymentgateway.mellat.enumeration.BpResponseCode;

/**
 * @author <a href="mailto:m.reza79ahmadi@gmail.com">MohammadReza Ahmadi</a>
 * 22.08.20
 */


public class BpBusinessUtil {

    public static BpRequestStatus resolveBpRequestStatus(BpResponseCode bpResponseCode) {
        if (bpResponseCode.isSuccess())
            return BpRequestStatus.SUCCESS;

        if (bpResponseCode.isCancel())
            return BpRequestStatus.CANCEL;

        if (bpResponseCode.isError())
            return BpRequestStatus.ERROR;

        if (bpResponseCode.isFailed())
            return BpRequestStatus.FAILED;

        throw new IllegalArgumentException("BpResponseCode could not resolved!");
    }

}
