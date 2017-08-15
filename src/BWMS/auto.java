package BWMS;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class auto extends JFrame{

	static TextField text1;
	
	public static JButton bt_start,bt_end;
	public static JTextArea area;
	Timer timer = null;
	
	public auto(String s)  
    {  	
        super(s);  
        setLayout(new FlowLayout()); 
        bt_start = new JButton("开始");
        text1 = new TextField("等待中...", 6); 
       /* text1.setVisible(false);*/
        text1.setEditable(false);  
        bt_end = new JButton("结束");
        area = new JTextArea(12, 20);
        area.setTabSize(4);
        area.setFont(new Font("标楷体", Font.BOLD, 12));
        area.setEditable(false);  
        area.setLineWrap(true);// 激活自动换行功能
        area.setWrapStyleWord(true);// 激活断行不断字功能
               
        JScrollPane jsp = new JScrollPane(area);
        //设置矩形大小.参数依次为(矩形左上角横坐标x,矩形左上角纵坐标y，矩形长度，矩形宽度)
        jsp.setBounds(20, 10, 50, 50);
        //默认的设置是超过文本框才会显示滚动条，以下设置让滚动条一直显示
        jsp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //把滚动条添加到容器里面
        
       /* this.setSize(400, 400);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);*/
        
		add(bt_start); 
		add(bt_end);	
		add(text1); 
		/*add(area);*/
		add(jsp);
				
		bt_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	bt_start();
            }
        });
		bt_end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	bt_end();
            }
        });
		
        setBounds(100, 100, 270, 300);  
        setVisible(true);  
        setResizable(false);
        validate();//确保组件具有有效的布局  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         
    } 
	
	protected void bt_start() {
		timer = new Timer();
		text1.setText("运行中...");
		/*area.append("123");*/
		timer.schedule(new timer(),0,60 * 1000);
	}

	protected void bt_end() {
		text1.setText("已停止...");
		timer.cancel();
		System.gc();
	}

	public static void main(String[] args)
	{
		new auto("auto");
		/*new asd("Interface");*/
	}
	

	
	
}


