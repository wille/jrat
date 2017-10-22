package apiv2;

import java.io.DataInputStream;

public abstract class Listener {

    public abstract void emit(DataInputStream dis);
}
