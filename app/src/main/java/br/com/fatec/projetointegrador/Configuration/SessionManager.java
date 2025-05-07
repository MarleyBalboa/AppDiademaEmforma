package br.com.fatec.projetointegrador.Configuration;

import android.content.Context;
import android.content.SharedPreferences;

// SessionManager.java
public class SessionManager {
    private static final String PREF_NAME = "app_session";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public SessionManager(Context ctx) {
        prefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void saveUserId(int id) {
        editor.putInt(KEY_USER_ID, id);
        editor.apply();
    }

    public int getUserId() {
        return prefs.getInt(KEY_USER_ID, -1);
    }

    public void saveUserName(String name) {
        editor.putString(KEY_USER_NAME, name);
        editor.apply();
    }

    public String getUserName() {
        return prefs.getString(KEY_USER_NAME, null);
    }

    public boolean isLoggedIn() {
        return prefs.contains(KEY_USER_ID);
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}

