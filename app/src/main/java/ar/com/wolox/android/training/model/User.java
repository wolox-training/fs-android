package ar.com.wolox.android.training.model;

/**
 * User model, contains credentials from logged person
 */
public class User {

    private String user;
    private String pass;

    /**
     * @param user username to login
     * @param pass password to login
     */
    public User(String user, String pass) {

        this.user = user;
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
