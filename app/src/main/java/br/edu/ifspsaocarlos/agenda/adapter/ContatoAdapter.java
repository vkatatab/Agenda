package br.edu.ifspsaocarlos.agenda.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import br.edu.ifspsaocarlos.agenda.model.Contato;
import br.edu.ifspsaocarlos.agenda.R;

import java.util.List;


public class ContatoAdapter extends RecyclerView.Adapter<ContatoAdapter.ContatoViewHolder> {

    private List<Contato> contatos;
    private Context context;

    private static ItemClickListener clickListener;


    public ContatoAdapter(List<Contato> contatos, Context context) {
        this.contatos = contatos;
        this.context = context;
    }

    @Override
    public ContatoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.contato_celula, parent, false);
        return new ContatoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ContatoViewHolder holder, int position) {
        Contato contato  = contatos.get(position) ;
        holder.nome.setText(contato.getNome());
        int image = 0;
        if (contato.getFavorite() == 0) {
            image = R.drawable.off_star;
        } else {
            image = R.drawable.on_star;
        }
        holder.favorite.setImageResource(image);
    }

    @Override
    public int getItemCount() {
        return contatos.size();
    }


    public void setClickListener(ItemClickListener itemClickListener) {
        clickListener = itemClickListener;
    }


    public class ContatoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        final TextView nome;
        final ImageView favorite;

        ContatoViewHolder(View view) {
            super(view);
            nome = (TextView)view.findViewById(R.id.nome);
            favorite = (ImageView)view.findViewById(R.id.favorite);
            view.setOnClickListener(this);
            favorite.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view == favorite) {
                clickListener.onItemClick(getAdapterPosition(), true);
            } else if (clickListener != null) {
                clickListener.onItemClick(getAdapterPosition(), false);
            }
        }
    }


    public interface ItemClickListener {
        void onItemClick(int position, Boolean favorite);
    }

}


