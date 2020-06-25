package utility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataReader {
	public static String TestDataFileName = System.getProperty("user.dir") + "\\src\\test\\resources\\testdata\\credential.xlsx";
	public static XSSFWorkbook wb;
	public static XSSFSheet sheet;
	private static Cell cell;
	private static Row row;
	
	//Row Number
    public static int rowNumber;
 
    //Column Number
    public static int columnNumber;
    
    //Setter and Getters of row and columns
    public static void setRowNumber(int pRowNumber) {
        rowNumber = pRowNumber;
    }
 
    public static int getRowNumber() {
        return rowNumber;
    }
 
    public static void setColumnNumber(int pColumnNumber) {
        columnNumber = pColumnNumber;
    }
 
    public static int getColumnNumber() {
        return columnNumber;
    }

	public static List<HashMap<String, String>> getData(String sheetName) {

		List<HashMap<String, String>> mydata = new ArrayList<HashMap<String, String>>();
		try {
			FileInputStream fis = new FileInputStream(TestDataFileName);
			wb = new XSSFWorkbook(fis);
			sheet = wb.getSheet(sheetName);
			Row HeaderRow = sheet.getRow(0);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row currentRow = sheet.getRow(i);
				HashMap<String, String> data = new HashMap<String, String>();
				for (int j = 0; j < currentRow.getLastCellNum(); j++) {
					if (currentRow.getCell(j) != null) {
						Cell currentCell = currentRow.getCell(j);
						if (getCellValue(currentCell) != null) {
							data.put(HeaderRow.getCell(j).getStringCellValue(), getCellValue(currentCell));
						}
					}
				}
				mydata.add(data);
			}
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mydata;
	}
	
	public static void setCellData(String value, int RowNum, int ColNum) {
        try {
			row = sheet.getRow(RowNum);
			cell = row.getCell(ColNum);
			if (cell == null) {
				cell = row.createCell(ColNum);
				cell.setCellValue(value);
			} else {
				cell.setCellValue(value);
			}
			// Constant variables Test Data path and Test Data file name
            FileOutputStream fileOut = new FileOutputStream(TestDataFileName);
            wb.write(fileOut);
            fileOut.flush();
            fileOut.close();
        } catch (Exception e) {
                e.printStackTrace();
        }
    }

	public static String getCellValue(Cell c) {
		String value = null;
		
		switch (c.getCellType()) {
		case STRING:
			value = c.getStringCellValue();
			break;
		case NUMERIC:
			value = new BigDecimal(c.getNumericCellValue()).toPlainString();
			break;
		default:
			value = null;
			break;
		}
		
		return value;
	}

}
