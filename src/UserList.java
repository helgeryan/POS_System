import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserList {
    ArrayList<User> users = new ArrayList<>();

    Scanner sc = new Scanner(new File("src\\users.txt"));

    UserList() throws FileNotFoundException {

        while (sc.hasNextLine()) {
            String row = sc.nextLine();
            Scanner rowParse = new Scanner(row);
            rowParse.useDelimiter(",");
            users.add(new User(rowParse.nextInt(),rowParse.next(),rowParse.next(),rowParse.next(), rowParse.next(), rowParse.next()));
        }
    }

    public void PrintUsers() {
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.get(i));
        }
    }

    public void saveUsersFile(User user) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter("C:/dev/SEIS602/POSUser/src/users.txt"));
        for(int i = 0; i < users.size(); i ++){
            String stringtoWrite = users.get(i).toString();
            writer.println(stringtoWrite);
        }
        writer.close();
    }

    public ArrayList<User> getUserList(){
        return users;
    }
}