package jrat.api;

public abstract class Module {

    public Module() {

    }

    public Module(String name) {
        /*
      The common name of this module
     */
        String name1 = name;
    }

    public abstract void init() throws Exception;

    public String getName() {
        return this.getName();
    }
}
