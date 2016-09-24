package br.com.eventoseartigos.sefd.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.annotation.Transacao;
import br.com.eventoseartigos.sefd.task.TransacaoTask;
import br.com.eventoseartigos.sefd.utils.AndroidUtils;

/**
 * Created by gilmar on 19/09/16.
 */
public class BaseFragment extends Fragment {
    private View mProgressView;
    private View mFormView;

    // Inicia Thred
    public void  startTrasacao(Transacao transacao) {
        boolean redOk = AndroidUtils.isNetworkAvailable(getContext());
        if (redOk) {
            new TransacaoTask(getActivity(), transacao, mProgressView, mFormView).execute();
        } else {
            Toast.makeText(getContext(), getString(R.string.erro_conexao_indisponivel), Toast.LENGTH_SHORT).show();
        }
    }

    public void setProgress(View progressView) {
        this.mProgressView = progressView;
    }

    public void setFormView(View formView) {
        this.mFormView = formView;
    }
}
