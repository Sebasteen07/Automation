package com.medfusion.factory.pojos.provisioning;

import org.openqa.selenium.internal.Base64Encoder;

/**
 * Created by lubson on 01.12.15.
 */
public class User {

    private String username;

    private String password;

    private Role role;

    public String getExpectedMenuItems() {
        switch (role) {
            case FINANCE:
                return "[DASHBOARD, MERCHANTS, SEARCH_FOR_MERCHANT, ADD_NEW_MERCHANT, ACH, LEDGER, SETTLEMENT, FEES, FEE_SETUP]";
            case ADMIN:
                return "[DASHBOARD, MERCHANTS, SEARCH_FOR_MERCHANT]";
            case IMPLEMENTATION:
                return "[DASHBOARD, MERCHANTS, SEARCH_FOR_MERCHANT]";
            default:
                return "";
        }
    }

    public String getCredentialsEncodedInBase() {
        Base64Encoder encoder = new Base64Encoder();
        return "Basic " + encoder.encode((username + ":" + password).getBytes());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
