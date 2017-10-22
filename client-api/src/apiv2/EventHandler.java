package apiv2;

import java.util.HashMap;
import java.util.Map;

public class EventHandler {

    public static final Map<Short, Listener> map = new HashMap<Short, Listener>();

    public static void registerListener(short opcode, Listener l) {
        map.put(opcode, l);
    }
}
