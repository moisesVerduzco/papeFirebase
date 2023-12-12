package com.example.papefirebase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Opcion_seccion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_opcion_seccion);

        Button btIngresarAdmin = findViewById(R.id.btIngresarAdmin);

        btIngresarAdmin.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), loginadmin.class);
            startActivity(intent);
        });

        Button btIngresarUsuario = findViewById(R.id.btIngresarUsuario);

        btIngresarUsuario.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), login_usuario.class);
            startActivity(intent);
        });

    }

}