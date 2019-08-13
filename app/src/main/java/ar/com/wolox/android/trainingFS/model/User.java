package ar.com.wolox.android.trainingFS.model;

/**
 * User model, contains credentials from logged person
 */
public class User {

    private String email;
    private String password;
    private String username;
    private int id;

    private String token;

    /**
     * @param email email to login
     * @param password password to login
     */
    public User(String email, String password) {

        this.email = email;
        this.password = password;
    }

    public User(String email) {
        this.email = email;
        this.password = "";
        this.token = "";
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
