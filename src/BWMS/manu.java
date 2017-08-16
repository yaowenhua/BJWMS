package BWMS;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class manu extends JFrame {
	static TextField text1,text2,text3,text4;
	static public JButton bt1,bt2,bt3,bt4;
	
	private static String tempcode="E:\\BWMS\\tempcode.txt";
	private static String seqid="E:\\BWMS\\seqid.txt";
	private static String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String url = "jdbc:sqlserver://192.168.1.242:1433;databaseName=UFDATA_008_2015;user=sa;password=Jsml2013";
	private static String path_getlist = "E:\\BWMS\\result\\getlist";
	private static String path_320 = "E:\\BWMS\\result\\320";
	private static String path_340 = "E:\\BWMS\\result\\340";
	private static String path_910 = "E:\\BWMS\\result\\910";
	private static String path_400 = "E:\\BWMS\\result\\400";
	private static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
    public manu(String s)  
    {     	
        super(s);  
       
        asd.core("getExpDataList",asd.impxml102(),"getlist");
        String[] get = asd.loadget();
		if (get.length == 0)
        {
        	JOptionPane.showInternalMessageDialog(asd.bt_base,"出错！请重试或联系管理员","信息", JOptionPane.INFORMATION_MESSAGE); 		
        }
        else
        {
        	setLayout(new FlowLayout());
        	text1 = new TextField("所需下载的入库单数量：" + get[0], 20); 
        	text2 = new TextField("所需下载的出库单数量：" + get[1], 20); 
        	text3 = new TextField("所需下载的库存数据：" + get[2], 20); 
        	text4 = new TextField("库存调整单：" + get[3], 20); 
        	bt1 = new JButton("下载");
        	bt2 = new JButton("下载");
        	bt3 = new JButton("下载");
        	bt4 = new JButton("下载");
        	
        	add(text1);
        	add(bt1);
        	add(text2);
        	add(bt2);
        	add(text3);
        	add(bt3);
        	add(text4);
        	add(bt4);
        	
        	bt1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	bt1();
                }
            });
        	bt2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	bt2();
                }
            });
        	bt3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	bt3();
                }
            });
        	bt4.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                	bt4();
                }
            });
    		
            setBounds(100, 100, 350, 300);  
            setVisible(true);  
            setResizable(false);
            validate();//确保组件具有有效的布局  
            /*setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
        }
       
          
    } 
    protected void bt1() {
    	int res = JOptionPane.showConfirmDialog(null, "是否确认要全部下载？","是否继续",JOptionPane.YES_NO_OPTION);
		if (res == JOptionPane.YES_OPTION) 
		{
			asd.core("expWmsData",asd.get320(),"320");
	    	/*asd.getxml(path_320);*/
	    	asd.storein(path_320 + "\\" + asd.getxml(path_320));   //参数为指定目录下最后修改的文件名
	    	/*asd.core("getReturnConfirm",asd.su_confirm("320"),"con320"); */  //下载成功，反馈给WMS
	    	/*asd.empty(seqid); */   //清空seqid文件
	    	JOptionPane.showInternalMessageDialog(bt1, "下载成功", "信息",JOptionPane.INFORMATION_MESSAGE);
		} else 
		{
			return;
		}
		try
		{
			asd.su_confirm("320");
			/*asd.core("getReturnConfirm",asd.su_confirm("320"),"con320");*/
			File file = new File(seqid);
			FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
		}
		catch(Exception e)
		{
			JOptionPane.showInternalMessageDialog(bt1, "反馈信息发送失败，请检查", "信息",JOptionPane.INFORMATION_MESSAGE);		
		}
    	
	}
	protected void bt2() {
		int res = JOptionPane.showConfirmDialog(null, "是否确认要全部下载？","是否继续",JOptionPane.YES_NO_OPTION);
		if (res == JOptionPane.YES_OPTION) 
		{
			asd.core("expWmsData",asd.get340(),"340");
	    	/*asd.getxml(path_340);*/
	    	/*asd.storein(path_340 + "\\" + asd.getxml(path_340));*/   //参数为指定目录下最后修改的文件名
	    	JOptionPane.showInternalMessageDialog(bt2, "下载成功", "信息",JOptionPane.INFORMATION_MESSAGE);
		} else 
		{
			return;
		}
		
	}
	protected void bt3() {
		int res = JOptionPane.showConfirmDialog(null, "是否确认要全部下载？", "是否继续",JOptionPane.YES_NO_OPTION);
		if (res == JOptionPane.YES_OPTION) 
		{
			asd.core("expWmsData",asd.get910(),"910");
	    	/*asd.getxml(path_910);*/
	    	asd.stock(path_910 + "\\" + asd.getxml(path_910));   //参数为指定目录下最后修改的文件名
	    	JOptionPane.showInternalMessageDialog(bt3, "下载成功", "信息",JOptionPane.INFORMATION_MESSAGE);
		} else 
		{
			return;
		}	
	}
	protected void bt4() {
		int res = JOptionPane.showConfirmDialog(null, "是否确认要全部下载？", "是否继续",JOptionPane.YES_NO_OPTION);
		if (res == JOptionPane.YES_OPTION) 
		{
			asd.core("expWmsData",asd.get400(),"400");
	    	/*asd.getxml(path_400);*/
	    	/*asd.storein(path_400 + "\\" + asd.getxml(path_400));*/   //参数为指定目录下最后修改的文件名
	    	JOptionPane.showInternalMessageDialog(bt4, "下载成功", "信息",JOptionPane.INFORMATION_MESSAGE);
		} else 
		{
			return;
		}	
	}
	
	public static void main(String[] args){
		
	}
}
