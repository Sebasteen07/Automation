// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package com.ng.product.integrationplatform.apiUtils;

/************************
 * 
 * @author Narora
 * 
 ************************/

import org.apache.commons.collections.map.MultiValueMap;

import com.intuit.ifs.csscat.core.utils.Log4jUtil;

import java.net.URISyntaxException;
import java.util.*;

public class FinalSignature {

	public static String CanonicalURI(String uri)
	{
		if (uri == null /*&& uri.isEmpty()*/)
		{
			return "/";
		}

		java.net.URI url = null;
		try 
		{
			url = new java.net.URI(uri);
		} catch (URISyntaxException e) {
			Log4jUtil.log(e.getMessage());
		}
		String canonicalURI = url.getPath().replace("{", "%7B")
				.replace("}", "%7D")
				.replace("!", "%21")
				.replace("@", "%40")
				.replace("#", "%23")
				.replace("$", "%24")
				.replace("^", "%5E")
				.replace("*", "%2A")
				.replace("(", "%28")
				.replace(")", "%29")
				.replace("+", "%20")
				.replace(",", "%2C");
		
		System.out.println("Canonical URI:"+canonicalURI);

		if (canonicalURI.startsWith("/"))
			return canonicalURI;
		else
			return "/" + canonicalURI;
	}

	public static String CanonicalQueryString(String queryParams)
	{
		try
		{
			if(queryParams.length() <= 0)
			{
				return "";

			}
			else
			{
				queryParams = queryParams.substring(1, queryParams.length());
				//Make Microsoft RFC 2396 compliant
				queryParams = queryParams.replace("'", "%27");
				queryParams = queryParams.replace(":", "%3A");
				queryParams = queryParams.replace("@", "%40");
				queryParams = queryParams.replace("$", "%24");
				queryParams = queryParams.replace(",", "%2C");
				queryParams = queryParams.replace("+", "%20");
				queryParams = queryParams.replace(" ", "%20");

				MultiValueMap map = new MultiValueMap();

				String[] pairs = queryParams.split("&");
				String[] parts;

				for (String pair : pairs)
				{
					parts = pair.split("=");
					
					if(parts.length > 1)
					{
						String key = parts[0].trim().replace("=", "");
						String value = parts[1];
						map.put(key, value);
					}
					else
					{
						String key = parts[0];
						String value = "";
						map.put(key, value);
					}
				}

				@SuppressWarnings("unchecked")
				Map<String, Object> treeMap = new TreeMap<String, Object>(map);

				//build the query string from sorted parameters
				StringBuilder sb_QueryString = new StringBuilder();
				Set<?> entrySet = treeMap.entrySet();
				Iterator<?> it = entrySet.iterator();
				while (it.hasNext()) 
				{
					@SuppressWarnings("rawtypes")
					Map.Entry mapEntry = (Map.Entry) it.next();
					List<?> list = (List<?>) map.get(mapEntry.getKey());
					for (int j = 0; j < list.size(); j++) 
					{
						if(sb_QueryString.length() > 0)
							sb_QueryString.append("&");
						sb_QueryString.append(mapEntry.getKey());
						sb_QueryString.append("=");
						if (list.get(j) != null && !list.get(j).toString().isEmpty())
						{
							sb_QueryString.append(list.get(j));
						}
					}
				}
				System.out.println("CanonicalQueryString:"+sb_QueryString.toString());
				return sb_QueryString.toString();
			}
		}
		catch (Exception ex)
		{
			Log4jUtil.log(ex.getMessage());
		}
		return null;
	}
}
