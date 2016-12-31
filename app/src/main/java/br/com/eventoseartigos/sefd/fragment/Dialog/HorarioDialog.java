package br.com.eventoseartigos.sefd.fragment.Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.model.Horario;
import br.com.eventoseartigos.sefd.model.ListHorario;

import static br.com.eventoseartigos.sefd.R.id.layout_horarios;
import static br.com.eventoseartigos.sefd.utils.FormatUtils.formtDate;
import static br.com.eventoseartigos.sefd.utils.FormatUtils.formtHora;

/**
 * Created by gilmar on 30/12/16.
 */

public class HorarioDialog extends BaseFragmentDialog {
    private List<Horario> mHorarios;

    public static void show(FragmentManager fm, List<Horario> horarios) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("horario_dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        HorarioDialog frag = new HorarioDialog();
        Bundle args = new Bundle();
        args.putParcelable(ListHorario.KEY, new ListHorario(horarios));
        frag.setArguments(args);
        frag.show(fm, "horario_dialog");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_horario, container, false);

        LinearLayout layout_horario = (LinearLayout) view.findViewById(layout_horarios);

        view.findViewById(R.id.btn_ok).setOnClickListener(onClickOk());

        ListHorario list = getArguments().getParcelable(ListHorario.KEY);
        mHorarios = list.horarios;
        if (mHorarios != null) {
            setHorarios(mHorarios, layout_horario);
        }

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

    private void setHorarios(List<Horario> horarios, LinearLayout layout_horarios) {
        if (horarios != null || horarios.size() > 0) {
            for (Horario h: horarios) {
                layout_horarios.addView(setTextView("Data: " + formtDate(h.getData())));
                layout_horarios.addView(setTextView("Início: " + formtHora(h.getInicio())));
                layout_horarios.addView(setTextView("Término: " + formtHora(h.getTermino())));
                layout_horarios.addView(setView());
                layout_horarios.addView(setView());
            }
        }
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
        final TextView textView = new TextView(getActivity());
        textView.setText(atributo);
        textView.setLayoutParams(params);
        return textView;
    }
}
