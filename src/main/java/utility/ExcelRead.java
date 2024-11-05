package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelRead {
	
	public static void main(String[] args) throws IOException {
		System.out.println("file location");
		File file=new File("./resources/TestData.xlsx");	
		
		System.out.println("to read the file");
		FileInputStream fis=new FileInputStream(file);
		
		System.out.println("to load the file {Workbook is an interface}");
		Workbook wb=new XSSFWorkbook(fis);
		
		System.out.println("which sheet needs to read");
		Sheet sheet=wb.getSheet("LoginTestData");
		
		System.out.println("Find total rows and columns from sheet");
		int totalRow=sheet.getLastRowNum(); 
		System.out.println(totalRow);                		//4
		int totalColumn=sheet.getRow(0).getLastCellNum();	
		Object [][] data=new Object[totalRow][totalColumn];
	
		for(int row=0;row<totalRow;row++) {
			for(int column=0;column<totalColumn;column++) {
				String cellValue= sheet.getRow(row+1).getCell(column).getStringCellValue();
				data[row][column]=cellValue;
			}
		}
		System.out.println(Arrays.deepToString(data));
		wb.close();
	}
	
	public static Object[][] getDataFromExcel(String filepath,String sheetname) throws IOException {
		System.out.println("file location");
		File file=new File(filepath);	
		
		System.out.println("to read the file");
		FileInputStream fis=new FileInputStream(file);
		
		System.out.println("to load the file {Workbook is an interface}");
		Workbook wb=new XSSFWorkbook(fis);
		
		System.out.println("which sheet needs to read");
		Sheet sheet=wb.getSheet(sheetname);
		
		System.out.println("Find total rows and columns from sheet");
		int totalRow=sheet.getLastRowNum(); 
		System.out.println(totalRow);                		
		int totalColumn=sheet.getRow(0).getLastCellNum();	
		Object [][] data=new Object[totalRow][totalColumn];
	
		for(int row=0;row<totalRow;row++) {
			for(int column=0;column<totalColumn;column++) {
				Cell cell= sheet.getRow(row+1).getCell(column);
				Object cellValue="";
				try {
					if(cell.getCellType()==CellType.STRING) {
						cellValue=cell.getStringCellValue();
					}else if(cell.getCellType()==CellType.BOOLEAN) {
						//cellValue=String.valueOf(cell.getBooleanCellValue());
						cellValue=(cell.getBooleanCellValue());
					}else if(cell.getCellType()==CellType.NUMERIC) {
						cellValue=String.valueOf(cell.getNumericCellValue());
					}else if(cell.getCellType()==CellType.BLANK) {
						cellValue="";
					}
				}catch(NullPointerException e) {
					cellValue="";
				}
				data[row][column]=cellValue;
			}
		}
		System.out.println(Arrays.deepToString(data));
		wb.close();
		return data;
	}
}
