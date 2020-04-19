import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Authentication {
    private static Boolean authStatus = false;

    public Authentication() throws FileNotFoundException {
    }

    public static boolean Authenticate(User user) throws FileNotFoundException {

        File creds = new File("C:\\dev\\IdeaWorkspace\\POSProject\\POSAuthentication\\src\\creds.txt");
        Scanner sc = new Scanner(creds);

        ArrayList<User> users = new ArrayList<User>();

        int i = 0;
        while (sc.hasNext()) {
            users.add(i, new User(sc.next(), sc.next()));
            i++;
        }

        for (i = 0; i < users.size(); i++) {

            if (users.get(i).getUsername().equals(user.getUsername()) && users.get(i).getPassword().equals(user.getPassword())){
                System.out.println("You have been authenticated");
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