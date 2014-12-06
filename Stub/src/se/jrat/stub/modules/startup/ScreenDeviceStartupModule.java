package se.jrat.stub.modules.startup;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Robot;
import java.util.Map;

import se.jrat.stub.Main;

public class ScreenDeviceStartupModule extends StartupModule {

	public ScreenDeviceStartupModule(Map<String, String> config) {
		super(config);
	}

	public void run() throws Exception {
		Main.robot = new Robot();
		GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
		Main.robots = new Robot[devices.length];
		for (int i = 0; i < devices.length; i++) {
			Main.robots[i] = new Robot(devices[i]);
		}
	}

}
