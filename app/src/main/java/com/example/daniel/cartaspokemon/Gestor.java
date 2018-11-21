package com.example.daniel.cartaspokemon;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class Gestor extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static final int REQUEST_CODE = 1;
    static ArrayList<Pokemon> pokeLista = new ArrayList<>();
    RecyclerView recyclerView;
    Adaptador adaptadorPokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //instanciar recycler y lista
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //instanciar el adaptador
        adaptadorPokemon = new Adaptador(pokeLista);

        adaptadorPokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Toast.makeText(getApplicationContext(), "Seleccion: " + pokeLista.get
                        (recyclerView.getChildAdapterPosition(v)).getNombre(), Toast.LENGTH_LONG).show();*/
                Intent intent = new Intent(Gestor.this, ConsultaCartas.class);
                Pokemon consulta = pokeLista.get(recyclerView.getChildAdapterPosition(v));
                intent.putExtra("pokeconsulta", consulta);
                intent.putExtra("posicion", recyclerView.getChildAdapterPosition(v));
                startActivity(intent);
            }
        });

        recyclerView.setAdapter(adaptadorPokemon);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPokemon();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gestor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.add_card) {
            addPokemon();
        } else if (id == R.id.help) {
            Intent i = new Intent(Gestor.this, Helper.class);
            startActivity(i);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addPokemon(){
        Intent intent = new Intent(this, CrearPokemon.class);
        //Para editar los objetos hay que pasarle un objeto con los datos.
        //Pokemon pokemon = new Pokemon();
        //intent.putExtra("pokemon", pokemon);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == REQUEST_CODE){
            if(resultCode == RESULT_OK){
                Pokemon pokemon = data.getParcelableExtra("pokemon");
                pokeLista.add(pokemon);
                adaptadorPokemon.notifyDataSetChanged();
            }
        }
    }
}
