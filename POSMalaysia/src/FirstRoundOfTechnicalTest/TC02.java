package FirstRoundOfTechnicalTest;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import io.github.bonigarcia.wdm.WebDriverManager;

/*
Test Case 2:
	Title: Verify that Pos website has links to create e-Consignment note.
	Test Steps:
		Go to pos.com.my
		Click on the menu bar “Send” > “Parcel”.
		On the new page (pos.com.my/send/send-parcel), scroll down and click on “Create shipment now” button under Cash section:
		Verify that the new page loaded is the e-Consignment Note forms:
		URL should be https://send.pos.com.my/home/e-connote?lg=en
		It should show Sender Info, Receiver Info, Parcel Info, Summary sections:
*/

public class TC02 {
	
	public static void waitTime(long val) throws Exception{
		Thread.sleep(val);		
	}
		
	@Test
	public void executeTc01() throws Exception {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		JavascriptExecutor js= (JavascriptExecutor) driver;
		
		try {
		
		//get the page "Pos.com.my"
		driver.get("https://pos.com.my");
		
		//Maximize the window
		driver.manage().window().maximize();
		waitTime(2000);

		driver.findElement(By.xpath("//div[contains(@class,'close')]")).click();
		
		WebElement send = driver.findElement(By.xpath("(//span[contains(text(),'Send')])[last()]"));
		Actions action= new Actions(driver);
		action.moveToElement(send).build().perform();
		
		driver.findElement(By.xpath("(//a[@href='parcel'])[2]")).click(); 
		WebElement createShipment = driver.findElement(By.xpath("//div[contains(text(),'Create Shipment')]"));
		
		WebElement scroll = driver.findElement(By.xpath("//h2[contains(text(),'Flexible')]"));
		
		js.executeScript("arguments[0].scrollIntoView()", scroll);
		createShipment.click();
		
		Assert.assertTrue("Validate eConsignemnt page is loaded", driver.getCurrentUrl().contains("connote"));
		String expectedConsignmentText = driver.findElement(By.xpath("//div[starts-with(text(),'e-Consign') and contains(@class,'title')]")).getText();
		Assert.assertTrue("validate econsignment", expectedConsignmentText.equals("e-Consignment Note")); //Validate eConsignment page is loaded and displayed
				
		String senderInfo = driver.findElement(By.xpath("//div[contains(text(),'Sender') and contains(@class,'tab')]")).getText();
		System.out.println("sender: "+senderInfo);
		Assert.assertTrue(senderInfo.contains("Sender Info"));
			
		String receiverInfo = driver.findElement(By.xpath("//div[contains(text(),'Receiver') and contains(@class,'tab')]")).getText();
		System.out.println("receiver: "+receiverInfo);
		Assert.assertTrue(receiverInfo.contains("Receiver Info"));
			
		WebElement scrollDown = driver.findElement(By.xpath("//div[contains(@formgroupname,'receiverAddress')]//following::div[contains(text(),'Postcode')]"));
				
		 js.executeScript("arguments[0].scrollIntoView(true);", scrollDown);
		
		WebElement parcelInformation = driver.findElement(By.xpath("//div[contains(text(),'Parcel') and contains(@class,'tab')]"));
		String parcelInfo = parcelInformation.getText();
		System.out.println("parcelInfo: "+parcelInfo);
		Assert.assertTrue(parcelInfo.contains("Parcel Info"));
			
		WebElement summaryInformation = driver.findElement(By.xpath("//div[contains(text(),'Summary') and contains(@class,'tab')]"));
		String summaryInfo = summaryInformation.getText();
		System.out.println("summaryInfo: "+summaryInfo);
		Assert.assertTrue(summaryInfo.contains("Summary"));
		
		driver.close();
		driver.quit();				
		}
		catch(Exception e)
		{
		e.printStackTrace();
		System.out.println(e.getCause());
		System.out.println(e.getMessage());
	
		}
	}
}
