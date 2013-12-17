package com.intuit.ihg.product.sitegen.page.discreteforms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenConstants;

/**
 * 
 * @author bbinisha
 * @Date : Dec 06
 * Description : Custom feature in Sitegen.
 */
	
public class CustomFormPage extends BasePageObject {

	
	@FindBy( xpath = ".//input[@name = 'custom_form_name']")
	private WebElement customFormNameFiled;

	@FindBy( xpath = ".//input[@id = 'custom_headingtitle_customfirst_section_0']")
	private WebElement firstSectionHeadingTitleFiled;
	
	@FindBy ( xpath = ".//select[@id='custom_itemtype_customfirst_section_0']")
	private WebElement firstSectionItemTypeDropDwn;
	
	@FindBy ( xpath = ".//ul/li[@data-section='customsecond_section']/a")
	private WebElement secondSection;
	
	@FindBy ( xpath = ".//ul/li[@data-section='customthird_section']/a")
	private WebElement thirdSection;
	
	@FindBy( xpath = ".//div[@class='configuration_section customsecond_section']//li[1]//a[@class='insert_item']")
	private WebElement SecondSectionInsertItemButton;
	
	@FindBy( xpath = ".//div[@class='configuration_section customthird_section']//li[1]//a[@class='insert_item']")
	private WebElement thirdSectionInsertItemButton;
		
	@FindBy( xpath = ".//textarea[@class = 'available_answers xxxxl']")
	private WebElement availableAnswersField;
	
	@FindBy( xpath = ".//a[@id = 'save_config_form']")
	private WebElement saveFormButton;

	
	//Constructor
	public CustomFormPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	/**
	 * Description : Rename the Custom Form Name to Unique name.
	 * @param uniqueName 
	 */
	public void renameCustomForm(String uniqueName) {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, customFormNameFiled);
		customFormNameFiled.clear();
		customFormNameFiled.sendKeys(uniqueName);
	}
	
	/**
	 * Description : Selecting Item type in the First Section First item.
	 * @param option
	 * @throws Exception
	 */
	public void selectFirstSectionItemType(String option) throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, firstSectionItemTypeDropDwn);
		Select itemType = new Select(firstSectionItemTypeDropDwn);
		itemType.selectByVisibleText(option);
		Thread.sleep(3000);
		
	}
	
	/**
	 * Description : Selecting Item type in the Second Section First item.
	 * @param option
	 * @throws Exception
	 */
	public void selectSecondSection1ItemType(String option) throws Exception {
		IHGUtil.PrintMethodName();
		WebElement item = driver.findElement(By.id("custom_itemtype_customsecond_section_0"));
		Select itemType = new Select(item);
		itemType.selectByVisibleText(option);
		Thread.sleep(3000);
		
	}
	
	/**
	 * Description : Selecting Item type in the Second Section Second item.
	 * @param option
	 * @throws Exception
	 */
	public void selectSecondSection2ItemType(String option) throws Exception {
		IHGUtil.PrintMethodName();
		WebElement item = driver.findElement(By.id("custom_itemtype_customsecond_section_1"));
		Select itemType = new Select(item);
		itemType.selectByVisibleText(option);
		Thread.sleep(3000);
		
	}
	
	/**
	 * Description : Selecting Item type in the third Section First item.
	 * @param option
	 * @throws Exception
	 */
	public void selectThirdSection1ItemType(String option) throws Exception {
		IHGUtil.PrintMethodName();
		WebElement item = driver.findElement(By.id("custom_itemtype_customthird_section_0"));
		Select itemType = new Select(item);
		itemType.selectByVisibleText(option);
		Thread.sleep(3000);
		
	}
		
	/**
	 * Description : Selecting Question type in the Second Section First item.
	 * @param option
	 * @throws Exception
	 */
	public void selectSecondSection1QuestionType(String option) throws Exception {
		IHGUtil.PrintMethodName();
		WebElement questionType = driver.findElement(By.id("custom_questiontype_customsecond_section_0"));
		Select questionTypedrpbox = new Select(questionType);
		Thread.sleep(3000);
		questionTypedrpbox.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Question type in the First Section second item.
	 * @param option
	 * @throws Exception
	 */
	public void selectSecondSection2QuestionType(String option) throws Exception {
		IHGUtil.PrintMethodName();
		WebElement questionType = driver.findElement(By.id("custom_questiontype_customsecond_section_1"));
		Select questionTypedrpbox = new Select(questionType);
		Thread.sleep(3000);
		questionTypedrpbox.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Question type in the First Section Third item.
	 * @param option
	 * @throws Exception
	 */
	public void selectSecondSection3QuestionType(String option) throws Exception {
		IHGUtil.PrintMethodName();
		WebElement questionType = driver.findElement(By.id("custom_questiontype_customsecond_section_2"));
		Select questionTypedrpbox = new Select(questionType);
		Thread.sleep(3000);
		questionTypedrpbox.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Question type in the First Section Fourth item.
	 * @param option
	 * @throws Exception
	 */
	public void selectSecondSection4QuestionType(String option) throws Exception {
		IHGUtil.PrintMethodName();
		WebElement questionType = driver.findElement(By.id("custom_questiontype_customsecond_section_3"));
		Select questionTypedrpbox = new Select(questionType);
		Thread.sleep(3000);
		questionTypedrpbox.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Question type in the third Section First item.
	 * @param option
	 * @throws Exception
	 */
	public void selectThirdSection1QuestionType(String option) throws Exception {
		IHGUtil.PrintMethodName();
		WebElement questionType = driver.findElement(By.id("custom_questiontype_customthird_section_0"));
		Select questionTypedrpbox = new Select(questionType);
		Thread.sleep(3000);
		questionTypedrpbox.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Question type in the third Section Second item.
	 * @param option
	 * @throws Exception
	 */
	public void selectThirdSection2QuestionType(String option) throws Exception {
		IHGUtil.PrintMethodName();
		WebElement questionType = driver.findElement(By.id("custom_questiontype_customthird_section_1"));
		Select questionTypedrpbox = new Select(questionType);
		Thread.sleep(3000);
		questionTypedrpbox.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Question type in the third Section Third item.
	 * @param option
	 * @throws Exception
	 */
	public void selectThirdSection3QuestionType(String option) throws Exception {
		IHGUtil.PrintMethodName();
		WebElement questionType = driver.findElement(By.id("custom_questiontype_customthird_section_2"));
		Select questionTypedrpbox = new Select(questionType);
		Thread.sleep(3000);
		questionTypedrpbox.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Question type in the third Section First item.
	 * @param option
	 * @throws Exception
	 */
	public void selectThirdSection4QuestionType(String option) throws Exception {
		IHGUtil.PrintMethodName();
		WebElement questionType = driver.findElement(By.id("custom_questiontype_customthird_section_3"));
		Select questionTypedrpbox = new Select(questionType);
		Thread.sleep(3000);
		questionTypedrpbox.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Required Drop Down in the Second Section First item.
	 * @param option
	 * @throws Exception
	 */	
	public void setSecondSection1ReuiredDropdwn(String option) {
		IHGUtil.PrintMethodName();
		WebElement requiredTypeDrpDwn = driver.findElement(By.id("custom_questionrequired_customsecond_section_0"));
		Select requiredType = new Select(requiredTypeDrpDwn);
		requiredType.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Required Drop Down in the Second Section Second item.
	 * @param option
	 * @throws Exception
	 */	
	public void setSecondSection2ReuiredDropdwn(String option) {
		IHGUtil.PrintMethodName();
		WebElement requiredTypeDrpDwn = driver.findElement(By.id("custom_questionrequired_customsecond_section_1"));
		Select requiredType = new Select(requiredTypeDrpDwn);
		requiredType.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Required Drop Down in the Second Section Third item.
	 * @param option
	 * @throws Exception
	 */	
	public void setSecondSection3ReuiredDropdwn(String option) {
		IHGUtil.PrintMethodName();
		WebElement requiredTypeDrpDwn = driver.findElement(By.id("custom_questionrequired_customsecond_section_2"));
		Select requiredType = new Select(requiredTypeDrpDwn);
		requiredType.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Required Drop Down in the Second Section Fourth item.
	 * @param option
	 * @throws Exception
	 */	
	public void setSecondSection4ReuiredDropdwn(String option) {
		IHGUtil.PrintMethodName();
		WebElement requiredTypeDrpDwn = driver.findElement(By.id("custom_questionrequired_customsecond_section_3"));
		Select requiredType = new Select(requiredTypeDrpDwn);
		requiredType.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Required Drop Down in the third Section First item.
	 * @param option
	 * @throws Exception
	 */	
	public void setThirdSection1ReuiredDropdwn(String option) {
		IHGUtil.PrintMethodName();
		WebElement requiredTypeDrpDwn = driver.findElement(By.id("custom_questionrequired_customthird_section_0"));
		Select requiredType = new Select(requiredTypeDrpDwn);
		requiredType.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Required Drop Down in the third Section Second item.
	 * @param option
	 * @throws Exception
	 */	
	public void setThirdSection2ReuiredDropdwn(String option) {
		IHGUtil.PrintMethodName();
		WebElement requiredTypeDrpDwn = driver.findElement(By.id("custom_questionrequired_customthird_section_1"));
		Select requiredType = new Select(requiredTypeDrpDwn);
		requiredType.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Required Drop Down in the third Section Third item.
	 * @param option
	 * @throws Exception
	 */	
	public void setThirdSection3ReuiredDropdwn(String option) {
		IHGUtil.PrintMethodName();
		WebElement requiredTypeDrpDwn = driver.findElement(By.id("custom_questionrequired_customthird_section_2"));
		Select requiredType = new Select(requiredTypeDrpDwn);
		requiredType.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Required Drop Down in the third Section Fourth item.
	 * @param option
	 * @throws Exception
	 */	
	public void setThirdSection4ReuiredDropdwn(String option) {
		IHGUtil.PrintMethodName();
		WebElement requiredTypeDrpDwn = driver.findElement(By.id("custom_questionrequired_customthird_section_3"));
		Select requiredType = new Select(requiredTypeDrpDwn);
		requiredType.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Prefill Drop Down in the Second Section First item.
	 * @param option
	 * @throws Exception
	 */	
	public void setSecondSection1PreFillDropdwn(String option) {
		IHGUtil.PrintMethodName();
		WebElement requiredTypeDrpDwn = driver.findElement(By.id("custom_questionprefill_customsecond_section_0"));
		Select requiredType = new Select(requiredTypeDrpDwn);
		requiredType.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Prefill Drop Down in the Second Section second item.
	 * @param option
	 * @throws Exception
	 */	
	public void setSecondSection2PreFillDropdwn(String option) {
		IHGUtil.PrintMethodName();
		WebElement requiredTypeDrpDwn = driver.findElement(By.id("custom_questionprefill_customsecond_section_1"));
		Select requiredType = new Select(requiredTypeDrpDwn);
		requiredType.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Prefill Drop Down in the Second Section Third item.
	 * @param option
	 * @throws Exception
	 */	
	public void setSecondSection3PreFillDropdwn(String option) {
		IHGUtil.PrintMethodName();
		WebElement requiredTypeDrpDwn = driver.findElement(By.id("custom_questionprefill_customsecond_section_2"));
		Select requiredType = new Select(requiredTypeDrpDwn);
		requiredType.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Prefill Drop Down in the Second Section Fourth item.
	 * @param option
	 * @throws Exception
	 */	
	public void setSecondSection4PreFillDropdwn(String option) {
		IHGUtil.PrintMethodName();
		WebElement requiredTypeDrpDwn = driver.findElement(By.id("custom_questionprefill_customsecond_section_3"));
		Select requiredType = new Select(requiredTypeDrpDwn);
		requiredType.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Prefill Drop Down in the Second Section First item.
	 * @param option
	 * @throws Exception
	 */	
	public void setThirdSection1PreFillDropdwn(String option) {
		IHGUtil.PrintMethodName();
		WebElement requiredTypeDrpDwn = driver.findElement(By.id("custom_questionprefill_customthird_section_0"));
		Select requiredType = new Select(requiredTypeDrpDwn);
		requiredType.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Prefill Drop Down in the Second Section Second item.
	 * @param option
	 * @throws Exception
	 */	
	public void setThirdSection2PreFillDropdwn(String option) {
		IHGUtil.PrintMethodName();
		WebElement requiredTypeDrpDwn = driver.findElement(By.id("custom_questionprefill_customthird_section_1"));
		Select requiredType = new Select(requiredTypeDrpDwn);
		requiredType.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Prefill Drop Down in the Second Section Third item.
	 * @param option
	 * @throws Exception
	 */	
	public void setThirdSection3PreFillDropdwn(String option) {
		IHGUtil.PrintMethodName();
		WebElement requiredTypeDrpDwn = driver.findElement(By.id("custom_questionprefill_customthird_section_2"));
		Select requiredType = new Select(requiredTypeDrpDwn);
		requiredType.selectByVisibleText(option);
	}
	
	/**
	 * Description : Selecting Prefill Drop Down in the Second Section Fourth item.
	 * @param option
	 * @throws Exception
	 */	
	public void setThirdSection4PreFillDropdwn(String option) {
		IHGUtil.PrintMethodName();
		WebElement requiredTypeDrpDwn = driver.findElement(By.id("custom_questionprefill_customthird_section_3"));
		Select requiredType = new Select(requiredTypeDrpDwn);
		requiredType.selectByVisibleText(option);
	}
	
	/**
	 *  Description : Navigate to Second Section.
	 * @throws Exception
	 */
	public void clickOnSecondSection() throws Exception {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, secondSection);
		secondSection.click();
		Thread.sleep(5000);
	}
	
	/**
	 *  Description : Navigate to Third Section.
	 * @throws Exception
	 */
	public void clickOnThirdSection() {
		IHGUtil.PrintMethodName();
		IHGUtil.waitForElement(driver, 30, thirdSection);
		thirdSection.click();
	}
	
	/**
	 * Description : Fill all the custom form sections.
	 * @param headingTitle : Heading title in the first section.
	 * @param questionTitle1 : First question in the second section.
	 * @param questionTitle2 : Second Question in the second section.
	 * @param answers : Answers to provide in the second section.
	 * @throws Exception
	 */
	public void fillCustomFormDetails(String headingTitle, String questionTitle1, String questionTitle2, String answers) throws Exception {
		IHGUtil.PrintMethodName();
		
		log("Filling First section");
		selectFirstSectionItemType(SitegenConstants.CUSTOMFORM_ITEM_TYPE2);
		firstSectionHeadingTitleFiled.sendKeys(headingTitle);
		clickOnSecondSection();
		
		log("Filling second section");
		log("Filling second section first question");
		driver.findElement(By.xpath(".//input[@name='custom_questiontitle_customsecond_section_0']")).sendKeys(questionTitle1);
		selectSecondSection1QuestionType(SitegenConstants.QUESTION_TYPE1);
		setSecondSection1ReuiredDropdwn(SitegenConstants.OPTION1);
		setSecondSection1PreFillDropdwn(SitegenConstants.OPTION2);
		IHGUtil.waitForElement(driver, 20, SecondSectionInsertItemButton);
		SecondSectionInsertItemButton.click();
		
		log("Filling second section second question");
		driver.findElement(By.id("custom_questiontitle_customsecond_section_1")).sendKeys(questionTitle2);
		selectSecondSection2QuestionType(SitegenConstants.QUESTION_TYPE4);
		setSecondSection2ReuiredDropdwn(SitegenConstants.OPTION1);
		setSecondSection2PreFillDropdwn(SitegenConstants.OPTION2);
		availableAnswersField.sendKeys(answers);
		
		log("Filling third section");
		clickOnThirdSection();
		Thread.sleep(3000);
		selectThirdSection1ItemType(SitegenConstants.CUSTOMFORM_ITEM_TYPE1);
		Thread.sleep(3000);
		driver.findElement(By.id("custom_questiontitle_customthird_section_0")).sendKeys(questionTitle1);
		selectThirdSection1QuestionType(SitegenConstants.QUESTION_TYPE2);
		setThirdSection1ReuiredDropdwn(SitegenConstants.OPTION2);
		setThirdSection1PreFillDropdwn(SitegenConstants.OPTION2);
		
		saveFormButton.click();
		Thread.sleep(5000);
		
	}
	
	/**
	 * Description : Fill all the custom form sections.
	 * @param headingTitle : Heading title in the first section.
	 * @param questionTitle1 : First question in the second section.
	 * @param questionTitle2 : Second Question in the second section.
	 * @param answers : Answers to provide in the second section.
	 * @throws Exception
	 */
	public void fillCustomFormDetailsWithAllReqNUnReqQuestions(String headingTitle) throws Exception {
		IHGUtil.PrintMethodName();
		
		log("Filling First section");
		selectFirstSectionItemType(SitegenConstants.CUSTOMFORM_ITEM_TYPE2);
		firstSectionHeadingTitleFiled.sendKeys(headingTitle);
		clickOnSecondSection();
		
		log("Filling second section");
		log("Filling second section first question");
		driver.findElement(By.xpath(".//input[@name='custom_questiontitle_customsecond_section_0']")).sendKeys(SitegenConstants.QUESTIONTITLE3);
		selectSecondSection1QuestionType(SitegenConstants.QUESTION_TYPE1);
		setSecondSection1ReuiredDropdwn(SitegenConstants.OPTION1);
		setSecondSection1PreFillDropdwn(SitegenConstants.OPTION2);
		IHGUtil.waitForElement(driver, 20, SecondSectionInsertItemButton);
		SecondSectionInsertItemButton.click();
		SecondSectionInsertItemButton.click();
		SecondSectionInsertItemButton.click();
		
		log("Filling second section second question");
		driver.findElement(By.xpath(".//input[@name='custom_questiontitle_customsecond_section_1']")).sendKeys(SitegenConstants.QUESTIONTITLE4);
		selectSecondSection2QuestionType(SitegenConstants.QUESTION_TYPE2);
		setSecondSection2ReuiredDropdwn(SitegenConstants.OPTION1);
		setSecondSection2PreFillDropdwn(SitegenConstants.OPTION2);
		
		log("Filling second section third question");
		driver.findElement(By.xpath(".//input[@name='custom_questiontitle_customsecond_section_2']")).sendKeys(SitegenConstants.QUESTIONTITLE1);
		selectSecondSection3QuestionType(SitegenConstants.QUESTION_TYPE3);
		setSecondSection3ReuiredDropdwn(SitegenConstants.OPTION1);
		setSecondSection3PreFillDropdwn(SitegenConstants.OPTION2);
		driver.findElement(By.id("custom_questioncheckboxesvalues_customsecond_section_2")).sendKeys(SitegenConstants.AVAILABLE_ANSWERS2);
		
		log("Filling second section fourth question");
		driver.findElement(By.id("custom_questiontitle_customsecond_section_3")).sendKeys(SitegenConstants.QUESTIONTITLE2);
		selectSecondSection4QuestionType(SitegenConstants.QUESTION_TYPE4);
		setSecondSection4ReuiredDropdwn(SitegenConstants.OPTION1);
		setSecondSection4PreFillDropdwn(SitegenConstants.OPTION2);
		driver.findElement(By.id("custom_questionradiobuttonsvalues_customsecond_section_3")).sendKeys(SitegenConstants.AVAILABLE_ANSWERS4);
		
		
		log("Filling third section");
		clickOnThirdSection();
		Thread.sleep(3000);
		
		log("Filling third section first question");
		selectThirdSection1ItemType(SitegenConstants.CUSTOMFORM_ITEM_TYPE1);
		Thread.sleep(3000);
		driver.findElement(By.id("custom_questiontitle_customthird_section_0")).sendKeys(SitegenConstants.QUESTIONTITLE5);
		selectThirdSection1QuestionType(SitegenConstants.QUESTION_TYPE1);
		setThirdSection1ReuiredDropdwn(SitegenConstants.OPTION2);
		setThirdSection1PreFillDropdwn(SitegenConstants.OPTION2);
		IHGUtil.waitForElement(driver, 20, thirdSectionInsertItemButton);
		thirdSectionInsertItemButton.click();
		thirdSectionInsertItemButton.click();
		thirdSectionInsertItemButton.click();
		
		
		log("Filling third section second question");
//		selectThirdSection1ItemType(SitegenConstants.CUSTOMFORM_ITEM_TYPE1);
		Thread.sleep(3000);
		driver.findElement(By.id("custom_questiontitle_customthird_section_1")).sendKeys(SitegenConstants.QUESTIONTITLE6);
		selectThirdSection2QuestionType(SitegenConstants.QUESTION_TYPE2);
		setThirdSection2ReuiredDropdwn(SitegenConstants.OPTION2);
		setThirdSection2PreFillDropdwn(SitegenConstants.OPTION2);
		
		
		log("Filling third section third question");
//		selectThirdSection1ItemType(SitegenConstants.CUSTOMFORM_ITEM_TYPE1);
		Thread.sleep(3000);
		driver.findElement(By.id("custom_questiontitle_customthird_section_2")).sendKeys(SitegenConstants.QUESTIONTITLE7);
		selectThirdSection3QuestionType(SitegenConstants.QUESTION_TYPE3);
		setThirdSection3ReuiredDropdwn(SitegenConstants.OPTION2);
		setThirdSection3PreFillDropdwn(SitegenConstants.OPTION2);
		driver.findElement(By.id("custom_questioncheckboxesvalues_customthird_section_2")).sendKeys(SitegenConstants.AVAILABLE_ANSWERS3);
		
		log("Filling third section Fourth question");
//		selectThirdSection1ItemType(SitegenConstants.CUSTOMFORM_ITEM_TYPE1);
		Thread.sleep(3000);
		driver.findElement(By.id("custom_questiontitle_customthird_section_3")).sendKeys(SitegenConstants.QUESTIONTITLE8);
		selectThirdSection4QuestionType(SitegenConstants.QUESTION_TYPE4);
		setThirdSection4ReuiredDropdwn(SitegenConstants.OPTION2);
		setThirdSection4PreFillDropdwn(SitegenConstants.OPTION2);
		driver.findElement(By.id("custom_questionradiobuttonsvalues_customthird_section_3")).sendKeys(SitegenConstants.AVAILABLE_ANSWERS5);
		
		saveFormButton.click();
		Thread.sleep(5000);
		
	}
	
	
	/**
	 * Description : Publish the Saved Form.
	 * @param formName : Form name of the form which needs to be deleted.
	 * @throws Exception
	 */
	public void publishTheSavedForm(String formName) throws Exception {
		IHGUtil.PrintMethodName();
		driver.findElement(By.xpath(".//a[text()='"+formName+"']/ancestor::td/following-sibling::td/a[@class='publish']")).click();
		Thread.sleep(3000);
	}
	
}
