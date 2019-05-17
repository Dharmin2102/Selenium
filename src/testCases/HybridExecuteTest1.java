package testCases;

import java.io.IOException;
import java.util.Properties;
import operation.ReadObject;
import operation.UIOperation2;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import excelExportAndFileIO.ReadGuru99ExcelFile;

public class HybridExecuteTest1 {
	WebDriver webdriver = null;
    @Test(dataProvider="hybridData")
	public void testLogin(String testcaseName,String keyword,String objectName,String objectType,String value,String Exp_result) throws Exception {
		// TODO Auto-generated method stub
      
    	if(testcaseName!="FF")
    	
    	//if(testcaseName!=null&&testcaseName.length()!=0)
    	{
    		System.setProperty("webdriver.gecko.driver", "E://Selenium//geckodriver.exe");
    		webdriver=new FirefoxDriver();
    		
    	}
    	
        ReadObject object = new ReadObject();
        Properties allObjects =  object.getObjectRepository();
        UIOperation2 operation = new UIOperation2(webdriver);
      	//Call perform function to perform operation on UI
    			operation.perform(allObjects, keyword, objectName,
    				objectType, value, Exp_result);
    	    
	}

    
    @DataProvider(name="hybridData")
	public Object[][] getDataFromDataprovider() throws IOException{
    	Object[][] object = null; 
    	ReadGuru99ExcelFile file = new ReadGuru99ExcelFile();
        
         //Read keyword sheet
    	System.out.println(System.getProperty("user.dir"));
         Sheet guru99Sheet = file.readExcel(System.getProperty("user.dir")+"\\test-output\\","TestCase.xlsx" , "PublicEquity");
       //Find number of rows in excel file
     	int rowCount = guru99Sheet.getLastRowNum()-guru99Sheet.getFirstRowNum();
     	
     	object = new Object[rowCount][6];
     	for (int i = 0; i < rowCount; i++) {
    		//Loop over all the rows
     		
    		Row row = guru99Sheet.getRow(i+1);
    		
    		//Create a loop to print cell values in a row
    		for (int j = 0; j < row.getLastCellNum(); j++) {
    			//Print excel data in console
    			
    			object[i][j] = row.getCell(j).toString();
    			
    			
    		}
         
    	}
     	
     	  return object;	 
	}
}
