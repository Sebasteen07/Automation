package com.medfusion.pages.provisioning;

import com.medfusion.factory.pojos.provisioning.Merchant;

/**
 * Created by lubson on 29.12.15.
 */
public interface ProvisioningSectionForm {

    void scrollUp();

    void fillInForm(Merchant merchant);

    void getFormData(Merchant merchant);

    ProvisioningMerchantDetailPage save();

    ProvisioningMerchantDetailPage cancel();
}
