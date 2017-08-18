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
     * ���ǵ�����дEXCEL���  
     * **/    
    public static void writeEx(int row,String[][] data){    
          
        WritableWorkbook wwb = null;       
        Label label = null;       
        String file =writeUrl;    
        try {       
            // ������д��Ĺ���������       
             
            wwb = Workbook.createWorkbook(new File(file));       
            if (wwb != null) {       
                // �ڹ������ﴴ����д��Ĺ�������һ������Ϊ�����������ڶ�������Ϊ�ù����������λ��     
                WritableSheet ws = wwb.createSheet("sheet1", 0);       
                if (ws != null) {       
                    /* ��ӱ�ṹ */      
                    // ��       
                    for (int i=0;i<row;i++) {       
                        // ��       
                        for (int j=0;j<data[i].length;j++) {       
                            // Label����������������������һ��Ϊ�У��ڶ���Ϊ�У���������Ϊ��Ԫ����������    
                              
                            label = new Label(j, i,data[i][j] );       
                            // ����д�����ݵĵ�Ԫ����ӵ�������       
                            ws.addCell(label);       
                        }       
                    }       
                    // ���ڴ���д�뵽�ļ�       
                    wwb.write();       
                }       
                System.out.println("·��Ϊ��" + file + "�Ĺ�����д�����ݳɹ���");       
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
