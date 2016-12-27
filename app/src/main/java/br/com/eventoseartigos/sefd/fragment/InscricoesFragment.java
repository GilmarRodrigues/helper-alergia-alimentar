package br.com.eventoseartigos.sefd.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.annotation.Transacao;

public class InscricoesFragment extends BaseFragment implements Transacao {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inscricoes, container, false);
    }

    @Override
    public void executar() throws Exception {

    }

    @Override
    public void atualizarView() {

    }
}
