public class User {
    private int id;
    private String name;
    private String username;
    private String password;
    private String position;

    User(String username, String password){
        this.username = username;
        this.password = password;
    }

    User(int id, String name, String position, String username, String password) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.username = username;
        this.password = password;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", username=" + username + ", password=" + password + "]";
    }
}
