package br.com.eventoseartigos.sefd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.activity.InscricaoActivity;
import br.com.eventoseartigos.sefd.activity.InscricaoDetalhesActivity;
import br.com.eventoseartigos.sefd.adapter.EventosAdapter;
import br.com.eventoseartigos.sefd.adapter.InscricaoAdapter;
import br.com.eventoseartigos.sefd.annotation.Transacao;
import br.com.eventoseartigos.sefd.dao.Prefs;
import br.com.eventoseartigos.sefd.model.Evento;
import br.com.eventoseartigos.sefd.model.Inscricao;
import br.com.eventoseartigos.sefd.model.ListInscricao;
import br.com.eventoseartigos.sefd.service.InscricaoService;

public class InscricoesFragment extends BaseFragment implements Transacao {
    private String token;
    private RecyclerView mRecyclerView;
    private List<Inscricao> mInscricaos;
    private InscricaoAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inscricoes, container, false);

        token = Prefs.getString(getContext(), "token");

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);

        setProgress(view.findViewById(R.id.progress));
        setFormView(view.findViewById(R.id.layout_progress));

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            ListInscricao list = savedInstanceState.getParcelable(ListInscricao.KEY);
            this.mInscricaos = list.inscricaos;
        }

        if (mInscricaos != null) {
            atualizarView();
        } else {
            startTrasacao(this);
        }
    }

    @Override
    public void executar() throws Exception {
        mInscricaos = InscricaoService.getInscricao(token);
    }

    @Override
    public void atualizarView() {
        carregarLista();
    }

    private void carregarLista() {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(llm);

        mAdapter = new InscricaoAdapter(mInscricaos, getActivity(), onClickIncricao());
        mRecyclerView.setAdapter(mAdapter);
    }

    private InscricaoAdapter.OnClickListener onClickIncricao() {
        return new InscricaoAdapter.OnClickListener() {
            @Override
            public void onClick(View v, int idx) {
                Intent intent = new Intent(getActivity(), InscricaoDetalhesActivity.class);
                intent.putExtra(Inscricao.KEY, mInscricaos.get(idx));
                startActivity(intent);
            }
        };
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ListInscricao.KEY, new ListInscricao(mInscricaos));
    }
}
