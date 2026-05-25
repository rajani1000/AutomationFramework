package com.mytest.automation.utils;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Rajani
 */
public class Datapool {
    private static XSSFSheet ExcelWSheet;
    private static XSSFWorkbook ExcelWBook;
    private static XSSFCell Cell;
    private static XSSFRow Row;
    private int currentRow;
    Logs logger = Logs.getInstance();

    public void loadDatapool(String path, String sheetName) throws Throwable {

        try {
            FileInputStream ExcelFile = new FileInputStream("src/main/resources/data/" + path + ".xlsx");
            ExcelWBook = new XSSFWorkbook(ExcelFile);
            ExcelWSheet = ExcelWBook.getSheet(sheetName);
            Logs.info("Data pool loaded");

        } catch (Exception e) {
            Logs.error("Error on loading data pool");
            throw (e);
        }
    }

    public void loadDatapool(String path) throws Throwable {
        this.loadDatapool(path, "Sheet1");
    }

    public String getData(int RowNum, int ColNum) throws Throwable {
        try {
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            String CellData = new DataFormatter().formatCellValue(Cell);
            return CellData;
        } catch (Exception e) {
            return "";
        }
    }

    public String getData(String columnName) throws Throwable {
        return getData(currentRow, getColNum(columnName));
    }

    public int getRowContains(String description) throws Throwable {
        int rowNo = -1;
        boolean displayed = false;
        try {
            int rowCount = getRowUsed();
            for (int i = 0; i < rowCount+1; i++) {
                for(Cell cell:ExcelWSheet.getRow(i)){
                    if (new DataFormatter().formatCellValue(cell).equals(description)){
                        displayed = true;
                        rowNo = i;
                        break;
                    }
                }
                if(displayed){
                    break;
                }
            }

            return rowNo;
        } catch (Exception e) {
            Logs.error("Class ExcelUtil | Method getRowContains | Exception desc : " + e.getMessage());
            throw (e);
        }
    }

    public int getColNum(String cName) throws Throwable {
        int colIndex = -1;
        for(Cell cell:ExcelWSheet.getRow(0)) {
            if (new DataFormatter().formatCellValue(cell).equals(cName)) {
                colIndex = cell.getColumnIndex();
                break;
            }
        }
        if(colIndex == -1){
            throw new RuntimeException("There is no column with the defined column name");
        }
        return  colIndex;
    }

    public int getRowUsed() throws Throwable {
        try {
            int RowCount = ExcelWSheet.getLastRowNum();
            return RowCount;
        } catch (Exception e) {
            Logs.error("Class ExcelUtil | Method getRowUsed | Exception desc : " + e.getMessage());
            System.out.println(e.getMessage());
            throw (e);
        }
    }

    public void setDataRow(String testDescription) throws Throwable {
        try {
            currentRow  = getRowContains(testDescription);
            if(currentRow == -1) {
                throw new RuntimeException("Row was not found, please add data row in workbook");
            }
        } catch(Exception e) {
            Logs.error("Data row setup error ");
            throw e;
        }
    }
}
