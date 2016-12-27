package br.com.eventoseartigos.sefd.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.activity.PerfilActivity;
import br.com.eventoseartigos.sefd.annotation.Transacao;
import br.com.eventoseartigos.sefd.dao.Prefs;
import br.com.eventoseartigos.sefd.model.Evento;
import br.com.eventoseartigos.sefd.model.ListEventos;
import br.com.eventoseartigos.sefd.model.Login;
import br.com.eventoseartigos.sefd.service.EventosService;

public class EventosFragment extends BaseFragment implements Transacao{
    private String token;
    private List<Evento> mEventos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

        token = Prefs.getString(getContext(), "token");

        setProgress(view.findViewById(R.id.progress));
        setFormView(view.findViewById(R.id.layout_progress));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            ListEventos list = savedInstanceState.getParcelable(ListEventos.KEY);
            this.mEventos = list.eventos;
        }
        if (mEventos != null) {
            atualizarView();
        } else {
            startTrasacao(this);
        }
    }

    @Override
    public void executar() throws Exception {
        mEventos = EventosService.getEventos(token);
    }

    @Override
    public void atualizarView() {
        carregaLista();
    }

    private void carregaLista() {

    }
}
