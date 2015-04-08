package se.jrat.controller.ui.panels;

import iconlib.IconUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import se.jrat.controller.settings.Settings;


@SuppressWarnings("serial")
public class PanelSettingsTheme extends JPanel {

	private DefaultListModel<String> model;
	private JTextField txtTheme;
	private JList<String> list;
	public JFrame parent;

	public String getTheme() {
		return txtTheme.getText();
	}

	public PanelSettingsTheme(JFrame m) {
		this.parent = m;
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Themes"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 427, GroupLayout.PREFERRED_SIZE).addContainerGap(13, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addComponent(panel, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE).addContainerGap(15, Short.MAX_VALUE)));

		JScrollPane scrollPane = new JScrollPane();

		txtTheme = new JTextField();
		txtTheme.setEditable(false);
		txtTheme.setColumns(10);

		JLabel lblTheme = new JLabel("Theme:");

		JButton btnDefault = new JButton("Default");
		btnDefault.setIcon(IconUtils.getIcon("all"));
		btnDefault.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					txtTheme.setText(UIManager.getLookAndFeel().toString());
					setText();
				} catch (Exception ex) {

				}
			}
		});

		JButton btnSelect = new JButton("Select");
		btnSelect.setIcon(IconUtils.getIcon("themes"));
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Object obj = list.getSelectedValue();
					if (obj != null) {
						UIManager.setLookAndFeel(obj.toString());
						txtTheme.setText(obj.toString());
						Settings.getGlobal().save();
					}
					SwingUtilities.updateComponentTreeUI(parent);
					parent.pack();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panel.createParallelGroup(Alignment.TRAILING).addComponent(txtTheme, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE).addComponent(lblTheme).addComponent(btnSelect, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE).addComponent(btnDefault, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)).addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel.createSequentialGroup().addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false).addGroup(gl_panel.createSequentialGroup().addContainerGap().addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)).addGroup(gl_panel.createSequentialGroup().addGap(8).addComponent(lblTheme).addPreferredGap(ComponentPlacement.RELATED).addComponent(txtTheme, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(btnDefault).addPreferredGap(ComponentPlacement.RELATED).addComponent(btnSelect))).addContainerGap(15, Short.MAX_VALUE)));

		list = new JList<String>();
		model = new DefaultListModel<String>();
		list.setModel(model);

		model.clear();
		LookAndFeelInfo[] ls = UIManager.getInstalledLookAndFeels();
		for (LookAndFeelInfo info : ls) {
			model.add(model.size(), info.getClassName());
		}

		scrollPane.setViewportView(list);
		panel.setLayout(gl_panel);
		setLayout(groupLayout);
		setText();
	}

	public void setText() {
		String[] themeargs = UIManager.getLookAndFeel().toString().split(" - ");
		String th = themeargs[1].substring(0, themeargs[1].length() - 1);
		txtTheme.setText(th);
	}
}
