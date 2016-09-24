package br.com.eventoseartigos.sefd.activity;

import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.View;
import android.widget.Toast;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.annotation.Transacao;
import br.com.eventoseartigos.sefd.task.TransacaoTask;
import br.com.eventoseartigos.sefd.utils.AndroidUtils;

/**
 * Created by gilmar on 17/09/16.
 */
public class BaseActivity extends AppCompatActivity {
    private View mProgressView;
    private View mFormView;

    // Inicia Thred
    public void  startTrasacao(Transacao transacao) {
        boolean redOk = AndroidUtils.isNetworkAvailable(this);
        if (redOk) {
            new TransacaoTask(this, transacao, mProgressView, mFormView).execute();
        } else {
            Toast.makeText(this, getString(R.string.erro_conexao_indisponivel), Toast.LENGTH_SHORT).show();
        }
    }

    public void setProgress(View progressView) {
        this.mProgressView = progressView;
    }

    public void setFormView(View formView) {
        this.mFormView = formView;
    }
}
