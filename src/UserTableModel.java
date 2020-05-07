import javax.swing.table.AbstractTableModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserTableModel extends AbstractTableModel {

    ArrayList<User> users = new ArrayList<>();
    private String[] colNames = {"Employee ID", "First Name", "Last Name", "Role", "Username", "Status"};


    public UserTableModel() throws FileNotFoundException {
        setData();
    }

    public void setData() throws FileNotFoundException {
        users.clear();
        Scanner row = new Scanner(new File("src/users.txt"));
        while(row.hasNextLine()){
            String rowParse = row.nextLine();
            Scanner sc = new Scanner(String.valueOf(rowParse));
            sc.useDelimiter(",");

            User userToAdd = new User(Integer.parseInt(sc.next()),sc.next(),sc.next(),sc.next(), sc.next(), sc.next(), sc.nextBoolean());
            users.add(userToAdd);
        }
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }

    @Override
    public Object getValueAt(int row, int col) {

        User user = users.get(row);

        switch(col) {
            case 0:
                return user.getEmpID();
            case 1:
                return user.getFirstName();
            case 2:
                return user.getLastName();
            case 3:
                return user.getPosition();
            case 4:
                return user.getUsername();
            case 5:
                return user.getStatus();
        }
        return null;
    }
}
