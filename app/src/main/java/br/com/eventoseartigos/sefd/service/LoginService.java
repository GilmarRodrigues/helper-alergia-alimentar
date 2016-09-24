package br.com.eventoseartigos.sefd.service;

import java.io.IOException;

import br.com.eventoseartigos.sefd.converter.LoginConverter;
import br.com.eventoseartigos.sefd.model.Login;
import br.com.eventoseartigos.sefd.utils.HttpUtils;

/**
 * Created by gilmar on 16/09/16.
 */
public class LoginService {
    private static final String URL = "http://api-sefd.ifpicos.com.br/api-token-auth/";

    public static String getLogin(Login login) throws IOException {
        /*String json = LoginConverter.converteUsuarioParaJson(login);
        String tokenJson = HttpUtils.doPost(URL, json);
        if (tokenJson != null) {
            String token = LoginConverter.converteTokenParaString(tokenJson);
            if (token != null) {
                return token;
            }
        }*/
        String loginJson = LoginConverter.converteUsuarioParaJson(login);
        String respostaJson = HttpUtils.doPost(URL, loginJson);
        if (respostaJson != null) {
            return respostaJson;
        }
        return null;
    }
}
