package ar.com.wolox.android.training.utils;

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

    private String user;
    private String pass;
    private SharedPreferencesManager manager;

    @Inject
    public CredentialsSession(SharedPreferencesManager sharedPreferencesManager) {
        manager = sharedPreferencesManager;
        user = sharedPreferencesManager.get(USER_KEY, null);
        pass = sharedPreferencesManager.get(PASS_KEY, null);
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

    public void clearCredentials() {
        user = "";
        pass = "";
        manager.clearKey(USER_KEY);
        manager.clearKey(PASS_KEY);
    }
}
