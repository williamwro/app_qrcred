package br.com.qrcred;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Button btnEntrar;

    public EditText editTextCartao;
    EditText editTextSenha;
    TextView rotuloAssociado;
    TextView trocar_Cartao;
    String cartao;
    String senha;
    public String email = "";
    public String email_aux = "";
    public String cpf;
    public String matricula;
    public String empregador;
    public String UID_ = "";
    ProgressBar progressBar;
    public static FirebaseAuth mAuth;
    RequestQueue requestQueue;
    private Map<String, String> params;
    FirebaseFirestore firebaseFirestore;
    String newToken;
    public static RoomDB myAppDatabase;
    public List<Usuario_local> usuario_locals;
    public List<Usuario_local> usuario_locals_unico;
    public int id_cartao;
    public String n_cartao = "";
    public String rotulo_associado = "";
    public String Mes_Corrente = "";
    TextView tvEsqueciSenha;
    public Boolean E_usuario = false;
    FirebaseUser user;
    public String novo = "false";
    public MainActivity() {
    }

    private void inicializarFirebase() {
        FirebaseApp.initializeApp(MainActivity.this);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // ******** Inicio O HttpsUrlConnection está configurado para confiar em qualquer certificado.
        //trustEveryone();
        // ********   Fim  O HttpsUrlConnection está configurado para confiar em qualquer certificado.


        caputra_mescorrente();


        firebaseFirestore = FirebaseFirestore.getInstance();
        FirebaseApp.initializeApp(MainActivity.this);
        mAuth = FirebaseAuth.getInstance();

        btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(v -> btnEntrarHome());
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        editTextCartao = findViewById(R.id.editTextCartao);
        editTextSenha = findViewById(R.id.editTextSenha);
        rotuloAssociado = findViewById(R.id.TvRotuloAssociado);
        tvEsqueciSenha = findViewById(R.id.tvEsquecisenha);
        trocar_Cartao = findViewById(R.id.tvTrocarCartao);

        Intent intent2 = this.getIntent( );
        rotulo_associado = intent2.getStringExtra("user");
        n_cartao = intent2.getStringExtra("cartao");
        novo = intent2.getStringExtra("novo");
        // ****** VERIFICA SE CARTAO JA FOI GRAVADO NA ROOM
        myAppDatabase = RoomDB.getInstance(getApplicationContext());
        usuario_locals = myAppDatabase.usuario_localDao().getAll();
        if(usuario_locals.size() > 1){
            usuario_locals_unico = myAppDatabase.usuario_localDao().getUser(n_cartao,rotulo_associado);
            for (Usuario_local usr : usuario_locals_unico) {
                id_cartao = usr.getID();
                n_cartao = usr.getCartao();
                rotulo_associado = usr.getNomeassociado();
                Mes_Corrente = usr.getMescorrente();
            }
        }else{
            for (Usuario_local usr : usuario_locals) {
                id_cartao = usr.getID();
                n_cartao = usr.getCartao();
                rotulo_associado = usr.getNomeassociado();
                Mes_Corrente = usr.getMescorrente();
            }
        }
        if (novo.equals("true")) {
            editTextCartao.setEnabled(true);
            rotuloAssociado.setText("Login do Usuário");
            n_cartao = "";
        } else {
            rotuloAssociado.setText(rotulo_associado);
            editTextCartao.setText(n_cartao);
            editTextCartao.setEnabled(false);
        }
        editTextSenha.setText("");
        editTextCartao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 10) {
                    editTextSenha.requestFocus();
                }
            }
        });
        if(editTextCartao.getText().toString().equals("")) {
            editTextCartao.requestFocus();
        }else{
            editTextSenha.requestFocus();
        }
        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            ActionBar ab = getSupportActionBar();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_black_24dp, null);
            //drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_IN));
            ab.setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_theme_light_onPrimary));
        getSupportActionBar().setTitle("Login usuário");
        tvEsqueciSenha.setOnClickListener(v -> {
            if(!editTextCartao.getText().toString().equals("")) {
                Intent intent = new Intent(MainActivity.this, MainActivityRedefinirSenha.class);
                intent.putExtra("cartao", (editTextCartao.getText().toString()));
                startActivity(intent);
            }else{
                AestheticDialog.Builder dialog1 =
                        new AestheticDialog.Builder(MainActivity.this,
                                DialogStyle.FLAT, DialogType.ERROR);
                dialog1.setTitle("Atenção!");
                dialog1.setMessage("Para recuperar a senha tem que informar o numero do cartão!");
                dialog1.setCancelable(false);
                dialog1.setDarkMode(true);
                dialog1.setGravity(Gravity.CENTER);
                dialog1.setAnimation(DialogAnimation.SHRINK);
                dialog1.setOnClickListener(new OnDialogClickListener() {
                    @Override
                    public void onClick(@NonNull AestheticDialog.Builder builder) {

                        builder.dismiss();
                    }
                });
                dialog1.show();
            }
        });
        trocar_Cartao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_tela_User.class);
                startActivity(intent);
            }
        });
        inicializarFirebase();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }
    @Override
    public boolean onSupportNavigateUp() {
        if(isTaskRoot()){
            finish();
            return false;
        }else{
            Intent intent = new Intent(MainActivity.this, TelaMenuActivity.class);
            startActivity(intent);
            return true;
        }
    }
    public void btnEntrarHome() {
        //inicio *********************************************
        cartao = editTextCartao.getText().toString();
        senha = editTextSenha.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        if (cartao.equals("") || senha.equals("")) {
            progressBar.setVisibility(View.GONE);
            new AestheticDialog.Builder(MainActivity.this, DialogStyle.FLAT, DialogType.ERROR)
                    .setTitle("Atenção!").setMessage("O CARTÃO E A SENHA É OBRIGATÓRIO!").setCancelable(false).setGravity(Gravity.CENTER).setAnimation(DialogAnimation.SHRINK).show();
        } else {
            String urlm = getResources().getString(R.string.HOST) + "localiza_associado_app_2.php";
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, urlm,
                    response -> {
                        JSONObject campos;
                        try {

                            campos = new JSONObject(response);

                            String resultado = campos.getString("situacao");

                            if (resultado.equals("1")) {//cartao liberado
                                progressBar.setVisibility(View.GONE);
                                // Inseri o cartao na memoria local do celular
                                Usuario_local usuario_local = new Usuario_local();
                                usuario_local.setCartao(cartao);
                                usuario_local.setNomeassociado(campos.getString("nome"));
                                usuario_local.setMescorrente(Mes_Corrente);
                                usuario_local.setSenhaassoc(senha);
                                if (n_cartao.equals("")) {
                                    myAppDatabase.usuario_localDao().insert(usuario_local);
                                } else if (!n_cartao.equals(editTextCartao.getText().toString())) {
                                    myAppDatabase.usuario_localDao().update(id_cartao, editTextCartao.getText().toString(), campos.getString("nome"), Mes_Corrente);
                                }
                                // Inseri o cartao na memoria local do celular
                                // Envia o obejto dados do Associado para atctivity home2activity.java

                                String cartao = campos.getString("cod_cart");
                                String email  = campos.getString("email");
                                String senha  = campos.getString("senha");
                                matricula     = campos.getString("matricula");
                                empregador    = campos.getString("empregador");
                                email_aux     = campos.getString("email");
                                if(!email.equals("")) {
                                    mAuth.createUserWithEmailAndPassword(email, senha).
                                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if (task.isSuccessful()) {
                                                        user = mAuth.getCurrentUser();
                                                        updateUI(user);
                                                        entrar_por_email_senha(email, senha);
                                                        //get_Token();
                                                    } else {
                                                        //get_Token();
                                                        String errorMessage = task.getException().getMessage();
                                                    }
                                                }
                                            });
                                }
                                Intent intent = new Intent(MainActivity.this, Home2ActivityMenu.class);
                                intent.putExtra("matricula", campos.getString("matricula"));
                                intent.putExtra("nome", campos.getString("nome"));
                                intent.putExtra("empregador", campos.getString("empregador"));
                                intent.putExtra("cartao", campos.getString("cod_cart"));
                                intent.putExtra("limite", campos.getString("limite"));
                                intent.putExtra("cpf", campos.getString("cpf"));
                                intent.putExtra("email", campos.getString("email"));
                                intent.putExtra("cel", campos.getString("cel"));
                                intent.putExtra("cep", campos.getString("cep"));
                                intent.putExtra("endereco", campos.getString("endereco"));
                                intent.putExtra("numero", campos.getString("numero"));
                                intent.putExtra("bairro", campos.getString("bairro"));
                                intent.putExtra("cidade", campos.getString("cidade"));
                                intent.putExtra("estado", campos.getString("uf"));
                                intent.putExtra("celzap", campos.getString("celwatzap"));
                                intent.putExtra("senha", campos.getString("senha"));
                                intent.putExtra("cod_situacao2", campos.getString("situacao"));
                                intent.putExtra("nome_divisao", campos.getString("nome_divisao"));

                                intent.putExtra("Mes_Corrente", Mes_Corrente);
                                intent.putExtra("cartao", cartao);
                                intent.putExtra("senha", senha);
                                intent.putExtra("rotulo_associado",rotulo_associado);
                                intent.putExtra("novo",novo);
                                startActivity(intent);

                            } else if (resultado.equals("6")) {
                                progressBar.setVisibility(View.GONE);
                                new AestheticDialog.Builder(MainActivity.this, DialogStyle.FLAT, DialogType.ERROR)
                                        .setTitle("Atenção!").setMessage("Senha errada!").setCancelable(false).setGravity(Gravity.CENTER).setAnimation(DialogAnimation.SHRINK).show();

                            } else if (resultado.equals("2") || resultado.equals("3")) {
                                progressBar.setVisibility(View.GONE);
                                new AestheticDialog.Builder(MainActivity.this, DialogStyle.FLAT, DialogType.ERROR)
                                        .setTitle("Atenção!").setMessage("Cartão não encontrado!").setCancelable(false).setGravity(Gravity.CENTER).setAnimation(DialogAnimation.SHRINK).show();

                            } else if (resultado.equals("0")) {
                                progressBar.setVisibility(View.GONE);
                                new AestheticDialog.Builder(MainActivity.this, DialogStyle.FLAT, DialogType.ERROR)
                                        .setTitle("Atenção!").setMessage("Cartão bloqueado!").setCancelable(false).setGravity(Gravity.CENTER).setAnimation(DialogAnimation.SHRINK).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, volleyError -> {
                String message = null; // error message, show it in toast or dialog, whatever you want
                if (volleyError instanceof NetworkError || volleyError instanceof AuthFailureError || volleyError instanceof TimeoutError) {
                    progressBar.setVisibility(View.GONE);
                    message = "Não está conectado a Internet";
                    new AestheticDialog.Builder(MainActivity.this, DialogStyle.CONNECTIFY, DialogType.ERROR).
                            setTitle("Atenção!").setMessage("Não está conectado a Internet!").setCancelable(false).setGravity(Gravity.CENTER).setAnimation(DialogAnimation.SHRINK).show();

                } else if (volleyError instanceof ServerError) {
                    message = "Servidor indisponivel. por favor tente dentro de alguns minutos";
                } else if (volleyError instanceof ParseError) {
                    message = "Erro gerado! por favor, tente mais tarde";
                }
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }) {
                @Override
                public Map<String, String> getParams() {
                    params = new HashMap<>();
                    params.put("cartao", cartao);
                    params.put("senha", senha);
                    return params;
                }
            };
            requestQueue.add(stringRequest);
        }
        // fim *********************************************
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
        E_usuario = currentUser != null;
    }
    private void entrar_por_email_senha(String email, String password){
        if(!email.equals("null") && !password.equals("null")){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (!task.isSuccessful()) {
                                updateUI(user);
                            }else {
                                updateUI(null);
                            }
                        }
                    });
        }
    }
    private void updateUI(FirebaseUser user){
        if (user == null) {
            mAuth.signInAnonymously( ).addOnCompleteListener(task -> {
                if (task.isSuccessful( )) {
                    FirebaseUser user1 = mAuth.getCurrentUser();
                    updateUI(user1);
                    //Toast.makeText(getBaseContext( ), "sucesso", Toast.LENGTH_LONG).show( );
                } else {
                    updateUI(null);
                    //Toast.makeText(getBaseContext( ), "erro", Toast.LENGTH_LONG).show( );
                }
            });
        }
    }
    public void atualiza_token_associado(String matricula,String empregador, String token) {
        String urlm = getResources( ).getString(R.string.HOST) + "associado_atualiza_token.php";
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,urlm,
                response -> {
                }, error -> {
        }) {
            @Override
            public Map<String, String> getParams() {
                params = new HashMap<>();
                params.put("token", token);
                params.put("matricula", matricula);
                params.put("empregador", empregador);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void caputra_mescorrente() {

        String urlm = getResources().getString(R.string.HOST) + "meses_corrente_app.php";
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlm,
                response -> {
                    try {
                        JSONArray arr = new JSONArray(response);
                        for (int i = 0; i < arr.length(); i++) {
                            Mes_Corrente = arr.getJSONObject(i).getString("abreviacao");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, volleyError -> {
            String message = null; // error message, show it in toast or dialog, whatever you want
            if (volleyError instanceof NetworkError || volleyError instanceof AuthFailureError || volleyError instanceof TimeoutError) {
                message = "Sua internet pode estar lenta ou não está conectado a Internet";
            } else if (volleyError instanceof ServerError) {
                message = "Servidor indisponivel. por favor tente dentro de alguns minutos";
            } else if (volleyError instanceof ParseError) {
                message = "Erro gerado! por favor, tente mais tarde";
            }
            //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
        );
        requestQueue.add(stringRequest);
    }
}
