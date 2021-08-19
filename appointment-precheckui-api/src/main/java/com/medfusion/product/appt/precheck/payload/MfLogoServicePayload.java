package com.medfusion.product.appt.precheck.payload;

public class MfLogoServicePayload {
	private static MfLogoServicePayload payload = new MfLogoServicePayload();

	private MfLogoServicePayload() {

	}

	public static MfLogoServicePayload getMfLogoServicePayload() {
		return payload;
	}
}
