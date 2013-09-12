package com.intuit.ihg.product.apiehcore.page;


import java.net.HttpURLConnection;
import org.apache.log4j.Logger;
import com.intuit.ihg.eh.core.dto.DataJob;
import com.intuit.ihg.product.apiehcore.utils.EhcoreAPIConstants;
import com.intuit.ihg.product.apiehcore.utils.EhcoreAPIUtil;



public class DataJobIDPage {
	private static final Logger logger = Logger.getLogger(DataJobIDPage.class);
	private static DataJob dj = null;
	public static boolean isProcStatusAsExpected = false;
	public static String actualStatus = null;
	public static String djStatus = null;
	public static String expectedProcStatus;
	public static String xmlFile;
	public static String response="";

	public static HttpURLConnection conn = null;;

	public static DataJob openDataJob(String transStatus) throws Exception {
		
		String url = EhcoreAPIUtil.getUrl(EhcoreAPIConstants.URL_DATAJOB);

		String fromXML = EhcoreAPIConstants.DATA_JOB_INPUT + "OpenDataJob_template.xml";
		String toXML = EhcoreAPIConstants.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml";
		//update xml 
		EhcoreAPIUtil.updateOpenDataJobXml(fromXML, transStatus,toXML);

		DataJob dj = EhcoreAPIUtil.processRequest(url, EhcoreAPIConstants.POST_REQUEST,toXML,EhcoreAPIConstants.EXPECTEDRESPONSEMESSAGE_DATAJOB);
		
		return dj;
	}


	





}