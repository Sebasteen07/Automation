// Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.payment_modulator.helpers;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.Map;

import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.payment_modulator.pojos.PayloadDetails;
import com.medfusion.payment_modulator.tests.BaseRest;

public class TransactionResourceDetails extends BaseRest {

	protected PropertyFileLoader testData;

	public Response makeAnAuthorize(String mmid, String transactionAmount, String accountNumber, String consumerName,
			String paymentSource, String cvv, String type, String cardNumber, String expiratioNumber, String bin,
			String zipCode, String lastName, String addresLine1, String city, String state, String firstName)
			throws Exception {

		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForAuthorizeSaleMap(transactionAmount,
				accountNumber, consumerName, paymentSource, cvv, type, cardNumber, expiratioNumber, bin, zipCode,
				lastName, addresLine1, city, state, firstName);

		Response response = given().spec(requestSpec).body(transactiondetails).when().post("authorize/" + mmid).then()
				.spec(responseSpec).and().extract().response();

		return response;
	}

	public Response makeACapture(String mmid, String transactionid, String orderid, String transactionAmount,
			String accountNumber, String consumerName, String paymentSource, String cvv, String type, String cardNumber,
			String expiratioNumber, String bin, String zipCode, String lastName, String addresLine1, String city,
			String state, String firstName) throws IOException {

		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForCaptureMap(transactionAmount,
				accountNumber, consumerName, paymentSource, cvv, type, cardNumber, expiratioNumber, bin, zipCode,
				lastName, addresLine1, city, state, firstName, transactionid, orderid);

		Response response = given().spec(requestSpec).body(transactiondetails).when().post("capture/" + mmid).then()
				.spec(responseSpec).and().extract().response();

		return response;
	}

	public Response makeASale(String mmid, String transactionAmount, String accountNumber, String consumerName,
			String paymentSource, String cvv, String type, String cardNumber, String expiratioNumber, String bin,
			String zipCode, String lastName, String addresLine1, String city, String state, String firstName) throws IOException {
		testData = new PropertyFileLoader();
		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForAuthorizeSaleMap(
				transactionAmount,
				accountNumber, consumerName, paymentSource, cvv, type, cardNumber, expiratioNumber, bin, zipCode,
				lastName, addresLine1, city, state, firstName);

		Response response = given().spec(requestSpec).body(transactiondetails).when().post("sale/" + mmid).then()
				.spec(responseSpec).and().extract().response();

		return response;

	}

	public Response makeAVoid(String mmid, String transactionid,String transactionAmount, String accountNumber, String consumerName,
			String paymentSource, String cvv, String type, String cardNumber, String expiratioNumber, String bin,
			String zipCode, String lastName, String addresLine1, String city, String state, String firstName) throws IOException {
		testData = new PropertyFileLoader();
		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForVoidCreditMap(
				transactionAmount,
						accountNumber, consumerName, paymentSource, cvv, type, cardNumber, expiratioNumber, bin, zipCode,
						lastName, addresLine1, city, state, firstName, transactionid);

		Response response = given().spec(requestSpec).body(transactiondetails).when().post("void/" + mmid).then()
				.spec(responseSpec).and().extract().response();

		
		return response;
	}

	public Response makeARefund (String mmid, String transactionid,String refundamount, String accountNumber, String consumerName,
			String paymentSource, String cvv, String type, String cardNumber, String expiratioNumber, String bin,
			String zipCode, String lastName, String addresLine1, String city, String state, String firstName   ) throws IOException {
		
		
		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForVoidCreditMap(
				refundamount,
						accountNumber, consumerName, paymentSource, cvv, type, cardNumber, expiratioNumber, bin, zipCode,
						lastName, addresLine1, city, state, firstName, transactionid);

		Response response = given().spec(requestSpec).body(transactiondetails).when().post("credit/" + mmid).then()
				.spec(responseSpec).and().extract().response();

		return response;

	}

	public Response makeAChargeback(String mmid, String transactionid, String orderid, String chargebackurl,String chargeBackAmount)
			throws IOException {
		testData = new PropertyFileLoader();
		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForChargeback(mmid, transactionid, orderid,
				chargeBackAmount);

		Response response = given().spec(requestSpec).body(transactiondetails).when()
				.post(chargebackurl + "merchant/" + mmid + "/chargeback").then().spec(responseSpec).and().extract()
				.response();

		return response;
	}

	
	public Response makeChargeBack  (String mmid, String transactionid, String orderid, String chargebackurl,String chargeBackAmount)
			throws IOException{
		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForChargeback(mmid, transactionid, orderid,
				chargeBackAmount);

		Response response = given().spec(requestSpec).body(transactiondetails).when()
				.post(chargebackurl + "merchant/" + mmid + "/chargeback").then().spec(responseSpec).and().extract()
				.response();

		return response;
	}

	public Response makeAECheckSale(String mmid, String transactionAmount, String accountNumber, String consumerName,
							  String paymentSource, String AccountType, String AccountNumber, String RoutingNumber,
									String AccountHolderFirstName, String AccountHolderLastName) throws IOException {
		testData = new PropertyFileLoader();
		Map<String, Object> transactiondetails = PayloadDetails.getPayloadForECheckSaleMap(
				transactionAmount, accountNumber, consumerName, paymentSource,
				AccountType, AccountNumber, RoutingNumber, AccountHolderFirstName, AccountHolderLastName);

		Response response = given().spec(requestSpec).body(transactiondetails).when().post("sale/" + mmid).then()
				.spec(responseSpec).and().extract().response();

		return response;

	}

	public Response viewReceipt (String basev2url, String mmid, String transactionid, String orderid) {
		Response response = given().when()
				.get(basev2url + "merchant/" + mmid + "/receipt/" + transactionid + "/order/" + orderid)
				.then().spec(responseSpec).and().log().all().extract()
				.response();

		return response;
	}

	public Response saleUsingSavedCard(String mmid,
									   String accountNumber,
									   String consumerFName, String consumerLName, String source,
									   String paymentMethodId, int transactionAmount) throws Exception {
		testData = new PropertyFileLoader();
		Map<String, Object> digitalWallet = PayloadDetails.getPayloadForDigitalWalletSale(accountNumber, consumerFName, consumerLName, source,
				paymentMethodId, transactionAmount);

		Response response = given().spec(requestSpec).body(digitalWallet).when()
				.post("sale/" + mmid).then().log().all().extract().response();

		return response;
	}

}