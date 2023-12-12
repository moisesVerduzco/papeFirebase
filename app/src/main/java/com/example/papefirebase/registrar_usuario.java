package com.example.papefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registrar_usuario extends AppCompatActivity {

    Button btn_register;
    EditText  emailUs, passwordUs;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        this.setTitle("Registro");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        emailUs = findViewById(R.id.correoUs);
        passwordUs = findViewById(R.id.contrasenaUs);
        btn_register = findViewById(R.id.btn_Registrar);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUsuario = emailUs.getText().toString().trim();
                String passUsuario = passwordUs.getText().toString().trim();

                if (emailUsuario.isEmpty() && passUsuario.isEmpty()){
                    Toast.makeText(registrar_usuario.this, "Ingresa los datos", Toast.LENGTH_SHORT).show();
                }else{
                    registerUser(emailUsuario, passUsuario);
                }
            }
        });
    }

    private void registerUser(String emailUsuario, String passUsuario) {


                Map<String, Object> map = new HashMap<>();
                map.put("usuario", emailUsuario);
                map.put("password", passUsuario);

                mFirestore.collection("usuario").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"guardado exitosamente", Toast.LENGTH_SHORT).show();
                        /* si es correcto nos llevara de nuevo al menu */
                        Intent intent = new Intent(registrar_usuario.this, Menu_admin.class);
                        startActivity(intent);

                        finish();
                    }/* creamos el sigueinte metodo por si hay una error al ingresar lo muestre */
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "error al guardar", Toast.LENGTH_SHORT).show();
                    }
                });
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}