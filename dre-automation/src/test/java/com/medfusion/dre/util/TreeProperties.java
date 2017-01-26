package com.medfusion.dre.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TreeProperties extends Properties {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TreeProperties() {
		super();
	}

	// TODO Do it in smart way
	public Map<String, String> getPropertySubtree(String key) {
		Map<String, String> subtree = new HashMap<String, String>();

		for (String propertyName : super.stringPropertyNames()) {
			if (propertyName.startsWith(key)) {
				subtree.put(propertyName.replace(key + ".", ""), getProperty(propertyName));
			}
		}

		return subtree;
	}
}
