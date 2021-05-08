package com.dam.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int NPreguntas = 3;
    private BBDD bd;
    private Pregunta p;
    private Button a;
    private Button b;
    private Button c;
    private Button d;
    private ImageButton botonplantarse;
    private int banderaplantarse=0;
    private ImageButton botonpub;
    private int banderapublico = 0;
    private ImageButton boton50;
    private int banderaboton50 = 0;
    private ImageButton botonsaltar;
    private int banderasaltar=0;
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
        botonplantarse = (ImageButton) findViewById(R.id.boton_plantarse);
        botonplantarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PantallaFinal.class);
                intent.putExtra("puntuacion", puntos);
                startActivity(intent);
            }
        });
        botonpub = (ImageButton) findViewById(R.id.boton_publico);
        botonpub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aqui debe salir una ventana y decir el publico ha determinado lo siguiente para
                //cada pregunta, me vale con que aparezca una ventanita y 4 porcentajes aleatorio
                //porque recordemos que el publico es tonto
                Dialog dialogopublico = creaDialogoPublico(savedInstanceState);
                dialogopublico.show();
                banderapublico++;
            }
        });
        boton50 = (ImageButton) findViewById(R.id.boton_5050);
        boton50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aqui debe salir una ventana que pida y detecte agitacion y luego eliminamos dos
                //opciones
            }
        });
        botonsaltar = (ImageButton) findViewById(R.id.boton_saltar);
        botonsaltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aqui debe salir una ventana que pregunte si se aplica y eliminar dos opciones
                //incorrectas
                if(banderasaltar == 0) {
                    Dialog dialogo = creaDialogoSaltar(savedInstanceState);
                    dialogo.show();
                    banderasaltar++;
                }
            }
        });
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
    private void AplicarComodinPublico(){

    }
    private void AplicarComodinSaltar(){

    }
    private void AplicarComodin50(){

    }
    private Dialog creaDialogoSaltar(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Comodín Saltar pregunta")
        .setMessage("Saltarás a la siguiente pregunta, ¿de verdad que tú solit@ no puedes?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Aqui compruebo y quito dos opciones incorrectas
                        MuestraPregunta();
                    }
                })
                .setNegativeButton("Voy a pensar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Se cancela el dialogo, no hace nada
                    }
                });
        // Create the AlertDialog object and return it

        return builder.create();
    }

    private Dialog creaDialogoPublico(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Comodín Estadísticas")
                .setMessage("Preguntaremos al público su opinión, ¿confías?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Aqui compruebo y quito dos opciones incorrectas
                        Dialog dialogostats=creaStats(savedInstanceState);
                        dialogostats.show();
                    }
                })
                .setNegativeButton("Voy a pensar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Se cancela el dialogo, no hace nada
                    }
                });
        // Create the AlertDialog object and return it

        return builder.create();
    }
    private Dialog creaStats(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        int A=(int) (Math.random() * (50 - 1)) + 1;
        int B=(int) (Math.random() * (A - 1)) + 1;
        int C=(int) (Math.random() * (B - 1)) + 1;
        int D=(int) 100-(A+B+C);
        builder.setTitle("Comodín Estadísticas")
                .setMessage("El público ha decidido que:\n\n " +
                        "-Opción A: "+ A+"%\n -Opción B: "+D+"%\n -Opción C: "+B+"%\n -Opción D: "+C+"%")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Aqui compruebo y quito dos opciones incorrectas

                    }
                });


        // Create the AlertDialog object and return it

        return builder.create();
    }

}