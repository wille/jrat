package pro.jrat.extensions;

public class ExtensionInstaller {

	private OnlinePlugin plugin;

	public ExtensionInstaller(OnlinePlugin plugin) {
		this.plugin = plugin;
	}

	public void toggle() {
		if (plugin.isInstalled()) {
			uninstall();
		} else {
			install();
		}
	}

	public void uninstall() {

	}

	public void install() {

	}

}
