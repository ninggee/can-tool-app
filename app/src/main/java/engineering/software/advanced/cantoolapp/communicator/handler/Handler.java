package engineering.software.advanced.cantoolapp.communicator.handler;

/**
 * Created by ningge on 11/10/2017.
 */

public abstract class Handler extends android.os.Handler {
    public abstract void handle(String message);
}
