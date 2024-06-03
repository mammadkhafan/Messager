package Server;

public class User {
    private String name;
    private String password;

    
    public User(String name) {
        setName(name);
    }

    public User(String name, String password) {
        this(name);
        setPassword(password);
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
