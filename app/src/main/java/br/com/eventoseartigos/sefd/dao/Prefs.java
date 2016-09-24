package br.com.eventoseartigos.sefd.dao;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ObbInfo;

/**
 * Created by gilmar on 18/09/16.
 */
public class Prefs {
    public static final String PREF_ID = "sefd";

    public static void setString(Context context, String chave, String valor) {
        SharedPreferences pref = context.getSharedPreferences(PREF_ID, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(chave, valor);
        editor.commit();
    }

    public static String getString(Context context, String chave) {
        SharedPreferences pref = context.getSharedPreferences(PREF_ID, 0);
        String s = pref.getString(chave, "");
        return s;
    }
}
