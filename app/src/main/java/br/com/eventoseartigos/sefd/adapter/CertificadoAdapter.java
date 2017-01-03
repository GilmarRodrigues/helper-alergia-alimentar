package br.com.eventoseartigos.sefd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.model.Certificado;

/**
 * Created by gilmar on 01/01/17.
 */

public class CertificadoAdapter extends RecyclerView.Adapter<CertificadoAdapter.ViewHolder> {
    private List<Certificado> mCertificados;
    private OnClickListener onClickListener;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public CertificadoAdapter(Context mContext, List<Certificado> mCertificados, OnClickListener onClickListener) {
        this.mContext = mContext;
        this.mCertificados = mCertificados;
        this.onClickListener = onClickListener;
        this.mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_certificados, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_tipo.setText(mCertificados.get(position).getTipo());
        holder.tv_turma_palestra.setText(mCertificados.get(position).getTurma() + mCertificados.get(position).getPalestra());
        holder.tv_evento.setText(mContext.getString(R.string.text_evento)+ ": " +mCertificados.get(position).getEvento());

        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(v, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mCertificados != null ? mCertificados.size() : 0;
    }

    public interface OnClickListener {
        public void onClick(View v, int idx);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_tipo;
        private TextView tv_turma_palestra;
        private TextView tv_evento;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_tipo = (TextView) itemView.findViewById(R.id.tv_tipo);
            tv_turma_palestra = (TextView) itemView.findViewById(R.id.tv_turma_palestra);
            tv_evento = (TextView) itemView.findViewById(R.id.tv_evento);
        }
    }
}
