package pro.jrat.client.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import pro.jrat.client.Help;
import pro.jrat.client.ShellcodeGenerator;
import pro.jrat.client.ui.frames.FrameExecutableInfo;
import javax.swing.JCheckBox;

@SuppressWarnings("serial")
public class PanelBuildOutput extends JPanel {

	public FrameExecutableInfo frame = new FrameExecutableInfo();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private final ButtonGroup buttonGroup_1 = new ButtonGroup();
	private JRadioButton rdbtnShellcode;
	private JRadioButton rdbtnjarjavaArchive;
	private JRadioButton rdbtnCcArray;
	private JRadioButton rdbtnPythonArray;
	private JRadioButton rdbtnDelphiArray;
	private JRadioButton rdbtnJavaArray;
	private JRadioButton rdbtnCArray;
	private JRadioButton rdbtnexenetExecutable;
	private JButton btnAssemblyInfo;
	private JCheckBox chckbxObfuscate;

	public boolean useShellcode() {
		return rdbtnShellcode.isSelected();
	}

	public boolean useExe() {
		return rdbtnexenetExecutable.isSelected();
	}

	public int getShellcode() {
		if (!useShellcode()) {
			return -1;
		}
		if (rdbtnCcArray.isSelected()) {
			return ShellcodeGenerator.CCPP;
		} else if (rdbtnPythonArray.isSelected()) {
			return ShellcodeGenerator.PYTHON;
		} else if (rdbtnDelphiArray.isSelected()) {
			return ShellcodeGenerator.DELPHI;
		} else if (rdbtnJavaArray.isSelected()) {
			return ShellcodeGenerator.JAVA;
		} else if (rdbtnCArray.isSelected()) {
			return ShellcodeGenerator.CSHARP;
		} else {
			return -1;
		}
	}
	
	public boolean obfuscate() {
		return chckbxObfuscate.isSelected();
	}

	public PanelBuildOutput() {

		rdbtnjarjavaArchive = new JRadioButton(".jar (Java Archive)");
		rdbtnjarjavaArchive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JRadioButton btn = (JRadioButton) arg0.getSource();
				boolean b = !btn.isSelected();
				rdbtnCcArray.setEnabled(b);
				rdbtnPythonArray.setEnabled(b);
				rdbtnDelphiArray.setEnabled(b);
				rdbtnJavaArray.setEnabled(b);
				rdbtnCArray.setEnabled(b);
			}
		});
		buttonGroup.add(rdbtnjarjavaArchive);
		rdbtnjarjavaArchive.setSelected(true);

		rdbtnShellcode = new JRadioButton("Shellcode");
		rdbtnShellcode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JRadioButton btn = (JRadioButton) arg0.getSource();
				boolean b = btn.isSelected();
				rdbtnCcArray.setEnabled(b);
				rdbtnPythonArray.setEnabled(b);
				rdbtnDelphiArray.setEnabled(b);
				rdbtnJavaArray.setEnabled(b);
				rdbtnCArray.setEnabled(b);
			}
		});
		buttonGroup.add(rdbtnShellcode);

		rdbtnCcArray = new JRadioButton("C/C++ Array");
		rdbtnCcArray.setEnabled(false);
		buttonGroup_1.add(rdbtnCcArray);

		rdbtnPythonArray = new JRadioButton("Python Array");
		rdbtnPythonArray.setEnabled(false);
		buttonGroup_1.add(rdbtnPythonArray);

		rdbtnJavaArray = new JRadioButton("Java Array");
		rdbtnJavaArray.setEnabled(false);
		rdbtnJavaArray.setSelected(true);
		buttonGroup_1.add(rdbtnJavaArray);

		rdbtnDelphiArray = new JRadioButton("Delphi Array");
		rdbtnDelphiArray.setEnabled(false);
		buttonGroup_1.add(rdbtnDelphiArray);

		rdbtnCArray = new JRadioButton("C# Array");
		rdbtnCArray.setEnabled(false);
		buttonGroup_1.add(rdbtnCArray);

		btnAssemblyInfo = new JButton("Assembly Info");
		btnAssemblyInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frame.setVisible(true);
			}
		});

		rdbtnexenetExecutable = new JRadioButton(".exe (.NET executable)");
		buttonGroup.add(rdbtnexenetExecutable);

		JButton button = new JButton("?");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Help.help("Not recommended. Use third party tool instead.");
			}
		});
		
		chckbxObfuscate = new JCheckBox("Obfuscate");
		
		File zkmDir = new File("files/zkm/");
		
		if (!zkmDir.exists()) {
			chckbxObfuscate.setEnabled(false);
			chckbxObfuscate.setText("No ZKM installation found in /files/zkm/");
		} else {
			chckbxObfuscate.setSelected(true);
		}
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(37)
							.addComponent(rdbtnShellcode))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(58)
							.addComponent(rdbtnCcArray))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(58)
							.addComponent(rdbtnPythonArray))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(58)
							.addComponent(rdbtnJavaArray))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(58)
							.addComponent(rdbtnDelphiArray))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(58)
							.addComponent(rdbtnCArray))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(37)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnexenetExecutable)
								.addComponent(rdbtnjarjavaArchive))
							.addGap(4)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(btnAssemblyInfo)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(button))
								.addComponent(chckbxObfuscate))))
					.addGap(76))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(58)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnjarjavaArchive)
						.addComponent(chckbxObfuscate))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnexenetExecutable)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(btnAssemblyInfo)
							.addComponent(button)))
					.addGap(3)
					.addComponent(rdbtnShellcode)
					.addGap(3)
					.addComponent(rdbtnCcArray)
					.addGap(3)
					.addComponent(rdbtnPythonArray)
					.addGap(3)
					.addComponent(rdbtnJavaArray)
					.addGap(3)
					.addComponent(rdbtnDelphiArray)
					.addGap(3)
					.addComponent(rdbtnCArray))
		);
		setLayout(groupLayout);

	}
}
