package common;


import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class SetResultData{

    public void  SetResult(String filepath, String filename, String sheetname, List results,String title) throws IOException {

        File file = new File(filepath + "\\" + filename);
        FileInputStream fileInputStream=new FileInputStream(file);
        XSSFWorkbook xssfWorkbook=new XSSFWorkbook(fileInputStream);
        XSSFSheet xssfSheet=xssfWorkbook.getSheet(sheetname);
        FileOutputStream outputStream = new FileOutputStream(file);
        Row row = xssfSheet.getRow(0);
        int startColumn=row.getLastCellNum();
        row.createCell(startColumn).setCellValue(title);


        for(int i=0;i<results.size();i++){
            String result= (String) results.get(i);
            System.out.println(result);
            xssfSheet.getRow(i+1).createCell(startColumn).setCellValue(result);
        }
        outputStream.flush();
        xssfWorkbook.write(outputStream);
        fileInputStream.close();
        outputStream.close();

    }


}