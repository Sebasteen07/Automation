package com.medfusion.mfpay.merchant_provisioning.tests;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.mfpay.merchant_provisioning.helpers.MerchantInfo;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class InstaMedWorkflowsAsImplementationTests extends BaseRest{
    protected PropertyFileLoader testData;

    @BeforeTest
    public void setBaseUri() throws Exception{
        testData = new PropertyFileLoader();
        setupImplementationRequestSpecBuilder();
        setupResponsetSpecBuilder();
    }

    @Test
    public void testViewInstaMedMerchantAccountDetails(){
        MerchantInfo merchantinfo = new MerchantInfo();
        Response response = merchantinfo.getMerchantDetails(testData.getProperty("instamed.mmid"));
        JsonPath jsonPath = new JsonPath(response.asString());
        Assert.assertTrue(response.getStatusCode() == 200);

        Assert.assertEquals(jsonPath.get("accountDetails.instaMedAccountDetails.clientId"),
                "****************************e34f", "The clientId is partly masked for the Implementation user");
        Assert.assertEquals(jsonPath.get("accountDetails.instaMedAccountDetails.clientSecret"),
                "", "The clientId is partly masked for the Implementation user");
    }
}
