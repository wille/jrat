package com.redpois0n;

import java.io.Serializable;
import java.util.ArrayList;

public class StatEntry implements Serializable {

	private static final long serialVersionUID = 8462429809223243541L;

	public String country = "Unknown";
	public String longcountry = "Unknown";
	public Integer connects = 0;
	public ArrayList<String> list = new ArrayList<String>();

}
