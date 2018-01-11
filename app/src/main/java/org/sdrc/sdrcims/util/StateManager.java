package org.sdrc.sdrcims.util;

/**
 * Created by Harsh Pratyush on 10-01-2018.
 */

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.sdrc.sdrcims.model.UserDataModel;

public class StateManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";


    public static final String PASSWORD = "password";

    public static final String COOKIE = "cookie";

    public static final String USERTYPE = "userType";

    // Constructor
    public StateManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    public void createLoginSession(String email, String password,String cookie,String userType){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);

        // Storing password in pref
        editor.putString(PASSWORD, password);

        // Storing cookie
        editor.putString(COOKIE,cookie);

        //Storing user type
        editor.putString(USERTYPE,userType);

        // commit changes
        editor.commit();
    }


    public void updateCookie(String cookie)
    {
        editor.putString(COOKIE,cookie);

        // commit changes
        editor.commit();
    }

    public UserDataModel getLoginDetail()
    {
        UserDataModel userDataModel=new UserDataModel();
        userDataModel.setUserName(pref.getString(KEY_EMAIL,null));
        userDataModel.setPassword(pref.getString(PASSWORD,null));

        return userDataModel;
    }


    public String getCookie()
    {
        return pref.getString(COOKIE,null);
    }

}
