//  Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package net.medfusion.integrations.webservices.framework;


public final class ConfigError extends Error {
	private static final long serialVersionUID = 20081015L;

	public ConfigError(String message) {
		super(message);
	} 

	public ConfigError(String message, Throwable cause) {
		super(message, cause);
	} 
} 
