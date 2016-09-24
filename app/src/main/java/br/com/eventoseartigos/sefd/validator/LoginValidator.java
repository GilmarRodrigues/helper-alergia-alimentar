package br.com.eventoseartigos.sefd.validator;

import br.com.eventoseartigos.sefd.converter.LoginConverter;

/**
 * Created by gilmar on 18/09/16.
 */
public class LoginValidator extends Validator{

    public static final String LOGIN_INVALIDO = "{\"non_field_errors\":[\"Impossível fazer login com as credenciais fornecidas.\"]}";

    public static boolean valitadeEmail(String email) {
        return UsuarioValidator.valitadeEmail(email);
    }

    public static boolean validatePassword(String password) {
        return UsuarioValidator.validatePassword(password);
    }

    public static boolean validateUsernamePasswordInvalidos(String json) {
        if(json.equals(LOGIN_INVALIDO)) {
            return true;
        }
        return false;
    }
}
