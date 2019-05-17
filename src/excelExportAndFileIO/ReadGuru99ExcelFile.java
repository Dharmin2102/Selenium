package excelExportAndFileIO;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import operation.Constant;

public class ReadGuru99ExcelFile {
	
	public Sheet readExcel(String filePath,String fileName,String sheetName) throws IOException{
	//Create a object of File class to open xlsx file
	File file =	new File(filePath+"\\"+fileName);
	//Create an object of FileInputStream class to read excel file
	FileInputStream inputStream = new FileInputStream(file);
	Workbook guru99Workbook = null;
	//Find the file extension by spliting file name in substing and getting only extension name
	String fileExtensionName = fileName.substring(fileName.indexOf("."));
	//Check condition if the file is xlsx file
	if(fileExtensionName.equals(".xlsx")){
	//If it is xlsx file then create object of XSSFWorkbook class
	guru99Workbook = new XSSFWorkbook(inputStream);
	}
	//Check condition if the file is xls file
	else if(fileExtensionName.equals(".xls")){
		//If it is xls file then create object of XSSFWorkbook class
		guru99Workbook = new HSSFWorkbook(inputStream);
	}
	//Read sheet inside the workbook by its name
	Sheet  guru99Sheet = guru99Workbook.getSheet(sheetName);
	 return guru99Sheet;	
	}


@SuppressWarnings("deprecation")
public static void excelwrite(String result2, int LastRow, String ScrFilePath) throws Exception {

	FileOutputStream fos = null;
	FileInputStream file = new FileInputStream(new File(Constant.Path_TestData));
	@SuppressWarnings("resource")
	XSSFWorkbook workbook = new XSSFWorkbook(file);
	XSSFSheet sheet = workbook.getSheetAt(0);
	XSSFRow row = null;
	XSSFCell cell = null;
	XSSFFont font = workbook.createFont();
	XSSFCellStyle style = workbook.createCellStyle();

	int col_Num = 0;
	row = sheet.getRow(0);
	System.out.println(row.getLastCellNum());
	for (int i = 1; i < row.getLastCellNum(); i++) {
		if (row.getCell(i).getStringCellValue().trim().equals("Result")) {
			col_Num = i;
		}
	}

	row = sheet.getRow(LastRow);
	if (row == null)
		row = sheet.createRow(LastRow);

	cell = row.getCell(col_Num);
	if (cell == null)
		cell = row.createCell(col_Num);

	font.setFontName("Comic Sans MS");
	font.setFontHeight(12.0);
	font.setBold(true);
	font.setColor(HSSFColor.WHITE.index);
	
	
	if (result2.equalsIgnoreCase("True")) {
		System.out.println("in");
		System.out.println(LastRow);
		style.setFont(font);
		style.setFillForegroundColor(HSSFColor.GREEN.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		cell.setCellValue("PASS");
	} 
	else if (result2.equalsIgnoreCase("False")) {
		System.out.println("inFail");
		style.setFont(font);
		style.setFillForegroundColor(HSSFColor.RED.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		cell.setCellValue("FAIL");
	} 
	else {
		style.setFont(font);
		style.setFillForegroundColor(HSSFColor.YELLOW.index);
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

		cell.setCellStyle(style);
		cell.setCellValue("SKIP");
	}
	
	file.close();
	fos = new FileOutputStream(new File(Constant.Path_TestData));
	workbook.write(fos);
	fos.flush();

	File sourceExcel = new File(Constant.Path_TestData);
	File Dup_Path_TestData = new File(Constant.Dup_Path_TestData);
	try {

		FileUtils.copyFile(sourceExcel, Dup_Path_TestData);

	} catch (IOException e) {

		e.printStackTrace();
	}
}
}




