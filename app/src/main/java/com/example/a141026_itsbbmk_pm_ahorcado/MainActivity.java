package com.example.a141026_itsbbmk_pm_ahorcado;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private String[] words = {"kikin", "kikote", "kike", "kiko", "koko"};
    private Random random;
    private String palabraAnterior;
    private String palabraActual;
    TextView txtPalabraOculta, Tittle;
    private EditText inputLetra;
    private Button btnAdivinar;
    private ImageView cabeza, cuerpo, brazoIzquierdo, brazoDerecho, piernaIzquierda, piernaDerecha;
    private int errores = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        random = new Random();

        Tittle = findViewById(R.id.palabrita);
        txtPalabraOculta = findViewById(R.id.txtPalabraOculta);
        inputLetra = findViewById(R.id.inputLetra);
        btnAdivinar = findViewById(R.id.btnAdivinar);

        cabeza = findViewById(R.id.cabeza);
        cuerpo = findViewById(R.id.cuerpo);
        brazoIzquierdo = findViewById(R.id.brazoIzquierdo);
        brazoDerecho = findViewById(R.id.brazoDerecho);
        piernaIzquierda = findViewById(R.id.piernaIzquierda);
        piernaDerecha = findViewById(R.id.piernaDerecha);

        String nuevaPalabra = words[random.nextInt(words.length)];
        Tittle.setText(nuevaPalabra);
        jugar(nuevaPalabra);

        btnAdivinar.setOnClickListener(this);
    }

    private void jugar(String nuevaPalabra) {
        errores = 0;
        while (nuevaPalabra.equals(palabraAnterior)) {
            nuevaPalabra = words[random.nextInt(words.length)];
        }
        palabraAnterior = nuevaPalabra;
        palabraActual = nuevaPalabra;

        String guiones = "";
        for (int i = 0; i < palabraActual.length(); i++) {
            guiones = guiones + "_ ";
        }
        txtPalabraOculta.setText(guiones.toString().trim());
    }

    private void verificarLetra() {
        String textoInput = inputLetra.getText().toString().trim();
        if (textoInput.isEmpty()) {
            return;
        }

        char letraIngresada = textoInput.charAt(0);
        inputLetra.setText("");
        //txt actual
        String textoActual = txtPalabraOculta.getText().toString().replace(" ", "");

        String nuevoTexto = "";
        boolean acerto = false;

        for (int i = 0; i < palabraActual.length(); i++) {

            if (palabraActual.charAt(i) == letraIngresada) {
                nuevoTexto = nuevoTexto + letraIngresada + " ";
                acerto = true;
            } else {
                nuevoTexto = nuevoTexto + textoActual.charAt(i) + " ";
            }
        }

        txtPalabraOculta.setText(nuevoTexto.trim());

        if (acerto == false) {
            errores = errores + 1;
            mostrarParteAhorcado();
        } else {
            verificarVictoria();
        }
    }

    private void mostrarParteAhorcado() {
        switch (errores) {
            case 1: cabeza.setVisibility(ImageView.VISIBLE); break;
            case 2: cuerpo.setVisibility(ImageView.VISIBLE); break;
            case 3: brazoIzquierdo.setVisibility(ImageView.VISIBLE); break;
            case 4: brazoDerecho.setVisibility(ImageView.VISIBLE); break;
            case 5: piernaIzquierda.setVisibility(ImageView.VISIBLE); break;
            case 6:
                piernaDerecha.setVisibility(ImageView.VISIBLE);
                Toast.makeText(this, "¡Perdiste!", Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void verificarVictoria() {
        String estadoActual = txtPalabraOculta.getText().toString();
        if (!estadoActual.contains("_")) {
            Toast.makeText(this, "¡Felicidades, ganaste!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        verificarLetra();
    }
}