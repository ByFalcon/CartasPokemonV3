package com.example.daniel.cartaspokemon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ConsultaCartas extends AppCompatActivity {

    private ImageView imageView;
    private TextView consultaNombre, consultaTipo, consultaPs, consultaAtaque, consultaDefensa,
        consultaPeso, consultaAltura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consulta_cartas);

        imageView = findViewById(R.id.imageView3);
        consultaNombre = findViewById(R.id.consultaNombre);
        consultaTipo = findViewById(R.id.consultaTipo);
        consultaPs = findViewById(R.id.consultaPs);
        consultaAtaque = findViewById(R.id.consultaAtaque);
        consultaDefensa = findViewById(R.id.consultaDefensa);
        consultaPeso = findViewById(R.id.consultaPeso);
        consultaAltura = findViewById(R.id.consultaAltura);

        Intent i = getIntent();
        Pokemon pokemon = i.getParcelableExtra("pokeconsulta");

        imageView.setImageBitmap(pokemon.getImagen());
        consultaNombre.setText(pokemon.getNombre());
        consultaTipo.setText(pokemon.getTipo());
        consultaPs.setText(pokemon.getPuntosDeSalud() + "");
        consultaAtaque.setText(pokemon.getAtaque() + "");
        consultaDefensa.setText(pokemon.getDefensa() + "");
        consultaPeso.setText(pokemon.getPeso() + "");
        consultaAltura.setText(pokemon.getAltura() + "");
    }
}
