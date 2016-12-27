package br.com.eventoseartigos.sefd.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.annotation.Transacao;
import br.com.eventoseartigos.sefd.dao.Prefs;
import br.com.eventoseartigos.sefd.model.ListUsuario;
import br.com.eventoseartigos.sefd.model.Login;
import br.com.eventoseartigos.sefd.model.Usuario;
import br.com.eventoseartigos.sefd.service.LoginService;
import br.com.eventoseartigos.sefd.service.UsuarioService;
import br.com.eventoseartigos.sefd.utils.MaskUtils;
import br.com.eventoseartigos.sefd.validator.UsuarioValidator;

public class PerfilActivity extends BaseActivity implements Transacao {
    private static final String TAG = "PerfilActivity";
    private EditText campo_nome;
    private EditText campo_sobrenome;
    private EditText campo_cpf;
    private EditText campo_password;
    private Usuario mUsuario;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        campo_nome = (EditText) findViewById(R.id.et_first_name);
        campo_sobrenome = (EditText) findViewById(R.id.et_last_name);
        campo_cpf = (EditText) findViewById(R.id.et_cpf);
        campo_cpf.addTextChangedListener(MaskUtils.insert("###.###.###-##", campo_cpf));
        campo_password = (EditText) findViewById(R.id.et_password);

        findViewById(R.id.salvar_button).setOnClickListener(onClickSalvarFormulario());

        setFormView(findViewById(R.id.usuario_form_scroll));
        setProgress(findViewById(R.id.form_usuario_progress));

        mUsuario = getIntent().getParcelableExtra(Usuario.KEY);
        if (mUsuario != null)
            setUsuario(mUsuario);

        token = Prefs.getString(this, "token");
    }


    private View.OnClickListener onClickSalvarFormulario() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validator()) {
                    startTrasacao(PerfilActivity.this);
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
        mUsuario.setPassword(campo_password.getText().toString());

        String json = UsuarioService.update(mUsuario, token);
    }

    @Override
    public void atualizarView() {
        Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_SHORT).show();
    }

    private void setUsuario(Usuario usuario) {
        campo_nome.setText(usuario.getFirstName());
        campo_sobrenome.setText(usuario.getLastName());
        campo_cpf.setText(usuario.getCpf());
    }

    private boolean validator() {
        campo_nome.setError(null);
        campo_sobrenome.setError(null);
        campo_cpf.setError(null);
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
