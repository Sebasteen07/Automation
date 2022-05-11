//Copyright 2013-2022 NXGN Management, LLC. All Rights Reserved.
package com.medfusion.product.object.maps.patientportal2.page.AskAStaff;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.medfusion.common.utils.IHGUtil;
import com.medfusion.product.object.maps.patientportal2.page.JalapenoMenu;
import com.medfusion.product.patientportal2.pojo.CreditCard;
import com.medfusion.product.patientportal2.pojo.CreditCard.CardType;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class JalapenoAskAStaffV2Page1 extends JalapenoMenu {
	@FindBy(how = How.ID, using = "historyButton")
	private WebElement historyButton;

	@FindBy(how = How.ID, using = "continueButton")
	private WebElement continueButton;

	@FindBy(how = How.ID, using = "subject")
	private WebElement subjectBox;

	@FindBy(how = How.ID, using = "question")
	private WebElement questionBox;

	@FindBy(how = How.ID, using = "removeCardOkButton")
	private WebElement removeCardOkButton;

	@FindBy(how = How.XPATH, using = "//div[text()=' Payment Details ']")
	private WebElement paymentDetails;

    @FindBy(how = How.XPATH, using = "//button[@aria-labelledby='attachments_label']")
	private WebElement attachmnetFiles;

	// Custom component, use click -> sendkeys sequence to manipulate
	@FindBy(how = How.XPATH, using = "//div[@id='locationField']/mf-combobox/div")
	private WebElement locationCombo;

	@FindBy(how = How.XPATH, using = "//div[@id='providerField']/mf-combobox/div")
	private WebElement providerCombo;

	@FindBy(how = How.ID, using = "creditCardAddButton")
	private WebElement addNewCardButton;

	@FindBy(how = How.ID, using = "payment_amount")
	private WebElement paymentAmount;

	@FindBy(how = How.ID, using = "bill_zipcode")
	private WebElement bill_zipcode;

	@FindBy(how = How.ID, using = "cardNumber")
	private WebElement cardNumber;

	@FindBy(how = How.ID, using = "expirationDate_month")
	private WebElement expirationMonth;

	@FindBy(how = How.ID, using = "expirationDate_year")
	private WebElement expirationYear;

	@FindBy(how = How.ID, using = "creditCardCVV")
	private WebElement creditCardCVV;

	@FindBy(how = How.ID, using = "cardSubmitButton")
	private WebElement submitNewCard;

	@FindBy(how = How.ID, using = "amex")
	private WebElement amexCard;

	@FindBy(how = How.ID, using = "discover")
	private WebElement discoverCard;

	@FindBy(how = How.ID, using = "mastercard")
	private WebElement mastercardCard;

	@FindBy(how = How.ID, using = "visa")
	private WebElement visaCard;

	@FindBy(how = How.ID, using = "accountNumber")
	private WebElement accountNumber;

	// Credit card
	@FindBy(how = How.ID, using = "creditCardAddButton")
	private WebElement addCard;

	@FindBy(how = How.ID, using = "cvv")
	private WebElement confirmCVV;

	@FindBy(how = How.ID, using = "cvv")
	private WebElement cardCVV;

	@FindBy(how = How.ID, using = "nameOnCard")
	private WebElement nameOnCard;

	@FindBy(how = How.XPATH, using = "//input[@data-ng-maxlength='30']")
	private WebElement subjectLenght;

	@FindBy(how = How.XPATH, using = "//span[text()='Subject has too many characters.']")
	private WebElement errorMessage;

	@FindBy(how = How.XPATH, using = "//span[@class=\"pull-right ng-binding\"]")
	private WebElement subjectCharCount;

	@FindBy(how = How.XPATH, using = "//span[text()='Error_Files_Testing.pdf']")
	private WebElement errorFileName;

	@FindBy(how = How.XPATH, using = "//span[text()='sw-test-academy.txt']")
	private WebElement properFileName;

    @FindBy(how = How.XPATH, using = "//a[@class='attachmentRemoveLink pull-right']")
	private WebElement errorFileRemove;

    @FindBy(how = How.XPATH, using = "//a[@class='attachmentRemoveLink pull-right']")
	private WebElement fileRemovbutton;

	@FindBy(how = How.ID, using = "attachments_error")
	private WebElement fileUploadErrorMsg;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='ng-select-container ng-has-value'])[1]")
	private WebElement LocationDropDown;
	
	@FindBy(how = How.XPATH, using = "(//div[@class='ng-select-container'])[1]")
	private WebElement ProviderDropDown;

	@FindBy(how = How.XPATH, using = "//*[text()='Location']/../ng-select")
    private WebElement lstLocation;
   
    @FindBy(how = How.XPATH, using = "//*[text()='Provider']/../ng-select")
    private WebElement lstProvider;
   
    @FindBy(how = How.XPATH, using = "(//*[@class='ng-option'])[1]")
    private WebElement setLocation;
   
    @FindBy(how = How.XPATH, using = "//*[@class='ng-option ng-option-marked']")
    private WebElement setProvider;
    
    @FindBy(how = How.XPATH, using = "//div[contains(@class,'ng-option-marked')]")
    private WebElement firstProvider;
    
    @FindBy(how = How.XPATH, using = "//input[contains(@name,'attachments')]")
    private WebElement chooseFile;
    
    @FindBy(how = How.XPATH, using = "//span[text()='Error_Files_Testing1.json']")
	private WebElement invalidFileName;
	
	@FindBy(how = How.XPATH, using = "//span[@class='pull-right']")
	private WebElement subjectLengthCount;

	@FindBy(how = How.XPATH, using = " (//span[@class='pull-right'])[2]")
	private WebElement quesLengthCount;
	
	private long createdTS;

	public JalapenoAskAStaffV2Page1(WebDriver driver) {
		super(driver);
		IHGUtil.PrintMethodName();
		createdTS = System.currentTimeMillis();
	}

	public long getCreatedTimeStamp() {
		return createdTS;
	}

	public JalapenoAskAStaffV2Page2 fillAndContinue(String subject, String question) throws InterruptedException {
		if (subject != null && !subject.trim().isEmpty()) {
			subjectBox.clear();
			wait.until(ExpectedConditions.visibilityOf(subjectBox));
			Thread.sleep(2000);
			subjectBox.sendKeys(subject);
			
		}
		Thread.sleep(2000);			
		log("Selecting Provider ");
		try {
			IHGUtil.waitForElement(driver, 0, ProviderDropDown);
			ProviderDropDown.click();
			Thread.sleep(2000);
			firstProvider.click();
		}
		catch(NoSuchElementException e) {
			log("Provider drop down not displayed");
		}
		questionBox.sendKeys(question);
		Thread.sleep(2000);
		continueButton.click();
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page2.class);
	}

	public JalapenoAskAStaffV2Page2 uploadAttachment(String subject, String question,String correctfilePath) throws InterruptedException {
		if (subject != null && !subject.trim().isEmpty()) {
			wait.until(ExpectedConditions.visibilityOf(subjectBox));
			subjectBox.clear();
			Thread.sleep(1000);
			subjectBox.sendKeys(subject);
		}
		Thread.sleep(2000);			
		log("Selecting Provider ");
		try {
			IHGUtil.waitForElement(driver, 10, ProviderDropDown);
			ProviderDropDown.click();
			Thread.sleep(1000);
			firstProvider.click();
		}
		catch(NoSuchElementException e) {
			log("Provider drop down not displayed");
		}
		questionBox.sendKeys(question);
		chooseFile.sendKeys(correctfilePath);
		continueButton.click();
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page2.class);
		
	}
	
	public JalapenoAskAStaffV2Page2 fillDetails(String subject, String question) throws InterruptedException {
		if (subject != null && !subject.trim().isEmpty()) {
			subjectBox.clear();
			wait.until(ExpectedConditions.visibilityOf(subjectBox));
			Thread.sleep(2000);
			subjectBox.sendKeys(subject);
		}
		Thread.sleep(2000);
		log("Selecting Provider ");
		try {
			IHGUtil.waitForElement(driver, 0, ProviderDropDown);
			ProviderDropDown.click();
			Thread.sleep(2000);
			firstProvider.click();
		} catch (NoSuchElementException e) {
			log("Provider drop down not displayed");
		}
		questionBox.sendKeys(question);
		Thread.sleep(2000);
		
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page2.class);
	}

	public JalapenoAskAStaffV2Page2 continueClick() {

		continueButton.click();
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page2.class);
	}
	
	public JalapenoAskAStaffV2Page2 fillAndContinue(String invalidLengthText, String question, String validLengthText)
			throws InterruptedException {
		for (int i = 0; i < 2; i++) {
			if (i == 0) {
				if (invalidLengthText != null && !invalidLengthText.trim().isEmpty()) {
					subjectBox.clear();
					if (invalidLengthText.length() > 30) {
						subjectBox.sendKeys(invalidLengthText);
						Thread.sleep(100);
						String errorMessageText = errorMessage.getText();
						log("More than 30 character showing error msg: " + errorMessageText);
						assertTrue(errorMessageText.equals("Subject has too many characters."),
								"Expected: " + errorMessageText + ", found: " + "Subject has too many characters.");
						subjectBox.clear();
						continue;
					}
				}
			} else {
				if (validLengthText != null && !validLengthText.trim().isEmpty()) {
					subjectBox.sendKeys(validLengthText);
					String string[] = subjectCharCount.getText().split("/");
					int charCount = Integer.parseInt(string[0]);
					if (validLengthText.length() == charCount) {
						log("The character count in subject  is equals to the Displaying character count in UI");
					} else {
						log("The character count in subject  is Notequals to the Displaying character count in UI");

					}
				}
			}
		}

		questionBox.sendKeys(question);
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page2.class);
	}

	public JalapenoAskAStaffV2Page2 fillAndContinueAttachment(String subject, String question)
			throws InterruptedException {
		if (subject != null && !subject.trim().isEmpty()) {
			subjectBox.clear();
			subjectBox.sendKeys(subject);
			Thread.sleep(1000);
		}
		try {
			IHGUtil.waitForElement(driver, 0, ProviderDropDown);
			ProviderDropDown.click();
			Thread.sleep(2000);
			firstProvider.click();
		}
		catch(NoSuchElementException e) {
			log("Provider drop down not displayed");
		}
			
		questionBox.sendKeys(question);
		Thread.sleep(1000);
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page2.class);
	}

	public JalapenoAskAStaffV2Page2 setClipboardData(String string) {
		IHGUtil.PrintMethodName();
		// StringSelection is a class that can be used for copy and paste operations.
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page2.class);
	}

	public JalapenoAskAStaffV2Page2 uploadFileWithRobot(String errorfilePath, String correctfilePath) {
		IHGUtil.PrintMethodName();
		attachmnetFiles.click();
		Robot robot = null;

		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		robot.delay(8000);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.delay(1500);
		robot.keyRelease(KeyEvent.VK_ENTER);
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page2.class);
	}

	public void uploadFileWithRobotRepeat(String errorfilePath, String correctfilePath) throws InterruptedException {

		for (int i = 0; i <= 5; i++) {
			if (i == 0) {
				setClipboardData(errorfilePath);
				log("Path of Error File " + errorfilePath);
				JalapenoAskAStaffV2Page1 ref = new JalapenoAskAStaffV2Page1(driver);
				ref.uploadFileWithRobot(errorfilePath, correctfilePath);
				log("Uploaded more than 10 MB file  " + errorFileName.getText());
				assertTrue(errorFileName.getText().equals("Error_Files_Testing.pdf"),
						"Expected: " + errorFileName.getText() + ", found: " + "Error_Files_Testing.pdf");
				assertTrue(fileUploadErrorMsg.getText().equals("Your attachments exceed the maximum size of 10MB."),
						"Expected: " + fileUploadErrorMsg.getText() + ", found: " + "Your attachments exceed the maximum size of 10MB.");	
				errorFileRemove.click();
				continue;
			} else {

				setClipboardData(correctfilePath);
				//wait for the window to open the folder
				Thread.sleep(5000);
				JalapenoAskAStaffV2Page1 ref = new JalapenoAskAStaffV2Page1(driver);
				ref.uploadFileWithRobot(errorfilePath, correctfilePath);
				log("Uploaded  2 MB file  " + properFileName.getText());
				assertTrue(properFileName.getText().equals("sw-test-academy.txt"),
						"Expected: " + properFileName.getText() + ", found: " + "sw-test-academy.txt");
			}
		}
		
	}

	private boolean areAddNewCreditCardLightboxElementsPresent() {
		ArrayList<WebElement> webElementsList = new ArrayList<WebElement>();
		webElementsList.add(nameOnCard);
		webElementsList.add(bill_zipcode);
		webElementsList.add(cardNumber);
		webElementsList.add(expirationMonth);
		webElementsList.add(expirationYear);
		webElementsList.add(creditCardCVV);
		webElementsList.add(submitNewCard);
		webElementsList.add(amexCard);
		webElementsList.add(discoverCard);
		webElementsList.add(mastercardCard);
		webElementsList.add(visaCard);

		return assessPageElements(webElementsList);
	}

	public JalapenoAskAStaffV2HistoryListPage clickOnHistory() throws InterruptedException {
		log("Clicking on Ask a Question menu button");
		wait.until(ExpectedConditions.elementToBeClickable(historyButton));
		historyButton.click();
		return PageFactory.initElements(driver, JalapenoAskAStaffV2HistoryListPage.class);
	}

	private ArrayList<WebElement> getCreditCards() {
		return (ArrayList<WebElement>) driver.findElements(By.xpath("//li[contains(@class, 'toggleCheck')]"));
	}

	public boolean isAnyCardPresent() {
		return getCreditCards().size() > 0;
	}

	public void removeAllCards() throws InterruptedException {
		log("Removing of displayed cards");
		ArrayList<WebElement> cards = getCreditCards();

		if (cards.size() > 0) {
			log("Count of displayed cards: " + cards.size());
			int removedCards = 0;

			ArrayList<WebElement> removeButtons = (ArrayList<WebElement>) driver
					.findElements(By.xpath("//a[contains(@class,'creditCardRemoveButton')]"));
			for (int i = 0; i < removeButtons.size(); i++) {
				if (removeButtons.get(i).isDisplayed()) {
					removeCreditCard(removeButtons.get(i));
					log("Card #" + ++removedCards + " removed");
					// need to sleep because of modal disappearing time
					Thread.sleep(5000);
				}
			}
		} else {
			log("No previous card is displayed");
		}
	}

	private ArrayList<WebElement> getRemoveAttachment() {
		return (ArrayList<WebElement>) driver
                .findElements(By.xpath("//tr[contains(@class, 'attachmentItem')]"));
	}

	public void removeAttachment() throws InterruptedException {
		log("Removing of displayed Attachment");
		ArrayList<WebElement> attachmnet = getRemoveAttachment();

		if (attachmnet.size() > 0) {
			log("Count of displayed Attachment: " + attachmnet.size());
			int removeButton = 0;

			ArrayList<WebElement> removeButtons = (ArrayList<WebElement>) driver
                    .findElements(By.xpath("//a[@class='attachmentRemoveLink pull-right']"));
			for (int i = 0; i < removeButtons.size() - 1; i++) {
				if (removeButtons.get(i).isDisplayed()) {
					fileRemovbutton.click();
					removeButtons.get(i);
					log("Attachment #" + removeButton + " removed");
					// need to sleep because of modal disappearing time
					Thread.sleep(5000);
				}
			}
		} else {
			log("No Attachment  is displayed");
		}
		continueButton.sendKeys(Keys.ENTER);
	}

	private JalapenoAskAStaffV2Page1 removeCreditCard(WebElement removeButton) {
		removeButton.click();
		wait.until(ExpectedConditions.elementToBeClickable(removeCardOkButton));
		removeCardOkButton.click();
		return this;
	}

	private boolean isCardTypeSelected(CardType type) {
		switch (type) {
		case Visa:
			return visaCard.getAttribute("class").contains("ccselected");
		case Mastercard:
			return mastercardCard.getAttribute("class").contains("ccselected");
		case Discover:
			return discoverCard.getAttribute("class").contains("ccselected");
		case Amex:
			return amexCard.getAttribute("class").contains("ccselected");
		default:
			log("Unknown card type was inserted");
			return false;
		}
	}

	private void fillNewCardInformation(CreditCard card) {
		log("Verify all elements of lightbox are visible");
		assertTrue(areAddNewCreditCardLightboxElementsPresent());

		log("Filling info about new credit card");
		log("Name on card: " + card.getName());
		nameOnCard.sendKeys(card.getName());

		log("ZipCode: " + card.getZipCode());
		bill_zipcode.sendKeys(card.getZipCode());

		log("Card number: " + card.getCardNumber());
		cardNumber.sendKeys(card.getCardNumber());

		log("Expiration: " + card.getExpMonth() + "/" + card.getExpYear());

		Select selectMonth = new Select(expirationMonth);
		selectMonth.selectByVisibleText(card.getExpMonth());

		Select selectYear = new Select(expirationYear);
		selectYear.selectByVisibleText(card.getExpYear());

		log("CVV: " + card.getCvvCode());
		creditCardCVV.sendKeys(card.getCvvCode());

		log("Checking if " + card.getType() + " card type is selected");
		assert isCardTypeSelected(card.getType()) : "Wrong card type was selected.";

		log("Submit new card");
		submitNewCard.click();
	}

	public JalapenoAskPayBillsConfirmationPage fillPaymentInfo(String accNumber, CreditCard creditCard) {
		return fillPaymentInfo(accNumber, creditCard, "");
	}

	public JalapenoAskPayBillsConfirmationPage fillPaymentInfo(String accNumber, CreditCard creditCard,
			String location) {

		WebDriverWait wait = new WebDriverWait(driver, 10);

		log("Click on Add New Card");
		wait.until(ExpectedConditions.elementToBeClickable(addNewCardButton));
		addNewCardButton.sendKeys(Keys.ENTER);
		fillNewCardInformation(creditCard);

		log("Insert CVV code: " + creditCard.getCvvCode());
		wait.until(ExpectedConditions.visibilityOf(confirmCVV));
		confirmCVV.sendKeys(creditCard.getCvvCode());

		log("Click on Continue button");
		// Race condition - sometimes click doesn't work, added explicit wait (didn't
		// help), updated to sendKeys
		wait.until(ExpectedConditions.elementToBeClickable(addNewCardButton));
		continueButton.sendKeys(Keys.ENTER);
		return PageFactory.initElements(driver, JalapenoAskPayBillsConfirmationPage.class);
	}

	public WebElement getPaymentText(WebElement paymentAmount) {
		return paymentAmount;
	}

	public String getAskaPaymentText() {
		return paymentAmount.getText();

	}

	public String getProperFileText() {
		return properFileName.getText();

	}

	public String getErrorFileText() {
		return errorFileName.getText();

	}

	public String getErrorFileMsgText() {
		return fileUploadErrorMsg.getText();

	}
	
	public String getSubjectLength() {
		return subjectLengthCount.getText();

	}

	public String getQuestionLength() {
		return quesLengthCount.getText();

	}
	
	public JalapenoAskAStaffV2Page2 NGfillAndContinue(String subject, String question,String ProviderName,String LocationName) throws InterruptedException {
		if (subject != null && !subject.trim().isEmpty()) {
			subjectBox.clear();
			subjectBox.sendKeys(subject);
			Thread.sleep(1000);
		}
		log("Selecting Location "+LocationName);
		LocationDropDown.click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("(//*[contains(text(),'"+LocationName+"')])[2]")).click();
		Thread.sleep(4000);			
		log("Selecting Provider "+ProviderName);
		ProviderDropDown.click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//*[contains(text(),'"+ProviderName+"')]")).click();
		Thread.sleep(4000);	
		log("Entering Question "+question);
		questionBox.sendKeys(question);
		Thread.sleep(10000);
		continueButton.click();
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page2.class);
	}
	
    public JalapenoAskAStaffV2Page2 NGfillASKADetails(String subject, String question,String ProviderName,String LocationName) throws InterruptedException {
        if (subject != null && !subject.trim().isEmpty()) {
            subjectBox.clear();
            subjectBox.sendKeys(subject);
        }
        log("Selecting Location "+LocationName);
        IHGUtil.waitForElement(driver, 30, LocationDropDown);
        LocationDropDown.click();
        driver.findElement(By.xpath("(//*[contains(text(),'"+LocationName+"')])[2]")).click();
        IHGUtil.waitForElement(driver, 30, ProviderDropDown);
        log("Selecting Provider "+ProviderName);
        ProviderDropDown.click();
        driver.findElement(By.xpath("//*[contains(text(),'"+ProviderName+"')]")).click();
        IHGUtil.waitForElement(driver, 30, questionBox);   
        log("Entering Question "+question);
        questionBox.sendKeys(question);
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page2.class);
    }
    
    public void uploadFile(String filePath,String fileName) throws InterruptedException {
    	setClipboardData(filePath);
    	Thread.sleep(5000);
    	log("Path of File " + filePath);
    	JalapenoAskAStaffV2Page1 ref = new JalapenoAskAStaffV2Page1(driver);
    	ref.uploadFileWithRobot(filePath, "");
    	
    	String fileAttached = driver.findElement(By.xpath("//span[text()='"+fileName+"']")).getText();
    	log("Uploaded file  " + fileAttached);
				assertTrue(fileAttached.equals(fileName),
						"Expected: " + fileAttached + ", found: " + fileName);
		}
    
    public JalapenoAskAStaffV2Page2 clickContinue() throws InterruptedException {
    	IHGUtil.waitForElement(driver, 30, continueButton);
    	continueButton.click();
		return PageFactory.initElements(driver, JalapenoAskAStaffV2Page2.class);
	}

    public JalapenoAskAStaffV2Page2 fillAndContinueWithProviderAndLocation(String subject, String question) throws InterruptedException {
        if (subject != null && !subject.trim().isEmpty()) {   
        	IHGUtil.waitForElement(driver, 60, subjectBox);
        	subjectBox.clear();
            wait.until(ExpectedConditions.visibilityOf(subjectBox));
            subjectBox.sendKeys(subject);
        }
        if (new IHGUtil(driver).isRendered(lstLocation)) {
            log("Set primary location");
            lstLocation.click();
            IHGUtil.waitForElement(driver, 60, setLocation);
            javascriptClick(setLocation);
        }
        if (new IHGUtil(driver).isRendered(lstProvider)) {
            log("Set primary location");
            lstProvider.click();
            IHGUtil.waitForElement(driver, 60, setProvider);
            javascriptClick(setProvider);
        }
        IHGUtil.waitForElement(driver, 60, questionBox);
        questionBox.sendKeys(question);
        IHGUtil.waitForElement(driver, 60, continueButton);
        continueButton.click();
        return PageFactory.initElements(driver, JalapenoAskAStaffV2Page2.class);
    }
    
    public void uploadInvalidAndValidFile(String errorfilePath, String correctfilePath) throws InterruptedException {
				setClipboardData(errorfilePath);
				log("Path of Error File " + errorfilePath);
				JalapenoAskAStaffV2Page1 ref = new JalapenoAskAStaffV2Page1(driver);
				ref.uploadFileWithRobot(errorfilePath, correctfilePath);
				log("Uploaded invalid file  " + invalidFileName.getText());
				assertTrue(invalidFileName.getText().equals("Error_Files_Testing1.json"),
						"Expected: " + invalidFileName.getText() + ", found: " + "Error_Files_Testing.pdf");
				assertTrue(fileUploadErrorMsg.getText().equals("Invalid file type. Allowed file types are .bmp, .png, .jpg, .jpeg, .tiff, .tif, .doc, .docx, .pdf, .txt"),
						"Expected: " + fileUploadErrorMsg.getText() + ", found: " + "Invalid file type. Allowed file types are .bmp, .png, .jpg, .jpeg, .tiff, .tif, .doc, .docx, .pdf, .txt");
				log("Verifying continue button is disabled");
				assertFalse(continueButton.isEnabled(), "Continue button is disabled");
				errorFileRemove.click();
				log("Verifying continue button is enabled");
				assertTrue(continueButton.isEnabled(), "Continue button is disabled");

				setClipboardData(correctfilePath);
				//wait for the window to open the folder
				Thread.sleep(2000);
				JalapenoAskAStaffV2Page1 ref1 = new JalapenoAskAStaffV2Page1(driver);
				ref1.uploadFileWithRobot(errorfilePath, correctfilePath);
				log("Uploaded valid file  " + properFileName.getText());
				assertTrue(properFileName.getText().equals("sw-test-academy.txt"),
						"Expected: " + properFileName.getText() + ", found: " + "sw-test-academy.txt");
		
	}
    
}
