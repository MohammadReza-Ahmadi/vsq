package com.vosouq.bookkeeping.service.business.impl;

import com.vosouq.bookkeeping.enumeration.RequestType;
import com.vosouq.bookkeeping.model.BookkeepingRequest;
import com.vosouq.bookkeeping.model.domainobject.VoucherFormulaAmount;
import com.vosouq.bookkeeping.model.posting.*;
import com.vosouq.bookkeeping.service.business.PostingModelBusinessService;
import com.vosouq.bookkeeping.service.crud.PostingModelCrudService;
import com.vosouq.bookkeeping.service.crud.SubsidiaryLedgerCrudService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.vosouq.bookkeeping.util.AppUtil.getZeroIfNull;

@Slf4j
@Service
public class PostingModelBusinessServiceImpl implements PostingModelBusinessService {

    private final PostingModelCrudService postingModelCrudService;
    private final SubsidiaryLedgerCrudService subsidiaryLedgerCrudService;

    public PostingModelBusinessServiceImpl(PostingModelCrudService postingModelCrudService, SubsidiaryLedgerCrudService subsidiaryLedgerCrudService) {
        this.postingModelCrudService = postingModelCrudService;
        this.subsidiaryLedgerCrudService = subsidiaryLedgerCrudService;
    }

    @Override
    public PostingModel createPostingModel(RequestType requestType, BookkeepingRequest bookkeepingRequest, VoucherFormulaAmount voucherFormulaAmount) {

        /* get PostingModel by code from db */
        PostingModel postingModel = postingModelCrudService.findByCode(requestType.getCode());

        /* loop on loaded postingModel to calculate each voucherRowModel's amount */
        for (VoucherModel voucherModel : postingModel.getVoucherModels()) {
            Map<VoucherIdentifier, BigDecimal> identifierMap = new HashMap<>();
            Collections.sort(voucherModel.getVoucherRowModels());

            for (VoucherRowModel voucherRowModel : voucherModel.getVoucherRowModels()) {
                /* resolve voucherFormula enum based on amountFormula value */
                VoucherFormula voucherFormula = VoucherFormula.resolve(voucherRowModel.getAmountFormula());

                switch (voucherFormula) {
                    case AMOUNT:
                        voucherRowModel.setAmount(bookkeepingRequest.getAmount());
                        voucherFormulaAmount.setAmount(bookkeepingRequest.getAmount());
//                        identifierMap.put(voucherRowModel.getIdentifier(), voucherRowModel.getAmount());
                        break;

                    case COMMISSION:
                        voucherRowModel.setAmount(getZeroIfNull(bookkeepingRequest.getCommissionAmount()));
                        identifierMap.put(voucherRowModel.getIdentifier(), voucherRowModel.getAmount());
                        voucherFormulaAmount.setCommissionAmount(bookkeepingRequest.getCommissionAmount());
                        break;

                    case CONTROL:
                        voucherRowModel.setAmount(BigDecimal.ONE);
                        identifierMap.put(voucherRowModel.getIdentifier(), voucherRowModel.getAmount());
                        break;

                    case FORMULA:
                        /* it needs to create a voucher formula parser and calculate amount based on amount formula */
                        /*todo: this block should be implemented later if it's required */
                        break;

                    case VAT:
                        voucherRowModel.setAmount(getZeroIfNull(bookkeepingRequest.getVatAmount()));
                        identifierMap.put(voucherRowModel.getIdentifier(), voucherRowModel.getAmount());
                        voucherFormulaAmount.setVATAmount(bookkeepingRequest.getVatAmount());
                        break;

                    case REMAIN_AMOUNT:
                        /* The value of this type of voucherRowModel is calculated this way: (Add the values of the above parts minus the total amount) */
                        Optional<BigDecimal> consideredAmountsSum = identifierMap.values().stream().reduce(BigDecimal::add);
                        BigDecimal remainAmount = bookkeepingRequest.getAmount().subtract(consideredAmountsSum.orElse(BigDecimal.ZERO));
                        voucherRowModel.setAmount(remainAmount);
                        voucherFormulaAmount.setRemainAmount(remainAmount);
                        identifierMap.put(voucherRowModel.getIdentifier(), voucherRowModel.getAmount());
                        break;
                }
            }
        }

        return postingModel;
    }

    @Override
    public PostingDebitCreditSubsidiaryLedgers getDebitAndCreditSubsidiaryLedgers(RequestType requestType) {
        PostingDebitCreditSubsidiaryLedgers postingDebitCreditSubsidiaryLedgers = new PostingDebitCreditSubsidiaryLedgers();
        postingDebitCreditSubsidiaryLedgers.setRequestType(requestType);

        /* get PostingModel by code from db */
        PostingModel postingModel = postingModelCrudService.findByCode(requestType.getCode());

        /* loop on loaded postingModel to calculate each voucherRowModel's amount */
        for (VoucherModel voucherModel : postingModel.getVoucherModels()) {
//            Map<VoucherIdentifier, BigDecimal> identifierMap = new HashMap<>();
            Collections.sort(voucherModel.getVoucherRowModels());
            for (VoucherRowModel voucherRowModel : voucherModel.getVoucherRowModels()) {
                /* resolve voucherFormula enum based on amountFormula value */
                VoucherFormula voucherFormula = VoucherFormula.resolve(voucherRowModel.getAmountFormula());

                /* add Amount and RemainAmount voucherFormal as debit and credit subsidiaryLedgers */
                if (voucherFormula.isAmount() || voucherFormula.isRemainAmount()) {
                    if (voucherRowModel.getNormalBalance().isCredit()) {
                        postingDebitCreditSubsidiaryLedgers.setCreditSubsidiaryLedger(voucherRowModel.getSubsidiaryLedger());
                    }

                    if (voucherRowModel.getNormalBalance().isDebit()) {
                        postingDebitCreditSubsidiaryLedgers.setDebitSubsidiaryLedger(voucherRowModel.getSubsidiaryLedger());
                    }
                }
            }
        }

        return postingDebitCreditSubsidiaryLedgers;
    }
}
