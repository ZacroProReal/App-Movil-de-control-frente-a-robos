package com.example.aplicativomovil.token;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

public class Email {
    private   FirebaseUser user;
    private Context context;

    public Email(Context context){
        this.context = context;
       this.user= user;
    }

    public String getEmail(FirebaseUser user){
        if(user != null){
            String tokenUser;
            tokenUser = user.getEmail();
            //Toast.makeText(context, "Usuario " +user.getEmail(), Toast.LENGTH_SHORT).show();
            return tokenUser;
        }
        return "El Usuario no tien apk Instalada";
    }

}
