package com.example.aplicativomovil;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aplicativomovil.Mensajes.EnviarMensajeActivity;
import com.example.aplicativomovil.entidades.listacontactos;
import com.example.aplicativomovil.token.Tokens;
import com.example.aplicativomovil.token.Email;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class homeActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnmapa, btnrestaurarContrasena;
    private Button btnConecDIs;
    private Button btnContact;
    private ImageButton btnVermensajes;
    private FirebaseAuth mAuth;
    private String correoUsuario;
    private FirebaseFirestore db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //INICIALIZAR BOTON MAPA
        btnmapa = findViewById(R.id.btnMapa);
        btnrestaurarContrasena = findViewById(R.id.btnRestaurarContrasena);

        //BUTTON LISTENER
        btnmapa.setOnClickListener(homeActivity.this);
        btnrestaurarContrasena.setOnClickListener(this);



        //BOTON CONEXION DEL DISPOSITIVO BLEUTOOTH
        btnConecDIs = findViewById(R.id.btnConecDIs);
        btnConecDIs.setOnClickListener(this);

        //BOTON CONEXION DEL CONTACTO EMERGENCIA
        btnContact = findViewById(R.id.btnContact);
        btnContact.setOnClickListener(this);

        //BOTON CONEXION DEL CONTACTO EMERGENCIA
        btnVermensajes = findViewById(R.id.btnVermensajes);
        btnVermensajes.setOnClickListener(this);

        //FIREBASE AUTHENTIFICATION
        mAuth = FirebaseAuth.getInstance();

        //GENERAR TOQUEN
        /*
        Tokens tk = new Tokens(homeActivity.this);
        tk.setToken();
        //CLASE TOKEN
        Tokens tk = new Tokens(homeActivity.this);
        tk.setToken(user);

         */

    }

    @Override
    public void onClick(View view) {
        String auth = String.valueOf(mAuth.getCurrentUser());
        Intent intent = new Intent();
        if (view.getId() == R.id.btnMapa) {
            intent = new Intent(homeActivity.this, MapsActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btnRestaurarContrasena) {
            intent = new Intent(homeActivity.this, restaurarContrasenaActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.btnConecDIs) {
            intent = new Intent(homeActivity.this, bluetoothConexion.class);
            startActivity(intent);
        }else if (view.getId() == R.id.btnContact) {
            intent = new Intent(homeActivity.this, listacontactos.class);
            startActivity(intent);
        }else if (view.getId() == R.id.btnVermensajes) {
            intent = new Intent(homeActivity.this, EnviarMensajeActivity.class);
            startActivity(intent);
        }
    }
}