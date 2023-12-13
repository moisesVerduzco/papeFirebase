package com.example.papefirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class createTareas extends AppCompatActivity {

    Button btn_añadir;
    EditText tarea,descripcion,FechaEntrega;
    /* agregamos el mfirestore con sus dependencias y librerias*/
    private FirebaseFirestore mfirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tareas);
        /*le dimos un nombre a la brra de navegacion*/

        this.setTitle("crear Tarea");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /* apuntamos hacia la base de datos*/
        String id = getIntent().getStringExtra("id_tarea");

        mfirestore = FirebaseFirestore.getInstance();

        tarea = findViewById(R.id.tarea);
        descripcion = findViewById(R.id.Desc);
        FechaEntrega = findViewById(R.id.FechaEntrega);
        btn_añadir = findViewById(R.id.btn_añadir);

//botn de agregaar
        if(id == null || id == ""){

            btn_añadir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombreTarea = tarea.getText().toString().trim();
                    String nombreDesc = descripcion.getText().toString().trim();
                    String Fecha = FechaEntrega.getText().toString().trim();
                    /* creamos un if para que por lo menos una tarea este llena antes de podera agregarla*/
                    if(nombreTarea.isEmpty() && nombreDesc.isEmpty()&& Fecha.isEmpty()){
                        Toast.makeText(getApplicationContext(),"ingresa los datos", Toast.LENGTH_SHORT).show();
                    }else{
                        postTar(nombreTarea,nombreDesc,Fecha);
                    }
                }
            });
        }else{
            //si seleccionamos la imagenbutton nos va a cambier el nombre del boton a edtira
            btn_añadir.setText("Editar");
            getTarea(id);
            btn_añadir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String nombreTarea = tarea.getText().toString().trim();
                    String nombreDesc = descripcion.getText().toString().trim();
                    String Fecha = FechaEntrega.getText().toString().trim();

                    if(nombreTarea.isEmpty() && nombreDesc.isEmpty()&& Fecha.isEmpty()){
                        Toast.makeText(getApplicationContext(),"ingresa los datos", Toast.LENGTH_SHORT).show();
                    }else{
                        updateTar(nombreTarea,nombreDesc,Fecha,id);
                    }
                }
            });
        }

    }
//para editar
    private void updateTar(String nombreTarea, String nombreDesc, String Fecha, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("tarea", nombreTarea);
        map.put("descripcion", nombreDesc);
        map.put("FechaEntrega", Fecha);

        mfirestore.collection("tareas").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(),"Actualizado exitosamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(createTareas.this, Menu_admin.class);
                startActivity(intent);
                finish();
            }//en caso de que tenga un error se ejcuta el siguiente codigo
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "error al Actualizar", Toast.LENGTH_SHORT).show();
            }
        });
    }
//para añadir
    private void postTar(String nombreTarea, String nombreDesc, String Fecha) {
        /* este sera el nombre con el que se guardara en la base de datos */
        Map<String, Object> map = new HashMap<>();
        map.put("tarea", nombreTarea);
        map.put("descripcion", nombreDesc);
        map.put("FechaEntrega", Fecha);

        mfirestore.collection("tareas").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
           /* con este metodo si  fue exitoso muestra un mensaqje */
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(getApplicationContext(),"guardado exitosamente", Toast.LENGTH_SHORT).show();
                /* si es correcto nos llevara de nuevo al main activyty y podremos visualisar las tareas */
                Intent intent = new Intent(createTareas.this, Menu_admin.class);
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

    //obctener la informacion para el momentto de actualizar
    private void getTarea(String id){
        mfirestore.collection("tareas").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
            String nombreTarea = documentSnapshot.getString("tarea");
            String nombreDesc = documentSnapshot.getString("descripcion");
            String Fecha = documentSnapshot.getString("FechaEntrega");

            tarea.setText(nombreTarea);
            descripcion.setText(nombreDesc);
            FechaEntrega.setText(Fecha);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "error al obctener los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* creamos la accion de retroseso en la barra de navegacion*/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}