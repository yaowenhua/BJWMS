package BWMS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class timer extends TimerTask{
	
	private static String path_320 = "E:\\BWMS\\result\\320";
	private static String path_340 = "E:\\BWMS\\result\\340";
	private static String path_910 = "E:\\BWMS\\result\\910";
	private static String path_400 = "E:\\BWMS\\result\\400";
	@Override
	public void run()     //�Զ��ϴ�
	{
		// TODO �Զ����ɵķ������
		asd.crtfile();   //�����ļ���
		asd.autopo();
		asd.autoso();
		asd.core("expWmsData",asd.get320(),"320");
		asd.storein(path_320 + "\\" + asd.getxml(path_320));
		/*asd.core("getReturnConfirm",asd.su_confirm("320"),"con320"); */  //���سɹ���������WMS
    	/*asd.empty(seqid); */   //���seqid�ļ�
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