package se.jrat.controller;

public class Drive {

	private String name;
	private short totalspace;
	private short freespace;
	private short usablespace;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public short getTotalSpace() {
		return totalspace;
	}

	public void setTotalSpace(short totalspace) {
		this.totalspace = totalspace;
	}

	public short getFreeSpace() {
		return freespace;
	}

	public void setFreeSpace(short freespace) {
		this.freespace = freespace;
	}

	public short getUsableSpace() {
		return usablespace;
	}

	public void setUsableSpace(short usablespace) {
		this.usablespace = usablespace;
	}

}
