package ar.com.wolox.android.training.model;

/**
 * User model, contains credentials from logged person
 */
public class User {

    private String user;
    private String pass;

    /**
     * @param user username to login (can be null)
     * @param pass password to login (can be null)
     */
    public User(String user, String pass) {

        if (user != null) {
            this.user = user;
        } else {
            this.user = "";
        }

        if (pass != null) {
            this.pass = pass;
        } else {
            this.pass = "";
        }
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

    public boolean isValid() {
        return !user.isEmpty() && !pass.isEmpty();
    }
}
