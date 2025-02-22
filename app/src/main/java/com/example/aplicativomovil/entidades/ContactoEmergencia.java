package com.example.aplicativomovil.entidades;

public class ContactoEmergencia {
    String nombreContacto,telefonoContacto,correoContacto;

    public ContactoEmergencia(){

    }
    public ContactoEmergencia(String nombreContacto, String telefonoContacto, String correoContacto){
        this.nombreContacto = nombreContacto;
        this.telefonoContacto = telefonoContacto;
        this.correoContacto = correoContacto;
    }

    public String getNombreContacto(){
        return nombreContacto;
    }
    public String getTelefonoContacto(){
        return telefonoContacto;
    }
    public String getCorreoContacto(){
        return correoContacto;
    }
    public void setNombreContacto(String nombreContacto){
        this.nombreContacto = nombreContacto;
    }
    public void setTelefonoContacto(String telefonoContacto){
        this.telefonoContacto = telefonoContacto;
    }
    public void setCorreoContacto(String correoContacto){
        this.correoContacto = correoContacto;
    }
}
