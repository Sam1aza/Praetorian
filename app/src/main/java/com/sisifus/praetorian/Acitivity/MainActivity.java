package com.sisifus.praetorian.Acitivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sisifus.praetorian.R;

import com.sisifus.praetorian.Classes.Usuario;
import com.sisifus.praetorian.helper.Preferencias;

import java.security.Permission;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CAMERA = 10;
    private static final int PERMISSION_WRITE = 11;
    private static final int PERMISSION_READ = 12;


    private FirebaseAuth mAuth;

    private Usuario usuario;

    private BootstrapEditText edtEmail;
    private BootstrapEditText edtSenha;
    private BootstrapButton btnloguin;
    private BootstrapButton btncadastro;
    private BootstrapButton btnrecuperarsenha;

    private BootstrapButton btnCancelarAlert;
    private BootstrapButton btnSendEmail;
    private BootstrapEditText edtsendemail;


    private Dialog dialog;


    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verificarPermissões();


        mAuth = FirebaseAuth.getInstance();

        edtEmail = (BootstrapEditText) findViewById(R.id.emailloguinid);
        edtSenha = (BootstrapEditText) findViewById(R.id.senhaloguinid);
        btnloguin = (BootstrapButton) findViewById(R.id.btnloginid);
        btncadastro = (BootstrapButton) findViewById(R.id.btncadastrarid);
        btnrecuperarsenha = (BootstrapButton) findViewById(R.id.recoverypassid);



        btncadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirCadastro();
            }
        });

        /*if(usuarioLogado()){
            Intent intentMinhaconta = new Intent(MainActivity.this, PainelInicial.class);
            abrirNovaActivity(intentMinhaconta);
            finish();

        }else{*/

        btnloguin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtEmail.getText().toString().equals("") && edtSenha.getText().toString().equals("")) {
                    Toast.makeText(MainActivity.this, "Preencha os campos de e-mail e senha", Toast.LENGTH_LONG).show();
                }else if (edtEmail.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Preencha o campo de e-mail ", Toast.LENGTH_LONG).show();
                }else if (edtSenha.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Preencha o campo de senha ", Toast.LENGTH_LONG).show();
                }else{
                    efetuarLogin(edtEmail.getText().toString(), edtSenha.getText().toString());
                }
            }
        });

        btnrecuperarsenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirDialog();
            }
        });

    }

    private void efetuarLogin(String email, String password){

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            Toast.makeText(MainActivity.this, "Login efetuado com sucesso!",
                                    Toast.LENGTH_LONG).show();
                                    abrirPrincipal();
                                    finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Erro de autenticação.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // ...
                    }
                });

    }



    private void abrirCadastro(){
        Intent intent = new Intent(MainActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarPermissões();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        if(currentUser!= null){
            abrirPrincipal();
            finish();
        }
    }

    private void abrirPrincipal(){
        Intent intent = new Intent(MainActivity.this,PainelInicial.class);
        startActivity(intent);
        finish();
    }

    private void abrirDialog(){
        dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.alert_recoverypassword);

        btnCancelarAlert = (BootstrapButton) dialog.findViewById(R.id.btncancelarrecup);
        btnSendEmail = (BootstrapButton) dialog.findViewById(R.id.btnsendemail);
        edtsendemail = (BootstrapEditText) dialog.findViewById(R.id.edtemailrecovery);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mAuth.sendPasswordResetEmail(edtsendemail.getText().toString())
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    Toast.makeText(MainActivity.this,
                                                                            "Verifique a sua caixa de email", Toast.LENGTH_SHORT).show();
                                                                } else {
                                                                    Toast.makeText(MainActivity.this,
                                                                            "Email invalido", Toast.LENGTH_SHORT).show();
                                                                }
                                                            }

                                                        });
                                                dialog.dismiss();
                                            }
                                        });

        btnCancelarAlert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    private void verificarPermissões(){

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CAMERA) && ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        PERMISSION_CAMERA);

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_WRITE);

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_READ);




                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

    }

}

