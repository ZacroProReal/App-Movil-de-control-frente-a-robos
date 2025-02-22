package com.example.aplicativomovil.messageCloud;

import static android.content.ContentValues.TAG;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.aplicativomovil.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    @Override
    public void onCreate() {
        super.onCreate();
        // Obtener el token en onCreate o en un lugar específico
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());

                        }
                        // Obtener el nuevo token de registro FCM
                        String token = task.getResult();
                        // Mostrar el token en los logs y en un Toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        //Toast.makeText(MyFirebaseInstanceIDService.this, msg, Toast.LENGTH_SHORT).show();

                    }
                });
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        // Manejar el nuevo token aquí
        Log.d(TAG, "Nuevo token generado: " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        // Manejar mensajes entrantes aquí
        Log.d(TAG, "Mensaje recibido: " + message.getMessageId());
    }
}
