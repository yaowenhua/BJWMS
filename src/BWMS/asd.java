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
        text1 = new TextField("输入采购订单号：", 12);  
        text1.setEditable(false);  
        text2 = new TextField(20);  
        text3 = new TextField("输入发货单号：", 12);  
        text3.setEditable(false);  
        text4 = new TextField(20); 
        bt1 = new JButton("上传");
        bt2 = new JButton("上传");
        bt_down = new JButton("所需下载数据查询");
        bt_base = new JButton("基础数据");
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
        validate();//确保组件具有有效的布局  
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
	
	public static String core(String methodName,String xml,String type)/*数据上传函数（方法名，上传数据，单据类型）*/
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
			// 将创建的OMElement对象放置到Header中
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
			System.out.println("验证失败");
			e.printStackTrace();
		}
		return "";
	}

	public static String asxml(String path)      /*根据目录读取xml文件，返回String*/
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
	
	public static String ddate()               /*获取时间*/
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
			                    "<tradegoodspack>盒</tradegoodspack>"+
			                    "<memo>测试1</memo>"+
			                    "<arrivedate></arrivedate>"+
			                    "<checkdate></checkdate>"+
			                    "<srcdtlno>071901</srcdtlno>"+
			                    "<lotflag>1</lotflag>"+
			                    "<batchflag>1</batchflag>"+
			                    "<gtradepack>盒</gtradepack>"+
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

	public static String crtxml101()           /*doc方式创建101（货品基础数据）*/
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
        Elementdocegoodsname.setText("穿刺鞘和交换扩张器");
        Elementdocegoodsengname.setText("");
        Elementdocegoodsformalname.setText("");
        Elementdocegoodstype.setText("多功能短弯型620mmPreface穿刺鞘，含675mm扩张器和1500mm导丝");
        Elementdoceprodarea.setText("美国");
        Elementdocefactname.setText("强生（上海）医疗器材有限公司");
        Elementdocetradepack.setText("盒");
        Elementdoceoutpack.setText("盒");
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
        Elementdoceregistdocno.setText("国食药监械（进）字2013第3774056号");
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return asxml(path);
	}
	
	public static String crtxml102()           /*doc方式创建102（供应商&客户基础数据）*/
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
        Elementdocecompanyname.setText("上海海鹏医疗器械有限公司");
        Elementdocegopinyin.setText("");
        Elementdoceopcode.setText("135");
        Elementdocecompanyshortname.setText("上海海鹏医疗器械有限公司");
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
        Elementdocedtleinceptaddr.setText("上海市徐汇区肇嘉浜路201号303室");
        Elementdocedtleconnector.setText("杜雅芳 ");
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
      			// TODO 自动生成的 catch 块
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
	
	
	public static String crtxml301_1()         /*String方式创建301（采购订单）*/
	{
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = sdf.format(d);
        
		String xml301="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<result class=\"object\">"+
				"<datatype>301</datatype>"+            /*单据类型*/
				"<totalProperty>1</totalProperty>"+    /*单据数量*/
				"<doc class=\"array\">"+
					"<e class=\"object\">"+
					"<porderno>dingdan003</porderno>"+    /*订单附属号*/
		"<credate>" + datetime + "</credate>"+           /*制单日期*/
		"<arrivedate>" + datetime + "</arrivedate>"+
		"<medicineclass>1</medicineclass>"+          
		"<importflag>1</importflag>"+
		"<operationtype>1</operationtype>"+             /*业务类型 1：进货；7：销退；22：报溢*/
		"<dtllines>1</dtllines>"+  
		"<memo>beizhu03</memo>"+                       /*备注*/
		"<srcno>20170726003</srcno>"+                  /*订单号*/
		"<exporderid>003</exporderid>"+              
		"<billfrom>3</billfrom>"+
		"<usestatus>2</usestatus>"+
		"<mulrecflag>1</mulrecflag>"+
		"<reccount>1</reccount>"+
		"<inmode>0</inmode>"+
		"<invalidate>" + datetime + "</invalidate>"+
		"<sabackdate></sabackdate>"+                      /*退货日期*/
		"<sbreasonid></sbreasonid>"+                       /*退货原因ID*/
		"<deptno>1</deptno>"+
						"<deptname>1</deptname>"+
						"<gcompanyid>001</gcompanyid>"+      /*供应商ID？*/
						"<decisionflag>1</decisionflag>"+    
						"<bmsdocid>1</bmsdocid>"+
						"<nowlflag>0</nowlflag>"+
						"<transclass>1</transclass>"+
						"<ecodeimpflag>0</ecodeimpflag>"+
						"<sysmodifydate>" + datetime + "</sysmodifydate>"+     /*时间戳？*/
						"<srcporderid>005</srcporderid>"+                     /*原货主订单ID*/
						"<companystyle>2</companystyle>"+                    /*单位类型 1：客户；2：供应商；3：生产厂家*/
						"<transmode>1</transmode>"+
						"<erptransid>1</erptransid>"+
						"<goodsownerid>521</goodsownerid>"+                    /*货主ID 521*/
						"<warehid>3</warehid>"+                                /*物流中心ID 3*/
						"<relevanceno>521201707260031</relevanceno>"+         /*货主ID+订单号+业务类型*/
						"<dtl class=\"array\">"+
			"<e class=\"object\">"+
			"<porderstatus>1</porderstatus>"+
			"<batchno>0</batchno>"+                   /*批号？*/
			"<lotno></lotno>"+
			"<goodsqty>12</goodsqty>"+                /*数量*/
			"<tradegoodsqty>12</tradegoodsqty>"+
			"<tradegoodspack>盒</tradegoodspack>"+       /*基本单位*/
			"<memo>测试003</memo>"+                       /*备注*/
			"<arrivedate>" + datetime + "</arrivedate>"+
			"<checkdate>" + datetime + "</checkdate>"+
			"<srcdtlno>01</srcdtlno>"+                      /*货主原始单据号（主键？）*/
			"<lotflag>1</lotflag>"+
			"<batchflag>0</batchflag>"+
			"<gtradepack>盒</gtradepack>"+                  /*基本单位*/
			"<originqty>12</originqty>"+  
			"<price>100</price>"+                           /*单价*/
			"<retailprice>100</retailprice>"+               /*零售价*/
			"<amt>1200</amt>"+                              /*金额*/
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
			"<packsize>1</packsize>"+                     /*包装大小*/
			"<packname>箱</packname>"+                    /*包装名称*/  
			"<prtclass>1</prtclass>"+
			"<srcgoodsqty>12</srcgoodsqty>"+
			"<erpgoodsid>301803MS</erpgoodsid>"+          /*货号*/
			"<lotid>1</lotid>"+
			"<srcno>20170726003</srcno>"+                 /*表头订单号关联*/
			"<realqty>12</realqty>"+
			"<warehid>3</warehid>"+                       /*物流中心ID 3*/
			"<killlotno>mm01</killlotno>"+
			"<relevanceno>521201707260031</relevanceno>"+        /*货主ID+订单号+业务类型*/
			"<operatioontype>1</operatioontype>"+                /*业务类型 1：进货；7：销退；22：报溢*/
			"<goodsownerid>521</goodsownerid>"+                  /*货主ID 521*/
			"</e>"+
			"</dtl>"+
					"</e>"+
				"</doc>"+
			"</result>";
		
		
		
		/* 写入Txt文件 */  
        File writename = new File("E:\\BWMS\\301\\" + ddate() + "crt301.xml"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        try {
        	writename.delete();
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writename),"UTF-8"));  
	        out.write(xml301); // \r\n即为换行  
	        out.flush(); // 把缓存区内容压入文件  
	        out.close(); // 最后记得关闭文件  
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} // 创建新文件  
              
  		return xml301;

	}
	
	public static String crtxml302_1()         /*String方式创建302（销售订单）*/
	{
		Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = sdf.format(d);
        
		String xml302="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
		"<result class=\"object\">"+
		"<datatype>302</datatype>"+                 /*单据类型*/
		"<totalProperty>1</totalProperty>"+         /*单据数量*/
		"<doc class=\"array\">"+
		"<e class=\"object\">"+
		"<eorderno></eorderno>"+
		"<credate>" + datetime + "</credate>"+      /*单据日期*/
		"<preexpdate></preexpdate>"+
		"<expdate></expdate>"+
		"<prearrivedate></prearrivedate>"+
		"<arrivedate></arrivedate>"+
		"<ptransmodeid></ptransmodeid>"+
		"<transmode></transmode>"+
		"<earlydate></earlydate>"+
		"<composeinfo></composeinfo>"+
		"<operationtype>11</operationtype>"+      /*业务类型 11：销售；12：进货退出（采退）；21 （报损）移库出是18*/
		"<medicineclass></medicineclass>"+   
		"<receiveaddr>上海市徐汇区肇嘉浜路201号303室</receiveaddr>"+           /*收货地址*/
		"<receivehead></receivehead>"+          
		"<receiveman>杜雅芳</receiveman>"+       /*收货人*/
		"<partexpflag></partexpflag>"+
		"<samelotflag></samelotflag>"+
		"<urgenflag></urgenflag>"+
		"<addinvoiceflag></addinvoiceflag>"+
		"<arrivememo></arrivememo>"+
		"<exportmemo></exportmemo>"+
		"<detaillines></detaillines>"+
		"<srcexpno>2017070001</srcexpno>"+          /*销售订单号*/
		"<transid></transid>"+
		"<porderid></porderid>"+
		"<billfrom></billfrom>"+
		"<usestatus></usestatus>"+
		"<outmode></outmode>"+               
		"<invoicetype>0</invoicetype>"+       /*发票类型 0：增值税发票，1：普通发票，2:自购发票，默认为1*/
		"<transclass></transclass>"+
		"<receivephone>64669879 13816013927</receivephone>"+       /*收货人电话*/
		"<tpricetick></tpricetick>"+
		"<tpriceflag></tpriceflag>"+
		"<deptno></deptno>"+
		"<deptname></deptname>"+
		"<gcompanyid>30001</gcompanyid>"+                 /*客户ID？*/
		"<salerid></salerid>"+
		"<salername>张三</salername>"+                        /*业务员名称？*/
		"<prtclass></prtclass>"+
		"<prtgroupset></prtgroupset>"+
		"<nowlflag></nowlflag>"+
		"<sysmodifydate>" + datetime + "</sysmodifydate>"+   /*时间戳？*/
		"<goodsownerid>521</goodsownerid>"+                  /*货主ID 521*/
		"<companystype>1</companystype>"+                      /*单位类型 1：客户；2：供应商；3：生产厂家；*/
		"<warehid>3</warehid>"+                               /*物流中心ID 3*/
		"<relevanceno>521201707000111</relevanceno>"+        /*货主ID+销售订单号+业务类型*/
		"<realrecaddr></realrecaddr>"+               /*???*/ 		   
		"<dtl class=\"array\">"+
		"<e class=\"object\">"+
		"<batchno></batchno>"+
		"<lotno>1</lotno>"+                                      /*批号？*/
		"<validdate>" + datetime +"</validdate>"+            /*有效期*/
		"<goodsstatusid>1</goodsstatusid>"+                   /*货品状态 1：合格*/
		"<price>300</price>"+                                 /*单价*/
		"<goodsqty>2</goodsqty>"+                             /*数量*/
		"<tradegoodsqty></tradegoodsqty>"+
		"<tradegoodspack>盒</tradegoodspack>"+                /*基本单位*/
		"<addmedcheckflag></addmedcheckflag>"+
		"<srcexpdtlno>0001</srcexpdtlno>"+                /*销售订单细单号*/
		"<usestatus></usestatus>"+
		"<gtradepack></gtradepack>"+
		"<dtlmemo></dtlmemo>"+
		"<retailprice>300</retailprice>"+                /*零售价*/
		"<amt>600</amt>"+                               /*金额*/
		"<expdtlmemo></expdtlmemo>"+
		"<otcflag></otcflag>"+
		"<trademark></trademark>"+
		"<approvedocno></approvedocno>"+
		"<proddate></proddate>"+
		"<tpricetick></tpricetick>"+
		"<tpriceflag></tpriceflag>"+
		"<packsize>2</packsize>"+                        /*包装大小*/
		"<invno></invno>"+
		"<packname>盒</packname>"+                       /*包装名称*/
		"<sorttype></sorttype>"+
		"<lotlimit></lotlimit>"+
		"<periodlimit></periodlimit>"+
		"<periodlimitunit></periodlimitunit>"+
		"<erpgoodsid>301803MS</erpgoodsid>"+            /*存货编码*/
		"<srcexpno>2017070001</srcexpno>"+              /*包装名称*/
		"<warehid>3</warehid>"+                         /*物流中心ID 3*/
		"<killlotno></killlotno>"+           
		"<relevanceno>521201707000111</relevanceno>"+    /*货主ID+销售订单号+业务类型*/
		"<goodsownerid>521</goodsownerid>"+              /*货主ID 521*/	
		"<realqty></realqty>"+ 
		"</e>"+
		"</dtl>"+
		"</e>"+
		"</doc>"+
		"</result>";

		
		
		
		/* 写入Txt文件 */  
        File writename = new File("E:\\BWMS\\302\\" + ddate() + "crt302.xml"); // 相对路径，如果没有则要建立一个新的output。txt文件  
        try {
        	writename.delete();
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writename),"UTF-8"));  
	        out.write(xml302); // \r\n即为换行  
	        out.flush(); // 把缓存区内容压入文件  
	        out.close(); // 最后记得关闭文件  
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} // 创建新文件  
              
  		return xml302;
	}
	
	public static String get320()              /*获取数据320（入库单）*/
	{
		String xml320="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
		"<result class=\"object\">" +
		"<datatype>320</datatype>" +
		"<start>0</start>" +
		"<limit>1000</limit>" +
		"</result>";
		return xml320;
	}
 
	public static String get340()              /*获取数据340（出库单）*/
	{
		String xml340="<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
				"<result class=\"object\">" +
				"<datatype>340</datatype>" +
				"<start>0</start>" +
				"<limit>1000</limit>" +
				"</result>";
				return xml340;
	}

	public static String get910()     /*获取数据910（库存数据）*/
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
	
    public static void savexml(String xml,String path)     /*将字符串保存为XML格式，保存路径*/
	{
		 File writename = new File(path); // 相对路径，如果没有则要建立一个新的output。txt文件  
	        try {
	        	writename.delete();
				writename.createNewFile();
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writename),"UTF-8"));  
		        out.write(xml); // \r\n即为换行  
		        out.flush(); // 把缓存区内容压入文件  
		        out.close(); // 最后记得关闭文件  
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			} // 创建新文件  
	}
	
	public static String getLogInfo()                /*doc方式创建log（日志）*/
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
	  			// TODO 自动生成的 catch 块
	  			e.printStackTrace();
	  		}
	  		return asxml(path);
	}
	
	public static String getseqid()           /*获得seqid,返回字符串*/
	{
		try {
			File file=new File(seqid);
			if(file.isFile() && file.exists())
			{ //判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file),"UTF-8");//考虑到编码格式
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
				return"找不到指定的文件";
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
			return"读取文件内容出错";			
		}
	}
	
	public static String su_confirm(String type)       /*WMS反馈数据确认（成功），所需的参数数据*/
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
	
	public static String fa_confirm(String type)       /*WMS反馈数据确认（失败），所需的参数数据*/
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
	
	public static String tempread()     /*读取tempcode文件*/
	{
		try {
			String encoding="UTF-8";
			File file=new File(tempcode);
			if(file.isFile() && file.exists())
			{ //判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file),encoding);//考虑到编码格式
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
				return"找不到指定的文件";
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
			return"读取文件内容出错";			
		}
	}
	
	public static void tempwrite(String code)    /*写入tempcode文件*/
	{
		dt();
		FileWriter fw = null;
		if (tempread().indexOf(code) > -1) {
		}
		else
		{
			try {
				// 如果文件存在，则追加内容；如果文件不存在，则创建文件
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
	
	public static void dt()      /*处理tempcode文件*/
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
	
	public static String autoInvent()    /*自动读取数据库，获取存货档案*/
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
            writelog("货品资料批量上传：" + e + ddate());
            return "";
        }
		
		try {
			while (rs.next()) {
				int cclass = 2;
				if (rs.getString("ClassName").indexOf("一") > -1) 
				{
					cclass = 0;
				} else if (rs.getString("ClassName").indexOf("二") > -1) 
				{
					cclass = 1;
				}					
				xml101 = xml101
						+ "<e class=\"object\">"
						+ "<goodsownerid>521</goodsownerid>"      /*货主ID 521*/
						+ "<goodsownid>"+ rs.getString("cInvcode") +"</goodsownid>"    /*存货编码*/
						+ "<barcode></barcode>"
						+ "<goodsno></goodsno>"
						+ "<gopcode>135</gopcode>"                /*操作码？*/
						+ "<gopinyin>"+ rs.getString("cInvcode") +"</gopinyin>"      /*拼音？*/
						+ "<poisondrug>"+ cclass +"</poisondrug>"                    /*存货分类*/
						+ "<goodsname>"+ rs.getString("cInvName") +"</goodsname>"     /*存货名称*/
						+ "<goodsengname>"+ rs.getString("cInvcode") +"</goodsengname>"  /*存货英文名？*/
						+ "<goodstype>"+ rs.getString("cInvStd") +"</goodstype>"        /*规格*/
						+ "<goodsformalname></goodsformalname>"
						/*+ "<factname>"+ (rs.getString("cEnterprise")==null?"1":rs.getString("cEnterprise")) +"</factname>" */ /*生产厂家*/
						+ "<factname>强生（上海）医疗器材有限公司</factname>"
						+ "<tradepack>盒</tradepack>"         /*交易单位*/
						+ "<outpack>盒</outpack>"           /*出货单位*/
						+ "<addmedcheckflag>1</addmedcheckflag>"
						+ "<pasteflag>0</pasteflag>"
						+ "<wholesaleprice></wholesaleprice>"
						+ "<resaleprice></resaleprice>"
						+ "<purchasetax></purchasetax>"
						+ "<saletax></saletax>"
						+ "<lotflag>1</lotflag>"
						+ "<batchflag>0</batchflag>"
						+ "<memo></memo>"             /*备注*/
						+ "<drugform></drugform>"
						+ "<status>1</status>"
						+ "<otcflag></otcflag>"
						+ "<trademark></trademark>"
						+ "<storagecondition>1</storagecondition>"
						+ "<approvedocno></approvedocno>"
						+ "<packsize>"+ (int)Float.parseFloat(rs.getString("iChangRate")) +"</packsize>"  /*包装大小（用作换算率？）*/
						+ "<dbcheck></dbcheck>"
						+ "<periodunit></periodunit>"
						+ "<validperiod></validperiod>"
						+ "<varietyname></varietyname>"
						+ "<supplyername></supplyername>"
						+ "<registdocno>"+ rs.getString("cInvDefine7") +"</registdocno>"   /*注册证号*/
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
						+ "<prodarea>"+ (rs.getString("cProduceAddress")==null?"1":rs.getString("cProduceAddress")) +"</prodarea>"  /*产地*/
						+ "<goodsstatus>1</goodsstatus>"       /*货品状态*/
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
						+ "<templow></templow>"     /*温度下限*/
						+ "<temphigh></temphigh>"     /*温度上限*/
						+ "<packscodeflag></packscodeflag>"
						+ "<lotnocannullflag></lotnocannullflag>"
						+ "<warehid>3</warehid>"             /*物流中心ID 3*/
						+ "<registstartdate>"+ (rs.getString("dEffDate")==null?"":rs.getString("dEffDate")) +"</registstartdate>"     /*注册证开始时间*/
						+ "<registenddate>"+ (rs.getString("dInvalidDate")==null?"":rs.getString("dInvalidDate")) +"</registenddate>"         /*注册证结束时间*/
						+ "<goodsmodel>"+ rs.getString("cSAComUnitCode") +"</goodsmodel>" /*增加型号字段（换算率编码）*/
						+ "<scopeno></scopeno>" + "</e>";
				
				count++;
			}
				xml101 = xml101 + "</doc>" + "<totalProperty>" + count
						+ "</totalProperty>" +  /*单据数量 */
						"</result>";
			
				
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			xml101 = "";	
			writelog("货品资料批量上传：" + e + ddate());
			return "";
		}
		try {
			con.close();
			con=null;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			writelog("货品资料批量上传：" + e + ddate());
	    	return "";
		}
		File writename = new File("E:\\BWMS\\101\\" + ddate() + "auto101.xml"); // 相对路径，如果没有则要建立一个新的output。txt文件
		try {
			writename.delete();
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(writename), "UTF-8"));
			out.write(xml101); // \r\n即为换行
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			writelog("货品资料批量上传：" + e + ddate());
			return "";
		} // 创建新文件

		return xml101;
	}
	
	public static String autocustom()    /*自动读取数据库，获取客户档案*/
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
            writelog("客户资料批量上传：" + e + ddate());
            return "";
        }
		
		try {
			while (rs.next())
			{
				xml102 = xml102 + "<e class=\"object\">"
						+ "<goodsownerid>521</goodsownerid>"     /*货主ID 521*/
						+ "<gcompanyid>" + rs.getString("cCusCode") + "</gcompanyid>"   /*客户编码*/
						+ "<companystype>1</companystype>"     /*单位类型 1：客户；2：供应商；3：生产厂家*/
						+ "<companyname>" + rs.getString("cCusName") + "</companyname>"   /*客户名称*/
						+ "<gopinyin></gopinyin>"
						+ "<opcode>135</opcode>"        /*操作码？*/
						+ "<companyshortname>" + rs.getString("cCusAbbName") + "</companyshortname>"  /*客户简称*/
						+ "<bank>" + rs.getString("cCusBank") + "</bank>"    /*开户银行*/
						+ "<bankno>" + rs.getString("cCusAccount") + "</bankno>"     /*银行账号*/
						+ "<chiefofficer></chiefofficer>"
						+ "<connector>" + rs.getString("cCusPerson") + "</connector>"    /*联系人*/
						+ "<connphone>" + rs.getString("cCusPhone") + "</connphone>"    /*联系电话*/
						+ "<deliveraddr>" + rs.getString("cCusAddress") + "</deliveraddr>"   /*送货地址*/
						+ "<invoiceaddr>" + rs.getString("cCusDefine7") + "</invoiceaddr>"   /*发票地址*/
						+ "<invoicehead>" + rs.getString("cCusName") + "</invoicehead>"     /*发票名称？*/
						+ "<invoiceman>" + rs.getString("cCusDefine3") + "</invoiceman>"  /*发票人员？ 收票人？*/
						+ "<invoicephone>" + rs.getString("cCusDefine5") + "</invoicephone>"  /*发票电话？ 收票人电话？*/
						+ "<postcode></postcode>"
						+ "<specialrequire></specialrequire>"
						+ "<taxregistno>" + rs.getString("cCusRegCode") + "</taxregistno>"  /*税号*/
						+ "<telephone></telephone>"
						+ "<memo></memo>"              /*备注*/
						+ "<addmedcheckflag>1</addmedcheckflag>"
						+ "<usestatus>1</usestatus>"
						+ "<companyno>" + rs.getString("cCusCode") + "</companyno>"     /*货主原单位编码？*/
						+ "<orgno>1</orgno>"               /*组织机构代码?*/
						+ "<impflag>1</impflag>"         /*导入标志？*/
						+ "<creDate>" + datetime + "</creDate>"  
						+ "<city></city>"
						+ "<corpid></corpid>"
						+ "<goodsownerflag>0</goodsownerflag>"    /*货主标志 0：非货主，1：货主*/
						+ "<legalpersion></legalpersion>"
						+ "<unifyno></unifyno>"
						+ "<defrtanid>1</defrtanid>"     /*默认地址ID？*/
						+ "<invclass>1</invclass>"        /*发票分类？*/
						+ "<invprintplan></invprintplan>"
						+ "<invtype>1</invtype>"        /*发票类型？*/
						+ "<modedocid>1</modedocid>"    /*二维码打印方案模板ID？*/
						+ "<orderflag>1</orderflag>"    /*集合拣货标志？*/
						+ "<periodlimit>1</periodlimit>"    /*效期限制？*/
						+ "<periodlimtunit></periodlimtunit>"
						+ "<printflag>0</printflag>"       /*是否打印发票？ 0：不打印，1：打印*/
						+ "<companycredate>" + datetime + "</companycredate>"
						+ "<printplan></printplan>"
						+ "<lotlimit>3</lotlimit>"     /*1：只能一个批号，2：最多两个批号，3：不限批号个数，默认为3*/
						+ "<sysmodifydate>" + datetime + "</sysmodifydate>"
						+ "<warehid>3</warehid>"      /*物流中心ID 3*/
						+ "<dtl class=\"array\">";
				
				/*String SQL_ = "select * from PO_POdetails where POID='" + rs.getString("POID") + "'";
				stmt = con.createStatement();    
	            rs_ = stmt.executeQuery(SQL_);
				
	            while (rs_.next())
	            {*/
				xml102 = xml102 + "<e class=\"object\">"
						+ "<goodsownerid>521</goodsownerid>"    /*货主ID 521*/
						+ "<gocompanyid>" + rs.getString("cCusCode") + "</gocompanyid>"      /*客户编码*/
						+ "<transid>1</transid>"     /*默认地址ID？*/
						+ "<inceptaddr>" + rs.getString("cCusAddress") + "</inceptaddr>"   /*运输地址*/
						+ "<connector>" + rs.getString("cCusPerson") + "</connector>"     /*联系人*/
						+ "<connphone>" + rs.getString("cCusPhone") + "</connphone>"      /*联系电话*/
						+ "<telefax></telefax>"
						+ "<postcode></postcode>"
						+ "<memo></memo>"           /*备注*/
						+ "<usestatus>1</usestatus>"   /*使用状态 1:正常，0：作废，默认为1*/
						+ "<tranposname></tranposname>"
						+ "<impflag>1</impflag>"     /*导入标注？ 默认为1*/
						+ "<creDate>" + datetime + "</creDate>"
						+ "<city></city>"
						+ "<equtransid>1</equtransid>"    /*等同地点id?*/
						+ "<sysmodifydate>" + datetime + "</sysmodifydate>"
						+ "<companystype>1</companystype>"      /*单位类型 1：客户；2：供应商；3：生产厂家*/
						+ "<warehid>3</warehid>"        /*物流中心ID 3*/
						+ "</e>";
	            /*}*/		
	            xml102=xml102 + "</dtl>";
			
			 count++;		
			 xml102=xml102 + "</e>";
					
			}
			xml102=xml102+"</doc>"+
					"<totalProperty>" + count + "</totalProperty>"+  /*单据数量*/
					"</result>";
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			xml102 = "";
			writelog("客户资料批量上传：" + e + ddate());
			return "";			
		}
		try {
			con.close();
			con=null;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			writelog("客户资料批量上传：" + e + ddate());
			return "";
		}
		File writename = new File("E:\\BWMS\\102\\" + ddate() + "auto102.xml"); // 相对路径，如果没有则要建立一个新的output。txt文件
		try {
			writename.delete();
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(writename), "UTF-8"));
			out.write(xml102); // \r\n即为换行
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			writelog("客户资料批量上传：" + e + ddate());
			return "";
		} // 创建新文件

		return xml102;
	}
	
	public static String autopo()   /*自动读取数据库，获取采购订单列表*/
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
	    String ccode ="";    //记录单号
	    
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
				            "<porderno>" + rs.getString("POID") + "</porderno>"+    /*订单附属号*/
							"<credate>" + datetime + "</credate>"+           /*制单日期*/
							"<arrivedate></arrivedate>"+
							"<medicineclass>1</medicineclass>"+          
							"<importflag>1</importflag>"+
							"<operationtype>1</operationtype>"+             /*业务类型 1：进货；7：销退；22：报溢*/
							"<dtllines></dtllines>"+  
							"<memo>" + (rs.getString("cMemo")==null?"":rs.getString("cMemo"))+ "</memo>"+                /*备注*/
							"<srcno>" + rs.getString("cPOID") + "</srcno>"+                  /*订单号*/
							"<exporderid></exporderid>"+              
							"<billfrom>3</billfrom>"+
							"<usestatus>2</usestatus>"+
							"<mulrecflag></mulrecflag>"+
							"<reccount></reccount>"+
							"<inmode>0</inmode>"+
							"<invalidate></invalidate>"+
							"<sabackdate></sabackdate>"+                      /*退货日期*/
							"<sbreasonid></sbreasonid>"+                       /*退货原因ID*/
							"<deptno></deptno>"+
							"<deptname></deptname>"+
							"<gcompanyid>" + rs.getString("cVenCode") + "</gcompanyid>"+      /*供应商ID？*/
							"<decisionflag></decisionflag>"+    
							"<bmsdocid></bmsdocid>"+
							"<nowlflag>0</nowlflag>"+
							"<transclass>1</transclass>"+
							"<ecodeimpflag></ecodeimpflag>"+
							"<sysmodifydate>" + datetime + "</sysmodifydate>"+     /*时间戳？*/
							"<srcporderid>" + rs.getString("cPTCode") + "</srcporderid>"+    /*原货主订单ID （用作订单类型） */
							"<companystyle>2</companystyle>"+                    /*单位类型 1：客户；2：供应商；3：生产厂家*/
							"<transmode></transmode>"+
							"<erptransid></erptransid>"+
							"<goodsownerid>521</goodsownerid>"+                    /*货主ID 521*/
							"<warehid>3</warehid>"+                                /*物流中心ID 3*/
							"<relevanceno>521" + rs.getString("cPOID") + "1</relevanceno>"+ /*货主ID+订单号+业务类型*/
							"<dtl class=\"array\">";         
					
					String SQL_ = "select * from PO_POdetails where POID='" + rs.getString("POID") + "'";
					stmt = con.createStatement();    
		            rs_ = stmt.executeQuery(SQL_);
					
		            while (rs_.next())
		            {
		            	xml301=xml301+"<e class=\"object\">"+
		            			"<porderstatus>1</porderstatus>"+
		            			"<batchno></batchno>"+                   
		            			"<lotno></lotno>"+                        /*批号？*/
		            			"<goodsqty>" + String.format("%.4f", Float.parseFloat(rs_.getString("iQuantity"))) + "</goodsqty>"+        /*数量*/
		            			"<tradegoodsqty></tradegoodsqty>"+
		            			"<tradegoodspack>盒</tradegoodspack>"+       /*基本单位*/
		            			"<memo>" + (rs_.getString("cbMemo")==null?"":rs_.getString("cbMemo")) + "</memo>"+  /*备注*/
		            			"<arrivedate></arrivedate>"+
		            			"<checkdate></checkdate>"+
		            			"<srcdtlno>" + rs_.getString("ID") + "</srcdtlno>"+         /*货主原始单据号（主键？）*/
		            			"<lotflag>1</lotflag>"+
		            			"<batchflag>0</batchflag>"+
		            			"<gtradepack>盒</gtradepack>"+                  /*基本单位*/
		            			"<originqty></originqty>"+  
		            			"<price></price>"+                           /*单价*/
		            			"<retailprice></retailprice>"+               /*零售价*/
		            			"<amt></amt>"+                              /*金额*/
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
		            			"<packsize>" + (int)(Float.parseFloat(rs_.getString("iQuantity"))/Float.parseFloat(rs_.getString("iNum"))) + "</packsize>"+   /*包装大小*/
		            			"<packname>箱</packname>"+                    /*包装名称*/  
		            			"<prtclass></prtclass>"+
		            			"<srcgoodsqty></srcgoodsqty>"+
		            			"<erpgoodsid>" + rs_.getString("cInvCode") + "</erpgoodsid>"+       /*货号*/
		            			"<lotid></lotid>"+
		            			"<srcno>" + rs.getString("cPOID") + "</srcno>"+                 /*表头订单号关联*/
		            			"<realqty></realqty>"+
		            			"<warehid>3</warehid>"+                       /*物流中心ID 3*/
		            			"<killlotno></killlotno>"+
		            			"<relevanceno>521" + rs.getString("cPOID") + "1</relevanceno>"+        /*货主ID+订单号+业务类型*/
		            			"<operatioontype>1</operatioontype>"+                /*业务类型 1：进货；7：销退；22：报溢*/
		            			"<goodsownerid>521</goodsownerid>"+                  /*货主ID 521*/
		            			"</e>";
		            }			
		            xml301=xml301 + "</dtl>";
				}
				/*count++;	*/
				count = 1;
				xml301=xml301 + "</e>";
				xml301=xml301 + "</doc>" +
							"<totalProperty>" + count + "</totalProperty>"+  /*单据数量*/
							"</result>";
				/*core("impWmsData",xml301,"301");*/
				
				tempwrite(rs.getString("cPOID"));   //将单据号写入temp
				File writename = new File("E:\\BWMS\\301\\" + rs.getString("cPOID") + " " + ddate()
						+ "auto301.xml"); // 相对路径，如果没有则要建立一个新的output。txt文件
				try {
					writename.delete();
					writename.createNewFile();
					BufferedWriter out = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(
									writename), "UTF-8"));
					out.write(xml301); // \r\n即为换行
					out.flush(); // 把缓存区内容压入文件
					out.close(); // 最后记得关闭文件
					
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();		
					auto.area.append(e + ddate()+"\r\n");
					writelog(e + ddate());
				}
				auto.area.append(ccode + "上传成功 " + ddate()+"\r\n");
				writelog(ccode + "上传成功 " + ddate());
			}			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			xml301 = "";	
			auto.area.append(ccode + "上传失败 " + ddate()+"\r\n");
			writelog(ccode + "上传失败 " + ddate());
		}
		try {
			con.close();
			con=null;
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
			auto.area.append(e1 + ddate()+"\r\n");
			writelog(e1 + ddate());
		}	 
		return xml301;
	}
	
	public static String autoso()   /*自动读取数据库，获取销售*/
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
	    String ccode = "";   //记录单号
	    	    
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
								"<eorderno>" + rs.getString("cSOCode") + "</eorderno>"+   /*销售订单*/
								"<credate>" + datetime + "</credate>"+      /*单据日期*/
								"<preexpdate></preexpdate>"+
								"<expdate></expdate>"+
								"<prearrivedate></prearrivedate>"+
								"<arrivedate></arrivedate>"+
								"<ptransmodeid></ptransmodeid>"+
								"<transmode></transmode>"+
								"<earlydate></earlydate>"+
								"<composeinfo></composeinfo>"+
								"<operationtype>11</operationtype>"+      /*业务类型 11：销售；12：进货退出（采退）；21 （报损）移库出是18*/
								"<medicineclass></medicineclass>"+   
								"<receiveaddr>" + rs.getString("cDefine11") + "</receiveaddr>"+     /*收货地址*/
								"<receivehead></receivehead>"+          
								"<receiveman>" + rs.getString("cDefine12") + "</receiveman>"+       /*收货人*/
								"<partexpflag></partexpflag>"+
								"<samelotflag></samelotflag>"+
								"<urgenflag></urgenflag>"+
								"<addinvoiceflag></addinvoiceflag>"+
								"<arrivememo></arrivememo>"+
								"<exportmemo>" + (rs.getString("cMemo")==null?"":rs.getString("cMemo"))+ "</exportmemo>"+   //出货备注
								"<detaillines></detaillines>"+
								"<srcexpno>" + rs.getString("cDLCode") + "</srcexpno>"+          /*发货单号*/
								"<transid></transid>"+
								"<porderid></porderid>"+
								"<billfrom></billfrom>"+
								"<usestatus></usestatus>"+
								"<outmode></outmode>"+               
								"<invoicetype>0</invoicetype>"+       /*发票类型 0：增值税发票，1：普通发票，2:自购发票，默认为1*/
								"<transclass></transclass>"+
								"<receivephone>" + rs.getString("cDefine10") + "</receivephone>"+       /*收货人电话*/
								"<tpricetick></tpricetick>"+
								"<tpriceflag></tpriceflag>"+
								"<deptno></deptno>"+
								"<deptname></deptname>"+
								"<gcompanyid>" + rs.getString("cCusCode") + "</gcompanyid>"+      /*客户ID？*/
								"<salerid></salerid>"+
								"<salername>" + rs.getString("cMaker") + "</salername>"+       /*业务员名称？*/
								"<prtclass></prtclass>"+
								"<prtgroupset></prtgroupset>"+
								"<nowlflag></nowlflag>"+
								"<sysmodifydate>" + datetime + "</sysmodifydate>"+   /*时间戳？*/
								"<goodsownerid>521</goodsownerid>"+                  /*货主ID 521*/
								"<companystype>1</companystype>"+                      /*单位类型 1：客户；2：供应商；3：生产厂家；*/
								"<warehid>3</warehid>"+                               /*物流中心ID 3*/
								"<relevanceno>521" + rs.getString("cDLCode") + "11</relevanceno>"+        /*货主ID+销售订单号+业务类型*/
								"<realrecaddr></realrecaddr>"+               /*???*/ 		   
								"<dtl class=\"array\">";         
						
						String SQL_ = "select * from dispatchlists where DLID='" + rs.getString("DLID") + "'";
						stmt = con.createStatement();    
			            rs_ = stmt.executeQuery(SQL_);
						
			            while (rs_.next())
			            {
			            	xml302=xml302+"<e class=\"object\">"+
			            			"<batchno></batchno>"+
			            			"<lotno>" + rs_.getString("cBatch") + "</lotno>"+     /*批号？*/                                 /*批号？*/
			            			"<validdate>" + rs_.getString("dvDate") + "</validdate>"+      /*有效期*/
			            			"<goodsstatusid>1</goodsstatusid>"+                   /*货品状态 1：合格*/
			            			"<price>" + rs_.getString("iTaxUnitPrice") + "</price>"+       /*单价*/
			            			"<goodsqty>" + rs_.getString("iQuantity") + "</goodsqty>"+     /*数量*/
			            			"<tradegoodsqty></tradegoodsqty>"+
			            			"<tradegoodspack>盒</tradegoodspack>"+    /*基本单位*/
			            			"<addmedcheckflag></addmedcheckflag>"+
			            			"<srcexpdtlno>" + rs_.getString("iDLsID") + "</srcexpdtlno>"+   /*发货单细单号*/
			            			"<usestatus></usestatus>"+
			            			"<gtradepack></gtradepack>"+
			            			"<dtlmemo></dtlmemo>"+
			            			"<retailprice>" + rs_.getString("iTaxUnitPrice") + "</retailprice>"+   /*零售价*/
			            			"<amt></amt>"+         /*金额*/
			            			"<expdtlmemo></expdtlmemo>"+
			            			"<otcflag></otcflag>"+
			            			"<trademark></trademark>"+
			            			"<approvedocno></approvedocno>"+
			            			"<proddate>" + (rs_.getString("dMDate")==null?"":rs_.getString("dMDate")) + "</proddate>"+    /*生产日期*/
			            			"<tpricetick></tpricetick>"+
			            			"<tpriceflag></tpriceflag>"+
			            			"<packsize>" + rs_.getString("iInvExchRate") + "</packsize>"+    /*包装大小?*/
			            			"<invno></invno>"+
			            			"<packname>盒</packname>"+                       /*包装名称*/
			            			"<sorttype></sorttype>"+
			            			"<lotlimit></lotlimit>"+
			            			"<periodlimit></periodlimit>"+
			            			"<periodlimitunit></periodlimitunit>"+
			            			"<erpgoodsid>" + rs_.getString("cInvCode") + "</erpgoodsid>"+     /*存货编码*/
			            			"<srcexpno>" + rs.getString("cDLCode") + "</srcexpno>"+     /*发货单号*/
			            			"<warehid>3</warehid>"+                         /*物流中心ID 3*/
			            			"<killlotno></killlotno>"+              /*灭菌批号*/
			            			"<relevanceno>521" + rs.getString("cDLCode") + "11</relevanceno>"+   /*货主ID+销售订单号+业务类型*/
			            			"<goodsownerid>521</goodsownerid>"+              /*货主ID 521*/	
			            			"<realqty></realqty>"+ 
			            			"</e>";
			            }			
			            xml302=xml302 + "</dtl>";
					
					/*count++;	*/	
					count = 1;
					xml302=xml302 + "</e>";
					xml302=xml302 + "</doc>" +
								"<totalProperty>" + count + "</totalProperty>"+  /*单据数量*/
								"</result>";
					/*core("impWmsData",xml302,"302");*/
					 
					tempwrite(rs.getString("cDLCode"));   //将单据号写入temp
					File writename = new File("E:\\BWMS\\302\\" + rs.getString("cDLCode") + " " + ddate()
							+ "auto302.xml"); // 相对路径，如果没有则要建立一个新的output。txt文件
					try {
						writename.delete();
						writename.createNewFile();
						BufferedWriter out = new BufferedWriter(
								new OutputStreamWriter(new FileOutputStream(
										writename), "UTF-8"));
						out.write(xml302); // \r\n即为换行
						out.flush(); // 把缓存区内容压入文件
						out.close(); // 最后记得关闭文件
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
						auto.area.append(e + ddate()+"\r\n");
						writelog(e + ddate());
					}
					auto.area.append(ccode + "上传成功 " + ddate()+"\r\n");
					writelog(ccode + "上传成功 " + ddate());
				}
				else if(rs.getString("bReturnFlag").equals("1"))    //销售退货
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
					            "<porderno>" + (rs.getString("cSOCode")==null?"":rs.getString("cSOCode"))+ "</porderno>"+    /*订单号*/
								"<credate>" + datetime + "</credate>"+           /*制单日期*/
								"<arrivedate></arrivedate>"+
								"<medicineclass>1</medicineclass>"+          
								"<importflag>1</importflag>"+
								"<operationtype>7</operationtype>"+             /*业务类型 1：进货；7：销退；22：报溢*/
								"<dtllines></dtllines>"+  
								"<memo>" + (rs.getString("cMemo")==null?"":rs.getString("cMemo"))+ "</memo>"+  /*备注*/
								"<srcno>" + rs.getString("cDLCode") + "</srcno>"+                  /*退货单号*/
								"<exporderid></exporderid>"+              
								"<billfrom>3</billfrom>"+
								"<usestatus>2</usestatus>"+
								"<mulrecflag></mulrecflag>"+
								"<reccount></reccount>"+
								"<inmode>0</inmode>"+
								"<invalidate></invalidate>"+
								"<sabackdate>" + rs.getString("dDate") + "</sabackdate>"+     /*退货日期*/
								"<sbreasonid></sbreasonid>"+                       /*退货原因ID*/
								"<deptno></deptno>"+
								"<deptname></deptname>"+
								"<gcompanyid>" + rs.getString("cCusCode") + "</gcompanyid>"+      /*客户ID？*/
								"<decisionflag></decisionflag>"+    
								"<bmsdocid></bmsdocid>"+
								"<nowlflag>0</nowlflag>"+
								"<transclass>1</transclass>"+
								"<ecodeimpflag></ecodeimpflag>"+
								"<sysmodifydate>" + datetime + "</sysmodifydate>"+     /*时间戳？*/
								"<srcporderid>" + rs.getString("cSTCode") + "</srcporderid>"+    /*原货主订单ID （用作销售类型） */
								"<companystyle>1</companystyle>"+                    /*单位类型 1：客户；2：供应商；3：生产厂家*/
								"<transmode></transmode>"+
								"<erptransid></erptransid>"+
								"<goodsownerid>521</goodsownerid>"+                    /*货主ID 521*/
								"<warehid>3</warehid>"+                                /*物流中心ID 3*/
								"<relevanceno>521" + rs.getString("cDLCode") + "7</relevanceno>"+ /*货主ID+订单号+业务类型*/
								"<dtl class=\"array\">";         
						
						String SQL_ = "select * from dispatchlists where DLID='" + rs.getString("DLID") + "'";
						stmt = con.createStatement();    
			            rs_ = stmt.executeQuery(SQL_);
						
			            while (rs_.next())
			            {
			            	xml301=xml301+"<e class=\"object\">"+
			            			"<porderstatus>1</porderstatus>"+
			            			"<batchno></batchno>"+                   
			            			"<lotno>" + rs_.getString("cBatch") + "</lotno>"+         /*批号？*/
			            			"<goodsqty>" + (rs_.getString("iQuantity").substring(1,(rs_.getString("iQuantity")).length())) + "</goodsqty>"+  /*数量*/
			            			"<tradegoodsqty></tradegoodsqty>"+
			            			"<tradegoodspack>盒</tradegoodspack>"+       /*基本单位*/
			            			"<memo>" + (rs_.getString("cMemo")==null?"":rs_.getString("cMemo")) + "</memo>"+  /*备注*/
			            			"<arrivedate></arrivedate>"+
			            			"<checkdate></checkdate>"+
			            			"<srcdtlno>" + rs_.getString("AutoID") + "</srcdtlno>"+         /*货主原始单据号（主键？）*/
			            			"<lotflag>1</lotflag>"+
			            			"<batchflag>0</batchflag>"+
			            			"<gtradepack>盒</gtradepack>"+                  /*基本单位*/
			            			"<originqty></originqty>"+  
			            			"<price>" + rs_.getString("iTaxUnitPrice") + "</price>"+      /*单价*/
			            			"<retailprice>" + rs_.getString("iTaxUnitPrice") + "</retailprice>"+    /*零售价*/
			            			"<amt></amt>"+               /*金额*/
			            			"<qty></qty>"+
			            			"<quadqty></quadqty>"+
			            			"<unquadqty></unquadqty>"+
			            			"<testqty></testqty>"+
			            			"<sadtlid>" + rs_.getString("iDLsID") + "</sadtlid>"+  //销退原业务单号 （发货单细单号？）
			            			"<expdtlmemo></expdtlmemo>"+
			            			"<goodsstatusid></goodsstatusid>"+
			            			"<quantitystatus></quantitystatus>"+
			            			"<validdate>" + rs_.getString("dvDate") + "</validdate>"+   //有效期
			            			"<proddate>" + (rs_.getString("dMDate")==null?"":rs_.getString("dMDate")) + "</proddate>"+   //生产日期
			            			"<approvedocno></approvedocno>"+
			            			"<firstflag></firstflag>"+
			            			"<tpricetick></tpricetick>"+
			            			"<tpriceflag></tpriceflag>"+
			            			"<packsize>" + (int)(Float.parseFloat(rs_.getString("iQuantity"))/Float.parseFloat(rs_.getString("iNum"))) + "</packsize>"+   /*包装大小*/
			            			"<packname>箱</packname>"+                    /*包装名称*/  
			            			"<prtclass></prtclass>"+
			            			"<srcgoodsqty></srcgoodsqty>"+
			            			"<erpgoodsid>" + rs_.getString("cInvCode") + "</erpgoodsid>"+       /*货号*/
			            			"<lotid></lotid>"+
			            			"<srcno>" + rs.getString("cDLCode") + "</srcno>"+          /*表头订单号关联*/
			            			"<realqty></realqty>"+
			            			"<warehid>3</warehid>"+                       /*物流中心ID 3*/
			            			"<killlotno></killlotno>"+
			            			"<relevanceno>521" + rs.getString("cDLCode") + "7</relevanceno>"+        /*货主ID+订单号+业务类型*/
			            			"<operatioontype>7</operatioontype>"+                /*业务类型 1：进货；7：销退；22：报溢*/
			            			"<goodsownerid>521</goodsownerid>"+                  /*货主ID 521*/
			            			"</e>";
			            }			
			            xml301=xml301 + "</dtl>";
					}
					/*count++;	*/
					count = 1;
					xml301=xml301 + "</e>";
					xml301=xml301 + "</doc>" +
								"<totalProperty>" + count + "</totalProperty>"+  /*单据数量*/
								"</result>";
					
					/*core("impWmsData",xml301,"301");*/
					 
					tempwrite(rs.getString("cDLCode"));   //将单据号写入temp
					File writename = new File("E:\\BWMS\\301\\" + rs.getString("cDLCode") + " " + ddate()
							+ "auto301.xml"); // 相对路径，如果没有则要建立一个新的output。txt文件
					try
					{
						writename.delete();
						writename.createNewFile();
						BufferedWriter out = new BufferedWriter(
								new OutputStreamWriter(new FileOutputStream(
										writename), "UTF-8"));
						out.write(xml301); // \r\n即为换行
						out.flush(); // 把缓存区内容压入文件
						out.close(); // 最后记得关闭文件
					}
					catch(Exception e)
					{
						e.printStackTrace();
						auto.area.append(e + ddate()+"\r\n");
						writelog(e + ddate());
					}
					auto.area.append(ccode + "上传成功 " + ddate()+"\r\n");
					writelog(ccode + "上传成功 " + ddate());
				}
		    }
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			xml302 = "";	
			xml301 = "";
			auto.area.append(ccode + "上传失败 " + ddate()+"\r\n");
			writelog(ccode + "上传失败 " + ddate());
		}				
		try {
			con.close();
			con=null;
		} catch (SQLException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
			auto.area.append(e1 + ddate()+"\r\n");
			writelog(e1 + ddate());
		}	
		return xml302;
	}

	public static String manupo(String code)  /*手动上传采购订单*/
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
				JOptionPane.showInternalMessageDialog(bt1,"该单号不存在","信息", JOptionPane.INFORMATION_MESSAGE); 	
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
					int res = JOptionPane.showConfirmDialog(null, "该订单已上传，是否继续？", "提示",
							JOptionPane.YES_NO_OPTION);
					if (res == JOptionPane.YES_OPTION) {
					} else {
						return "1";
					}
				}
					xml301=xml301+"<e class=\"object\">"+ 
				            "<porderno>" + rs.getString("POID") + "</porderno>"+    /*订单附属号*/
							"<credate>" + datetime + "</credate>"+           /*制单日期*/
							"<arrivedate></arrivedate>"+
							"<medicineclass>1</medicineclass>"+          
							"<importflag>1</importflag>"+
							"<operationtype>1</operationtype>"+             /*业务类型 1：进货；7：销退；22：报溢*/
							"<dtllines></dtllines>"+  
							"<memo>" + (rs.getString("cMemo")==null?"":rs.getString("cMemo"))+ "</memo>"+                /*备注*/
							"<srcno>" + rs.getString("cPOID") + "</srcno>"+                  /*订单号*/
							"<exporderid></exporderid>"+              
							"<billfrom>3</billfrom>"+
							"<usestatus>2</usestatus>"+
							"<mulrecflag></mulrecflag>"+
							"<reccount></reccount>"+
							"<inmode>0</inmode>"+
							"<invalidate></invalidate>"+
							"<sabackdate></sabackdate>"+                      /*退货日期*/
							"<sbreasonid></sbreasonid>"+                       /*退货原因ID*/
							"<deptno></deptno>"+
							"<deptname></deptname>"+
							"<gcompanyid>" + rs.getString("cVenCode") + "</gcompanyid>"+      /*供应商ID？*/
							"<decisionflag></decisionflag>"+    
							"<bmsdocid></bmsdocid>"+
							"<nowlflag>0</nowlflag>"+
							"<transclass>1</transclass>"+
							"<ecodeimpflag></ecodeimpflag>"+
							"<sysmodifydate>" + datetime + "</sysmodifydate>"+     /*时间戳？*/
							"<srcporderid>" + rs.getString("cPTCode") + "</srcporderid>"+    /*原货主订单ID （用作订单类型） */
							"<companystyle>2</companystyle>"+                    /*单位类型 1：客户；2：供应商；3：生产厂家*/
							"<transmode></transmode>"+
							"<erptransid></erptransid>"+
							"<goodsownerid>521</goodsownerid>"+                    /*货主ID 521*/
							"<warehid>3</warehid>"+                                /*物流中心ID 3*/
							"<relevanceno>521" + rs.getString("cPOID") + "1</relevanceno>"+ /*货主ID+订单号+业务类型*/
							"<dtl class=\"array\">";         
					
					String SQL_ = "select * from PO_POdetails where POID='" + rs.getString("POID") + "'";
					stmt = con.createStatement();    
		            rs_ = stmt.executeQuery(SQL_);
					
		            while (rs_.next())
		            {
		            	xml301=xml301+"<e class=\"object\">"+
		            			"<porderstatus>1</porderstatus>"+
		            			"<batchno></batchno>"+                   
		            			"<lotno></lotno>"+                        /*批号？*/
		            			"<goodsqty>" + String.format("%.4f", Float.parseFloat(rs_.getString("iQuantity"))) + "</goodsqty>"+        /*数量*/
		            			"<tradegoodsqty></tradegoodsqty>"+
		            			"<tradegoodspack>盒</tradegoodspack>"+       /*基本单位*/
		            			"<memo>" + (rs_.getString("cbMemo")==null?"":rs_.getString("cbMemo")) + "</memo>"+  /*备注*/
		            			"<arrivedate></arrivedate>"+
		            			"<checkdate></checkdate>"+
		            			"<srcdtlno>" + rs_.getString("ID") + "</srcdtlno>"+         /*货主原始单据号（主键？）*/
		            			"<lotflag>1</lotflag>"+
		            			"<batchflag>0</batchflag>"+
		            			"<gtradepack>盒</gtradepack>"+                  /*基本单位*/
		            			"<originqty></originqty>"+  
		            			"<price></price>"+                           /*单价*/
		            			"<retailprice></retailprice>"+               /*零售价*/
		            			"<amt></amt>"+                              /*金额*/
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
		            			"<packsize>" + (int)(Float.parseFloat(rs_.getString("iQuantity"))/Float.parseFloat(rs_.getString("iNum"))) + "</packsize>"+   /*包装大小*/
		            			"<packname>箱</packname>"+                    /*包装名称*/  
		            			"<prtclass></prtclass>"+
		            			"<srcgoodsqty></srcgoodsqty>"+
		            			"<erpgoodsid>" + rs_.getString("cInvCode") + "</erpgoodsid>"+       /*货号*/
		            			"<lotid></lotid>"+
		            			"<srcno>" + rs.getString("cPOID") + "</srcno>"+                 /*表头订单号关联*/
		            			"<realqty></realqty>"+
		            			"<warehid>3</warehid>"+                       /*物流中心ID 3*/
		            			"<killlotno></killlotno>"+
		            			"<relevanceno>521" + rs.getString("cPOID") + "1</relevanceno>"+        /*货主ID+订单号+业务类型*/
		            			"<operatioontype>1</operatioontype>"+                /*业务类型 1：进货；7：销退；22：报溢*/
		            			"<goodsownerid>521</goodsownerid>"+                  /*货主ID 521*/
		            			"</e>";
		            }			
		            xml301=xml301 + "</dtl>";
				
				count++;		
				xml301=xml301 + "</e>";
				xml301=xml301 + "</doc>" +
							"<totalProperty>" + count + "</totalProperty>"+  /*单据数量*/
							"</result>";
				core("impWmsData",xml301,"301");
				writelog("采购订单" + code + "上传成功" + ddate()); //写入log 
				tempwrite(rs.getString("cPOID"));   //将单据号写入temp
				File writename = new File("E:\\BWMS\\301\\" + rs.getString("cPOID") + " " + ddate()
						+ "manu301.xml"); // 相对路径，如果没有则要建立一个新的output。txt文件
				try {
					writename.delete();
					writename.createNewFile();
					BufferedWriter out = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(
									writename), "UTF-8"));
					out.write(xml301); // \r\n即为换行
					out.flush(); // 把缓存区内容压入文件
					out.close(); // 最后记得关闭文件
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					writelog(e + ddate());
				}
			}
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			xml301 = "";		
			writelog(e + ddate());
		}
		
		if (xml301.equals("")) 
		{
			JOptionPane.showInternalMessageDialog(bt1, "上传失败", "信息",JOptionPane.INFORMATION_MESSAGE);
		} else 
		{
			JOptionPane.showInternalMessageDialog(bt1, "上传完成", "信息",JOptionPane.INFORMATION_MESSAGE);			
		}
		try {
			con.close();
			con = null;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			writelog(e + ddate());
		}
		return xml301;
	}
	
    public static String manuso(String code)   /*手动上传发货单*/
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
	    String xml = "";     //中间数据
	    	    
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
				JOptionPane.showInternalMessageDialog(bt1,"该单号不存在","信息", JOptionPane.INFORMATION_MESSAGE); 	
				return "1";
			}
			else 
			{
				if(rs.getString("bReturnFlag").equals("0"))    //正常退货
				{
				xml302="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
						"<result class=\"object\">"+
						"<datatype>302</datatype>"+        
						"<doc class=\"array\">";
				
				if (tempread().indexOf(rs.getString("cDLCode")) > -1)
				{
					int res = JOptionPane.showConfirmDialog(null, "该订单已上传，是否继续？", "提示",
							JOptionPane.YES_NO_OPTION);
					if (res == JOptionPane.YES_OPTION) {
					} else {
						return "1";
					}
				}				
					xml302=xml302+"<e class=\"object\">"+
							"<eorderno>" + rs.getString("cSOCode") + "</eorderno>"+   /*销售订单*/
							"<credate>" + datetime + "</credate>"+      /*单据日期*/
							"<preexpdate></preexpdate>"+
							"<expdate></expdate>"+
							"<prearrivedate></prearrivedate>"+
							"<arrivedate></arrivedate>"+
							"<ptransmodeid></ptransmodeid>"+
							"<transmode></transmode>"+
							"<earlydate></earlydate>"+
							"<composeinfo></composeinfo>"+
							"<operationtype>11</operationtype>"+      /*业务类型 11：销售；12：进货退出（采退）；21 （报损）移库出是18*/
							"<medicineclass></medicineclass>"+   
							"<receiveaddr>" + rs.getString("cDefine11") + "</receiveaddr>"+     /*收货地址*/
							"<receivehead></receivehead>"+          
							"<receiveman>" + rs.getString("cDefine12") + "</receiveman>"+       /*收货人*/
							"<partexpflag></partexpflag>"+
							"<samelotflag></samelotflag>"+
							"<urgenflag></urgenflag>"+
							"<addinvoiceflag></addinvoiceflag>"+
							"<arrivememo></arrivememo>"+
							"<exportmemo>" + (rs.getString("cMemo")==null?"":rs.getString("cMemo"))+ "</exportmemo>"+   //出货备注
							"<detaillines></detaillines>"+
							"<srcexpno>" + rs.getString("cDLCode") + "</srcexpno>"+          /*发货单号*/
							"<transid></transid>"+
							"<porderid></porderid>"+
							"<billfrom></billfrom>"+
							"<usestatus></usestatus>"+
							"<outmode></outmode>"+               
							"<invoicetype>0</invoicetype>"+       /*发票类型 0：增值税发票，1：普通发票，2:自购发票，默认为1*/
							"<transclass></transclass>"+
							"<receivephone>" + rs.getString("cDefine10") + "</receivephone>"+       /*收货人电话*/
							"<tpricetick></tpricetick>"+
							"<tpriceflag></tpriceflag>"+
							"<deptno></deptno>"+
							"<deptname></deptname>"+
							"<gcompanyid>" + rs.getString("cCusCode") + "</gcompanyid>"+      /*客户ID？*/
							"<salerid></salerid>"+
							"<salername>" + rs.getString("cMaker") + "</salername>"+       /*业务员名称？*/
							"<prtclass></prtclass>"+
							"<prtgroupset></prtgroupset>"+
							"<nowlflag></nowlflag>"+
							"<sysmodifydate>" + datetime + "</sysmodifydate>"+   /*时间戳？*/
							"<goodsownerid>521</goodsownerid>"+                  /*货主ID 521*/
							"<companystype>1</companystype>"+                      /*单位类型 1：客户；2：供应商；3：生产厂家；*/
							"<warehid>3</warehid>"+                               /*物流中心ID 3*/
							"<relevanceno>521" + rs.getString("cDLCode") + "11</relevanceno>"+        /*货主ID+销售订单号+业务类型*/
							"<realrecaddr></realrecaddr>"+               /*???*/ 		   
							"<dtl class=\"array\">";         
					
					String SQL_ = "select * from dispatchlists where DLID='" + rs.getString("DLID") + "'";
					stmt = con.createStatement();    
		            rs_ = stmt.executeQuery(SQL_);
					
		            while (rs_.next())
		            {
		            	xml302=xml302+"<e class=\"object\">"+
		            			"<batchno></batchno>"+
		            			"<lotno>" + rs_.getString("cBatch") + "</lotno>"+     /*批号？*/                                 /*批号？*/
		            			"<validdate>" + rs_.getString("dvDate") + "</validdate>"+      /*有效期*/
		            			"<goodsstatusid>1</goodsstatusid>"+                   /*货品状态 1：合格*/
		            			"<price>" + rs_.getString("iTaxUnitPrice") + "</price>"+       /*单价*/
		            			"<goodsqty>" + rs_.getString("iQuantity") + "</goodsqty>"+     /*数量*/
		            			"<tradegoodsqty></tradegoodsqty>"+
		            			"<tradegoodspack>盒</tradegoodspack>"+    /*基本单位*/
		            			"<addmedcheckflag></addmedcheckflag>"+
		            			"<srcexpdtlno>" + rs_.getString("iDLsID") + "</srcexpdtlno>"+   /*发货单细单号*/
		            			"<usestatus></usestatus>"+
		            			"<gtradepack></gtradepack>"+
		            			"<dtlmemo></dtlmemo>"+
		            			"<retailprice>" + rs_.getString("iTaxUnitPrice") + "</retailprice>"+   /*零售价*/
		            			"<amt>" + rs_.getString("iSum") + "</amt>"+         /*金额*/
		            			"<expdtlmemo></expdtlmemo>"+
		            			"<otcflag></otcflag>"+
		            			"<trademark></trademark>"+
		            			"<approvedocno></approvedocno>"+
		            			"<proddate>" + rs_.getString("dMDate") + "</proddate>"+    /*生产日期*/
		            			"<tpricetick></tpricetick>"+
		            			"<tpriceflag></tpriceflag>"+
		            			"<packsize>" + rs_.getString("iInvExchRate") + "</packsize>"+    /*包装大小?*/
		            			"<invno></invno>"+
		            			"<packname>盒</packname>"+                       /*包装名称*/
		            			"<sorttype></sorttype>"+
		            			"<lotlimit></lotlimit>"+
		            			"<periodlimit></periodlimit>"+
		            			"<periodlimitunit></periodlimitunit>"+
		            			"<erpgoodsid>" + rs_.getString("cInvCode") + "</erpgoodsid>"+     /*存货编码*/
		            			"<srcexpno>" + rs.getString("cDLCode") + "</srcexpno>"+     /*发货单号*/
		            			"<warehid>3</warehid>"+                         /*物流中心ID 3*/
		            			"<killlotno>20160810</killlotno>"+              /*灭菌批号*/
		            			"<relevanceno>521" + rs.getString("cDLCode") + "11</relevanceno>"+   /*货主ID+销售订单号+业务类型*/
		            			"<goodsownerid>521</goodsownerid>"+              /*货主ID 521*/	
		            			"<realqty></realqty>"+ 
		            			"</e>";
		            }			
		            xml302=xml302 + "</dtl>";
				
				count++;		
				xml302=xml302 + "</e>";
				xml302=xml302 + "</doc>" +
							"<totalProperty>" + count + "</totalProperty>"+  /*单据数量*/
							"</result>";
				core("impWmsData",xml302,"302");
				tempwrite(rs.getString("cDLCode"));   //将单据号写入temp
				writelog("发货单" + code + "上传成功" + ddate()); //写入log
				writename = new File("E:\\BWMS\\302\\" + rs.getString("cDLCode") + " " + ddate()
						+ "manu302.xml"); // 相对路径，如果没有则要建立一个新的output。txt文件
				xml = xml302;
				}
				
				else if(rs.getString("bReturnFlag").equals("1"))    //销售退货
				{
					xml301="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
							"<result class=\"object\">"+
							"<datatype>301</datatype>"+        
							"<doc class=\"array\">";
					
					if (tempread().indexOf(rs.getString("cDLCode")) > -1)   
					{
						int res = JOptionPane.showConfirmDialog(null, "该订单已上传，是否继续？", "提示",
								JOptionPane.YES_NO_OPTION);
						if (res == JOptionPane.YES_OPTION) {
						} else {
							return "1";
						}
					}
					
						xml301=xml301+"<e class=\"object\">"+ 
					            "<porderno>" + (rs.getString("cSOCode")==null?"":rs.getString("cSOCode"))+ "</porderno>"+    /*订单号*/
								"<credate>" + datetime + "</credate>"+           /*制单日期*/
								"<arrivedate></arrivedate>"+
								"<medicineclass>1</medicineclass>"+          
								"<importflag>1</importflag>"+
								"<operationtype>7</operationtype>"+             /*业务类型 1：进货；7：销退；22：报溢*/
								"<dtllines></dtllines>"+  
								"<memo>" + (rs.getString("cMemo")==null?"":rs.getString("cMemo"))+ "</memo>"+  /*备注*/
								"<srcno>" + rs.getString("cDLCode") + "</srcno>"+                  /*退货单号*/
								"<exporderid></exporderid>"+              
								"<billfrom>3</billfrom>"+
								"<usestatus>2</usestatus>"+
								"<mulrecflag></mulrecflag>"+
								"<reccount></reccount>"+
								"<inmode>0</inmode>"+
								"<invalidate></invalidate>"+
								"<sabackdate>" + rs.getString("dDate") + "</sabackdate>"+     /*退货日期*/
								"<sbreasonid></sbreasonid>"+                       /*退货原因ID*/
								"<deptno></deptno>"+
								"<deptname></deptname>"+
								"<gcompanyid>" + rs.getString("cCusCode") + "</gcompanyid>"+      /*客户ID？*/
								"<decisionflag></decisionflag>"+    
								"<bmsdocid></bmsdocid>"+
								"<nowlflag>0</nowlflag>"+
								"<transclass>1</transclass>"+
								"<ecodeimpflag></ecodeimpflag>"+
								"<sysmodifydate>" + datetime + "</sysmodifydate>"+     /*时间戳？*/
								"<srcporderid>" + rs.getString("cSTCode") + "</srcporderid>"+    /*原货主订单ID （用作销售类型） */
								"<companystyle>1</companystyle>"+                    /*单位类型 1：客户；2：供应商；3：生产厂家*/
								"<transmode></transmode>"+
								"<erptransid></erptransid>"+
								"<goodsownerid>521</goodsownerid>"+                    /*货主ID 521*/
								"<warehid>3</warehid>"+                                /*物流中心ID 3*/
								"<relevanceno>521" + rs.getString("cDLCode") + "7</relevanceno>"+ /*货主ID+订单号+业务类型*/
								"<dtl class=\"array\">";         
						
						String SQL_ = "select * from dispatchlists where DLID='" + rs.getString("DLID") + "'";
						stmt = con.createStatement();    
			            rs_ = stmt.executeQuery(SQL_);
						
			            while (rs_.next())
			            {
			            	xml301=xml301+"<e class=\"object\">"+
			            			"<porderstatus>1</porderstatus>"+
			            			"<batchno></batchno>"+                   
			            			"<lotno>" + rs_.getString("cBatch") + "</lotno>"+         /*批号？*/
			            			"<goodsqty>" + (rs_.getString("iQuantity").substring(1,(rs_.getString("iQuantity")).length())) + "</goodsqty>"+  /*数量*/
			            			"<tradegoodsqty></tradegoodsqty>"+
			            			"<tradegoodspack>盒</tradegoodspack>"+       /*基本单位*/
			            			"<memo>" + (rs_.getString("cMemo")==null?"":rs_.getString("cMemo")) + "</memo>"+  /*备注*/
			            			"<arrivedate></arrivedate>"+
			            			"<checkdate></checkdate>"+
			            			"<srcdtlno>" + rs_.getString("AutoID") + "</srcdtlno>"+         /*货主原始单据号（主键？）*/
			            			"<lotflag>1</lotflag>"+
			            			"<batchflag>0</batchflag>"+
			            			"<gtradepack>盒</gtradepack>"+                  /*基本单位*/
			            			"<originqty></originqty>"+  
			            			"<price>" + rs_.getString("iTaxUnitPrice") + "</price>"+      /*单价*/
			            			"<retailprice>" + rs_.getString("iTaxUnitPrice") + "</retailprice>"+    /*零售价*/
			            			"<amt></amt>"+               /*金额*/
			            			"<qty></qty>"+
			            			"<quadqty></quadqty>"+
			            			"<unquadqty></unquadqty>"+
			            			"<testqty></testqty>"+
			            			"<sadtlid>" + rs_.getString("iDLsID") + "</sadtlid>"+  //销退原业务单号 （发货单细单号？）
			            			"<expdtlmemo></expdtlmemo>"+
			            			"<goodsstatusid></goodsstatusid>"+
			            			"<quantitystatus></quantitystatus>"+
			            			"<validdate>" + rs_.getString("dvDate") + "</validdate>"+   //有效期
			            			"<proddate>" + (rs_.getString("dMDate")==null?"":rs_.getString("dMDate")) + "</proddate>"+   //生产日期
			            			"<approvedocno></approvedocno>"+
			            			"<firstflag></firstflag>"+
			            			"<tpricetick></tpricetick>"+
			            			"<tpriceflag></tpriceflag>"+
			            			"<packsize>" + (int)(Float.parseFloat(rs_.getString("iQuantity"))/Float.parseFloat(rs_.getString("iNum"))) + "</packsize>"+   /*包装大小*/
			            			"<packname>箱</packname>"+                    /*包装名称*/  
			            			"<prtclass></prtclass>"+
			            			"<srcgoodsqty></srcgoodsqty>"+
			            			"<erpgoodsid>" + rs_.getString("cInvCode") + "</erpgoodsid>"+       /*货号*/
			            			"<lotid></lotid>"+
			            			"<srcno>" + rs.getString("cDLCode") + "</srcno>"+          /*表头订单号关联*/
			            			"<realqty></realqty>"+
			            			"<warehid>3</warehid>"+                       /*物流中心ID 3*/
			            			"<killlotno></killlotno>"+
			            			"<relevanceno>521" + rs.getString("cDLCode") + "7</relevanceno>"+        /*货主ID+订单号+业务类型*/
			            			"<operatioontype>7</operatioontype>"+                /*业务类型 1：进货；7：销退；22：报溢*/
			            			"<goodsownerid>521</goodsownerid>"+                  /*货主ID 521*/
			            			"</e>";
			            }			
			            xml301=xml301 + "</dtl>";
					
					count++;		
					xml301=xml301 + "</e>";
					xml301=xml301 + "</doc>" +
								"<totalProperty>" + count + "</totalProperty>"+  /*单据数量*/
								"</result>";
					
					core("impWmsData",xml301,"301");
					writelog("发货单" + code + "上传成功" + ddate()); //写入log 
					tempwrite(rs.getString("cDLCode"));   //将单据号写入temp
					writename = new File("E:\\BWMS\\301\\" + rs.getString("cDLCode") + " " + ddate()
							+ "manu301.xml"); // 相对路径，如果没有则要建立一个新的output。txt文件		
					xml = xml301;
				}		
								
				try {
					writename.delete();
					writename.createNewFile();
					BufferedWriter out = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(
									writename), "UTF-8"));
					out.write(xml); // \r\n即为换行
					out.flush(); // 把缓存区内容压入文件
					out.close(); // 最后记得关闭文件
				} catch (IOException e) {
					// TODO 自动生成的 catch 块
					e.printStackTrace();
					writelog(e + ddate()); //写入log
				}
			}
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			xml = "";
			writelog(e + ddate()); //写入log
		}	
		try {
			con.close();
			con=null;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			writelog(e + ddate()); //写入log
		}	
		return xml;
	}

    public static String manucustom(String code)    /*手动上传客户档案*/
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
            writelog("客户资料：" + e + ddate());
        }
		
		try {
			if (!rs.next())
			{
				JOptionPane.showInternalMessageDialog(basedata.bt1,"该客户不存在","信息", JOptionPane.INFORMATION_MESSAGE); 	
				return "1";
			}
			else
			{
				xml102 = xml102 + "<e class=\"object\">"
						+ "<goodsownerid>521</goodsownerid>"     /*货主ID 521*/
						+ "<gcompanyid>" + rs.getString("cCusCode") + "</gcompanyid>"   /*客户编码*/
						+ "<companystype>1</companystype>"     /*单位类型 1：客户；2：供应商；3：生产厂家*/
						+ "<companyname>" + rs.getString("cCusName") + "</companyname>"   /*客户名称*/
						+ "<gopinyin></gopinyin>"
						+ "<opcode>135</opcode>"        /*操作码？*/
						+ "<companyshortname>" + rs.getString("cCusAbbName") + "</companyshortname>"  /*客户简称*/
						+ "<bank>" + rs.getString("cCusBank") + "</bank>"    /*开户银行*/
						+ "<bankno>" + rs.getString("cCusAccount") + "</bankno>"     /*银行账号*/
						+ "<chiefofficer></chiefofficer>"
						+ "<connector>" + rs.getString("cCusPerson") + "</connector>"    /*联系人*/
						+ "<connphone>" + rs.getString("cCusPhone") + "</connphone>"    /*联系电话*/
						+ "<deliveraddr>" + rs.getString("cCusAddress") + "</deliveraddr>"   /*送货地址*/
						+ "<invoiceaddr>" + rs.getString("cCusDefine7") + "</invoiceaddr>"   /*发票地址*/
						+ "<invoicehead>" + rs.getString("cCusName") + "</invoicehead>"     /*发票名称？*/
						+ "<invoiceman>" + rs.getString("cCusDefine3") + "</invoiceman>"  /*发票人员？ 收票人？*/
						+ "<invoicephone>" + rs.getString("cCusDefine5") + "</invoicephone>"  /*发票电话？ 收票人电话？*/
						+ "<postcode></postcode>"
						+ "<specialrequire></specialrequire>"
						+ "<taxregistno>" + rs.getString("cCusRegCode") + "</taxregistno>"  /*税号*/
						+ "<telephone></telephone>"
						+ "<memo></memo>"              /*备注*/
						+ "<addmedcheckflag>1</addmedcheckflag>"
						+ "<usestatus>1</usestatus>"
						+ "<companyno>" + rs.getString("cCusCode") + "</companyno>"     /*货主原单位编码？*/
						+ "<orgno>1</orgno>"               /*组织机构代码?*/
						+ "<impflag>1</impflag>"         /*导入标志？*/
						+ "<creDate>" + datetime + "</creDate>"  
						+ "<city></city>"
						+ "<corpid></corpid>"
						+ "<goodsownerflag>0</goodsownerflag>"    /*货主标志 0：非货主，1：货主*/
						+ "<legalpersion></legalpersion>"
						+ "<unifyno></unifyno>"
						+ "<defrtanid>1</defrtanid>"     /*默认地址ID？*/
						+ "<invclass>1</invclass>"        /*发票分类？*/
						+ "<invprintplan></invprintplan>"
						+ "<invtype>1</invtype>"        /*发票类型？*/
						+ "<modedocid>1</modedocid>"    /*二维码打印方案模板ID？*/
						+ "<orderflag>1</orderflag>"    /*集合拣货标志？*/
						+ "<periodlimit>1</periodlimit>"    /*效期限制？*/
						+ "<periodlimtunit></periodlimtunit>"
						+ "<printflag>0</printflag>"       /*是否打印发票？ 0：不打印，1：打印*/
						+ "<companycredate>" + datetime + "</companycredate>"
						+ "<printplan></printplan>"
						+ "<lotlimit>3</lotlimit>"     /*1：只能一个批号，2：最多两个批号，3：不限批号个数，默认为3*/
						+ "<sysmodifydate>" + datetime + "</sysmodifydate>"
						+ "<warehid>3</warehid>"      /*物流中心ID 3*/
						+ "<dtl class=\"array\">";
				
				/*String SQL_ = "select * from PO_POdetails where POID='" + rs.getString("POID") + "'";
				stmt = con.createStatement();    
	            rs_ = stmt.executeQuery(SQL_);
				
	            while (rs_.next())
	            {*/
				xml102 = xml102 + "<e class=\"object\">"
						+ "<goodsownerid>521</goodsownerid>"    /*货主ID 521*/
						+ "<gocompanyid>" + rs.getString("cCusCode") + "</gocompanyid>"      /*客户编码*/
						+ "<transid>1</transid>"     /*默认地址ID？*/
						+ "<inceptaddr>" + rs.getString("cCusAddress") + "</inceptaddr>"   /*运输地址*/
						+ "<connector>" + rs.getString("cCusPerson") + "</connector>"     /*联系人*/
						+ "<connphone>" + rs.getString("cCusPhone") + "</connphone>"      /*联系电话*/
						+ "<telefax></telefax>"
						+ "<postcode></postcode>"
						+ "<memo></memo>"           /*备注*/
						+ "<usestatus>1</usestatus>"   /*使用状态 1:正常，0：作废，默认为1*/
						+ "<tranposname></tranposname>"
						+ "<impflag>1</impflag>"     /*导入标注？ 默认为1*/
						+ "<creDate>" + datetime + "</creDate>"
						+ "<city></city>"
						+ "<equtransid>1</equtransid>"    /*等同地点id?*/
						+ "<sysmodifydate>" + datetime + "</sysmodifydate>"
						+ "<companystype>1</companystype>"      /*单位类型 1：客户；2：供应商；3：生产厂家*/
						+ "<warehid>3</warehid>"        /*物流中心ID 3*/
						+ "</e>";
	            /*}*/		
	            xml102=xml102 + "</dtl>";
			
			 count++;		
			 xml102=xml102 + "</e>";
					
			}
			xml102=xml102+"</doc>"+
					"<totalProperty>" + count + "</totalProperty>"+  /*单据数量*/
					"</result>";
			if(core("impWmsData",xml102,"102").indexOf("success") > -1)   //上传
			{
				writelog("客户资料：" + code + "上传成功" + ddate());
			}
			else
			{
				writelog("客户资料：" + code + "上传失败" + ddate());
			}
			
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			writelog("客户资料：" + e + ddate());
			xml102 = "";		
		}
		try {
			con.close();
			con=null;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			writelog("客户资料：" + e + ddate());
		}
		File writename = new File("E:\\BWMS\\102\\" + ddate() + "manu102.xml"); // 相对路径，如果没有则要建立一个新的output。txt文件
		try {
			writename.delete();
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(writename), "UTF-8"));
			out.write(xml102); // \r\n即为换行
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			writelog("客户资料：" + e + ddate());
		} // 创建新文件

		
		return xml102;
    }
    
	public static String manuInvent(String code)    /*手动上传货品资料*/
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
            writelog("货品资料：" + e + ddate());
        }
		
		try {
			if (!rs.next())
			{
				JOptionPane.showInternalMessageDialog(basedata.bt1,"该存货不存在","信息", JOptionPane.INFORMATION_MESSAGE); 	
				return "1";
			}
			else
			{
				int cclass = 2;
				if (rs.getString("ClassName").indexOf("一") > -1) 
				{
					cclass = 0;
				} else if (rs.getString("ClassName").indexOf("二") > -1) 
				{
					cclass = 1;
				}					
				xml101 = xml101
						+ "<e class=\"object\">"
						+ "<goodsownerid>521</goodsownerid>"      /*货主ID 521*/
						+ "<goodsownid>"+ rs.getString("cInvcode") +"</goodsownid>"    /*存货编码*/
						+ "<barcode></barcode>"
						+ "<goodsno></goodsno>"
						+ "<gopcode>135</gopcode>"                /*操作码？*/
						+ "<gopinyin>"+ rs.getString("cInvcode") +"</gopinyin>"      /*拼音？*/
						+ "<poisondrug>"+ cclass +"</poisondrug>"                    /*存货分类*/
						+ "<goodsname>"+ rs.getString("cInvName") +"</goodsname>"     /*存货名称*/
						+ "<goodsengname>"+ rs.getString("cInvcode") +"</goodsengname>"  /*存货英文名？*/
						+ "<goodstype>"+ rs.getString("cInvStd") +"</goodstype>"        /*规格*/
						+ "<goodsformalname></goodsformalname>"
						/*+ "<factname>"+ (rs.getString("cEnterprise")==null?"1":rs.getString("cEnterprise")) +"</factname>" */ /*生产厂家*/
						+ "<factname>强生（上海）医疗器材有限公司</factname>"
						+ "<tradepack>盒</tradepack>"         /*交易单位*/
						+ "<outpack>盒</outpack>"           /*出货单位*/
						+ "<addmedcheckflag>1</addmedcheckflag>"
						+ "<pasteflag>0</pasteflag>"
						+ "<wholesaleprice></wholesaleprice>"
						+ "<resaleprice></resaleprice>"
						+ "<purchasetax></purchasetax>"
						+ "<saletax></saletax>"
						+ "<lotflag>1</lotflag>"
						+ "<batchflag>0</batchflag>"
						+ "<memo></memo>"             /*备注*/
						+ "<drugform></drugform>"
						+ "<status>1</status>"
						+ "<otcflag></otcflag>"
						+ "<trademark></trademark>"
						+ "<storagecondition>1</storagecondition>"
						+ "<approvedocno></approvedocno>"
						+ "<packsize>"+ (int)Float.parseFloat(rs.getString("iChangRate")) +"</packsize>"  /*包装大小（用作换算率？）*/
						+ "<dbcheck></dbcheck>"
						+ "<periodunit></periodunit>"
						+ "<validperiod></validperiod>"
						+ "<varietyname></varietyname>"
						+ "<supplyername></supplyername>"
						+ "<registdocno>"+ rs.getString("cInvDefine7") +"</registdocno>"   /*注册证号*/
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
						+ "<prodarea>"+ (rs.getString("cProduceAddress")==null?"1":rs.getString("cProduceAddress")) +"</prodarea>"  /*产地*/
						+ "<goodsstatus>1</goodsstatus>"       /*货品状态*/
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
						+ "<templow></templow>"     /*温度下限*/
						+ "<temphigh></temphigh>"     /*温度上限*/
						+ "<packscodeflag></packscodeflag>"
						+ "<lotnocannullflag></lotnocannullflag>"
						+ "<warehid>3</warehid>"             /*物流中心ID 3*/
						+ "<registstartdate>"+ (rs.getString("dEffDate")==null?"":rs.getString("dEffDate")) +"</registstartdate>"     /*注册证开始时间*/
						+ "<registenddate>"+ (rs.getString("dInvalidDate")==null?"":rs.getString("dInvalidDate")) +"</registenddate>"         /*注册证结束时间*/
						+ "<goodsmodel>"+ rs.getString("cSAComUnitCode") +"</goodsmodel>" /*增加型号字段（换算率编码）*/
						+ "<scopeno></scopeno>" + "</e>";
				
				count++;
			}
				xml101 = xml101 + "</doc>" + "<totalProperty>" + count
						+ "</totalProperty>" +  /*单据数量 */
						"</result>";
			
				if(core("impWmsData",xml101,"101").indexOf("success") > -1)
				{
					writelog("货品资料：" + code + "上传成功" + ddate());
				}
				else
				{
					writelog("货品资料：" + code + "上传失败" + ddate());
				}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			writelog("货品资料：" + e + ddate());
			xml101 = "";			
		}
		try {
			con.close();
			con=null;
		} catch (SQLException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			writelog("货品资料：" + e + ddate());
		}
		File writename = new File("E:\\BWMS\\101\\" + ddate() + "manu101.xml"); // 相对路径，如果没有则要建立一个新的output。txt文件
		try {
			writename.delete();
			writename.createNewFile();
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(writename), "UTF-8"));
			out.write(xml101); // \r\n即为换行
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			writelog("货品资料：" + e + ddate());
		} // 创建新文件

		return xml101;
	}
	
	public static String[] loadget()      /*获取所需下载数据的数量*/
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}   
		return cou;
	}
	
	public static String getxml(String path)   //返回指定目录下最后修改的文件名
	{
		File fi=new File(path);
		//列出该目录下所有文件和文件夹
		File[] files = fi.listFiles();
		//按照文件最后修改日期倒序排序
		Arrays.sort(files, new Comparator<File>() {
		   @Override
		   public int compare(File file1, File file2) {
		      return (int)(file2.lastModified()-file1.lastModified());
		   }
		});
		return(files[0].getName());
	}
	
	public static void storein(String fullpath)    //将指定路径下的文件转换为U8标准导入文件
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
		String lorderids = "";  //用来储存所有的入库单单号
		String xml320 = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ufinterface sender=\"A008\" receiver=\"u8\" roottag=\"storein\" proc=\"add\" docid=\"345821559\" codeexchanged=\"N\" exportneedexch=\"N\" display=\"入库单\" family=\"库存管理\" timestamp=\"0x00000000001D47D5\">";
		try {
			doc = reader.read(f);
			Element root = doc.getRootElement();
			Iterator iter_ = root.elementIterator("doc");
			Element element_ = (Element) iter_.next();
			testDownload("","bwmssql.txt");  //下载bwmssql.txt
			/*Element foo;*/
			for (Iterator iter = element_.elementIterator("e"); iter.hasNext();)   //用来输出日志
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
            auto.area.append(lorderids + "下载成功 " + ddate()+"\r\n");
			writelog(lorderids + "下载成功 " + ddate());
			
			for (Iterator iter = element_.elementIterator("e"); iter.hasNext();) 
			{
				Element element = (Element) iter.next();
				//换算率&&U8换算率编码
				Class.forName(driverName);
				con = DriverManager.getConnection(url);
				String SQL = "select D.ichangrate,A.csacomunitcode from Inventory A left join ComputationUnit D on A.cPUComUnitCode = D.cComunitCode where A.cInvCode='" + element.elementText("goodsid") + "'";
	            stmt = con.createStatement();    
	            rs = stmt.executeQuery(SQL);
	            rs.next();
	            //件数
	            float num = Float.parseFloat(rs.getString("ichangrate")) * Float.parseFloat(element.elementText("realrecvqty"));
	            
				if(porderid=="")    //如果是第一次  添加主单和细单
				{
					porderid = element.elementText("porderid");
					xml320 = xml320 + "<storein>"
							+ "<header>"
							+ "<receiveflag>1</receiveflag>"
							+ "<vouchtype>01</vouchtype>"
							+ "<businesstype>普通采购</businesstype>"
							+ "<source>采购订单</source>"
							+ "<businesscode>" + element.elementText("porderid") + "</businesscode>"  //采购订单号
							+ "<warehousecode>10001</warehousecode>"   //仓库
							+ "<date>" + date.format(new Date()) + "</date>"   //日期（今天）
							+ "<code>" + element.elementText("lorderid") + "</code>"    //入库单编号
							+ "<purchasetypecode>01</purchasetypecode>"    //采购类型
							+ "<vendorcode>" + element.elementText("sourcecompanyid") + "</vendorcode>"  //供应商ID
							+ "<ordercode>" + element.elementText("porderid") + "</ordercode>"     //采购订单号
							+ "<memory>" + element.elementText("memo") + "</memory>"     //备注
							+ "<maker>翟佳鸣</maker>"
							+ "<auditdate>" + sub2(element.elementText("validdate")) + "</auditdate>"  //审核日期
							+ "<taxrate>17</taxrate>"
							+ "<exchname>人民币</exchname>"
							+ "<exchrate>1</exchrate>"
							+ "</header>"
							+ "<body>"
							+ "<entry>"
							+ "<inventorycode>" + element.elementText("goodsid") + "</inventorycode>"   //存货编码
							+ "<quantity>" + element.elementText("realrecvqty") + "</quantity>"      //数量
							+ "<assitantunit>" + rs.getString("csacomunitcode") + "</assitantunit>"    //U8换算率编码
							+ "<define32></define32>"    //套包号
							+ "<irate>" + rs.getString("ichangrate") + "</irate>"    //换算率
							+ "<number>" + num + "</number>"    //件数
							+ "<serial>" + element.elementText("lotno") + "</serial>"     //批号
							+ "<makedate>" + sub2(element.elementText("proddate")) + "</makedate>"    //生产日期
							+ "<validdate>" + sub2(element.elementText("validdate")) + "</validdate>"    //失效日期
							+ "<batchproperty6></batchproperty6>"     //注册证号
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
				else if(porderid.equals(element.elementText("porderid")))    /*如果属于同一单据  新增细单*/
				{
					xml320 = xml320 +  "<entry>"
							+ "<inventorycode>" + element.elementText("goodsid") + "</inventorycode>"   //存货编码
							+ "<quantity>" + element.elementText("realrecvqty") + "</quantity>"      //数量
							+ "<assitantunit>" + rs.getString("csacomunitcode") + "</assitantunit>"    //U8换算率编码
							+ "<define32></define32>"    //套包号
							+ "<irate>" + rs.getString("ichangrate") + "</irate>"    //换算率
							+ "<number>" + num + "</number>"    //件数
							+ "<serial>" + element.elementText("lotno") + "</serial>"     //批号
							+ "<makedate>" + sub2(element.elementText("proddate")) + "</makedate>"    //生产日期
							+ "<validdate>" + sub2(element.elementText("validdate")) + "</validdate>"    //失效日期
							+ "<batchproperty6></batchproperty6>"     //注册证号
							+ "</entry>";
					sql = sql + " " + element.elementText("goodsid") + " " + element.elementText("realrecvqty") + " " + num;
				}	
				else          /*如果属于不同单据  新增主单和细单*/
				{
					porderid = element.elementText("porderid");
					xml320 = xml320 + "</body>"+"</storein>";
					xml320 = xml320 + "<storein>"
							+ "<header>"
							+ "<receiveflag>1</receiveflag>"
							+ "<vouchtype>01</vouchtype>"
							+ "<businesstype>普通采购</businesstype>"
							+ "<source>采购订单</source>"
							+ "<businesscode>" + element.elementText("porderid") + "</businesscode>"  //采购订单号
							+ "<warehousecode>10001</warehousecode>"   //仓库
							+ "<date>" + date.format(new Date()) + "</date>"   //日期（今天）
							+ "<code>" + element.elementText("lorderid") + "</code>"    //入库单编号
							+ "<purchasetypecode>01</purchasetypecode>"    //采购类型
							+ "<vendorcode>" + element.elementText("sourcecompanyid") + "</vendorcode>"  //供应商ID
							+ "<ordercode>" + element.elementText("porderid") + "</ordercode>"     //采购订单号
							+ "<memory>" + element.elementText("memo") + "</memory>"     //备注
							+ "<maker>翟佳鸣</maker>"
							+ "<auditdate>" + sub2(element.elementText("validdate")) + "</auditdate>"  //审核日期
							+ "<taxrate>17</taxrate>"
							+ "<exchname>人民币</exchname>"
							+ "<exchrate>1</exchrate>"
							+ "</header>"
							+ "<body>"
							+ "<entry>"
							+ "<inventorycode>" + element.elementText("goodsid") + "</inventorycode>"   //存货编码
							+ "<quantity>" + element.elementText("realrecvqty") + "</quantity>"      //数量
							+ "<assitantunit>" + rs.getString("csacomunitcode") + "</assitantunit>"    //U8换算率编码
							+ "<define32></define32>"    //套包号
							+ "<irate>" + rs.getString("ichangrate") + "</irate>"    //换算率
							+ "<number>" + num + "</number>"    //件数
							+ "<serial>" + element.elementText("lotno") + "</serial>"     //批号
							+ "<makedate>" + sub2(element.elementText("proddate")) + "</makedate>"    //生产日期
							+ "<validdate>" + sub2(element.elementText("validdate")) + "</validdate>"    //失效日期
							+ "<batchproperty6></batchproperty6>"     //注册证号
							+ "</entry>";
					sql = sql + "\r\n" + "1" + " " + element.elementText("lorderid") + " " + element.elementText("porderid")+ " " + element.elementText("goodsid") + " " + element.elementText("realrecvqty") + " " + num;
				}				
			}
			xml320 = xml320 + "</body>"+"</storein></ufinterface>";
			
			try {
				// 写入sql
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
			File writename = new File(filepath); // 相对路径，如果没有则要建立一个新的output。txt文件  
	        try {
	        	writename.delete();
				writename.createNewFile();
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(writename),"UTF-8"));  
		        out.write(xml320); // \r\n即为换行  
		        out.flush(); // 把缓存区内容压入文件  
		        out.close(); // 最后记得关闭文件  
			} catch (IOException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
				auto.area.append(e + ddate()+"\r\n");
				writelog(e + ddate());
			} // 创建新文件
	        
			try {
				//上传
				testUpload(filepath,filename,"BWMS//storein");
				testUpload(bwmssql,"bwmssql.txt","");
			} catch (Exception e) {
				auto.area.append(e + ddate()+"\r\n");
				writelog(e + ddate());
			}
			
			for (Iterator iter = element_.elementIterator("e"); iter.hasNext();)   //添加seqid 用来反馈信息
			{
				Element element = (Element) iter.next();			
				try {
					// 如果文件存在，则追加内容；如果文件不存在，则创建文件
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
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			auto.area.append(e + ddate()+"\r\n");
			writelog(e + ddate());
		}   
	}
	
	public static void stock(String fullpath)    //转换库存
	{
		File f = new File(fullpath);	
		Document doc;
		SAXReader reader = new SAXReader();  
		ArrayList<String[]> arrayList = new ArrayList();
		int i = 1 ;
		String[] st = {"货主名称","存货编码","库存数量","可用数量","批号","生产日期","失效日期","灭菌批号","注册证号","供应商","seqid","goodsownerid","goodsstatus","goodsstatusid","lotid","packsize","stockdate"};
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
			writelog("库存获取成功" + ddate());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			writelog("库存获取失败" + e + ddate());
		}
	}
	
	protected void bt1() {
		String msg = manupo(text2.getText()); 
		if (msg.equals("")) 
		{
			JOptionPane.showInternalMessageDialog(bt1, "上传失败", "信息",JOptionPane.INFORMATION_MESSAGE);
		} else if (!msg.equals("1"))
		{
			JOptionPane.showInternalMessageDialog(bt1, "上传完成", "信息",JOptionPane.INFORMATION_MESSAGE);			
		}
	}
	
	protected void bt2() {
		/*JOptionPane.showInternalMessageDialog(bt2,text4.getText(),"信息", JOptionPane.INFORMATION_MESSAGE); 	*/	
		String msg = manuso(text4.getText()); 	
		if (msg.equals("")) 
		{
			JOptionPane.showInternalMessageDialog(bt2, "上传失败", "信息",JOptionPane.INFORMATION_MESSAGE);
		} else if (!msg.equals("1"))
		{
			JOptionPane.showInternalMessageDialog(bt2, "上传完成", "信息",JOptionPane.INFORMATION_MESSAGE);			
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
	
	public static String sub2(String str)   //去掉字符串最后两位（时间格式不合法）
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
			// TODO 自动生成的 catch 块
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
                  	//如果文件存在，则追加内容；如果文件不存在，则创建文件
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

	public static void writesql(String code)    /*写入tempcode文件*/
	{
		dt();
		FileWriter fw = null;
		if (tempread().indexOf(code) > -1) {
		}
		else
		{
			try {
				// 如果文件存在，则追加内容；如果文件不存在，则创建文件
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
	
	public static void writelog(String str)   /*写入log*/
	{
		FileWriter fw = null;

		try {
			// 如果文件存在，则追加内容；如果文件不存在，则创建文件
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

	public static void testUpload(String filepath,String filename,String type)   //FTP上传  （需上传文件路径，文件名，FTP路径名）
	{ 
        FTPClient ftpClient = new FTPClient(); 
        FileInputStream fis = null; 
        try { 
            ftpClient.connect("220.248.69.70",21); 
            ftpClient.login("EAI", "zxadmin"); 

            File srcFile = new File(filepath); 
            fis = new FileInputStream(srcFile); 
            //设置上传目录 
            ftpClient.changeWorkingDirectory("//" + type); 
            ftpClient.setBufferSize(1024); 
            ftpClient.setControlEncoding("GBK"); 
            //设置文件类型（二进制） 
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
            ftpClient.storeFile(filename, fis); 
        } catch (IOException e) { 
            e.printStackTrace(); 
            throw new RuntimeException("FTP客户端出错！", e); 
        } finally { 
            IOUtils.closeQuietly(fis); 
            try { 
                ftpClient.disconnect(); 
            } catch (IOException e) { 
                e.printStackTrace(); 
                throw new RuntimeException("关闭FTP连接发生异常！", e); 
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
	            //设置文件类型（二进制） 
	            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE); 
	            ftpClient.retrieveFile(remoteFileName, fos); 
	        } catch (IOException e) { 
	            e.printStackTrace(); 
	            throw new RuntimeException("FTP客户端出错！", e); 
	        } finally { 
	            IOUtils.closeQuietly(fos); 
	            try { 
	                ftpClient.disconnect(); 
	            } catch (IOException e) { 
	                e.printStackTrace(); 
	                throw new RuntimeException("关闭FTP连接发生异常！", e); 
	            } 
	        } 
	    } 
	
    public static void existfile(String filepath)   //判断文件夹，若无则创建
    {
    	File file = new File(filepath);
    	if(!file.exists())
    	{
			try {
				file.mkdir();
				writelog(filepath + "创建完成");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	else
    	{
    		writelog(filepath + "已创建");
    	}
    }
    
    public static void crtfile()   //创建文件夹
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
    
    public static void empty(String file)   //清空指定TXT文件
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
