package br.com.eventoseartigos.sefd.activity;

import android.os.Bundle;
import android.renderscript.Script;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.annotation.Transacao;
import br.com.eventoseartigos.sefd.converter.InscricaoConverter;
import br.com.eventoseartigos.sefd.dao.Prefs;
import br.com.eventoseartigos.sefd.fragment.Dialog.MinicursoAtividadeDialog;
import br.com.eventoseartigos.sefd.model.Atividade;
import br.com.eventoseartigos.sefd.model.AtividadeGeral;
import br.com.eventoseartigos.sefd.model.Evento;
import br.com.eventoseartigos.sefd.model.Grupos;
import br.com.eventoseartigos.sefd.model.Minicurso;
import br.com.eventoseartigos.sefd.model.Palestras;
import br.com.eventoseartigos.sefd.model.TipoInscricao;
import br.com.eventoseartigos.sefd.service.AtividadesGeraisService;
import br.com.eventoseartigos.sefd.service.GruposService;
import br.com.eventoseartigos.sefd.service.InscricaoService;
import br.com.eventoseartigos.sefd.service.PalestrasService;
import br.com.eventoseartigos.sefd.service.TipoInscricaoService;

import static br.com.eventoseartigos.sefd.R.id.card_view_manha;
import static br.com.eventoseartigos.sefd.R.id.card_view_palestras;
import static br.com.eventoseartigos.sefd.R.id.card_view_tarde;
import static br.com.eventoseartigos.sefd.R.id.layout_atividades_gerais;

public class InscricaoActivity extends BaseActivity implements Transacao {
    private static final String TAG = "InscricaoActivity";
    private Evento mEvento;
    private List<AtividadeGeral> mAtividadesGerais;
    private List<TipoInscricao> mTipoInscricaos;
    private List<Palestras> mPalestras;
    private List<Grupos> mGruposList;
    private String token;
    private Spinner spinner_tipo_inscricao;
    private TextView tv_valor_total;
    private LinearLayout layout_palestras;
    private LinearLayout layout_manha;
    private LinearLayout layout_tarde;
    private LinearLayout layout_atividades_gerais;
    private CheckBox checkBox;
    private Double valorTotal;
    private Double precoMinicurso;
    private Double precoAtividade;
    private Double precoPalestras;
    private HashMap<String, Double> mValorHashMap;
    private boolean palestrasCheckBox;
    private Minicurso minicursoManha;
    private Minicurso minicursoTarde;
    private Atividade atividadeManha;
    private Atividade atividadeTarde;
    private TipoInscricao tipoInscricao;
    private String response;
    private boolean flag = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscricao);

        spinner_tipo_inscricao = (Spinner) findViewById(R.id.spinner_tipo_inscrcao);
        layout_palestras = (LinearLayout) findViewById(R.id.layout_palestras);
        tv_valor_total = (TextView) findViewById(R.id.tv_valor_total);
        layout_manha = (LinearLayout) findViewById(R.id.layout_manha);
        layout_tarde = (LinearLayout) findViewById(R.id.layout_tarde);
        layout_atividades_gerais = (LinearLayout) findViewById(R.id.layout_atividades_gerais);
        findViewById(R.id.btn_confirmar).setOnClickListener(onClickConfirmar());

        mEvento = getIntent().getParcelableExtra(Evento.KEY);
        if (mEvento != null) {
            //setEvento(mEvento);
        }

        token = Prefs.getString(this, "token");

        setProgress(findViewById(R.id.progress));
        setFormView(findViewById(R.id.layout_progress));
        startTrasacao(this);

        setValorList();

        valorTotal = 0.0;
    }

    private View.OnClickListener onClickConfirmar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = false;
                startTrasacao(InscricaoActivity.this);
                /*if (minicursosEnviar().size() > 0 || atividadesEnviar().size() > 0 || palestrasCheckBox == true) {
                    startTrasacao(InscricaoActivity.this);
                } else {
                    Toast.makeText(InscricaoActivity.this, getString(R.string.erro_verfiqueque_opcoes), Toast.LENGTH_SHORT).show();
                }*/
            }
        };
    }

    @Override
    public void executar() throws Exception {
        if (flag) {
            mTipoInscricaos = TipoInscricaoService.getIncricao(token, mEvento.getPk());
            mPalestras = PalestrasService.getPalestras(token, mEvento.getPk());
            mGruposList = GruposService.getGrupos(token, mEvento.getPk());
            mAtividadesGerais = AtividadesGeraisService.getAtividadesGerais(token, mEvento.getPk());

        } else {
            List<Minicurso> minicursoList = minicursosEnviar();
            List<Atividade> atividadeList = atividadesEnviar();
            response = InscricaoService.setInscricao(tipoInscricao, minicursoList, atividadeList, palestrasCheckBox, token);
            //InscricaoService.setInscricao(token);
        }
    }

    @Override
    public void atualizarView() {
        if (flag) {
            setTipoInscricao(mTipoInscricaos);
            setPalestras(mPalestras);
            setGrupos(mGruposList);
            setAtividadesGerais(mAtividadesGerais);
        } else {
            Toast.makeText(InscricaoActivity.this, getString(R.string.msg_cadastrado_sucesso), Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void setGrupos(List<Grupos> gruposList) {
        CardView card_view_tarde = (CardView) findViewById(R.id.card_view_tarde);
        CardView card_view_manha = (CardView) findViewById(R.id.card_view_manha);
        if (gruposList != null && gruposList.size() > 0) {
            for (int i = 0; i < gruposList.size(); i++) {
                if (gruposList.get(i).getNome().equals("Manhã")) {
                    setMinicursoAtividade(gruposList, i, layout_manha, "Manhã");
                } else {
                    card_view_manha.setVisibility(View.GONE);
                }

                if (gruposList.get(i).getNome().equals("Tarde")) {
                    setMinicursoAtividade(gruposList, i, layout_tarde, "Tarde");
                } else {
                    card_view_tarde.setVisibility(View.GONE);
                }
            }
        }
        else {
            card_view_manha.setVisibility(View.GONE);
            card_view_tarde.setVisibility(View.GONE);
        }
    }

    private void setPalestras(List<Palestras> palestrasList) {
        if (palestrasList != null && palestrasList.size() > 0) {
            for (Palestras p : mPalestras) {

                layout_palestras.addView(setTextView(p.getNome(), "Palestras", p));
                layout_palestras.addView(setTextView(p.getProfissional(), "Palestras", p));
                layout_palestras.addView(setTextView(formtDate(p.getData()) + " às " + formtHora(p.getHora()), "Palestras", p));
                layout_palestras.addView(setView());
            }
            checkBox = new CheckBox(this);
            checkBox.setText("Desejo participar das palestras");
            checkBox.setOnClickListener(OnClickPalestras());
            layout_palestras.addView(checkBox);
        }
        else {
            CardView card_view_palestras = (CardView) findViewById(R.id.card_view_palestras);
            card_view_palestras.setVisibility(View.GONE);
        }
    }

    public void setAtividadesGerais(List<AtividadeGeral> atividadesGerais) {
        if (atividadesGerais != null && atividadesGerais.size() > 0) {
            for (AtividadeGeral a : atividadesGerais) {

                layout_atividades_gerais.addView(setTextView(a.getDescricao(), "AtividadeGeral", a));
                layout_atividades_gerais.addView(setTextView(formtDate(a.getData()), "AtividadeGeral", a));
                layout_atividades_gerais.addView(setTextView(formtHora(a.getInicio()) + " às " + formtHora(a.getTermino()), "AtividadeGeral", a));

                layout_atividades_gerais.addView(setView());
            }
        }
        else {
            CardView card_view_atividades_gerais = (CardView) findViewById(R.id.card_view_atividades_gerais);
            card_view_atividades_gerais.setVisibility(View.GONE);
        }
    }

    private View.OnClickListener OnClickPalestras() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                palestrasCheckBox = checkBox.isChecked();
                if (palestrasCheckBox) {
                    updateValorHashMap("palestrasCheckBox", precoPalestras);
                } else {
                    updateValorHashMap("palestrasCheckBox", 0.0);
                }
                updateValorTotal();
            }
        };
    }

    private void setTipoInscricao(final List<TipoInscricao> tipoInscricaoList) {
        if (tipoInscricaoList != null || tipoInscricaoList.size() > 0) {
            final String[] tipoIncricao = new String[tipoInscricaoList.size()];
            for (int i = 0; i < tipoInscricaoList.size(); i++) {
                tipoIncricao[i] = tipoInscricaoList.get(i).getNome();
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tipoIncricao);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            spinner_tipo_inscricao.setAdapter(adapter);

            spinner_tipo_inscricao.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    precoMinicurso = Double.parseDouble(tipoInscricaoList.get(position).getPreco_minicurso());
                    precoAtividade = Double.parseDouble(tipoInscricaoList.get(position).getPreco_atividade());
                    precoPalestras = Double.parseDouble(tipoInscricaoList.get(position).getPreco_palestra());
                    updateRecauculaValores(precoMinicurso, precoAtividade, precoPalestras);
                    updateValorTotal();
                    tipoInscricao = tipoInscricaoList.get(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    private void setMinicursoAtividade(final List<Grupos> gruposList, final int i, LinearLayout layout, String manhaOuTarde) {
        RadioGroup group = new RadioGroup(this);
        RadioButton buttonNull = new RadioButton(this);
        buttonNull.setText("Nenhum");
        buttonNull.setOnCheckedChangeListener(onClickMinicursoAtividadeNull(manhaOuTarde));

        group.addView(buttonNull);
        for (final Minicurso minicurso : gruposList.get(i).getMinicursos()) {
            RadioButton button = setRarioButton(minicurso.getMinicurso(), "Minicurso", minicurso, manhaOuTarde);
            group.addView(button);
        }

        for (final Atividade atividade : gruposList.get(i).getAtividades()) {
            RadioButton button = setRarioButton(atividade.getAtividade(), "Atividade", atividade, manhaOuTarde);
            group.addView(button);
        }

        layout.addView(group);
    }

    private CompoundButton.OnCheckedChangeListener onClickMinicursoAtividadeNull(final String manhaOuTarde) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (manhaOuTarde == "Manhã") {
                    updateValorHashMap("minicursoManha", 0.0);
                    updateValorHashMap("atividadeManha", 0.0);
                } else if (manhaOuTarde == "Tarde") {
                    updateValorHashMap("minicursoTarde", 0.0);
                    updateValorHashMap("atividadeTarde", 0.0);
                }
                updateValorTotal();
            }
        };
    }

    private RadioButton setRarioButton(final String atributo, final String tipo, final Object object, String manhaOuTarde) {
        RadioButton button = new RadioButton(this);
        button.setText(atributo);
        button.setOnClickListener(onClickRadioButton(tipo, object, manhaOuTarde));
        return button;
    }

    private View.OnClickListener onClickRadioButton(final String tipo, final Object object, final String manhaOuTarde) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MinicursoAtividadeDialog.show(getSupportFragmentManager(), object, tipo, manhaOuTarde, new MinicursoAtividadeDialog.Callback() {
                    @Override
                    public void onClick(Object o, String manhaOuTarde) {
                        if (o != null) {
                            saveMinicursoOuAtividade(object, tipo, manhaOuTarde);
                            updateValorTotal();
                        }
                    }
                });
            }
        };
    }

    private void saveMinicursoOuAtividade(Object o, String tipo, String manhaOuTarde) {
        if (manhaOuTarde == "Manhã") {
            if (tipo == "Minicurso") {
                minicursoManha = (Minicurso) o;
                atividadeManha = null;
                updateValorHashMap("minicursoManha", precoMinicurso);
                updateValorHashMap("atividadeManha", 0.0);
            } else if (tipo == "Atividade") {
                atividadeManha = (Atividade) o;
                minicursoManha = null;
                updateValorHashMap("atividadeManha", precoAtividade);
                updateValorHashMap("minicursoManha", 0.0);
            }
        } else if (manhaOuTarde == "Tarde") {
            if (tipo == "Minicurso") {
                minicursoTarde = (Minicurso) o;
                atividadeTarde = null;
                updateValorHashMap("minicursoTarde", precoMinicurso);
                updateValorHashMap("atividadeTarde", 0.0);
            } else if (tipo == "Atividade") {
                atividadeTarde = (Atividade) o;
                minicursoTarde = null;
                updateValorHashMap("atividadeTarde", precoAtividade);
                updateValorHashMap("minicursoTarde", 0.0);
            }
        }
    }

    private View setView() {
        View view = new View(this);
        LinearLayout.LayoutParams ln = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        view.setBackgroundColor(getResources().getColor(R.color.divider));
        view.setLayoutParams(ln);
        return view;
    }

    private TextView setTextView(final String atributo, final String tipo, final Object object) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(4, 7, 0, 7);
        final TextView textView = new TextView(this);
        textView.setText(atributo);
        textView.setLayoutParams(params);
        return textView;
    }

    private String formtDate(String date) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date data = fmt.parse(date);
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            String str = format.format(data);
            return str;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String formtHora(String hora) {
        try {
            SimpleDateFormat fmt = new SimpleDateFormat("HH:mm:ss");
            Date time = fmt.parse(hora);
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            String str = format.format(time);
            return str;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateValorTotal() {
        for (String key : mValorHashMap.keySet()) {
            Double value = mValorHashMap.get(key);
            valorTotal = valorTotal + value;
        }

        tv_valor_total.setText(valorTotal.toString());
        valorTotal = 0.0;
    }

    private void setValorList() {
        Double value = 0.0;
        mValorHashMap = new HashMap<>();
        mValorHashMap.put("palestrasCheckBox", value);
        mValorHashMap.put("minicursoManha", value);
        mValorHashMap.put("atividadeManha", value);
        mValorHashMap.put("minicursoTarde", value);
        mValorHashMap.put("atividadeTarde", value);
    }

    private void updateValorHashMap(String key, Double value) {
        if (mValorHashMap.containsKey(key)) {
            mValorHashMap.put(key, value);
        }
    }

    private void updateRecauculaValores(Double precoMinicurso, Double precoAtividade, Double precoPalestras) {
        for (String key : mValorHashMap.keySet()) {
            Double value = mValorHashMap.get(key);
            if (value > 0.0) {
                switch (key) {
                    case "palestrasCheckBox":
                        mValorHashMap.put(key, precoPalestras);
                        break;
                    case "minicursoManha":
                        mValorHashMap.put(key, precoMinicurso);
                        break;
                    case "atividadeManha":
                        mValorHashMap.put(key, precoAtividade);
                        break;
                    case "minicursoTarde":
                        mValorHashMap.put(key, precoMinicurso);
                        break;
                    case "atividadeTarde":
                        mValorHashMap.put(key, precoAtividade);
                        break;
                    default:
                        break;

                }
            }
        }
    }

    private List<Minicurso> minicursosEnviar() {
        List<Minicurso> minicursos = new ArrayList<>();
        if (minicursoManha != null) {
            minicursos.add(minicursoManha);
        }
        if (minicursoTarde != null) {
            minicursos.add(minicursoTarde);
        }

        return minicursos;
    }

    private List<Atividade> atividadesEnviar() {
        List<Atividade> atividades = new ArrayList<>();
        if (atividadeManha != null) {
            atividades.add(atividadeManha);
        }
        if (atividadeTarde != null) {
            atividades.add(atividadeTarde);
        }
        return atividades;
    }
}
