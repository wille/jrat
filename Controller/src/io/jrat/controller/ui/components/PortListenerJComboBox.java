package io.jrat.controller.ui.components;

import io.jrat.controller.listeners.SocketComboBoxListener;
import io.jrat.controller.net.PortListener;
import io.jrat.controller.net.ServerListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;


@SuppressWarnings("serial")
public class PortListenerJComboBox extends JComboBox<Object> {

	private SocketComboBoxListener listener;

	public PortListenerJComboBox(SocketComboBoxListener listener) {
		super();
		this.listener = listener;

		List<String> names = new ArrayList<String>();

		for (PortListener pl : PortListener.listeners) {
			names.add(pl.getName());
		}

		super.setModel(new DefaultComboBoxModel<Object>(names.toArray(new String[0])));

		super.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				PortListenerJComboBox.this.listener.onChange(ServerListener.getListener(PortListenerJComboBox.this.getSelectedItem().toString()));
			}
		});
	}

}
