package br.com.eventoseartigos.sefd.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.annotation.Transacao;
import br.com.eventoseartigos.sefd.model.Login;
import br.com.eventoseartigos.sefd.model.Usuario;
import br.com.eventoseartigos.sefd.service.LoginService;
import br.com.eventoseartigos.sefd.service.UsuarioService;
import br.com.eventoseartigos.sefd.utils.MaskUtils;
import br.com.eventoseartigos.sefd.validator.UsuarioValidator;
import br.com.eventoseartigos.sefd.validator.Validator;

public class UsuarioFormActivity extends BaseActivity implements Transacao{
    private static final String TAG = "UsuarioFormActivity";
    private EditText campo_nome;
    private EditText campo_sobrenome;
    private EditText campo_cpf;
    private EditText campo_email;
    private EditText campo_password;
    private boolean emailExiste;
    private Usuario mUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_form);

        campo_nome = (EditText) findViewById(R.id.et_first_name);
        campo_sobrenome = (EditText) findViewById(R.id.et_last_name);
        campo_cpf = (EditText) findViewById(R.id.et_cpf);
        campo_cpf.addTextChangedListener(MaskUtils.insert("###.###.###-##", campo_cpf));
        campo_email = (EditText) findViewById(R.id.et_email);
        campo_password = (EditText) findViewById(R.id.et_password);

        findViewById(R.id.salvar_button).setOnClickListener(onClickSalvarFormulario());
        findViewById(R.id.tv_entrar).setOnClickListener(onClickEntrar());

        setFormView(findViewById(R.id.usuario_form_scroll));
        setProgress(findViewById(R.id.form_usuario_progress));
    }

    private View.OnClickListener onClickEntrar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        };
    }

    private View.OnClickListener onClickSalvarFormulario() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validator()) {
                    startTrasacao(UsuarioFormActivity.this);
                }
            }
        };
    }

    @Override
    public void executar() throws Exception {

        mUsuario = new Usuario();
        mUsuario.setFirstName(campo_nome.getText().toString());
        mUsuario.setLastName(campo_sobrenome.getText().toString());
        mUsuario.setCpf(campo_cpf.getText().toString());
        mUsuario.setEmail(campo_email.getText().toString());
        mUsuario.setPassword(campo_password.getText().toString());

        String json = UsuarioService.setUsuario(mUsuario);
        emailExiste = UsuarioValidator.validatorEmailExiste(json);
        if (!emailExiste) {
            // Faz login(automaticamente) depois que se cadastrar...
            Login login = new Login(mUsuario.getEmail(), mUsuario.getPassword());
            String token = LoginService.getLogin(login);
        }

    }

    @Override
    public void atualizarView() {
        if (emailExiste) {
            campo_email.setError(getString(R.string.error_email_ja_registardo));
            campo_email.setFocusable(true);
            campo_email.requestFocus();
        } else {
            Toast.makeText(this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean validator() {
        campo_nome.setError(null);
        campo_sobrenome.setError(null);
        campo_cpf.setError(null);
        campo_email.setError(null);
        campo_password.setError(null);

        boolean nome_valido = UsuarioValidator.validateNotNull(campo_nome, getString(R.string.error_field_required));
        if (!nome_valido) {
            return true;
        }
        boolean sobrenome_valido = UsuarioValidator.validateNotNull(campo_sobrenome, getString(R.string.error_field_required));
        if (!sobrenome_valido) {
            return true;
        }
        boolean cpf_valido_not_null = UsuarioValidator.validateNotNull(campo_cpf, getString(R.string.error_field_required));
        if (!cpf_valido_not_null) {
            return true;
        }
        boolean cpf_valido = UsuarioValidator.validateCPF(campo_cpf.getText().toString());
        if (!cpf_valido) {
            campo_cpf.setError(getString(R.string.error_invalid_cpf));
            campo_cpf.setFocusable(true);
            campo_cpf.requestFocus();
            return true;
        }
        boolean email_valido = UsuarioValidator.valitadeEmail(campo_email.getText().toString());
        if (!email_valido) {
            campo_email.setError(getString(R.string.error_invalid_email));
            campo_email.setFocusable(true);
            campo_email.requestFocus();
            return true;
        }
        boolean password_valido = UsuarioValidator.validatePassword(campo_password.getText().toString().trim());
        if (!password_valido) {
            campo_password.setError(getString(R.string.error_invalid_password));
            campo_password.setFocusable(true);
            campo_password.requestFocus();
            return true;
        }

        return false;
    }



}
