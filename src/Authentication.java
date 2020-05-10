import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Authentication {
    private static Boolean authStatus = false;


    public Authentication() throws FileNotFoundException {
    }

    public static boolean Authenticate(POS_System pos_system, int registerID, User user) throws FileNotFoundException {
        Register register;
        UserList userList = new UserList();
        int registerIndex = 0;
        ArrayList<User> users = new ArrayList<User>();

        users = userList.getUserList();

        for (int i = 0; i < users.size(); i++) {
            if ((users.get(i).getUsername().equals(user.getUsername()) && users.get(i).getPassword().equals(user.getPassword()) )&& (users.get(i).getStatus())){
                System.out.println("You have been authenticated");
                boolean registerExists = false;
                for(int y = 0; y< pos_system.getRegisters().size(); y++){
                    if(pos_system.getRegisters().get(y).getId() == registerID){
                        pos_system.getRegisters().get(y).setCurrUser(users.get(i));
                        registerIndex = y;
                        registerExists = true;
                        break;
                    }
                }
                if(!registerExists){
                    registerIndex = pos_system.getRegisters().size();
                    register = new Register();
                    register.setId(registerID);
                    register.setCurrUser(users.get(i));
                    pos_system.addRegister(register);
                }

                new MainFrame(pos_system, registerIndex);
                authStatus = true;
                break;
            }
        }

        if(!authStatus){
            System.out.println("The credentials you entered are incorrect.");
        }

        return authStatus;
    }
}