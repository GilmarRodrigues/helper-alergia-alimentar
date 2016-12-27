package br.com.eventoseartigos.sefd.service;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import br.com.eventoseartigos.sefd.converter.UsuarioConverter;
import br.com.eventoseartigos.sefd.model.Usuario;
import br.com.eventoseartigos.sefd.utils.HttpUtils;

/**
 * Created by gilmar on 17/09/16.
 */
public class UsuarioService {
    private static final String URL = "http://api-sefd.ifpicos.com.br/evento/participante/";

    public static String setUsuario(Usuario usuario) throws IOException{
        String json = UsuarioConverter.converteUsuarioParaJson(usuario);
        String respostaJson = HttpUtils.doPost(URL, json);
        return respostaJson;
    }

    public static Usuario getUsuario(String token) {
        try {
            String json = HttpUtils.doGet(URL, token);
            Usuario usuario = UsuarioConverter.converteUsuarioParaString(json);
            return usuario;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String update(Usuario usuario, String token) throws IOException{
        String json = UsuarioConverter.converteUsuarioParaJson(usuario);
        String respostaJson = HttpUtils.doPut(URL, json, token);
        return respostaJson;
    }
}
