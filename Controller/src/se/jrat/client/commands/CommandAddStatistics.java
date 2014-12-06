package se.jrat.client.commands;

import java.io.PrintStream;

import se.jrat.client.SampleMode;
import jrat.api.commands.AbstractCommand;

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
