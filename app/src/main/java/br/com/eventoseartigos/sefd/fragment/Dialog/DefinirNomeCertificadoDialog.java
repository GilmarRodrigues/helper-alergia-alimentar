package br.com.eventoseartigos.sefd.fragment.Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.annotation.Transacao;
import br.com.eventoseartigos.sefd.dao.Prefs;
import br.com.eventoseartigos.sefd.model.Certificado;
import br.com.eventoseartigos.sefd.service.DefinirNomeCertificadoService;
import br.com.eventoseartigos.sefd.task.TransacaoTask;
import br.com.eventoseartigos.sefd.utils.AndroidUtils;
import br.com.eventoseartigos.sefd.validator.DefinirNomeCertificadoValidator;

/**
 * Created by gilmar on 02/01/17.
 */

public class DefinirNomeCertificadoDialog extends BaseFragmentDialog implements Transacao {
    private Callback callback;;
    private TextView campo_nome;
    private View mProgressView, mFormView;
    private String token;
    private Certificado mCertificado;
    private String response;

    public static void show(FragmentManager fm, Certificado certificado , Callback callback) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("definir_nome_certificado");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DefinirNomeCertificadoDialog frag = new DefinirNomeCertificadoDialog();
        frag.callback = callback;
        Bundle args = new Bundle();
        args.putParcelable(Certificado.KEY, certificado);
        frag.setArguments(args);
        frag.show(fm, "definir_nome_certificado");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_definir_nome_certificado, container, false);

        campo_nome = (EditText) view.findViewById(R.id.et_nome);
        mProgressView = view.findViewById(R.id.progress);
        mFormView = view.findViewById(R.id.layout_progress);

        view.findViewById(R.id.btn_save).setOnClickListener(onClickConfirmar());
        view.findViewById(R.id.btn_cancelar).setOnClickListener(onClickCancelar());

        token = Prefs.getString(getContext(), "token");
        mCertificado = getArguments().getParcelable(Certificado.KEY);

        return view;
    }

    @Override
    public void executar() throws Exception {
        response = DefinirNomeCertificadoService.setNome(token, mCertificado.getNome_certificado(), mCertificado.getPk());
    }

    @Override
    public void atualizarView() {
        Toast.makeText(getActivity(), response, Toast.LENGTH_SHORT).show();
        dismiss();
    }

    private View.OnClickListener onClickConfirmar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validation()) {

                    mCertificado.setNome_certificado(campo_nome.getText().toString());
                    startTrasacao(DefinirNomeCertificadoDialog.this);

                    if (callback != null) {
                        callback.onClickNomeDefinido(mCertificado);
                    }
                }

            }
        };
    }

    private View.OnClickListener onClickCancelar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };
    }



    public boolean validation() {
        campo_nome.setError(null);

        boolean nome_valido_not_null = DefinirNomeCertificadoValidator.validateNotNull(campo_nome, getString(R.string.error_field_required));
        if (nome_valido_not_null){
            return true;
        }

        return false;
    }

    public interface Callback {
        public void onClickNomeDefinido(Certificado certificado);
    }

    // Inicia Thred
    public void  startTrasacao(Transacao transacao) {
        boolean redOk = AndroidUtils.isNetworkAvailable(getActivity());
        if (redOk) {
            new TransacaoTask(getActivity(), transacao, mProgressView, mFormView).execute();
        } else {
            Toast.makeText(getActivity(), getActivity().getString(R.string.erro_conexao_indisponivel), Toast.LENGTH_SHORT).show();
        }
    }
}
