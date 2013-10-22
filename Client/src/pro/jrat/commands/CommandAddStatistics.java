package pro.jrat.commands;

import java.io.PrintStream;

import pro.jrat.SampleMode;
import pro.jrat.api.commands.AbstractCommand;

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
