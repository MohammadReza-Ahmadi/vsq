package com.vosouq.bookkeeping.controller.business.scenarios;

import com.vosouq.bookkeeping.controller.ipg.IpgMellatPGResponseControllerTest;
import org.junit.jupiter.api.Test;

public class BookkeepingBusinessScenariosTest extends IpgMellatPGResponseControllerTest {

    @Test
    void scenario_1() throws Exception {
//        ipgRecharge();
//        paymentSuccess();
//        create();
//        accountPayment();
        goodsDelivery();
        settlement();
//        withdrawalAccountAndDepositToIban();
    }


}