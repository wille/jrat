package se.jrat.common.io;

import java.util.HashMap;
import java.util.Map;

import se.jrat.common.io.TransferData.State;

public class FileCache {
	
	public static final Map<Object, TransferData> MAP = new HashMap<Object, TransferData>();

	public static TransferData get(Object o) {
		return MAP.get(o);
	}

	public static void put(Object o, TransferData data) {
		MAP.put(o, data);
	}

	public static void remove(Object o) {
		try {
			TransferData data = MAP.remove(o);
			
			if (data != null && data.getOutputStream() != null) {
				data.getOutputStream().close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void clear(Object o) {
		try {
			TransferData data = FileCache.get(o);

			if (data != null) {
				if (data.getOutputStream() != null) {
					data.getOutputStream().close();
				}				
				data.setState(State.COMPLETED);
				FileCache.remove(data);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
