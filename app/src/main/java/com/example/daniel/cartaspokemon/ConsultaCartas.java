package com.example.daniel.cartaspokemon;

import android.content.Context;
import android.content.Intent;
import android.print.PrintManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ConsultaCartas extends AppCompatActivity {

    private ImageView imageView;
    private TextView consultaNombre, consultaTipo, consultaPs, consultaAtaque, consultaDefensa,
        consultaPeso, consultaAltura;
    private Button btImprimir;

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
        btImprimir = findViewById(R.id.bt_imprimir);

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

        btImprimir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doPrint();


            }
        });
    }

    public void doPrint(){
        //Cogemos el servicio con el objeto printManager.
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);
        String jobName = this.getString(R.string.app_name) + " Document";
        //Print nos permite hacer el trabajo de impresión.
        printManager.print(jobName, new MyPrintDocumentAdapter(this, imageView, consultaNombre, consultaTipo, consultaPs, consultaAtaque, consultaDefensa,
                consultaPeso, consultaAltura), null);
    }
}
