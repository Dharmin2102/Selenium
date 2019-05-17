package operation;

import java.io.File;

import java.util.Properties;
import org.apache.commons.io.FileUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class UIOperation {

	WebDriver driver;
	public UIOperation(WebDriver driver){
		this.driver = driver;
	}
	public void perform(Properties p, String operation, String objectName, String objectType, String value) throws Exception{
		System.out.println("");
		switch (operation.toUpperCase()) {
		case "CLICK":
			Thread.sleep(2000);
			//Perform click
			driver.findElement(this.getObject(p,objectName,objectType)).click();
			break;
		case "SETTEXT":
			Thread.sleep(2000);
			//Set text on control
			driver.findElement(this.getObject(p,objectName,objectType)).sendKeys(value);
			break;			
		case "GOTOURL":
			Thread.sleep(2000);
			//Get url of application
			driver.get(p.getProperty(value));
			break;
		case "GETTEXT":
			Thread.sleep(2000);
			//Get text of an element
			driver.findElement(this.getObject(p,objectName,objectType)).getText();
			break;
			
		case "LIST":
			Thread.sleep(2000);
			//Get text of an element
			driver.findElement(this.getObject(p,objectName,objectType)).getText();
			break;
			
		case "WAIT":
			//put wait time 
			Thread.sleep(2000);
			break;			
		case "SCREENSHOT":
			//put wait time 
			Thread.sleep(2000);
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);		
			// Now you can do whatever you need to do with it, for example copy somewhere
		FileUtils.copyFile(scrFile,  new File("C:\\Users\\Rupika\\Desktop\\screenshot\\"+sdf.format(d)+".png"));
			
			break;
			
		case "ASSERT":
			//put wait time 
			Thread.sleep(2000);
			String Title =driver.findElement(this.getObject(p,objectName,objectType)).getText();
			Assert.assertEquals(Title, value);
			
			break;	
			
		default:
			break;
		}
	}
	
	/**
	 * Find element BY using object type and value
	 * @param p
	 * @param objectName
	 * @param objectType
	 * @return
	 * @throws Exception
	 */
	private By getObject(Properties p,String objectName,String objectType) throws Exception{
		//Find by xpath
		if(objectType.equalsIgnoreCase("XPATH")){
			
			return By.xpath(p.getProperty(objectName));
		}
		//find by class
		else if(objectType.equalsIgnoreCase("CLASSNAME")){
			
			return By.className(p.getProperty(objectName));
			
		}
		//find by name
		else if(objectType.equalsIgnoreCase("NAME")){
			
			return By.name(p.getProperty(objectName));
			
		}
		//Find by css
		else if(objectType.equalsIgnoreCase("CSS")){
			
			return By.cssSelector(p.getProperty(objectName));
			
		}
		//find by link
		else if(objectType.equalsIgnoreCase("LINK")){
			
			return By.linkText(p.getProperty(objectName));
			
		}
		//find by partial link
		else if(objectType.equalsIgnoreCase("PARTIALLINK")){
			
			return By.partialLinkText(p.getProperty(objectName));
			
		}else
		{
			throw new Exception("Wrong object type");
		}
	}
}
