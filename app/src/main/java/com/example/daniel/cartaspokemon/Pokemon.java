package com.example.daniel.cartaspokemon;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URI;

public class Pokemon implements Parcelable{
    private String nombre;
    private String tipo;
    private int puntosDeSalud, peso, altura, ataque, defensa;
    private Bitmap imagen;

    public Pokemon(Bitmap imagen, String nombre, String tipo, int puntosDeSalud, int peso, int altura, int ataque, int defensa) {
        this.imagen = imagen;
        this.nombre = nombre;
        this.tipo = tipo;
        this.puntosDeSalud = puntosDeSalud;
        this.peso = peso;
        this.altura = altura;
        this.ataque = ataque;
        this.defensa = defensa;
    }

    public Pokemon() {
    }

    protected Pokemon(Parcel in) {
        nombre = in.readString();
        tipo = in.readString();
        puntosDeSalud = in.readInt();
        peso = in.readInt();
        altura = in.readInt();
        ataque = in.readInt();
        defensa = in.readInt();
        //imagen = in.readParcelable(Uri.class.getClassLoader());
        imagen = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Pokemon> CREATOR = new Creator<Pokemon>() {
        @Override
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPuntosDeSalud() {
        return puntosDeSalud;
    }

    public void setPuntosDeSalud(int puntosDeSalud) {
        this.puntosDeSalud = puntosDeSalud;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeString(tipo);
        dest.writeInt(puntosDeSalud);
        dest.writeInt(peso);
        dest.writeInt(altura);
        dest.writeInt(ataque);
        dest.writeInt(defensa);
        dest.writeParcelable(imagen, flags);
    }
}
