// Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.digital_wallet.helpers;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.digital_wallet.tests.DigitalWalletBaseTest;
import io.restassured.response.Response;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class DigitalWalletResource extends DigitalWalletBaseTest {

	protected PropertyFileLoader testData;

	public Response getCountOfExpiringCards(String token, String fromMonth, String toMonth) throws IOException {
		testData = new PropertyFileLoader();

		Response response = given().that().spec(requestSpec).header("Authorization", "Bearer " + token).when()
				.get("cards-to-expire-count?fromMonth=" + fromMonth + "&toMonth=" + toMonth).then().extract()
				.response();
		return response;
	}

	public Response getDetailsOfCards(String token) throws IOException {
		testData = new PropertyFileLoader();

		Response response = given().that().spec(requestSpec).header("Authorization", "Bearer" + token).when()
				.get("/customer/" + testData.getProperty("testpaycustomeruuid") + "/wallets/"
						+ testData.getProperty("externalWalletId") + "/cards/" + testData.getProperty("externalCardId"))
				.then().extract().response();

		return response;

	}

}
