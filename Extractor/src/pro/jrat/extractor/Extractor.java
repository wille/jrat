package pro.jrat.extractor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Extractor {
	
	public static void extract(Frame frame) {
		try {
			File temp = File.createTempFile("temp_jrat", ".zip");
			
			HttpURLConnection urlConnection = (HttpURLConnection) new URL("https://jrat.pro/downloads/jRAT.zip").openConnection();
			urlConnection.connect();
			
			InputStream is = urlConnection.getInputStream();
			
			FileOutputStream fos = new FileOutputStream(temp);
			
			int length = urlConnection.getContentLength();
			
			byte[] buffer = new byte[1024];
			
			int totalRead = 0;
			int i;
			
			while ((i = is.read(buffer)) != -1) {
				totalRead += i;
				frame.getProgressBar().setValue(Utils.getPercentFromTotal(totalRead, length));
				fos.write(buffer, 0, i);
			}
			
			fos.close();
			
			
			
			
			
			
			temp.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
