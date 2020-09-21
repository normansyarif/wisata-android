package com.si.wisatadestinasi.Database;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    private static Preferences mInstance;
    private Context mContext;
    private SharedPreferences prefAuth, prefUser;

    private Preferences(){ }


    // ------ Return this class, so another class may access the method.


    public static Preferences getInstance(){
        if (mInstance == null) mInstance = new Preferences();
        return mInstance;
    }


    // ------ Initialize. Only at first Activity.


    public void Initialize(Context context){
        mContext = context;
        prefUser = context.getSharedPreferences("user", 0);
    }


    // ----- Write using Editor


    public void write(SharedPreferences pref, String key, String val){
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, val);
        editor.commit();
    }


    //  -------
    //  ------- One Preference One Write Method
    //  -------


    public void writeUser(String key, String val) {
        write(prefUser, key, val);
    }


    //  -------
    // -------- Get Value
    //  -------



    public String getNama() {
        return prefUser.getString("nama", null);
    }

    public SharedPreferences getUser() {
        return prefUser;
    }



}
