package ar.com.wolox.android.trainingFS.utils;

import javax.inject.Inject;

import ar.com.wolox.wolmo.core.di.scopes.ApplicationScope;
import ar.com.wolox.wolmo.core.util.SharedPreferencesManager;

/**
 * CredntialsSession stores userData on sharedPreferences
 */
@ApplicationScope
public class CredentialsSession {

    private static final String USER_KEY = "USERNAME";
    private static final String PASS_KEY = "PASSWORD";
    private static final String USER_ID_KEY = "ID";
    private static final String TOKEN_KEY = "TOKEN";

    private String user;
    private String pass;
    private int id;
    private String token;
    private SharedPreferencesManager manager;

    @Inject
    public CredentialsSession(SharedPreferencesManager sharedPreferencesManager) {
        manager = sharedPreferencesManager;
        user = sharedPreferencesManager.get(USER_KEY, null);
        pass = sharedPreferencesManager.get(PASS_KEY, null);
        id = sharedPreferencesManager.get(USER_ID_KEY, -1);
        token = sharedPreferencesManager.get(TOKEN_KEY, null);
    }

    public String getUsername() {
        if (user == null) {
            return manager.get(USER_KEY, null);
        } else {
            return user;
        }
    }

    public void setUsername(final String userId) {
        user = userId;
        manager.store(USER_KEY, userId);
    }

    public String getPassword() {
        if (pass == null) {
            return manager.get(PASS_KEY, null);
        } else {
            return pass;
        }
    }

    public void setPassword(final String passId) {
        pass = passId;
        if (manager != null) {
            manager.store(PASS_KEY, passId);
        }
    }

    public int getId() {
        if (id == -1) {
            return manager.get(USER_ID_KEY, -1);
        } else {
            return id;
        }
    }

    public void setId(int userId) {
        id = userId;
        if (manager != null) {
            manager.store(USER_ID_KEY, userId);
        }
    }

    public String getToken() {
        if (token == null) {
            return manager.get(TOKEN_KEY, null);
        } else {
            return token;
        }
    }

    public void setToken(String tokenKey) {
        token = tokenKey;
        if (manager != null) {
            manager.store(TOKEN_KEY, tokenKey);
        }
    }

    public void clearCredentials() {
        user = "";
        pass = "";
        id = -1;
        manager.clearKey(USER_KEY);
        manager.clearKey(PASS_KEY);
        manager.clearKey(USER_ID_KEY);
        manager.clearKey(TOKEN_KEY);
    }
}
