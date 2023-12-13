package com.example.papefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.QuerySnapshot;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_usuario extends AppCompatActivity {

    Button btn_loginUs;
    EditText emailUs, passwordUs;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_usuario);
        this.setTitle("Regresar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        emailUs = findViewById(R.id.correoUs);
        passwordUs = findViewById(R.id.contrasenaUs);
        btn_loginUs = findViewById(R.id.btn_ingresarUsuario);

        btn_loginUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUsuario = emailUs.getText().toString().trim();
                String passUsuario = passwordUs.getText().toString().trim();

                if (emailUsuario.isEmpty() || passUsuario.isEmpty()) {
                    Toast.makeText(login_usuario.this, "Ingresar los datos", Toast.LENGTH_SHORT).show();
                } else {
                    loginUsers(emailUsuario, passUsuario);
                }
            }
        });
    }
/*
    private void loginUsers(String emailUsuario, String passUsuario) {
        mAuth.signInWithEmailAndPassword(emailUsuario, passUsuario).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(login_usuario.this, MainActivity.class));
                    Toast.makeText(login_usuario.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(login_usuario.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(login_usuario.this, "Error al obctener credenciales", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

    private void loginUsers(String emailUsuario, String passUsuario) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Consulta la colección de usuarios para encontrar un documento con eso sigueinte deatos
        db.collection("usuario")
                .whereEqualTo("usuario", emailUsuario)
                .whereEqualTo("password", passUsuario)

                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            // Verifica si hay algún documento coincidente
                            if (task.getResult().size() > 0) {
                                // Si hay coincidencias, la autenticación es exitosa
                                finish();
                                startActivity(new Intent(login_usuario.this, principal_usuario.class));
                                Toast.makeText(login_usuario.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                            } else {
                                // No hay coincidencias, muestra un mensaje de error
                                Toast.makeText(login_usuario.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Error en la consulta
                            Toast.makeText(login_usuario.this, "Error al obtener credenciales", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
