package com.example.proto;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/***
 *
 * Desarrollo De Software Para Plataformas Moviles
 * Proyecto #1
 *
 * Malony Jaramillo 8-969-365
 * Ashley Bulgin 8-971-1775
 * Ameth Torrero 8-976-482
 * Diego Hernandez 8-975-1590
 * Cristian Arroyo 9-758-245
 *
 */
public class MainActivity extends AppCompatActivity {

    private ImageView carroUno;
    private ImageView carroDos;
    private ImageView carroTres;
    private ImageView personas;

    private ImageView semaforoImage;

    private boolean semaforo = true;
    private boolean repeat = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //se instancian los objetos
        ImageView fondo = findViewById(R.id.fondo);
        carroUno = findViewById(R.id.carroUno);
        carroDos = findViewById(R.id.carroDos);
        carroTres = findViewById(R.id.carroTres);
        personas = findViewById(R.id.personas);
        semaforoImage = findViewById(R.id.semaforo);

        //Se llama a las animaciones de los carros inicialmente
        movimientoCarroUno();
        movimientoCarroDos();
        movimientoCarroTres();


        //al hacer click en el fondo, se cambiara el boolean del cual los metodos de movimiento tienen que validar si seguir o no
        fondo.setOnClickListener( view -> {
            if (semaforo){
                semaforoImage.setImageResource(R.drawable.semaforo_rojo);
                semaforo = false;
                movimientoPersonas();
            }

        });
    }


    /***
     *
     * Metodos para el movimiento de carros.
     *
     * Debido a que los otros dos metodos hacen exactamente lo mismo, solo se documentara este primer metodo...
     * No se ha podido realizar un metodo con todas las animaciones ya que, estas terminarian en tiempos
     * distintos y su listener crearia problemas con la vuelta de carga de animacion, ocacionando sobreposiciones
     * en las animaciones, que no terminen las animaciones, o que, a mitad desaparezcan y vuelvan a aparecer.
     *
     * Se ha usado el metodo setAnimationListener de la clase Animation para saber el momento en que la animacion
     * termina y validar si es necesario volver a llamar al mismo metodo, para volver a cargar la animacion, o,
     * si se debe cargar la animacion de espera antes del semaforo para los carros.
     *
     */
    private void movimientoCarroUno(){
        //se cambia la duracion de la animacion para cada carro, dando la impresion de que los carros tienen velocidades independientes
        Animation movimiento = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movimiento_carro);
        movimiento.setDuration(2200);
        carroUno.startAnimation(movimiento);

        /***
         *
         * un listener para saber cuando la animacion termina... si la animacion ha terminado se valida
         * si se vuelve a llamar al metodo de nuevo, o sea, que la animacion se repita o se cambia la animacion de los
         * carros estaticos.
         *
         */
        movimiento.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //validacion si el semaforo sigue en verde
                if (semaforo){
                    movimientoCarroUno();
                }else{
                    Animation estatico = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.espera_auto);
                    carroUno.startAnimation(estatico);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void movimientoCarroDos(){
        Animation movimiento = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movimiento_carro);
        movimiento.setDuration(700);
        carroDos.startAnimation(movimiento);

        movimiento.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (semaforo){
                    movimientoCarroDos();
                }else{
                    Animation estatico = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.espera_auto);
                    carroDos.startAnimation(estatico);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }
    private void movimientoCarroTres(){
        Animation movimiento = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movimiento_carro);
        movimiento.setDuration(1500);
        carroTres.startAnimation(movimiento);

        movimiento.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (semaforo){
                    movimientoCarroTres();
                }else{
                    Animation estatico = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.espera_auto);
                    carroTres.startAnimation(estatico);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    /***
     *
     * Metodo para el movimiento de peatones.
     *
     * La primera animacion tiene como fin el movimiento en la axis Y, mientras que al terminar esta, por medio
     * de un listener a la animacion, se llama a otra animacion para que vuelva a aparecer otros peatones y se
     * pueda volver dar click en el fondo.
     *
     */
    private void movimientoPersonas(){
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movimiento_personas);
        animation.setDuration(2000);
        personas.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                    Animation mov = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movimiento_personas2);
                    mov.setDuration(2000);
                    personas.startAnimation(mov);
                    repeat = false;
                    semaforo = true;
                    semaforoImage.setImageResource(R.drawable.semaforo_verde);
                /***
                 * los carros desaparecen si se vuelve a llamar a todos los metodos de los carros uno a uno
                 * por lo que se hace una animacion que inicie en el punto donde los carros paran y puedan continuar
                 * en ese punto hasta el final,
                 */
                    fromStaticToMov();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /***
     * Metodo para pasar de los carros quietos a movimientos.
     *
     * En esta, todos los carros comparten la misma animacion con su duracion pero al terminar esta animacion,
     * se vuelve a llamar a los metodos independientes de los carros para que obtengan la velocidad establecida.
     */
    private void fromStaticToMov(){
        Animation movCarro = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.movimiento_carro2);
        movCarro.setDuration(2000);
        carroUno.startAnimation(movCarro);
        carroDos.startAnimation(movCarro);
        carroTres.startAnimation(movCarro);

        movCarro.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                movimientoCarroUno();
                movimientoCarroDos();
                movimientoCarroTres();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}