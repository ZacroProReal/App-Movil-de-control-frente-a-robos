package com.example.aplicativomovil.token;

import android.content.Context;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.messaging.FirebaseMessaging;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import java.util.HashMap;
import java.util.Map;

public class Tokens {
    private final FirebaseFirestore db;
    private final Context context;
    public String idToken;
    public String token;


    // Constructor para inicializar FirebaseFirestore y el contexto
    public Tokens(Context context) {
        this.db = FirebaseFirestore.getInstance();
        this.context = context;
    }
    public Tokens(){
        this.db = FirebaseFirestore.getInstance();
        this.context = null;
    }

    // Interfaz para contar documentos
    public interface CountCallback {
        void onCountReady(int count);
    }

    // Método para contar el número de documentos en la colección "Tokens"
    public void totalDatos(CountCallback callback) {
        db.collection("Tokens").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        int count = 0;
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            count++;
                        }
                        callback.onCountReady(count);
                    } else {
                        // Mostrar un mensaje de error en caso de fallo
                        Toast.makeText(context, "Error al contar los datos", Toast.LENGTH_SHORT).show();
                        callback.onCountReady(0);
                    }
                });
    }
    // Interfaz para manejar el resultado del token
    public interface OnTokenReceivedCallback {
        void onTokenReceived(String token);
    }

    // Método para buscar el token en Firestore
    public void getToken(String email, OnTokenReceivedCallback callback) {
        db.collection("Tokens")
                .whereEqualTo("Email_Token", email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Obtener el primer documento encontrado
                        String token = queryDocumentSnapshots.getDocuments().get(0).getString("Token_Registro");
                        callback.onTokenReceived(token); // Llamar al callback con el token
                    } else {
                        // No se encontró ningún documento
                        callback.onTokenReceived(null); // Devuelve null si no se encuentra el token
                    }
                })
                .addOnFailureListener(e -> {
                    // Manejar el error y devolver null
                    callback.onTokenReceived(null);
                });
    }

    // Método para obtener y registrar un token
    public void setToken(FirebaseUser user) {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            // Mostrar un mensaje de error si no se obtiene el token
                            Toast.makeText(context, "Error al obtener el token: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        // Obtener el token de registro de FCM
                        String token = task.getResult();
                        // Verificar si el token existe en la base de datos
                        isTokenExist(token, user);
                    }
                });
    }

    // Verificar si el token ya existe en la base de datos
    private void isTokenExist(String token, FirebaseUser user) {
        db.collection("Tokens")
                .whereEqualTo("Token_Registro", token)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // El token ya existe en la base de datos
                        //Toast.makeText(context, "El token ya existe", Toast.LENGTH_SHORT).show();
                    } else {
                        // El token no existe, guardar en Firestore con correo
                        saveToken(token, user);
                    }
                })
                .addOnFailureListener(e -> {
                    // Mostrar un mensaje de error si ocurre un fallo
                    Toast.makeText(context, "Error al verificar el token: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    // Guardar el token en Firestore
    private void saveToken(String token, FirebaseUser user) {
        // Crear un mapa con el token
        Map<String, Object> tokenRegistro = new HashMap<>();
        tokenRegistro.put("Email_Token", user.getEmail());
        tokenRegistro.put("Token_Registro", token);

        totalDatos(count -> {
        idToken = "Token_" + count;

        // Guardar el token en Firestore con un documento único
        db.collection("Tokens")
                .document(idToken)
                .set(tokenRegistro)
                .addOnSuccessListener(documentReference -> {
                    // Mostrar un mensaje de éxito
                    Toast.makeText(context, "Token guardado con éxito", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    // Mostrar un mensaje de error
                    Toast.makeText(context, "Error al guardar el token: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
        });
    }
}
