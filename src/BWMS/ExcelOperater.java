package BWMS;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelOperater {
	private static String writeUrl ="";  
    public String getWriteUrl() {  
        return writeUrl;  
    }  
  
  
    public void setWriteUrl(String writeUrl) {  
        this.writeUrl = writeUrl;  
    }  
  
  
    public ExcelOperater(String writeUrl ) {  
        // TODO Auto-generated constructor stub  
        this.writeUrl= writeUrl;  
    }  
      
      
    /**  
     *   
     * 这是单纯的写EXCEL表格  
     * **/    
    public static void writeEx(int row,String[][] data){    
          
        WritableWorkbook wwb = null;       
        Label label = null;       
        String file =writeUrl;    
        try {       
            // 创建可写入的工作簿对象       
             
            wwb = Workbook.createWorkbook(new File(file));       
            if (wwb != null) {       
                // 在工作簿里创建可写入的工作表，第一个参数为工作表名，第二个参数为该工作表的所在位置     
                WritableSheet ws = wwb.createSheet("sheet1", 0);       
                if (ws != null) {       
                    /* 添加表结构 */      
                    // 行       
                    for (int i=0;i<row;i++) {       
                        // 列       
                        for (int j=0;j<data[i].length;j++) {       
                            // Label构造器中有三个参数，第一个为列，第二个为行，第三个则为单元格填充的内容    
                              
                            label = new Label(j, i,data[i][j] );       
                            // 将被写入数据的单元格添加到工作表       
                            ws.addCell(label);       
                        }       
                    }       
                    // 从内存中写入到文件       
                    wwb.write();       
                }       
                System.out.println("路径为：" + file + "的工作簿写入数据成功！");       
            }       
        } catch (Exception e) {       
            System.out.println(e.getMessage());       
        } finally {       
            try {     
                wwb.close();    
            } catch (WriteException e) {    
                // TODO Auto-generated catch block    
                e.printStackTrace();    
            } catch (IOException e) {    
                // TODO Auto-generated catch block    
                e.printStackTrace();    
            }       
        }       
    } 
    public static void main(String[] args) throws IOException{  
        /*ExcelOperater we = new ExcelOperater("e:/BWMS/TEST1.xls");     
        String[][] data1 = new String[][]{{"11","12","13","14"},{"21","22","23","24"}};
        we.writeEx(2,data1); */ 
         
    }
}
