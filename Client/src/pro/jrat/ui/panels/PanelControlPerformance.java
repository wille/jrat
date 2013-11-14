package pro.jrat.ui.panels;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;

import pro.jrat.Slave;
import pro.jrat.packets.outgoing.Packet74GarbageCollect;
import pro.jrat.settings.Colors;
import pro.jrat.threads.ThreadSystemMonitor;
import pro.jrat.ui.components.JColorBox;

import com.redpois0n.graphs.taskmgr.ITaskmgrColors;
import com.redpois0n.graphs.taskmgr.TaskmgrColors;
import com.redpois0n.graphs.taskmgr.TaskmgrGraph;

@SuppressWarnings("serial")
public class PanelControlPerformance extends PanelControlParent {

	public TaskmgrGraph ramMeter;
	public JPanel panelRAM;
	public JSlider sliderRam;
	public boolean needRam = false;
	public JProgressBar barRAM;
	private JColorBox colorBox;

	public PanelControlPerformance(Slave slave) {
		super(slave);
		final Slave sl = slave;

		colorBox = new JColorBox(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Color color = colorBox.getColor();
				if (barRAM != null && ramMeter != null) {
					barRAM.setForeground(color);
					ramMeter.setColors(getColors(color));
				}
			}
		});

		colorBox.setProfile(Colors.getGlobal().get("system monitor"));

		ramMeter = new TaskmgrGraph(getColors(colorBox.getColor()), false);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("System Monitor"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 580, GroupLayout.PREFERRED_SIZE).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE).addContainerGap(14, Short.MAX_VALUE)));

		panelRAM = new JPanel();
		panelRAM.setLayout(new BoxLayout(panelRAM, BoxLayout.X_AXIS));
		panelRAM.add(ramMeter);
		panelRAM.setBorder(BorderFactory.createLineBorder(Color.gray.brighter()));

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

		sliderRam = new JSlider();
		sliderRam.setValue(1);
		sliderRam.setMaximum(10);

		JLabel lblInterval = new JLabel("Interval");

		barRAM = new JProgressBar();
		barRAM.setForeground(colorBox.getColor());
		barRAM.setStringPainted(true);

		JButton btnGc = new JButton("GC");
		btnGc.setToolTipText("Runs the garbage collector on servers computer");
		btnGc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sl.addToSendQueue(new Packet74GarbageCollect());
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addComponent(panelRAM, GroupLayout.PREFERRED_SIZE, 549, GroupLayout.PREFERRED_SIZE).addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup().addComponent(btnGc, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE).addPreferredGap(ComponentPlacement.RELATED).addComponent(colorBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(lblInterval).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(sliderRam, GroupLayout.PREFERRED_SIZE, 324, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panel.createSequentialGroup().addComponent(chckbxActiveRamMonitor).addPreferredGap(ComponentPlacement.RELATED).addComponent(barRAM, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE))).addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(panelRAM, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createParallelGroup(Alignment.BASELINE).addComponent(btnGc).addComponent(colorBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addComponent(lblInterval)).addComponent(sliderRam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(13).addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false).addComponent(barRAM, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(chckbxActiveRamMonitor, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addContainerGap(80, Short.MAX_VALUE)));
		panel.setLayout(gl_panel);
		setLayout(groupLayout);

	}

	public ITaskmgrColors getColors(final Color originalColor) {
		return originalColor == null ? new TaskmgrColors() : new ITaskmgrColors() {
			@Override
			public Color getBorderColor() {
				return Color.white;
			}

			@Override
			public Color getCurveColor() {
				return originalColor.brighter();
			}

			@Override
			public Color getGreenMeterColor() {
				return originalColor.darker();
			}

			@Override
			public Color getInnerFillColor() {
				return Color.black;
			}

			@Override
			public Color getNetColor() {
				return originalColor.darker();
			}
		};
	}
}
