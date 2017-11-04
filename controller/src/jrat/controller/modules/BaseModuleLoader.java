package jrat.controller.modules;

import java.io.File;

public abstract class BaseModuleLoader {

    protected File file;

    public BaseModuleLoader(File file) {
        this.file = file;
    }

    public abstract void load() throws Exception;
}
