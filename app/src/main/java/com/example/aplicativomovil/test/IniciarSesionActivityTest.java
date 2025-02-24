package com.example.aplicativomovil.test;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import org.junit.Before;
import org.testng.annotations.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class IniciarSesionActivityTest {

    private IniciarSesionActivity activity;
    private FirebaseAuth mockAuth;
    private FirebaseUser mockUser;

    @Before
    public void setUp() {
        // Inicializar la actividad y simular la autenticación de Firebase
        activity = Mockito.spy(new IniciarSesionActivity());
        mockAuth = mock(FirebaseAuth.class);
        mockUser = mock(FirebaseUser.class);
        activity.mAuth = mockAuth;  // Usamos el mock de FirebaseAuth en lugar del real
    }

    @Test
    public void testLoginSuccess() {
        String email = "usuario@example.com";
        String password = "contraseña123";

        // Simular un éxito de autenticación
        AuthResult mockAuthResult = mock(AuthResult.class);
        when(mockAuthResult.getUser()).thenReturn(mockUser);
        Task<AuthResult> mockTask = Tasks.forResult(mockAuthResult);

        when(mockAuth.signInWithEmailAndPassword(email, password)).thenReturn(mockTask);

        // Realizar el login
        activity.txtcorreo.setText(email);
        activity.txtcontrasena.setText(password);
        activity.onClick(mock(View.class));  // Simula hacer clic en el botón de inicio de sesión

        // Verificar que el login fue exitoso
        verify(mockAuth).signInWithEmailAndPassword(email, password);
        assertNotNull(activity.mAuth.getCurrentUser());
    }

    @Test
    public void testLoginFailure() {
        String email = "usuario@example.com";
        String password = "contraseñaErronea";

        // Simular un error de autenticación
        Task<AuthResult> mockTask = Tasks.forException(new Exception("Usuario o Contraseña Incorrectos"));

        when(mockAuth.signInWithEmailAndPassword(email, password)).thenReturn(mockTask);

        // Realizar el login
        activity.txtcorreo.setText(email);
        activity.txtcontrasena.setText(password);
        activity.onClick(mock(View.class));  // Simula hacer clic en el botón de inicio de sesión

        // Verificar que se muestra un mensaje de error
        verify(mockAuth).signInWithEmailAndPassword(email, password);
        assertNull(activity.mAuth.getCurrentUser());
    }
}


