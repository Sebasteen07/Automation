package com.medfusion.factory;

import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.medfusion.dre.objects.Retriever;

public class RetrieversFactory {

	public static Retriever getRetriever(Map<String, String> map) {
		final ObjectMapper mapper = new ObjectMapper();
		Retriever retriever = mapper.convertValue(map, Retriever.class);

		return retriever;
	}
}
