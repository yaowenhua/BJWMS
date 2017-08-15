package BWMS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class timer extends TimerTask{

	@Override
	public void run()     //自动上传
	{
		// TODO 自动生成的方法存根
		asd.autopo();
		asd.autoso();
		auto.area.append(ddate()+"\r\n");
		auto.area.setCaretPosition(auto.area.getText().length());  //使textarea自动滚到最末
	}
	
	public static void main(String[] args)
	{
		/*Timer timer = new Timer();
		timer.schedule(new auto(), 6 * 1000,6 * 1000);*/
	}
	
	public static String ddate()               /*获取时间*/
	{
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        return sdf.format(d);
	}
	
	
}