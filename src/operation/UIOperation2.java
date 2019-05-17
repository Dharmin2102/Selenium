package operation;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class UIOperation2 {

	static WebDriver driver;
	static JavascriptExecutor executor;

	private static int n = 0;
	private static int y = 0;

	DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	DateFormat TimeFormate = new SimpleDateFormat("HHmmss");
	// get current date time with Date()
	Date date = new Date();

	// Now format the date
	String date1 = dateFormat.format(date);
	String date2 = TimeFormate.format(date);

	int LastRow = ++n;
	int CaseId = y++;
	String ScrFilePath = Constant.Path_ScreenShot + "Screenshot" + date1 + "\\Case" + CaseId + date2 + ".jpg";

	public UIOperation2(WebDriver driver) {
		UIOperation2.driver = driver;
	}

	public void perform(Properties p, String operation, String objectName, String objectType, String value, String Exp_result) throws Exception {
		System.out.println("");

		if (operation.equalsIgnoreCase("CLICK")) {
			try {// Set text on control
				Thread.sleep(8000);
				executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();",
						driver.findElement(this.getObject(p, objectName, objectType)));
				System.out.println("Click pass");

				//ReadGuru99ExcelFile.excelwrite("True", LastRow, ScrFilePath);
				//Takescreenshot2(ScrFilePath);
			} catch (NoSuchElementException e) {
				System.out.println("Click Fail");
				//ReadGuru99ExcelFile.excelwrite("false", LastRow, ScrFilePath);
				System.err.format("No Element Found to perform click" + e);
			}
		} 
		else if (operation.equalsIgnoreCase("SETTEXT")) {
			try {// Set text on control
				Thread.sleep(2000);
				//System.out.println(this.getObject(p, objectName, objectType));
				System.out.println("SETTEXT Pass");
				driver.findElement(this.getObject(p, objectName, objectType)).sendKeys(value);
				//ReadGuru99ExcelFile.excelwrite("True", LastRow, ScrFilePath);
				//Takescreenshot2(ScrFilePath);

			} catch (NoSuchElementException e) {
				System.out.println("SETTEXT Fail");
				///ReadGuru99ExcelFile.excelwrite("false", LastRow, ScrFilePath);
				
			}
		} 
		else if (operation.equalsIgnoreCase("SELECT")) {
			Thread.sleep(2000);
			String s1 = value;
			/*
			 * String newStr = s1.substring(0, s1.indexOf(".")); System.out.println();
			 */
			String replaceString = s1.replace("B=", "");
			WebElement mySelectElement = driver.findElement(this.getObject(p, objectName, objectType));
			Select dropdown = new Select(mySelectElement);
			dropdown.selectByVisibleText(replaceString);
			//Takescreenshot2(ScrFilePath);

		} 
		// find by Screenshot
		else if (operation.equalsIgnoreCase("SCREENSHOT")) {

			Thread.sleep(2000);
			Date d = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);		
				// Now you can do whatever you need to do with it, for example copy somewhere
			FileUtils.copyFile(scrFile,  new File("C:\\Users\\Rupika\\Desktop\\screenshot\\"+sdf.format(d)+".png"));
			//ReadGuru99ExcelFile.excelwrite("True", LastRow, ScrFilePath);
		}
		
		else if (operation.equalsIgnoreCase("GOTOURL")) {
			try {// Set text on control
				System.out.println("in");
				driver.get(p.getProperty(value));
				Takescreenshot2(ScrFilePath);
				//ReadGuru99ExcelFile.excelwrite("True", LastRow, ScrFilePath);
				//Takescreenshot2(ScrFilePath);
			} catch (NoSuchElementException e) {
				System.out.println("Fail");
				//ReadGuru99ExcelFile.excelwrite("false", LastRow, ScrFilePath);
				System.err.format("No Element Found to perform click" + e);
			}

		} 
		else if (operation.equalsIgnoreCase("GETTEXT")) {
			// Get text of an element
			driver.findElement(this.getObject(p, objectName, objectType)).getText();
			Takescreenshot2(ScrFilePath);
			/* Read_WriteExcelFile.excelwrite(true, LastRow, ScrFilePath); */
		} 
		else if (operation.equalsIgnoreCase("LIST")) {
			//System.out.println(value);
			Thread.sleep(5000);
			List<WebElement> List = driver.findElements(this.getObject(p, objectName, objectType));
			
					for (WebElement cell : List) {
						
				
								if (cell.getText().equalsIgnoreCase(value)) {
									System.out.println("True");
					cell.click();
					break;
				}
								
				
						}
			
			
			
		}
		else if (operation.equalsIgnoreCase("DYM_LOOP")) {
			
			Thread.sleep(2000);
			String SearchBy = value;
			String[] SearchByValue = SearchBy.split(",\\s");

			List<WebElement> List2 = driver.findElements(this.getObject(p, objectName, objectType));

			for (WebElement cell : List2) {
				for (String w : SearchByValue) {

					if (cell.getText().equalsIgnoreCase(w)) {
						System.out.println("1s23");
						
						cell.click();
						break;
					}
				}

				// ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,
				// document.body.scrollHeight)");
			}
			//Takescreenshot2(ScrFilePath);

		}
		else {
			//throw new Exception("Wrong object type");
		}

	}

	
	private By getObject(Properties p, String objectName, String objectType) throws Exception {
		// Find by xpath
		if (objectType.equalsIgnoreCase("XPATH")) {
			return By.xpath(p.getProperty(objectName));
		}
		// find by class
		else if (objectType.equalsIgnoreCase("CLASSNAME")) {

			return By.className(p.getProperty(objectName));

		}
		// find by name
		else if (objectType.equalsIgnoreCase("NAME")) {

			return By.name(p.getProperty(objectName));

		}
		// Find by css
		else if (objectType.equalsIgnoreCase("CSS")) {

			return By.cssSelector(p.getProperty(objectName));

		}
		// find by link
		else if (objectType.equalsIgnoreCase("LINK")) {

			return By.linkText(p.getProperty(objectName));

		}
		
		
		// find by partial link
		else if (objectType.equalsIgnoreCase("PARTIALLINK")) {

			return By.partialLinkText(p.getProperty(objectName));

		} else if (objectType.equalsIgnoreCase("ID")) {

			return By.id(p.getProperty(objectName));

		} else {
			//ReadGuru99ExcelFile.excelwrite("false", LastRow, ScrFilePath);
			throw new Exception("Wrong object type2");
		}
	}

	public static void Takescreenshot2(String ScrFilePath) {
		System.out.println("in");
		File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		try {
			// now copy the screenshot to desired location using copyFile
			// method
			FileUtils.copyFile(src, new File(ScrFilePath));
		}

		catch (IOException e) {
			System.out.println(e.getMessage());

		}
	}
}
