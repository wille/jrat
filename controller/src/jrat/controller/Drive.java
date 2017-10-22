package jrat.controller;

public class Drive {

	/**
	 * Name of drive (just / on *nix)
	 */
	private String name;
	
	/**
	 * Total space in gigabytes
	 */
	private short totalspace;
	
	/**
	 * Free space in gigabytes
	 */
	private short freespace;
	
	/**
	 * Usable space in gigabytes
	 */
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
