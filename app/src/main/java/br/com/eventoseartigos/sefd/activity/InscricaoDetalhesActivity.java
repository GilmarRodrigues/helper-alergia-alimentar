package br.com.eventoseartigos.sefd.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.annotation.Transacao;
import br.com.eventoseartigos.sefd.dao.Prefs;
import br.com.eventoseartigos.sefd.fragment.Dialog.HorarioDialog;
import br.com.eventoseartigos.sefd.model.Atividade;
import br.com.eventoseartigos.sefd.model.AtividadeGeral;
import br.com.eventoseartigos.sefd.model.DocumentosEnviados;
import br.com.eventoseartigos.sefd.model.DocumentosNecessarios;
import br.com.eventoseartigos.sefd.model.Horario;
import br.com.eventoseartigos.sefd.model.Inscricao;
import br.com.eventoseartigos.sefd.model.Minicurso;
import br.com.eventoseartigos.sefd.model.Palestras;
import br.com.eventoseartigos.sefd.service.DocumentosNecessarioService;
import br.com.eventoseartigos.sefd.utils.SDCardUtils;
import id.zelory.compressor.Compressor;

import static br.com.eventoseartigos.sefd.utils.FormatUtils.formtDate;
import static br.com.eventoseartigos.sefd.utils.FormatUtils.formtHora;

public class InscricaoDetalhesActivity extends BaseActivity implements Transacao {
    private Inscricao mInscricao;
    private Spinner spinner_documentos_necessarios;
    private String documentoPK;
    private File file;
    private String token;
    private DocumentosEnviados docEnviado;
    private int CAMERA_REQUEST = 1;
    private CardView card_view_necessario;
    private CardView card_view_enviados;
    private LinearLayout layout_documentos_enviados;
    private LinearLayout layout_atividades_gerais;
    private TextView text_foto;
    private List<DocumentosEnviados> mEnviadosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscricao_detalhes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spinner_documentos_necessarios = (Spinner) findViewById(R.id.spinner_documento_necessario);
        findViewById(R.id.btn_confirmar).setOnClickListener(onClickConfirmar());
        findViewById(R.id.btn_foto).setOnClickListener(onClickFoto());
        card_view_necessario = (CardView) findViewById(R.id.card_view_necessario);
        card_view_enviados = (CardView) findViewById(R.id.card_view_enviados);
        layout_documentos_enviados = (LinearLayout) findViewById(R.id.layout_documentos_enviados);
        layout_atividades_gerais = (LinearLayout) findViewById(R.id.layout_atividades_gerais);
        text_foto = (TextView) findViewById(R.id.text_foto);

        token = Prefs.getString(this, "token");

        mInscricao = getIntent().getParcelableExtra(Inscricao.KEY);
        if (mInscricao != null) {
            setInscricao();
            getSupportActionBar().setTitle(mInscricao.getEdicao());
        }

        setProgress(findViewById(R.id.progress));
        setFormView(findViewById(R.id.layout_progress));

        if (savedInstanceState != null) {
            file = (File) savedInstanceState.getSerializable("file");
            text_foto.setVisibility(View.VISIBLE);
            text_foto.setText(file.toString());
        }

    }

    private View.OnClickListener onClickFoto() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(InscricaoDetalhesActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(InscricaoDetalhesActivity.this, new String[]{Manifest.permission.CAMERA}, 123);
                } else {
                    file = SDCardUtils.getPrivateFile(InscricaoDetalhesActivity.this.getBaseContext(), System.currentTimeMillis() + ".jpg", Environment.DIRECTORY_PICTURES);
                    Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    startActivityForResult(intentCamera, CAMERA_REQUEST);
                }
            }
        };
    }

    private View.OnClickListener onClickConfirmar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInscricao.getPk() != null && documentoPK != null && file != null && token != null) {
                    startTrasacao(InscricaoDetalhesActivity.this);

                } else {
                    Toast.makeText(InscricaoDetalhesActivity.this, "Foto invalida", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    @Override
    public void executar() throws Exception {
        docEnviado = DocumentosNecessarioService.setDocumento(mInscricao.getPk(), documentoPK, file, token);
    }

    @Override
    public void atualizarView() {
        //teste -> !docEnviado.getPk().equals("")
        if (!docEnviado.getPk().equals("")) {
            int pk = Integer.parseInt(docEnviado.getPk());
            if (docEnviado.getPk() != null && pk > 0) {
                mEnviadosList.add(docEnviado);
                text_foto.setVisibility(View.GONE);
                setDocumentosEnviados();
                setDocumentoNecessario();
                Toast.makeText(this, "Enviado com sucesso", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK && file != null) {
            //showImage
            this.file = Compressor.getDefault(this).compressToFile(file);
            text_foto.setVisibility(View.VISIBLE);
            text_foto.setText(file.toString());
        }
    }

    private void setInscricao() {
        setMinicursos();
        setAtividades();
        setPalestras();
        setAtividadesGerais();
        setDocumentoNecessario();
        setDocumentosEnviados();
    }

    private void setMinicursos() {
        List<Minicurso> minicursos = mInscricao.getMinicursos();
        LinearLayout layout_minicuros = (LinearLayout) findViewById(R.id.layout_minicursos);
        CardView card_view_minicursos = (CardView) findViewById(R.id.card_view_minicursos);

        if (minicursos != null && minicursos.size() > 0) {
            for (int i = 0; i < minicursos.size(); i++) {
                layout_minicuros.addView(setTextView(minicursos.get(i).getMinicurso()));
                layout_minicuros.addView(setTextView(minicursos.get(i).getProfissional()));
                layout_minicuros.addView(setTextViewHorario(minicursos.get(i).getHorarios()));
                if (i + 1 != minicursos.size()) {
                    layout_minicuros.addView(setView());
                }

            }
            card_view_minicursos.setVisibility(View.VISIBLE);
        } else {
            card_view_minicursos.setVisibility(View.GONE);
        }
    }

    private void setAtividades() {
        List<Atividade> atividades = mInscricao.getAtividades();
        LinearLayout layout_atividades = (LinearLayout) findViewById(R.id.layout_atividades);
        CardView card_view_atividades = (CardView) findViewById(R.id.card_view_atividades);

        if (atividades != null && atividades.size() > 0) {
            for (int i = 0; i < atividades.size(); i++) {
                layout_atividades.addView(setTextView(atividades.get(i).getAtividade()));

                String notNull = atividades.get(i).getDescricao();
                if (!notNull.trim().equals("null")) {
                    layout_atividades.addView(setTextView(atividades.get(i).getDescricao()));
                }

                layout_atividades.addView(setTextViewHorario(atividades.get(i).getHorarios()));

                if (i + 1 != atividades.size()) {
                    layout_atividades.addView(setView());
                }

            }
            card_view_atividades.setVisibility(View.VISIBLE);
        } else {
            card_view_atividades.setVisibility(View.GONE);
        }
    }

    private void setPalestras() {
        List<Palestras> palestrasList = mInscricao.getPalestras();
        LinearLayout layout_palestras = (LinearLayout) findViewById(R.id.layout_palestras);
        CardView card_view_palestras = (CardView) findViewById(R.id.card_view_palestras);
        if (palestrasList != null && palestrasList.size() > 0) {
            for (int i = 0; i < palestrasList.size(); i++) {

                layout_palestras.addView(setTextView(palestrasList.get(i).getNome()));
                layout_palestras.addView(setTextView(palestrasList.get(i).getProfissional()));
                layout_palestras.addView(setTextView(formtDate(palestrasList.get(i).getData()) + " às " + formtHora(palestrasList.get(i).getHora())));
                if (i + 1 != palestrasList.size()) {
                    layout_palestras.addView(setView());
                }
            }
            card_view_palestras.setVisibility(View.VISIBLE);
        }else {
            card_view_palestras.setVisibility(View.GONE);
        }
    }

    public void setAtividadesGerais() {
        List<AtividadeGeral> atividadesGerais = mInscricao.getAtividades_gerais();
        if (atividadesGerais != null && atividadesGerais.size() > 0) {
            for (AtividadeGeral a : atividadesGerais) {

                layout_atividades_gerais.addView(setTextView(a.getDescricao()));
                layout_atividades_gerais.addView(setTextView(formtDate(a.getData())));
                layout_atividades_gerais.addView(setTextView(formtHora(a.getInicio()) + " às " + formtHora(a.getTermino())));

                layout_atividades_gerais.addView(setView());
            }
        }
        else {
            CardView card_view_atividades_gerais = (CardView) findViewById(R.id.card_view_atividades_gerais);
            card_view_atividades_gerais.setVisibility(View.GONE);
        }
    }

    private void setDocumentoNecessario() {
        final List<DocumentosNecessarios> necessariosList = mInscricao.getDocumentos_necessarios();
        if (necessariosList != null && necessariosList.size() > 0) {
            String[] documentos = new String[necessariosList.size()];
            for (int i = 0; i < necessariosList.size(); i++) {
                documentos[i] = necessariosList.get(i).getNome();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, documentos);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            spinner_documentos_necessarios.setAdapter(adapter);

            spinner_documentos_necessarios.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    documentoPK = necessariosList.get(position).getPk();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            card_view_necessario.setVisibility(View.VISIBLE);
        } else {
            card_view_necessario.setVisibility(View.GONE);
        }
    }

    private void setDocumentosEnviados() {
        layout_documentos_enviados.removeAllViews();
        mEnviadosList = mInscricao.getDocumentos_enviados();
        if (mEnviadosList != null && mEnviadosList.size() > 0) {
            for (DocumentosEnviados enviado: mEnviadosList) {
                layout_documentos_enviados.addView(setTextView(enviado.getNome_tipo()));
                layout_documentos_enviados.addView(setTextView("Válido: " + valido(enviado.getValido())));
            }
            card_view_enviados.setVisibility(View.VISIBLE);
        } else {
            card_view_enviados.setVisibility(View.GONE);
        }
    }

    private View setView() {
        View view = new View(this);
        LinearLayout.LayoutParams ln = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        view.setBackgroundColor(getResources().getColor(R.color.divider));
        view.setLayoutParams(ln);
        return view;
    }

    private TextView setTextView(final String atributo) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(4, 7, 0, 7);
        final TextView textView = new TextView(this);
        textView.setText(atributo);
        textView.setLayoutParams(params);
        return textView;
    }

    private TextView setTextViewHorario(List<Horario> horarios) {
        TextView button = setTextView("Vizualizar Horário");
        button.setTextColor(getResources().getColor(R.color.accent));
        button.setOnClickListener(onClickHorario(horarios));
        return button;
    }

    private View.OnClickListener onClickHorario(final List<Horario> horario) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HorarioDialog.show(getSupportFragmentManager(), horario);
            }
        };
    }

    private String valido (String valido) {
        if (valido.equals("false")) {
            valido = "Não conferido";
        } else {
            valido = "Conferido";
        }
        return valido;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("file", file);
    }
}
