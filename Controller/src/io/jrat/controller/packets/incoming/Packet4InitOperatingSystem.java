package io.jrat.controller.packets.incoming;

import com.redpois0n.oslib.AbstractOperatingSystem;
import com.redpois0n.oslib.Arch;
import com.redpois0n.oslib.DesktopEnvironment;
import com.redpois0n.oslib.OperatingSystem;
import com.redpois0n.oslib.UnixOperatingSystem;
import com.redpois0n.oslib.UnknownOperatingSystem;
import com.redpois0n.oslib.bsd.BSDOperatingSystem;
import com.redpois0n.oslib.bsd.Flavor;
import com.redpois0n.oslib.linux.Distro;
import com.redpois0n.oslib.linux.DistroSpec;
import com.redpois0n.oslib.linux.LinuxOperatingSystem;
import com.redpois0n.oslib.osx.OSXOperatingSystem;
import com.redpois0n.oslib.osx.OSXVersion;
import com.redpois0n.oslib.solaris.SolarisOperatingSystem;
import com.redpois0n.oslib.windows.WindowsOperatingSystem;
import io.jrat.controller.Slave;
import java.io.DataInputStream;

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
		} else if (type == OperatingSystem.OSX) {
			OSXVersion version = null;

			if (dis.readBoolean()) {
				String display = slave.readLine();
				String ver = slave.readLine();

				version = OSXVersion.getExact(display, ver);
			}

			os = new OSXOperatingSystem(version, arch);
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
