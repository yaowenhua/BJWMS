package BWMS;  

import java.awt.FlowLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.namespace.QName;


import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class asd extends JFrame{

	static TextField text1,text2,text3,text4;
	static public JButton bt1,bt2,bt_down,bt_base;
	JDesktopPane jdp;
	JDialog dialog =null;
	
	public asd(String s)  
    {  
        super(s);  
        setLayout(new FlowLayout());  
        text1 = new TextField("����ɹ������ţ�", 12);  
        text1.setEditable(false);  
        text2 = new TextField(20);  
        text3 = new TextField("���뷢�����ţ�", 12);  
        text3.setEditable(false);  
        text4 = new TextField(20); 
        bt1 = new JButton("�ϴ�");
        bt2 = new JButton("�ϴ�");
        bt_down = new JButton("�����������ݲ�ѯ");
        bt_base = new JButton("��������");
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
        bt_down.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	bt_down();
            }
        });
        bt_base.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	bt_base();
            }
        });
        add(text1);  
        add(text2);  
        add(bt1); 
        add(text3); 
        add(text4); 
        add(bt2); 
        add(bt_down); 
        add(bt_base); 
        setBounds(80, 80, 370, 270);  
          
        setVisible(true);  
        setResizable(false);
        validate();//ȷ�����������Ч�Ĳ���  
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);          
    }
	
	private static String tempcode="E:\\BWMS\\tempcode.txt";
	private static String bwmssql="E:\\BWMS\\bwmssql.txt";
	private static String seqid="E:\\BWMS\\seqid.txt";
	private static String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static String url = "jdbc:sqlserver://192.168.1.242:1433;databaseName=UFDATA_008_2015;user=sa;password=Jsml2013";
	private static String path_getlist = "E:\\BWMS\\result\\getlist";
	private static String path_320 = "E:\\BWMS\\result\\320";
	private static SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static String log = "E:\\BWMS\\log.txt";
	
	public static void main(String[] args) {
		
		crtfile();
		new asd("Interface");
		
		
		
		/*new auto("auto");*/
		/*System.out.println(log());*/
		/*core("impWmsData",impxml102());*/
		/*core("getExpDataList",impxml102());*/
		/*core("getLogInfo",getLogInfo(),"log");*/
		
		/*core("expWmsData",get910(),"910");*/
	
		/*core("impWmsData",asxml("E:\\BWMS\\302\\2017-08-03 14.20.01auto302.xml"),"302");*/
		
        /*core("impWmsData",autoInvent(),"101");*/
		
		
	/*	String path="E:\\BWMS\\301\\2017-07-26 crt301.xml";
		core("impWmsData",crtxml301_1());*/
		
		/*core("expWmsData",get320(),"320");*/
		/*core("getReturnConfirm",su_confirm("320"),"con320");*/
	}
	
	public static String core(String methodName,String xml,String type)/*�����ϴ����������������ϴ����ݣ��������ͣ�*/
	{
		String path = "";
		String serviceName = "https://jstqxkhz.jst-wl.cn:8092/WmsWebService/services/wmsservice?wsdl";
		String namespace = "http://webservice.fms.com";
		/*String methodName = "impWmsData";*/
		/*String methodName = "getExpDataList";*/
		System.setProperty("javax.net.ssl.trustStore","e:\\BWMS\\test.keystore");
		
		Object[] methodArgs = new Object[] {xml};
		Class[] returnTypes = new Class[] { String.class };
		try {
			ProtocolSocketFactory fcty = new MySecureProtocolSocketFactory();  
			Protocol.registerProtocol("https", new Protocol("https", fcty, 443));  
			RPCServiceClient serviceClient = new RPCServiceClient();
			// ��������OMElement������õ�Header��
			serviceClient.addHeader(HeaderOMElement.createHeaderOMElement());
			Options options = serviceClient.getOptions();
			EndpointReference targetEPR = new EndpointReference(serviceName);
			options.setTo(targetEPR);
			options.setManageSession(true);   
		    options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT,true); 
			QName op = new QName(namespace, methodName);
			Object[] response = serviceClient.invokeBlocking(op, methodArgs,returnTypes);
			String result = response[0].toString();
			if (result == null) {
				System.out.println("didn't initialize!");
				return "";
			} else {
				System.out.println("result ====== " + result);
				switch (type)
				{
				case "getlist" :path="E:\\BWMS\\result\\getlist\\result" + ddate() + ".xml";savexml(result,path);break;
				case "320" :path="E:\\BWMS\\result\\320\\result" + ddate() + ".xml";savexml(result,path);break;
				case "340" :path="E:\\BWMS\\result\\340\\result" + ddate() + ".xml";savexml(result,path);break;
				case "910" :path="E:\\BWMS\\result\\910\\result" + ddate() + ".xml";savexml(result,path);break;
				case "400" :path="E:\\BWMS\\result\\400\\result" + ddate() + ".xml";savexml(result,path);break;
				case "101" :path="E:\\BWMS\\result\\101\\result" + ddate() + ".xml";savexml(result,path);break;
				case "102" :path="E:\\BWMS\\result\\102\\result" + ddate() + ".xml";savexml(result,path);break;
				case "301" :path="E:\\BWMS\\result\\301\\result" + ddate() + ".xml";savexml(result,path);break;
				case "302" :path="E:\\BWMS\\result\\302\\result" + ddate() + ".xml";savexml(result,path);break;
				case "con320" :path="E:\\BWMS\\result\\confirm\\320\\" + ddate() + ".xml";savexml(result,path);break;
				case "con340" :path="E:\\BWMS\\result\\confirm\\340\\" + ddate() + ".xml";savexml(result,path);break;
				case "con910" :path="E:\\BWMS\\result\\confirm\\910\\" + ddate() + ".xml";savexml(result,path);break;
				case "con400" :path="E:\\BWMS\\result\\confirm\\400\\" + ddate() + ".xml";savexml(result,path);break;
				case "log" :path="E:\\BWMS\\log\\log" + ddate() + ".xml";savexml(result,path);break;
				default :path="E:\\BWMS\\other\\result" + ddate() + ".xml";savexml(result,path);break;						
				}
				return result;
			}
		} catch (AxisFault e) {
			System.out.println("��֤ʧ��");
			e.printStackTrace();
		}
		return "";
	}

	public static String asxml(String path)      /*����Ŀ¼��ȡxml�ļ�������String*/
	{
		SAXReader reader = new SAXReader();
		String xml ="";
		try
		{
			Document doc = reader.read(new File(path));
			xml = doc.asXML();
		}
     	catch(Exception e)
     	{
     		e.printStackTrace();
     	}
		return xml;
	}
	
	public static String ddate()               /*��ȡʱ��*/
	{
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
        return sdf.format(d);
	}
	
	public static String impxml102()               
	{					
		SAXReader reader = new SAXReader();
		String xml102 ="";
		try
		{
			Document doc = reader.read(new File("E:\\BWMS\\102\\102.xml"));
			xml102 = doc.asXML();
		}
     	catch(Exception e)
     	{
     		e.printStackTrace();
     	}
		
		String xml301= "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<result class=\"object\">"+
				"<datatype>301</datatype>"+
				"<totalProperty>1</totalProperty>"+
				"<doc class=\"array\">"+
					"<e class=\"object\">"+
					"<porderno>20170719</porderno>"+
		            "<credate>2017-7-19 10:25:48</credate>"+
		            "<arrivedate>2017-7-19 10:25:48</arrivedate>"+
		            "<medicineclass>1</medicineclass>"+
		            "<importflag>1</importflag>"+
		            "<operationtype>1</operationtype>"+
		            "<dtllines>1</dtllines>"+
		            "<memo>beizhu</memo>"+
		            "<srcno>20170719001</srcno>"+
		            "<exporderid></exporderid>"+
		            "<billfrom>3</billfrom>"+
		            "<usestatus>2</usestatus>"+
		            "<mulrecflag></mulrecflag>"+
		            "<reccount></reccount>"+
		            "<inmode>0</inmode>"+
		            "<invalidate>2017-8-19 10:25:48</invalidate>"+
		            "<sabackdate></sabackdate>"+
		            "<sbreasonid></sbreasonid>"+
		            "<deptno></deptno>"+
						"<deptname></deptname>"+
						"<gcompanyid>GA01</gcompanyid>"+
						"<decisionflag></decisionflag>"+
						"<bmsdocid></bmsdocid>"+
						"<nowlflag>0</nowlflag>"+
						"<transclass>1</transclass>"+
						"<ecodeimpflag></ecodeimpflag>"+
						"<sysmodifydate>2017-07-19 00:00:00</sysmodifydate>"+
						"<srcporderid>123001</srcporderid>"+
						"<companystyle>2</companystyle>"+
						"<transmode></transmode>"+
						"<erptransid></erptransid>"+
						"<goodsownerid>521</goodsownerid>"+
						"<warehid>3</warehid>"+
						"<relevanceno>521201707190011</relevanceno>"+
						 "<dtl class=\"array\">"+
			                "<e class=\"object\">"+
			                    "<porderstatus>1</porderstatus>"+
			                    "<batchno>PP556</batchno>"+
			                    "<lotno></lotno>"+
			                    "<goodsqty>12</goodsqty>"+
			                    "<tradegoodsqty>12</tradegoodsqty>"+
			                    "<tradegoodspack>��</tradegoodspack>"+
			                    "<memo>����1</memo>"+
			                    "<arrivedate></arrivedate>"+
			                    "<checkdate></checkdate>"+
			                    "<srcdtlno>071901</srcdtlno>"+
			                    "<lotflag>1</lotflag>"+
			                    "<batchflag>1</batchflag>"+
			                    "<gtradepack>��</gtradepack>"+
			                    "<originqty>12</originqty>"+
			                    "<price></price>"+
			                    "<retailprice></retailprice>"+
			                    "<amt></amt>"+
			                    "<qty></qty>"+
			                    "<quadqty></quadqty>"+
			                    "<unquadqty></unquadqty>"+
			                    "<testqty></testqty>"+
			                    "<sadtlid></sadtlid>"+
			                    "<expdtlmemo></expdtlmemo>"+
			                    "<goodsstatusid></goodsstatusid>"+
			                    "<quantitystatus></quantitystatus>"+
			                    "<validdate></validdate>"+
			                    "<proddate></proddate>"+
			                    "<approvedocno></approvedocno>"+
			                    "<firstflag></firstflag>"+
			                    "<tpricetick></tpricetick>"+
			                    "<tpriceflag></tpriceflag>"+
			                    "<packsize>1</packsize>"+
			                    "<packname>1</packname>"+
			                    "<prtclass>1</prtclass>"+
			                    "<srcgoodsqty>12</srcgoodsqty>"+
			                    "<erpgoodsid>1</erpgoodsid>"+
			                    "<lotid>1</lotid>"+
			                    "<srcno>20170719001</srcno>"+
			                    "<warehid>3</warehid>"+
			                    "<killlotno></killlotno>"+
			                    "<relevanceno>521201707190011</relevanceno>"+
			                "</e>"+
			            "</dtl>"+
					"</e>"+
				"</doc>"+
			"</result>";

		String xml320 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<result class=\"object\">"+
				"<datatype>320</datatype>"+
				"<start>0</start>"+
				"<limit>100</limit>"+
				"</result>";
		return xml102;		
	}

	public static String crtxml101()           /*doc��ʽ����101����Ʒ�������ݣ�*/
	{ 
		String path = "E:\\BWMS\\101\\" + ddate() + "crt101.xml";
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("result");
	    document.setRootElement(root);
	    root.addAttribute("class", "object");
	    
	    Element Element1 = root.addElement("datatype");
        Element Element2 = root.addElement("totalProperty");
        Element Element3 = root.addElement("doc");
        Element1.setText("101");
        Element2.setText("1");
        Element3.addAttribute("class", "array");
        Element Elementdoc = Element3.addElement("e");
        Elementdoc.addAttribute("class", "object");
        
        Element Elementdocegoodsownerid = Elementdoc.addElement("goodsownerid");
        Element Elementdocegoodsownid = Elementdoc.addElement("goodsownid");
        Element Elementdocebarcode = Elementdoc.addElement("barcode");
        Element Elementdocegoodsno = Elementdoc.addElement("goodsno");
        Element Elementdocegopcode = Elementdoc.addElement("gopcode");
        Element Elementdocegopinyin = Elementdoc.addElement("gopinyin");
        Element Elementdocepoisondrug = Elementdoc.addElement("poisondrug");
        Element Elementdocegoodsname = Elementdoc.addElement("goodsname");
        Element Elementdocegoodsengname = Elementdoc.addElement("goodsengname");
        Element Elementdocegoodsformalname = Elementdoc.addElement("goodsformalname");
        Element Elementdocegoodstype = Elementdoc.addElement("goodstype");
        Element Elementdoceprodarea = Elementdoc.addElement("prodarea");
        Element Elementdocefactname = Elementdoc.addElement("factname");
        Element Elementdocetradepack = Elementdoc.addElement("tradepack");
        Element Elementdoceoutpack = Elementdoc.addElement("outpack");
        Element Elementdoceaddmedcheckflag = Elementdoc.addElement("addmedcheckflag");
        Element Elementdocepasteflag = Elementdoc.addElement("pasteflag");
        Element Elementdocewholesaleprice = Elementdoc.addElement("wholesaleprice");
        Element Elementdoceresaleprice = Elementdoc.addElement("resaleprice");
        Element Elementdocepurchasetax = Elementdoc.addElement("purchasetax");
        Element Elementdocesaletax = Elementdoc.addElement("saletax");
        Element Elementdocelotflag = Elementdoc.addElement("lotflag");
        Element Elementdocebatchflag = Elementdoc.addElement("batchflag");
        Element Elementdocememo = Elementdoc.addElement("memo");
        Element Elementdocedrugform = Elementdoc.addElement("drugform");
        Element Elementdocestatus = Elementdoc.addElement("status");
        Element Elementdoceotcflag = Elementdoc.addElement("otcflag");
        Element Elementdocetrademark = Elementdoc.addElement("trademark");
        Element Elementdocestoragecondition = Elementdoc.addElement("storagecondition");
        Element Elementdoceapprovedocno = Elementdoc.addElement("approvedocno");
        Element Elementdocepacksize = Elementdoc.addElement("packsize");
        Element Elementdocedbcheck  = Elementdoc.addElement("dbcheck ");
        Element Elementdoceperiodunit = Elementdoc.addElement("periodunit");
        Element Elementdocevalidperiod = Elementdoc.addElement("validperiod");
        Element Elementdocevarietyname = Elementdoc.addElement("varietyname");
        Element Elementdocesupplyername = Elementdoc.addElement("supplyername");
        Element Elementdoceregistdocno = Elementdoc.addElement("registdocno");
        Element Elementdocevarietydescid = Elementdoc.addElement("varietydescid");
        Element Elementdocefirstindate = Elementdoc.addElement("firstindate");
        Element Elementdocecentrepicksize = Elementdoc.addElement("centrepicksize");
        Element Elementdoceseqid = Elementdoc.addElement("seqid");
        Element ElementdocecreDate = Elementdoc.addElement("creDate");
        Element Elementdocealonepackflag = Elementdoc.addElement("alonepackflag");
        Element Elementdocedictno = Elementdoc.addElement("dictno");
        Element Elementdocefilegroundid = Elementdoc.addElement("filegroundid");
        Element Elementdocegmpflag = Elementdoc.addElement("gmpflag");
        Element Elementdocejkhzflag = Elementdoc.addElement("jkhzflag");
        Element Elementdocesignature = Elementdoc.addElement("signature");
        Element Elementdocespecailflag = Elementdoc.addElement("specailflag");
        Element Elementdoceboxnoflag = Elementdoc.addElement("boxnoflag");
        Element Elementdocedbincheck = Elementdoc.addElement("dbincheck");
        Element Elementdocedbpickflag = Elementdoc.addElement("dbpickflag");
        Element Elementdoceecodeflag = Elementdoc.addElement("ecodeflag");
        Element Elementdoceemgoodsflag = Elementdoc.addElement("emgoodsflag");
        Element Elementdoceemgoodsreason = Elementdoc.addElement("emgoodsreason");
        Element Elementdoceprodlicenseno = Elementdoc.addElement("prodlicenseno");
        Element Elementdoceqtygene = Elementdoc.addElement("qtygene");
        Element Elementdocerepflag = Elementdoc.addElement("repflag");
        Element Elementdocecredate = Elementdoc.addElement("credate");
        Element Elementdocegoodsstatus = Elementdoc.addElement("goodsstatus");
        Element Elementdocetradepacknameid = Elementdoc.addElement("tradepacknameid");
        Element Elementdocegoodsid = Elementdoc.addElement("goodsid");
        Element Elementdoceownergoodsid = Elementdoc.addElement("ownergoodsid");
        Element Elementdoceoutpacknameid = Elementdoc.addElement("outpacknameid");
        Element Elementdocetradegoodspackid = Elementdoc.addElement("tradegoodspackid");
        Element Elementdoceoutgoodspackid = Elementdoc.addElement("outgoodspackid");
        Element Elementdocestoragetype = Elementdoc.addElement("storagetype");
        Element Elementdocesysmodifydate = Elementdoc.addElement("sysmodifydate");
        Element Elementdocefactid = Elementdoc.addElement("factid");
        Element Elementdoceclassname = Elementdoc.addElement("classname");
        Element Elementdocetradegoodslength = Elementdoc.addElement("tradegoodslength");
        Element Elementdocetradegoodswidth = Elementdoc.addElement("tradegoodswidth");
        Element Elementdocetradegoodsheight = Elementdoc.addElement("tradegoodsheight");
        Element Elementdocetradegoodscubage = Elementdoc.addElement("tradegoodscubage");
        Element Elementdocetradegoodsweight = Elementdoc.addElement("tradegoodsweight");
        Element Elementdocetradeweightunit = Elementdoc.addElement("tradeweightunit");
        Element Elementdoceoutgoodslength = Elementdoc.addElement("outgoodslength");
        Element Elementdoceoutgoodswidth = Elementdoc.addElement("outgoodswidth");
        Element Elementdoceoutgoodsheight = Elementdoc.addElement("outgoodsheight");
        Element Elementdoceoutgoodscubage = Elementdoc.addElement("outgoodscubage");
        Element Elementdoceoutgoodsweight = Elementdoc.addElement("outgoodsweight");
        Element Elementdoceoutweightunit = Elementdoc.addElement("outweightunit");
        Element Elementdocelotnocannullflag = Elementdoc.addElement("lotnocannullflag");
        Element Elementdocewarehid = Elementdoc.addElement("warehid");
        Element Elementdocepackscodeflag = Elementdoc.addElement("packscodeflag");
        Element Elementdoceregiststartdate = Elementdoc.addElement("registstartdate");
        Element Elementdoceregistenddate = Elementdoc.addElement("registenddate");
        Element Elementdocetemplow = Elementdoc.addElement("templow");
        Element Elementdocetemphigh = Elementdoc.addElement("temphigh");
        Element Elementdocegoodsmodel = Elementdoc.addElement("goodsmodel");
        Element Elementdocescopeno = Elementdoc.addElement("scopeno");


        Elementdocegoodsownerid.setText("521");
        Elementdocegoodsownid.setText("301803MS");
        Elementdocebarcode.setText("");
        Elementdocegoodsno.setText("301803MS");
        Elementdocegopcode.setText("135");
        Elementdocegopinyin.setText("MS");
        Elementdocepoisondrug.setText("2");
        Elementdocegoodsname.setText("�����ʺͽ���������");
        Elementdocegoodsengname.setText("");
        Elementdocegoodsformalname.setText("");
        Elementdocegoodstype.setText("�๦�ܶ�����620mmPreface�����ʣ���675mm��������1500mm��˿");
        Elementdoceprodarea.setText("����");
        Elementdocefactname.setText("ǿ�����Ϻ���ҽ���������޹�˾");
        Elementdocetradepack.setText("��");
        Elementdoceoutpack.setText("��");
        Elementdoceaddmedcheckflag.setText("1");
        Elementdocepasteflag.setText("0");
        Elementdocewholesaleprice.setText("");
        Elementdoceresaleprice.setText("");
        Elementdocepurchasetax.setText("");
        Elementdocesaletax.setText("");
        Elementdocelotflag.setText("");
        Elementdocebatchflag.setText("");
        Elementdocememo.setText("");
        Elementdocedrugform.setText("");
        Elementdocestatus.setText("");
        Elementdoceotcflag.setText("");
        Elementdocetrademark.setText("");
        Elementdocestoragecondition.setText("1");
        Elementdoceapprovedocno.setText("");
        Elementdocepacksize.setText("1");
        Elementdocedbcheck .setText("");
        Elementdoceperiodunit.setText("");
        Elementdocevalidperiod.setText("");
        Elementdocevarietyname.setText("");
        Elementdocesupplyername.setText("");
        Elementdoceregistdocno.setText("��ʳҩ��е��������2013��3774056��");
        Elementdocevarietydescid.setText("");
        Elementdocefirstindate.setText("");
        Elementdocecentrepicksize.setText("");
        Elementdoceseqid.setText("10101");
        ElementdocecreDate.setText("");
        Elementdocealonepackflag.setText("");
        Elementdocedictno.setText("");
        Elementdocefilegroundid.setText("");
        Elementdocegmpflag.setText("");
        Elementdocejkhzflag.setText("");
        Elementdocesignature.setText("");
        Elementdocespecailflag.setText("");
        Elementdoceboxnoflag.setText("");
        Elementdocedbincheck.setText("");
        Elementdocedbpickflag.setText("");
        Elementdoceecodeflag.setText("");
        Elementdoceemgoodsflag.setText("");
        Elementdoceemgoodsreason.setText("");
        Elementdoceprodlicenseno.setText("");
        Elementdoceqtygene.setText("");
        Elementdocerepflag.setText("");
        Elementdocecredate.setText("");
        Elementdocegoodsstatus.setText("");
        Elementdocetradepacknameid.setText("");
        Elementdocegoodsid.setText("");
        Elementdoceownergoodsid.setText("");
        Elementdoceoutpacknameid.setText("");
        Elementdocetradegoodspackid.setText("");
        Elementdoceoutgoodspackid.setText("");
        Elementdocestoragetype.setText("");
        Elementdocesysmodifydate.setText("2017-07-21 00:00:00");
        Elementdocefactid.setText("");
        Elementdoceclassname.setText("");
        Elementdocetradegoodslength.setText("");
        Elementdocetradegoodswidth.setText("");
        Elementdocetradegoodsheight.setText("");
        Elementdocetradegoodscubage.setText("");
        Elementdocetradegoodsweight.setText("");
        Elementdocetradeweightunit.setText("");
        Elementdoceoutgoodslength.setText("");
        Elementdoceoutgoodswidth.setText("");
        Elementdoceoutgoodsheight.setText("");
        Elementdoceoutgoodscubage.setText("");
        Elementdoceoutgoodsweight.setText("");
        Elementdoceoutweightunit.setText("");
        Elementdocelotnocannullflag.setText("");
        Elementdocewarehid.setText("3");
        Elementdocepackscodeflag.setText("");
        Elementdoceregiststartdate.setText("");
        Elementdoceregistenddate.setText("");
        Elementdocetemplow.setText("");
        Elementdocetemphigh.setText("");
        Elementdocegoodsmodel.setText("");
        Elementdocescopeno.setText("");


        XMLWriter xmlWriter;
		try {
			xmlWriter = new XMLWriter(new FileOutputStream(path));
			xmlWriter.write(document);
		    xmlWriter.flush();
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		return asxml(path);
	}
	
	public static String crtxml102()           /*doc��ʽ����102����Ӧ��&�ͻ��������ݣ�*/
	{
		String path = "E:\\BWMS\\102\\"+ ddate() + "crt102.xml" ;
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("result");
	    document.setRootElement(root);
	    root.addAttribute("class", "object");
	    
	    Element Element1 = root.addElement("datatype");
        Element Element2 = root.addElement("totalProperty");
        Element Element3 = root.addElement("doc");
        Element1.setText("102");
        Element2.setText("1");
        Element3.addAttribute("class", "array");
        Element Elementdoce = Element3.addElement("e");
        Elementdoce.addAttribute("class", "object");
              
        Element Elementdocegoodsownerid =Elementdoce.addElement("goodsownerid");
        Element Elementdocegcompanyid =Elementdoce.addElement("gcompanyid");
        Element Elementdocecompanystype =Elementdoce.addElement("companystype");
        Element Elementdocecompanyname =Elementdoce.addElement("companyname");
        Element Elementdocegopinyin =Elementdoce.addElement("gopinyin");
        Element Elementdoceopcode =Elementdoce.addElement("opcode");
        Element Elementdocecompanyshortname =Elementdoce.addElement("companyshortname");
        Element Elementdocebank =Elementdoce.addElement("bank");
        Element Elementdocebankno =Elementdoce.addElement("bankno");
        Element Elementdocechiefofficer =Elementdoce.addElement("chiefofficer");
        Element Elementdoceconnector =Elementdoce.addElement("connector");
        Element Elementdoceconnphone =Elementdoce.addElement("connphone");
        Element Elementdocedeliveraddr =Elementdoce.addElement("deliveraddr");
        Element Elementdoceinvoiceaddr =Elementdoce.addElement("invoiceaddr");
        Element Elementdoceinvoicehead =Elementdoce.addElement("invoicehead");
        Element Elementdoceinvoiceman =Elementdoce.addElement("invoiceman");
        Element Elementdoceinvoicephone =Elementdoce.addElement("invoicephone");
        Element Elementdocepostcode =Elementdoce.addElement("postcode");
        Element Elementdocespecialrequire =Elementdoce.addElement("specialrequire");
        Element Elementdocetaxregistno =Elementdoce.addElement("taxregistno");
        Element Elementdocetelephone =Elementdoce.addElement("telephone");
        Element Elementdocememo =Elementdoce.addElement("memo");
        Element Elementdoceaddmedcheckflag =Elementdoce.addElement("addmedcheckflag");
        Element Elementdoceusestatus =Elementdoce.addElement("usestatus");
        Element Elementdocecompanyno =Elementdoce.addElement("companyno");
        Element Elementdoceorgno =Elementdoce.addElement("orgno");
        Element Elementdoceimpflag =Elementdoce.addElement("impflag");
        Element Elementdoceseqid =Elementdoce.addElement("seqid");
        Element ElementdocecreDate =Elementdoce.addElement("creDate");
        Element Elementdocecity =Elementdoce.addElement("city");
        Element Elementdocecorpid =Elementdoce.addElement("corpid");
        Element Elementdocegoodsownerflag =Elementdoce.addElement("goodsownerflag");
        Element Elementdocelegalpersion =Elementdoce.addElement("legalpersion");
        Element Elementdoceunifyno =Elementdoce.addElement("unifyno");
        Element Elementdocedefrtanid =Elementdoce.addElement("defrtanid");
        Element Elementdoceinvclass =Elementdoce.addElement("invclass");
        Element Elementdoceinvprintplan =Elementdoce.addElement("invprintplan");
        Element Elementdoceinvtype =Elementdoce.addElement("invtype");
        Element Elementdocemodedocid =Elementdoce.addElement("modedocid");
        Element Elementdoceorderflag =Elementdoce.addElement("orderflag");
        Element Elementdoceperiodlimit =Elementdoce.addElement("periodlimit");
        Element Elementdoceperiodlimtunit =Elementdoce.addElement("periodlimtunit");
        Element Elementdoceprintflag =Elementdoce.addElement("printflag");
        Element Elementdoceprintplan =Elementdoce.addElement("printplan");
        Element Elementdocecompanycredate =Elementdoce.addElement("companycredate");
        Element Elementdocelotlimit =Elementdoce.addElement("lotlimit");
        Element Elementdocesysmodifydate =Elementdoce.addElement("sysmodifydate");
        Element Elementdocewarehid =Elementdoce.addElement("warehid");

        Elementdocegoodsownerid.setText("521");
        Elementdocegcompanyid.setText("30001");
        Elementdocecompanystype.setText("1");
        Elementdocecompanyname.setText("�Ϻ�����ҽ����е���޹�˾");
        Elementdocegopinyin.setText("");
        Elementdoceopcode.setText("135");
        Elementdocecompanyshortname.setText("�Ϻ�����ҽ����е���޹�˾");
        Elementdocebank.setText("");
        Elementdocebankno.setText("");
        Elementdocechiefofficer.setText("");
        Elementdoceconnector.setText("");
        Elementdoceconnphone.setText("");
        Elementdocedeliveraddr.setText("");
        Elementdoceinvoiceaddr.setText("");
        Elementdoceinvoicehead.setText("");
        Elementdoceinvoiceman.setText("");
        Elementdoceinvoicephone.setText("");
        Elementdocepostcode.setText("");
        Elementdocespecialrequire.setText("");
        Elementdocetaxregistno.setText("");
        Elementdocetelephone.setText("");
        Elementdocememo.setText("");
        Elementdoceaddmedcheckflag.setText("");
        Elementdoceusestatus.setText("");
        Elementdocecompanyno.setText("30001");
        Elementdoceorgno.setText("20149004");
        Elementdoceimpflag.setText("1");
        Elementdoceseqid.setText("03");
        ElementdocecreDate.setText("");
        Elementdocecity.setText("");
        Elementdocecorpid.setText("");
        Elementdocegoodsownerflag.setText("0");
        Elementdocelegalpersion.setText("");
        Elementdoceunifyno.setText("");
        Elementdocedefrtanid.setText("0");
        Elementdoceinvclass.setText("1");
        Elementdoceinvprintplan.setText("");
        Elementdoceinvtype.setText("1");
        Elementdocemodedocid.setText("0");
        Elementdoceorderflag.setText("1");
        Elementdoceperiodlimit.setText("5");
        Elementdoceperiodlimtunit.setText("1");
        Elementdoceprintflag.setText("0");
        Elementdoceprintplan.setText("");
        Elementdocecompanycredate.setText("2017-07-21 00:00:00");
        Elementdocelotlimit.setText("3");
        Elementdocesysmodifydate.setText("2017-07-21 00:00:00");
        Elementdocewarehid.setText("3");

        Element Elementdocedtl =Elementdoce.addElement("dtl");
        Elementdocedtl.addAttribute("class", "array");
        Element Elementdocedtle =Elementdocedtl.addElement("e");
        Elementdocedtle.addAttribute("class", "object");
        
        Element Elementdocedtlegoodsownerid =Elementdocedtle.addElement("goodsownerid");
        Element Elementdocedtlegocompanyid =Elementdocedtle.addElement("gocompanyid");
        Element Elementdocedtletransid =Elementdocedtle.addElement("transid");
        Element Elementdocedtleinceptaddr =Elementdocedtle.addElement("inceptaddr");
        Element Elementdocedtleconnector =Elementdocedtle.addElement("connector");
        Element Elementdocedtleconnphone =Elementdocedtle.addElement("connphone");
        Element Elementdocedtletelefax =Elementdocedtle.addElement("telefax");
        Element Elementdocedtlepostcode =Elementdocedtle.addElement("postcode");
        Element Elementdocedtlememo =Elementdocedtle.addElement("memo");
        Element Elementdocedtleusestatus =Elementdocedtle.addElement("usestatus");
        Element Elementdocedtletranposname =Elementdocedtle.addElement("tranposname");
        Element Elementdocedtleimpflag =Elementdocedtle.addElement("impflag");
        Element Elementdocedtleseqid =Elementdocedtle.addElement("seqid");
        Element ElementdocedtlecreDate =Elementdocedtle.addElement("creDate");
        Element Elementdocedtlecity =Elementdocedtle.addElement("city");
        Element Elementdocedtleequtransid =Elementdocedtle.addElement("equtransid");
        Element Elementdocedtlesysmodifydate =Elementdocedtle.addElement("sysmodifydate");
        Element Elementdocedtlecompanystype =Elementdocedtle.addElement("companystype");
        Element Elementdocedtlewarehid =Elementdocedtle.addElement("warehid");

        Elementdocedtlegoodsownerid.setText("521");
        Elementdocedtlegocompanyid.setText("30001");
        Elementdocedtletransid.setText("01");
        Elementdocedtleinceptaddr.setText("�Ϻ���������ؼ��·201��303��");
        Elementdocedtleconnector.setText("���ŷ� ");
        Elementdocedtleconnphone.setText("64669879 13816013927");
        Elementdocedtletelefax.setText("");
        Elementdocedtlepostcode.setText("111");
        Elementdocedtlememo.setText("");
        Elementdocedtleusestatus.setText("1");
        Elementdocedtletranposname.setText("");
        Elementdocedtleimpflag.setText("1");
        Elementdocedtleseqid.setText("10301");
        ElementdocedtlecreDate.setText("");
        Elementdocedtlecity.setText("");
        Elementdocedtleequtransid.setText("0");
        Elementdocedtlesysmodifydate.setText("2017-07-17 00:00:00");
        Elementdocedtlecompanystype.setText("1");
        Elementdocedtlewarehid.setText("3");

        
        XMLWriter xmlWriter;
      		try {
      			xmlWriter = new XMLWriter(new FileOutputStream(path));
      			xmlWriter.write(document);
      		    xmlWriter.flush();
      		} catch (IOException e) {
      			// TODO �Զ����ɵ� catch ��
      			e.printStackTrace();
      		}
      		return asxml(path);
	}
	
	/*public static String crtxml301()
	{
		String path = "E:\\BWMS\\301\\"+ ddate() + "crt103.xml" ;
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("result");
	    document.setRootElement(root);
	    root.addAttribute("class", "object");
	    
	    Element Element1 = root.addElement("datatype");
        Element Element2 = root.addElement("totalProperty");
        Element Element3 = root.addElement("doc");
        Element1.setText("301");
        Element2.setText("1");
        Element3.addAttribute("class", "array");
        Element Elementdoce = Element3.addElement("e");
        Elementdoce.addAttribute("class", "object");
        
        Element Elementdocegoodsownerid =Elementdoce.addElement("goodsownerid");
        Element Elementdocegcompanyid =Elementdoce.addElement("gcompanyid");
        Element Elementdocecompanystype =Elementdoce.addElement("companystype");
        Element Elementdocecompanyname =Elementdoce.addElement("companyname");
	}
	*/
	
	
	public static String crtxml301_1()         /*String��ʽ����301���ɹ�������*/
	{
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = sdf.format(d);
        
		String xml301="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<result class=\"object\">"+
				"<datatype>301</datatype>"+            /*��������*/
				"<totalProperty>1</totalProperty>"+    /*��������*/
				"<doc class=\"array\">"+
					"<e class=\"object\">"+
					"<porderno>dingdan003</porderno>"+    /*����������*/
		"<credate>" + datetime + "</credate>"+           /*�Ƶ�����*/
		"<arrivedate>" + datetime + "</arrivedate>"+
		"<medicineclass>1</medicineclass>"+          
		"<importflag>1</importflag>"+
		"<operationtype>1</operationtype>"+             /*ҵ������ 1��������7�����ˣ�22������*/
		"<dtllines>1</dtllines>"+  
		"<memo>beizhu03</memo>"+                       /*��ע*/
		"<srcno>20170726003</srcno>"+                  /*������*/
		"<exporderid>003</exporderid>"+              
		"<billfrom>3</billfrom>"+
		"<usestatus>2</usestatus>"+
		"<mulrecflag>1</mulrecflag>"+
		"<reccount>1</reccount>"+
		"<inmode>0</inmode>"+
		"<invalidate>" + datetime + "</invalidate>"+
		"<sabackdate></sabackdate>"+                      /*�˻�����*/
		"<sbreasonid></sbreasonid>"+                       /*�˻�ԭ��ID*/
		"<deptno>1</deptno>"+
						"<deptname>1</deptname>"+
						"<gcompanyid>001</gcompanyid>"+      /*��Ӧ��ID��*/
						"<decisionflag>1</decisionflag>"+    
						"<bmsdocid>1</bmsdocid>"+
						"<nowlflag>0</nowlflag>"+
						"<transclass>1</transclass>"+
						"<ecodeimpflag>0</ecodeimpflag>"+
						"<sysmodifydate>" + datetime + "</sysmodifydate>"+     /*ʱ�����*/
						"<srcporderid>005</srcporderid>"+                     /*ԭ��������ID*/
						"<companystyle>2</companystyle>"+                    /*��λ���� 1���ͻ���2����Ӧ�̣�3����������*/
						"<transmode>1</transmode>"+
						"<erptransid>1</erptransid>"+
						"<goodsownerid>521</goodsownerid>"+                    /*����ID 521*/
						"<warehid>3</warehid>"+                                /*��������ID 3*/
						"<relevanceno>521201707260031</relevanceno>"+         /*����ID+������+ҵ������*/
						"<dtl class=\"array\">"+
			"<e class=\"object\">"+
			"<porderstatus>1</porderstatus>"+
			"<batchno>0</batchno>"+                   /*���ţ�*/
			"<lotno></lotno>"+
			"<goodsqty>12</goodsqty>"+                /*����*/
			"<tradegoodsqty>12</tradegoodsqty>"+
			"<tradegoodspack>��</tradegoodspack>"+       /*������λ*/
			"<memo>����003</memo>"+                       /*��ע*/
			"<arrivedate>" + datetime + "</arrivedate>"+
			"<checkdate>" + datetime + "</checkdate>"+
			"<srcdtlno>01</srcdtlno>"+                      /*����ԭʼ���ݺţ���������*/
			"<lotflag>1</lotflag>"+
			"<batchflag>0</batchflag>"+
			"<gtradepack>��</gtradepack>"+                  /*������λ*/
			"<originqty>12</originqty>"+  
			"<price>100</price>"+                           /*����*/
			"<retailprice>100</retailprice>"+               /*���ۼ�*/
			"<amt>1200</amt>"+                              /*���*/
			"<qty>12</qty>"+
			"<quadqty>12</quadqty>"+
			"<unquadqty>0</unquadqty>"+
			"<testqty>12</testqty>"+
			"<sadtlid></sadtlid>"+
			"<expdtlmemo>bb01</expdtlmemo>"+
			"<goodsstatusid>1</goodsstatusid>"+
			"<quantitystatus>1</quantitystatus>"+
			"<validdate></validdate>"+
			"<proddate></proddate>"+
			"<approvedocno>pp01</approvedocno>"+
			"<firstflag>1</firstflag>"+
			"<tpricetick>1</tpricetick>"+
			"<tpriceflag>0</tpriceflag>"+
			"<packsize>1</packsize>"+                     /*��װ��С*/
			"<packname>��</packname>"+                    /*��װ����*/  
			"<prtclass>1</prtclass>"+
			"<srcgoodsqty>12</srcgoodsqty>"+
			"<erpgoodsid>301803MS</erpgoodsid>"+          /*����*/
			"<lotid>1</lotid>"+
			"<srcno>20170726003</srcno>"+                 /*��ͷ�����Ź���*/
			"<realqty>12</realqty>"+
			"<warehid>3</warehid>"+                       /*��������ID 3*/
			"<killlotno>mm01</killlotno>"+
			"<relevanceno>521201707260031</relevanceno>"+        /*����ID+������+ҵ������*/
			"<operatioontype>1</operatioontype>"+                /*ҵ������ 1��������7�����ˣ�22������*/
			"<goodsownerid>521</goodsownerid>"+                  /*����ID 521*/
			"</e>"+
			"</dtl>"+
					"</e>"+
				"</doc>"+
			"</result>";
		
		
		
		/* д��Txt�ļ� */  
        File writename = new File("E:\\BWMS\\301\\" + ddate() + "crt301.xml"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�  
        try {
        	writename.delete();
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writename),"UTF-8"));  
	        out.write(xml301); // \r\n��Ϊ����  
	        out.flush(); // �ѻ���������ѹ���ļ�  
	        out.close(); // ���ǵùر��ļ�  
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} // �������ļ�  
              
  		return xml301;

	}
	
	public static String crtxml302_1()         /*String��ʽ����302�����۶�����*/
	{
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = sdf.format(d);
        
		String xml302="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
		"<result class=\"object\">"+
		"<datatype>302</datatype>"+                 /*��������*/
		"<totalProperty>1</totalProperty>"+         /*��������*/
		"<doc class=\"array\">"+
		"<e class=\"object\">"+
		"<eorderno></eorderno>"+
		"<credate>" + datetime + "</credate>"+      /*��������*/
		"<preexpdate></preexpdate>"+
		"<expdate></expdate>"+
		"<prearrivedate></prearrivedate>"+
		"<arrivedate></arrivedate>"+
		"<ptransmodeid></ptransmodeid>"+
		"<transmode></transmode>"+
		"<earlydate></earlydate>"+
		"<composeinfo></composeinfo>"+
		"<operationtype>11</operationtype>"+      /*ҵ������ 11�����ۣ�12�������˳������ˣ���21 �������ƿ����18*/
		"<medicineclass></medicineclass>"+   
		"<receiveaddr>�Ϻ���������ؼ��·201��303��</receiveaddr>"+           /*�ջ���ַ*/
		"<receivehead></receivehead>"+          
		"<receiveman>���ŷ�</receiveman>"+       /*�ջ���*/
		"<partexpflag></partexpflag>"+
		"<samelotflag></samelotflag>"+
		"<urgenflag></urgenflag>"+
		"<addinvoiceflag></addinvoiceflag>"+
		"<arrivememo></arrivememo>"+
		"<exportmemo></exportmemo>"+
		"<detaillines></detaillines>"+
		"<srcexpno>2017070001</srcexpno>"+          /*���۶�����*/
		"<transid></transid>"+
		"<porderid></porderid>"+
		"<billfrom></billfrom>"+
		"<usestatus></usestatus>"+
		"<outmode></outmode>"+               
		"<invoicetype>0</invoicetype>"+       /*��Ʊ���� 0����ֵ˰��Ʊ��1����ͨ��Ʊ��2:�Թ���Ʊ��Ĭ��Ϊ1*/
		"<transclass></transclass>"+
		"<receivephone>64669879 13816013927</receivephone>"+       /*�ջ��˵绰*/
		"<tpricetick></tpricetick>"+
		"<tpriceflag></tpriceflag>"+
		"<deptno></deptno>"+
		"<deptname></deptname>"+
		"<gcompanyid>30001</gcompanyid>"+                 /*�ͻ�ID��*/
		"<salerid></salerid>"+
		"<salername>����</salername>"+                        /*ҵ��Ա���ƣ�*/
		"<prtclass></prtclass>"+
		"<prtgroupset></prtgroupset>"+
		"<nowlflag></nowlflag>"+
		"<sysmodifydate>" + datetime + "</sysmodifydate>"+   /*ʱ�����*/
		"<goodsownerid>521</goodsownerid>"+                  /*����ID 521*/
		"<companystype>1</companystype>"+                      /*��λ���� 1���ͻ���2����Ӧ�̣�3���������ң�*/
		"<warehid>3</warehid>"+                               /*��������ID 3*/
		"<relevanceno>521201707000111</relevanceno>"+        /*����ID+���۶�����+ҵ������*/
		"<realrecaddr></realrecaddr>"+               /*???*/ 		   
		"<dtl class=\"array\">"+
		"<e class=\"object\">"+
		"<batchno></batchno>"+
		"<lotno>1</lotno>"+                                      /*���ţ�*/
		"<validdate>" + datetime +"</validdate>"+            /*��Ч��*/
		"<goodsstatusid>1</goodsstatusid>"+                   /*��Ʒ״̬ 1���ϸ�*/
		"<price>300</price>"+                                 /*����*/
		"<goodsqty>2</goodsqty>"+                             /*����*/
		"<tradegoodsqty></tradegoodsqty>"+
		"<tradegoodspack>��</tradegoodspack>"+                /*������λ*/
		"<addmedcheckflag></addmedcheckflag>"+
		"<srcexpdtlno>0001</srcexpdtlno>"+                /*���۶���ϸ����*/
		"<usestatus></usestatus>"+
		"<gtradepack></gtradepack>"+
		"<dtlmemo></dtlmemo>"+
		"<retailprice>300</retailprice>"+                /*���ۼ�*/
		"<amt>600</amt>"+                               /*���*/
		"<expdtlmemo></expdtlmemo>"+
		"<otcflag></otcflag>"+
		"<trademark></trademark>"+
		"<approvedocno></approvedocno>"+
		"<proddate></proddate>"+
		"<tpricetick></tpricetick>"+
		"<tpriceflag></tpriceflag>"+
		"<packsize>2</packsize>"+                        /*��װ��С*/
		"<invno></invno>"+
		"<packname>��</packname>"+                       /*��װ����*/
		"<sorttype></sorttype>"+
		"<lotlimit></lotlimit>"+
		"<periodlimit></periodlimit>"+
		"<periodlimitunit></periodlimitunit>"+
		"<erpgoodsid>301803MS</erpgoodsid>"+            /*�������*/
		"<srcexpno>2017070001</srcexpno>"+              /*��װ����*/
		"<warehid>3</warehid>"+                         /*��������ID 3*/
		"<killlotno></killlotno>"+           
		"<relevanceno>521201707000111</relevanceno>"+    /*����ID+���۶�����+ҵ������*/
		"<goodsownerid>521</goodsownerid>"+              /*����ID 521*/	
		"<realqty></realqty>"+ 
		"</e>"+
		"</dtl>"+
		"</e>"+
		"</doc>"+
		"</result>";

		
		
		
		/* д��Txt�ļ� */  
        File writename = new File("E:\\BWMS\\302\\" + ddate() + "crt302.xml"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�  
        try {
        	writename.delete();
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writename),"UTF-8"));  
	        out.write(xml302); // \r\n��Ϊ����  
	        out.flush(); // �ѻ���������ѹ���ļ�  
	        out.close(); // ���ǵùر��ļ�  
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} // �������ļ�  
              
  		return xml302;
	}
	
	public static String get320()              /*��ȡ����320����ⵥ��*/
	{
		String xml320="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<result class=\"object\">" +
		"<datatype>320</datatype>" +
		"<start>0</start>" +
		"<limit>1000</limit>" +
		"</result>";
		return xml320;
	}
 
	public static String get340()              /*��ȡ����340�����ⵥ��*/
	{
		String xml340="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<result class=\"object\">" +
				"<datatype>340</datatype>" +
				"<start>0</start>" +
				"<limit>1000</limit>" +
				"</result>";
				return xml340;
	}

	public static String get910()     /*��ȡ����910��������ݣ�*/
	{
		String xml910 = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"
				+ "<result class=\"object\">" 
				+ "<datatype>910</datatype>"
				+ "<start>0</start>" 
				+ "<limit>1000</limit>" 
				+ "</result>";
		return xml910;
	}
	
	public static String get400() 
	{
		String xml400="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<result class=\"object\">" +
				"<datatype>400</datatype>" +
				"<start>0</start>" +
				"<limit>1000</limit>" +
				"</result>";
				return xml400;
	}
	
    public static void savexml(String xml,String path)     /*���ַ�������ΪXML��ʽ������·��*/
	{
		 File writename = new File(path); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�  
	        try {
	        	writename.delete();
				writename.createNewFile();
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writename),"UTF-8"));  
		        out.write(xml); // \r\n��Ϊ����  
		        out.flush(); // �ѻ���������ѹ���ļ�  
		        out.close(); // ���ǵùر��ļ�  
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
			} // �������ļ�  
	}
	
	public static String getLogInfo()                /*doc��ʽ����log����־��*/
	{
		String path = "E:\\BWMS\\getLogInfo.xml";
		Document document = DocumentHelper.createDocument();
		Element root = DocumentHelper.createElement("result");
	    document.setRootElement(root);
	    
	    Element Element1 = root.addElement("datatype");
	    Element Element2 = root.addElement("start");
	    Element Element3 = root.addElement("limit");
	    Element Element4 = root.addElement("startdate");
	    Element Element5 = root.addElement("enddate");
	    Element Element6 = root.addElement("succorerr");
	    
	    Element1.setText("301");
	    Element2.setText("1");
	    Element3.setText("20");
	    Element4.setText("2017-07-20 00:00:00");
	    Element5.setText("2017-08-08 00:00:00");
	    Element6.setText("1");
	    
	    XMLWriter xmlWriter;
	  		try {
	  			xmlWriter = new XMLWriter(new FileOutputStream(path));
	  			xmlWriter.write(document);
	  		    xmlWriter.flush();
	  		} catch (IOException e) {
	  			// TODO �Զ����ɵ� catch ��
	  			e.printStackTrace();
	  		}
	  		return asxml(path);
	}
	
	public static String getseqid()           /*���seqid,�����ַ���*/
	{
		try {
			File file=new File(seqid);
			if(file.isFile() && file.exists())
			{ //�ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");//���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);
				String seqids = "";
				String lineTxt = null;
				if((lineTxt = bufferedReader.readLine()) == null)
				{
					read.close();
					return seqids;
				}
				else
				{
					seqids = lineTxt;
				}
				while((lineTxt = bufferedReader.readLine()) != null)
				{
					seqids = seqids + "," + lineTxt;
				}
				read.close();
				return seqids;
			}else
			{
				return"�Ҳ���ָ�����ļ�";
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
			return"��ȡ�ļ����ݳ���";			
		}
	}
	
	public static String su_confirm(String type)       /*WMS��������ȷ�ϣ��ɹ���������Ĳ�������*/
	{
		String xmlconfirm = "";
		if(getseqid()!="")
		{
			xmlconfirm ="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
					"<result>"+
					"<datatype>"+ type +"</datatype>"+
					"<rtnVal>200</rtnVal>"+
					"<rtnMsg>success</rtnMsg>"+
					"<seqids>"+ getseqid() + "</seqids>"+
					"</result>";			
		}
		savexml(xmlconfirm,"E:\\BWMS\\confirm\\" + type + "\\" + ddate() + " con" + type + ".xml");
		return xmlconfirm;		
	}
	
	public static String fa_confirm(String type)       /*WMS��������ȷ�ϣ�ʧ�ܣ�������Ĳ�������*/
	{	
		String xmlconfirm ="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
		"<result>"+
		"<datatype>"+ type +"</datatype>"+
		"<rtnVal>400</rtnVal>"+
		"<rtnMsg>fail</rtnMsg>"+
		"</result>";	
		savexml(xmlconfirm,"E:\\BWMS\\confirm\\" + type + "\\" + ddate() + " con" + type + ".xml");
		return xmlconfirm;
	}
	
	public static String tempread()     /*��ȡtempcode�ļ�*/
	{
		try {
			String encoding="UTF-8";
			File file=new File(tempcode);
			if(file.isFile() && file.exists())
			{ //�ж��ļ��Ƿ����
				InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//���ǵ������ʽ
				BufferedReader bufferedReader = new BufferedReader(read);
				String tempread = "";
				String lineTxt = null;
				while((lineTxt = bufferedReader.readLine()) != null)
				{
					tempread = tempread + "\n" + lineTxt;
				}
				read.close();
				return tempread;
			}else
			{
				return"�Ҳ���ָ�����ļ�";
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
			return"��ȡ�ļ����ݳ���";			
		}
	}
	
	public static void tempwrite(String code)    /*д��tempcode�ļ�*/
	{
		dt();
		FileWriter fw = null;
		if (tempread().indexOf(code) > -1) {
		}
		else
		{
			try {
				// ����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
				File f = new File(tempcode);
				fw = new FileWriter(f, true);
				PrintWriter pw = new PrintWriter(fw);
				pw.println(code);
				pw.flush();
				fw.flush();
				pw.close();
				fw.close();  
			} catch (IOException e) {
				e.printStackTrace();
			}
		}				  
	}
	
	public static void dt()      /*����tempcode�ļ�*/
	{
		File file = new File(tempcode);
		long time = file.lastModified();
		Date dateOld = new Date(time);
		Date dt=new Date();				
		if((dateOld.getTime()-dt.getTime())/(3600*24*1000)==0)
		{		
		}
		else
		{
			try {
	            FileWriter fileWriter =new FileWriter(file);
	            fileWriter.write("");
	            fileWriter.flush();
	            fileWriter.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		}
	}
	
	public static String autoInvent()    /*�Զ���ȡ���ݿ⣬��ȡ�������*/
	{
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = sdf.format(d);
	    Connection con = null;    
	    Statement stmt = null;    
	    ResultSet rs = null;  
	    int count = 0;
	    
	    String xml101="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<result class=\"object\">"+
				"<datatype>101</datatype>"+        
				"<doc class=\"array\">";
		try
		{
			Class.forName(driverName);
			con = DriverManager.getConnection(url);
			/*System.out.println("end."); */
			
			// Create and execute an SQL statement that returns some data.    
           /* String SQL = "select * from Inventory A left join (select ccode,ClassName from EF_Base_V_Information B) C on A.cInvCode=C.ccode left join (select D.iChangRate,D.cComunitCode from ComputationUnit D) E on A.cSAComUnitCode=E.cComunitCode where cInvCode='00463'";*/
            String SQL = "select * from Inventory A left join (select ccode,ClassName from EF_Base_V_Information B) C on A.cInvCode=C.ccode left join (select D.iChangRate,D.cComunitCode from ComputationUnit D) E on A.cSAComUnitCode=E.cComunitCode left join (select G.dEffDate,G.dInvalidDate,F.cinvcode xxx,F.dcreatesystime from EF_ZZGL_VenLicence F left join  EF_ZZGL_VenLicences G on F.ID=G.ID ) H on H.xxx=A.cInvCode where E.iChangRate is not null and (cinvccode='0101') order by dcreatesystime desc";           
            stmt = con.createStatement();    
            rs = stmt.executeQuery(SQL);
            
		}
		catch (Exception e) 
        {
            e.printStackTrace();
            writelog("��Ʒ���������ϴ���" + e + ddate());
            return "";
        }
		
		try {
			while (rs.next()) {
				int cclass = 2;
				if (rs.getString("ClassName").indexOf("һ") > -1) 
				{
					cclass = 0;
				} else if (rs.getString("ClassName").indexOf("��") > -1) 
				{
					cclass = 1;
				}					
				xml101 = xml101
						+ "<e class=\"object\">"
						+ "<goodsownerid>521</goodsownerid>"      /*����ID 521*/
						+ "<goodsownid>"+ rs.getString("cInvcode") +"</goodsownid>"    /*�������*/
						+ "<barcode></barcode>"
						+ "<goodsno></goodsno>"
						+ "<gopcode>135</gopcode>"                /*�����룿*/
						+ "<gopinyin>"+ rs.getString("cInvcode") +"</gopinyin>"      /*ƴ����*/
						+ "<poisondrug>"+ cclass +"</poisondrug>"                    /*�������*/
						+ "<goodsname>"+ rs.getString("cInvName") +"</goodsname>"     /*�������*/
						+ "<goodsengname>"+ rs.getString("cInvcode") +"</goodsengname>"  /*���Ӣ������*/
						+ "<goodstype>"+ rs.getString("cInvStd") +"</goodstype>"        /*���*/
						+ "<goodsformalname></goodsformalname>"
						/*+ "<factname>"+ (rs.getString("cEnterprise")==null?"1":rs.getString("cEnterprise")) +"</factname>" */ /*��������*/
						+ "<factname>ǿ�����Ϻ���ҽ���������޹�˾</factname>"
						+ "<tradepack>��</tradepack>"         /*���׵�λ*/
						+ "<outpack>��</outpack>"           /*������λ*/
						+ "<addmedcheckflag>1</addmedcheckflag>"
						+ "<pasteflag>0</pasteflag>"
						+ "<wholesaleprice></wholesaleprice>"
						+ "<resaleprice></resaleprice>"
						+ "<purchasetax></purchasetax>"
						+ "<saletax></saletax>"
						+ "<lotflag>1</lotflag>"
						+ "<batchflag>0</batchflag>"
						+ "<memo></memo>"             /*��ע*/
						+ "<drugform></drugform>"
						+ "<status>1</status>"
						+ "<otcflag></otcflag>"
						+ "<trademark></trademark>"
						+ "<storagecondition>1</storagecondition>"
						+ "<approvedocno></approvedocno>"
						+ "<packsize>"+ (int)Float.parseFloat(rs.getString("iChangRate")) +"</packsize>"  /*��װ��С�����������ʣ���*/
						+ "<dbcheck></dbcheck>"
						+ "<periodunit></periodunit>"
						+ "<validperiod></validperiod>"
						+ "<varietyname></varietyname>"
						+ "<supplyername></supplyername>"
						+ "<registdocno>"+ rs.getString("cInvDefine7") +"</registdocno>"   /*ע��֤��*/
						+ "<varietydescid></varietydescid>"
						+ "<firstindate></firstindate>"
						+ "<centrepicksize></centrepicksize>"
						+ "<credate>" + datetime + "</credate>"
						+ "<alonepackflag></alonepackflag>"
						+ "<dictno></dictno>"
						+ "<filegroundid></filegroundid>"
						+ "<gmpflag></gmpflag>"
						+ "<jkhzflag></jkhzflag>"
						+ "<signature></signature>"
						+ "<specailflag></specailflag>"
						+ "<boxnoflag></boxnoflag>"
						+ "<dbincheck></dbincheck>"
						+ "<dbpickflag></dbpickflag>"
						+ "<ecodeflag></ecodeflag>"
						+ "<emgoodsflag></emgoodsflag>"
						+ "<emgoodsreason></emgoodsreason>"
						+ "<prodlicenseno></prodlicenseno>"
						+ "<qtygene></qtygene>"
						+ "<repflag></repflag>"
						+ "<creDate>" + datetime + "</creDate>"
						+ "<prodarea>"+ (rs.getString("cProduceAddress")==null?"1":rs.getString("cProduceAddress")) +"</prodarea>"  /*����*/
						+ "<goodsstatus>1</goodsstatus>"       /*��Ʒ״̬*/
						+ "<tradepacknameid></tradepacknameid>"
						+ "<goodsid></goodsid>"
						+ "<ownergoodsid></ownergoodsid>"
						+ "<outpacknameid></outpacknameid>"
						+ "<tradegoodspackid></tradegoodspackid>"
						+ "<outgoodspackid></outgoodspackid>"
						+ "<storagetype></storagetype>"
						+ "<sysmodifydate>" + datetime + "</sysmodifydate>"
						+ "<factid></factid>"
						+ "<classname></classname>"
						+ "<tradegoodslength></tradegoodslength>"
						+ "<tradegoodswidth></tradegoodswidth>"
						+ "<tradegoodsheight></tradegoodsheight>"
						+ "<tradegoodscubage></tradegoodscubage>"
						+ "<tradegoodsweight></tradegoodsweight>"
						+ "<tradeweightunit></tradeweightunit>"
						+ "<outgoodslength></outgoodslength>"
						+ "<outgoodswidth></outgoodswidth>"
						+ "<outgoodsheight></outgoodsheight>"
						+ "<outgoodscubage></outgoodscubage>"
						+ "<outgoodsweight></outgoodsweight>"
						+ "<outweightunit></outweightunit>"
						+ "<templow></templow>"     /*�¶�����*/
						+ "<temphigh></temphigh>"     /*�¶�����*/
						+ "<packscodeflag></packscodeflag>"
						+ "<lotnocannullflag></lotnocannullflag>"
						+ "<warehid>3</warehid>"             /*��������ID 3*/
						+ "<registstartdate>"+ (rs.getString("dEffDate")==null?"":rs.getString("dEffDate")) +"</registstartdate>"     /*ע��֤��ʼʱ��*/
						+ "<registenddate>"+ (rs.getString("dInvalidDate")==null?"":rs.getString("dInvalidDate")) +"</registenddate>"         /*ע��֤����ʱ��*/
						+ "<goodsmodel>"+ rs.getString("cSAComUnitCode") +"</goodsmodel>" /*�����ͺ��ֶΣ������ʱ��룩*/
						+ "<scopeno></scopeno>" + "</e>";
				
				count++;
			}
				xml101 = xml101 + "</doc>" + "<totalProperty>" + count
						+ "</totalProperty>" +  /*�������� */
						"</result>";
			
				
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			xml101 = "";	
			writelog("��Ʒ���������ϴ���" + e + ddate());
			return "";
		}
		try {
			con.close();
			con=null;
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			writelog("��Ʒ���������ϴ���" + e + ddate());
	    	return "";
		}
		File writename = new File("E:\\BWMS\\101\\" + ddate() + "auto101.xml"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�
		try {
			writename.delete();
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(writename), "UTF-8"));
			out.write(xml101); // \r\n��Ϊ����
			out.flush(); // �ѻ���������ѹ���ļ�
			out.close(); // ���ǵùر��ļ�
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			writelog("��Ʒ���������ϴ���" + e + ddate());
			return "";
		} // �������ļ�

		return xml101;
	}
	
	public static String autocustom()    /*�Զ���ȡ���ݿ⣬��ȡ�ͻ�����*/
	{
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = sdf.format(d);
	    Connection con = null;    
	    Statement stmt = null;    
	    ResultSet rs = null;    
	    int count = 0;
	    
	    String xml102="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<result class=\"object\">"+
				"<datatype>102</datatype>"+        
				"<doc class=\"array\">";
		try
		{
			Class.forName(driverName);
			con = DriverManager.getConnection(url);
			/*System.out.println("end.");*/ 
			
			// Create and execute an SQL statement that returns some data.    
            String SQL = "select * from customer where cCusCode='00157' or cCusCode='00181'";
            stmt = con.createStatement();    
            rs = stmt.executeQuery(SQL);
            
		}
		catch (Exception e) 
        {
            e.printStackTrace();
            writelog("�ͻ����������ϴ���" + e + ddate());
            return "";
        }
		
		try {
			while (rs.next())
			{
				xml102 = xml102 + "<e class=\"object\">"
						+ "<goodsownerid>521</goodsownerid>"     /*����ID 521*/
						+ "<gcompanyid>" + rs.getString("cCusCode") + "</gcompanyid>"   /*�ͻ�����*/
						+ "<companystype>1</companystype>"     /*��λ���� 1���ͻ���2����Ӧ�̣�3����������*/
						+ "<companyname>" + rs.getString("cCusName") + "</companyname>"   /*�ͻ�����*/
						+ "<gopinyin></gopinyin>"
						+ "<opcode>135</opcode>"        /*�����룿*/
						+ "<companyshortname>" + rs.getString("cCusAbbName") + "</companyshortname>"  /*�ͻ����*/
						+ "<bank>" + rs.getString("cCusBank") + "</bank>"    /*��������*/
						+ "<bankno>" + rs.getString("cCusAccount") + "</bankno>"     /*�����˺�*/
						+ "<chiefofficer></chiefofficer>"
						+ "<connector>" + rs.getString("cCusPerson") + "</connector>"    /*��ϵ��*/
						+ "<connphone>" + rs.getString("cCusPhone") + "</connphone>"    /*��ϵ�绰*/
						+ "<deliveraddr>" + rs.getString("cCusAddress") + "</deliveraddr>"   /*�ͻ���ַ*/
						+ "<invoiceaddr>" + rs.getString("cCusDefine7") + "</invoiceaddr>"   /*��Ʊ��ַ*/
						+ "<invoicehead>" + rs.getString("cCusName") + "</invoicehead>"     /*��Ʊ���ƣ�*/
						+ "<invoiceman>" + rs.getString("cCusDefine3") + "</invoiceman>"  /*��Ʊ��Ա�� ��Ʊ�ˣ�*/
						+ "<invoicephone>" + rs.getString("cCusDefine5") + "</invoicephone>"  /*��Ʊ�绰�� ��Ʊ�˵绰��*/
						+ "<postcode></postcode>"
						+ "<specialrequire></specialrequire>"
						+ "<taxregistno>" + rs.getString("cCusRegCode") + "</taxregistno>"  /*˰��*/
						+ "<telephone></telephone>"
						+ "<memo></memo>"              /*��ע*/
						+ "<addmedcheckflag>1</addmedcheckflag>"
						+ "<usestatus>1</usestatus>"
						+ "<companyno>" + rs.getString("cCusCode") + "</companyno>"     /*����ԭ��λ���룿*/
						+ "<orgno>1</orgno>"               /*��֯��������?*/
						+ "<impflag>1</impflag>"         /*�����־��*/
						+ "<creDate>" + datetime + "</creDate>"  
						+ "<city></city>"
						+ "<corpid></corpid>"
						+ "<goodsownerflag>0</goodsownerflag>"    /*������־ 0���ǻ�����1������*/
						+ "<legalpersion></legalpersion>"
						+ "<unifyno></unifyno>"
						+ "<defrtanid>1</defrtanid>"     /*Ĭ�ϵ�ַID��*/
						+ "<invclass>1</invclass>"        /*��Ʊ���ࣿ*/
						+ "<invprintplan></invprintplan>"
						+ "<invtype>1</invtype>"        /*��Ʊ���ͣ�*/
						+ "<modedocid>1</modedocid>"    /*��ά���ӡ����ģ��ID��*/
						+ "<orderflag>1</orderflag>"    /*���ϼ����־��*/
						+ "<periodlimit>1</periodlimit>"    /*Ч�����ƣ�*/
						+ "<periodlimtunit></periodlimtunit>"
						+ "<printflag>0</printflag>"       /*�Ƿ��ӡ��Ʊ�� 0������ӡ��1����ӡ*/
						+ "<companycredate>" + datetime + "</companycredate>"
						+ "<printplan></printplan>"
						+ "<lotlimit>3</lotlimit>"     /*1��ֻ��һ�����ţ�2������������ţ�3���������Ÿ�����Ĭ��Ϊ3*/
						+ "<sysmodifydate>" + datetime + "</sysmodifydate>"
						+ "<warehid>3</warehid>"      /*��������ID 3*/
						+ "<dtl class=\"array\">";
				
				/*String SQL_ = "select * from PO_POdetails where POID='" + rs.getString("POID") + "'";
				stmt = con.createStatement();    
	            rs_ = stmt.executeQuery(SQL_);
				
	            while (rs_.next())
	            {*/
				xml102 = xml102 + "<e class=\"object\">"
						+ "<goodsownerid>521</goodsownerid>"    /*����ID 521*/
						+ "<gocompanyid>" + rs.getString("cCusCode") + "</gocompanyid>"      /*�ͻ�����*/
						+ "<transid>1</transid>"     /*Ĭ�ϵ�ַID��*/
						+ "<inceptaddr>" + rs.getString("cCusAddress") + "</inceptaddr>"   /*�����ַ*/
						+ "<connector>" + rs.getString("cCusPerson") + "</connector>"     /*��ϵ��*/
						+ "<connphone>" + rs.getString("cCusPhone") + "</connphone>"      /*��ϵ�绰*/
						+ "<telefax></telefax>"
						+ "<postcode></postcode>"
						+ "<memo></memo>"           /*��ע*/
						+ "<usestatus>1</usestatus>"   /*ʹ��״̬ 1:������0�����ϣ�Ĭ��Ϊ1*/
						+ "<tranposname></tranposname>"
						+ "<impflag>1</impflag>"     /*�����ע�� Ĭ��Ϊ1*/
						+ "<creDate>" + datetime + "</creDate>"
						+ "<city></city>"
						+ "<equtransid>1</equtransid>"    /*��ͬ�ص�id?*/
						+ "<sysmodifydate>" + datetime + "</sysmodifydate>"
						+ "<companystype>1</companystype>"      /*��λ���� 1���ͻ���2����Ӧ�̣�3����������*/
						+ "<warehid>3</warehid>"        /*��������ID 3*/
						+ "</e>";
	            /*}*/		
	            xml102=xml102 + "</dtl>";
			
			 count++;		
			 xml102=xml102 + "</e>";
					
			}
			xml102=xml102+"</doc>"+
					"<totalProperty>" + count + "</totalProperty>"+  /*��������*/
					"</result>";
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			xml102 = "";
			writelog("�ͻ����������ϴ���" + e + ddate());
			return "";			
		}
		try {
			con.close();
			con=null;
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			writelog("�ͻ����������ϴ���" + e + ddate());
			return "";
		}
		File writename = new File("E:\\BWMS\\102\\" + ddate() + "auto102.xml"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�
		try {
			writename.delete();
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(writename), "UTF-8"));
			out.write(xml102); // \r\n��Ϊ����
			out.flush(); // �ѻ���������ѹ���ļ�
			out.close(); // ���ǵùر��ļ�
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			writelog("�ͻ����������ϴ���" + e + ddate());
			return "";
		} // �������ļ�

		return xml102;
	}
	
	public static String autopo()   /*�Զ���ȡ���ݿ⣬��ȡ�ɹ������б�*/
	{
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = sdf.format(d);
	    Connection con = null;    
	    Statement stmt = null;    
	    ResultSet rs = null;    
	    ResultSet rs_ = null;  
	    int count = 0;
	    String xml301 = "";
	    String ccode ="";    //��¼����
	    
		try
		{
			Class.forName(driverName);
			con = DriverManager.getConnection(url);
			/*System.out.println("end."); */
			
			// Create and execute an SQL statement that returns some data.    
            /*String SQL = "SELECT * FROM PO_POmain where cdefine4='2017-05-05 00:00:00.000'";*/
			String SQL = "SELECT top 5 * FROM PO_POmain";
            stmt = con.createStatement();    
            rs = stmt.executeQuery(SQL);      
		}
		catch (Exception e) 
        {
            e.printStackTrace();
            auto.area.append(e + ddate()+"\r\n");
			writelog(e + ddate());
        }
		
		try {
			while (rs.next())
			{
				ccode = rs.getString("cPOID");
				xml301="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<result class=\"object\">"+
						"<datatype>301</datatype>"+        
						"<doc class=\"array\">";
				
				if (tempread().indexOf(rs.getString("cPOID")) > -1)
				{
					continue;
				}
				else
				{
					xml301=xml301+"<e class=\"object\">"+ 
				            "<porderno>" + rs.getString("POID") + "</porderno>"+    /*����������*/
							"<credate>" + datetime + "</credate>"+           /*�Ƶ�����*/
							"<arrivedate></arrivedate>"+
							"<medicineclass>1</medicineclass>"+          
							"<importflag>1</importflag>"+
							"<operationtype>1</operationtype>"+             /*ҵ������ 1��������7�����ˣ�22������*/
							"<dtllines></dtllines>"+  
							"<memo>" + (rs.getString("cMemo")==null?"":rs.getString("cMemo"))+ "</memo>"+                /*��ע*/
							"<srcno>" + rs.getString("cPOID") + "</srcno>"+                  /*������*/
							"<exporderid></exporderid>"+              
							"<billfrom>3</billfrom>"+
							"<usestatus>2</usestatus>"+
							"<mulrecflag></mulrecflag>"+
							"<reccount></reccount>"+
							"<inmode>0</inmode>"+
							"<invalidate></invalidate>"+
							"<sabackdate></sabackdate>"+                      /*�˻�����*/
							"<sbreasonid></sbreasonid>"+                       /*�˻�ԭ��ID*/
							"<deptno></deptno>"+
							"<deptname></deptname>"+
							"<gcompanyid>" + rs.getString("cVenCode") + "</gcompanyid>"+      /*��Ӧ��ID��*/
							"<decisionflag></decisionflag>"+    
							"<bmsdocid></bmsdocid>"+
							"<nowlflag>0</nowlflag>"+
							"<transclass>1</transclass>"+
							"<ecodeimpflag></ecodeimpflag>"+
							"<sysmodifydate>" + datetime + "</sysmodifydate>"+     /*ʱ�����*/
							"<srcporderid>" + rs.getString("cPTCode") + "</srcporderid>"+    /*ԭ��������ID �������������ͣ� */
							"<companystyle>2</companystyle>"+                    /*��λ���� 1���ͻ���2����Ӧ�̣�3����������*/
							"<transmode></transmode>"+
							"<erptransid></erptransid>"+
							"<goodsownerid>521</goodsownerid>"+                    /*����ID 521*/
							"<warehid>3</warehid>"+                                /*��������ID 3*/
							"<relevanceno>521" + rs.getString("cPOID") + "1</relevanceno>"+ /*����ID+������+ҵ������*/
							"<dtl class=\"array\">";         
					
					String SQL_ = "select * from PO_POdetails where POID='" + rs.getString("POID") + "'";
					stmt = con.createStatement();    
		            rs_ = stmt.executeQuery(SQL_);
					
		            while (rs_.next())
		            {
		            	xml301=xml301+"<e class=\"object\">"+
		            			"<porderstatus>1</porderstatus>"+
		            			"<batchno></batchno>"+                   
		            			"<lotno></lotno>"+                        /*���ţ�*/
		            			"<goodsqty>" + String.format("%.4f", Float.parseFloat(rs_.getString("iQuantity"))) + "</goodsqty>"+        /*����*/
		            			"<tradegoodsqty></tradegoodsqty>"+
		            			"<tradegoodspack>��</tradegoodspack>"+       /*������λ*/
		            			"<memo>" + (rs_.getString("cbMemo")==null?"":rs_.getString("cbMemo")) + "</memo>"+  /*��ע*/
		            			"<arrivedate></arrivedate>"+
		            			"<checkdate></checkdate>"+
		            			"<srcdtlno>" + rs_.getString("ID") + "</srcdtlno>"+         /*����ԭʼ���ݺţ���������*/
		            			"<lotflag>1</lotflag>"+
		            			"<batchflag>0</batchflag>"+
		            			"<gtradepack>��</gtradepack>"+                  /*������λ*/
		            			"<originqty></originqty>"+  
		            			"<price></price>"+                           /*����*/
		            			"<retailprice></retailprice>"+               /*���ۼ�*/
		            			"<amt></amt>"+                              /*���*/
		            			"<qty></qty>"+
		            			"<quadqty></quadqty>"+
		            			"<unquadqty></unquadqty>"+
		            			"<testqty></testqty>"+
		            			"<sadtlid></sadtlid>"+
		            			"<expdtlmemo></expdtlmemo>"+
		            			"<goodsstatusid></goodsstatusid>"+
		            			"<quantitystatus></quantitystatus>"+
		            			"<validdate></validdate>"+
		            			"<proddate></proddate>"+
		            			"<approvedocno></approvedocno>"+
		            			"<firstflag></firstflag>"+
		            			"<tpricetick></tpricetick>"+
		            			"<tpriceflag></tpriceflag>"+
		            			"<packsize>" + (int)(Float.parseFloat(rs_.getString("iQuantity"))/Float.parseFloat(rs_.getString("iNum"))) + "</packsize>"+   /*��װ��С*/
		            			"<packname>��</packname>"+                    /*��װ����*/  
		            			"<prtclass></prtclass>"+
		            			"<srcgoodsqty></srcgoodsqty>"+
		            			"<erpgoodsid>" + rs_.getString("cInvCode") + "</erpgoodsid>"+       /*����*/
		            			"<lotid></lotid>"+
		            			"<srcno>" + rs.getString("cPOID") + "</srcno>"+                 /*��ͷ�����Ź���*/
		            			"<realqty></realqty>"+
		            			"<warehid>3</warehid>"+                       /*��������ID 3*/
		            			"<killlotno></killlotno>"+
		            			"<relevanceno>521" + rs.getString("cPOID") + "1</relevanceno>"+        /*����ID+������+ҵ������*/
		            			"<operatioontype>1</operatioontype>"+                /*ҵ������ 1��������7�����ˣ�22������*/
		            			"<goodsownerid>521</goodsownerid>"+                  /*����ID 521*/
		            			"</e>";
		            }			
		            xml301=xml301 + "</dtl>";
				}
				/*count++;	*/
				count = 1;
				xml301=xml301 + "</e>";
				xml301=xml301 + "</doc>" +
							"<totalProperty>" + count + "</totalProperty>"+  /*��������*/
							"</result>";
				/*core("impWmsData",xml301,"301");*/
				
				tempwrite(rs.getString("cPOID"));   //�����ݺ�д��temp
				File writename = new File("E:\\BWMS\\301\\" + rs.getString("cPOID") + " " + ddate()
						+ "auto301.xml"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�
				try {
					writename.delete();
					writename.createNewFile();
					BufferedWriter out = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(
									writename), "UTF-8"));
					out.write(xml301); // \r\n��Ϊ����
					out.flush(); // �ѻ���������ѹ���ļ�
					out.close(); // ���ǵùر��ļ�
					
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();		
					auto.area.append(e + ddate()+"\r\n");
					writelog(e + ddate());
				}
				auto.area.append(ccode + "�ϴ��ɹ� " + ddate()+"\r\n");
				writelog(ccode + "�ϴ��ɹ� " + ddate());
			}			
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			xml301 = "";	
			auto.area.append(ccode + "�ϴ�ʧ�� " + ddate()+"\r\n");
			writelog(ccode + "�ϴ�ʧ�� " + ddate());
		}
		try {
			con.close();
			con=null;
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
			auto.area.append(e1 + ddate()+"\r\n");
			writelog(e1 + ddate());
		}	 
		return xml301;
	}
	
	public static String autoso()   /*�Զ���ȡ���ݿ⣬��ȡ����*/
	{
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = sdf.format(d);
	    Connection con = null;    
	    Statement stmt = null;    
	    ResultSet rs = null;    
	    ResultSet rs_ = null;  
	    int count = 0;
	    String xml302 = "";
	    String xml301 = "";
	    String ccode = "";   //��¼����
	    	    
		try
		{
			Class.forName(driverName);
			con = DriverManager.getConnection(url);
			/*System.out.println("end."); */
			
			// Create and execute an SQL statement that returns some data.    
            String SQL = "select top 5 * from dispatchlist where bReturnFlag=1";
            stmt = con.createStatement();    
            rs = stmt.executeQuery(SQL);
            
		}
		catch (Exception e) 
        {
            e.printStackTrace();
            auto.area.append(e + ddate()+"\r\n");
			writelog(e + ddate());
        }
		
		try {
			while (rs.next())
			{
				if(rs.getString("bReturnFlag").equals("0"))
				{
					ccode = rs.getString("cDLCode");
					xml302="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
							"<result class=\"object\">"+
							"<datatype>302</datatype>"+        
							"<doc class=\"array\">";
					
					if (tempread().indexOf(rs.getString("cDLCode")) > -1)
					{
						continue;
					}				
						xml302=xml302+"<e class=\"object\">"+
								"<eorderno>" + rs.getString("cSOCode") + "</eorderno>"+   /*���۶���*/
								"<credate>" + datetime + "</credate>"+      /*��������*/
								"<preexpdate></preexpdate>"+
								"<expdate></expdate>"+
								"<prearrivedate></prearrivedate>"+
								"<arrivedate></arrivedate>"+
								"<ptransmodeid></ptransmodeid>"+
								"<transmode></transmode>"+
								"<earlydate></earlydate>"+
								"<composeinfo></composeinfo>"+
								"<operationtype>11</operationtype>"+      /*ҵ������ 11�����ۣ�12�������˳������ˣ���21 �������ƿ����18*/
								"<medicineclass></medicineclass>"+   
								"<receiveaddr>" + rs.getString("cDefine11") + "</receiveaddr>"+     /*�ջ���ַ*/
								"<receivehead></receivehead>"+          
								"<receiveman>" + rs.getString("cDefine12") + "</receiveman>"+       /*�ջ���*/
								"<partexpflag></partexpflag>"+
								"<samelotflag></samelotflag>"+
								"<urgenflag></urgenflag>"+
								"<addinvoiceflag></addinvoiceflag>"+
								"<arrivememo></arrivememo>"+
								"<exportmemo>" + (rs.getString("cMemo")==null?"":rs.getString("cMemo"))+ "</exportmemo>"+   //������ע
								"<detaillines></detaillines>"+
								"<srcexpno>" + rs.getString("cDLCode") + "</srcexpno>"+          /*��������*/
								"<transid></transid>"+
								"<porderid></porderid>"+
								"<billfrom></billfrom>"+
								"<usestatus></usestatus>"+
								"<outmode></outmode>"+               
								"<invoicetype>0</invoicetype>"+       /*��Ʊ���� 0����ֵ˰��Ʊ��1����ͨ��Ʊ��2:�Թ���Ʊ��Ĭ��Ϊ1*/
								"<transclass></transclass>"+
								"<receivephone>" + rs.getString("cDefine10") + "</receivephone>"+       /*�ջ��˵绰*/
								"<tpricetick></tpricetick>"+
								"<tpriceflag></tpriceflag>"+
								"<deptno></deptno>"+
								"<deptname></deptname>"+
								"<gcompanyid>" + rs.getString("cCusCode") + "</gcompanyid>"+      /*�ͻ�ID��*/
								"<salerid></salerid>"+
								"<salername>" + rs.getString("cMaker") + "</salername>"+       /*ҵ��Ա���ƣ�*/
								"<prtclass></prtclass>"+
								"<prtgroupset></prtgroupset>"+
								"<nowlflag></nowlflag>"+
								"<sysmodifydate>" + datetime + "</sysmodifydate>"+   /*ʱ�����*/
								"<goodsownerid>521</goodsownerid>"+                  /*����ID 521*/
								"<companystype>1</companystype>"+                      /*��λ���� 1���ͻ���2����Ӧ�̣�3���������ң�*/
								"<warehid>3</warehid>"+                               /*��������ID 3*/
								"<relevanceno>521" + rs.getString("cDLCode") + "11</relevanceno>"+        /*����ID+���۶�����+ҵ������*/
								"<realrecaddr></realrecaddr>"+               /*???*/ 		   
								"<dtl class=\"array\">";         
						
						String SQL_ = "select * from dispatchlists where DLID='" + rs.getString("DLID") + "'";
						stmt = con.createStatement();    
			            rs_ = stmt.executeQuery(SQL_);
						
			            while (rs_.next())
			            {
			            	xml302=xml302+"<e class=\"object\">"+
			            			"<batchno></batchno>"+
			            			"<lotno>" + rs_.getString("cBatch") + "</lotno>"+     /*���ţ�*/                                 /*���ţ�*/
			            			"<validdate>" + rs_.getString("dvDate") + "</validdate>"+      /*��Ч��*/
			            			"<goodsstatusid>1</goodsstatusid>"+                   /*��Ʒ״̬ 1���ϸ�*/
			            			"<price>" + rs_.getString("iTaxUnitPrice") + "</price>"+       /*����*/
			            			"<goodsqty>" + rs_.getString("iQuantity") + "</goodsqty>"+     /*����*/
			            			"<tradegoodsqty></tradegoodsqty>"+
			            			"<tradegoodspack>��</tradegoodspack>"+    /*������λ*/
			            			"<addmedcheckflag></addmedcheckflag>"+
			            			"<srcexpdtlno>" + rs_.getString("iDLsID") + "</srcexpdtlno>"+   /*������ϸ����*/
			            			"<usestatus></usestatus>"+
			            			"<gtradepack></gtradepack>"+
			            			"<dtlmemo></dtlmemo>"+
			            			"<retailprice>" + rs_.getString("iTaxUnitPrice") + "</retailprice>"+   /*���ۼ�*/
			            			"<amt></amt>"+         /*���*/
			            			"<expdtlmemo></expdtlmemo>"+
			            			"<otcflag></otcflag>"+
			            			"<trademark></trademark>"+
			            			"<approvedocno></approvedocno>"+
			            			"<proddate>" + (rs_.getString("dMDate")==null?"":rs_.getString("dMDate")) + "</proddate>"+    /*��������*/
			            			"<tpricetick></tpricetick>"+
			            			"<tpriceflag></tpriceflag>"+
			            			"<packsize>" + rs_.getString("iInvExchRate") + "</packsize>"+    /*��װ��С?*/
			            			"<invno></invno>"+
			            			"<packname>��</packname>"+                       /*��װ����*/
			            			"<sorttype></sorttype>"+
			            			"<lotlimit></lotlimit>"+
			            			"<periodlimit></periodlimit>"+
			            			"<periodlimitunit></periodlimitunit>"+
			            			"<erpgoodsid>" + rs_.getString("cInvCode") + "</erpgoodsid>"+     /*�������*/
			            			"<srcexpno>" + rs.getString("cDLCode") + "</srcexpno>"+     /*��������*/
			            			"<warehid>3</warehid>"+                         /*��������ID 3*/
			            			"<killlotno></killlotno>"+              /*�������*/
			            			"<relevanceno>521" + rs.getString("cDLCode") + "11</relevanceno>"+   /*����ID+���۶�����+ҵ������*/
			            			"<goodsownerid>521</goodsownerid>"+              /*����ID 521*/	
			            			"<realqty></realqty>"+ 
			            			"</e>";
			            }			
			            xml302=xml302 + "</dtl>";
					
					/*count++;	*/	
					count = 1;
					xml302=xml302 + "</e>";
					xml302=xml302 + "</doc>" +
								"<totalProperty>" + count + "</totalProperty>"+  /*��������*/
								"</result>";
					/*core("impWmsData",xml302,"302");*/
					 
					tempwrite(rs.getString("cDLCode"));   //�����ݺ�д��temp
					File writename = new File("E:\\BWMS\\302\\" + rs.getString("cDLCode") + " " + ddate()
							+ "auto302.xml"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�
					try {
						writename.delete();
						writename.createNewFile();
						BufferedWriter out = new BufferedWriter(
								new OutputStreamWriter(new FileOutputStream(
										writename), "UTF-8"));
						out.write(xml302); // \r\n��Ϊ����
						out.flush(); // �ѻ���������ѹ���ļ�
						out.close(); // ���ǵùر��ļ�
					} catch (IOException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
						auto.area.append(e + ddate()+"\r\n");
						writelog(e + ddate());
					}
					auto.area.append(ccode + "�ϴ��ɹ� " + ddate()+"\r\n");
					writelog(ccode + "�ϴ��ɹ� " + ddate());
				}
				else if(rs.getString("bReturnFlag").equals("1"))    //�����˻�
				{
					ccode = rs.getString("cDLCode");
					xml301="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
							"<result class=\"object\">"+
							"<datatype>301</datatype>"+        
							"<doc class=\"array\">";
					
					if (tempread().indexOf(rs.getString("cDLCode")) > -1)   
					{
						continue;
					}
					else
					{
						xml301=xml301+"<e class=\"object\">"+ 
					            "<porderno>" + (rs.getString("cSOCode")==null?"":rs.getString("cSOCode"))+ "</porderno>"+    /*������*/
								"<credate>" + datetime + "</credate>"+           /*�Ƶ�����*/
								"<arrivedate></arrivedate>"+
								"<medicineclass>1</medicineclass>"+          
								"<importflag>1</importflag>"+
								"<operationtype>7</operationtype>"+             /*ҵ������ 1��������7�����ˣ�22������*/
								"<dtllines></dtllines>"+  
								"<memo>" + (rs.getString("cMemo")==null?"":rs.getString("cMemo"))+ "</memo>"+  /*��ע*/
								"<srcno>" + rs.getString("cDLCode") + "</srcno>"+                  /*�˻�����*/
								"<exporderid></exporderid>"+              
								"<billfrom>3</billfrom>"+
								"<usestatus>2</usestatus>"+
								"<mulrecflag></mulrecflag>"+
								"<reccount></reccount>"+
								"<inmode>0</inmode>"+
								"<invalidate></invalidate>"+
								"<sabackdate>" + rs.getString("dDate") + "</sabackdate>"+     /*�˻�����*/
								"<sbreasonid></sbreasonid>"+                       /*�˻�ԭ��ID*/
								"<deptno></deptno>"+
								"<deptname></deptname>"+
								"<gcompanyid>" + rs.getString("cCusCode") + "</gcompanyid>"+      /*�ͻ�ID��*/
								"<decisionflag></decisionflag>"+    
								"<bmsdocid></bmsdocid>"+
								"<nowlflag>0</nowlflag>"+
								"<transclass>1</transclass>"+
								"<ecodeimpflag></ecodeimpflag>"+
								"<sysmodifydate>" + datetime + "</sysmodifydate>"+     /*ʱ�����*/
								"<srcporderid>" + rs.getString("cSTCode") + "</srcporderid>"+    /*ԭ��������ID �������������ͣ� */
								"<companystyle>1</companystyle>"+                    /*��λ���� 1���ͻ���2����Ӧ�̣�3����������*/
								"<transmode></transmode>"+
								"<erptransid></erptransid>"+
								"<goodsownerid>521</goodsownerid>"+                    /*����ID 521*/
								"<warehid>3</warehid>"+                                /*��������ID 3*/
								"<relevanceno>521" + rs.getString("cDLCode") + "7</relevanceno>"+ /*����ID+������+ҵ������*/
								"<dtl class=\"array\">";         
						
						String SQL_ = "select * from dispatchlists where DLID='" + rs.getString("DLID") + "'";
						stmt = con.createStatement();    
			            rs_ = stmt.executeQuery(SQL_);
						
			            while (rs_.next())
			            {
			            	xml301=xml301+"<e class=\"object\">"+
			            			"<porderstatus>1</porderstatus>"+
			            			"<batchno></batchno>"+                   
			            			"<lotno>" + rs_.getString("cBatch") + "</lotno>"+         /*���ţ�*/
			            			"<goodsqty>" + (rs_.getString("iQuantity").substring(1,(rs_.getString("iQuantity")).length())) + "</goodsqty>"+  /*����*/
			            			"<tradegoodsqty></tradegoodsqty>"+
			            			"<tradegoodspack>��</tradegoodspack>"+       /*������λ*/
			            			"<memo>" + (rs_.getString("cMemo")==null?"":rs_.getString("cMemo")) + "</memo>"+  /*��ע*/
			            			"<arrivedate></arrivedate>"+
			            			"<checkdate></checkdate>"+
			            			"<srcdtlno>" + rs_.getString("AutoID") + "</srcdtlno>"+         /*����ԭʼ���ݺţ���������*/
			            			"<lotflag>1</lotflag>"+
			            			"<batchflag>0</batchflag>"+
			            			"<gtradepack>��</gtradepack>"+                  /*������λ*/
			            			"<originqty></originqty>"+  
			            			"<price>" + rs_.getString("iTaxUnitPrice") + "</price>"+      /*����*/
			            			"<retailprice>" + rs_.getString("iTaxUnitPrice") + "</retailprice>"+    /*���ۼ�*/
			            			"<amt></amt>"+               /*���*/
			            			"<qty></qty>"+
			            			"<quadqty></quadqty>"+
			            			"<unquadqty></unquadqty>"+
			            			"<testqty></testqty>"+
			            			"<sadtlid>" + rs_.getString("iDLsID") + "</sadtlid>"+  //����ԭҵ�񵥺� ��������ϸ���ţ���
			            			"<expdtlmemo></expdtlmemo>"+
			            			"<goodsstatusid></goodsstatusid>"+
			            			"<quantitystatus></quantitystatus>"+
			            			"<validdate>" + rs_.getString("dvDate") + "</validdate>"+   //��Ч��
			            			"<proddate>" + (rs_.getString("dMDate")==null?"":rs_.getString("dMDate")) + "</proddate>"+   //��������
			            			"<approvedocno></approvedocno>"+
			            			"<firstflag></firstflag>"+
			            			"<tpricetick></tpricetick>"+
			            			"<tpriceflag></tpriceflag>"+
			            			"<packsize>" + (int)(Float.parseFloat(rs_.getString("iQuantity"))/Float.parseFloat(rs_.getString("iNum"))) + "</packsize>"+   /*��װ��С*/
			            			"<packname>��</packname>"+                    /*��װ����*/  
			            			"<prtclass></prtclass>"+
			            			"<srcgoodsqty></srcgoodsqty>"+
			            			"<erpgoodsid>" + rs_.getString("cInvCode") + "</erpgoodsid>"+       /*����*/
			            			"<lotid></lotid>"+
			            			"<srcno>" + rs.getString("cDLCode") + "</srcno>"+          /*��ͷ�����Ź���*/
			            			"<realqty></realqty>"+
			            			"<warehid>3</warehid>"+                       /*��������ID 3*/
			            			"<killlotno></killlotno>"+
			            			"<relevanceno>521" + rs.getString("cDLCode") + "7</relevanceno>"+        /*����ID+������+ҵ������*/
			            			"<operatioontype>7</operatioontype>"+                /*ҵ������ 1��������7�����ˣ�22������*/
			            			"<goodsownerid>521</goodsownerid>"+                  /*����ID 521*/
			            			"</e>";
			            }			
			            xml301=xml301 + "</dtl>";
					}
					/*count++;	*/
					count = 1;
					xml301=xml301 + "</e>";
					xml301=xml301 + "</doc>" +
								"<totalProperty>" + count + "</totalProperty>"+  /*��������*/
								"</result>";
					
					/*core("impWmsData",xml301,"301");*/
					 
					tempwrite(rs.getString("cDLCode"));   //�����ݺ�д��temp
					File writename = new File("E:\\BWMS\\301\\" + rs.getString("cDLCode") + " " + ddate()
							+ "auto301.xml"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�
					try
					{
						writename.delete();
						writename.createNewFile();
						BufferedWriter out = new BufferedWriter(
								new OutputStreamWriter(new FileOutputStream(
										writename), "UTF-8"));
						out.write(xml301); // \r\n��Ϊ����
						out.flush(); // �ѻ���������ѹ���ļ�
						out.close(); // ���ǵùر��ļ�
					}
					catch(Exception e)
					{
						e.printStackTrace();
						auto.area.append(e + ddate()+"\r\n");
						writelog(e + ddate());
					}
					auto.area.append(ccode + "�ϴ��ɹ� " + ddate()+"\r\n");
					writelog(ccode + "�ϴ��ɹ� " + ddate());
				}
		    }
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			xml302 = "";	
			xml301 = "";
			auto.area.append(ccode + "�ϴ�ʧ�� " + ddate()+"\r\n");
			writelog(ccode + "�ϴ�ʧ�� " + ddate());
		}				
		try {
			con.close();
			con=null;
		} catch (SQLException e1) {
			// TODO �Զ����ɵ� catch ��
			e1.printStackTrace();
			auto.area.append(e1 + ddate()+"\r\n");
			writelog(e1 + ddate());
		}	
		return xml302;
	}

	public static String manupo(String code)  /*�ֶ��ϴ��ɹ�����*/
	{
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = sdf.format(d);
	    Connection con = null;    
	    Statement stmt = null;    
	    ResultSet rs = null;    
	    ResultSet rs_ = null;  
	    int count = 0;
	    String xml301 = "";
	    	    
		try
		{
			Class.forName(driverName);
			con = DriverManager.getConnection(url);
			/*System.out.println("end."); */
			
			// Create and execute an SQL statement that returns some data.    
            String SQL = "SELECT * FROM PO_POmain where cPOID='" + code + "'";
			/*String SQL = "SELECT * FROM PO_POmain where cPOID='GRBW170417'";*/
            stmt = con.createStatement();    
            rs = stmt.executeQuery(SQL);
            
		}
		catch (Exception e) 
        {
            e.printStackTrace();
            writelog(e + ddate());
        }
		
		try {
			if (!rs.next())
			{
				JOptionPane.showInternalMessageDialog(bt1,"�õ��Ų�����","��Ϣ", JOptionPane.INFORMATION_MESSAGE); 	
				return "1";
			}
			else
			{
				xml301="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<result class=\"object\">"+
						"<datatype>301</datatype>"+        
						"<doc class=\"array\">";
				
				if (tempread().indexOf(rs.getString("cPOID")) > -1)
				{
					int res = JOptionPane.showConfirmDialog(null, "�ö������ϴ����Ƿ������", "��ʾ",
							JOptionPane.YES_NO_OPTION);
					if (res == JOptionPane.YES_OPTION) {
					} else {
						return "1";
					}
				}
					xml301=xml301+"<e class=\"object\">"+ 
				            "<porderno>" + rs.getString("POID") + "</porderno>"+    /*����������*/
							"<credate>" + datetime + "</credate>"+           /*�Ƶ�����*/
							"<arrivedate></arrivedate>"+
							"<medicineclass>1</medicineclass>"+          
							"<importflag>1</importflag>"+
							"<operationtype>1</operationtype>"+             /*ҵ������ 1��������7�����ˣ�22������*/
							"<dtllines></dtllines>"+  
							"<memo>" + (rs.getString("cMemo")==null?"":rs.getString("cMemo"))+ "</memo>"+                /*��ע*/
							"<srcno>" + rs.getString("cPOID") + "</srcno>"+                  /*������*/
							"<exporderid></exporderid>"+              
							"<billfrom>3</billfrom>"+
							"<usestatus>2</usestatus>"+
							"<mulrecflag></mulrecflag>"+
							"<reccount></reccount>"+
							"<inmode>0</inmode>"+
							"<invalidate></invalidate>"+
							"<sabackdate></sabackdate>"+                      /*�˻�����*/
							"<sbreasonid></sbreasonid>"+                       /*�˻�ԭ��ID*/
							"<deptno></deptno>"+
							"<deptname></deptname>"+
							"<gcompanyid>" + rs.getString("cVenCode") + "</gcompanyid>"+      /*��Ӧ��ID��*/
							"<decisionflag></decisionflag>"+    
							"<bmsdocid></bmsdocid>"+
							"<nowlflag>0</nowlflag>"+
							"<transclass>1</transclass>"+
							"<ecodeimpflag></ecodeimpflag>"+
							"<sysmodifydate>" + datetime + "</sysmodifydate>"+     /*ʱ�����*/
							"<srcporderid>" + rs.getString("cPTCode") + "</srcporderid>"+    /*ԭ��������ID �������������ͣ� */
							"<companystyle>2</companystyle>"+                    /*��λ���� 1���ͻ���2����Ӧ�̣�3����������*/
							"<transmode></transmode>"+
							"<erptransid></erptransid>"+
							"<goodsownerid>521</goodsownerid>"+                    /*����ID 521*/
							"<warehid>3</warehid>"+                                /*��������ID 3*/
							"<relevanceno>521" + rs.getString("cPOID") + "1</relevanceno>"+ /*����ID+������+ҵ������*/
							"<dtl class=\"array\">";         
					
					String SQL_ = "select * from PO_POdetails where POID='" + rs.getString("POID") + "'";
					stmt = con.createStatement();    
		            rs_ = stmt.executeQuery(SQL_);
					
		            while (rs_.next())
		            {
		            	xml301=xml301+"<e class=\"object\">"+
		            			"<porderstatus>1</porderstatus>"+
		            			"<batchno></batchno>"+                   
		            			"<lotno></lotno>"+                        /*���ţ�*/
		            			"<goodsqty>" + String.format("%.4f", Float.parseFloat(rs_.getString("iQuantity"))) + "</goodsqty>"+        /*����*/
		            			"<tradegoodsqty></tradegoodsqty>"+
		            			"<tradegoodspack>��</tradegoodspack>"+       /*������λ*/
		            			"<memo>" + (rs_.getString("cbMemo")==null?"":rs_.getString("cbMemo")) + "</memo>"+  /*��ע*/
		            			"<arrivedate></arrivedate>"+
		            			"<checkdate></checkdate>"+
		            			"<srcdtlno>" + rs_.getString("ID") + "</srcdtlno>"+         /*����ԭʼ���ݺţ���������*/
		            			"<lotflag>1</lotflag>"+
		            			"<batchflag>0</batchflag>"+
		            			"<gtradepack>��</gtradepack>"+                  /*������λ*/
		            			"<originqty></originqty>"+  
		            			"<price></price>"+                           /*����*/
		            			"<retailprice></retailprice>"+               /*���ۼ�*/
		            			"<amt></amt>"+                              /*���*/
		            			"<qty></qty>"+
		            			"<quadqty></quadqty>"+
		            			"<unquadqty></unquadqty>"+
		            			"<testqty></testqty>"+
		            			"<sadtlid></sadtlid>"+
		            			"<expdtlmemo></expdtlmemo>"+
		            			"<goodsstatusid></goodsstatusid>"+
		            			"<quantitystatus></quantitystatus>"+
		            			"<validdate></validdate>"+
		            			"<proddate></proddate>"+
		            			"<approvedocno></approvedocno>"+
		            			"<firstflag></firstflag>"+
		            			"<tpricetick></tpricetick>"+
		            			"<tpriceflag></tpriceflag>"+
		            			"<packsize>" + (int)(Float.parseFloat(rs_.getString("iQuantity"))/Float.parseFloat(rs_.getString("iNum"))) + "</packsize>"+   /*��װ��С*/
		            			"<packname>��</packname>"+                    /*��װ����*/  
		            			"<prtclass></prtclass>"+
		            			"<srcgoodsqty></srcgoodsqty>"+
		            			"<erpgoodsid>" + rs_.getString("cInvCode") + "</erpgoodsid>"+       /*����*/
		            			"<lotid></lotid>"+
		            			"<srcno>" + rs.getString("cPOID") + "</srcno>"+                 /*��ͷ�����Ź���*/
		            			"<realqty></realqty>"+
		            			"<warehid>3</warehid>"+                       /*��������ID 3*/
		            			"<killlotno></killlotno>"+
		            			"<relevanceno>521" + rs.getString("cPOID") + "1</relevanceno>"+        /*����ID+������+ҵ������*/
		            			"<operatioontype>1</operatioontype>"+                /*ҵ������ 1��������7�����ˣ�22������*/
		            			"<goodsownerid>521</goodsownerid>"+                  /*����ID 521*/
		            			"</e>";
		            }			
		            xml301=xml301 + "</dtl>";
				
				count++;		
				xml301=xml301 + "</e>";
				xml301=xml301 + "</doc>" +
							"<totalProperty>" + count + "</totalProperty>"+  /*��������*/
							"</result>";
				core("impWmsData",xml301,"301");
				writelog("�ɹ�����" + code + "�ϴ��ɹ�" + ddate()); //д��log 
				tempwrite(rs.getString("cPOID"));   //�����ݺ�д��temp
				File writename = new File("E:\\BWMS\\301\\" + rs.getString("cPOID") + " " + ddate()
						+ "manu301.xml"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�
				try {
					writename.delete();
					writename.createNewFile();
					BufferedWriter out = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(
									writename), "UTF-8"));
					out.write(xml301); // \r\n��Ϊ����
					out.flush(); // �ѻ���������ѹ���ļ�
					out.close(); // ���ǵùر��ļ�
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
					writelog(e + ddate());
				}
			}
			
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			xml301 = "";		
			writelog(e + ddate());
		}
		
		if (xml301.equals("")) 
		{
			JOptionPane.showInternalMessageDialog(bt1, "�ϴ�ʧ��", "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
		} else 
		{
			JOptionPane.showInternalMessageDialog(bt1, "�ϴ����", "��Ϣ",JOptionPane.INFORMATION_MESSAGE);			
		}
		try {
			con.close();
			con = null;
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			writelog(e + ddate());
		}
		return xml301;
	}
	
    public static String manuso(String code)   /*�ֶ��ϴ�������*/
    {
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = sdf.format(d);
	    Connection con = null;    
	    Statement stmt = null;    
	    ResultSet rs = null;    
	    ResultSet rs_ = null;  
	    int count = 0;
	    String xml302 = "";
	    String xml301 = "";
	    File writename = null;
	    String xml = "";     //�м�����
	    	    
		try
		{
			Class.forName(driverName);
			con = DriverManager.getConnection(url);
			/*System.out.println("end."); */
			
			// Create and execute an SQL statement that returns some data.    
            String SQL = "select * from dispatchlist where cdlcode='" + code + "'";
			/*String SQL = "SELECT * FROM PO_POmain where cPOID='GRBW170417'";*/
            stmt = con.createStatement();    
            rs = stmt.executeQuery(SQL);
            
		}
		catch (Exception e) 
        {
            e.printStackTrace();
            writelog(e + ddate());
        }
			
		try {
			if (!rs.next())
			{
				JOptionPane.showInternalMessageDialog(bt1,"�õ��Ų�����","��Ϣ", JOptionPane.INFORMATION_MESSAGE); 	
				return "1";
			}
			else 
			{
				if(rs.getString("bReturnFlag").equals("0"))    //�����˻�
				{
				xml302="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<result class=\"object\">"+
						"<datatype>302</datatype>"+        
						"<doc class=\"array\">";
				
				if (tempread().indexOf(rs.getString("cDLCode")) > -1)
				{
					int res = JOptionPane.showConfirmDialog(null, "�ö������ϴ����Ƿ������", "��ʾ",
							JOptionPane.YES_NO_OPTION);
					if (res == JOptionPane.YES_OPTION) {
					} else {
						return "1";
					}
				}				
					xml302=xml302+"<e class=\"object\">"+
							"<eorderno>" + rs.getString("cSOCode") + "</eorderno>"+   /*���۶���*/
							"<credate>" + datetime + "</credate>"+      /*��������*/
							"<preexpdate></preexpdate>"+
							"<expdate></expdate>"+
							"<prearrivedate></prearrivedate>"+
							"<arrivedate></arrivedate>"+
							"<ptransmodeid></ptransmodeid>"+
							"<transmode></transmode>"+
							"<earlydate></earlydate>"+
							"<composeinfo></composeinfo>"+
							"<operationtype>11</operationtype>"+      /*ҵ������ 11�����ۣ�12�������˳������ˣ���21 �������ƿ����18*/
							"<medicineclass></medicineclass>"+   
							"<receiveaddr>" + rs.getString("cDefine11") + "</receiveaddr>"+     /*�ջ���ַ*/
							"<receivehead></receivehead>"+          
							"<receiveman>" + rs.getString("cDefine12") + "</receiveman>"+       /*�ջ���*/
							"<partexpflag></partexpflag>"+
							"<samelotflag></samelotflag>"+
							"<urgenflag></urgenflag>"+
							"<addinvoiceflag></addinvoiceflag>"+
							"<arrivememo></arrivememo>"+
							"<exportmemo>" + (rs.getString("cMemo")==null?"":rs.getString("cMemo"))+ "</exportmemo>"+   //������ע
							"<detaillines></detaillines>"+
							"<srcexpno>" + rs.getString("cDLCode") + "</srcexpno>"+          /*��������*/
							"<transid></transid>"+
							"<porderid></porderid>"+
							"<billfrom></billfrom>"+
							"<usestatus></usestatus>"+
							"<outmode></outmode>"+               
							"<invoicetype>0</invoicetype>"+       /*��Ʊ���� 0����ֵ˰��Ʊ��1����ͨ��Ʊ��2:�Թ���Ʊ��Ĭ��Ϊ1*/
							"<transclass></transclass>"+
							"<receivephone>" + rs.getString("cDefine10") + "</receivephone>"+       /*�ջ��˵绰*/
							"<tpricetick></tpricetick>"+
							"<tpriceflag></tpriceflag>"+
							"<deptno></deptno>"+
							"<deptname></deptname>"+
							"<gcompanyid>" + rs.getString("cCusCode") + "</gcompanyid>"+      /*�ͻ�ID��*/
							"<salerid></salerid>"+
							"<salername>" + rs.getString("cMaker") + "</salername>"+       /*ҵ��Ա���ƣ�*/
							"<prtclass></prtclass>"+
							"<prtgroupset></prtgroupset>"+
							"<nowlflag></nowlflag>"+
							"<sysmodifydate>" + datetime + "</sysmodifydate>"+   /*ʱ�����*/
							"<goodsownerid>521</goodsownerid>"+                  /*����ID 521*/
							"<companystype>1</companystype>"+                      /*��λ���� 1���ͻ���2����Ӧ�̣�3���������ң�*/
							"<warehid>3</warehid>"+                               /*��������ID 3*/
							"<relevanceno>521" + rs.getString("cDLCode") + "11</relevanceno>"+        /*����ID+���۶�����+ҵ������*/
							"<realrecaddr></realrecaddr>"+               /*???*/ 		   
							"<dtl class=\"array\">";         
					
					String SQL_ = "select * from dispatchlists where DLID='" + rs.getString("DLID") + "'";
					stmt = con.createStatement();    
		            rs_ = stmt.executeQuery(SQL_);
					
		            while (rs_.next())
		            {
		            	xml302=xml302+"<e class=\"object\">"+
		            			"<batchno></batchno>"+
		            			"<lotno>" + rs_.getString("cBatch") + "</lotno>"+     /*���ţ�*/                                 /*���ţ�*/
		            			"<validdate>" + rs_.getString("dvDate") + "</validdate>"+      /*��Ч��*/
		            			"<goodsstatusid>1</goodsstatusid>"+                   /*��Ʒ״̬ 1���ϸ�*/
		            			"<price>" + rs_.getString("iTaxUnitPrice") + "</price>"+       /*����*/
		            			"<goodsqty>" + rs_.getString("iQuantity") + "</goodsqty>"+     /*����*/
		            			"<tradegoodsqty></tradegoodsqty>"+
		            			"<tradegoodspack>��</tradegoodspack>"+    /*������λ*/
		            			"<addmedcheckflag></addmedcheckflag>"+
		            			"<srcexpdtlno>" + rs_.getString("iDLsID") + "</srcexpdtlno>"+   /*������ϸ����*/
		            			"<usestatus></usestatus>"+
		            			"<gtradepack></gtradepack>"+
		            			"<dtlmemo></dtlmemo>"+
		            			"<retailprice>" + rs_.getString("iTaxUnitPrice") + "</retailprice>"+   /*���ۼ�*/
		            			"<amt>" + rs_.getString("iSum") + "</amt>"+         /*���*/
		            			"<expdtlmemo></expdtlmemo>"+
		            			"<otcflag></otcflag>"+
		            			"<trademark></trademark>"+
		            			"<approvedocno></approvedocno>"+
		            			"<proddate>" + rs_.getString("dMDate") + "</proddate>"+    /*��������*/
		            			"<tpricetick></tpricetick>"+
		            			"<tpriceflag></tpriceflag>"+
		            			"<packsize>" + rs_.getString("iInvExchRate") + "</packsize>"+    /*��װ��С?*/
		            			"<invno></invno>"+
		            			"<packname>��</packname>"+                       /*��װ����*/
		            			"<sorttype></sorttype>"+
		            			"<lotlimit></lotlimit>"+
		            			"<periodlimit></periodlimit>"+
		            			"<periodlimitunit></periodlimitunit>"+
		            			"<erpgoodsid>" + rs_.getString("cInvCode") + "</erpgoodsid>"+     /*�������*/
		            			"<srcexpno>" + rs.getString("cDLCode") + "</srcexpno>"+     /*��������*/
		            			"<warehid>3</warehid>"+                         /*��������ID 3*/
		            			"<killlotno>20160810</killlotno>"+              /*�������*/
		            			"<relevanceno>521" + rs.getString("cDLCode") + "11</relevanceno>"+   /*����ID+���۶�����+ҵ������*/
		            			"<goodsownerid>521</goodsownerid>"+              /*����ID 521*/	
		            			"<realqty></realqty>"+ 
		            			"</e>";
		            }			
		            xml302=xml302 + "</dtl>";
				
				count++;		
				xml302=xml302 + "</e>";
				xml302=xml302 + "</doc>" +
							"<totalProperty>" + count + "</totalProperty>"+  /*��������*/
							"</result>";
				core("impWmsData",xml302,"302");
				tempwrite(rs.getString("cDLCode"));   //�����ݺ�д��temp
				writelog("������" + code + "�ϴ��ɹ�" + ddate()); //д��log
				writename = new File("E:\\BWMS\\302\\" + rs.getString("cDLCode") + " " + ddate()
						+ "manu302.xml"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�
				xml = xml302;
				}
				
				else if(rs.getString("bReturnFlag").equals("1"))    //�����˻�
				{
					xml301="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
							"<result class=\"object\">"+
							"<datatype>301</datatype>"+        
							"<doc class=\"array\">";
					
					if (tempread().indexOf(rs.getString("cDLCode")) > -1)   
					{
						int res = JOptionPane.showConfirmDialog(null, "�ö������ϴ����Ƿ������", "��ʾ",
								JOptionPane.YES_NO_OPTION);
						if (res == JOptionPane.YES_OPTION) {
						} else {
							return "1";
						}
					}
					
						xml301=xml301+"<e class=\"object\">"+ 
					            "<porderno>" + (rs.getString("cSOCode")==null?"":rs.getString("cSOCode"))+ "</porderno>"+    /*������*/
								"<credate>" + datetime + "</credate>"+           /*�Ƶ�����*/
								"<arrivedate></arrivedate>"+
								"<medicineclass>1</medicineclass>"+          
								"<importflag>1</importflag>"+
								"<operationtype>7</operationtype>"+             /*ҵ������ 1��������7�����ˣ�22������*/
								"<dtllines></dtllines>"+  
								"<memo>" + (rs.getString("cMemo")==null?"":rs.getString("cMemo"))+ "</memo>"+  /*��ע*/
								"<srcno>" + rs.getString("cDLCode") + "</srcno>"+                  /*�˻�����*/
								"<exporderid></exporderid>"+              
								"<billfrom>3</billfrom>"+
								"<usestatus>2</usestatus>"+
								"<mulrecflag></mulrecflag>"+
								"<reccount></reccount>"+
								"<inmode>0</inmode>"+
								"<invalidate></invalidate>"+
								"<sabackdate>" + rs.getString("dDate") + "</sabackdate>"+     /*�˻�����*/
								"<sbreasonid></sbreasonid>"+                       /*�˻�ԭ��ID*/
								"<deptno></deptno>"+
								"<deptname></deptname>"+
								"<gcompanyid>" + rs.getString("cCusCode") + "</gcompanyid>"+      /*�ͻ�ID��*/
								"<decisionflag></decisionflag>"+    
								"<bmsdocid></bmsdocid>"+
								"<nowlflag>0</nowlflag>"+
								"<transclass>1</transclass>"+
								"<ecodeimpflag></ecodeimpflag>"+
								"<sysmodifydate>" + datetime + "</sysmodifydate>"+     /*ʱ�����*/
								"<srcporderid>" + rs.getString("cSTCode") + "</srcporderid>"+    /*ԭ��������ID �������������ͣ� */
								"<companystyle>1</companystyle>"+                    /*��λ���� 1���ͻ���2����Ӧ�̣�3����������*/
								"<transmode></transmode>"+
								"<erptransid></erptransid>"+
								"<goodsownerid>521</goodsownerid>"+                    /*����ID 521*/
								"<warehid>3</warehid>"+                                /*��������ID 3*/
								"<relevanceno>521" + rs.getString("cDLCode") + "7</relevanceno>"+ /*����ID+������+ҵ������*/
								"<dtl class=\"array\">";         
						
						String SQL_ = "select * from dispatchlists where DLID='" + rs.getString("DLID") + "'";
						stmt = con.createStatement();    
			            rs_ = stmt.executeQuery(SQL_);
						
			            while (rs_.next())
			            {
			            	xml301=xml301+"<e class=\"object\">"+
			            			"<porderstatus>1</porderstatus>"+
			            			"<batchno></batchno>"+                   
			            			"<lotno>" + rs_.getString("cBatch") + "</lotno>"+         /*���ţ�*/
			            			"<goodsqty>" + (rs_.getString("iQuantity").substring(1,(rs_.getString("iQuantity")).length())) + "</goodsqty>"+  /*����*/
			            			"<tradegoodsqty></tradegoodsqty>"+
			            			"<tradegoodspack>��</tradegoodspack>"+       /*������λ*/
			            			"<memo>" + (rs_.getString("cMemo")==null?"":rs_.getString("cMemo")) + "</memo>"+  /*��ע*/
			            			"<arrivedate></arrivedate>"+
			            			"<checkdate></checkdate>"+
			            			"<srcdtlno>" + rs_.getString("AutoID") + "</srcdtlno>"+         /*����ԭʼ���ݺţ���������*/
			            			"<lotflag>1</lotflag>"+
			            			"<batchflag>0</batchflag>"+
			            			"<gtradepack>��</gtradepack>"+                  /*������λ*/
			            			"<originqty></originqty>"+  
			            			"<price>" + rs_.getString("iTaxUnitPrice") + "</price>"+      /*����*/
			            			"<retailprice>" + rs_.getString("iTaxUnitPrice") + "</retailprice>"+    /*���ۼ�*/
			            			"<amt></amt>"+               /*���*/
			            			"<qty></qty>"+
			            			"<quadqty></quadqty>"+
			            			"<unquadqty></unquadqty>"+
			            			"<testqty></testqty>"+
			            			"<sadtlid>" + rs_.getString("iDLsID") + "</sadtlid>"+  //����ԭҵ�񵥺� ��������ϸ���ţ���
			            			"<expdtlmemo></expdtlmemo>"+
			            			"<goodsstatusid></goodsstatusid>"+
			            			"<quantitystatus></quantitystatus>"+
			            			"<validdate>" + rs_.getString("dvDate") + "</validdate>"+   //��Ч��
			            			"<proddate>" + (rs_.getString("dMDate")==null?"":rs_.getString("dMDate")) + "</proddate>"+   //��������
			            			"<approvedocno></approvedocno>"+
			            			"<firstflag></firstflag>"+
			            			"<tpricetick></tpricetick>"+
			            			"<tpriceflag></tpriceflag>"+
			            			"<packsize>" + (int)(Float.parseFloat(rs_.getString("iQuantity"))/Float.parseFloat(rs_.getString("iNum"))) + "</packsize>"+   /*��װ��С*/
			            			"<packname>��</packname>"+                    /*��װ����*/  
			            			"<prtclass></prtclass>"+
			            			"<srcgoodsqty></srcgoodsqty>"+
			            			"<erpgoodsid>" + rs_.getString("cInvCode") + "</erpgoodsid>"+       /*����*/
			            			"<lotid></lotid>"+
			            			"<srcno>" + rs.getString("cDLCode") + "</srcno>"+          /*��ͷ�����Ź���*/
			            			"<realqty></realqty>"+
			            			"<warehid>3</warehid>"+                       /*��������ID 3*/
			            			"<killlotno></killlotno>"+
			            			"<relevanceno>521" + rs.getString("cDLCode") + "7</relevanceno>"+        /*����ID+������+ҵ������*/
			            			"<operatioontype>7</operatioontype>"+                /*ҵ������ 1��������7�����ˣ�22������*/
			            			"<goodsownerid>521</goodsownerid>"+                  /*����ID 521*/
			            			"</e>";
			            }			
			            xml301=xml301 + "</dtl>";
					
					count++;		
					xml301=xml301 + "</e>";
					xml301=xml301 + "</doc>" +
								"<totalProperty>" + count + "</totalProperty>"+  /*��������*/
								"</result>";
					
					core("impWmsData",xml301,"301");
					writelog("������" + code + "�ϴ��ɹ�" + ddate()); //д��log 
					tempwrite(rs.getString("cDLCode"));   //�����ݺ�д��temp
					writename = new File("E:\\BWMS\\301\\" + rs.getString("cDLCode") + " " + ddate()
							+ "manu301.xml"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�		
					xml = xml301;
				}		
								
				try {
					writename.delete();
					writename.createNewFile();
					BufferedWriter out = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(
									writename), "UTF-8"));
					out.write(xml); // \r\n��Ϊ����
					out.flush(); // �ѻ���������ѹ���ļ�
					out.close(); // ���ǵùر��ļ�
				} catch (IOException e) {
					// TODO �Զ����ɵ� catch ��
					e.printStackTrace();
					writelog(e + ddate()); //д��log
				}
			}
			
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			xml = "";
			writelog(e + ddate()); //д��log
		}	
		try {
			con.close();
			con=null;
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			writelog(e + ddate()); //д��log
		}	
		return xml;
	}

    public static String manucustom(String code)    /*�ֶ��ϴ��ͻ�����*/
    {
    	Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = sdf.format(d);
	    Connection con = null;    
	    Statement stmt = null;    
	    ResultSet rs = null;    
	    int count = 0;
	    
	    String xml102="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<result class=\"object\">"+
				"<datatype>102</datatype>"+        
				"<doc class=\"array\">";
		try
		{
			Class.forName(driverName);
			con = DriverManager.getConnection(url);
			/*System.out.println("end.");*/ 
			
			// Create and execute an SQL statement that returns some data.    
            String SQL = "select * from customer where cCusCode='" + code + "'";
            stmt = con.createStatement();    
            rs = stmt.executeQuery(SQL);
            
		}
		catch (Exception e) 
        {
            e.printStackTrace();
            writelog("�ͻ����ϣ�" + e + ddate());
        }
		
		try {
			if (!rs.next())
			{
				JOptionPane.showInternalMessageDialog(basedata.bt1,"�ÿͻ�������","��Ϣ", JOptionPane.INFORMATION_MESSAGE); 	
				return "1";
			}
			else
			{
				xml102 = xml102 + "<e class=\"object\">"
						+ "<goodsownerid>521</goodsownerid>"     /*����ID 521*/
						+ "<gcompanyid>" + rs.getString("cCusCode") + "</gcompanyid>"   /*�ͻ�����*/
						+ "<companystype>1</companystype>"     /*��λ���� 1���ͻ���2����Ӧ�̣�3����������*/
						+ "<companyname>" + rs.getString("cCusName") + "</companyname>"   /*�ͻ�����*/
						+ "<gopinyin></gopinyin>"
						+ "<opcode>135</opcode>"        /*�����룿*/
						+ "<companyshortname>" + rs.getString("cCusAbbName") + "</companyshortname>"  /*�ͻ����*/
						+ "<bank>" + rs.getString("cCusBank") + "</bank>"    /*��������*/
						+ "<bankno>" + rs.getString("cCusAccount") + "</bankno>"     /*�����˺�*/
						+ "<chiefofficer></chiefofficer>"
						+ "<connector>" + rs.getString("cCusPerson") + "</connector>"    /*��ϵ��*/
						+ "<connphone>" + rs.getString("cCusPhone") + "</connphone>"    /*��ϵ�绰*/
						+ "<deliveraddr>" + rs.getString("cCusAddress") + "</deliveraddr>"   /*�ͻ���ַ*/
						+ "<invoiceaddr>" + rs.getString("cCusDefine7") + "</invoiceaddr>"   /*��Ʊ��ַ*/
						+ "<invoicehead>" + rs.getString("cCusName") + "</invoicehead>"     /*��Ʊ���ƣ�*/
						+ "<invoiceman>" + rs.getString("cCusDefine3") + "</invoiceman>"  /*��Ʊ��Ա�� ��Ʊ�ˣ�*/
						+ "<invoicephone>" + rs.getString("cCusDefine5") + "</invoicephone>"  /*��Ʊ�绰�� ��Ʊ�˵绰��*/
						+ "<postcode></postcode>"
						+ "<specialrequire></specialrequire>"
						+ "<taxregistno>" + rs.getString("cCusRegCode") + "</taxregistno>"  /*˰��*/
						+ "<telephone></telephone>"
						+ "<memo></memo>"              /*��ע*/
						+ "<addmedcheckflag>1</addmedcheckflag>"
						+ "<usestatus>1</usestatus>"
						+ "<companyno>" + rs.getString("cCusCode") + "</companyno>"     /*����ԭ��λ���룿*/
						+ "<orgno>1</orgno>"               /*��֯��������?*/
						+ "<impflag>1</impflag>"         /*�����־��*/
						+ "<creDate>" + datetime + "</creDate>"  
						+ "<city></city>"
						+ "<corpid></corpid>"
						+ "<goodsownerflag>0</goodsownerflag>"    /*������־ 0���ǻ�����1������*/
						+ "<legalpersion></legalpersion>"
						+ "<unifyno></unifyno>"
						+ "<defrtanid>1</defrtanid>"     /*Ĭ�ϵ�ַID��*/
						+ "<invclass>1</invclass>"        /*��Ʊ���ࣿ*/
						+ "<invprintplan></invprintplan>"
						+ "<invtype>1</invtype>"        /*��Ʊ���ͣ�*/
						+ "<modedocid>1</modedocid>"    /*��ά���ӡ����ģ��ID��*/
						+ "<orderflag>1</orderflag>"    /*���ϼ����־��*/
						+ "<periodlimit>1</periodlimit>"    /*Ч�����ƣ�*/
						+ "<periodlimtunit></periodlimtunit>"
						+ "<printflag>0</printflag>"       /*�Ƿ��ӡ��Ʊ�� 0������ӡ��1����ӡ*/
						+ "<companycredate>" + datetime + "</companycredate>"
						+ "<printplan></printplan>"
						+ "<lotlimit>3</lotlimit>"     /*1��ֻ��һ�����ţ�2������������ţ�3���������Ÿ�����Ĭ��Ϊ3*/
						+ "<sysmodifydate>" + datetime + "</sysmodifydate>"
						+ "<warehid>3</warehid>"      /*��������ID 3*/
						+ "<dtl class=\"array\">";
				
				/*String SQL_ = "select * from PO_POdetails where POID='" + rs.getString("POID") + "'";
				stmt = con.createStatement();    
	            rs_ = stmt.executeQuery(SQL_);
				
	            while (rs_.next())
	            {*/
				xml102 = xml102 + "<e class=\"object\">"
						+ "<goodsownerid>521</goodsownerid>"    /*����ID 521*/
						+ "<gocompanyid>" + rs.getString("cCusCode") + "</gocompanyid>"      /*�ͻ�����*/
						+ "<transid>1</transid>"     /*Ĭ�ϵ�ַID��*/
						+ "<inceptaddr>" + rs.getString("cCusAddress") + "</inceptaddr>"   /*�����ַ*/
						+ "<connector>" + rs.getString("cCusPerson") + "</connector>"     /*��ϵ��*/
						+ "<connphone>" + rs.getString("cCusPhone") + "</connphone>"      /*��ϵ�绰*/
						+ "<telefax></telefax>"
						+ "<postcode></postcode>"
						+ "<memo></memo>"           /*��ע*/
						+ "<usestatus>1</usestatus>"   /*ʹ��״̬ 1:������0�����ϣ�Ĭ��Ϊ1*/
						+ "<tranposname></tranposname>"
						+ "<impflag>1</impflag>"     /*�����ע�� Ĭ��Ϊ1*/
						+ "<creDate>" + datetime + "</creDate>"
						+ "<city></city>"
						+ "<equtransid>1</equtransid>"    /*��ͬ�ص�id?*/
						+ "<sysmodifydate>" + datetime + "</sysmodifydate>"
						+ "<companystype>1</companystype>"      /*��λ���� 1���ͻ���2����Ӧ�̣�3����������*/
						+ "<warehid>3</warehid>"        /*��������ID 3*/
						+ "</e>";
	            /*}*/		
	            xml102=xml102 + "</dtl>";
			
			 count++;		
			 xml102=xml102 + "</e>";
					
			}
			xml102=xml102+"</doc>"+
					"<totalProperty>" + count + "</totalProperty>"+  /*��������*/
					"</result>";
			if(core("impWmsData",xml102,"102").indexOf("success") > -1)   //�ϴ�
			{
				writelog("�ͻ����ϣ�" + code + "�ϴ��ɹ�" + ddate());
			}
			else
			{
				writelog("�ͻ����ϣ�" + code + "�ϴ�ʧ��" + ddate());
			}
			
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			writelog("�ͻ����ϣ�" + e + ddate());
			xml102 = "";		
		}
		try {
			con.close();
			con=null;
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			writelog("�ͻ����ϣ�" + e + ddate());
		}
		File writename = new File("E:\\BWMS\\102\\" + ddate() + "manu102.xml"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�
		try {
			writename.delete();
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(writename), "UTF-8"));
			out.write(xml102); // \r\n��Ϊ����
			out.flush(); // �ѻ���������ѹ���ļ�
			out.close(); // ���ǵùر��ļ�
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			writelog("�ͻ����ϣ�" + e + ddate());
		} // �������ļ�

		
		return xml102;
    }
    
	public static String manuInvent(String code)    /*�ֶ��ϴ���Ʒ����*/
	{
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = sdf.format(d);
	    Connection con = null;    
	    Statement stmt = null;    
	    ResultSet rs = null;  
	    int count = 0;
	    
	    String xml101="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<result class=\"object\">"+
				"<datatype>101</datatype>"+        
				"<doc class=\"array\">";
		try
		{
			Class.forName(driverName);
			con = DriverManager.getConnection(url);
			/*System.out.println("end.");*/ 
			
			// Create and execute an SQL statement that returns some data.    
           /* String SQL = "select * from Inventory A left join (select ccode,ClassName from EF_Base_V_Information B) C on A.cInvCode=C.ccode left join (select D.iChangRate,D.cComunitCode from ComputationUnit D) E on A.cSAComUnitCode=E.cComunitCode where cInvCode='00463'";*/
            String SQL = "select * from Inventory A left join (select ccode,ClassName from EF_Base_V_Information B) C on A.cInvCode=C.ccode left join (select D.iChangRate,D.cComunitCode from ComputationUnit D) E on A.cSAComUnitCode=E.cComunitCode left join (select G.dEffDate,G.dInvalidDate,F.cinvcode xxx,F.dcreatesystime from EF_ZZGL_VenLicence F left join  EF_ZZGL_VenLicences G on F.ID=G.ID ) H on H.xxx=A.cInvCode where E.iChangRate is not null and (cInvCode='" + code + "') order by dcreatesystime desc" ;           
            stmt = con.createStatement();    
            rs = stmt.executeQuery(SQL);
            
		}
		catch (Exception e) 
        {
            e.printStackTrace();
            writelog("��Ʒ���ϣ�" + e + ddate());
        }
		
		try {
			if (!rs.next())
			{
				JOptionPane.showInternalMessageDialog(basedata.bt1,"�ô��������","��Ϣ", JOptionPane.INFORMATION_MESSAGE); 	
				return "1";
			}
			else
			{
				int cclass = 2;
				if (rs.getString("ClassName").indexOf("һ") > -1) 
				{
					cclass = 0;
				} else if (rs.getString("ClassName").indexOf("��") > -1) 
				{
					cclass = 1;
				}					
				xml101 = xml101
						+ "<e class=\"object\">"
						+ "<goodsownerid>521</goodsownerid>"      /*����ID 521*/
						+ "<goodsownid>"+ rs.getString("cInvcode") +"</goodsownid>"    /*�������*/
						+ "<barcode></barcode>"
						+ "<goodsno></goodsno>"
						+ "<gopcode>135</gopcode>"                /*�����룿*/
						+ "<gopinyin>"+ rs.getString("cInvcode") +"</gopinyin>"      /*ƴ����*/
						+ "<poisondrug>"+ cclass +"</poisondrug>"                    /*�������*/
						+ "<goodsname>"+ rs.getString("cInvName") +"</goodsname>"     /*�������*/
						+ "<goodsengname>"+ rs.getString("cInvcode") +"</goodsengname>"  /*���Ӣ������*/
						+ "<goodstype>"+ rs.getString("cInvStd") +"</goodstype>"        /*���*/
						+ "<goodsformalname></goodsformalname>"
						/*+ "<factname>"+ (rs.getString("cEnterprise")==null?"1":rs.getString("cEnterprise")) +"</factname>" */ /*��������*/
						+ "<factname>ǿ�����Ϻ���ҽ���������޹�˾</factname>"
						+ "<tradepack>��</tradepack>"         /*���׵�λ*/
						+ "<outpack>��</outpack>"           /*������λ*/
						+ "<addmedcheckflag>1</addmedcheckflag>"
						+ "<pasteflag>0</pasteflag>"
						+ "<wholesaleprice></wholesaleprice>"
						+ "<resaleprice></resaleprice>"
						+ "<purchasetax></purchasetax>"
						+ "<saletax></saletax>"
						+ "<lotflag>1</lotflag>"
						+ "<batchflag>0</batchflag>"
						+ "<memo></memo>"             /*��ע*/
						+ "<drugform></drugform>"
						+ "<status>1</status>"
						+ "<otcflag></otcflag>"
						+ "<trademark></trademark>"
						+ "<storagecondition>1</storagecondition>"
						+ "<approvedocno></approvedocno>"
						+ "<packsize>"+ (int)Float.parseFloat(rs.getString("iChangRate")) +"</packsize>"  /*��װ��С�����������ʣ���*/
						+ "<dbcheck></dbcheck>"
						+ "<periodunit></periodunit>"
						+ "<validperiod></validperiod>"
						+ "<varietyname></varietyname>"
						+ "<supplyername></supplyername>"
						+ "<registdocno>"+ rs.getString("cInvDefine7") +"</registdocno>"   /*ע��֤��*/
						+ "<varietydescid></varietydescid>"
						+ "<firstindate></firstindate>"
						+ "<centrepicksize></centrepicksize>"
						+ "<credate>" + datetime + "</credate>"
						+ "<alonepackflag></alonepackflag>"
						+ "<dictno></dictno>"
						+ "<filegroundid></filegroundid>"
						+ "<gmpflag></gmpflag>"
						+ "<jkhzflag></jkhzflag>"
						+ "<signature></signature>"
						+ "<specailflag></specailflag>"
						+ "<boxnoflag></boxnoflag>"
						+ "<dbincheck></dbincheck>"
						+ "<dbpickflag></dbpickflag>"
						+ "<ecodeflag></ecodeflag>"
						+ "<emgoodsflag></emgoodsflag>"
						+ "<emgoodsreason></emgoodsreason>"
						+ "<prodlicenseno></prodlicenseno>"
						+ "<qtygene></qtygene>"
						+ "<repflag></repflag>"
						+ "<creDate>" + datetime + "</creDate>"
						+ "<prodarea>"+ (rs.getString("cProduceAddress")==null?"1":rs.getString("cProduceAddress")) +"</prodarea>"  /*����*/
						+ "<goodsstatus>1</goodsstatus>"       /*��Ʒ״̬*/
						+ "<tradepacknameid></tradepacknameid>"
						+ "<goodsid></goodsid>"
						+ "<ownergoodsid></ownergoodsid>"
						+ "<outpacknameid></outpacknameid>"
						+ "<tradegoodspackid></tradegoodspackid>"
						+ "<outgoodspackid></outgoodspackid>"
						+ "<storagetype></storagetype>"
						+ "<sysmodifydate>" + datetime + "</sysmodifydate>"
						+ "<factid></factid>"
						+ "<classname></classname>"
						+ "<tradegoodslength></tradegoodslength>"
						+ "<tradegoodswidth></tradegoodswidth>"
						+ "<tradegoodsheight></tradegoodsheight>"
						+ "<tradegoodscubage></tradegoodscubage>"
						+ "<tradegoodsweight></tradegoodsweight>"
						+ "<tradeweightunit></tradeweightunit>"
						+ "<outgoodslength></outgoodslength>"
						+ "<outgoodswidth></outgoodswidth>"
						+ "<outgoodsheight></outgoodsheight>"
						+ "<outgoodscubage></outgoodscubage>"
						+ "<outgoodsweight></outgoodsweight>"
						+ "<outweightunit></outweightunit>"
						+ "<templow></templow>"     /*�¶�����*/
						+ "<temphigh></temphigh>"     /*�¶�����*/
						+ "<packscodeflag></packscodeflag>"
						+ "<lotnocannullflag></lotnocannullflag>"
						+ "<warehid>3</warehid>"             /*��������ID 3*/
						+ "<registstartdate>"+ (rs.getString("dEffDate")==null?"":rs.getString("dEffDate")) +"</registstartdate>"     /*ע��֤��ʼʱ��*/
						+ "<registenddate>"+ (rs.getString("dInvalidDate")==null?"":rs.getString("dInvalidDate")) +"</registenddate>"         /*ע��֤����ʱ��*/
						+ "<goodsmodel>"+ rs.getString("cSAComUnitCode") +"</goodsmodel>" /*�����ͺ��ֶΣ������ʱ��룩*/
						+ "<scopeno></scopeno>" + "</e>";
				
				count++;
			}
				xml101 = xml101 + "</doc>" + "<totalProperty>" + count
						+ "</totalProperty>" +  /*�������� */
						"</result>";
			
				if(core("impWmsData",xml101,"101").indexOf("success") > -1)
				{
					writelog("��Ʒ���ϣ�" + code + "�ϴ��ɹ�" + ddate());
				}
				else
				{
					writelog("��Ʒ���ϣ�" + code + "�ϴ�ʧ��" + ddate());
				}
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			writelog("��Ʒ���ϣ�" + e + ddate());
			xml101 = "";			
		}
		try {
			con.close();
			con=null;
		} catch (SQLException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			writelog("��Ʒ���ϣ�" + e + ddate());
		}
		File writename = new File("E:\\BWMS\\101\\" + ddate() + "manu101.xml"); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�
		try {
			writename.delete();
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(writename), "UTF-8"));
			out.write(xml101); // \r\n��Ϊ����
			out.flush(); // �ѻ���������ѹ���ļ�
			out.close(); // ���ǵùر��ļ�
		} catch (IOException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			writelog("��Ʒ���ϣ�" + e + ddate());
		} // �������ļ�

		return xml101;
	}
	
	public static String[] loadget()      /*��ȡ�����������ݵ�����*/
	{
		String[] cou = new String[4];
		int i=0;

		File f = new File("E:\\BWMS\\result\\getlist\\" + getxml(path_getlist));   
		SAXReader reader = new SAXReader();   
		Document doc;
		try {
			doc = reader.read(f);
			Element root = doc.getRootElement();  
			/*Element foo;*/
			for (Iterator iter = root.elementIterator("e"); iter.hasNext();) 
			{
				Element element = (Element) iter.next(); 
				cou[i] = element.elementText("rowcount");
				i++;				
			}
			return cou;
			
		} catch (DocumentException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}   
		return cou;
	}
	
	public static String getxml(String path)   //����ָ��Ŀ¼������޸ĵ��ļ���
	{
		File fi=new File(path);
		//�г���Ŀ¼�������ļ����ļ���
		File[] files = fi.listFiles();
		//�����ļ�����޸����ڵ�������
		Arrays.sort(files, new Comparator<File>() {
		   @Override
		   public int compare(File file1, File file2) {
		      return (int)(file2.lastModified()-file1.lastModified());
		   }
		});
		return(files[0].getName());
	}
	
	public static void storein(String fullpath)    //��ָ��·���µ��ļ�ת��ΪU8��׼�����ļ�
	{
		/*File f = new File(path_320 + "\\" + getxml(path_320));  */
		File f = new File(fullpath);
		SAXReader reader = new SAXReader();   
		Document doc;
		Connection con = null;    
	    Statement stmt = null;    
	    ResultSet rs = null; 
	    FileWriter fw = null;
		String porderid = "";
		String sql = "";
		String lorderids = "";  //�����������е���ⵥ����
		String xml320 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ufinterface sender=\"A008\" receiver=\"u8\" roottag=\"storein\" proc=\"add\" docid=\"345821559\" codeexchanged=\"N\" exportneedexch=\"N\" display=\"��ⵥ\" family=\"������\" timestamp=\"0x00000000001D47D5\">";
		try {
			doc = reader.read(f);
			Element root = doc.getRootElement();
			Iterator iter_ = root.elementIterator("doc");
			Element element_ = (Element) iter_.next();
			testDownload("","bwmssql.txt");  //����bwmssql.txt
			/*Element foo;*/
			for (Iterator iter = element_.elementIterator("e"); iter.hasNext();)   //���������־
			{
				Element element = (Element) iter.next();
				if(lorderids.indexOf(element.elementText("lorderid")) > -1)
				{				
				}
				else
				{
					lorderids = lorderids + element.elementText("lorderid") + " ";
				}				
			}
            auto.area.append(lorderids + "���سɹ� " + ddate()+"\r\n");
			writelog(lorderids + "���سɹ� " + ddate());
			
			for (Iterator iter = element_.elementIterator("e"); iter.hasNext();) 
			{
				Element element = (Element) iter.next();
				//������&&U8�����ʱ���
				Class.forName(driverName);
				con = DriverManager.getConnection(url);
				String SQL = "select D.ichangrate,A.csacomunitcode from Inventory A left join ComputationUnit D on A.cPUComUnitCode = D.cComunitCode where A.cInvCode='" + element.elementText("goodsid") + "'";
	            stmt = con.createStatement();    
	            rs = stmt.executeQuery(SQL);
	            rs.next();
	            //����
	            float num = Float.parseFloat(rs.getString("ichangrate")) * Float.parseFloat(element.elementText("realrecvqty"));
	            
				if(porderid=="")    //����ǵ�һ��  ���������ϸ��
				{
					porderid = element.elementText("porderid");
					xml320 = xml320 + "<storein>"
							+ "<header>"
							+ "<receiveflag>1</receiveflag>"
							+ "<vouchtype>01</vouchtype>"
							+ "<businesstype>��ͨ�ɹ�</businesstype>"
							+ "<source>�ɹ�����</source>"
							+ "<businesscode>" + element.elementText("porderid") + "</businesscode>"  //�ɹ�������
							+ "<warehousecode>10001</warehousecode>"   //�ֿ�
							+ "<date>" + date.format(new Date()) + "</date>"   //���ڣ����죩
							+ "<code>" + element.elementText("lorderid") + "</code>"    //��ⵥ���
							+ "<purchasetypecode>01</purchasetypecode>"    //�ɹ�����
							+ "<vendorcode>" + element.elementText("sourcecompanyid") + "</vendorcode>"  //��Ӧ��ID
							+ "<ordercode>" + element.elementText("porderid") + "</ordercode>"     //�ɹ�������
							+ "<memory>" + element.elementText("memo") + "</memory>"     //��ע
							+ "<maker>�Լ���</maker>"
							+ "<auditdate>" + sub2(element.elementText("validdate")) + "</auditdate>"  //�������
							+ "<taxrate>17</taxrate>"
							+ "<exchname>�����</exchname>"
							+ "<exchrate>1</exchrate>"
							+ "</header>"
							+ "<body>"
							+ "<entry>"
							+ "<inventorycode>" + element.elementText("goodsid") + "</inventorycode>"   //�������
							+ "<quantity>" + element.elementText("realrecvqty") + "</quantity>"      //����
							+ "<assitantunit>" + rs.getString("csacomunitcode") + "</assitantunit>"    //U8�����ʱ���
							+ "<define32></define32>"    //�װ���
							+ "<irate>" + rs.getString("ichangrate") + "</irate>"    //������
							+ "<number>" + num + "</number>"    //����
							+ "<serial>" + element.elementText("lotno") + "</serial>"     //����
							+ "<makedate>" + sub2(element.elementText("proddate")) + "</makedate>"    //��������
							+ "<validdate>" + sub2(element.elementText("validdate")) + "</validdate>"    //ʧЧ����
							+ "<batchproperty6></batchproperty6>"     //ע��֤��
							+ "</entry>";
					if(sql.equals(""))
					{
						sql = "1" + " " + element.elementText("lorderid") + " " + element.elementText("porderid")+ " " + element.elementText("goodsid") + " " + element.elementText("realrecvqty") + " " + num;
					}
					else
					{
						sql = sql + "\r\n" + "1" + " " + element.elementText("lorderid") + " " + element.elementText("porderid")+ " " + element.elementText("goodsid") + " " + element.elementText("realrecvqty") + " " + num;						
					}
				}
				else if(porderid.equals(element.elementText("porderid")))    /*�������ͬһ����  ����ϸ��*/
				{
					xml320 = xml320 +  "<entry>"
							+ "<inventorycode>" + element.elementText("goodsid") + "</inventorycode>"   //�������
							+ "<quantity>" + element.elementText("realrecvqty") + "</quantity>"      //����
							+ "<assitantunit>" + rs.getString("csacomunitcode") + "</assitantunit>"    //U8�����ʱ���
							+ "<define32></define32>"    //�װ���
							+ "<irate>" + rs.getString("ichangrate") + "</irate>"    //������
							+ "<number>" + num + "</number>"    //����
							+ "<serial>" + element.elementText("lotno") + "</serial>"     //����
							+ "<makedate>" + sub2(element.elementText("proddate")) + "</makedate>"    //��������
							+ "<validdate>" + sub2(element.elementText("validdate")) + "</validdate>"    //ʧЧ����
							+ "<batchproperty6></batchproperty6>"     //ע��֤��
							+ "</entry>";
					sql = sql + " " + element.elementText("goodsid") + " " + element.elementText("realrecvqty") + " " + num;
				}	
				else          /*������ڲ�ͬ����  ����������ϸ��*/
				{
					porderid = element.elementText("porderid");
					xml320 = xml320 + "</body>"+"</storein>";
					xml320 = xml320 + "<storein>"
							+ "<header>"
							+ "<receiveflag>1</receiveflag>"
							+ "<vouchtype>01</vouchtype>"
							+ "<businesstype>��ͨ�ɹ�</businesstype>"
							+ "<source>�ɹ�����</source>"
							+ "<businesscode>" + element.elementText("porderid") + "</businesscode>"  //�ɹ�������
							+ "<warehousecode>10001</warehousecode>"   //�ֿ�
							+ "<date>" + date.format(new Date()) + "</date>"   //���ڣ����죩
							+ "<code>" + element.elementText("lorderid") + "</code>"    //��ⵥ���
							+ "<purchasetypecode>01</purchasetypecode>"    //�ɹ�����
							+ "<vendorcode>" + element.elementText("sourcecompanyid") + "</vendorcode>"  //��Ӧ��ID
							+ "<ordercode>" + element.elementText("porderid") + "</ordercode>"     //�ɹ�������
							+ "<memory>" + element.elementText("memo") + "</memory>"     //��ע
							+ "<maker>�Լ���</maker>"
							+ "<auditdate>" + sub2(element.elementText("validdate")) + "</auditdate>"  //�������
							+ "<taxrate>17</taxrate>"
							+ "<exchname>�����</exchname>"
							+ "<exchrate>1</exchrate>"
							+ "</header>"
							+ "<body>"
							+ "<entry>"
							+ "<inventorycode>" + element.elementText("goodsid") + "</inventorycode>"   //�������
							+ "<quantity>" + element.elementText("realrecvqty") + "</quantity>"      //����
							+ "<assitantunit>" + rs.getString("csacomunitcode") + "</assitantunit>"    //U8�����ʱ���
							+ "<define32></define32>"    //�װ���
							+ "<irate>" + rs.getString("ichangrate") + "</irate>"    //������
							+ "<number>" + num + "</number>"    //����
							+ "<serial>" + element.elementText("lotno") + "</serial>"     //����
							+ "<makedate>" + sub2(element.elementText("proddate")) + "</makedate>"    //��������
							+ "<validdate>" + sub2(element.elementText("validdate")) + "</validdate>"    //ʧЧ����
							+ "<batchproperty6></batchproperty6>"     //ע��֤��
							+ "</entry>";
					sql = sql + "\r\n" + "1" + " " + element.elementText("lorderid") + " " + element.elementText("porderid")+ " " + element.elementText("goodsid") + " " + element.elementText("realrecvqty") + " " + num;
				}				
			}
			xml320 = xml320 + "</body>"+"</storein></ufinterface>";
			
			try {
				// д��sql
				File fff = new File(bwmssql);
				fw = new FileWriter(fff, true);
				PrintWriter pw = new PrintWriter(fw);
				pw.println(sql);
				pw.flush();
				fw.flush();
				pw.close();
				fw.close();  
			} catch (IOException e) {
				e.printStackTrace();
                auto.area.append(e + ddate()+"\r\n");
				writelog(e + ddate());
			}
			
			String fd = ddate();
			String filename = fd + "crt320.xml";
			String filepath = "E:\\BWMS\\storein\\" + filename;
			File writename = new File(filepath); // ���·�������û����Ҫ����һ���µ�output��txt�ļ�  
	        try {
	        	writename.delete();
				writename.createNewFile();
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writename),"UTF-8"));  
		        out.write(xml320); // \r\n��Ϊ����  
		        out.flush(); // �ѻ���������ѹ���ļ�  
		        out.close(); // ���ǵùر��ļ�  
			} catch (IOException e) {
				// TODO �Զ����ɵ� catch ��
				e.printStackTrace();
				auto.area.append(e + ddate()+"\r\n");
				writelog(e + ddate());
			} // �������ļ�
	        
			try {
				//�ϴ�
				testUpload(filepath,filename,"BWMS//storein");
				testUpload(bwmssql,"bwmssql.txt","");
			} catch (Exception e) {
				auto.area.append(e + ddate()+"\r\n");
				writelog(e + ddate());
			}
			
			for (Iterator iter = element_.elementIterator("e"); iter.hasNext();)   //���seqid ����������Ϣ
			{
				Element element = (Element) iter.next();			
				try {
					// ����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
					File ff = new File(seqid);
					fw = new FileWriter(ff, true);
					PrintWriter pw = new PrintWriter(fw);
					pw.println(element.elementText("seqid"));
					pw.flush();
					fw.flush();
					pw.close();
					fw.close();  
				} catch (IOException e) {
					e.printStackTrace();
					auto.area.append(e + ddate()+"\r\n");
					writelog(e + ddate());
				}
			}			
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
			auto.area.append(e + ddate()+"\r\n");
			writelog(e + ddate());
		}   
	}
	
	public static void stock(String fullpath)    //ת�����
	{
		File f = new File(fullpath);	
		Document doc;
		SAXReader reader = new SAXReader();  
		ArrayList<String[]> arrayList = new ArrayList();
		int i = 1 ;
		String[] st = {"��������","�������","�������","��������","����","��������","ʧЧ����","�������","ע��֤��","��Ӧ��","seqid","goodsownerid","goodsstatus","goodsstatusid","lotid","packsize","stockdate"};
		arrayList.add(st);
		try 
		{
			doc = reader.read(f);
			Element root = doc.getRootElement();
			Iterator iter_ = root.elementIterator("doc");
			Element element_ = (Element) iter_.next();
			for (Iterator iter = element_.elementIterator("e"); iter.hasNext();) 
			{
				Element element = (Element) iter.next();
				String[] a = {element.elementText("goodsownername"),element.elementText("goodsownid"),
						element.elementText("goodsqty"),element.elementText("busiqty"),
						element.elementText("lotno"),element.elementText("proddate"),
						element.elementText("validdate"),element.elementText("killlotno"),
						element.elementText("registdocno"),element.elementText("factname"),
						element.elementText("seqid"),element.elementText("goodsownerid"),
						element.elementText("goodsstatus"),element.elementText("goodsstatusid"),
						element.elementText("lotid"),element.elementText("packsize"),
						element.elementText("stockdate")};
				arrayList.add(a);
				i++;
			}
			String[][] data = (String [][])arrayList.toArray(new String[0][0]);
			ExcelOperater we = new ExcelOperater("e:/BWMS/stock.xls"); 
			we.writeEx(i, data);
			writelog("����ȡ�ɹ�" + ddate());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			writelog("����ȡʧ��" + e + ddate());
		}
	}
	
	protected void bt1() {
		String msg = manupo(text2.getText()); 
		if (msg.equals("")) 
		{
			JOptionPane.showInternalMessageDialog(bt1, "�ϴ�ʧ��", "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
		} else if (!msg.equals("1"))
		{
			JOptionPane.showInternalMessageDialog(bt1, "�ϴ����", "��Ϣ",JOptionPane.INFORMATION_MESSAGE);			
		}
	}
	
	protected void bt2() {
		/*JOptionPane.showInternalMessageDialog(bt2,text4.getText(),"��Ϣ", JOptionPane.INFORMATION_MESSAGE); 	*/	
		String msg = manuso(text4.getText()); 	
		if (msg.equals("")) 
		{
			JOptionPane.showInternalMessageDialog(bt2, "�ϴ�ʧ��", "��Ϣ",JOptionPane.INFORMATION_MESSAGE);
		} else if (!msg.equals("1"))
		{
			JOptionPane.showInternalMessageDialog(bt2, "�ϴ����", "��Ϣ",JOptionPane.INFORMATION_MESSAGE);			
		}
	}
	protected void bt_down()
	{
		new manu("download");
	}
	protected void bt_base()
	{
		new basedata("base");
	}
	
	public static String sub2(String str)   //ȥ���ַ��������λ��ʱ���ʽ���Ϸ���
	{
		str=str.substring(0, str.length()-2);
		return str;
	}
	
	public static void asd(ResultSet rs)
	{
		FileWriter fw = null;
		try {
			while (rs.next())
			{
				if (tempread().indexOf(rs.getString("csocode")) > -1)
				{
					continue;
				}			
				
				File f = new File(tempcode);
				fw = new FileWriter(f, true);  
				PrintWriter pw = new PrintWriter(fw);
				pw.println(rs.getString("csocode"));
				pw.flush();
				fw.flush();
				pw.close();
				fw.close();              
			}
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
	}
	
	public static void sql()
	{
		String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
		String url = "jdbc:sqlserver://192.168.1.242:1433;databaseName=UFDATA_008_2015;user=sa;password=Jsml2013";
		
	    Connection con = null;    
	    Statement stmt = null;    
	    ResultSet rs = null;    
		try
		{
			Class.forName(driverName);
			con = DriverManager.getConnection(url);
			/*System.out.println("end.");*/ 
			
			// Create and execute an SQL statement that returns some data.    
            String SQL = "SELECT TOP 22 * FROM SO_SOmain where cDefine4='2017-07-25 00:00:000'";    
            stmt = con.createStatement();    
            rs = stmt.executeQuery(SQL);    
          
            
            FileWriter fw = null;
            while (rs.next())
            {
				if (tempread().indexOf(rs.getString("csocode")) > -1)
            	{
            		continue;
            	}
            	 try 
            	 {
                  	//����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
                      File f=new File(tempcode);
                      fw = new FileWriter(f, true);     
                 }                  
                 catch (IOException e) 
                 {
                     e.printStackTrace();
                 }                
                 PrintWriter pw = new PrintWriter(fw);
                 pw.println(rs.getString("csocode"));
                 pw.flush();                 
                 fw.flush();
                 pw.close();
                 fw.close();
                 
           
            }
            
            /*System.out.println(rs.getString("DLID") + " " + rs.getString("cDLcode"));*/
    
            // Iterate through the data in the result set and display it.    
           /* while (rs.next()) {    
                System.out.println(rs.getString(1) + " " + rs.getString(4) + " " + rs.getString(6));    
            } */  
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void writesql(String code)    /*д��tempcode�ļ�*/
	{
		dt();
		FileWriter fw = null;
		if (tempread().indexOf(code) > -1) {
		}
		else
		{
			try {
				// ����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
				File f = new File(tempcode);
				fw = new FileWriter(f, true);
				PrintWriter pw = new PrintWriter(fw);
				pw.println(code);
				pw.flush();
				fw.flush();
				pw.close();
				fw.close();  
			} catch (IOException e) {
				e.printStackTrace();
			}
		}				  
	}
	
	public static void writelog(String str)   /*д��log*/
	{
		FileWriter fw = null;

		try {
			// ����ļ����ڣ���׷�����ݣ�����ļ������ڣ��򴴽��ļ�
			File f = new File(log);
			fw = new FileWriter(f, true);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(str);
			pw.flush();
			fw.flush();
			pw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
	}

	public static void testUpload(String filepath,String filename,String type)   //FTP�ϴ�  �����ϴ��ļ�·�����ļ�����FTP·������
	{ 
        FTPClient ftpClient = new FTPClient(); 
        FileInputStream fis = null; 
        try { 
            ftpClient.connect("220.248.69.70",21); 
            ftpClient.login("EAI", "zxadmin"); 

            File srcFile = new File(filepath); 
            fis = new FileInputStream(srcFile); 
            //�����ϴ�Ŀ¼ 
            ftpClient.changeWorkingDirectory("//" + type); 
            ftpClient.setBufferSize(1024); 
            ftpClient.setControlEncoding("GBK"); 
            //�����ļ����ͣ������ƣ� 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.storeFile(filename, fis); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP�ͻ��˳���", e); 
        } finally { 
            IOUtils.closeQuietly(fis); 
            try { 
                ftpClient.disconnect(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
                throw new RuntimeException("�ر�FTP���ӷ����쳣��", e); 
            } 
        } 
    }
	
    public static void testDownload(String path,String name) 
	  { 
	        FTPClient ftpClient = new FTPClient(); 
	        FileOutputStream fos = null; 

	        try { 
	        	ftpClient.connect("220.248.69.70",21); 
	            ftpClient.login("EAI", "zxadmin"); 

	            String remoteFileName = "//" + path + name; 
	            fos = new FileOutputStream("e://BWMS//" + name); 

	            ftpClient.setBufferSize(1024); 
	            //�����ļ����ͣ������ƣ� 
	            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
	            ftpClient.retrieveFile(remoteFileName, fos); 
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	            throw new RuntimeException("FTP�ͻ��˳���", e); 
	        } finally { 
	            IOUtils.closeQuietly(fos); 
	            try { 
	                ftpClient.disconnect(); 
	            } catch (IOException e) { 
	                e.printStackTrace(); 
	                throw new RuntimeException("�ر�FTP���ӷ����쳣��", e); 
	            } 
	        } 
	    } 
	
    public static void existfile(String filepath)   //�ж��ļ��У������򴴽�
    {
    	File file = new File(filepath);
    	if(!file.exists())
    	{
			try {
				file.mkdir();
				writelog(filepath + "�������");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else
    	{
    		writelog(filepath + "�Ѵ���");
    	}
    }
    
    public static void crtfile()   //�����ļ���
    {
    	existfile("E:\\BWMS\\101");
    	existfile("E:\\BWMS\\102");
    	existfile("E:\\BWMS\\301");
    	existfile("E:\\BWMS\\302");
    	existfile("E:\\BWMS\\320");
    	existfile("E:\\BWMS\\340");
    	existfile("E:\\BWMS\\400");
    	existfile("E:\\BWMS\\910");
    	existfile("E:\\BWMS\\confirm\\320");
    	existfile("E:\\BWMS\\confirm\\340");
    	existfile("E:\\BWMS\\confirm\\400");
    	existfile("E:\\BWMS\\confirm\\910");
    	existfile("E:\\BWMS\\log");
    	existfile("E:\\BWMS\\other");
    	existfile("E:\\BWMS\\result\\101");
    	existfile("E:\\BWMS\\result\\102");
    	existfile("E:\\BWMS\\result\\301");
    	existfile("E:\\BWMS\\result\\302");
    	existfile("E:\\BWMS\\result\\320");
    	existfile("E:\\BWMS\\result\\340");
    	existfile("E:\\BWMS\\result\\400");
    	existfile("E:\\BWMS\\result\\910");
    	existfile("E:\\BWMS\\result\\confirm");
    	existfile("E:\\BWMS\\result\\getlist");
    	existfile("E:\\BWMS\\storein");
    	existfile("E:\\BWMS\\storeout");
    }
    
    public static void empty(String file)   //���ָ��TXT�ļ�
    {
    	try {
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
