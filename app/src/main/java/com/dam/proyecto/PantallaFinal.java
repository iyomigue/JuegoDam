package com.dam.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class PantallaFinal extends AppCompatActivity {

    private int puntos;
    private BBDD bd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.final_layout);
        bd = new BBDD(getApplicationContext());
        puntos = getIntent().getIntExtra("puntuacion", 0);
        TextView puntuacion = (TextView) findViewById(R.id.puntos);
        puntuacion.setText("PUNTUACION: "+ puntos);
    }
    public void guardar(View view){
        EditText nombre = (EditText) findViewById(R.id.nombre);
        Resultado resultado = new Resultado(nombre.getText().toString(), puntos);
        bd.insertarCalificacion(resultado);
    }

    public void calificaciones(View view){
        //TODO
        ArrayList<Resultado> resultados = bd.obtenerResultados();
    }

    @SuppressWarnings("deprecation")
    public void Ir_twitter(View view) {
        PublicarTweet tarea = new PublicarTweet();
        tarea.execute();
    }

    public void home(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void Ir_Compartir(View view) {
        Intent intent = new Intent(this, CompartirPuntuacion.class);
        startActivity(intent);
    }
}