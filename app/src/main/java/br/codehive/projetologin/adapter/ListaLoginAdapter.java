package br.codehive.projetologin.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.codehive.projetologin.R;
import br.codehive.projetologin.model.ListaLoginModel;

public class ListaLoginAdapter extends RecyclerView.Adapter<ListaLoginAdapter.ListLoginViewHolder>{

    private List<ListaLoginModel> list;

    public ListaLoginAdapter(List<ListaLoginModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ListLoginViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_login, parent, false);
        return new ListLoginViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListLoginViewHolder holder, int position) {
        ListaLoginModel model = list.get(position);
        Context context = holder.itemView.getContext();
        holder.textDescricao.setText(model.getDescricao());
        holder.itemView.setOnClickListener(view -> {
            if(model.getCls()!=null){
                Intent i = new Intent(context,model.getCls());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ListLoginViewHolder extends RecyclerView.ViewHolder{
        private TextView textDescricao;
        public ListLoginViewHolder(View itemView) {
            super(itemView);
            textDescricao = itemView.findViewById(R.id.text_view_descricao);
        }
    }
}
