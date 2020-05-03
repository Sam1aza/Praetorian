package com.sisifus.praetorian.Acitivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.sisifus.praetorian.Classes.Usuario;
import com.sisifus.praetorian.R;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private BootstrapEditText nome;
    private BootstrapEditText email;
    private BootstrapEditText senha;
    private BootstrapEditText rsenha;

    private BootstrapButton btncadastrar;
    private BootstrapButton btnretornar;
    private ImageView uploadFoto;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference profileRef;
    private FirebaseUser userAuth;

    private Dialog dialog;
    private TextView txtTakePicture;
    private TextView txtUploadPicture;

    private static final int REQUEST_IMAGE_CAPTURE = 1;





    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        storage = FirebaseStorage.getInstance();
        userAuth = FirebaseAuth.getInstance().getCurrentUser();


        usuario = new Usuario();

        nome =         (BootstrapEditText) findViewById(R.id.nomeid);
        email =        (BootstrapEditText) findViewById(R.id.emailcadastroid);
        senha =        (BootstrapEditText) findViewById(R.id.senhacadastroid);
        rsenha =       (BootstrapEditText) findViewById(R.id.repsenhacadastroid);
        btncadastrar = (BootstrapButton) findViewById(R.id.btncadastroid);
        btnretornar =  (BootstrapButton) findViewById(R.id.voltarcadasid);
        //uploadFoto = (ImageView) findViewById(R.id.imgCadastrarFoto);


        btnretornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CadastroUsuarioActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btncadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(senha.getText().toString().equals(rsenha.getText().toString())){
                    usuario.setEmail(email.getText().toString());
                    usuario.setSenha(senha.getText().toString());
                    usuario.setNome(nome.getText().toString());
                    cadastrarUsuario(email.getText().toString(),senha.getText().toString());
                }else {
                    Toast.makeText(CadastroUsuarioActivity.this,"As senhas não se correspondem!",Toast.LENGTH_LONG).show();
                }
            }});

//        uploadFoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                abrirDialog();
//
//            }
//        });


    }

    private void cadastrarUsuario(String email,String senha){

        mAuth.createUserWithEmailAndPassword(email, senha)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            /*userAuth = FirebaseAuth.getInstance().getCurrentUser();
                            final String uid = userAuth.getUid();
                            usuario.setUid(uid);
                            storageRef = storage.getReference();
                            profileRef = storageRef.child("profile/" + uid +".jpg");

                            // Get the data from an ImageView as bytes
                            uploadFoto.setDrawingCacheEnabled(true);
                            uploadFoto.buildDrawingCache();
                            Bitmap bitmap = ((BitmapDrawable) uploadFoto.getDrawable()).getBitmap();
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] data = baos.toByteArray();

                            UploadTask uploadTask = profileRef.putBytes(data);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                    // Handle unsuccessful uploads
                                    Toast.makeText(CadastroUsuarioActivity.this,
                                            "Falha ao enviar imagem!",Toast.LENGTH_LONG).show();
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                                    // ...*/
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("TAG", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(CadastroUsuarioActivity.this,
                                            "Conta criada com sucesso!",Toast.LENGTH_LONG).show();
                                    inserirUsuarioDatabase(usuario);
                                    abrirPrincipalAcitivty();
                                //}
                            //});
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(CadastroUsuarioActivity.this,
                                    "Erro de cadastro de usuario",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }


    private void abrirMainActivity(){
        Intent intent = new Intent(CadastroUsuarioActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void abrirPrincipalAcitivty(){
        Intent intent = new Intent(CadastroUsuarioActivity.this, PainelInicial.class);
        startActivity(intent);
        finish();
    }

    private void inserirUsuarioDatabase(Usuario usuario){
        myRef = database.getReference("Usuarios");
        String key = myRef.child("Usuarios").push().getKey();
        usuario.setKeyUser(key);
        myRef.child(key).setValue(usuario);
    }

    private void fazerUploadFoto(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent,"Selecione uma imagem"),123);
    }

    private void tirarFoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getPackageManager())!= null){
            startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
        }
    }




        private void abrirDialog(){

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.alert_picture);

        txtTakePicture = (TextView) dialog.findViewById(R.id.txtTakePicture);
        txtUploadPicture = (TextView) dialog.findViewById(R.id.txtUploadPicture);

        txtTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
                dialog.dismiss();
            }
        });

        txtUploadPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fazerUploadFoto();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 123) {
                Uri image = data.getData();
                Picasso.get().load(image.toString()).into(uploadFoto);
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extra = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extra.get("data");
                uploadFoto.setImageBitmap(imageBitmap);
            }
        }
    }
}
