package com.redpois0n;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.redpois0n.io.Files;
import com.redpois0n.net.Listener;

public class Sockets {
	
	@SuppressWarnings("unchecked")
	public static void load() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(Files.getSettings(), "sockets.dat")));
			List<SaveSocket> list = (ArrayList<SaveSocket>) in.readObject();
			for (SaveSocket socket : list) {
				socket.start();
			}
			in.close();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			ErrorDialog.create(ex);
		}
 	}
	
	public static void save() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File(Files.getSettings(), "sockets.dat")));
			List<SaveSocket> list = new ArrayList<SaveSocket>();
			for (int i = 0; i < Listener.servers.size(); i++) {
				Listener connection = Listener.servers.get(i);		
				SaveSocket save = new SaveSocket(connection.getName(), connection.getServer().getLocalPort(), connection.getTimeout(), connection.getKey(), connection.getPass());
				list.add(save);
			}
			out.writeObject(list);
			out.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			ErrorDialog.create(ex);
		}
	}

}
