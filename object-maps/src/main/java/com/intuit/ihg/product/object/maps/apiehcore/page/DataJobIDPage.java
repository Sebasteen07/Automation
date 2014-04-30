package com.intuit.ihg.product.object.maps.apiehcore.page;

import java.net.HttpURLConnection;
import com.intuit.ihg.eh.core.dto.DataJob;
import com.intuit.ihg.product.apiehcore.utils.EhcoreAPIUtil;
import com.intuit.ihg.product.apiehcore.utils.constants.DataJobConstant;
import com.intuit.ihg.product.apiehcore.utils.constants.EhcoreAPIConstants;

public class DataJobIDPage {
	
//	private static DataJob dj = null;
	public static boolean isProcStatusAsExpected = false;
	public static String actualStatus = null;
	public static String djStatus = null;
	public static String expectedProcStatus;
	public static String xmlFile;
	public static String response="";

	public static HttpURLConnection conn = null;;

	public static DataJob openDataJob(String transStatus) throws Exception {
		
		String url = EhcoreAPIUtil.getUrl(DataJobConstant.URL_DATAJOB);

		String fromXML = DataJobConstant.DATA_JOB_INPUT + "OpenDataJob_template.xml";
		String toXML = DataJobConstant.SAMPLE_DATA_JOB_INPUT + "testOpenDataJob.xml";
		//update xml 
		EhcoreAPIUtil.updateOpenDataJobXml(fromXML, transStatus,toXML);

		DataJob dj = EhcoreAPIUtil.processRequest(url, EhcoreAPIConstants.POST_REQUEST,toXML,DataJobConstant.EXPECTEDRESPONSEMESSAGE_DATAJOB);
		
		return dj;
	}
}