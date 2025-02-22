package com.example.aplicativomovil.Mensajes;
public class Mensaje {
    private String de;
    private String para;
    private String mensaje;
    private long timestamp;
    private String remitente;
    private String contenido;
    // Constructor vacío requerido por Firestore
    public Mensaje() {}

    public Mensaje(String de, String para, String mensaje, long timestamp) {
        this.de = de;
        this.para = para;
        this.mensaje = mensaje;
        this.timestamp = timestamp;
    }

    // Getters y setters
    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getPara() {
        return para;
    }

    public void setPara(String para) {
        this.para = para;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
