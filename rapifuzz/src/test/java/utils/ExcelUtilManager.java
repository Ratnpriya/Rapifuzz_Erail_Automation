package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.*;

public class ExcelUtilManager {

    public static void writeStationsNamesIntoExcel(String file, String sheet, List<String> data) throws Exception {
        Workbook wb = new XSSFWorkbook();
        Sheet sh = wb.createSheet(sheet);
        int rowNo = 0;
        for (String value : data) {
            Row row = sh.createRow(rowNo++);
            row.createCell(0).setCellValue(value);
        }
        FileOutputStream fos = new FileOutputStream(file);
        wb.write(fos);
        wb.close();
        fos.close();
    }

    public static List<String> readStationNamesFromExcel(String file, String sheet) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        Workbook wb = new XSSFWorkbook(fis);
        Sheet sh = wb.getSheet(sheet);
        List<String> list = new ArrayList<>();
        for (Row row : sh) {
            list.add(row.getCell(0).getStringCellValue());
        }
        wb.close();
        fis.close();
        return list;
    }
    
    public static String[][] readLoginData(String file, String sheet) throws Exception {
        FileInputStream fis = new FileInputStream(file);
        Workbook wb = new XSSFWorkbook(fis);
        Sheet sh = wb.getSheet(sheet);
        int rowCount = sh.getPhysicalNumberOfRows();
        int colCount = sh.getRow(0).getPhysicalNumberOfCells();
        String[][] data = new String[rowCount][colCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                data[i][j] = sh.getRow(i).getCell(j).toString();
            }
        }
        wb.close();
        fis.close();
        return data;
    }


}
