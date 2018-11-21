package com.example.daniel.cartaspokemon;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.print.PrintManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class CrearPokemon extends AppCompatActivity {

    private SeekBar seekBarPs, seekBarPeso, seekBarAltura, seekBarAtaque, seekBarDefensa;
    private Spinner spinnerNombre, spinnerTipo;
    private ImageView imageView;
    private Button bt;
    private Bitmap bitmap;
    private Uri uri;
    //private InputStream imageStream;
    //static int index;
    //int size = 12;
    //private ArrayList<Integer> arrayImages = new ArrayList<>();
    //private static final String TAG = "MYTAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageView = findViewById(R.id.imageView2);
        spinnerNombre = findViewById(R.id.spinnerNombre);
        spinnerTipo = findViewById(R.id.spinnerTipo);
        seekBarPs = findViewById(R.id.seekBarPs);
        seekBarPeso = findViewById(R.id.seekBarPeso);
        seekBarAltura = findViewById(R.id.seekBarAltura);
        seekBarAtaque = findViewById(R.id.seekBarAtaque);
        seekBarDefensa = findViewById(R.id.seekBarDefensa);
        bt= findViewById(R.id.button);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                Pokemon pokemon = new Pokemon(bitmap, spinnerNombre.getSelectedItem().toString(),
                        spinnerTipo.getSelectedItem().toString(), seekBarPs.getProgress(),
                        seekBarPeso.getProgress(), seekBarAltura.getProgress(),
                        seekBarAtaque.getProgress(), seekBarDefensa.getProgress());
                i.putExtra("pokemon", pokemon);
                setResult(Gestor.RESULT_OK, i);
                finish();
            }
        });
    }

   /* public void images(){
         *
         * Version 1
         *
         * index = 0;
         * for (int i = 1; i <= size; i++){
         *   arrayImages.add(getResources().getIdentifier("pokemon"+i,"drawable",getPackageName()));
         * }
         * imageView.setImageResource(arrayImages.get(index));
         * bt.setOnClickListener(new View.OnClickListener() {
         *   @Override
         *   public void onClick(View v) {
         *       if(index == size-1){
         *           index = 0;
         *       }else {
         *           index += 1;
         *       }
         *       imageView.setImageResource(arrayImages.get(index));
         *   }
         * });
    }*/

    public void cargarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 10){
            if(resultCode == RESULT_OK){
                uri = data.getData();
                bitmap= reduceBitmap(this, uri.toString(), 1024, 1024);
                imageView.setImageBitmap(bitmap);
                /*try {
                    imageStream = getContentResolver().openInputStream(uri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/
                //bitmap = BitmapFactory.decodeStream(imageStream);

                /*uri = data.getData(); //Version 2
                imageView.setImageBitmap(uri);
                CrearPokemon.this.grantUriPermission(CrearPokemon.this.getPackageName(), uri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                Glide.with(this)
                        .load(uri)
                        .into(imageView);*/
                //Picasso.with(this).load(uri).into(imageView);
            }
        }
    }

    public static Bitmap reduceBitmap(Context contexto, String uri,
                                      int maxAncho, int maxAlto) {
        try {
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(contexto.getContentResolver()
                    .openInputStream(Uri.parse(uri)), null, options);
            options.inSampleSize = (int) Math.max(
                    Math.ceil(options.outWidth / maxAncho),
                    Math.ceil(options.outHeight / maxAlto));
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeStream(contexto.getContentResolver()
                    .openInputStream(Uri.parse(uri)), null, options);
        } catch (FileNotFoundException e) {
            Toast.makeText(contexto, "Imagen no encontrada",
                    Toast.LENGTH_LONG).show();
            e.printStackTrace();
            return null;
        }
    }
}