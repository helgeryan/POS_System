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
            if (users.get(i).getUsername().equals(user.getUsername()) && users.get(i).getPassword().equals(user.getPassword())){
                System.out.println("You have been authenticated");
                register.setCurrUser(users.get(i));

                //adding dummy sales to test transaction ID on the MainFrame GUI
                register.setId(registerID);
                register.newSale();
                register.closeSale();
                register.newSale();
                register.closeSale();

                //new MainFrame(pos_system, register);
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