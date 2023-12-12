package com.example.papefirebase.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.papefirebase.R;
import com.example.papefirebase.modelo.Tareas;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class tareasAdapterMenu extends FirestoreRecyclerAdapter<Tareas, tareasAdapterMenu.ViewHolder> {
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public tareasAdapterMenu(@NonNull FirestoreRecyclerOptions<Tareas> options, Activity activity) {
        super(options);
        this.activity = activity;
    }
    /*llamamos los datos para poder mostrarlos a travez del set text*/
    @Override
    protected void onBindViewHolder(@NonNull tareasAdapterMenu.ViewHolder ViewHolder, int i, @NonNull Tareas Tareas) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(ViewHolder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        ViewHolder.tarea.setText(Tareas.getTarea());
        ViewHolder.descripcion.setText(Tareas.getDescripcion());
        ViewHolder.FechaEntrega.setText(Tareas.getFechaEntrega());

    }

    /* adaptado con el diseño de view_tareas_singler para mostrarlos en ese layout*/
    @NonNull
    @Override
    public tareasAdapterMenu.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tareas_sigler_menu,parent, false);
        return new tareasAdapterMenu.ViewHolder(v);
    }


    /* refrenciar los textview */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tarea, descripcion, FechaEntrega;
        //declaramos la imagen como boton pra eliminar

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tarea = itemView.findViewById(R.id.tarea);
            descripcion = itemView.findViewById(R.id.Desc);
            FechaEntrega = itemView.findViewById(R.id.FechaEntrega);



        }
    }
}