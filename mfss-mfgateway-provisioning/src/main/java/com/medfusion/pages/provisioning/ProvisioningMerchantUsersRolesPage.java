package com.medfusion.pages.provisioning;

import com.medfusion.factory.pojos.provisioning.UserRoles;
import com.medfusion.pages.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by lubson on 16.01.16.
 */
public class ProvisioningMerchantUsersRolesPage extends BasePage {

    @FindBy(how = How.XPATH, using = "//button[@data-ng-click='openDialog()']")
    public WebElement openAddUserForm;

    @FindBy(how = How.XPATH, using = "//button[@data-ng-click='cancelEdit()']")
    public WebElement cancelButton;

    @FindBy(how = How.ID, using = "practicestaffId")
    public WebElement userId;

    @FindBy(how = How.ID, using = "userName")
    public WebElement userName;

    @FindBy(how = How.ID, using = "role_VOIDREFUND")
    public WebElement voidRefundRole;

    @FindBy(how = How.ID, using = "role_POINTOFSALE")
    public WebElement poinOfSaleRole;

    @FindBy(how = How.ID, using = "role_REPORTING")
    public WebElement reportingRole;

    @FindBy(how = How.XPATH, using = "//button[@data-ng-click='addUserRoles()']")
    public WebElement saverUser;

    public ProvisioningMerchantUsersRolesPage(WebDriver webDriver) {
        super(webDriver);
    }

    public void openUserForm(){
        click(openAddUserForm);
    }

    public void openUserForm(UserRoles userRoles){
        sleep(500);
        WebElement updateButton = webDriver.findElement(By.xpath("(//*[@practicestaff-id='" + userRoles.userId + "']/button)[1]"));
        click(updateButton);
    }

    public void fillInUser(UserRoles userRoles) {
        sleep(500);
        if (userId.isEnabled()) {
            setText(userId,userRoles.userId);
            setText(userName,userRoles.userName);
        }
        setText(voidRefundRole, getExpectedValueForRole(voidRefundRole.getAttribute("id"),userRoles.getRoles()));
        setText(poinOfSaleRole, getExpectedValueForRole(poinOfSaleRole.getAttribute("id"),userRoles.getRoles()));
        setText(reportingRole, getExpectedValueForRole(reportingRole.getAttribute("id"),userRoles.getRoles()));
    }

    public void saveUserForm(){
        click(saverUser);
        sleep(500);
    }

    public void removeUser(UserRoles userRoles){
        sleep(500);
        WebElement removeButton = webDriver.findElement(By.xpath("(//*[@practicestaff-id='" + userRoles.userId + "']/button)[2]"));
        click(removeButton);
        sleep(500);
        WebElement confirmRemoveButton = webDriver.findElement(By.xpath("//div[contains(@class,'modal-dialog')]//button[@data-ng-click='remove()']"));
        click(confirmRemoveButton);
    }

    private String getExpectedValueForRole(String elementId, Set<String> roles){
        String result = "false";

        for (String role : roles) {
            if (elementId.contains(role)) {
                result = "true";
            }
        }
        return result;
    }

    public List<UserRoles> getAllUserRoles() {
        sleep(1500);
        List<UserRoles> userRolesList = new LinkedList<UserRoles>();
        List<WebElement> rows = webDriver.findElements(By.xpath("//table/tbody/tr"));

        for (WebElement row : rows) {
            UserRoles userRoles = new UserRoles();
            userRoles.userId = row.findElement(By.xpath("./td[1]")).getText();
            userRoles.userName = row.findElement(By.xpath("./td[2]")).getText();
            String extractedRoles = row.findElement(By.xpath("./td[3]")).getText();
            extractedRoles = extractedRoles.replaceAll("/","");
            extractedRoles = extractedRoles.replaceAll("-","");
            userRoles.setRoles(extractedRoles);
            userRolesList.add(userRoles);
        }
        return userRolesList;
    }

    public ProvisioningMerchantDetailPage closeUserRoles() {
        click(cancelButton);
        return PageFactory.initElements(webDriver, ProvisioningMerchantDetailPage.class);
    }
}
