import java.util.EventObject;

public class LoginEvent extends EventObject {

    private String username;
    private String password;

    public LoginEvent(Object source, String UserName, String Password) {

        super(source);

        this.username = UserName;
        this.password = Password;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
