import java.io.FileNotFoundException;
import java.util.EventListener;

public interface LoginListener extends EventListener {

    public void loginEventOccured(LoginEvent le) throws FileNotFoundException;
}
