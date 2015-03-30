package se.jrat.common.io;

import java.util.HashMap;
import java.util.Map;

public class FileCache {
	
	private static final Map<Object, TransferData> MAP = new HashMap<Object, TransferData>();

	public static TransferData get(Object o) {
		return MAP.get(o);
	}

	public static void put(Object o, TransferData data) {
		MAP.put(o, data);
	}

	public static void remove(Object o) {
		try {
			MAP.remove(o).getOutputStream().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
