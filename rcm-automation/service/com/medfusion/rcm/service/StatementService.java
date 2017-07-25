package com.medfusion.rcm.service;

import org.junit.Assert;

import com.intuit.ihg.common.utils.WebPoster;
import com.medfusion.common.utils.IHGUtil;
import com.medfusion.common.utils.PropertyFileLoader;
import com.medfusion.product.practice.api.pojo.PatientInfo;

public class StatementService {

    public void postStatementToPatientMfss(PropertyFileLoader testData, PatientInfo patInfo, String stmtJSON) throws Exception {

        IHGUtil.PrintMethodName();

        WebPoster poster = new WebPoster();
                
        Assert.assertNotNull( 
                "### PropertyFileLoader loaded a null base statement mfss post url", 
                testData.getProperty("mfssPostEndpoint"));
        Assert.assertNotNull( 
                "### PatientInfo supplied a null memberId", 
                patInfo.memberId); 
        String url = testData.getProperty("mfssPostEndpoint") + patInfo.memberId + "/statements";
        poster.setServiceUrl(url);        
        poster.setContentType( "application/json;" );
        poster.addHeader( "requestId", "stmtstaticpost" );
        poster.addHeader( "Authentication-Type", "2wayssl" );        
        poster.setExpectedStatusCode( 200 );    // HTTP Status Code
        poster.postFromString(stmtJSON);
    }
    /**
     * 
     * @param testData
     * @param patInfo
     * @param stmtJSON
     * @throws Exception
     */
    public void postStatementToPatientMfis(PropertyFileLoader testData, PatientInfo patInfo, String stmtJSON) throws Exception {

        IHGUtil.PrintMethodName();

        WebPoster poster = new WebPoster();
                
        Assert.assertNotNull( 
                "### PropertyFileLoader loaded a null base statement mfis post url", 
                testData.getProperty("mfisPostEndpoint"));
        Assert.assertNotNull( 
                "### PatientInfo supplied a null memberId", 
                patInfo.memberId); 
        String url = testData.getProperty("mfisPostEndpoint") + patInfo.memberId + "/statements";
        poster.setServiceUrl(url);        
        poster.setContentType( "application/json;" );
        poster.addHeader( "requestId", "stmtstaticpost" );
        poster.addHeader( "Authentication-Type", "2wayssl" );        
        poster.setExpectedStatusCode( 200 );    // HTTP Status Code
        poster.postFromString(stmtJSON);
    }
    
}
