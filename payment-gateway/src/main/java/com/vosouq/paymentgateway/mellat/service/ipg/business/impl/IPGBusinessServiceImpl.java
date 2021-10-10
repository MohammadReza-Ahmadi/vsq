package com.vosouq.paymentgateway.mellat.service.ipg.business.impl;

import com.vosouq.commons.config.feign.FeignDecodeException;
import com.vosouq.paymentgateway.mellat.constant.CommonConstants;
import com.vosouq.paymentgateway.mellat.constant.NumberConstants;
import com.vosouq.paymentgateway.mellat.controller.dto.GetIpgPaymentRefIdRequest;
import com.vosouq.paymentgateway.mellat.enumeration.BpRequestStatus;
import com.vosouq.paymentgateway.mellat.enumeration.BpResponseCode;
import com.vosouq.paymentgateway.mellat.exception.BookkeepingCallException;
import com.vosouq.paymentgateway.mellat.exception.VosouqEntityNotFoundException;
import com.vosouq.paymentgateway.mellat.exception.ipg.IpgMellatSettleFailedException;
import com.vosouq.paymentgateway.mellat.exception.ipg.IpgMellatVerifyFailedException;
import com.vosouq.paymentgateway.mellat.model.ipg.BpPayment;
import com.vosouq.paymentgateway.mellat.model.ipg.OrderSequence;
import com.vosouq.paymentgateway.mellat.model.ipg.domainobject.IpgCallbackParameters;
import com.vosouq.paymentgateway.mellat.model.ipg.domainobject.IpgPaymentResponse;
import com.vosouq.paymentgateway.mellat.model.ipg.domainobject.IpgResponse;
import com.vosouq.paymentgateway.mellat.repository.BpPaymentRepository;
import com.vosouq.paymentgateway.mellat.service.builder.BpPaymentBuilder;
import com.vosouq.paymentgateway.mellat.service.builder.IpgCallSendingObjectBuilder;
import com.vosouq.paymentgateway.mellat.service.ipg.business.IPGBusinessService;
import com.vosouq.paymentgateway.mellat.service.ipg.crud.BpPaymentCrudService;
import com.vosouq.paymentgateway.mellat.service.thirdparty.BookkeepingRepository;
import com.vosouq.paymentgateway.mellat.util.AppUtil;
import com.vosouq.paymentgateway.mellat.util.BpBusinessUtil;
import com.vosouq.paymentgateway.mellat.validation.BpPayValidator;
import com.vosouq.paymentgateway.mellat.webservice.ipg.IPGWebService;
import com.vosouq.paymentgateway.mellat.webservice.ipg.gen.BpPayRequest;
import com.vosouq.paymentgateway.mellat.webservice.ipg.gen.BpSettleRequest;
import com.vosouq.paymentgateway.mellat.webservice.ipg.gen.BpVerifyRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * This is BpPayRequest businessService which handles all payment business related activities.
 */

@Slf4j
@Service
public class IPGBusinessServiceImpl implements IPGBusinessService {

    private final BpPaymentRepository bpPaymentRepository;
    private final BookkeepingRepository bookkeepingRepository;
    private final BpPaymentCrudService bpPaymentCrudService;
    private final IPGWebService ipgWebService;
    private final IpgCallSendingObjectBuilder ipgCallSendingObjectBuilder;

    public IPGBusinessServiceImpl(BpPaymentRepository bpPaymentRepository, BookkeepingRepository bookkeepingRepository, BpPaymentCrudService bpPaymentCrudService, IPGWebService ipgWebService, IpgCallSendingObjectBuilder ipgCallSendingObjectBuilder) {
        this.bpPaymentRepository = bpPaymentRepository;
        this.bookkeepingRepository = bookkeepingRepository;
        this.bpPaymentCrudService = bpPaymentCrudService;
        this.ipgWebService = ipgWebService;
        this.ipgCallSendingObjectBuilder = ipgCallSendingObjectBuilder;
    }


    @Override
    public IpgResponse getIpgPaymentRefId(GetIpgPaymentRefIdRequest request) {
        /* create BpPayment instance */
        BpPayment bpPayment = BpPaymentBuilder.build(request, BpRequestStatus.PENDING);

        /* save bpPayment instance and generate a new orderId */
        bpPaymentCrudService.save(bpPayment);

        /* create BpPayRequest instance */
        BpPayRequest bpPayRequest = ipgCallSendingObjectBuilder.buildBpPayRequest(bpPayment);

        /* get BpPaymentResponse instance which contains received resCode and RefId from mellat IPG */
        IpgResponse ipgResponse = ipgWebService.bpPayRequest(bpPayRequest);
//        getIpgGetRefIdResponse.setMobileNo(request.getMobileNo());

        /* update bpPayment by refId which received from IPG webservice*/
        bpPayment.setRefId(ipgResponse.getRefId());
        bpPayment.setResCode(ipgResponse.getResCode());
        bpPaymentCrudService.save(bpPayment);
        return ipgResponse;
    }


    @Override
    public IpgPaymentResponse handleIpgPaymentResponse(IpgCallbackParameters ipgCallbackParameters) {
        /* update status of inserted BpPayment to related status based on IPG response */
        OrderSequence orderSequence = new OrderSequence(ipgCallbackParameters.getSaleOrderId());
        BpPayment bpPayment = bpPaymentRepository.findByRefIdAndOrderSequence(ipgCallbackParameters.getRefId(), orderSequence);
        if (AppUtil.isNull(bpPayment)) {
            throw new VosouqEntityNotFoundException(String.format("There is no any BpPayment record by RefId:[%s] and orderId:[%d]", ipgCallbackParameters.getRefId(), orderSequence.getOrderId()));
        }

        /* validate ipg response by ipgCallbackParameters */
        BpPayValidator.validateBpPayResponse(bpPayment, ipgCallbackParameters);
        bpPayment.setSaleOrderId(ipgCallbackParameters.getSaleOrderId());
        bpPayment.setSaleReferenceId(ipgCallbackParameters.getSaleReferenceId());

        BpResponseCode bpResponseCode = BpResponseCode.resolve(ipgCallbackParameters.getResCod());
        /* check to verifyRequest if payment operation is not canceled */
        if (bpResponseCode.isSuccess()) {
            /* call bpVerifyRequest from mellat ipg webservice to verify succeed payment operation */
            BpVerifyRequest bpVerifyRequest = ipgCallSendingObjectBuilder.buildBpVerifyRequest(bpPayment);

            //todo: should be uncomment
//        IpgResponse verifyResponse = ipgWebService.bpVerifyRequest(bpVerifyRequest);
            //todo: should be removed
            IpgResponse verifyResponse = new IpgResponse(BpResponseCode.SUCCESS);

            if (verifyResponse.getResCode().isFailed()) {
                throw new IpgMellatVerifyFailedException();
            }

            /* call bpSettleRequest from mellat ipg webservice to deposit succeed payment amount to target bank-account */
            BpSettleRequest bpSettleRequest = ipgCallSendingObjectBuilder.buildBpSettleRequest(bpPayment);

            //todo: should be uncomment
//        IpgResponse settleResponse = ipgWebService.bpSettleRequest(bpSettleRequest);
            //todo: should be removed
            IpgResponse settleResponse = new IpgResponse(BpResponseCode.SUCCESS);

            if (settleResponse.getResCode().isFailed()) {
                throw new IpgMellatSettleFailedException();
            }
        }
        /* update BpPayment status in db  */
        bpPayment.setStatus(BpBusinessUtil.resolveBpRequestStatus(bpResponseCode));
        bpPaymentRepository.save(bpPayment);

        /* call bookkeeping success service to notify that of received response */
        try {
            if (bpPayment.getStatus().isSuccess()) {
                bookkeepingRepository.paymentSuccess(bpPayment.getOrderSequence().getOrderId());
            } else if (bpPayment.getStatus().isFailed()) {
                bookkeepingRepository.paymentFailed(bpPayment.getOrderSequence().getOrderId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BookkeepingCallException(((FeignDecodeException) e).getErrorMessage().getMessage());
        }

        return new IpgPaymentResponse(bpResponseCode, bpPayment.getRequesterId());
    }



}
