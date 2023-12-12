package com.example.papefirebase.modelo;

public class Tareas {
    /*mandamos llamar de firebase los datos*/
    String FechaEntrega, descripcion, tarea;
    public Tareas(){}

    public Tareas(String FechaEntrega, String descripcion, String tarea){
        this.FechaEntrega = FechaEntrega;
        this.descripcion = descripcion;
        this.tarea = tarea;
    }

    public String getFechaEntrega() {
        return FechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        FechaEntrega = fechaEntrega;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTarea() {
        return tarea;
    }

    public void setTarea(String tarea) {
        this.tarea = tarea;
    }
}
