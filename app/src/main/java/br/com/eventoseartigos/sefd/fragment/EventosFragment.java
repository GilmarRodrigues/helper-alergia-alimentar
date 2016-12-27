package br.com.eventoseartigos.sefd.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.adapter.EventosAdapter;
import br.com.eventoseartigos.sefd.annotation.Transacao;
import br.com.eventoseartigos.sefd.dao.Prefs;
import br.com.eventoseartigos.sefd.model.Evento;
import br.com.eventoseartigos.sefd.model.ListEventos;
import br.com.eventoseartigos.sefd.service.EventosService;

public class EventosFragment extends BaseFragment implements Transacao{
    private String token;
    private List<Evento> mEventos;
    private RecyclerView mRecyclerView;
    private EventosAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventos, container, false);

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
        carregarLista();
    }

    private void carregarLista() {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(llm);

        mAdapter = new EventosAdapter(mEventos, getActivity(), onClickEvento());
        mRecyclerView.setAdapter(mAdapter);
    }

    private EventosAdapter.OnClickListener onClickEvento() {
        return new EventosAdapter.OnClickListener() {
            @Override
            public void onClick(View view, int idx) {
                Toast.makeText(getActivity(), mEventos.get(idx).getNome(), Toast.LENGTH_SHORT).show();
            }
        };
    }

}
