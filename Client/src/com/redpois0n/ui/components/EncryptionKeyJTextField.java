package com.redpois0n.ui.components;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

import com.redpois0n.common.crypto.Crypto;

@SuppressWarnings("serial")
public abstract class EncryptionKeyJTextField extends JTextField {
	
	public EncryptionKeyJTextField() {
		super();
		super.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				updateTextField();
			}
		});
	}
	
	public final void updateTextField() {
		String text = super.getText();
		
		//lblLength.setText(text.length() + "");
		
		if (text.length() == Crypto.KEY_LENGTH) {
			super.setBackground(Color.green); 
			//lblLength.setForeground(Color.green);
		} else {
			super.setBackground(Color.red);
			//lblLength.setForeground(Color.red);
		}
		
		onUpdate(text.length() == Crypto.KEY_LENGTH);
	}
	
	public abstract void onUpdate(boolean correct);

}
