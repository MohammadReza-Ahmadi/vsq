package com.vosouq.bookkeeping.service.business.impl;

import com.vosouq.bookkeeping.enumeration.AccountStatus;
import com.vosouq.bookkeeping.enumeration.AccountTransactionType;
import com.vosouq.bookkeeping.enumeration.AccountType;
import com.vosouq.bookkeeping.enumeration.CurrencyCode;
import com.vosouq.bookkeeping.model.journalizing.Account;
import com.vosouq.bookkeeping.model.journalizing.Currency;
import com.vosouq.bookkeeping.model.journalizing.GeneralLedger;
import com.vosouq.bookkeeping.model.journalizing.SubsidiaryLedger;
import com.vosouq.bookkeeping.repository.AccountRepository;
import com.vosouq.bookkeeping.service.crud.CurrencyCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class ImportBaseDataCrudServiceImpl {

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String ddlAuto;

    @Repository
    protected interface GeneralLedgerBaseDataRepository extends CrudRepository<GeneralLedger, Long> {
        @Modifying
        @Transactional
        @Query(value = "insert into general_ledger(type, code, debit ,credit,  description, normal_balance, date_time, balance) values\n" +
                "('ASSETS',1,0,0,'سرفصل کل: دارایی','DEBIT',now(),0);", nativeQuery = true)
        void insertASSETS();

        @Modifying
        @Transactional
        @Query(value = "insert into general_ledger(type, code,debit, credit,  description, normal_balance, date_time, balance) values\n" +
                "('LIABILIIES',2,0,0,'سرفصل کل: بدهی','CREDIT',now(),0);", nativeQuery = true)
        void insertLIABILIIES();

        @Modifying
        @Transactional
        @Query(value = "insert into general_ledger(type, code,debit, credit,  description, normal_balance, date_time, balance) values\n" +
                "('EQUITIES',3,0,0,'سرفصل کل: سرمایه','CREDIT',now(),0);", nativeQuery = true)
        void insertEQUITIES();

        @Modifying
        @Transactional
        @Query(value = "insert into general_ledger(type, code,debit, credit,  description, normal_balance, date_time, balance) values\n" +
                "('REVENUE',4,0,0,'سرفصل کل: درآمد','CREDIT',now(),0);", nativeQuery = true)
        void insertREVENUE();

        @Modifying
        @Transactional
        @Query(value = "insert into general_ledger(type, code,debit, credit,  description, normal_balance, date_time, balance) values\n" +
                "('GAINS',5,0,0,'سرفصل کل: سود','CREDIT',now(),0);", nativeQuery = true)
        void insertGAINS();

        @Modifying
        @Transactional
        @Query(value = "insert into general_ledger(type, code,debit, credit,  description, normal_balance, date_time, balance) values\n" +
                "('EXPENSES',6,0,0,'سرفصل کل: هزینه','DEBIT',now(),0);", nativeQuery = true)
        void insertEXPENSES();

        @Modifying
        @Transactional
        @Query(value = "insert into general_ledger(type, code,debit, credit,  description, normal_balance, date_time, balance) values\n" +
                "('LOSSES',7,0,0,'سرفصل کل: زیان','DEBIT',now(),0);", nativeQuery = true)
        void insertLOSSES();

        @Modifying
        @Transactional
        @Query(value = "insert into general_ledger(type, code,debit, credit,  description, normal_balance, date_time, balance) values\n" +
                "('CONTROL_ACCOUNTS',8,0,0,'سرفصل کل: حسابهای آماری (کنترلی)', 'NO_NATURE',now(),0);", nativeQuery = true)
        void insertCONTROL_ACCOUNTS();
    }

    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private GeneralLedgerBaseDataRepository generalLedgerBaseDataRepository;

    @PostConstruct
    private void insertGeneralLedgers(){
        if (ddlAuto.toLowerCase().equals("create")) {
            generalLedgerBaseDataRepository.insertASSETS();
            generalLedgerBaseDataRepository.insertLIABILIIES();
            generalLedgerBaseDataRepository.insertEQUITIES();
            generalLedgerBaseDataRepository.insertREVENUE();
            generalLedgerBaseDataRepository.insertGAINS();
            generalLedgerBaseDataRepository.insertEXPENSES();
            generalLedgerBaseDataRepository.insertCONTROL_ACCOUNTS();
        }
    }

    

    @Repository
    protected interface SubsidiaryLedgerBaseDataRepository extends CrudRepository<SubsidiaryLedger, Long> {

        @Modifying
        @Transactional
        @Query(value = "insert into subsidiary_ledger(code,debit, credit,  description, journalizing_page_number, normal_balance, title, date_time, balance, general_ledger_code) values\n" +
                "(1,0,0,'سرفصل معین: موجودی نقد و بانک',0,'DEBIT','SUB-LEDGER: BANK',now(),0,1)", nativeQuery = true)
        void insertBANK();

        @Modifying
        @Transactional
        @Query(value = "insert into subsidiary_ledger(code,debit, credit,  description, journalizing_page_number, normal_balance, title, date_time, balance, general_ledger_code) values\n" +
                "(2,0,0,'سرفصل معین: موجودی قابل معامله مشتریان',0,'CREDIT','SUB-LEDGER: Exchange Tradable Account Balance of Customers',now(),0,2)", nativeQuery = true)
        void insertExchangeTradableAccount();

        @Modifying
        @Transactional
        @Query(value = "insert into subsidiary_ledger(code,debit, credit,  description, journalizing_page_number, normal_balance, title, date_time, balance, general_ledger_code) values\n" +
                "(3,0,0,'سرفصل معین: موجودی مسدود شده مشتریان',0,'CREDIT','SUB-LEDGER: Blocked Account Balance of Customers',now(),0,2)", nativeQuery = true)
        void insertBlockedAccountBalance();

        @Modifying
        @Transactional
        @Query(value = "insert into subsidiary_ledger(code,debit, credit,  description, journalizing_page_number, normal_balance, title, date_time, balance, general_ledger_code) values\n" +
                "(4,0,0,'سرفصل معین: مالیات بر ارزش افزوده',0,'CREDIT','SUB-LEDGER: VAT (Value-added tax)',now(),0,2)", nativeQuery = true)
        void insertVAT();

        @Modifying
        @Transactional
        @Query(value = "insert into subsidiary_ledger(code,debit, credit,  description, journalizing_page_number, normal_balance, title, date_time, balance, general_ledger_code) values\n" +
                "(5,0,0,'سرفصل معین: کارمزد معامله امن خرید',0,'CREDIT','SUB-LEDGER: Secure Purchase Commission',now(),0,4)", nativeQuery = true)
        void insertSecurePurchaseCommission();

        @Modifying
        @Transactional
        @Query(value = "insert into subsidiary_ledger(code,debit, credit,  description, journalizing_page_number, normal_balance, title, date_time, balance, general_ledger_code) values\n" +
                "(6,0,0,'سرفصل معین: کارمزد معامله امن فروش',0,'CREDIT','SUB-LEDGER: Secure Sale Commission',now(),0,4)", nativeQuery = true)
        void insertSecureSaleCommission();

        @Modifying
        @Transactional
        @Query(value = "insert into subsidiary_ledger(code,debit, credit,  description, journalizing_page_number, normal_balance, title, date_time, balance, general_ledger_code) values\n" +
                "(7,0,0,'سرفصل معین: خریداران کالا و خدمات',0,'DEBIT','SUB-LEDGER: Buyers of goods and services',now(),0,8)", nativeQuery = true)
        void insertBuyersOfGoods();

        @Modifying
        @Transactional
        @Query(value = "insert into subsidiary_ledger(code,debit, credit,  description, journalizing_page_number, normal_balance, title, date_time, balance, general_ledger_code) values\n" +
                "(8,0,0,'سرفصل معین: فروشندگان کالا و خدمات',0,'CREDIT','SUB-LEDGER: Sellers of goods and services',now(),0,8)", nativeQuery = true)
        void insertSellerOfGoods();
    }


    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private SubsidiaryLedgerBaseDataRepository subsidiaryLedgerBaseDataRepository;

    @PostConstruct
    private void insertSubsidiaryLedgers(){
        if (ddlAuto.toLowerCase().equals("create")) {
            subsidiaryLedgerBaseDataRepository.insertBANK();
            subsidiaryLedgerBaseDataRepository.insertExchangeTradableAccount();
            subsidiaryLedgerBaseDataRepository.insertBlockedAccountBalance();
            subsidiaryLedgerBaseDataRepository.insertVAT();
            subsidiaryLedgerBaseDataRepository.insertSecurePurchaseCommission();
            subsidiaryLedgerBaseDataRepository.insertSecureSaleCommission();
            subsidiaryLedgerBaseDataRepository.insertBuyersOfGoods();
            subsidiaryLedgerBaseDataRepository.insertSellerOfGoods();
        }
    }




    @Repository
    protected interface AccountBaseDataRepository extends CrudRepository<Account, Long> {
        @Modifying
        @Transactional
        @Query(value = "insert into account(id,type,currency_code,subsidiary_ledger_code,user_id,status,balance,withdrawable_balance,blocked_balance,create_timestamp,title,description) values\n" +
                "(1,'VOSOUQ','IRR',1,1,'ACTIVE',0,0,0,now(),'حساب اعتباری وثوق','حساب اعتباری وثوق زیر سرفصل معین:موجودی نقد و بانک')", nativeQuery = true)
        void insertVosouqAccount();

        @Modifying
        @Transactional
        @Query(value = "insert into account(id,type,currency_code,subsidiary_ledger_code,user_id,status,balance,withdrawable_balance,blocked_balance,create_timestamp,title,description) values\n" +
                "(2,'USER','IRR',2,2,'ACTIVE',100000,100000,0,now(),'حساب اعتباری کاربر با شناسه ۲','حساب اعتباری کاربر با شناسه ۱ زیر سرفصل معین:موجودی قابل معامله مشتریان')", nativeQuery = true)
        void insertUserAccount();
    }



    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private AccountRepository accountRepository;
    @SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
    @Autowired
    private CurrencyCrudService currencyCrudService;

//    @PostConstruct
    private void insertAccounts(){
        if (ddlAuto.toLowerCase().equals("create")) {
            createAccounts();
        }
    }

    public void createAccounts() {
        /** save one pair of credit and debit accounts*/
        Account creditAccount = new Account();

        /** User id */
        creditAccount.setUserId(2L);
        creditAccount.setType(AccountType.USER);
        creditAccount.setStatus(AccountStatus.ACTIVE);
        creditAccount.setTransactionType(AccountTransactionType.DEPOSIT);
        creditAccount.setSubsidiaryLedger(new SubsidiaryLedger(1));
        creditAccount.setCreateTimestamp(new Date());
        creditAccount.setBalance(BigDecimal.valueOf(1000000L));
        creditAccount.setWithdrawableBalance(BigDecimal.valueOf(1000000L));
        creditAccount.setBlockedBalance(BigDecimal.valueOf(0));
        creditAccount.setTitle("حساب اعتباری کاربر با شناسه ۲");
        creditAccount.setDescription("حساب اعتباری کاربر با شناسه ۱ زیر سرفصل معین:موجودی قابل معامله مشتریان");


        /** Vosouq id */
        Account debitAccount = new Account();
        debitAccount.setUserId(1L);
        debitAccount.setType(AccountType.VOSOUQ);
        debitAccount.setStatus(AccountStatus.ACTIVE);
        debitAccount.setTransactionType(AccountTransactionType.DEPOSIT);
        debitAccount.setSubsidiaryLedger(new SubsidiaryLedger(2));
        debitAccount.setCreateTimestamp(new Date());
        debitAccount.setBalance(BigDecimal.valueOf(50000000L));
        debitAccount.setWithdrawableBalance(BigDecimal.valueOf(50000000L));
        debitAccount.setBlockedBalance(BigDecimal.valueOf(0));
        debitAccount.setTitle("حساب اعتباری وثوق");
        debitAccount.setDescription("حساب اعتباری وثوق زیر سرفصل معین:موجودی نقد و بانک");

        /** find currency by code*/
        Currency currency = currencyCrudService.findByCode(CurrencyCode.IRR);

        creditAccount.setCurrency(currency);
        debitAccount.setCurrency(currency);
        accountRepository.save(creditAccount);
        accountRepository.save(debitAccount);
    }
}
