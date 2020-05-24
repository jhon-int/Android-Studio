package com.example.jhonatandantas.fatec_newsv1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Login extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1;
    FirebaseDatabase firebaseDatabse;
    DatabaseReference databaseReference;
    private EditText edtLogin, edtSenha;
    private Button btnLogin, btnCadastro;
    private TextView txtReset;
    private ImageView img;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton btnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        inicializarFirebase();
        inicializarComponentes();
        eventoClicks();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Verifica se o usuario está logado
            Intent i = new Intent(Login.this, Principal.class);
            i.putExtra("email", user.getEmail());
            i.putExtra("google", false);
            startActivity(i);
            finish();
        }
    }

    private void eventoClicks() {
        btnCadastro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Cadastro.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLogin.setEnabled(false);
                btnCadastro.setEnabled(false);
                String email = edtLogin.getText().toString().trim();
                String senha = edtSenha.getText().toString().trim();

                if (email.equals("") || (senha.equals(""))) {
                    alerta("Erro", "E-mail ou senha não informados");
                    btnLogin.setEnabled(true);
                    btnCadastro.setEnabled(true);
                } else {
                    login(email, senha);
                }
            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build();
                mGoogleSignInClient = GoogleSignIn.getClient(Login.this, gso);
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
                new AlertDialog.Builder(Login.this).setMessage("Carregando...").show();
            }
        });

        txtReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Resetar.class);
                startActivity(i);
            }
        });
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(Login.this);
        firebaseDatabse = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabse.getReference();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("Erro", "Google falha no login", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        String email = edtLogin.getText().toString().trim();
                        String senha = edtSenha.getText().toString().trim();

                        if (task.isSuccessful()) {
                            Intent i = new Intent(Login.this, Principal.class);
                            i.putExtra("email", email);
                            i.putExtra("google", true);
                            startActivity(i);
                            btnLogin.setEnabled(true);
                            btnCadastro.setEnabled(true);
                            finish();
                        } else {
                            alerta("Erro", "E-mail ou senha incorretos!");
                            btnLogin.setEnabled(true);
                            btnCadastro.setEnabled(true);
                        }
                    }
                });
    }

    private void login(String email, String senha) {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                String email = edtLogin.getText().toString().trim();
                String senha = edtSenha.getText().toString().trim();

                if (task.isSuccessful()) {
                    Intent i = new Intent(Login.this, Principal.class);
                    i.putExtra("email", email);
                    i.putExtra("google", false);
                    startActivity(i);
                    btnLogin.setEnabled(true);
                    btnCadastro.setEnabled(true);
                    finish();
                } else {
                    alerta("Erro", "E-mail ou senha incorretos!");
                    btnLogin.setEnabled(true);
                    btnCadastro.setEnabled(true);
                }
            }
        });
    }

    private void inicializarComponentes() {
        edtLogin = findViewById(R.id.edtLogin);
        edtSenha = findViewById(R.id.edtSenha);
        btnLogin = findViewById(R.id.btnLogin);
        btnCadastro = findViewById(R.id.btnCadastro);
        btnGoogle = findViewById(R.id.btnGoogle);
        txtReset = findViewById(R.id.txtReset);

        img = findViewById(R.id.Imagem_Fatec_Login);
        Picasso.get().load(R.drawable.fatec_login).into(img);
    }

    private void alerta(String Titulo, String Corpo) {
        new AlertDialog.Builder(this).setTitle(Titulo).setMessage(Corpo).setPositiveButton("OK", null).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = conexao.getFirebaseAuth();
        user = conexao.getFirebaseUser();
    }
}
