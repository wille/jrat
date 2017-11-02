package jrat.controller.ui.panels;

import jrat.controller.Help;
import jrat.controller.ui.frames.FrameAppInfo;
import jrat.controller.ui.frames.FrameExecutableInfo;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class PanelBuildOutput extends JPanel {

	public FrameExecutableInfo frameExecutableInfo = new FrameExecutableInfo();
	public FrameAppInfo frameAppInfo = new FrameAppInfo();

	private JRadioButton rdbtnexenetExecutable;
    private JRadioButton rdbtnapposX;

	public boolean useExe() {
		return rdbtnexenetExecutable.isSelected();
	}

	public boolean useApp() {
		return rdbtnapposX.isSelected();
	}
	
	public PanelBuildOutput() {
        JRadioButton rdbtnjarjavaArchive = new JRadioButton(".jar (Java Archive)");

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rdbtnjarjavaArchive);
		rdbtnjarjavaArchive.setSelected(true);

        JButton btnAssemblyInfo = new JButton("Assembly Info");
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
		
		rdbtnapposX = new JRadioButton(".app (OS X application)");
		buttonGroup.add(rdbtnapposX);
		
		JButton btnProperties = new JButton("Properties");
		btnProperties.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frameAppInfo.setVisible(true);
			}
		});
		
		
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)

                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                                .addComponent(rdbtnexenetExecutable)
                                                .addComponent(rdbtnjarjavaArchive)
                                                .addComponent(rdbtnapposX, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
                                                .addComponent(btnProperties, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnAssemblyInfo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addComponent(button))));
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addGap(34)
                                .addComponent(rdbtnjarjavaArchive)
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(rdbtnexenetExecutable)
                                        .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                                .addComponent(btnAssemblyInfo)
                                                .addComponent(button)))
                                .addPreferredGap(ComponentPlacement.RELATED)
                                .addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
                                        .addComponent(rdbtnapposX)
                                        .addComponent(btnProperties))));
        setLayout(groupLayout);

    }
}
