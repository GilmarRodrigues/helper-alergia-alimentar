package br.com.eventoseartigos.sefd.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.annotation.Transacao;
import br.com.eventoseartigos.sefd.dao.Prefs;
import br.com.eventoseartigos.sefd.fragment.Dialog.DefinirNomeCertificadoDialog;
import br.com.eventoseartigos.sefd.model.Certificado;
import br.com.eventoseartigos.sefd.service.CertificadoService;

import static br.com.eventoseartigos.sefd.R.id.tv_disponivel;

public class CertificadoActivity extends BaseActivity  implements Transacao{
    private Certificado mCertificado;
    private TextView tv_nome;
    private Button btn_certificado;
    private String token;
    String urlCertificado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certificado);

        btn_certificado = (Button) findViewById(R.id.btn_certificado);
        token = Prefs.getString(this, "token");

        mCertificado = getIntent().getParcelableExtra(Certificado.KEY);
        if (mCertificado != null) {
            setCertificado(mCertificado);
            btn_certificado.setOnClickListener(onClickAbrirCertificado());
        }
    }

    private View.OnClickListener onClickAbrirCertificado() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTrasacaoSemProgress(CertificadoActivity.this);
            }
        };
    }

    private void setCertificado(Certificado certificado) {
        TextView tv_tipo = (TextView) findViewById(R.id.tv_tipo);
        TextView tv_evento = (TextView) findViewById(R.id.tv_evento);
        TextView tv_turma_palestra = (TextView) findViewById(R.id.tv_turma_palestra);
        tv_nome = (TextView) findViewById(R.id.tv_nome);
        TextView tv_disponivel = (TextView) findViewById(R.id.tv_disponivel);

        tv_tipo.setText(certificado.getTipo());
        tv_evento.setText(certificado.getEvento());

        //
        String turma_palestra = certificado.getTurma()+certificado.getPalestra();
        if (!TextUtils.isEmpty(turma_palestra)) {
            tv_turma_palestra.setText(turma_palestra);
            tv_turma_palestra.setVisibility(View.VISIBLE);
        }

        String nome_certificado = certificado.getNome_certificado();

        if (TextUtils.isEmpty(nome_certificado)) {
             tv_nome.setText(nome_certificado);
        } else {
            tv_nome.setOnClickListener(onClickDefinirNomeCertificado());
            tv_nome.setText("+ Definir nome no certificado");
            tv_nome.setTextColor(getResources().getColor(R.color.accent));
        }

        String disponivel = certificado.getDisponivel();
        if (disponivel.equals("true")) {
            tv_disponivel.setText("Status: Disponível");
            btn_certificado.setVisibility(View.VISIBLE);
        } else {
            tv_disponivel.setText("Status: Indisponível");
            btn_certificado.setVisibility(View.GONE);
        }
    }

    private View.OnClickListener onClickDefinirNomeCertificado() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefinirNomeCertificadoDialog.show(getSupportFragmentManager(), mCertificado, new DefinirNomeCertificadoDialog.Callback() {
                    @Override
                    public void onClickNomeDefinido(Certificado certificado) {
                        mCertificado = certificado;
                        tv_nome.setText(certificado.getNome_certificado());
                        tv_nome.setTextColor(getResources().getColor(R.color.secondary_text));
                        tv_nome.setOnClickListener(null);
                    }
                });
            }
        };
    }

    @Override
    public void executar() throws Exception {
        urlCertificado = CertificadoService.geraCertificado(token, mCertificado.getToken());

    }

    @Override
    public void atualizarView() {
        //String substring = codigo.substring(0, codigo.length() - 1).substring(0);
        //substring = substring.substring(1);
        //Log.i("Script", substring);
        //String url = "data:application/pdf;base64,"+codigo.substring(0, codigo.length() - 1).substring(0);
        //String replace = url.replace("{codigo}", codigo);
        Intent i = new Intent(Intent.ACTION_VIEW);
        //i.setData(Uri.parse(urlCertificado));
        i.setDataAndType(Uri.parse(urlCertificado), "text/html");
        startActivity(i);
    }
}
