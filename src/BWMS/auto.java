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
        bt_start = new JButton("��ʼ");
        text1 = new TextField("�ȴ���...", 6); 
       /* text1.setVisible(false);*/
        text1.setEditable(false);  
        bt_end = new JButton("����");
        area = new JTextArea(12, 20);
        area.setTabSize(4);
        area.setFont(new Font("�꿬��", Font.BOLD, 12));
        area.setEditable(false);  
        area.setLineWrap(true);// �����Զ����й���
        area.setWrapStyleWord(true);// ������в����ֹ���
               
        JScrollPane jsp = new JScrollPane(area);
        //���þ��δ�С.��������Ϊ(�������ϽǺ�����x,�������Ͻ�������y�����γ��ȣ����ο��)
        jsp.setBounds(20, 10, 50, 50);
        //Ĭ�ϵ������ǳ����ı���Ż���ʾ�����������������ù�����һֱ��ʾ
        jsp.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        //�ѹ�������ӵ���������
        
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
        validate();//ȷ�����������Ч�Ĳ���  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);         
    } 
	
	protected void bt_start() {
		timer = new Timer();
		text1.setText("������...");
		/*area.append("123");*/
		timer.schedule(new timer(),0,60 * 1000);
	}

	protected void bt_end() {
		text1.setText("��ֹͣ...");
		timer.cancel();
		System.gc();
	}

	public static void main(String[] args)
	{
		new auto("auto");
		/*new asd("Interface");*/
	}
	

	
	
}


