package com.example.proto;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView carro;
    private boolean semaforo = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView fondo = findViewById(R.id.fondo);
        carro = findViewById(R.id.carroUno);

        movimientoCarro();

        fondo.setOnClickListener( view -> {
            if (semaforo){
                semaforo = false;
            }else{
                movimientoCarro();
                semaforo = true;
            }

        });
    }

    private void movimientoCarro(){
        Animation movimiento = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movimiento_carro);
        carro.startAnimation(movimiento);

        movimiento.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (semaforo){
                    movimientoCarro();
                }else{
                    Animation estatico = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.espera_auto);
                    carro.startAnimation(estatico);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
}