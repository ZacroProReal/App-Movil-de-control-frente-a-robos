package com.example.aplicativomovil.entidades;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplicativomovil.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class listacontactos extends AppCompatActivity {

    private Spinner idContactoRegist;
    private Button idBtnVerContacto, idBtnAñadir;
    private RecyclerView recClearview;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private AmigosAdapter amigosAdapter;
    private List<String> amigosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacto_emergencia);

        // Inicializar Firebase Firestore y Auth
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        if (currentUser == null) {
            Toast.makeText(this, "Usuario no autenticado.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Inicializar vistas
        idContactoRegist = findViewById(R.id.IdContactoRegist);
        idBtnVerContacto = findViewById(R.id.idBtnVerContacto);
        idBtnAñadir = findViewById(R.id.IdBtnAñadir);
        recClearview = findViewById(R.id.recClearview);

        // Inicializar lista y adaptador
        amigosList = new ArrayList<>();
        amigosAdapter = new AmigosAdapter(amigosList);
        recClearview.setLayoutManager(new LinearLayoutManager(this));
        recClearview.setAdapter(amigosAdapter);

        // Configurar listeners
        idBtnVerContacto.setOnClickListener(v -> listarContactos());
        idBtnAñadir.setOnClickListener(v -> añadirContacto());

        // Listar los contactos al iniciar la actividad
        listarContactos();
    }

    private void listarContactos() {
        db.collection("Usuarios").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<String> correos = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String correo = document.getString("Correo Electronico");
                            if (correo != null && !correo.equals(currentUser.getEmail())) {
                                correos.add(correo); // Excluir el correo del usuario actual
                            }
                        }

                        // Configurar el Spinner con los correos obtenidos
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, correos);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        idContactoRegist.setAdapter(adapter);

                        Toast.makeText(this, "Contactos listados", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Error al listar contactos.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void añadirContacto() {
        String correoSeleccionado = idContactoRegist.getSelectedItem().toString().trim();

        if (correoSeleccionado.isEmpty()) {
            Toast.makeText(this, "Por favor selecciona un contacto.", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("Usuarios").whereEqualTo("Correo Electronico", correoSeleccionado)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && !task.getResult().isEmpty()) {
                        QueryDocumentSnapshot document = (QueryDocumentSnapshot) task.getResult().getDocuments().get(0);
                        String idAmigo = document.getId(); // Obtener el ID del amigo
                        String nombreAmigo = document.getString("Nombre"); // Obtener el nombre del amigo
                        String telefonoAmigo = document.getString("Telefono"); // Obtener el número de teléfono del amigo

                        // Crear un mapa con los datos del amigo
                        Map<String, Object> amigo = new HashMap<>();
                        amigo.put("amigoId", idAmigo);
                        amigo.put("correo", correoSeleccionado);
                        amigo.put("nombre", nombreAmigo);
                        amigo.put("telefono", telefonoAmigo);

                        // Guardar en la subcolección "amigos" del usuario actual
                        db.collection("Usuarios")
                                .document(currentUser.getUid())
                                .collection("amigos")
                                .document(idAmigo)
                                .set(amigo)
                                .addOnSuccessListener(aVoid -> {
                                    Toast.makeText(this, "Contacto añadido como amigo.", Toast.LENGTH_SHORT).show();
                                    listarAmigos(); // Refrescar la lista de amigos
                                })
                                .addOnFailureListener(e -> {
                                    Toast.makeText(this, "Error al añadir contacto.", Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(this, "Usuario no encontrado.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void listarAmigos() {
        db.collection("Usuarios")
                .document(currentUser.getUid())
                .collection("amigos")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        amigosList.clear(); // Limpiar la lista actual
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            String nombre = document.getString("nombre");
                            String telefono = document.getString("telefono");
                            String correo = document.getString("correo");
                            if (nombre != null && telefono != null && correo != null) {
                                // Mostrar nombre, correo y teléfono en la lista
                                amigosList.add(nombre + " (" + correo + ") - Tel: " + telefono);
                            }
                        }

                        amigosAdapter.notifyDataSetChanged(); // Notificar al adaptador que los datos han cambiado
                    } else {
                        Toast.makeText(this, "Error al listar amigos.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
