package com.medfusion.product.object.maps.pss2.page.util;

import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

public class ParseJSONFile {

	public static void parseObject(JSONObject json, String key) {

		System.out.println("Status of key present or not- " + json.has(key));
		System.out.println("Value of key- " + json.get(key));
	}

	public static void getKey(JSONObject json, String key) {

		boolean exists = json.has(key);
		Iterator<?> keys;
		String nextkey;

		if (!exists) {
			System.out.println("Next key is not present so coming in !exist block");
			keys = json.keys();
			while (keys.hasNext()) {
				nextkey = (String) keys.next();
				try {

					if (json.get(nextkey) instanceof JSONObject) {
						if (exists == false) {
							getKey(json.getJSONObject(nextkey), key);
						}

					} else if (json.get(nextkey) instanceof JSONArray) {
						System.out.println("I am in JSON ARRAY");
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
