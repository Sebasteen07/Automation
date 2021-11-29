package com.medfusion.teststeps;

import com.medfusion.factory.pojos.provisioning.Merchant;
import com.medfusion.factory.pojos.provisioning.UserRoles;
import com.medfusion.pages.provisioning.ProvisioningMerchantDetailPage;
import com.medfusion.pages.provisioning.ProvisioningMerchantSearchPage;
import com.medfusion.pages.provisioning.ProvisioningMerchantUsersRolesPage;
import com.medfusion.pages.provisioning.ProvisioningSectionForm;
import com.medfusion.pages.provisioning.partials.ProvisioningMerchantGeneralInfoForm;
import com.medfusion.pages.provisioning.partials.Section;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import java.util.List;

/**
 * Created by lubson on 23.12.15.
 */
public class MerchantSteps extends BaseSteps {

    private ProvisioningMerchantSearchPage merchantSearchPage;
    private ProvisioningMerchantDetailPage merchantDetailPage;
    private ProvisioningMerchantGeneralInfoForm addNewMerchantPage;
    private ProvisioningMerchantUsersRolesPage usersRolesPage;

    public MerchantSteps(WebDriver webDriver, Logger logger) {
        super(webDriver, logger);
    }

    public void setMerchantSearchPage(ProvisioningMerchantSearchPage merchantSearchPage) {
        this.merchantSearchPage = merchantSearchPage;
    }

    public void searchMerchant(String query) {
        logger.info("Search for merchant with query:'" + query + "'");
        merchantSearchPage.searchMerchant(query);
    }

    public void openMerchantDetail(String mmid) {
        logger.info("Clicking on 'View Detail' button of merchant with MMID: " + mmid);
        merchantDetailPage = merchantSearchPage.openMerchant(mmid);
    }

    public ProvisioningSectionForm openMerchantSection(Section section){
        logger.info("Opening merchant " + section + " section");
        ProvisioningSectionForm sectionPage = merchantDetailPage.openSection(section);
        return sectionPage;
    }

    public void goToMerchantDetailViaUrl(String mmid) {
        logger.info("Opening merchant detail with '" + mmid + "' via link");
        merchantDetailPage = new ProvisioningMerchantDetailPage(webDriver, mmid);
    }

    public void backToSearchPage(){
        logger.info("Clicking Back button.");
        merchantSearchPage =  merchantDetailPage.back();
    }

    public void openAddNewMerchantPage(){
        logger.info("Click on 'Add New Merchant' button");
        addNewMerchantPage = merchantSearchPage.openAddNewMerchantPage();
    }

    public void saveNewMerchant(Merchant merchant){
        logger.info("Filling General Merchant Info form with merchant name: " + merchant.name);
        addNewMerchantPage.fillInForm(merchant);
        logger.info("Saving Merchant");
        merchantDetailPage = addNewMerchantPage.save();
    }


    public void updateMerchant(Merchant merchant, Section[] sections) {
        for (Section section: sections) {
            ProvisioningSectionForm sectionPage = openMerchantSection(section);
            sectionPage.fillInForm(merchant);
            sectionPage.save();
        }
    }

    public Merchant getDisplayedMerchant() {
        return merchantDetailPage.getDisplayedMerchant();
    }

    public Merchant getMerchant(String mmid) {
        goToMerchantDetailViaUrl(mmid);
        return merchantDetailPage.getDisplayedMerchant();
    }

    public void addNewUserRoles(UserRoles userRoles){
    	logger.info("Add new user role");
        usersRolesPage.openUserForm();
        usersRolesPage.fillInUser(userRoles);
        usersRolesPage.saveUserForm();
    }

    public void updateUserRoles(UserRoles userRoles) {
    	logger.info("Update existng User role");
        usersRolesPage.openUserForm(userRoles);
        usersRolesPage.fillInUser(userRoles);
        usersRolesPage.saveUserForm();
    }

    public void removeUserRoles(UserRoles userRoles) {
    	logger.info("Remove User role");
        usersRolesPage.removeUser(userRoles);
    }

    public void openUserRolesSection(){
        usersRolesPage = merchantDetailPage.openUserRolesSection();
    }

    public List<UserRoles> getAllUserRoles() {
        return usersRolesPage.getAllUserRoles();
    }

    public boolean resultContainsMerchant(String mmid) {
        return (merchantSearchPage.getRowWithMerchant(mmid) == null)?false:true;
    }

    public void createNewMerchant(Merchant merchant){
        openAddNewMerchantPage();
        saveNewMerchant(merchant);
    }

    public Merchant getSearchedMerchant(String query, String mmid){
        searchMerchant(query);
        openMerchantDetail(mmid);
        return getDisplayedMerchant();
    }


}
