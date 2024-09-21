package com.ninza.hrm.api.genericutility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtility {
	
	public String getDataFromExcel(String sheetName , int rowNum , int cellNum) throws EncryptedDocumentException, IOException  {
		FileInputStream fis=new FileInputStream("");
		Workbook wb=WorkbookFactory.create(fis);
		Sheet sheet = wb.getSheet(sheetName);
		Row row = sheet.getRow(rowNum);
		Cell cell = row.getCell(cellNum);
		String data = cell.getStringCellValue();
		wb.close();
		return data;
		
		
	}
    public int getRowCount(String sheetName) throws Throwable
    {
    	FileInputStream fis=new FileInputStream("");
		Workbook wb = WorkbookFactory.create(fis);
		Sheet sh = wb.getSheet(sheetName);
		int rowCount = sh.getLastRowNum();
		wb.close();
		return rowCount;
		
    	
    }
    public void setDataIntoExcel(String sheetName , int rowNum , int cellNum , String data) throws EncryptedDocumentException, IOException 
    
   {
    	FileInputStream fis=new FileInputStream("");
		Workbook wb = WorkbookFactory.create(fis);
    	org.apache.poi.ss.usermodel.Cell cell = wb.getSheet(sheetName).getRow(rowNum).createCell(cellNum);
    	cell.setCellType(CellType.STRING);
    	cell.setCellValue(data);
    	FileOutputStream fos=new FileOutputStream("");
        wb.write(fos);
    	wb.close();
   }
}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	


