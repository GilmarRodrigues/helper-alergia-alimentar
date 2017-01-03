package br.com.eventoseartigos.sefd.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.activity.CertificadoActivity;
import br.com.eventoseartigos.sefd.adapter.CertificadoAdapter;
import br.com.eventoseartigos.sefd.annotation.Transacao;
import br.com.eventoseartigos.sefd.dao.Prefs;
import br.com.eventoseartigos.sefd.model.Certificado;
import br.com.eventoseartigos.sefd.model.ListCertificado;
import br.com.eventoseartigos.sefd.service.CertificadoService;


public class CertificadosFragment extends BaseFragment implements Transacao{
    private String token;
    private List<Certificado> mCertificados;
    private RecyclerView mRecyclerView;
    private CertificadoAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_certificados, container, false);

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
            ListCertificado list = savedInstanceState.getParcelable(ListCertificado.KEY);
            mCertificados  = list.certificados;
        }
        if (mCertificados != null) {
            atualizarView();
        } else {
            startTrasacao(this);
        }
    }

    @Override
    public void executar() throws Exception {
        mCertificados = CertificadoService.getCertificados(token, getContext());
    }

    @Override
    public void atualizarView() {
        carregarLista();
    }

    private void carregarLista() {
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(llm);

        mAdapter = new CertificadoAdapter(getContext(), mCertificados, onClickCertificado());
        mRecyclerView.setAdapter(mAdapter);
    }

    private CertificadoAdapter.OnClickListener onClickCertificado() {
        return new CertificadoAdapter.OnClickListener() {
            @Override
            public void onClick(View v, int idx) {
                Intent intent = new Intent(getActivity(), CertificadoActivity.class);
                intent.putExtra(Certificado.KEY, mCertificados.get(idx));
                startActivity(intent);
            }
        };
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ListCertificado.KEY, new ListCertificado(mCertificados));
    }
}
