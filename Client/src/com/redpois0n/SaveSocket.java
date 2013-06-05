package com.redpois0n;

import java.io.Serializable;

import com.redpois0n.net.PortListener;

public class SaveSocket implements Serializable {
	
	private static final long serialVersionUID = -2000514812654090252L;
	
	private String key;
	private String pass;
	private int timeout;
	private int port;
	private String name;
	
	public SaveSocket(String name, int port, int timeout, String key, String pass) {
		this.name = name;
		this.port = port;
		this.timeout = timeout;
		this.key = key;
		this.pass = pass;
	}
	
	public String getKey() {
		return key;
	}
	public String getPass() {
		return pass;
	}
	public int getTimeout() {
		return timeout;
	}
	public int getPort() {
		return port;
	}
	public String getName() {
		return name;
	}
	
	public void start() {
		try {
			PortListener connection = new PortListener(name, port, timeout, key, pass);
			connection.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
