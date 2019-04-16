package com.inevitablesol.www.shopmanagement.report;

import android.content.Context;
import android.os.Environment;
import android.widget.Toast;

import com.inevitablesol.www.shopmanagement.sales.SaleInfo;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Pritam on 11-04-2019.
 */

public class ReportExcelReadWrite {

    private static final String TAG = "ReportExcelReadWrite";

    public static void writeXLSFile() throws IOException {

        String excelFileName = "/root/sdcard1/Test.xls";//name of excel file

        String sheetName = "Sheet1.xls";//name of sheet

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet(sheetName) ;

        //iterating r number of rows
        for (int r=0;r < 5; r++ )
        {
            HSSFRow row = sheet.createRow(r);

            //iterating c number of columns
            for (int c=0;c < 5; c++ )
            {
                HSSFCell cell = row.createCell(c);

                cell.setCellValue("Cell "+r+" "+c);
            }
        }

        FileOutputStream fileOut = new FileOutputStream(excelFileName);

        //write this workbook to an Outputstream.
        wb.write(fileOut);
        fileOut.flush();
        fileOut.close();
    }

    public  static  void testFile(Context context, String fileName, ArrayList<SaleInfo> saleInfos) throws  IOException

    {
        int i=1;
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet firstSheet = workbook.createSheet("Sheet1");
        //HSSFSheet secondSheet = workbook.createSheet("Sheet2");
        HSSFRow rowAA = firstSheet.createRow(0);
        HSSFCell cellAA = rowAA.createCell(0);
        HSSFCell cellAB = rowAA.createCell(1);
        HSSFCell cellAc = rowAA.createCell(2);
        HSSFCell cellAd = rowAA.createCell(3);
        HSSFCell cellAe = rowAA.createCell(4);
        cellAA.setCellValue("Created Date");
        cellAB.setCellValue("Invocie Date");
        cellAc.setCellValue("payment Mode");
        cellAd.setCellValue("Amount");
        cellAe.setCellValue("Balance");

        if(saleInfos.size()>0) {

            for (SaleInfo id : saleInfos)
            {

                HSSFRow rowA = firstSheet.createRow(i);
                HSSFCell cellA = rowA.createCell(0);
                HSSFCell cellB = rowA.createCell(1);
                HSSFCell cellc = rowA.createCell(2);
                HSSFCell celld = rowA.createCell(3);
                HSSFCell celle = rowA.createCell(4);
                cellB.setCellValue(id.getInvoice_id());
                cellA.setCellValue(id.getCreated_Date());
                cellc.setCellValue(id.getModeOfPayment());
                celld.setCellValue(id.getAmountPaid());
                celle.setCellValue(id.getBalanceDue());
                i++;
            }
        }else
        {

            Toast.makeText(context,"Data Not Available",Toast.LENGTH_LONG).show();
            return;
        }

//        HSSFRow rowB = secondSheet.createRow(0);
//        HSSFCell cellB = rowB.createCell(0);
//        cellB.setCellValue(new HSSFRichTextString("Sheet2"));
        FileOutputStream fos = null;
        try {

            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File (sdCard.getAbsolutePath() + "/SaleReports");
            directory.mkdirs();
            File file = new File(directory, fileName+".xls");
//            String str_path = Environment.getExternalStorageDirectory().toString();
//            File file ;
            //  file = new File(str_path, fileName + ".xls");
            fos = new FileOutputStream(file);
            workbook.write(fos);
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(context, "Excel Sheet Generated", Toast.LENGTH_SHORT).show();
        }
    }

    public  static  void testFile(Context context, String fileName, ArrayList<SaleInfo> saleInfos,String Directory) throws  IOException

    {
        int i=1;
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet firstSheet = workbook.createSheet("Sheet1");
        //HSSFSheet secondSheet = workbook.createSheet("Sheet2");
        HSSFRow rowAA = firstSheet.createRow(0);
        HSSFCell cellAA = rowAA.createCell(0);
        HSSFCell cellAB = rowAA.createCell(1);
        HSSFCell cellAc = rowAA.createCell(2);
        HSSFCell cellAd = rowAA.createCell(3);
        HSSFCell cellAe = rowAA.createCell(4);

        HSSFCell cellAf = rowAA.createCell(5);
        HSSFCell cellAg = rowAA.createCell(6);
        HSSFCell cellAh = rowAA.createCell(7);
        HSSFCell cellAi = rowAA.createCell(8);


        cellAA.setCellValue("Created Date");
        cellAB.setCellValue("Invocie Date");
        cellAc.setCellValue("Taxable Value");
        cellAd.setCellValue("Total GST");
        cellAe.setCellValue("Total Amount");

        cellAf.setCellValue("Amount Paid");
        cellAg.setCellValue("Balance");
        cellAh.setCellValue("Payment Mode");
        cellAi.setCellValue("Status");


        if(saleInfos.size()>0)
        {
            for (SaleInfo id : saleInfos)
            {

                HSSFRow rowA = firstSheet.createRow(i);
                HSSFCell cellA = rowA.createCell(0);
                HSSFCell cellB = rowA.createCell(1);
                HSSFCell cellc = rowA.createCell(2);
                HSSFCell celld = rowA.createCell(3);
                HSSFCell celle = rowA.createCell(4);
                HSSFCell cellf = rowA.createCell(5);
                HSSFCell cellg = rowA.createCell(6);
                HSSFCell cellh = rowA.createCell(7);
                HSSFCell celli = rowA.createCell(8);

                cellB.setCellValue(id.getInvoice_id());
                cellA.setCellValue(id.getCreated_Date());
                cellc.setCellValue(id.getTaxable_value());
                celld.setCellValue(id.getTotal_gst());
                celle.setCellValue(id.getTotalAmount());
                cellf.setCellValue(id.getAmountPaid());
                cellg.setCellValue(id.getBalanceDue());
                cellh.setCellValue(id.getModeOfPayment());
                celli.setCellValue(id.getStatus());

                i++;
            }
        }else
        {

            Toast.makeText(context,"Data Not Available",Toast.LENGTH_LONG).show();
            return;
        }

//        HSSFRow rowB = secondSheet.createRow(0);
//        HSSFCell cellB = rowB.createCell(0);
//        cellB.setCellValue(new HSSFRichTextString("Sheet2"));
        FileOutputStream fos = null;
        try {

            File sdCard = Environment.getExternalStorageDirectory();
            File directory = new File (sdCard.getAbsolutePath() + "/SaleReports/"+Directory);
            directory.mkdirs();
            File file = new File(directory, fileName+".xls");
//            String str_path = Environment.getExternalStorageDirectory().toString();
//            File file ;
            //  file = new File(str_path, fileName + ".xls");
            fos = new FileOutputStream(file);
            workbook.write(fos);
            workbook.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (fos != null) {
                try {
                    fos.flush();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(context, "Excel Sheet Generated", Toast.LENGTH_SHORT).show();
        }
    }


}
