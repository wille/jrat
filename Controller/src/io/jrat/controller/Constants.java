package io.jrat.controller;


public class Constants {
	
	/**
	 * What data to retrieve to use in {@link io.jrat.controller.ui.frames#FrameSystemVariables}
	 */
	public static enum VariableMode {
		ENVIRONMENT_VARIABLES,
		SYSTEM_PROPERTIES;
	}
	
	/**
	 * Current used domain
	 */
	public static String HOST = "https://jrat.io";

	/**
	 * Global software name
	 */
	public static final String NAME = "jRAT";

}