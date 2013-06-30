package pro.jrat.extractor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Extractor {

	public static final String URL = "https://jrat.pro/downloads/jRAT.zip";

	public static void extract(File outputDir, Frame frame) {
		try {
			if (!outputDir.exists()) {
				frame.log("Creating directory...");
				outputDir.mkdirs();
			}

			frame.log("Creating temp file...");
			File temp = File.createTempFile("temp_jrat", ".zip");

			frame.log("Connecting...");

			HttpURLConnection urlConnection = (HttpURLConnection) new URL(URL).openConnection();
			urlConnection.connect();

			InputStream is = urlConnection.getInputStream();

			FileOutputStream fos = new FileOutputStream(temp);

			int length = urlConnection.getContentLength();

			byte[] buffer = new byte[1024];

			int totalRead = 0;
			int i;

			frame.log("Downloading archive to " + temp.getAbsolutePath());

			while ((i = is.read(buffer)) != -1) {
				totalRead += i;
				frame.getProgressBar().setValue(Utils.getPercentFromTotal(totalRead, length));
				frame.getStatusLabel().setText("Downloading " + (totalRead / 1024) + "/" + (length / 1024) + " kB");
				fos.write(buffer, 0, i);
			}

			fos.close();

			frame.log("Extracting archive...");

			ZipFile zip = new ZipFile(temp);

			Enumeration<? extends ZipEntry> entriesEnum = zip.entries();
			List<ZipEntry> entries = new ArrayList<ZipEntry>();

			while (entriesEnum.hasMoreElements()) {
				entries.add(entriesEnum.nextElement());
			}

			for (i = 0; i < entries.size(); i++) {
				frame.getProgressBar().setValue(Utils.getPercentFromTotal(i, entries.size()));
				ZipEntry entry = entries.get(i);

				File output = new File(outputDir, entry.getName());

				if (entry.getName().endsWith("/")) {
					frame.log("Creating directory " + output.getAbsolutePath() + "...");
					output.mkdirs();
					continue;
				}

				frame.log("Extracting " + entry.getName());
				
				fos = new FileOutputStream(output);
				is = zip.getInputStream(entry);

				totalRead = 0;
				int read = 0;

				while ((read = is.read(buffer)) != -1) {
					totalRead += read;
					fos.write(buffer, 0, read);
				}
				fos.close();
			}

			zip.close();
			
			frame.log("Deleting temporary archive...");

			temp.delete();
			
			frame.log("Successfully installed jRAT");
			
			Frame.path = outputDir;
			
			frame.getButton().setText("Launch jRAT");
			
			frame.getButton().setEnabled(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
