package br.com.qrcred.convenio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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
/*import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;*/
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

import org.json.JSONException;
import org.json.JSONObject;

import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.qrcred.Convenio_local;
import br.com.qrcred.Menu2MainActivity;
import br.com.qrcred.R;
import br.com.qrcred.RoomDBConvenio;
import br.com.qrcred.TelaContainerConvenio;


public class ActivityLoginConvenio extends AppCompatActivity  {
    Button btnEntrar;
    Button btnCadastrarConv;
    TextView textViewManualConvenio;
    TextView rotuloConvenio;
    EditText editTextUsuario;
    EditText editTextSenha;
    public String usuario;
    public String senha;
    ProgressBar progressBar;
    RequestQueue requestQueue;
    public String cod_convenio;
    public String razaosocial;
    public String nome_fantasia;
    public String endereco;
    public String bairro;
    public String cidade;
    public String cnpj;
    public String cpf;
    String cartao;
    public String parcelas;
    public String divulga;
    public String pede_senha;
    public String numero;
    public String cep;
    public String cel;
    public String tel;
    public String email;
    public String estado;
    public String latitude;
    public String longitude;
    public String id_categoria;
    public String contato;
    public String aceito_termo;
    private Map<String, String> params;
    //public AdView mAdView;
    public static RoomDBConvenio myAppDatabase;
    public List<Convenio_local> convenio_locals;
    public int idc_logconv;
    public String n_logconv="";
    public String rotulo_convenio="";
    public String n_usuario_conv;
    String mes_corrente;
    public TextView tvEsqueciSenhaConv;
    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    String UID_;
    FirebaseUser user;
    String newToken;
    @Override
    protected void onStart(){
        super.onStart();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_convenio);
       /* MobileAds.initialize(this, initializationStatus -> {
        });
        mAdView = findViewById(R.id.adView3);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);*/
        // ******** Inicio O HttpsUrlConnection está configurado para confiar em qualquer certificado.
        //trustEveryone();
        // ********   Fim  O HttpsUrlConnection está configurado para confiar em qualquer certificado.
        btnEntrar = findViewById(R.id.btnEntrar_conv);

        editTextUsuario = findViewById(R.id.editTextUsuario_conv);
        editTextSenha = findViewById(R.id.editTextSenha_conv);
        rotuloConvenio  = findViewById(R.id.TvRotuloConvenio);
        tvEsqueciSenhaConv = findViewById(R.id.tvEsquecisenhaConv);

        myAppDatabase = RoomDBConvenio.getInstance(getApplicationContext());

        convenio_locals = myAppDatabase. convenio_localDao().getAll();

        for(Convenio_local usrc : convenio_locals){
            idc_logconv = usrc.getID();
            n_logconv = usrc.getLogconv();
            rotulo_convenio = usrc.getNomeconvenio();
        }
        if(rotulo_convenio.equals("")){
            rotuloConvenio.setText("Login do Convenio");
        }else{
            rotuloConvenio.setText(rotulo_convenio);
        }
        editTextUsuario.setText(n_logconv);
        editTextSenha.setText("");

        btnEntrar.setOnClickListener(v -> {
            usuario = editTextUsuario.getText().toString();
            senha = editTextSenha.getText().toString();
            if (usuario.equals("")) {
                new AestheticDialog.Builder(ActivityLoginConvenio.this, DialogStyle.FLAT, DialogType.ERROR)
                        .setTitle("Atenção!").setMessage("Informe o usuário!").setCancelable(false).setGravity(Gravity.CENTER).setAnimation(DialogAnimation.SHRINK).show();
                progressBar.setVisibility(View.GONE);
            } else if (senha.equals("")) {
                new AestheticDialog.Builder(ActivityLoginConvenio.this, DialogStyle.FLAT, DialogType.ERROR)
                        .setTitle("Atenção!").setMessage("Informe a senha!").setCancelable(false).setGravity(Gravity.CENTER).setAnimation(DialogAnimation.SHRINK).show();
                progressBar.setVisibility(View.GONE);
            } else {

                try {
                    btnEntrarHome();
                } catch (CertificateException e) {
                    e.printStackTrace();
                }
            }
        });
        progressBar = findViewById(R.id.progressBar_conv);
        progressBar.setVisibility(View.GONE);
        // toolbar
        Toolbar toolbar = findViewById(R.id.toobarLoginConvenio);
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
        getSupportActionBar().setTitle("Login convenio");
        if(editTextUsuario.getText().toString().equals("")) {
            editTextUsuario.requestFocus();
        }else{
            editTextSenha.requestFocus();
        }
        tvEsqueciSenhaConv.setOnClickListener(v -> {
            if(!editTextUsuario.getText().toString().equals("")) {
                Intent intent = new Intent(ActivityLoginConvenio.this, MainActivityRedefinirSenhaConvenio.class);
                intent.putExtra("usuario", (editTextUsuario.getText().toString()));
                startActivity(intent);
            }else{
                AestheticDialog.Builder dialog1 =
                        new AestheticDialog.Builder(ActivityLoginConvenio.this,
                                DialogStyle.FLAT, DialogType.ERROR);
                dialog1.setTitle("Atenção!");
                dialog1.setMessage("Para recuperar a senha tem que informar o usuário do convenio!");
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
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }
    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();
        Intent intentx;
        intentx = new Intent(ActivityLoginConvenio.this, Menu2MainActivity.class);
        startActivity(intentx);
        return true;
    }
    public void btnEntrarHome() throws CertificateException {
        progressBar.setVisibility(View.VISIBLE);
        n_usuario_conv = editTextUsuario.getText().toString();
        String urlm = getResources().getString(R.string.HOST) + "convenio_autenticar_app.php";
        requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlm,
                response -> {
                    JSONObject campos;
                    try {
                        campos = new JSONObject(response);

                        String resultado = campos.getString("tipo_login");
                        if (resultado.equals("login sucesso")) {
                            progressBar.setVisibility(View.GONE);
                            // Inseri o cartao na memoria local do celular
                            Convenio_local convenio_local = new Convenio_local();
                            convenio_local.setLogconv(n_usuario_conv);
                            convenio_local.setSenha_convenio(senha);
                            convenio_local.setNomeconvenio(campos.getString("razaosocial"));
                            if(n_logconv.equals("")){
                                myAppDatabase.convenio_localDao().insert(convenio_local);
                            }else if(!n_logconv.equals(editTextUsuario.getText().toString())) {
                                myAppDatabase.convenio_localDao().update(idc_logconv,editTextUsuario.getText().toString(),campos.getString("razaosocial"));
                            }
                            cod_convenio  = campos.getString("cod_convenio");
                            razaosocial   = campos.getString("razaosocial");
                            nome_fantasia = campos.getString("nomefantasia");
                            endereco      = campos.getString("endereco");
                            bairro        = campos.getString("bairro");
                            numero        = campos.getString("numero");
                            cep           = campos.getString("cep");
                            cel           = campos.getString("cel");
                            tel           = campos.getString("tel");
                            email         = campos.getString("email");
                            cidade        = campos.getString("cidade");
                            estado        = campos.getString("estado");
                            cnpj          = campos.getString("cnpj");
                            cpf           = campos.getString("cpf");
                            parcelas      = campos.getString("parcela_conv");
                            divulga       = campos.getString("divulga");
                            pede_senha    = campos.getString("pede_senha");
                            latitude      = campos.getString("latitude");
                            longitude     = campos.getString("longitude");
                            id_categoria  = campos.getString("id_categoria");
                            contato       = campos.getString("contato");
                            senha         = campos.getString("senha");
                            aceito_termo  = campos.getString("aceita_termo");
                            mes_corrente = campos.getString("mes_corrente");
                            mAuth.createUserWithEmailAndPassword(email, senha)
                                    .addOnCompleteListener(ActivityLoginConvenio.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {
                                                FirebaseUser user = mAuth.getCurrentUser();
                                                updateUIh(user);
                                                entrar_por_email_senhah(email, senha);
                                                //get_Token();
                                            } else {
                                                //get_Token();
                                                String errorMessage = task.getException().getMessage();
                                            }
                                        }
                                    });
                            Intent intentx;
                            intentx = new Intent(ActivityLoginConvenio.this, TelaContainerConvenio.class);
                            intentx.putExtra("cod_convenio", cod_convenio);
                            intentx.putExtra("razaosocial", razaosocial);
                            intentx.putExtra("nome_fantasia", nome_fantasia);
                            intentx.putExtra("endereco", endereco);
                            intentx.putExtra("bairro", bairro);
                            intentx.putExtra("numero", numero);
                            intentx.putExtra("cep", cep);
                            intentx.putExtra("cidade", cidade);
                            intentx.putExtra("estado", estado);
                            intentx.putExtra("email", email);
                            intentx.putExtra("cnpj", cnpj);
                            intentx.putExtra("cpf", cpf);
                            intentx.putExtra("cel", cel);
                            intentx.putExtra("tel", tel);
                            intentx.putExtra("parcelas", parcelas);
                            intentx.putExtra("divulga", divulga);
                            intentx.putExtra("pede_senha", pede_senha);
                            intentx.putExtra("latitude", latitude);
                            intentx.putExtra("longitude", longitude);
                            intentx.putExtra("id_categoria", id_categoria);
                            intentx.putExtra("contato", contato);
                            intentx.putExtra("senha", senha);
                            intentx.putExtra("aceito_termo", aceito_termo);
                            intentx.putExtra("mes_corrente", mes_corrente);
                            startActivity(intentx);
                        } else if (resultado.equals("login incorreto")) {
                            progressBar.setVisibility(View.GONE);
                            new AestheticDialog.Builder(ActivityLoginConvenio.this, DialogStyle.FLAT, DialogType.ERROR)
                                    .setTitle("Atenção!").setMessage("Usuário do estabelecimento ou senha incorretos!").setCancelable(false).setGravity(Gravity.CENTER).setAnimation(DialogAnimation.SHRINK).show();

                        } else if (resultado.equals("login vazio")) {
                            progressBar.setVisibility(View.GONE);
                            new AestheticDialog.Builder(ActivityLoginConvenio.this, DialogStyle.FLAT, DialogType.ERROR)
                                    .setTitle("Atenção!").setMessage("Informe o Usuário do estabelecimento e a Senha!").setCancelable(false).setGravity(Gravity.CENTER).setAnimation(DialogAnimation.SHRINK).show();

                        }
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }, volleyError -> {
                    String message = null; // error message, show it in toast or dialog, whatever you want
                    if (volleyError instanceof NetworkError || volleyError instanceof AuthFailureError || volleyError instanceof TimeoutError) {
                        message = "Não está conectado a Internet";
                    } else if (volleyError instanceof ServerError) {
                        message = "Servidor indisponivel. por favor tente dentro de alguns minutos";
                    } else if (volleyError instanceof ParseError) {
                        message = "Erro gerado! por favor, tente mais tarde";
                    }
                    Toast.makeText(ActivityLoginConvenio.this, message, Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }){
            @Override
            public Map<String, String> getParams() {
                params = new HashMap<>();
                params.put("userconv", usuario);
                params.put("passconv", senha);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void updateUIh(FirebaseUser user){
        if (user == null) {
            mAuth.signInAnonymously( ).addOnCompleteListener(task -> {
                if (task.isSuccessful( )) {
                    FirebaseUser user1 = mAuth.getCurrentUser();
                    updateUIh(user1);
                    //Toast.makeText(getBaseContext( ), "sucesso", Toast.LENGTH_LONG).show( );
                } else {
                    updateUIh(null);
                    //Toast.makeText(getBaseContext( ), "erro", Toast.LENGTH_LONG).show( );
                }
            });
        }
    }
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void entrar_por_email_senhah(String email, String password){
        if(!email.equals("null") && !password.equals("null")){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (!task.isSuccessful()) {
                            updateUIh(user);
                        }
                    });
        }
    }
    public void atualiza_token_convenioh(String codconvenio,String token) {
        String urlm = getResources( ).getString(R.string.HOST) + "convenio_atualiza_token.php";
        requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,urlm,
                response -> {
                }, error -> {
        }) {
            @Override
            public Map<String, String> getParams() {
                params = new HashMap<>();
                params.put("token", token);
                params.put("codigo", codconvenio);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
