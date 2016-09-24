package br.com.eventoseartigos.sefd.task;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.view.View;

import br.com.eventoseartigos.sefd.annotation.Transacao;

/**
 * Created by gilmar on 14/09/16.
 */
public class TransacaoTask extends AsyncTask<Object, Object, Boolean> {
    private Context mContext;
    private Transacao mTransacao;
    private Throwable exceptionErro;
    private View mProgressView;
    private View mFormView;
    private static final String TAG = "TransacaoTask";

    public TransacaoTask(Context context, Transacao transacao, View progressView, View formView) {
        this.mContext = context;
        this.mTransacao = transacao;
        this.mProgressView = progressView;
        this.mFormView = formView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        try {
            showProgress(true);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }

    @Override
    protected Boolean doInBackground(Object[] objects) {
        try {
            mTransacao.executar();
        } catch (Throwable e) {
            Log.e(TAG, e.getMessage(), e);
            // Salva o erro e retorna false
            this.exceptionErro = e;
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean resposta) {
        showProgress(false);
        mTransacao.atualizarView();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        showProgress(false);
    }

    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = mContext.getResources().getInteger(android.R.integer.config_shortAnimTime);

            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
