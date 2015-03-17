package se.jrat.client;

import java.util.Random;

import se.jrat.client.net.ConnectionHandler;
import se.jrat.client.settings.StatisticsCountry;
import se.jrat.client.settings.StatisticsOperatingSystem;
import se.jrat.client.utils.FlagUtils;
import se.jrat.client.utils.NetUtils;
import se.jrat.common.Version;

import com.redpois0n.oslib.AbstractOperatingSystem;
import com.redpois0n.oslib.bsd.BSDOperatingSystem;
import com.redpois0n.oslib.bsd.Flavor;
import com.redpois0n.oslib.linux.Distro;
import com.redpois0n.oslib.linux.LinuxOperatingSystem;
import com.redpois0n.oslib.osx.OSXOperatingSystem;
import com.redpois0n.oslib.osx.OSXVersion;
import com.redpois0n.oslib.solaris.SolarisOperatingSystem;
import com.redpois0n.oslib.windows.WindowsOperatingSystem;
import com.redpois0n.oslib.windows.WindowsVersion;

public class SampleMode {

	private static boolean sampleMode = false;

	public static boolean isInSampleMode() {
		return sampleMode;
	}

	public static void start(boolean stats) {
		sampleMode = true;

		for (WindowsVersion wv : WindowsVersion.values()) {
			String country = randomCountry();
			make(country, new WindowsOperatingSystem(wv), stats);
		}

		for (OSXVersion ov : OSXVersion.values()) {
			String country = randomCountry();
			make(country, new OSXOperatingSystem(ov), stats);
		}

		for (Flavor f : Flavor.values()) {
			String country = randomCountry();
			make(country, new BSDOperatingSystem(f), stats);
		}

		for (Distro d : Distro.values()) {
			String country = randomCountry();
			make(country, new LinuxOperatingSystem(d), stats);
		}
		
		make(randomCountry(), new SolarisOperatingSystem(), stats);
	}

	public static void make(String country, AbstractOperatingSystem os, boolean stats) {
		int i = (new Random()).nextInt(65535);

		final String ip = NetUtils.randomizeIP();

		final int p = new Random().nextInt(500);

		SampleSlave slave = new SampleSlave(os) {
			public String getRawIP() {
				return ip;
			}

			public int getPing() {
				return p;
			}
		};

		if (stats) {
			int howMany = new Random().nextInt(100);
			for (int s = 0; s < howMany; s++) {
				StatisticsCountry.getGlobal().add(slave);
				StatisticsOperatingSystem.getGlobal().add(slave);
			}
		} else {
			ConnectionHandler.addSlave(slave);
			slave.setCountry(country);
			slave.setStatus(5);
			slave.setID(Constants.NAME + new Random().nextInt(1000));
			slave.setComputerName("Sample");
			slave.setUsername("Sample");
			slave.setVersion(Version.getVersion());
		}
	}

	public static String randomCountry() {
		return FlagUtils.COUNTRIES.keySet().toArray(new String[0])[new Random().nextInt(FlagUtils.COUNTRIES.size() - 1)];
	}

}
