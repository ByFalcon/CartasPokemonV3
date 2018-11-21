package com.example.daniel.cartaspokemon;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder>
        implements View.OnClickListener{

    ArrayList<Pokemon> pokeLista;
    private View.OnClickListener listener;

    public Adaptador(ArrayList<Pokemon> pokeLista) {
        this.pokeLista = pokeLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //enlaza el adaptador con la vista
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.carta, null);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        //comunicacion entre el adaptador y la clase ViewHolder
        holder.imageView.setImageBitmap(pokeLista.get(i).getImagen());
        holder.nombre.setText(pokeLista.get(i).getNombre());
        holder.tipo.setText(pokeLista.get(i).getTipo());
        holder.ps.setText(pokeLista.get(i).getPuntosDeSalud()+"");
        holder.ataque.setText(pokeLista.get(i).getAtaque()+ "");
        holder.defensa.setText(pokeLista.get(i).getDefensa()+"");
        holder.altura.setText(pokeLista.get(i).getAltura()+"");
        holder.peso.setText(pokeLista.get(i).getPeso()+"");
    }

    @Override
    public int getItemCount() {
        //devuelve el tamaño de la lista
        if(pokeLista != null)
            return pokeLista.size();
        return 0;
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, tipo, ps, ataque, defensa, altura, peso;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            nombre = itemView.findViewById(R.id.tnombre);
            tipo = itemView.findViewById(R.id.ttipo);
            ps = itemView.findViewById(R.id.tps);
            ataque = itemView.findViewById(R.id.tataque);
            defensa = itemView.findViewById(R.id.tdefensa);
            altura = itemView.findViewById(R.id.taltura);
            peso = itemView.findViewById(R.id.tpeso);
        }
    }
}
