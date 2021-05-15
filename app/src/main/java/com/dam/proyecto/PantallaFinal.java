package com.dam.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PantallaFinal extends AppCompatActivity {

    private int puntos;
    private BBDD bd;
    private String name;
    private View view1, view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view1 = getLayoutInflater().inflate(R.layout.final_layout, null);
        view2 = getLayoutInflater().inflate(R.layout.clasificacion, null);
        setContentView(view1);
        bd = new BBDD(getApplicationContext());
        puntos = getIntent().getIntExtra("puntuacion", 0);
        TextView puntuacion = (TextView) findViewById(R.id.puntos);
        puntuacion.setText("PUNTUACION: "+ puntos);
    }
    public void guardar(View view){
        EditText nombre = (EditText) findViewById(R.id.nombre);
        name = nombre.getText().toString();
        Resultado resultado = new Resultado(nombre.getText().toString(), puntos);
        bd.insertarCalificacion(resultado);
    }

    public void calificaciones(View view){
        ArrayList<Resultado> resultados = bd.obtenerResultados();
        setContentView(view2);
        TableLayout tbl=(TableLayout) findViewById(R.id.tabla);
        tbl.removeAllViews();
        TableRow row = new TableRow(this);
        TextView txt=new TextView(this);
        txt.setText("Calificaciones");
        txt.setBackgroundColor(Color.BLUE);
        txt.setTextColor(Color.WHITE);
        txt.setTextSize(26);
        row.addView(txt);
        tbl.addView(row);

        for (Resultado i : resultados) {
            TableRow row1 = new TableRow(this);
            TextView txt1=new TextView(this);
            txt1.setText(i.getNombre());
            txt1.setTextColor(Color.WHITE);
            txt1.setTextSize(18);
            TextView txt2=new TextView(this);
            txt2.setTextColor(Color.WHITE);
            txt2.setTextSize(18);
           Integer puntos = i.getPuntuacion();
            txt2.setText(puntos.toString());
            row1.addView(txt1);
            row1.addView(txt2);
            tbl.addView(row1);
        }

    }

    @SuppressWarnings("deprecation")
    public void Ir_twitter(View view) {
        PublicarTweet tarea = new PublicarTweet(name,puntos);
        tarea.execute();
        Toast.makeText(getApplicationContext(),
                "Tweet enviado", Toast.LENGTH_LONG).show();
    }

    public void home(View view) {
        Intent intent = new Intent(this, inicio.class);
        startActivity(intent);
    }

    public void Ir_Compartir(View view) {
        Intent intent = new Intent(this, CompartirPuntuacion.class);
        startActivity(intent);
    }

    public void atras(View view) {

        setContentView(view1);
    }
}