package pro.jrat.commands;

import pro.jrat.SampleMode;

public class Commandaddstats extends AbstractCommand {

	@Override
	public void process(String[] args) throws Exception {
		SampleMode.start(true);
	}

}
