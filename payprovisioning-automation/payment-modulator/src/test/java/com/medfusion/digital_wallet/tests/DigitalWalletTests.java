// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.digital_wallet.tests;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.digital_wallet.helpers.DigitalWalletResource;
import com.medfusion.gateway_proxy.utils.DBUtils;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DigitalWalletTests extends DigitalWalletBaseTest{

    protected static PropertyFileLoader testData;

    @BeforeTest
    public void setUp() throws Exception{
        testData = new PropertyFileLoader();
        setupRequestSpecBuilder();
        setupResponsetSpecBuilder();
    }

    @Test
    public void getCountOfExpiringCards() throws Throwable {

        DigitalWalletResource digitalWallet = new DigitalWalletResource();
        String fromMonth = (testData.getProperty("from.month")).substring(5,7) + (testData.getProperty("from.month")).substring(2,4);
        String toMonth = (testData.getProperty("to.month")).substring(5,7) + (testData.getProperty("to.month")).substring(2,4);
        Response response = digitalWallet.getCountOfExpiringCards(fromMonth, toMonth);

        JsonPath jsonPath = new JsonPath(response.asString());
        String totalCardsInDb = DBUtils.executeQueryOnDB("pay_walt","SELECT COUNT(wallet_id) FROM public.wallet_card;");
        String totalCardsExpiringInGivenDuration = DBUtils.executeQueryOnDB("pay_walt","SELECT COUNT(wallet_id) FROM public.wallet_card where card_expiry_dt >= '"+testData.getProperty("from.month")+"' and card_expiry_dt <= '"+testData.getProperty("to.month")+"';");

        Assert.assertNotNull(jsonPath.get("totalCardsInSystem"),"totalCardsInSystem is null");
        Assert.assertEquals(Integer.parseInt(totalCardsInDb), jsonPath.get("totalCardsInSystem"));
        Assert.assertEquals(Integer.parseInt(totalCardsExpiringInGivenDuration), jsonPath.get("totalCardsExpiringInPeriod"));

    }
}
