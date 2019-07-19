package ar.com.wolox.android.training.utils;

import javax.inject.Inject;

import ar.com.wolox.wolmo.core.di.scopes.ApplicationScope;
import ar.com.wolox.wolmo.core.util.SharedPreferencesManager;

/**
 * CredntialsSession stores userData on sharedPreferences
 */
@ApplicationScope
public class CredentialsSession {

    private final String userKey = "USERNAME";
    private final String passKey = "PASSWORD";

    private String user;
    private String pass;
    private SharedPreferencesManager manager;

    @Inject
    public CredentialsSession(SharedPreferencesManager sharedPreferencesManager) {
        manager = sharedPreferencesManager;
        user = sharedPreferencesManager.get(userKey, "");
        pass = sharedPreferencesManager.get(passKey, "");
    }

    public String getUsername() {
        return user;
    }

    public void setUsername(final String userId) {
        if (manager != null) {
            manager.store(userKey, userId);
        }
    }

    public String getPassword() {
        return pass;
    }

    public void setPassword(final String passId) {
        if (manager != null) {
            manager.store(passKey, passId);
        }
    }

    public void clearCredentials() {
        manager.clearKey(userKey);
        manager.clearKey(passKey);
    }
}
