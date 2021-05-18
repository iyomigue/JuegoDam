package com.dam.proyecto;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

public class inicio extends AppCompatActivity {
    MediaPlayer sonido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sonido= MediaPlayer.create(this, R.raw.intro);
        sonido.start();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio);
    }

    public void jugar(View view){
        sonido.stop();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}