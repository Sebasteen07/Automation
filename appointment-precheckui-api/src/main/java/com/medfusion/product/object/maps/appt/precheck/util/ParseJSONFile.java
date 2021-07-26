// Copyright 2020 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.appt.precheck.util;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class ParseJSONFile {

	public static String parseObject(JSONObject json, String key) {

		System.out.println("Value of " + key + "- " + json.get(key));
		String KEY = (String) json.get(key);
		return KEY;
	}

	public static void getKey(JSONObject json, String key) {

		boolean exists = json.has(key);
		Iterator<?> keys;
		String nextkey;

		if (!exists) {

			keys = json.keys();
			while (keys.hasNext()) {
				nextkey = (String) keys.next();
				try {

					if (json.get(nextkey) instanceof JSONObject) {
						if (exists == false) {
							getKey(json.getJSONObject(nextkey), key);
						}

					} else if (json.get(nextkey) instanceof JSONArray) {

						JSONArray jarr = json.getJSONArray(nextkey);
						for (int i = 0; i < jarr.length(); i++) {

							String jsonString = jarr.getJSONObject(i).toString();
							JSONObject innerobject = new JSONObject(jsonString);
							if (exists == false) {
								getKey(innerobject, key);
							}
						}
					}

				} catch (Exception e) {
					System.out.println("Exception occured");
				}
			}

		} else {
			ParseJSONFile.parseObject(json, key);
		}

	}

}
