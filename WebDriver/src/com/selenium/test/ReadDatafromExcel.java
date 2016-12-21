package com.selenium.test;


import java.io.File;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class ReadDatafromExcel {

	public String Excel_path = (System.getProperty("user.dir") + "\\src\\TestData\\Testdata.xls");

	@DataProvider(name = "DP1")
	public Object[][] createData1() throws Exception {

		// String path = prop.getProperty("Testdata_excel");
		String path = Excel_path;
		Object[][] retObjArr = getTableArray(path, "Masters", "cat_start","cat_end");
		return (retObjArr);
	}
	
	public String[][] getTableArray(String xlFilePath, String sheetName,
			String tableStartName, String tableEndName) throws Exception {
		String[][] tabArray = null;

		Workbook wk  = Workbook.getWorkbook(new File(xlFilePath));
		Sheet sheet = wk.getSheet("");
		int startRow, startCol, endRow, endCol, ci, cj;
		
		Cell tableStart = sheet.findCell(tableStartName);
		
		startRow = tableStart.getRow();
		startCol = tableStart.getColumn();

		Cell tableEnd = sheet.findCell(tableEndName, startCol + 1,startRow + 1, 100, 100, false);

		endRow = tableEnd.getRow();
		endCol = tableEnd.getColumn();
		
		tabArray = new String[endRow - startRow - 1][endCol - startCol - 1];
		ci = 0;

		for (int i = startRow + 1; i < endRow; i++, ci++) {
			cj = 0;
			for (int j = startCol + 1; j < endCol; j++, cj++) {
				tabArray[ci][cj] = sheet.getCell(j, i).getContents();
			}
		}
		return (tabArray);
	}
	
	@Test(dataProvider = "DP1")
	public void testDataProviderExample(String Category, String Code, String Name, String Satus ) throws Exception {
	
		
	
	}
}

