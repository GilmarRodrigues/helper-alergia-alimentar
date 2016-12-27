package br.com.eventoseartigos.sefd.converter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.List;

import br.com.eventoseartigos.sefd.model.Usuario;

/**
 * Created by gilmar on 17/09/16.
 */
public class UsuarioConverter {
    public static String converteUsuarioParaJson(Usuario usuario) {
        JSONStringer js = new JSONStringer();
        try {
            js.object();
            js.key("first_name").value(usuario.getFirstName());
            js.key("last_name").value(usuario.getLastName());
            js.key("cpf").value(usuario.getCpf());
            js.key("email").value(usuario.getEmail());
            js.key("password").value(usuario.getPassword());
            js.key("confirm_password").value(usuario.getPassword());
            js.endObject();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }

    public static String converteEmailParaString(String json) {
        try {
            //{"email":["Esse e-mail já está registrado."]}

            JSONObject root = new JSONObject(json);//{ }
            JSONArray array = root.getJSONArray("email");//"email":[]
            String email = array.optString(0);
            return email;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Usuario converteUsuarioParaString(String json) {
        try {
            JSONObject root = new JSONObject(json);
            Usuario u = new Usuario();
            u.setFirstName(root.optString("first_name"));
            u.setLastName(root.optString("last_name"));
            u.setCpf(root.optString("cpf"));

            return u;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
