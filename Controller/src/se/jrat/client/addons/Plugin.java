package se.jrat.client.addons;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

import se.jrat.client.Main;
import se.jrat.common.Logger;
import se.jrat.common.Version;
import jrat.api.PluginClassLoader;
import jrat.api.RATControlMenuEntry;
import jrat.api.RATMenuItem;
import jrat.api.events.OnConnectEvent;
import jrat.api.events.OnDisableEvent;
import jrat.api.events.OnDisconnectEvent;
import jrat.api.events.OnEnableEvent;
import jrat.api.events.OnPacketEvent;
import jrat.api.events.OnSendPacketEvent;
import jrat.api.utils.JarUtils;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class Plugin {
	
	public static final int STATUS_DISABLED = 0;
	public static final int STATUS_ENABLED = 1;
	public static final int STATUS_INITIALIZING = 2;
	
	public static final int NAME = 0;
	public static final int AUTHOR = 1;
	public static final int DESCRIPTION = 2;
	public static final int VERSION = 3;
	
	public static final int ON_LOAD = 4;
	public static final int ON_CONNECT = 5;
	public static final int ON_DISCONNECT = 6;
	public static final int ON_PACKET = 7; 
	public static final int ON_ENABLE = 8;
	public static final int ON_DISABLE = 9;
	public static final int ON_SEND_PACKET = 10;

	private ClassLoader loader;
	private Class classToLoad;
	private Object instance;
	private ActionListener globalItemListener;

	private String name;
	private String version;
	private String author;
	private String description;

	private String jarname;

	private int status;
	
	private Map<Integer, Method> methods = new HashMap<Integer, Method>();
	private List<RATMenuItem> items = new ArrayList<RATMenuItem>();
	private List<RATControlMenuEntry> controlitems = new ArrayList<RATControlMenuEntry>();

	public Plugin(File jar) throws Exception {
		this(jar.getAbsolutePath());
	}

	public Plugin(String file) throws Exception {
		setStatus(STATUS_INITIALIZING);
		setJarName(file);
		
		FileInputStream fis = new FileInputStream(file);
		JarInputStream jis = new JarInputStream(fis);
		setLoader(new PluginClassLoader(this.getClass().getClassLoader(), jis));
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

		setClassToLoad(Class.forName(mainClass, true, getLoader()));
		setInstance(classToLoad.newInstance());

		Method onLoad = classToLoad.getMethod("onEnable", new Class[] { OnEnableEvent.class });
		onLoad.invoke(getInstance(), new Object[] { new OnEnableEvent(Version.getVersion()) });
		getMethods().put(Plugin.ON_ENABLE, onLoad);

		setVersion(classToLoad.getMethod("getVersion", new Class[] {}).invoke(getInstance(), new Object[] {}).toString());
		setAuthor(classToLoad.getMethod("getAuthor", new Class[] {}).invoke(getInstance(), new Object[] {}).toString());
		setDescription(classToLoad.getMethod("getDescription", new Class[] {}).invoke(getInstance(), new Object[] {}).toString());
		setName(classToLoad.getMethod("getName", new Class[] {}).invoke(getInstance(), new Object[] {}).toString());
		this.setGlobalItemListener((ActionListener) classToLoad.getMethod("getGlobalMenuItemListener", new Class[] {}).invoke(getInstance(), new Object[] {}));

		List<RATMenuItem> menuitems = (List<RATMenuItem>) classToLoad.getMethod("getMenuItems", new Class[] {}).invoke(getInstance(), new Object[] {});
		if (menuitems != null) {
			setItems(menuitems);
		}

		List<RATControlMenuEntry> citems = (List<RATControlMenuEntry>) classToLoad.getMethod("getControlTreeItems", new Class[] {}).invoke(getInstance(), new Object[] {});
		if (citems != null) {
			setControlitems(citems);
		}

		Method onPacket = classToLoad.getMethod("onPacket", new Class[] { OnPacketEvent.class });
		getMethods().put(Plugin.ON_PACKET, onPacket);

		Method onConnect = classToLoad.getMethod("onConnect", new Class[] { OnConnectEvent.class });
		getMethods().put(Plugin.ON_CONNECT, onConnect);

		Method onDisconnect = classToLoad.getMethod("onDisconnect", new Class[] { OnDisconnectEvent.class });
		getMethods().put(Plugin.ON_DISCONNECT, onDisconnect);

		Method onDisable = classToLoad.getMethod("onDisable", new Class[] { OnDisableEvent.class });
		getMethods().put(Plugin.ON_DISABLE, onDisable);

		Method onSendPacket = classToLoad.getMethod("onSendPacket", new Class[] { OnSendPacketEvent.class });
		getMethods().put(Plugin.ON_SEND_PACKET, onSendPacket);

		Main.debug("Plugin " + getName() + " " + getVersion() + " enabled");

		PluginLoader.register(this);
		
		setStatus(STATUS_ENABLED);
	}

	public ClassLoader getLoader() {
		return loader;
	}

	public void setLoader(ClassLoader classLoader) {
		this.loader = classLoader;
	}

	public Class getClassToLoad() {
		return classToLoad;
	}

	public void setClassToLoad(Class classToLoad) {
		this.classToLoad = classToLoad;
	}

	public Object getInstance() {
		return instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getJarname() {
		return jarname;
	}

	public void setJarName(String jarname) {
		this.jarname = jarname;
	}

	public Map<Integer, Method> getMethods() {
		return methods;
	}

	public void setMethods(Map<Integer, Method> methods) {
		this.methods = methods;
	}

	public List<RATMenuItem> getItems() {
		return items;
	}

	public void setItems(List<RATMenuItem> items) {
		this.items = items;
	}

	public List<RATControlMenuEntry> getControlItems() {
		return controlitems;
	}

	public void setControlitems(List<RATControlMenuEntry> controlitems) {
		this.controlitems = controlitems;
	}

	public ActionListener getGlobalItemListener() {
		return globalItemListener;
	}

	public void setGlobalItemListener(ActionListener globalItemListener) {
		this.globalItemListener = globalItemListener;
	}

	public boolean equals(Object o) {
		if (o instanceof Plugin) {
			Plugin plugin = (Plugin) o;

			return plugin.name.equals(this.name);
		}

		return false;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public static String getStatusString(int i) {
		if (i == STATUS_ENABLED) {
			return "Enabled";
		} else if (i == STATUS_DISABLED) {
			return "Disabled";
		} else if (i == STATUS_INITIALIZING) {
			return "Initializing...";
		} else {
			return "Unknown status";
		}
	}

}
