package com.example.aplicativomovil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aplicativomovil.DataBase.dataBaseHelper;
import com.example.aplicativomovil.token.Tokens;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btncrearusuario;
    private Button btniniciarsesion;
    private FirebaseFirestore db;
    private String idCount;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //INICIALIZACION BASE DE DATOS FIREBASE
        db = FirebaseFirestore.getInstance();

        //BOTON USUARIO
        btncrearusuario= findViewById(R.id.btnCrearUsuario);
        btncrearusuario.setOnClickListener(this);

        //BOTON INICIAR SESION
        btniniciarsesion= findViewById(R.id.btnIniciarSesion);
        btniniciarsesion.setOnClickListener(this);


    }
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
         if (view.getId() == R.id.btnCrearUsuario) {
            intent = new Intent(MainActivity.this, registrarse.class);
            startActivity(intent);

        }else if(view.getId() == R.id.btnIniciarSesion){
            intent = new Intent(MainActivity.this, IniciarSesionActivity.class);
            startActivity(intent);
        }
    }
}