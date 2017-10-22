package jrat.api;

import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public abstract class Plugin {
	
	public static final File DEFAULT_PLUGIN_DIRECTORY = new File("files/plugins");

	protected String name;
	protected String version;
	protected String description;
	protected String author;
	protected ImageIcon icon;
	
	public Plugin(String name, String version, String description, String author) {
		this(name, version, description, author, null);
	}
	
	public Plugin(String name, String version, String description, String author, ImageIcon icon) {
		this.name = name;
		this.version = version;
		this.description = description;
		this.author = author;
		this.icon = icon;
	}

	/**
	 * Note that this is the display name, like "Test Plugin", but the working
	 * name is "TestPlugin"
	 * 
	 * @return Name of the plugin
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * 
	 * @return Plugin version
	 */
	public String getVersion() {
		return this.version;
	}

	/**
	 * 
	 * @return Plugin description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * 
	 * @return Author of plugin
	 */
	public String getAuthor() {
		return this.author;
	}
	
	/**
	 * Returns the icon for this plugin (Can be null if no icon is specified)
	 * @return
	 */
	public ImageIcon getIcon() {
		return icon;
	}
	
	/**
	 * Called when the global menu item for this plugin is clicked
	 * 
	 * If returning null, will display information about the plugin instead
	 * @return
	 */
	public ActionListener getGlobalActionListener() {
		return null;
	}

	/**
	 * Returns a resource as a byte array from the current plugin archive
	 * @param resource path to the resource in the archive
	 * @return
	 */
	public final byte[] getResource(String resource) throws Exception {
		JarInputStream jis = new JarInputStream(new FileInputStream(new File(DEFAULT_PLUGIN_DIRECTORY, getName() + ".jar")));

		JarEntry entry;
		while ((entry = jis.getNextJarEntry()) != null) {
			if (entry.getName().equals(resource)) {
				ByteArrayOutputStream bais = new ByteArrayOutputStream(entry.getSize() == -1 ? 1024 : (int) entry.getSize());

				int read;
				byte[] b = new byte[1024];

				while ((read = jis.read(b)) != -1) {
					bais.write(b, 0, read);
				}

				jis.closeEntry();

				jis.close();
				bais.close();

				return bais.toByteArray();
			}
		}

		jis.close();

		return null;
	}

}
