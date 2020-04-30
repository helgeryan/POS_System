import java.util.EventObject;

public class LoginEvent extends EventObject {

    private int register;
    private String username;
    private String password;

    public LoginEvent(Object source, int Register, String UserName, String Password) {

        super(source);

        this.register = Register;
        this.username = UserName;
        this.password = Password;

    }

    public int getRegister() {
        return register;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
