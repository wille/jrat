package jrat.api;

import java.util.Random;

public abstract class Module {

    /**
     * The common name of this module
     */
    private String name;

    public Module() {

    }

    public Module(String name) {
        this.name = name;
    }

    public abstract void init() throws Exception;

    public String getName() {
        return this.getName();
    }
}
