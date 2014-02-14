package io.jrat.api.commands;

import java.io.PrintStream;

public abstract class AbstractCommand {

	/**
	 * Processes the command
	 * 
	 * @param args
	 * @param printer
	 * @throws Exception
	 */
	public abstract void process(String[] args, PrintStream printer) throws Exception;

	/**
	 * Returns command usage
	 * 
	 * @return
	 */
	public abstract String getUsage();

	/**
	 * Returns command example
	 * 
	 * @return
	 */
	public abstract String getExample();

	/**
	 * Returns command description
	 * 
	 * @return
	 */
	public abstract String getDescription();

}
