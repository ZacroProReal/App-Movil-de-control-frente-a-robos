package com.example.aplicativomovil.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import javax.crypto.NullCipher;

public class DataBaseUsuario  extends  dataBaseHelper{

    Context context;
    public DataBaseUsuario(@Nullable Context context) {
        super(context);
        this.context =  context;
    }

    public long insertarTabla(String nombre, String telefono, String correo_electronico){
        long id = 0;
        try{
            dataBaseHelper dbHelper = new dataBaseHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            //INSERTAR REGISTRO EN BASE DE DATOS
            ContentValues values = new ContentValues();

            values.put(nombre, nombre);
            values.put(telefono, telefono);
            values.put(correo_electronico, correo_electronico);

            //INSERTAR EN LA TABLA
            id = db.insert(TABLE_USUARIOS,  null , values);
        }catch (Exception ex){
            ex.toString();
        }
        return id;
    }
}
