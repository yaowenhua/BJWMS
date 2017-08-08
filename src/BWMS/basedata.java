package BWMS;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.secure.asd;

public class basedata extends JFrame {
	
	static TextField text1,text2,text3,text4;
	public static JButton bt_g,bt_h,bt_k,bt1,bt2,bt3;
    public basedata(String s)  
    {  
        super(s);  
        setLayout(new FlowLayout()); 
        bt_g = new JButton("供应商");
        text1 = new TextField(20); 
        bt1 = new JButton("上传");
        bt_k = new JButton("客户");
        text2 = new TextField(20); 
        bt2 = new JButton("上传");
	    bt_h = new JButton("存货");
	    text3 = new TextField(20); 
	    bt3 = new JButton("上传");
		add(bt_g); 
		add(text1); 
		add(bt1);	
		add(bt_k);
		add(text2);
		add(bt2);
		add(bt_h);
		add(text3);
		add(bt3);
		bt_g.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	bt_g();
            }
        });
		bt_k.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	bt_k();
            }
        });
		bt_h.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	bt_h();
            }
        });
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
		
        setBounds(100, 100, 350, 270);  
        setVisible(true);  
        setResizable(false);
        validate();//确保组件具有有效的布局  
        /*setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
          
    } 
    protected void bt1() {
    	JOptionPane.showInternalMessageDialog(bt_g,"暂未开放","信息", JOptionPane.INFORMATION_MESSAGE);
		
	}
	protected void bt2() {
		
		String msg = asd.manucustom(text2.getText());	
		if (msg.equals("")) 
		{
			JOptionPane.showInternalMessageDialog(bt2, "上传失败", "信息",JOptionPane.INFORMATION_MESSAGE);
		} else if (!msg.equals("1"))
		{
			JOptionPane.showInternalMessageDialog(bt2, "上传完成", "信息",JOptionPane.INFORMATION_MESSAGE);			
		}
	}
	protected void bt3() {
		String msg = asd.manuInvent(text3.getText());	
		if (msg.equals("")) 
		{
			JOptionPane.showInternalMessageDialog(bt3, "上传失败", "信息",JOptionPane.INFORMATION_MESSAGE);
		} else if (!msg.equals("1"))
		{
			JOptionPane.showInternalMessageDialog(bt3, "上传完成", "信息",JOptionPane.INFORMATION_MESSAGE);			
		}
		
	}
	protected void bt_g() {
    	JOptionPane.showInternalMessageDialog(bt_g,"暂未开放","信息", JOptionPane.INFORMATION_MESSAGE); 	
	}
	protected void bt_k() {
		int res = JOptionPane.showConfirmDialog(null, "是否确认要全部导入？","是否继续",JOptionPane.YES_NO_OPTION);
		if (res == JOptionPane.YES_OPTION) 
		{
			asd.core("impWmsData",asd.autocustom(),"102");
		} else 
		{
			return;
		}
		
	}
	protected void bt_h() {
		int res = JOptionPane.showConfirmDialog(null, "是否确认要全部导入？", "是否继续",JOptionPane.YES_NO_OPTION);
		if (res == JOptionPane.YES_OPTION) 
		{
			asd.core("impWmsData",asd.autoInvent(),"101");
		} else 
		{
			return;
		}
	
	}
	
	public static void main(String[] args){
		/*new basedata("Interface");*/
	}
}
