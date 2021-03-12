package helpers;

import static io.restassured.path.json.JsonPath.from;
import io.restassured.response.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.testng.Assert;

import com.jayway.restassured.path.json.JsonPath;
import com.medfusion.common.utils.PropertyFileLoader;

public class Validations {
	
	protected static PropertyFileLoader testData;
	
	
	public void verifyMerchantDetailsOnUpdate(String merchantdetails) throws IOException {
		   testData = new PropertyFileLoader();
		   JsonPath jsonpath = new JsonPath(merchantdetails);
		   Assert.assertNotNull(jsonpath, "Response was null");
		   Assert.assertEquals(jsonpath.get("merchantName"), (testData.getProperty("merchantnameupdate")));
		   Assert.assertEquals(jsonpath.get("externalMerchantId"), Integer.parseInt((testData.getProperty("externalmerchantidupdate"))));
		   Assert.assertEquals(jsonpath.get("maxTransactionLimit"), Integer.parseInt((testData.getProperty("transactionlimitupdate"))));
		   Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.websiteURL"), (testData.getProperty("websiteurlupdate")));
		   Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.mccCode"), (testData.getProperty("mcccodeupdate")));
			
	   }
		


	   public void verifyMerchantDetails(String merchantdetails) throws IOException {
		   testData = new PropertyFileLoader();
		   JsonPath jsonpath = new JsonPath(merchantdetails);
		   Assert.assertNotNull(jsonpath, "Response was null");
		   Assert.assertEquals(jsonpath.get("merchantName"), (testData.getProperty("merchantname")));
		   Assert.assertEquals(jsonpath.get("doingBusinessAs"), (testData.getProperty("doingbusinessas")));
		   Assert.assertEquals(jsonpath.get("externalMerchantId"), Integer.parseInt((testData.getProperty("externalmerchantid"))));
		   Assert.assertEquals(jsonpath.get("maxTransactionLimit"), Integer.parseInt((testData.getProperty("transactionlimit"))));
		   Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.elementAccountId"), (testData.getProperty("elementaccountid")));
		   Assert.assertNotNull(jsonpath.get("accountDetails.worldPayAccountDetails.elementAcceptorId"), "Acceptor id was null");
		   Assert.assertNotNull(jsonpath.get("accountDetails.worldPayAccountDetails.elementTerminalId"), "Element Terminal id was null");
		   Assert.assertEquals(jsonpath.get("accountDetails.worldPayAccountDetails.elementAccountToken"), (testData.getProperty("elementaccounttoken")));
		   Assert.assertNotNull(jsonpath.get("accountDetails.worldPayAccountDetails.vantivIbmMid"), "IBM Mid was null");
		   Assert.assertNotNull(jsonpath.get("accountDetails.worldPayAccountDetails.subMerchantId"), "Submerchant id was null");
		
	   }



	    public static void validateStaffUser(String staffusername,String practicestaffid, String response) throws IOException {
	       testData = new PropertyFileLoader();
	       JsonPath jsonpath = new JsonPath(response);
	       Assert.assertNotNull(jsonpath, "Response was null.Adding user was not successful");
	       Assert.assertEquals(jsonpath.get("practicestaffId").toString(), (testData.getProperty("practicestaffid")));
	       Assert.assertEquals(jsonpath.get("userName"), (testData.getProperty("staffusername")));
			
		
	}

}
