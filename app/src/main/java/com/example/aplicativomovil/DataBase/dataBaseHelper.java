package com.example.aplicativomovil.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class dataBaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2; // Aumenta el número de versión si realizas cambios en la DB
    private static final String DATABASE_NAME = "Usuario.db";
    public static final String TABLE_USUARIOS = "table_usuarios";

    public dataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de usuarios

        try {
        String createTableSQL = ("CREATE TABLE " + TABLE_USUARIOS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "telefono TEXT NOT NULL, " +
                "correo_electronico TEXT" +
                ")");

            db.execSQL(createTableSQL);
        } catch (Exception e) {
            e.printStackTrace(); // Aquí podrías mostrar un mensaje de error o loguear el error
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Aquí ejecutas el cambio cuando se actualiza la versión de la base de datos
        if (oldVersion < newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
            onCreate(db); // Volver a crear la base de datos
        }
    }
}
