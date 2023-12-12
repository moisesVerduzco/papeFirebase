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

import com.example.papefirebase.Menu_admin;
import com.example.papefirebase.R;
import com.example.papefirebase.createTareas;
import com.example.papefirebase.modelo.Tareas;
import com.example.papefirebase.principal_usuario;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class tareasAdapterUsuario extends  FirestoreRecyclerAdapter<Tareas, tareasAdapterUsuario.ViewHolder>{
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public tareasAdapterUsuario(@NonNull FirestoreRecyclerOptions<Tareas> options, Activity activity) {
        super(options);
        this.activity = activity;
    }
    /*llamamos los datos para poder mostrarlos a travez del set text*/
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder ViewHolder, int i, @NonNull Tareas Tareas) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(ViewHolder.getAdapterPosition());
        final String id = documentSnapshot.getId();

        ViewHolder.tarea.setText(Tareas.getTarea());
        ViewHolder.descripcion.setText(Tareas.getDescripcion());
        ViewHolder.FechaEntrega.setText(Tareas.getFechaEntrega());


        ViewHolder.btn_completa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//logica para eliminar
                deleteTar(id);

                // Después de eliminar mandar al principa
                Intent intent = new Intent(activity, principal_usuario.class);
                activity.startActivity(intent);
            }
        });

    }

    private void deleteTar(String id) {
        mFirestore.collection("tareas").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity, "Completada con exito",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Error al Completar",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* adaptado con el diseño de view_tareas_singler para mostrarlos en ese layout*/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tareas_singler_usuario,parent, false);
        return new ViewHolder(v);
    }


    /* refrenciar los textview */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tarea, descripcion, FechaEntrega;
        //declaramos la imagen como boton pra eliminar
        ImageButton btn_completa ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tarea = itemView.findViewById(R.id.tarea);
            descripcion = itemView.findViewById(R.id.Desc);
            FechaEntrega = itemView.findViewById(R.id.FechaEntrega);
            btn_completa = itemView.findViewById(R.id.btn_completar);



        }
    }
}
