package com.medfusion.funding.tests;

import com.intuit.ifs.csscat.core.BaseTestNG;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.funding.pojo.Reports;
import com.medfusion.funding.service.FundingUtils;
import com.medfusion.funding.service.IBFService;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.*;

public class IBFTests extends BaseTestNG {
    protected static PropertyFileLoader testData;
    private IBFService flows = new IBFService();
    String url;

    @BeforeTest
    public void setUp() throws Exception {
        testData = new PropertyFileLoader();
        url = testData.getProperty("ibfreporting.base.url");
    }

    @Test(enabled = true)
    public void testGetReportsForFinancialYear() throws Exception {
        FundingUtils fundingUtils = new FundingUtils();
        Response response =
                fundingUtils.getReportsYearly(url + "/yearly", testData.getProperty("financial.year.start"),
                        testData.getProperty("financial.year.end"));
        JsonPath jsonPath = new JsonPath(response.asString());
        List<Reports> listOfReportsPerCustomers = jsonPath.getList(".", Reports.class);

        for(Reports report: listOfReportsPerCustomers){
            flows.verifyGetReportResponse(report);
        }
    }

    @Test(enabled = true)
    public void testGetReportsForQuarterlyYear() throws Exception {
        FundingUtils fundingUtils = new FundingUtils();
        Response response =
                fundingUtils.getReportsQuarterly(url + "/quarterly", testData.getProperty("financial.quarter"),
                        testData.getProperty("financial.year.start"), testData.getProperty("financial.year.end"));
        JsonPath jsonPath = new JsonPath(response.asString());
        List<Reports> listOfReportsPerCustomers = jsonPath.getList(".", Reports.class);

        for(Reports report: listOfReportsPerCustomers){
            flows.verifyGetReportResponse(report);
        }
    }

    @Test(enabled = true)
    public void testGetMontlyReports() throws Exception {
        FundingUtils fundingUtils = new FundingUtils();
        Response response =
                fundingUtils.getReportsMonthly(url + "/monthly", testData.getProperty("month"), testData.getProperty("financial.year.start"));
        JsonPath jsonPath = new JsonPath(response.asString());
        List<Reports> listOfReportsForCustomers = jsonPath.getList(".", Reports.class);

        for(Reports report: listOfReportsForCustomers){
            flows.verifyGetReportResponse(report);
        }
    }

}
