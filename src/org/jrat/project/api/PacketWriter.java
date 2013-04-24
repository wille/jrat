package org.jrat.project.api;

import java.util.List;

public abstract class PacketWriter {
	
	public void write(String header, Object obj) {
		write(header, new Object[] { obj });
	}

	public void write(String header, List<Object> obj) {
		write(header, obj.toArray());
	}
	
	public abstract void write(String header, Object[] obj);
	
}
