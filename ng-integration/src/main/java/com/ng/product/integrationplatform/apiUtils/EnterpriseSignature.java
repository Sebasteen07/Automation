// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.apiUtils;

/************************
 * 
 * @author Narora
 * 
 ************************/

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class EnterpriseSignature {
    public static String requestData;
    public static String NGTime;

    @SuppressWarnings("deprecation")
    public static String SignatureGeneration(String username, String password, String email, String finalURl,
                                             String body, String queryString, String requestMethod) {
        try {
        	String timezone="UTC";
        	String dateFormat ="yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
			String passwordHash = new String();
			passwordHash = sha1(username + "nghash" + password);
			body = new String(Hex.encodeHex(DigestUtils.md5(body))).toUpperCase();
			queryString = FinalSignature.CanonicalQueryString(queryString);
			queryString = new String(Hex.encodeHex(DigestUtils.md5(queryString))).toUpperCase();
			String url = FinalSignature.CanonicalURI(finalURl);
			TimeZone tz = TimeZone.getTimeZone(timezone);
			DateFormat df = new SimpleDateFormat(dateFormat); 
			df.setTimeZone(tz);
			NGTime = df.format(new Date());

			requestData = email.toLowerCase() + NGTime + "NEXTGEN-AMB-API-V2" + requestMethod + url + queryString
					+ body;

			requestData = new String(
					Hex.encodeHex(HmacUtils.hmacSha256(passwordHash.getBytes("UTF-8"), requestData.getBytes("UTF-8"))));
			System.out.println(requestData);
		} catch (Exception e) {
            e.printStackTrace();
        }

        return requestData;

    }
    
    public static String sha1(String strPrePasswordHash) throws NoSuchAlgorithmException {

        MessageDigest mDigest = MessageDigest.getInstance("SHA1");
        byte[] result = mDigest.digest(strPrePasswordHash.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; ++i) {

            sb.append(Integer.toHexString(result[i] & 0xFF));
        }
        return sb.toString().toUpperCase();
    }
}
