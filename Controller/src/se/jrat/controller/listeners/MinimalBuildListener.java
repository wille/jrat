package se.jrat.controller.listeners;

import java.util.LinkedHashMap;

import se.jrat.controller.build.BuildStatus;
import se.jrat.controller.ui.frames.FrameBuildMinimal;


public class MinimalBuildListener extends BuildListener {

	private final FrameBuildMinimal panel;

	public MinimalBuildListener(FrameBuildMinimal panel) {
		this.panel = panel;
	}

	@Override
	public void done(String msg) {
		panel.getProgressBar().setValue(100);
		panel.getBuildButton().setEnabled(true);
		panel.getStatusLabel().setText(msg);
	}

	@Override
	public void fail(String message) {
		panel.getStatusLabel().setText(message);
		panel.getBuildButton().setEnabled(true);
	}

	@Override
	public void start() {
		panel.getProgressBar().setValue(0);
		panel.getStatusLabel().setText("Building...");
		panel.getBuildButton().setEnabled(false);
	}

	@Override
	public void reportProgress(int val, String msg, BuildStatus status) {
		panel.getProgressBar().setValue(val);
		panel.getStatusLabel().setText(msg);
		panel.getStatuses().put(msg, status);
		panel.repaint();
	}

	@Override
	public LinkedHashMap<String, BuildStatus> getStatuses() {
		return panel.getStatuses();
	}

}