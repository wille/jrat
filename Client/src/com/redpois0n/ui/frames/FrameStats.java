package com.redpois0n.ui.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.redpois0n.StatEntry;
import com.redpois0n.StatMeterEntry;
import com.redpois0n.Statistics;
import com.redpois0n.ui.panels.PanelImage;
import com.redpois0n.ui.renderers.StatMeter;
import com.redpois0n.util.FlagUtils;


@SuppressWarnings("serial")
public class FrameStats extends BaseFrame {

	private JPanel contentPane;
	private PanelImage panel;
	private StatMeter meter;
	
	public FrameStats() {
		super();
		setTitle("Graphical Statistics");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameStats.class.getResource("/icons/resource-monitor-protector.png")));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 537, 266);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(BorderFactory.createLineBorder(Color.gray.brighter()));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 769, Short.MAX_VALUE)
					.addGap(5))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
					.addGap(1))
		);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		panel = new PanelImage();
		panel_1.add(panel, BorderLayout.CENTER);
		contentPane.setLayout(gl_contentPane);
		
		meter = new StatMeter();
		for (int i = 0; i < Statistics.list.size(); i++) {
			StatEntry entry = Statistics.list.get(i);
			StatMeterEntry en = new StatMeterEntry();
			en.color = Color.green.brighter();
			en.country = entry.longcountry;
			en.flag = FlagUtils.getCountry(entry.country.toLowerCase());
			en.entry = entry;
			en.scountry = entry.country;
			
			meter.values.add(en);		
			if (entry.list.size() > meter.max) {
				meter.max = entry.list.size();
			}
		}
		
		new Thread("Stat monitor painter") {
			public void run() {
				try {
					Thread.sleep(500L);
					while (FrameStats.this.isVisible()) {
						panel.image = meter.generate(getWidth(), getHeight());
						repaint();
						Thread.sleep(500L);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}.start();

		int size = Statistics.list.size();
		
		if (size * 15 > getWidth()) {
			setSize(size * 15, getHeight());
		}
	}
}
