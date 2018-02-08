package io.jrat.controller.net;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebRequest {

    public static URL getUrl(String surl) throws Exception {
        return getUrl(surl, false);
    }

    public static URL getUrl(String turl, boolean ignoreask) throws Exception {
        return new URL(turl);
    }

    public static HttpURLConnection getConnection(String surl) throws Exception {
        return getConnection(surl, false);
    }

    public static HttpURLConnection getConnection(String surl, boolean ignoreask) throws Exception {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {

                    }

                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {

                    }
                }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }
        URL url = getUrl(surl, ignoreask);

        HttpURLConnection connection = null;

        connection = (HttpURLConnection) url.openConnection();

        connection.setReadTimeout(2500);
        connection.connect();

        return connection;
    }
}
