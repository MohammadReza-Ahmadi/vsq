package com.vosouq.bookkeeping.controller.business.old;

import com.vosouq.bookkeeping.controller.crud.IbanAccountCrudControllerTest;
import com.vosouq.bookkeeping.service.business.UserBusinessService;
import com.vosouq.bookkeeping.service.business.thirdparty.ContractRepository;
import com.vosouq.bookkeeping.service.crud.BookkeepingRequestCrudService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
@ActiveProfiles("dev")
@SpringBootTest
@AutoConfigureMockMvc
public class BookkeepingBusinessTest_OLD {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserBusinessService userBusinessService;
    @MockBean
    private ContractRepository contractRepository;
    @Autowired
    private BookkeepingRequestCrudService bookkeepingRequestCrudService;

    private AccountBusinessControllerTest_OLD accountBusinessControllerTestOLD;
    private ContractBusinessControllerTest_OLD contractBusinessControllerTestOLD;
    private IpgMellatPGResponseControllerTest_OLD ipgMellatPGResponseControllerTestOLD;
    private IbanAccountCrudControllerTest ibanAccountCrudControllerTest;

    @BeforeEach
    public void init() {
        accountBusinessControllerTestOLD = new AccountBusinessControllerTest_OLD();
        contractBusinessControllerTestOLD = new ContractBusinessControllerTest_OLD();
        ipgMellatPGResponseControllerTestOLD = new IpgMellatPGResponseControllerTest_OLD();
        ibanAccountCrudControllerTest = new IbanAccountCrudControllerTest();

        accountBusinessControllerTestOLD.setMvc(mvc);
        accountBusinessControllerTestOLD.setUserBusinessService(userBusinessService);
        accountBusinessControllerTestOLD.setBookkeepingRequestCrudService(bookkeepingRequestCrudService);

        contractBusinessControllerTestOLD.setMvc(mvc);
        contractBusinessControllerTestOLD.setUserBusinessService(userBusinessService);
        contractBusinessControllerTestOLD.setContractRepository(contractRepository);

        ipgMellatPGResponseControllerTestOLD.setMvc(mvc);
        ipgMellatPGResponseControllerTestOLD.setUserBusinessService(userBusinessService);
        ipgMellatPGResponseControllerTestOLD.setContractRepository(contractRepository);

        ibanAccountCrudControllerTest.setContractRepository(contractRepository);
        ibanAccountCrudControllerTest.setUserBusinessService(userBusinessService);
        ibanAccountCrudControllerTest.setMvc(mvc);
    }


    @Test
    void contract_create_payment_delivery_settlement_scenario_1() throws Exception {
        contractBusinessControllerTestOLD.create();

        contractBusinessControllerTestOLD.ipgPaymentRequest_spec_contractId_spec_Amount();
        ipgMellatPGResponseControllerTestOLD.paymentSuccess_by_spec_user();

        contractBusinessControllerTestOLD.ipgPaymentRequest_spec_contractId_spec_Amount();
        ipgMellatPGResponseControllerTestOLD.paymentSuccess_by_spec_user();

        contractBusinessControllerTestOLD.goodsDelivery();
        contractBusinessControllerTestOLD.settlement();
    }

    @Test
    void account_recharge_contract_account_payment_scenario_2() throws Exception {
//        ibanAccountCrudControllerTest.create();
        contractBusinessControllerTestOLD.create(); /*create contract*/
        accountBusinessControllerTestOLD.ipgRecharge_specUserSpecAmount();  /*recharge account in ipg*/
        ipgMellatPGResponseControllerTestOLD.paymentSuccess_by_spec_user(); /*success ipg response*/
        contractBusinessControllerTestOLD.accountPayment(); /*contract payment from account*/
        contractBusinessControllerTestOLD.goodsDelivery();
        contractBusinessControllerTestOLD.settlement();
//        accountBusinessControllerTestOLD.withdrawalAccountAndDepositToIban();
    }

    @Test
    void contract_create_payment_delivery_settlement_scenario_3() throws Exception {
        contractBusinessControllerTestOLD.create();
        contractBusinessControllerTestOLD.ipgPaymentRequest_spec_contractId_spec_Amount(); /*payment-1*/
        ipgMellatPGResponseControllerTestOLD.paymentSuccess_by_spec_user();
        contractBusinessControllerTestOLD.ipgPaymentRequest_spec_contractId_spec_Amount(); /*payment-2*/
        ipgMellatPGResponseControllerTestOLD.paymentSuccess_by_spec_user();
        contractBusinessControllerTestOLD.ipgPaymentRequest_spec_contractId_spec_Amount(); /*payment-3*/
        ipgMellatPGResponseControllerTestOLD.paymentSuccess_by_spec_user();
        contractBusinessControllerTestOLD.ipgPaymentRequest_spec_contractId_spec_Amount(); /*payment-4*/
        ipgMellatPGResponseControllerTestOLD.paymentSuccess_by_spec_user();
        contractBusinessControllerTestOLD.ipgPaymentRequest_spec_contractId_spec_Amount(); /*payment-5*/
        ipgMellatPGResponseControllerTestOLD.paymentSuccess_by_spec_user();
        contractBusinessControllerTestOLD.ipgPaymentRequest_spec_contractId_spec_Amount(); /*payment-6*/
        ipgMellatPGResponseControllerTestOLD.paymentSuccess_by_spec_user();
        contractBusinessControllerTestOLD.goodsDelivery();
        contractBusinessControllerTestOLD.settlement();
    }

}