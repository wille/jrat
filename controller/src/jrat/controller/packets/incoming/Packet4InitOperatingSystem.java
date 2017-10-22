package jrat.controller.packets.incoming;

import jrat.controller.Slave;
import java.io.DataInputStream;
import oslib.AbstractOperatingSystem;
import oslib.Arch;
import oslib.DesktopEnvironment;
import oslib.OperatingSystem;
import oslib.UnixOperatingSystem;
import oslib.UnknownOperatingSystem;
import oslib.bsd.BSDOperatingSystem;
import oslib.bsd.Flavor;
import oslib.linux.Distro;
import oslib.linux.DistroSpec;
import oslib.linux.LinuxOperatingSystem;
import oslib.macos.MacOSOperatingSystem;
import oslib.macos.MacOSVersion;
import oslib.solaris.SolarisOperatingSystem;
import oslib.windows.WindowsOperatingSystem;

public class Packet4InitOperatingSystem extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {	
		String name = slave.readLine();
		String details = slave.readLine();
		String sarch = slave.readLine();
		String sde = slave.readLine();
		String deversion = null;
		
		if (dis.readBoolean()) {
			deversion = slave.readLine();
		}

		Arch arch = Arch.getArch(sarch);
		DesktopEnvironment de = DesktopEnvironment.getFromString(sde);
		de.setVersion(deversion);

		OperatingSystem type = OperatingSystem.getOperatingSystem(name);

		AbstractOperatingSystem os = null;

		if (type == OperatingSystem.WINDOWS) {
			os = new WindowsOperatingSystem(details, arch);
		} else if (type == OperatingSystem.LINUX) {
			String distroName = slave.readLine();
			String codename = null;
			String release = null;

			if (dis.readBoolean()) {
				codename = slave.readLine();
			}

			if (dis.readBoolean()) {
				release = slave.readLine();
			}

			DistroSpec ds = new DistroSpec(Distro.getDistroFromName(distroName));

			ds.setCodename(codename);
			ds.setRelease(release);

			os = new LinuxOperatingSystem(ds, arch);
		} else if (type == OperatingSystem.MACOS) {
			MacOSVersion version = null;

			if (dis.readBoolean()) {
				String display = slave.readLine();
				String ver = slave.readLine();

				version = MacOSVersion.getExact(display, ver);
			}

			os = new MacOSOperatingSystem(version, arch);
		} else if (type == OperatingSystem.BSD) {
			String f = slave.readLine();

			Flavor flavor = Flavor.getFlavorFromString(f);

			os = new BSDOperatingSystem(flavor, arch);
		} else if (type == OperatingSystem.SOLARIS) {
			os = new SolarisOperatingSystem(arch);
		} else {
			os = new UnknownOperatingSystem(arch);
		}

		if (os instanceof UnixOperatingSystem) {
			UnixOperatingSystem uos = (UnixOperatingSystem) os;
			uos.setDetailed(details);
		}

		os.setDesktopEnvironment(de);

		slave.setOperatingSystem(os);
	}

}
