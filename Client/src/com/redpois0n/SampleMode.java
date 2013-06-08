package com.redpois0n;

import java.util.Random;

import com.redpois0n.ip2c.Country;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.utils.FlagUtils;
import com.redpois0n.utils.NetworkUtils;


public class SampleMode {
	
	private static boolean sampleMode = false;
	
	public static boolean isInSampleMode() {
		return sampleMode;
	}
	
	public static void start() {	
		sampleMode = true;
		make("AD");
		make("AE");
		make("AF");
		make("AG");
		make("AI");
		make("AL");
		make("AM");
		make("AN");
		make("AO");
		make("AR");
		make("AS");
		make("AT");
		make("AU");
		make("AW");
		make("AX");
		make("AZ");
		make("BA");
		make("BB");
		make("BD");
		make("BE");
		make("BF");
		make("BG");
		make("BH");
		make("BI");
		make("BJ");
		make("BM");
		make("BN");
		make("BO");
		make("BR");
		make("BS");
		make("BT");
		make("BV");
		make("BW");
		make("BY");
		make("BZ");
		make("CA");
		make("CC");
		make("CD");
		make("CF");
		make("CG");
		make("CH");
		make("CI");
		make("CK");
		make("CL");
		make("CM");
		make("CN");
		make("CO");
		make("CR");
		make("CS");
		make("CU");
		make("CV");
		make("CX");
		make("CY");
		make("CZ");
		make("DE");
		make("DJ");
		make("DK");
		make("DM");
		make("DO");
		make("DZ");
		make("EC");
		make("EE");
		make("EG");
		make("EH");
		make("ENGLAND");
		make("ER");
		make("ERRORFLAG");
		make("ES");
		make("ET");
		make("FAM");
		make("FI");
		make("FJ");
		make("FK");
		make("FM");
		make("FO");
		make("FR");
		make("GA");
		make("GB");
		make("GD");
		make("GE");
		make("GH");
		make("GI");
		make("GL");
		make("GM");
		make("GN");
		make("GP");
		make("GQ");
		make("GR");
		make("GS");
		make("GT");
		make("GU");
		make("GW");
		make("GY");
		make("HK");
		make("HN");
		make("HR");
		make("HT");
		make("HU");
		make("ICON");
		make("ID");
		make("IE");
		make("IL");
		make("IN");
		make("IO");
		make("IQ");
		make("IR");
		make("IS");
		make("IT");
		make("JM");
		make("JO");
		make("JP");
		make("KE");
		make("KG");
		make("KH");
		make("KI");
		make("KM");
		make("KN");
		make("KP");
		make("KR");
		make("KW");
		make("KY");
		make("KZ");
		make("LA");
		make("LB");
		make("LC");
		make("LI");
		make("LK");
		make("LR");
		make("LS");
		make("LT");
		make("LU");
		make("LV");
		make("LY");
		make("MA");
		make("MC");
		make("MD");
		make("MG");
		make("MH");
		make("MK");
		make("ML");
		make("MM");
		make("MN");
		make("MO");
		make("MP");
		make("MQ");
		make("MR");
		make("MS");
		make("MT");
		make("MU");
		make("MV");
		make("MW");
		make("MX");
		make("MY");
		make("MZ");
		make("NA");
		make("NC");
		make("NE");
		make("NF");
		make("NG");
		make("NI");
		make("NL");
		make("NO");
		make("NP");
		make("NR");
		make("NU");
		make("NZ");
		make("OM");
		make("PA");
		make("PE");
		make("PF");
		make("PG");
		make("PH");
		make("PK");
		make("PL");
		make("PM");
		make("PN");
		make("PR");
		make("PS");
		make("PT");
		make("PW");
		make("PY");
		make("QA");
		make("RO");
		make("RU");
		make("RW");
		make("SA");
		make("SB");
		make("SC");
		make("SCOTLAND");
		make("SD");
		make("SE");
		make("SG");
		make("SH");
		make("SI");
		make("SK");
		make("SL");
		make("SM");
		make("SN");
		make("SO");
		make("SR");
		make("ST");
		make("SV");
		make("SY");
		make("SZ");
		make("TC");
		make("TD");
		make("TF");
		make("TG");
		make("TH");
		make("TJ");
		make("TK");
		make("TL");
		make("TM");
		make("TN");
		make("TO");
		make("TR");
		make("TT");
		make("TV");
		make("TW");
		make("TZ");
		make("UA");
		make("UG");
		make("UK");
		make("UM");
		make("US");
		make("UY");
		make("UZ");
		make("VA");
		make("VC");
		make("VE");
		make("VG");
		make("VI");
		make("VN");
		make("VU");
		make("WALES");
		make("WF");
		make("WS");
		make("YE");
		make("YT");
		make("ZA");
		make("ZM");
		make("ZW");
	}
	
	public static void make(String country) {
		int i = (new Random()).nextInt(65535);
		
		String ip = NetworkUtils.randomizeIP() + " / " + i;
		
		Slave slave = generate(country, ip);
		
		Main.connections.add(slave);
		
		Frame.mainModel.addRow(new Object[] { slave.getCountry(), "Example", "Example", ip, "0", "Example", "Example", "Example" });	
	}
	
	public static Slave generate(String country, String ip) {
		Slave slave = new Slave(null, null);
		
		if (Settings.getGlobal().getBoolean("geoip")) {
			Country c = FlagUtils.getCountry(ip.split(" / ")[0]);
			
			if (c != null) {
				country = c.get2cStr().toUpperCase();
			} else {
				country = "?";
			}
		}
		
		slave.setCountry(country);
		
		slave.setIP(ip);
		return slave;
	}

}
