//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.funding.qa.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medfusion.mfpay.funding.qa.exception.RestException;

public class FundingRestClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(FundingRestClient.class);
    private static final DateTimeFormatter DATETIME_FORMATTER = new DateTimeFormatterBuilder().appendPattern("yyyyMMdd")
            .toFormatter();
    private static final String FUNDING_PROPERTIES = "funding.properties";

    private Client client;
    private Properties properties;

    public FundingRestClient() throws FileNotFoundException, IOException {
        ResteasyClientBuilder clientBuilder = (ResteasyClientBuilder) ResteasyClientBuilder.newBuilder();
        client = clientBuilder.build();
        properties = new Properties();
        properties.load(new FileInputStream(FUNDING_PROPERTIES));
    }

    public int postEMafFile(String fileLocation, String fundingDate) throws FileNotFoundException {
        File file = new File(fileLocation);
        if (!file.exists()) {
            LOGGER.error("File at " + fileLocation + " does not exist");
            return 0;
        }
        String fileName = file.getName();
        if (StringUtils.isEmpty(fundingDate)) {
            LOGGER.info("No funding date provided, using today");
            fundingDate = LocalDate.now().format(DATETIME_FORMATTER);
        }
        MultivaluedMap<String, String> headers = new MultivaluedMapImpl<>();
        headers.put("content-disposition", Arrays.asList("filename=\"" + fileName + "\""));
        MultipartFormDataOutput output = new MultipartFormDataOutput();
        output.addFormData("emaf", new FileInputStream(file), MediaType.MULTIPART_FORM_DATA_TYPE);
        GenericEntity<MultipartFormDataOutput> genericEntity = new GenericEntity<MultipartFormDataOutput>(output,
                MultipartFormDataOutput.class);
        String uri = properties.getProperty("funding.url") + properties.getProperty("funding.emaf.nodwh");

        try {
            doPost(uri, null, genericEntity, MediaType.APPLICATION_JSON_TYPE, MediaType.MULTIPART_FORM_DATA_TYPE,
                    headers);
            return 202;
        } catch (RestException e) {
            LOGGER.error("Error posting emaf file to " + uri + " response code " + e.getStatusCode(), e);
            return e.getStatusCode();
        }
    }

    private <S, T> S doPost(String uri, Class<S> responseClazz, T requestObject, MediaType responseMediaType,
            MediaType requestMediaType, MultivaluedMap<String, String> headers) throws RestException {
        Entity<T> entity = Entity.entity(requestObject, requestMediaType);
        Builder builder = client.target(uri).request(responseMediaType);
        if (MapUtils.isNotEmpty(headers)) {
            addHeadersToBuilder(headers, builder);
        }
        Response response = builder.post(entity);
        try {
            if (response.getStatus() == 202) {
                return response.readEntity(responseClazz);
            }
            throw new RestException("Received non-accepted response code of " + response.getStatus(),
                    response.getStatus());
        } finally {
            response.close();
        }
    }

    private void addHeadersToBuilder(MultivaluedMap<String, String> headers, Builder builder) {
        for (Entry<String, List<String>> entry : headers.entrySet()) {
            List<String> headerValues = entry.getValue();
            for (Object obj : headerValues) {
                builder.header(entry.getKey(), obj);
            }
        }
    }
}
