package BWMS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class timer extends TimerTask{

	@Override
	public void run()     //�Զ��ϴ�
	{
		// TODO �Զ����ɵķ������
		asd.autopo();
		asd.autoso();
		auto.area.append(ddate()+"\r\n");
		auto.area.setCaretPosition(auto.area.getText().length());  //ʹtextarea�Զ�������ĩ
	}
	
	public static void main(String[] args)
	{
		/*Timer timer = new Timer();
		timer.schedule(new auto(), 6 * 1000,6 * 1000);*/
	}
	
	public static String ddate()               /*��ȡʱ��*/
	{
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        return sdf.format(d);
	}
	
	
}