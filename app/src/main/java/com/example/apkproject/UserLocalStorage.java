package com.example.apkproject;

import android.content.Context;
import android.content.SharedPreferences;

public class UserLocalStorage {

    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStorage(Context context)
    {
        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void StoreUserData(User user)
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();

        spEditor.putString("login", user.login);
        spEditor.putString("password", user.password);

        spEditor.apply();
    }

    public User GetLoggedUser()
    {
        String login = userLocalDatabase.getString("login", "");
        String password = userLocalDatabase.getString("password", "");
        return new User(login, password);
    }

    public boolean GetUserLoggedIn()
    {
        return userLocalDatabase.getBoolean("loggedIn", false);
    }

    public void SetUserLoggedIn(boolean loggedIn)
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn", loggedIn);
        spEditor.apply();
    }

    public void ClearUserData()
    {
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.apply();
    }


}
