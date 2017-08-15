package BWMS;  

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

public class HeaderOMElement {
	public static OMElement createHeaderOMElement() {
		OMFactory factory = OMAbstractFactory.getOMFactory();
		OMNamespace SecurityElementNamespace = factory.createOMNamespace("http://webservice.fms.com", "xs");
		OMElement authenticationOM = factory.createOMElement("Authentication",SecurityElementNamespace);
		OMElement usernameOM = factory.createOMElement("Username",SecurityElementNamespace);
		OMElement passwordOM = factory.createOMElement("Password",SecurityElementNamespace);
		usernameOM.setText("gru8inca00300521");
		passwordOM.setText("gru8inca201707101432");
		authenticationOM.addChild(usernameOM);
		authenticationOM.addChild(passwordOM);
		return authenticationOM;
	}
}
