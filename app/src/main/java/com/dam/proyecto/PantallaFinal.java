package com.dam.proyecto;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
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
    private MediaPlayer sonido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sonido= MediaPlayer.create(this, R.raw.finalwii);
        sonido.start();
        view1 = getLayoutInflater().inflate(R.layout.final_layout, null);
        view2 = getLayoutInflater().inflate(R.layout.clasificacion, null);
        setContentView(view1);
        bd = new BBDD(getApplicationContext());
        puntos = getIntent().getIntExtra("puntuacion", 0);
        TextView puntuacion = (TextView) findViewById(R.id.puntos);
        puntuacion.setText("PUNTUACION: "+ puntos);
        //Gestiona el boton de atrás, lleva al menu inicial y apaga la musicota
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                home(view1);
                sonido.stop();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
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
        row.setGravity(Gravity.CENTER);
        TextView txt=new TextView(this);
        txt.setText("Calificaciones");
        txt.setBackgroundColor(Color.rgb(124,40,119));
        txt.setTextColor(Color.WHITE);
        txt.setTextSize(26);
        row.addView(txt);
        tbl.addView(row);



        for (Resultado i : resultados) {
            TableRow row1 = new TableRow(this);
            
            row1.setGravity(Gravity.CENTER);
            TextView txt1=new TextView(this);
            txt1.setGravity(Gravity.CENTER);
            txt1.setText(i.getNombre());
            txt1.setTextColor(Color.WHITE);
            txt1.setTextSize(18);
            TextView txt2=new TextView(this);
            txt2.setGravity(Gravity.CENTER);
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
        sonido.stop();
        Intent intent = new Intent(this, inicio.class);
        startActivity(intent);
    }

    public void Ir_Compartir(View view) {

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        String cadena = "Mi puntuacion en '¿Quién quiere ser teleco?' ha sido de "+puntos+" puntos.";
        shareIntent.putExtra(Intent.EXTRA_TEXT, cadena);
        startActivity(Intent.createChooser(shareIntent, "Share your score"));
    }

    public void atras(View view) {
        setContentView(view1);
    }
}