package su.jrat.client.commands;

import java.io.PrintStream;
import java.util.List;

import jrat.api.commands.AbstractCommand;
import su.jrat.client.settings.Statistics;
import su.jrat.client.settings.Statistics.StatEntry;

public class CommandListStatistics extends AbstractCommand {

	@Override
	public void process(String[] args, PrintStream out) throws Exception {
		List<StatEntry> list = Statistics.getGlobal().getList();

		for (StatEntry entry : list) {
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
