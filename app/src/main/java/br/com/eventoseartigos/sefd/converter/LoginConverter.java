package br.com.eventoseartigos.sefd.converter;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import br.com.eventoseartigos.sefd.model.Login;

/**
 * Created by gilmar on 14/09/16.
 */
public class LoginConverter {
    public static String converteUsuarioParaJson(Login login) {
        JSONStringer js = new JSONStringer();
        try {
            js.object();
            js.key("username").value(login.getUserName());
            js.key("password").value(login.getPassword());
            js.endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }

    public static String converteTokenParaString(String json) {
        JSONObject jo = null;
        String token = null;
        try {
            jo = new JSONObject(json);
            token = jo.optString("token");
            return token;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String converteMessageErrorParaString(String json) {
        try {
            JSONObject root = new JSONObject(json);
            JSONArray array = root.getJSONArray("non_field_errors");
            String messageError = array.optString(0);
            return messageError;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}