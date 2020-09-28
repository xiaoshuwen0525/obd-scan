package com.ruoyi.common.utils.poi;

import com.ruoyi.common.config.Global;
import com.ruoyi.common.core.domain.AjaxResult;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class JavaArryList {



//    public static void main(String[] args) {
//        List workbookList=createlist();
//        //System.out.println(workbookList);
//        try {
//            writeToXls(workbookList);
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//    public static List createlist(){
//        List biaotou=new ArrayList();
//        List resultList=new ArrayList();
//        biaotou.add("name");
//        biaotou.add("id");
//        resultList.add(biaotou);
//        List neirong=new ArrayList();
//        neirong.add("A");
//        neirong.add("AA");
//        resultList.add(neirong);
//        neirong=new ArrayList();//初始化一下，这样之前的值就不会加入
//        neirong.add("b");
//        neirong.add("bb");
//        resultList.add(neirong);
//        neirong=new ArrayList();
//        neirong.add("C");
//        neirong.add("CC");
//        resultList.add(neirong);
//        System.out.print(resultList);
//        return resultList;
//    }
    public static AjaxResult writeToXls(HashMap<Object,List<ArrayList<Object>>> resultMap ){

        if(resultMap.size()==0){
            return AjaxResult.error();
        }

        try {

        //创建一个EXCEL
        Workbook wb = new XSSFWorkbook();

        Map<String, CellStyle> styles = createStyles(wb);

      //  disposeData((List)resultMap.get("herd"),"herd",wb,styles);

        disposeData((List)resultMap.get("detail"),"detail",wb,styles);

        String filename = encodingFilename("order");

        OutputStream   out = new FileOutputStream(getAbsoluteFile(filename));

        //FileOutputStream fileOut = new FileOutputStream("d:\\testa.xls");
        wb.write(out);
        out.close();
        return AjaxResult.success(filename);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.error();
    }

    // disposeData
    public static void disposeData(List resultList,String sheetName,Workbook wb,Map<String, CellStyle> styles){


        if(resultList ==null) {
            return;
        }
        //创建一个SHEET
        Sheet sheet1 = wb.createSheet(sheetName);

        int sheetColumnCount = 0 ;
        for (int i = 0; i < resultList.size(); i++) {

                sheet1.autoSizeColumn(i);

                //创建一行
                Row row = sheet1.createRow(i);
                row.setHeight((short) (20 * 30));
                List rowList=(List)resultList.get(i);
                String fag="";
                int count = 0;
                if(rowList.size()>sheetColumnCount ){
                    sheetColumnCount = rowList.size();
                }
                for (int j = 0; j < rowList.size(); j++) {

                    String value=String.valueOf(rowList.get(j));

                    if(value.equals("\t")){
                        Cell cell = row.createCell(count);
                        count++;
                        if(fag.equals("")||fag==null){
                            value="";
                        }else{
                            value = fag;
                            fag = "";
                        }

                        String cellLiString=String.valueOf(value);
                        cell.setCellStyle(styles.get("data"));
                        cell.setCellValue(cellLiString );
                        continue;
                    }

                    fag = fag + value;

                    if (j != rowList.size()-1) {
                        continue;
                    }

                    Cell cell = row.createCell(count);
                    count++;
                    String cellLiString = fag;
                    cell.setCellStyle(styles.get("data"));
                    cell.setCellValue(cellLiString);
                    fag = "";


                    System.out.println("aa");
                }
            }




        for(int i=0 ;i<sheetColumnCount ;i++){

            sheet1.setColumnWidth(i,256*25);
        }
    }
    /**
     * 编码文件名
     */
    public static String encodingFilename(String filename)
    {
        filename = UUID.randomUUID().toString() + "_" + filename + ".xlsx";
        return filename;
    }

    /**
     * 获取下载路径
     *
     * @param filename 文件名称
     */
    public static String getAbsoluteFile(String filename)
    {
        String downloadPath = Global.getDownloadPath() + filename;
        File desc = new File(downloadPath);
        if (!desc.getParentFile().exists())
        {
            desc.getParentFile().mkdirs();
        }
        return downloadPath;
    }



    /**
     * 创建表格样式
     *
     * @param wb 工作薄对象
     * @return 样式列表
     */
    private static Map<String, CellStyle> createStyles(Workbook wb)
    {
        // 写入各条记录,每条记录对应excel表中的一行
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
//        style.setBorderRight(BorderStyle.THIN);
//        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
//        style.setBorderLeft(BorderStyle.THIN);
//        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
//        style.setBorderTop(BorderStyle.THIN);
//        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
//        style.setBorderBottom(BorderStyle.THIN);
//        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        style.setWrapText(true);
        styles.put("data", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
         styles.put("header", style);

        return styles;
    }
}