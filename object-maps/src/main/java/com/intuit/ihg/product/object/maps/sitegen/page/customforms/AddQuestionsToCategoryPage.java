package com.intuit.ihg.product.object.maps.sitegen.page.customforms;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.intuit.ifs.csscat.core.pageobject.BasePageObject;
import com.intuit.ihg.common.utils.IHGUtil;
import com.intuit.ihg.product.sitegen.utils.SitegenlUtil;

public class AddQuestionsToCategoryPage extends BasePageObject {

	@FindBy(name="questionText[0]")
	private WebElement dropDownQuestionText0;

	@FindBy(name="questionLabel0[0]")
	private WebElement txtDownQuestionLabel0;

	@FindBy(name="type[0]")
	private WebElement dropDownStdAnswerSet0;


	@FindBy(name="questionText[1]")
	private WebElement dropDownQuestionText1;

	@FindBy(name="questionLabel1[0]")
	private WebElement txtDownQuestionLabel1;

	@FindBy(name="type[1]")
	private WebElement dropDownStdAnswerSet1;

	@FindBy(name="questionText[2]")
	private WebElement dropDownQuestionText2;

	@FindBy(name="questionLabel2[0]")
	private WebElement txtDownQuestionLabel2; 

	@FindBy(name="type[2]")
	private WebElement dropDownStdAnswerSet2;

	@FindBy(xpath = "//input[@class='html-control-text' and @value='Save Questions']")
	private WebElement btnSave;

	@FindBy(xpath="//ul[@id='tabnav']//a[contains(@href,'../action/doQnaireLayout?method=init&sessionRequired=false')]")
	private WebElement formLayOutTab;


	public AddQuestionsToCategoryPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Indicates if the search page is loaded
	 * 
	 * @return true or false
	 */
	public boolean isSearchPageLoaded() {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);

		boolean result = false;
		try {
			result = IHGUtil.waitForElement(driver, 15, dropDownQuestionText0);
		} catch (Exception e) {
			// Catch any element not found errors
		}

		return result;
	}

	/**
	 * Adding question 1 with answer set 1 to the custom form category
	 * @param formquestion1
	 * @param formanswerset1
	 * @return true if question label matches question selected else returns false.
	 * @throws Exception
	 */
	public boolean addQuestion1ToCategory(String formquestion1)throws Exception {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);

		log("Select question1");
		Select dropdownquestion1 =new Select(dropDownQuestionText0);
		dropdownquestion1.selectByVisibleText(formquestion1);
		dropDownQuestionText0.click();
		SitegenlUtil sutil = new SitegenlUtil(driver);
		sutil.pressTabKey();
		IHGUtil.waitForElement(driver, 30, txtDownQuestionLabel0);
		String questionLabell=txtDownQuestionLabel0.getText();
		log("Text :"+questionLabell);
		if(questionLabell.equalsIgnoreCase(formquestion1))
			return true;
		else
			return false;

	}

	/**
	 * @author bbinisha
	 * @param formanswerset1
	 */
	public void addAnswerForQuestion1(String formanswerset1) {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		log("Select first question answer.");
		Select dropdownanswer1=new Select(dropDownStdAnswerSet0);
		dropdownanswer1.selectByVisibleText(formanswerset1);

	}

	/**
	 *  Adding question 2 with answer set 2 to the custom form category
	 * @param formquestion2
	 * @param formanswerset2
	 * @return  true if question label matches question selected else returns false.
	 * @throws Exception
	 */
	public boolean addQuestion2ToCategory(String formquestion2)throws Exception {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);

		log("Select question2 and answer2");
		Select dropdownquestion2 =new Select(dropDownQuestionText1);
		dropdownquestion2.selectByVisibleText(formquestion2);
		dropDownQuestionText1.click();
		SitegenlUtil sutil = new SitegenlUtil(driver);
		sutil.pressTabKey();
		String questionLabel2=txtDownQuestionLabel1.getText();
		if(questionLabel2.equalsIgnoreCase(formquestion2))
			return true;
		else
			return false;

	}

	/**
	 * @author bbinisha
	 * @param formanswerset2
	 */
	public void addAnswerForQuestion2(String formanswerset2) {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		log("Select Second Question answer ");
		Select dropdownanswer1=new Select(dropDownStdAnswerSet1);
		dropdownanswer1.selectByVisibleText(formanswerset2);

	}

	/**
	 *  Adding question 3 with answer set 3 to the custom form category
	 * @param formquestion3
	 * @param formanswerset3
	 * @return  true if question label matches question selected else returns false.
	 * @throws Exception
	 */
	public boolean addQuestion3ToCategory(String formquestion3)throws Exception {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);

		log("Select question3 and answer3");
		Select dropdownquestion3 =new Select(dropDownQuestionText2);
		dropdownquestion3.selectByVisibleText(formquestion3);
		dropDownQuestionText2.click();
		SitegenlUtil sutil = new SitegenlUtil(driver);
		sutil.pressTabKey();
		String questionLabel3=txtDownQuestionLabel2.getText();
		if(questionLabel3.equalsIgnoreCase(formquestion3))
			return true;
		else
			return false;
	}

	/**
	 * @author bbinisha
	 * @param formanswerset3
	 */
	public void addAnswerForQuestion3(String formanswerset3) {
		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		log("Select third Question answer ");
		Select dropdownanswer1=new Select(dropDownStdAnswerSet2);
		dropdownanswer1.selectByVisibleText(formanswerset3);	
	}


	/**
	 * Click on Form Layout tab to set form layout page number
	 * @return  true if question label matches question selected else returns false.
	 * @throws Exception
	 */
	public CustomFormLayoutPage saveCategoryQuestions() throws Exception {

		IHGUtil.PrintMethodName();
		SitegenlUtil.setDefaultFrame(driver);
		IHGUtil.waitForElement(driver, 30, btnSave);
		btnSave.click();
		IHGUtil.waitForElement(driver, 30, formLayOutTab);
		formLayOutTab.click();
		Thread.sleep(9000);
		return PageFactory.initElements(driver, CustomFormLayoutPage.class);

	}




}
