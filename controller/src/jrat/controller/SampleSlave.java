package jrat.controller;

import oslib.AbstractOperatingSystem;

import java.io.File;

public class SampleSlave extends AbstractSlave {

	public SampleSlave(AbstractOperatingSystem os) {
		super(null, null);
		super.os = os;
	}

	@Override
	public void run() {
		
	}

	@Override
	public void ping() throws Exception {
		
	}

	@Override
	public String getHostname() {
		return "Sample";
	}

	@Override
	public String getUsername() {
		return "Sample";
	}

	@Override
	public String getFileSeparator() {
		return File.separator;
	}

}
