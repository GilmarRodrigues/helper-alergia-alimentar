package br.com.eventoseartigos.sefd.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.eventoseartigos.sefd.R;
import br.com.eventoseartigos.sefd.model.Evento;

/**
 * Created by gilmar on 27/12/16.
 */

public class EventosAdapter extends RecyclerView.Adapter<EventosAdapter.ViewHolder> {
    private List<Evento> mEventos;
    private OnClickListener onClickListener;
    private LayoutInflater mLayoutInflater;
    private Activity mContext;

    public EventosAdapter(List<Evento> mEventos, Activity context, OnClickListener onClickListener) {
        this.mEventos = mEventos;
        this.onClickListener = onClickListener;
        this.mContext = context;
        this.mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_eventos, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tv_nome.setText(mEventos.get(position).getNome());

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
        return mEventos != null ? mEventos.size() : 0;
    }

    public interface OnClickListener {
        public void onClick(View view, int idx);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_nome;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_nome = (TextView) itemView.findViewById(R.id.tv_nome);
        }
    }
}
