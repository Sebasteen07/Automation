// Copyright 2022 NXGN Management, LLC. All Rights Reserved.
package utils;

public enum Role {
	
	 FINANCE, ADMIN, IMPLEMENTATION;
	 
	
	 
	 public static Role getRole(String role) {
	        if (role.equalsIgnoreCase("FINANCE"))
	            return Role.FINANCE;
	        if (role.equalsIgnoreCase("IMPLEMENTATION"))
	            return Role.IMPLEMENTATION;
	        else if (role.equalsIgnoreCase("ADMIN"))
	            return Role.ADMIN;
	        else
	            return Role.FINANCE;
	    }


}
