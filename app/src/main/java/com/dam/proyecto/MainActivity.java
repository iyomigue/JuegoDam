package com.dam.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int NPreguntas = 3;
    private BBDD bd;
    private Pregunta p;
    private Button a;
    private Button b;
    private Button c;
    private Button d;
    private TextView preg;
    private TextView puntuacion;
    private int puntos = 0;
    private int index = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregunta);
        bd = new BBDD(getApplicationContext());
        bd.cargaDatos();
        a = (Button) findViewById(R.id.boton_opcion_1);
        b = (Button) findViewById(R.id.boton_opcion_2);
        c = (Button) findViewById(R.id.boton_opcion_3);
        d = (Button) findViewById(R.id.boton_opcion_4);
        preg = (TextView) findViewById(R.id.pregunta);
        puntuacion = (TextView) findViewById(R.id.puntuacion);

        MuestraPregunta();
    }

    private void MuestraPregunta(){
        if (index > NPreguntas)
            Victoria();
        else {
            //TODO
            //falta hacer que sea aleatoria y no se repita
            p = bd.obtenerPregunta(index);
            a.setText(p.getA());
            b.setText(p.getB());
            c.setText(p.getC());
            d.setText(p.getD());
            preg.setText(p.getPregunta());
            puntuacion.setText("Por "+ (puntos + 100)+" puntos:");
            index++;
        }
    }

    public void CompruebaPregunta(View view){
        //TODO
        //añadir animaciones si quereis para comprobar la respuesta
        switch (view.getId()) {
            case R.id.boton_opcion_1:
                if("a ".equals(p.getRespuesta()) || "a".equals(p.getRespuesta())){
                    //acierto
                    puntos +=100;
                    MuestraPregunta();
                }else{
                    Derrota();
                }
                break;
            case R.id.boton_opcion_2:
                if("b ".equals(p.getRespuesta()) || "b".equals(p.getRespuesta())){
                    //acierto
                    puntos +=100;
                    MuestraPregunta();
                }else{
                    Derrota();
                }
                break;
            case R.id.boton_opcion_3:
                if("c ".equals(p.getRespuesta()) || "c".equals(p.getRespuesta())){
                    //acierto
                    puntos +=100;
                    MuestraPregunta();
                }else{
                    Derrota();
                }
                break;
            case R.id.boton_opcion_4:
                if("d ".equals(p.getRespuesta()) || "d".equals(p.getRespuesta())){
                    //acierto
                    puntos +=100;
                    MuestraPregunta();
                }else{
                    Derrota();
                }
                break;
        }
    }

    private void Victoria(){
        //TODO
    }

    private void Derrota(){
        Intent intent = new Intent(this, PantallaFinal.class);
        intent.putExtra("puntuacion", puntos);
        startActivity(intent);
    }
}