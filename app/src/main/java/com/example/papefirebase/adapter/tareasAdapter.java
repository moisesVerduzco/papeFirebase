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
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class tareasAdapter extends  FirestoreRecyclerAdapter<Tareas, tareasAdapter.ViewHolder>{
    private FirebaseFirestore mFirestore = FirebaseFirestore.getInstance();
    Activity activity;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public tareasAdapter(@NonNull FirestoreRecyclerOptions<Tareas> options, Activity activity) {
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
//metodo editar
        ViewHolder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(activity, createTareas.class);
                i.putExtra("id_tarea", id);
                activity.startActivity(i);
            }
        });


        ViewHolder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//logica para eliminar
                deleteTar(id);

                // Después de eliminar mandar al principa
                Intent intent = new Intent(activity, Menu_admin.class);
                activity.startActivity(intent);
            }
        });

    }

    private void deleteTar(String id) {
        mFirestore.collection("tareas").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(activity, "Eliminado",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity, "Error al eliminar",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /* adaptado con el diseño de view_tareas_singler para mostrarlos en ese layout*/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_tareas_singler,parent, false);
    return new ViewHolder(v);
    }


    /* refrenciar los textview */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tarea, descripcion, FechaEntrega;
        //declaramos la imagen como boton pra eliminar
        ImageButton btn_delete , btn_edit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tarea = itemView.findViewById(R.id.tarea);
            descripcion = itemView.findViewById(R.id.Desc);
            FechaEntrega = itemView.findViewById(R.id.FechaEntrega);
            btn_delete = itemView.findViewById(R.id.btn_eliminar);
            btn_edit = itemView.findViewById(R.id.btn_editar);


        }
    }
}
