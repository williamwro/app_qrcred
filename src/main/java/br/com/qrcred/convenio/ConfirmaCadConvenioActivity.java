package br.com.qrcred.convenio;

import static br.com.qrcred.AppController.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import br.com.qrcred.R;
import br.com.qrcred.service.APIService;
import br.com.qrcred.service.Client;

public class ConfirmaCadConvenioActivity extends AppCompatActivity {

    TextView editTextCel;
    TextView editTextUsuario;
    TextView editTextSenha;
    Button botaoVoltar;
    public String usuario;
    public String senha;
    public String email;
    public String cpf;
    public String cnpj;
    public String cod_convenio;
    public String newToken;
    public FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authListener;
    FirebaseFirestore firebaseFirestore;
    private APIService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirma_cad_convenio);
        editTextCel = findViewById(R.id.textViewCelGrav);
        botaoVoltar = findViewById(R.id.buttonVoltar);
        editTextUsuario  = findViewById(R.id.tvUsuario);
        editTextSenha  = findViewById(R.id.tvSenha);
        Intent intent = getIntent();
        editTextCel.setText(intent.getStringExtra("email"));
        usuario = intent.getStringExtra("usuario");
        senha = intent.getStringExtra("senha");
        email = intent.getStringExtra("email");
        cod_convenio = intent.getStringExtra("cod_convenio");
        cpf = intent.getStringExtra("cpf");
        cnpj = intent.getStringExtra("cnpj");

        editTextUsuario.setText(intent.getStringExtra("usuario"));
        editTextSenha.setText(intent.getStringExtra("senha"));

        botaoVoltar.setOnClickListener(v -> {
            Intent it = new Intent(ConfirmaCadConvenioActivity.this,ActivityLoginConvenio.class);
            startActivity(it);
        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            System.out.println("Fetching FCM registration token failed");
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();

                        // Log and toast
                        System.out.println(token);

                        //Toast.makeText(ConfirmaCadConvenioActivity.this, "Dispositivo registrado", Toast.LENGTH_SHORT).show();
                        newToken = token;
                    }
                });

        //newToken = FirebaseInstanceId.getInstance().getToken();
        //newToken = "";
        /* GET TOKEN CONVENIO */
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);


        mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    //Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    firebaseFirestore = FirebaseFirestore.getInstance();
                    FirebaseApp.initializeApp(ConfirmaCadConvenioActivity.this);

                    //* ********** GRAVA ENTRADA NO FIRESTORE INICIO ********** *//*
                    Locale BRAZIL = new Locale("pt","BR");
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",BRAZIL);
                    Date data = new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(data);
                    Date data_atual = cal.getTime();
                    String data_completa = dateFormat.format(data_atual);

                    Map<String, Object> docData = new HashMap<>();
                    docData.put("codigo",cod_convenio);
                    docData.put("cpf",cpf);
                    docData.put("cnpj",cnpj);
                    docData.put("email",email);
                    docData.put("data_criado", data_completa);
                    docData.put("token_convenio",newToken);
                    firebaseFirestore.collection("usuario_convenio")
                            .document(Objects.requireNonNull(FirebaseAuth.getInstance( ).getCurrentUser( )).getUid( ))
                            .set(docData);
                    //* ********** GRAVA ENTRADA NO FIRESTORE FIM ********** *//*

                    //sendNotifications(newToken,"QrCred","Usuário : ( "+ usuario +" ) , Senha: ( "+ senha +" )" );
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    Toast.makeText(getApplication(), "Authentication failed.",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        //updateUI(currentUser);
    }
    //desabilita o botao voltar do android
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
