package se.jrat.client.ui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import se.jrat.client.listeners.SocketComboBoxListener;
import se.jrat.client.net.PortListener;
import se.jrat.client.net.ServerListener;


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
