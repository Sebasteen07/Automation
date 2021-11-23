package com.medfusion.tests.provisioning;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.medfusion.factory.ProvisioningFactory;
import com.medfusion.factory.pojos.provisioning.ApiVersions;
import com.medfusion.factory.pojos.provisioning.Role;
import com.medfusion.factory.pojos.provisioning.User;
import com.medfusion.tests.BaseRestTest;
import com.medfusion.util.Data;

public class ChargebackRestTests extends BaseRestTest {

    private String baseUrl = Data.get("url") + "/provisioning-api/services/v";

    @Test
    public void createChargeback() throws InterruptedException {
        User financeUser = ProvisioningFactory.getUser(Data.getMapFor(Role.FINANCE.toString().toLowerCase()),
                Role.FINANCE);
        logger.info("Create a new chargeback");
        JSONObject chargeback = new JSONObject().put("parentExternalTransactionId", Data.get("chargeback.parentTID"));
        chargeback.put("chargebackAmount", Data.get("chargeback.amount"));
        String chargebackUrl = Data.get("url") + "/gateway-proxy/services/v1/merchant/" + Data.get("chargeback.mmid")
                + "/chargebacks";
        HttpPost httpPost = buildHttpPost(chargebackUrl, financeUser, chargeback);
        JSONObject response = new JSONObject(executeRequest(httpPost));
        String chargebackTID = response.getString("mfTransactionId");
        Assert.assertEquals(Data.get("chargeback.parentTID"), response.getString("parentExternalTransactionId"));
        Assert.assertEquals(Data.get("chargeback.amount"), String.valueOf(response.getInt("chargebackAmount")));
        Assert.assertEquals(Data.get("finance.username"), response.getString("chargebackIssuer"));
        Assert.assertEquals(0, response.getInt("chargebackFee"));
        Assert.assertNotNull(chargebackTID);
        Assert.assertTrue(response.isNull("chargebackDate"));
        Assert.assertTrue(response.isNull("status"));
    
        logger.info("Wait and check the chargeback history for the chargeback just created");
        Thread.sleep(10000);
        HttpGet httpGet = buildHttpGet(chargebackUrl, financeUser);
        JSONArray allChargebacks = new JSONArray(executeRequest(httpGet));
        boolean found = false;
        for (int i = 0; i != allChargebacks.length(); i++) {
            JSONObject chargebackEntity = allChargebacks.getJSONObject(i);
            if (chargebackEntity.getString("mfTransactionId").equals(chargebackTID)) {
                Assert.assertEquals(Data.get("chargeback.parentTID"),
                        chargebackEntity.getString("parentExternalTransactionId"));
                Assert.assertEquals(Data.get("chargeback.amount"),
                        String.valueOf(chargebackEntity.getInt("chargebackAmount")));
                Assert.assertEquals(0, chargebackEntity.getInt("chargebackFee"));
                Assert.assertEquals(Data.get("finance.username"), chargebackEntity.getString("chargebackIssuer"));
                Assert.assertEquals("PENDING", chargebackEntity.getString("status"));
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date today = new Date();
                Assert.assertEquals(dateFormat.format(today), chargebackEntity.getString("chargebackDate"));
                found = true;
                break;
            }
        }
        Assert.assertTrue("Chargeback not found in the chargeback history", found);
    }

    @Test
    public void setGetChargebackFee() {
        User financeUser = ProvisioningFactory.getUser(Data.getMapFor(Role.FINANCE.toString().toLowerCase()),
                Role.FINANCE);
        JSONObject expectedChargebackFee = new JSONObject().put("CHARGEBACK_FEE", Data.get("chargeback.fee"));
        logger.info("Set Global Chargeback fee");
        HttpPost httpPost = buildHttpPost(
                baseUrl + ApiVersions.GLOBAL_CONFIG_API_VERSION + "/application/1/globalconfig",
                financeUser, expectedChargebackFee);
        executeRequest(httpPost);
    
        logger.info("Get Global Chargeback fee");
        HttpGet httpGet = buildHttpGet(baseUrl + ApiVersions.GLOBAL_CONFIG_API_VERSION + "/application/1/globalconfig",
                financeUser);
        String chargebackFee = executeRequest(httpGet);
        JSONAssert.assertEquals(new JSONObject(chargebackFee), expectedChargebackFee, false);
    }

}
