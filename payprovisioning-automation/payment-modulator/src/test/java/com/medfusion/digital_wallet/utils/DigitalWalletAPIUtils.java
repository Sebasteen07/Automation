// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.digital_wallet.utils;

import com.medfusion.common.utils.PropertyFileLoader;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang.StringUtils;

import static io.restassured.RestAssured.given;

public class DigitalWalletAPIUtils {

    protected static PropertyFileLoader testData;

    public void setUp() throws Exception{
        testData = new PropertyFileLoader();
        RestAssured.useRelaxedHTTPSValidation();

    }

    public static String getTokenForCustomer() throws Exception{
        testData = new PropertyFileLoader();
        RestAssured.useRelaxedHTTPSValidation();

        //For some reason it is adding a semicolon at the end of value we get from property file so chopping it
        String grant_type = StringUtils.chop(testData.getProperty("grant.type"));
        String client_id = StringUtils.chop(testData.getProperty("client.id.system"));
        String client_secret = StringUtils.chop(testData.getProperty("client.secret.system"));
        String systemToken = StringUtils.chop(testData.getProperty("system.token"));

        String body = String.format("grant_type=%s&client_id=%s&client_secret=%s", grant_type, client_id, client_secret);

        Response response = given().with().header("Content-Type", "application/x-www-form-urlencoded")
                .body(body).and().log().body()
                .when()
                .post(systemToken)
                .then().statusCode(200).and().extract().response();

        JsonPath jsonpath = new JsonPath(response.asString());
        return jsonpath.get("access_token").toString();
    }

}
