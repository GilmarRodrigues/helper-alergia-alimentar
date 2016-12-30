package br.com.eventoseartigos.sefd.fragment.Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.model.Atividade;
import br.com.eventoseartigos.sefd.model.Horario;
import br.com.eventoseartigos.sefd.model.Minicurso;

/**
 * Created by gilmar on 29/12/16.
 */

public class MinicursoAtividadeDialog extends DialogFragment {
    private Callback callback;
    private TextView tv_minicurso_atividade;
    private TextView tv_vagas;
    private TextView tv_profissional_descricao;
    private Minicurso mMinicurso;
    private Atividade mAtividade;
    private String tipo;
    private String manhaOuTarde;
    private LinearLayout layout_horarios;

    public static void show(FragmentManager fm, Object object, String tipo, String manhaOuTarde, Callback callback) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("minicurso_atividade_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        MinicursoAtividadeDialog frag = new MinicursoAtividadeDialog();
        frag.callback = callback;
        Bundle args = new Bundle();
        if (tipo == "Minicurso") {
            Minicurso mi = (Minicurso) object;
            args.putParcelable(Minicurso.KEY, (Minicurso) object);
            args.putString("tipo", tipo);
        } else if (tipo == "Atividade") {
            Atividade a = (Atividade) object;
            args.putParcelable(Atividade.KEY, (Atividade) object);
            args.putString("tipo", tipo);
        }
        args.putString("manhaOuTarde", manhaOuTarde);
        frag.setArguments(args);
        frag.show(fm, "minicurso_atividade_dialog");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_minicurso_atividade, container, false);

        tv_minicurso_atividade = (TextView) view.findViewById(R.id.tv_minicurso_atividade);
        tv_vagas = (TextView) view.findViewById(R.id.tv_vagas);
        tv_profissional_descricao = (TextView) view.findViewById(R.id.tv_profissional_descricao);
        layout_horarios = (LinearLayout) view.findViewById(R.id.layout_horarios);

        tipo = getArguments().getString("tipo");
        manhaOuTarde = getArguments().getString("manhaOuTarde");
        setMinicursoAtividade(tipo);

        view.findViewById(R.id.btn_ok).setOnClickListener(onClickOk());

        return view;
    }

    private View.OnClickListener onClickOk() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Object o = null;
        if (mMinicurso != null) {
            o = mMinicurso;
        } else if (mAtividade != null) {
            o = mAtividade;
        }

        if (callback != null) {
            callback.onClick(o, manhaOuTarde);
        }
    }

    private void setMinicursoAtividade(String tipo) {
        if (tipo == "Minicurso") {
            mMinicurso = getArguments().getParcelable(Minicurso.KEY);

            if (mMinicurso != null) {
                tv_minicurso_atividade.setText("Minicurso: " + mMinicurso.getMinicurso());
                tv_vagas.setText("Vagas: " + mMinicurso.getVagas());
                tv_profissional_descricao.setText("Profissional: " + mMinicurso.getProfissional());

                for (Horario h : mMinicurso.getHorarios()) {
                    setHorario(h);
                }
            }

        } else if (tipo == "Atividade") {
            mAtividade = getArguments().getParcelable(Atividade.KEY);

            if (mAtividade != null) {
                tv_minicurso_atividade.setText("Atividade: " + mAtividade.getAtividade());
                tv_vagas.setText("Vagas: " + mAtividade.getVagas());
                if (mAtividade.getDescricao().equals(null)) {
                    tv_profissional_descricao.setText("Descrição: " + mAtividade.getDescricao());
                } else {
                    tv_profissional_descricao.setVisibility(View.GONE);
                }

                for (Horario h : mAtividade.getHorarios()) {
                    setHorario(h);
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() == null) {
            return;
        }

        Window window = getDialog().getWindow();
        window.setBackgroundDrawableResource(android.R.color.white);
    }

    private View setView() {
        View view = new View(getActivity());
        LinearLayout.LayoutParams ln = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2);
        view.setBackgroundColor(getResources().getColor(R.color.divider));
        view.setLayoutParams(ln);
        return view;
    }

    private TextView setTextView(final String atributo) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(4, 7, 0, 7);
        TextView textView = new TextView(getActivity());
        textView.setText(atributo);
        textView.setLayoutParams(params);
        return textView;
    }

    private void setHorario(Horario h) {
        layout_horarios.addView(setTextView("Data: " + formtDate(h.getData())));
        layout_horarios.addView(setTextView("Início: " + formtHora(h.getInicio())));
        layout_horarios.addView(setTextView("Término: " + formtHora(h.getTermino())));
        layout_horarios.addView(setView());
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

    public static interface Callback {
        public void onClick(Object o, String manhaOuTarde);
    }
}
