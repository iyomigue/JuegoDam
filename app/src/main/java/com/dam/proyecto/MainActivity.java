package com.dam.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private static final int NPreguntas = 10;
    private static int NpreguntasBBDD;
    int array[] = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; //para llevar la cuenta de las preguntas
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

    private static final long SHAKE_WAIT_TIME_MS =250 ;
    private static final double SHAKE_THRESHOLD = 1.1;


    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private long mShakeTime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregunta);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        bd = new BBDD(getApplicationContext());
        bd.cargaDatos();
        NpreguntasBBDD = bd.getProfilesCount();
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
                if(banderapublico == 0) {
                    Dialog dialogopublico = creaDialogoPublico(savedInstanceState);
                    dialogopublico.show();
                    banderapublico++;
                }
            }
        });

        boton50 = (ImageButton) findViewById(R.id.boton_5050);
        boton50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Aqui debe salir una ventana que pida y detecte agitacion y luego eliminamos dos
                //opciones
                if (banderaboton50 ==0){
                    Agitar(savedInstanceState);
                    Dialog dialogo50 = creaDialogo50(savedInstanceState);
                    dialogo50.show();
                }

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
            int numeroAleatorio;
            int bandera = 1;
            do{
                numeroAleatorio = (int) (Math.random() * ((NpreguntasBBDD+1) - 1)) + 1;
                for (int element : array) {
                    if (element == numeroAleatorio) {
                        bandera = 0;
                        break;
                    }else{bandera = 1;}
                }
            }while (bandera != 1);

            p = bd.obtenerPregunta(numeroAleatorio);
            a.setText(p.getA());
            b.setText(p.getB());
            c.setText(p.getC());
            d.setText(p.getD());preg.setText(p.getPregunta());
            puntuacion.setText("Por "+ (puntos + 100)+" puntos:");
            array[index]=numeroAleatorio;
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
        Derrota();
    }

    private void Derrota(){
        Intent intent = new Intent(this, PantallaFinal.class);
        intent.putExtra("puntuacion", puntos);
        startActivity(intent);
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

    private Dialog creaDialogo50(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Comodín 50%")
                .setMessage("Agita el telefono para eliminar dos respustas")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

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

    public void eliminaRespuesta() {
   switch (p.getRespuesta()){
       case "a ":
           c.setText("");
           d.setText("");
           break;
       case "b ":
           a.setText("");
           d.setText("");
           break;
       case "c ":
           b.setText("");
           d.setText("");
           break;
       case "d ":
           c.setText("");
           a.setText("");
           break;
       default:
           break;
        }
        banderaboton50++;
    }

    private void Agitar( Bundle savedInstanceState) {
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
         mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onAccuracyChanged(Sensor sensor, int i) { }
    // References:
// - http://jasonmcreynolds.com/?p=388
// - http://code.tutsplus.com/tutorials/using-the-accelerometer-on-android--mobile-22125
    /**
     * Detect a shake based on the ACCELEROMETER sensor
     *
     * @param event
     */
    private void detectShake(SensorEvent event) {
        long now = System.currentTimeMillis();
        if ((now - mShakeTime) > SHAKE_WAIT_TIME_MS) {
            mShakeTime = now;
            float gX = event.values[0] / SensorManager.GRAVITY_EARTH;
            float gY = event.values[1] / SensorManager.GRAVITY_EARTH;
            float gZ = event.values[2] / SensorManager.GRAVITY_EARTH;
            // gForce will be close to 1 when there is no movement
            double gForce = Math.sqrt(gX * gX + gY * gY + gZ * gZ);
            // Change background color if gForce exceeds threshold;
            // otherwise, reset the color
            if (gForce > SHAKE_THRESHOLD) {
                Log.d("cincuenta","Sacudida detectada");
                if(banderaboton50 == 0)
                    eliminaRespuesta();

            }       }    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensorAux = sensorEvent.sensor;
        if (sensorAux.getType()==Sensor.TYPE_ACCELEROMETER) {
            detectShake(sensorEvent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* mSensorManager.registerListener(this, mAccelerometer,
                SensorManager.SENSOR_DELAY_NORMAL);*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this, mAccelerometer);
    }

}