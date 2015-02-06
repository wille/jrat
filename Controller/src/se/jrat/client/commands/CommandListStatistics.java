package se.jrat.client.commands;

import java.io.PrintStream;
import java.util.List;

import jrat.api.commands.AbstractCommand;
import se.jrat.client.settings.CountryStatistics;
import se.jrat.client.settings.CountryStatistics.CountryStatEntry;

public class CommandListStatistics extends AbstractCommand {

	@Override
	public void process(String[] args, PrintStream out) throws Exception {
		List<CountryStatEntry> list = CountryStatistics.getGlobal().getList();

		for (CountryStatEntry entry : list) {
			out.println(entry.getLongCountry() + ", " + entry.getCountry() + ", " + entry.getConnects() + ", " + entry.getList().size());
		}
	}

	@Override
	public String getDescription() {
		return "List statistics - debug command";
	}

	@Override
	public String getExample() {
		return "liststats";
	}

	@Override
	public String getUsage() {
		return "liststats";
	}

}
