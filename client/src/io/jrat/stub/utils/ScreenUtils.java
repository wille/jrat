package io.jrat.stub.utils;

import java.awt.AWTException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;

public class ScreenUtils {
	
	private static Robot defaultRobot;
	private static Robot[] allRobots;
	
	public static Robot getDefault() throws AWTException {
		if (defaultRobot == null) {
			defaultRobot = new Robot();
		}
		
		return defaultRobot;
	}
	
	public static Robot[] getAllRobots() throws AWTException {
		if (allRobots == null) {
            GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
            allRobots = new Robot[devices.length];
            for (int i = 0; i < devices.length; i++) {
            	allRobots[i] = new Robot(devices[i]);
            }

		}
		
		return allRobots;
	}

}
