package io.jrat.client.commands;

import io.jrat.api.commands.AbstractCommand;
import io.jrat.client.SampleMode;

import java.io.PrintStream;

public class CommandAddStatistics extends AbstractCommand {

	@Override
	public void process(String[] args, PrintStream arg1) throws Exception {
		SampleMode.start(true);
	}

	@Override
	public String getDescription() {
		return "Populates statistics for debugging";
	}

	@Override
	public String getExample() {
		return "addstats";
	}

	@Override
	public String getUsage() {
		return "addstats";
	}

}
