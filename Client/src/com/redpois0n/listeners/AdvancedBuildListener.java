package com.redpois0n.listeners;

import java.awt.Color;
import java.util.LinkedHashMap;

import com.redpois0n.BuildStatus;
import com.redpois0n.ui.panels.PanelBuildFinal;


public class AdvancedBuildListener extends BuildListener {

	private final PanelBuildFinal panel;

	public AdvancedBuildListener(PanelBuildFinal panel) {
		this.panel = panel;
	}

	@Override
	public void done(String msg) {
		panel.setValue(100);
		panel.setStatus(msg, BuildStatus.FINISH);
		panel.log(msg, BuildStatus.FINISH);
		panel.getBuildButton().setEnabled(true);
		panel.getBuildButton().setText("Build");
	}

	@Override
	public void fail(String message) {
		panel.getStatusLabel().setForeground(Color.red);
		panel.setStatus(message, BuildStatus.ERROR);
		panel.log(message, BuildStatus.ERROR);
		panel.getBuildButton().setEnabled(true);
		panel.getBuildButton().setText("Build");
	}

	@Override
	public void start() {
		getStatuses().clear();
		panel.setValue(0);
		panel.getStatusLabel().setForeground(Color.black);
		panel.clearRows();
		panel.setStatus("Building...", BuildStatus.INFO);
		panel.log("Started building...", BuildStatus.INFO);
		panel.getBuildButton().setEnabled(false);
		panel.getBuildButton().setText("Building...");
	}

	@Override
	public void reportProgress(int val, String msg, BuildStatus status) {
		panel.setValue(val);
		panel.setStatus(msg, status);
		panel.repaint();
		panel.log(msg, status);
	}

	@Override
	public LinkedHashMap<String, BuildStatus> getStatuses() {
		return panel.getStatuses();
	}

}
