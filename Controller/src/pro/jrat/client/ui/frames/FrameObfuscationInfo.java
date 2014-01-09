package pro.jrat.client.ui.frames;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import pro.jrat.client.utils.Utils;

import com.redpois0n.zkmlib.Configuration;
import com.redpois0n.zkmlib.types.EncryptStringLiterals;
import com.redpois0n.zkmlib.types.ExceptionObfuscation;
import com.redpois0n.zkmlib.types.ObfuscateFlow;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class FrameObfuscationInfo extends BaseFrame {

	private JPanel contentPane;
	private JComboBox<String> cbObfuscate;
	private JComboBox<String> cbExceptions;
	private JComboBox<String> cbStrings;
	private JTextField txtPackageName;
	private JCheckBox chckbxCollapsePackages;
	
	public String getObfuscateFlow() {
		return cbObfuscate.getSelectedItem().toString();
	}
	
	public String getExceptionObfuscation() {
		return cbExceptions.getSelectedItem().toString();
	}
	
	public String getStringLiterals() {
		return cbStrings.getSelectedItem().toString();
	}

	public FrameObfuscationInfo() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("Obfuscation Info");
		setIconImage(Toolkit.getDefaultToolkit().getImage(FrameObfuscationInfo.class.getResource("/icons/block.png")));
		setResizable(false);
		setBounds(100, 100, 300, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblObfuscateFlow = new JLabel("Obfuscate Flow:");
		lblObfuscateFlow.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		cbObfuscate = new JComboBox<String>();
		
		for (ObfuscateFlow z : ObfuscateFlow.values()) {
			cbObfuscate.addItem(z.toString());
		}
		
		cbExceptions = new JComboBox<String>();
		
		for (ExceptionObfuscation z : ExceptionObfuscation.values()) {
			cbExceptions.addItem(z.toString());
		}
		
		cbStrings = new JComboBox<String>();
		
		for (EncryptStringLiterals z : EncryptStringLiterals.values()) {
			cbStrings.addItem(z.toString());
		}
				
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
		
		JLabel lblName = new JLabel("Name");
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(chckbxCollapsePackages)
								.addContainerGap())
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblEncryptStrings, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
									.addContainerGap())
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_contentPane.createSequentialGroup()
											.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
												.addComponent(lblExceptionObf, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
												.addComponent(lblObfuscateFlow))
											.addGap(14))
										.addGroup(gl_contentPane.createSequentialGroup()
											.addComponent(lblName)
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(cbObfuscate, Alignment.TRAILING, 0, 172, Short.MAX_VALUE)
										.addComponent(cbStrings, Alignment.TRAILING, 0, 172, Short.MAX_VALUE)
										.addComponent(cbExceptions, 0, 172, Short.MAX_VALUE)
										.addComponent(txtPackageName, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)))))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
							.addGap(10))))
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
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtPackageName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblName))
					.addPreferredGap(ComponentPlacement.RELATED, 127, Short.MAX_VALUE)
					.addComponent(btnOk)
					.addContainerGap())
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

	public Configuration getConfig() {
		ObfuscateFlow flow = ObfuscateFlow.NONE;
		
		for (ObfuscateFlow of : ObfuscateFlow.values()) {
			if (of.toString().equals(cbObfuscate.getSelectedItem().toString())) {
				flow = of;
			}
		}
		
		ExceptionObfuscation exceptionObfuscation = ExceptionObfuscation.NONE;
		
		for (ExceptionObfuscation of : ExceptionObfuscation.values()) {
			if (of.toString().equals(cbExceptions.getSelectedItem().toString())) {
				exceptionObfuscation = of;
			}
		}
		
		EncryptStringLiterals strings = EncryptStringLiterals.NONE;
		
		for (EncryptStringLiterals of : EncryptStringLiterals.values()) {
			if (of.toString().equals(cbStrings.getSelectedItem().toString())) {
				strings = of;
			}
		}
		
		return new Configuration(null, null, null, flow, exceptionObfuscation, strings, chckbxCollapsePackages.isSelected(), txtPackageName.getText().trim());
	}
}
