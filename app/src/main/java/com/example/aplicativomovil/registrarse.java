package com.example.aplicativomovil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class registrarse extends AppCompatActivity implements View.OnClickListener {

    private EditText txtnombre, txttelefono, txtcorreo_electronico;
    private EditText txtcontrasena, txtconfirmarcontrasena;
    private Button btnguardar;
    public String documentId;
    //VARIABLE FIREBASE
    private FirebaseFirestore db;
    //AUTENTIFICACION CUENTA
    @SuppressLint("RestrictedApi")
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrarse);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // INICIALIZAR FIREBASE
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        // TEXTO
        txtnombre = findViewById(R.id.txtNombreUsuario);
        txttelefono = findViewById(R.id.txtTelefono);
        txtcorreo_electronico = findViewById(R.id.txtCorreo);
        txtcontrasena = findViewById(R.id.txtcontrasena);
        txtconfirmarcontrasena = findViewById(R.id.txtConfirmarContrasena);

        //CONFIGURACION BOTON GUARDAR
        btnguardar = findViewById(R.id.btnGuardar);
        // CREAR ACTION LISTENER PARA EL BOTON
        btnguardar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // CONVERTIR VALORES A STRING DE EDIT TEXT
        String nombre = txtnombre.getText().toString().trim();
        String telefono = txttelefono.getText().toString().trim();
        String correo_electronico = txtcorreo_electronico.getText().toString().trim();
        String contrasena = txtcontrasena.getText().toString().trim();
        String confirContrasena = txtconfirmarcontrasena.getText().toString().trim();



        if (view.getId() == R.id.btnGuardar) { // ESCUCHAR EL EVENTO BOTON GUARDAR
            // REVISAR QUE LOS EDIT TEXT NO ESTEN VACIOS
            if (nombre.isEmpty() || telefono.isEmpty() || correo_electronico.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese todos los datos", Toast.LENGTH_SHORT).show();
            } else {
                if (!correo_electronico.isEmpty()) {
                    if (contrasena.equals(confirContrasena)) {
                        Toast.makeText(this, "Espere un Momento", Toast.LENGTH_SHORT).show();
                        btnguardar.setEnabled(false);
                        crearUsuario(correo_electronico, contrasena);
                    } else {
                        Toast.makeText(registrarse.this, "Contraseña no Coincide", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(registrarse.this, "Por favor ingrese Correo Electrónico", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    private void crearUsuario(String correo, String cont) {
        // CONVERTIR VALORES A STRING DE EDIT TEXT
        String nombre = txtnombre.getText().toString().trim();
        String telefono = txttelefono.getText().toString().trim();
        String correo_electronico = txtcorreo_electronico.getText().toString().trim();
        String contrasena = txtcontrasena.getText().toString().trim();
        String confirContrasena = txtconfirmarcontrasena.getText().toString().trim();
        mAuth.createUserWithEmailAndPassword(correo, cont)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            //PASAR LOS DATOS A OTRA VIEW
                            postUsuario(nombre, telefono, correo_electronico);


                        } else {
                            if (cont.length() < 6) {
                                Toast.makeText(registrarse.this, "Error: Ingrese una contraseña válida", Toast.LENGTH_LONG).show();
                                Toast.makeText(registrarse.this, "Mínimo 6 Caracteres.", Toast.LENGTH_LONG).show();
                                btnguardar.setEnabled(true);
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registrarse.this, "Ha ocurrido un error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        btnguardar.setEnabled(true);
                    }
                });
    }
    public interface CountCallback {
        void onCountReady(int count);
    }
    private void totalDatos(registrarse.CountCallback callback) {
        db.collection("Usuarios").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int count = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            count++;
                        }
                        //Toast.makeText(contancoEmergenciaActivity.this, "Total documentos: " + count, Toast.LENGTH_SHORT).show();
                        callback.onCountReady(count);  // Llama al callback con el conteo
                    } else {
                        Log.w("Error", "Error obteniendo documentos.", task.getException());
                        callback.onCountReady(0);  // En caso de error, retorna 0
                    }
                });
    }

        private void postUsuario(String nombre, String telefono, String correoElectronico) {
            Map<String, Object> user = new HashMap<>();
            List<Map<String, Object>> contacto_emergencia = new ArrayList<>();

            // AGREGAR VALORES AL MAPA CLAVE-VALOR
            user.put("Nombre", nombre);
            user.put("Telefono", telefono);
            user.put("Correo Electronico", correoElectronico);
            user.put("Contacto Emergencia", contacto_emergencia);
            totalDatos(count -> {
                // Puedes usar 'count' para establecer un ID o cualquier lógica
                documentId = "usuario_" + count;  // Ejemplo: crear un ID basado en el conteo

                // AGREGAR VALORES A LA BASE DE DATOS con el ID específico
                db.collection("Usuarios")
                        .document(documentId)  // Usa el ID específico
                        .set(user)  // Usar 'set' en lugar de 'add'
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                //Toast.makeText(registrarse.this, "Se ha registrado el Usuario", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(registrarse.this, homeActivity.class);
                                btnguardar.setEnabled(true);
                                startActivity(intent);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.e("FirestoreError", "Error al guardar el usuario: ", e);
                                Toast.makeText(registrarse.this, "Fallo al guardar: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                btnguardar.setEnabled(true);
                            }
                        });
            });
        }
    private void limpiarCampos() {
        txtnombre.setText("");
        txttelefono.setText("");
        txtcorreo_electronico.setText("");
        txtcontrasena.setText("");
        txtconfirmarcontrasena.setText("");
    }
}
