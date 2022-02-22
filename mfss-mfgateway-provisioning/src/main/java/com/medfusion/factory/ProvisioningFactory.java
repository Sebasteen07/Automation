package com.medfusion.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medfusion.factory.pojos.provisioning.Merchant;
import com.medfusion.factory.pojos.provisioning.Role;
import com.medfusion.factory.pojos.provisioning.User;
import com.medfusion.factory.pojos.provisioning.UserRoles;

import java.util.Map;
import java.util.Random;

/**
 * Created by lhrub on 26.11.2015.
 */
public class ProvisioningFactory {
    public static Merchant getMerchant(Map<String,String> map, Role role){
        final ObjectMapper mapper = new ObjectMapper();
        Merchant merchant = mapper.convertValue(map, Merchant.class);

        if (role == Role.ADMIN) {
            merchant.routingNumber = "";
            merchant.accountNumber = "";
            merchant.accountType = "";
            merchant.amexSid = "";
            merchant.federalTaxId = "";

            merchant.vantivCoreMid = "";
            merchant.vantivTid = "";
            merchant.vantivIbfMid = "";
            merchant.vantivPbfMid = "";

            merchant.elementAccountId = "";
            merchant.elementAcceptorId = "";

            merchant.qTierBoundary = "";
            merchant.mQTierBoundary = "";
            merchant.nQTierBoundary = "";
        } else if ( role == Role.IMPLEMENTATION) {
            merchant.routingNumber = "";
            merchant.accountNumber = getPublicCardFormat(merchant.accountNumber);
            merchant.accountType = "";

            merchant.vantivCoreMid = "";
            merchant.vantivIbfMid = "";
            merchant.vantivPbfMid = "";
        }
        return merchant;
    }


    public static void randomizeMerchantIdentifiers(Merchant merchant) {
        Random rand = new Random();
        int randomStamp =  rand.nextInt(899998) + 100000;

        merchant.mmid = "";
        merchant.name ="[Automation]NewTestMerchant" + randomStamp;
        merchant.externalId ="1111" + randomStamp;
        merchant.vantivPbfMid = "11" + randomStamp;
        merchant.vantivCoreMid = "1111111" + randomStamp;
        merchant.elementAcceptorId = "111" + randomStamp;
    }


    public static User getUser(Map<String,String> map, Role role){
        final ObjectMapper mapper = new ObjectMapper();
        User user = mapper.convertValue(map, User.class);
        user.setRole(role);
        return user;
    }

    private static String getPublicCardFormat(String cardNumber) {
        if (cardNumber.length() <= 4) {
            return cardNumber;
        } else {
            String asterisks = new String(new char[cardNumber.length()-4]).replace("\0", "*");
            String lastFourNumbers = cardNumber.substring(cardNumber.length() - 5, cardNumber.length() - 1);
            return asterisks + lastFourNumbers;
        }
    }

    public static UserRoles getUserRoles(Map<String,String> map) {
        final ObjectMapper mapper = new ObjectMapper();
        UserRoles userRoles = mapper.convertValue(map, UserRoles.class);
        return userRoles;
    }
}
