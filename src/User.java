public class User {
    int empID;
    String firstName;
    String lastName;
    String position;
    String username;
    String password;

    User(int EmpID, String FirstName, String LastName, String Position, String Username, String Password) {
        this.empID = EmpID;
        this.firstName = FirstName;
        this.lastName = LastName;
        this.position = Position;
        this.username = Username;
        this.password = Password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public String getfirstName() {
        return firstName;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return empID + ","+ firstName + "," + lastName + "," +  position + "," +  username  + "," +  password;
    }
}
