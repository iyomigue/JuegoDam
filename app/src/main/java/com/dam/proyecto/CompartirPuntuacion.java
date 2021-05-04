package com.dam.proyecto;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;


public class CompartirPuntuacion extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {

        super.onCreate(savedInstanceState);
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        //Aqui habría que poner el valor de la puntuación
        String cadena = "Puntuacion: 50";
        shareIntent.putExtra(Intent.EXTRA_TEXT, cadena);
        startActivity(Intent.createChooser(shareIntent, "Share your score"));


    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        /*-------AQUI HAY QUE DECIR QUE HAY QUE HACER---------*/

    }


}
