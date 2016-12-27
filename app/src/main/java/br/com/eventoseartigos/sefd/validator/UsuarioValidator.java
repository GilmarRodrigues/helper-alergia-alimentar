package br.com.eventoseartigos.sefd.validator;

import android.text.Editable;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;

import br.com.eventoseartigos.sefd.converter.UsuarioConverter;
import br.com.eventoseartigos.sefd.utils.MaskUtils;

/**
 * Created by gilmar on 18/09/16.
 */
public class UsuarioValidator extends Validator {

    private static final String ERROR_EMAIL_JA_EXISTE = "{\"email\":[\"Esse e-mail já está registrado.\"]}" ;

    public static boolean validateCPF(String CPF) {
        CPF = MaskUtils.unmask(CPF);
        if (CPF.equals("00000000000") || CPF.equals("11111111111")
                || CPF.equals("22222222222") || CPF.equals("33333333333")
                || CPF.equals("44444444444") || CPF.equals("55555555555")
                || CPF.equals("66666666666") || CPF.equals("77777777777")
                || CPF.equals("88888888888") || CPF.equals("99999999999")) {
            return false;
        }
        char dig10, dig11;
        int sm, i, r, num, peso;
        try {
            sm = 0;
            peso = 10;
            for (i = 0; i < 9; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else
                dig10 = (char) (r + 48);
            sm = 0;
            peso = 11;
            for (i = 0; i < 10; i++) {
                num = (int) (CPF.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso - 1;
            }
            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig11 = '0';
            else
                dig11 = (char) (r + 48);
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                return (true);
            else
                return (false);
        } catch (Exception erro) {
            return (false);
        }
    }

    public static boolean valitadeEmail(String email) {
        if (TextUtils.isEmpty(email)) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }

    public static boolean validatePassword(String password) {
        return password.length() > 7;
    }

    public static boolean validatorEmailExiste(String json) {
        if (json.equals(ERROR_EMAIL_JA_EXISTE)) {
            return true;
        }
        return false;
    }
}
