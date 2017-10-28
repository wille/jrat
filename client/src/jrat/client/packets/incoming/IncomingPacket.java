package jrat.client.packets.incoming;

import jrat.client.Connection;


public interface IncomingPacket {

    void read(Connection con) throws Exception;
}
