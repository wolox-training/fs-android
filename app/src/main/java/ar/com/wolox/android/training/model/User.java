package ar.com.wolox.android.training.model;

/**
 * User model, contains credentials from logged person
 */
public class User {

    private String email;
    private String password;
    private String username;
    private int id;

    /**
     * @param email email to login
     * @param password password to login
     */
    public User(String email, String password) {

        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
