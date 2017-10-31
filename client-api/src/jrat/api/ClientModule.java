package jrat.api;

public abstract class ClientModule {

    /**
     * Called when the module is first initialized.
     * If an exception is thrown, the module will be considered disabled.
     * @throws Exception
     */
    public abstract void init() throws Exception;
}
