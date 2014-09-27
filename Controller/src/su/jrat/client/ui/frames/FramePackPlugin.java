package su.jrat.client.ui.frames;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarFile;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import jrat.api.utils.JarUtils;
import su.jrat.client.Main;
import su.jrat.common.Logger;

@SuppressWarnings("serial")
public class FramePackPlugin extends JFrame {

	private JPanel contentPane;
	private JLabel lblName;
	private JLabel lblVersion;
	private JLabel lblIcon;
	private JTextField txtClientJAR;
	private JLabel lblAuthor;
	private JTextPane txtDescription;

	public FramePackPlugin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FramePackPlugin.class.getResource("/icons/plugin_edit.png")));
		setResizable(false);
		setTitle("Pack Plugin");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 552, 437);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Plugin Preview"));
		
		JLabel lblClientJar = new JLabel("Client JAR");
		
		txtClientJAR = new JTextField();
		txtClientJAR.setColumns(10);
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser c = new JFileChooser();
				c.showOpenDialog(null);
				File file = c.getSelectedFile();
				
				if (file != null) {
					txtClientJAR.setText(file.getAbsolutePath());
					loadControllerPlugin(file);
				}
			}
		});
		button.setIcon(new ImageIcon(FramePackPlugin.class.getResource("/icons/folder.png")));
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 536, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblClientJar)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtClientJAR, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(button)
					.addContainerGap(227, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblClientJar)
							.addComponent(txtClientJAR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(button))
					.addContainerGap(235, Short.MAX_VALUE))
		);
		
		lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(FramePackPlugin.class.getResource("/icons/plugin.png")));
		
		lblName = new JLabel("Unknown name");
		
		lblVersion = new JLabel("Unknown version");
		
		lblAuthor = new JLabel("Unknown author");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblIcon)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblAuthor)
						.addComponent(lblVersion)
						.addComponent(lblName))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(230, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblName)
								.addComponent(lblIcon))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblVersion)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblAuthor))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		txtDescription = new JTextPane();
		txtDescription.setEditable(false);
		txtDescription.setText("No description");
		scrollPane.setViewportView(txtDescription);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
	
	@SuppressWarnings("deprecation")
	private void loadControllerPlugin(File file) {
		try {
			ClassLoader cl = new URLClassLoader(new URL[] { file.toURL() }, Main.class.getClassLoader());

			String mainClass;

			try {
				mainClass = JarUtils.getMainClassFromInfo(new JarFile(file));
			} catch (Exception ex) {
				ex.printStackTrace();
				Logger.log("Failed loading main class from plugin.txt, trying meta-inf");
				mainClass = JarUtils.getMainClass(new JarFile(file));
			}

			Class<?> classToLoad = Class.forName(mainClass, true, cl);
			Object instance = classToLoad.newInstance();

			setVersion(classToLoad.getMethod("getVersion", new Class[] {}).invoke(instance, new Object[] {}).toString());
			setAuthor(classToLoad.getMethod("getAuthor", new Class[] {}).invoke(instance, new Object[] {}).toString());
			setDescription(classToLoad.getMethod("getDescription", new Class[] {}).invoke(instance, new Object[] {}).toString());
			setName(classToLoad.getMethod("getName", new Class[] {}).invoke(instance, new Object[] {}).toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setVersion(String version) {
		lblVersion.setText(version);
	}
	
	public void setAuthor(String author) {
		lblAuthor.setText(author);
	}
	
	public void setDescription(String desc) {
		txtDescription.setText(desc);
	}
	
	public void setName(String name) {
		lblName.setText(name);
	}
}
