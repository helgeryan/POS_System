import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Authentication {
    private static Boolean authStatus = false;


    public Authentication() throws FileNotFoundException {
    }

    public static boolean Authenticate(POS_System pos_system, int registerID, User user) throws FileNotFoundException {
        Register register = new Register();
        UserList userList = new UserList();

        ArrayList<User> users = new ArrayList<User>();

        users = userList.getUserList();

        for (int i = 0; i < users.size(); i++) {
            if ((users.get(i).getUsername().equals(user.getUsername()) && users.get(i).getPassword().equals(user.getPassword()) )&& (users.get(i).getStatus())){
                System.out.println("You have been authenticated");
                register.setId(registerID);
                register.setCurrUser(users.get(i));
                pos_system.addRegister(register);
                int registerIndex = 0;
                for(int y = 0; y < pos_system.getRegisters().size(); y++){
                    if(pos_system.getRegisters().get(y).getId() == registerID){
                        registerIndex = y;
                    }
                }
                new MainFrame(pos_system, registerIndex);
                authStatus = true;
                break;
            }
        }

        if(authStatus == false){
            System.out.println("The credentials you entered are incorrect.");
        }

        return authStatus;
    }
}