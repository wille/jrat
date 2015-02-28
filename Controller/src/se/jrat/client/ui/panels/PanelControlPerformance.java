package se.jrat.client.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.LayoutStyle.ComponentPlacement;

import se.jrat.client.Slave;
import se.jrat.client.threads.ThreadSystemMonitor;

import com.redpois0n.graphs.smooth.SmoothGraph;

@SuppressWarnings("serial")
public class PanelControlPerformance extends PanelControlParent {

	private SmoothGraph ramMeter;
	private JPanel panelRAM;
	private boolean needRam = false;
	private JProgressBar barRAM;

	public PanelControlPerformance(Slave slave) {
		super(slave);
		final Slave sl = slave;

		ramMeter = new SmoothGraph();

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("System Monitor"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 580, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE).addContainerGap(14, Short.MAX_VALUE)));

		panelRAM = new JPanel();
		panelRAM.setLayout(new BoxLayout(panelRAM, BoxLayout.X_AXIS));
		panelRAM.add(ramMeter);

		JCheckBox chckbxActiveRamMonitor = new JCheckBox("Active RAM monitor");
		chckbxActiveRamMonitor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JCheckBox src = (JCheckBox) arg0.getSource();
				boolean checked = src.isSelected();
				if (checked) {
					needRam = true;
					new ThreadSystemMonitor(sl).start();
				} else {
					needRam = false;
				}
			}
		});

		barRAM = new JProgressBar();
		barRAM.setStringPainted(true);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(chckbxActiveRamMonitor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(barRAM, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
					.addContainerGap())
				.addComponent(panelRAM, GroupLayout.DEFAULT_SIZE, 568, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelRAM, GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(barRAM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxActiveRamMonitor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
	}
	
	public boolean shouldSend() {
		return needRam;
	}
	
	public SmoothGraph getGraph() {
		return ramMeter;
	}
	
	public JProgressBar getProgressBar() {
		return barRAM;
	}
}
