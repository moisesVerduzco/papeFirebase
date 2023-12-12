package com.example.papefirebase;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.papefirebase.adapter.tareasAdapterMenu;
import com.example.papefirebase.modelo.Tareas;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;



public class Menu_admin extends AppCompatActivity {

    RecyclerView mRecycler;
    tareasAdapterMenu mAdapter;
    FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        this.setTitle("Principal");


        mFirestore = FirebaseFirestore.getInstance();
        mRecycler = findViewById(R.id.recyclerViewMenu);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        Query query = mFirestore.collection("tareas");
        FirestoreRecyclerOptions<Tareas> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Tareas>().setQuery(query, Tareas.class).build();

        mAdapter = new tareasAdapterMenu(firestoreRecyclerOptions, this);
        /* lee los cambios */
        mAdapter.notifyDataSetChanged();
        mRecycler.setAdapter(mAdapter);


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

//metodo para el menu de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.menu_item1) {
            Intent intent1 = new Intent(Menu_admin.this, MainActivity.class);
            startActivity(intent1);
            return true;
        }

        if (itemId == R.id.menu_item2) {
            Intent intent2 = new Intent(Menu_admin.this, registrar_usuario.class);
            startActivity(intent2);
            return true;
        }

        if (itemId == R.id.menu_item3) {
            // Agrega la lógica para el tercer item
            return true;
        }

        if (itemId == R.id.menu_item4) {
            Intent intent4 = new Intent(Menu_admin.this, Opcion_seccion.class);
            startActivity(intent4);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
