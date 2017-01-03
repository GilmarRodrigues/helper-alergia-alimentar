package br.com.eventoseartigos.sefd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.annotation.Transacao;
import br.com.eventoseartigos.sefd.converter.LoginConverter;
import br.com.eventoseartigos.sefd.dao.Prefs;
import br.com.eventoseartigos.sefd.model.Login;
import br.com.eventoseartigos.sefd.service.LoginService;
import br.com.eventoseartigos.sefd.validator.LoginValidator;

public class LoginActivity extends BaseActivity implements  Transacao {
    private AutoCompleteTextView campo_email;
    private EditText campo_password;
    private String token = null;
    private String messageErrorLoginInvalid = null;
    private Login mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String tokenPrefs = Prefs.getString(this, Login.TOKEN);

        if (!TextUtils.isEmpty(tokenPrefs)) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(Login.TOKEN, tokenPrefs);
            startActivity(intent);
            finish();
        }
        
        setContentView(R.layout.activity_login);

        campo_email = (AutoCompleteTextView) findViewById(R.id.et_email);

        campo_password = (EditText) findViewById(R.id.et_password);

        findViewById(R.id.email_sign_in_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                logar();
            }
        });

        findViewById(R.id.tv_criar_conta).setOnClickListener(onClickCriarConta());

        setFormView(findViewById(R.id.login_form));
        setProgress(findViewById(R.id.login_progress));
    }

    private OnClickListener onClickCriarConta() {
        return new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, UsuarioFormActivity.class));
            }
        };
    }

    private void logar() {
        if (!validator()) {
            startTrasacao(this);
        }
    }

    private boolean validator() {
        campo_email.setError(null);
        campo_password.setError(null);
        boolean email_valido_not_null = LoginValidator.validateNotNull(campo_email, getString(R.string.error_field_required));
        if (!email_valido_not_null) {
            return true;
        }
        boolean email_valido = LoginValidator.valitadeEmail(campo_email.getText().toString());
        if (!email_valido) {
            campo_email.setError(getString(R.string.error_invalid_email));
            campo_email.setFocusable(true);
            campo_email.requestFocus();
            return true;
        }
        boolean password_valido_not_null = LoginValidator.validateNotNull(campo_password, getString(R.string.error_field_required));
        if (!password_valido_not_null) {
            return true;
        }
        boolean password_valido = LoginValidator.validatePassword(campo_password.getText().toString());
        if (!password_valido) {
            campo_password.setError(getString(R.string.error_invalid_password));
            campo_password.setFocusable(true);
            campo_password.requestFocus();
            return true;
        }
        return false;
    }


    @Override
    public void executar() throws Exception {
        try {
            String json = null;
            mLogin = new Login();
            mLogin.setUserName(campo_email.getText().toString());
            mLogin.setPassword(campo_password.getText().toString());

            json = LoginService.getLogin(mLogin);
            if (json != null) {
                if (json.equals(LoginValidator.LOGIN_INVALIDO)) {
                    messageErrorLoginInvalid = LoginConverter.converteMessageErrorParaString(json);
                    Prefs.setString(this, Login.TOKEN, null);
                } else {
                    token = LoginConverter.converteTokenParaString(json);
                    Prefs.setString(this, Login.TOKEN, token);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void atualizarView() {
        if (token == null) {
            Toast.makeText(this, messageErrorLoginInvalid, Toast.LENGTH_SHORT).show();
        } else {
            mLogin.setToken(token);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(Login.KEY, mLogin);
            startActivity(intent);
            finish();
        }
    }

}

