//  Copyright 2013-2021 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.mfpay.funding.qa.exception;

public class RestException extends Exception {

    private static final long serialVersionUID = 3178645800700071660L;
    private int statusCode;

    public RestException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
    
    public int getStatusCode() {
        return statusCode;
    }

}