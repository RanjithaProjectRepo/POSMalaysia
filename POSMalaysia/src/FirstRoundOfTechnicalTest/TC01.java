package FirstRoundOfTechnicalTest;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

/*
TestCase1:
Title: Verify that Pos website offers to buy Insurance.   
Test Steps:
	1. 	Go to Pos.com.my
	2.	Scroll down to the section “What can Pos Malaysia do for you, 
	today?” and click on the button “Buy Insurance”. 
	3. 	Verify that a new page is open (in a new tab) and the new URL 
	destination is insurance.pos.com.my  
	4. 	Verify that the button “I drive a car” and “I ride a motorcycle” can 
	be clicked. Once either of the buttons is clicked, it will show a new 
	section “Ok, let’s get to know you” and ask for five different fields.
*/

public class TC01 {
		
	public static void waitTime(long val) throws Exception{
		Thread.sleep(val);	
	}
	
	@Test
	public void executeTC01() throws Exception {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver=new ChromeDriver();
		
		try {
		
		//get the page "Pos.com.my"
		driver.get("https://pos.com.my");
		
		//Maximize the window
		driver.manage().window().maximize();
		waitTime(2000);

		driver.findElement(By.xpath("//div[contains(@class,'close')]")).click();
				
		//Not Found “What can Pos Malaysia do for you, today?” hence scrolling down to click on Buy Insurance
		//scroll using javascriptExecutor
		JavascriptExecutor js = (JavascriptExecutor) driver;
		waitTime(2000);
		js.executeScript("window.scrollBy(0, -150)");
			
		//Click on Buy Insurance Button
		driver.findElement(By.xpath("//a[contains(@href,'insurance')]")).click();
		waitTime(5000);
		
		//Traverse to Insurance browser tab
		String mainWindowHandle = driver.getWindowHandle();
		for (String childWindowHandle : driver.getWindowHandles()) {

			 if(!childWindowHandle.equals(mainWindowHandle)){
				 String actualUrl = driver.switchTo().window(childWindowHandle).getCurrentUrl();
				 String expectedUrl = "https://insurance.pos.com.my/";
				 
				 System.out.println("Actual url: "+actualUrl);
				 System.out.println("Expected url: "+actualUrl);
				 Assert.assertEquals(actualUrl, expectedUrl); //Validate the instance is in Insurance tab
			 }
		}
		
		waitTime(7000);
		
		WebElement driveACar = driver.findElement(By.xpath("//div[contains(@class,'imageBox')]//following-sibling::div[contains(text(),'car')]"));
		WebElement rideAMotorCycle = driver.findElement(By.xpath("//div[contains(@class,'imageBox')]//following-sibling::div[contains(text(),'motor')]"));
		
		Assert.assertTrue("Validate driveACar is enabled and displayed to be clickable",driveACar.isEnabled() && driveACar.isDisplayed());
		Assert.assertTrue("Validate rideAMotorCycle is enabled and displayed to be clickable",rideAMotorCycle.isEnabled() && rideAMotorCycle.isDisplayed());
		
		waitTime(3000);
		driveACar.click();
		
		String letsGetToKnow = driver.findElement(By.xpath("//h5[contains(text(),'know')]")).getText();
		Assert.assertTrue("Validate lets get to know you is displayed",letsGetToKnow.contains("Ok, let's get to know you"));
		
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
