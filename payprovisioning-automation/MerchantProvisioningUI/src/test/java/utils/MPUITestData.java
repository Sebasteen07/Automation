package utils;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import org.testng.annotations.DataProvider;
import provisioningtests.ProvisioningBaseTest;

import java.io.IOException;

public class MPUITestData extends ProvisioningBaseTest {

    @DataProvider(name = "edit_settlement_type")
    public static Object[][] dpMethod() throws IOException {
        testData = new PropertyFileLoader();
        return new Object[][] {

                { testData.getProperty("settlement.type.monthly") },

        };

    }

    @DataProvider(name = "edit_general_merchant_info")
    public static Object[][] dpGeneralMerchantInfo() throws IOException {
        testData = new PropertyFileLoader();
        return new Object[][] {

                { "NEXTGEN" + IHGUtil.createRandomNumericString(3), IHGUtil.createRandomNumericString(5),
                        IHGUtil.createRandomNumericString(5), IHGUtil.createRandomNumericString(10)},

        };

    }
}
