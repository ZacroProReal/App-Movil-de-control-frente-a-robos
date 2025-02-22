package com.example.aplicativomovil;

import android.content.Intent;
import android.os.Bundle;
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

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;

public class restaurarContrasenaActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtcontrasena, txtconfContrasena;
    private Button btnaceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_restaurar_contrasena);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //INIICARLIZAR EDIT TEXT
        txtcontrasena = findViewById(R.id.txtcontrasena);
        txtconfContrasena = findViewById(R.id.txtcofircontrasena);

        btnaceptar = findViewById(R.id.btnAceptar);
        //ACTION LISTENER
        btnaceptar.setOnClickListener(restaurarContrasenaActivity.this);


    }

            @Override
            public void onClick(View view) {

                String contrasena = txtcontrasena.getText().toString().trim();
                String confContrasena = txtconfContrasena.getText().toString().trim();

                if (view.getId() == R.id.btnAceptar) {
                    if(contrasena.equals(confContrasena)){
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String newPassword = contrasena; // Asegúrate de usar la nueva contraseña ingresada

                        user.updatePassword(newPassword)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(restaurarContrasenaActivity.this, "Éxito: Contraseña Actualizada", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(restaurarContrasenaActivity.this, homeActivity.class);
                                            startActivity(intent);
                                        }
                                    }
                                });
                    }else {
                        Toast.makeText(restaurarContrasenaActivity.this, "Error: Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                        limpiarCampos();
                }

                }
            }






    private void limpiarCampos() {
        txtcontrasena.setText("");
        txtconfContrasena.setText("");

    }
}