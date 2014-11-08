package su.jrat.client.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

import su.jrat.client.AbstractSlave;
import su.jrat.client.Main;
import su.jrat.client.Slave;
import su.jrat.client.ip2c.Country;
import su.jrat.client.ip2c.IP2Country;


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
			if (flags.containsKey(name)) {
				icon = flags.get(name);
			} else {
				icon = new ImageIcon(Main.class.getResource("/flags/" + name + ".png"));
				flags.put(name, icon);
			}

		} catch (Exception e) {
			if (flags.containsKey("unknown")) {
				icon = flags.get("unknown");
			} else {
				icon = new ImageIcon(Main.class.getResource("/flags/unknown.png"));
				flags.put("unknown", icon);
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
