package com.example.papefirebase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.papefirebase.adapter.tareasAdapter;
import com.example.papefirebase.modelo.Tareas;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class MainActivity extends AppCompatActivity {

    Button btn_add;
    RecyclerView mRecycler;
    tareasAdapter mAdapter;
    FirebaseFirestore mFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.setTitle("Regresar");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mFirestore =FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recyclerViewSingler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = mFirestore.collection("tareas");
        FirestoreRecyclerOptions<Tareas> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Tareas>().setQuery(query, Tareas.class).build();

        mAdapter = new tareasAdapter(firestoreRecyclerOptions, this);
        /* lee los cambios */
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);

        btn_add = findViewById(R.id.btn_añadir);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, createTareas.class));
            }


        });


    }


//metodos para eschuar las actualizaciones de la bse de datos
    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }


    /* creamos la accion de retroseso en la barra de navegacion*/
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    }


