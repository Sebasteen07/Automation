package com.medfusion.product.practice.api.pojo;

public class Practice {

    public String url = "";
    public String username = "";
    public String password = "";
    public String formUser = "";
    public String formPassword = "";
    public String patientUser = "";
    public String patientEmail = "";
    public String patientPassword = "";
    public String payPalDoctor = "";
    public String payPalPassword = "";

    public Practice() {

    }

    public Practice(String login, String password, String url) {
        this.username = login;
        this.password = password;
        this.url = url;
    }

    public String toString() {
        return "Practice info > " + username + ", " + password + ", " + url;
    }
}
