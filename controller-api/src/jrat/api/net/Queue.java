package jrat.api.net;

import jrat.api.Client;

public interface Queue {

	/**
	 * Add packet to queue, callback into client to add to *real* queue
	 * 
	 * @param packet
	 * @param rat
	 * @throws Exception
	 */
    void addToSendQueue(PacketBuilder packet, Client rat) throws Exception;

}
