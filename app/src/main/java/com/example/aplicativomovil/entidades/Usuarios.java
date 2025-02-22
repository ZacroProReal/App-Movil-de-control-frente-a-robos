package com.example.aplicativomovil.entidades;

public class Usuarios {
    private String nombre,telefono, email;

    public Usuarios(String nombre, String telefono, String email){
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;

    }
    //CONSTRUCTOR VACIO
    public Usuarios(){

    }

    //METODOS GETTERS Y SETTERS

    //METODOS GETTERS
    public String getNombre(){
        return nombre;
    }

    public String getTelefono(){
        return telefono;
    }
    public String getEmail(){
        return email;
    }

    //METODOS SETTERES
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
    public void setEmail(String email){
        this.email = email;
    }



}
