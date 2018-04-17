package gbpec.comida;

/**
 * Created by Mohit Chauhan on 10/14/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    static Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "ComidaPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String NOTIFICATION_OFF = "ISNOTIFICATION";
    // User name (make variable public to access from outside)
    public static final String USER_ID="User_Id";
    public static final String USER_CONTACT = "User_Contact";
    public static final String USER_LATITUDE = "User_Latitude";
    public static final String USER_LONGITUDE= "User_Longitude";
    public static final String USER_NAME= "User_Name";
    public static final String USER_TYPE= "User_Type";
    public static final String USER_ADDRESS= "User_Address";
    public static final String USER_EMAIL= "User_Email";
    public static final String USER_IMG= "User_Image";










    // Email address (make variable public to access from outside)
    public static final String KEY_ID = "User_ID";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String name, String type){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(USER_CONTACT, name);

        // Storing email in pref
        editor.putString(USER_TYPE,type);




        // commit changes
        editor.commit();
    }
    public void createDataSessionNGO(String id,String name, String latitude, String longitude, String address, String email, String image){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(USER_ID,id);
        editor.putString(USER_NAME, name);
        editor.putString(USER_LATITUDE,latitude );
        editor.putString(USER_LONGITUDE, longitude);
        editor.putString(USER_ADDRESS, address);
        editor.putString(USER_EMAIL, email);

        // Storing email in pref
        editor.putString(USER_IMG,image);




        // commit changes
        editor.commit();
    }
    public void createDataSessionBUSINESS(String id,String name, String address, String email, String image){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(USER_ID,id);
        editor.putString(USER_NAME, name);
        editor.putString(USER_ADDRESS, address);
        editor.putString(USER_EMAIL, email);

        // Storing email in pref
        editor.putString(USER_IMG,image);




        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(USER_ID,pref.getString(USER_ID, null));
        user.put(USER_CONTACT, pref.getString(USER_CONTACT, null));
        user.put(USER_TYPE, pref.getString(USER_TYPE, null));
        user.put(USER_ADDRESS, pref.getString(USER_ADDRESS, null));
        user.put(USER_EMAIL, pref.getString(USER_EMAIL, null));
        user.put(USER_LATITUDE, pref.getString(USER_LATITUDE, null));
        user.put(USER_LONGITUDE, pref.getString(USER_LONGITUDE, null));
        user.put(USER_IMG, pref.getString(USER_IMG, null));


        // user email id
        user.put(USER_NAME, pref.getString(USER_NAME, null));

        // return user
        return user;
    }

    public boolean isNotification(){
        return pref.getBoolean(NOTIFICATION_OFF, false);
    }

    public void notificationOn(){
        // Storing login value as TRUE
        editor.putBoolean(NOTIFICATION_OFF, true);

        // Storing name in pref

        // commit changes
        editor.commit();
    }
    public void notificationOff(){
        // Storing login value as TRUE
        editor.putBoolean(NOTIFICATION_OFF, false);

        // Storing name in pref

        // commit changes
        editor.commit();
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
