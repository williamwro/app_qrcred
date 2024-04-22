package br.com.qrcred;

import static br.com.qrcred.AppController.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import br.com.qrcred.convenio.ActivityLoginConvenio;
import br.com.qrcred.databinding.ActivityTelaContainerConvenioBinding;

public class TelaContainerConvenio extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTelaContainerConvenioBinding binding;
    DrawerLayout drawer;
    NavigationView navigationView;
    String razaosocial;
    String nome_fantasia;
    String cod_convenio;
    String cnpj;
    String cpf;
    String email;
    String senha;
    String cartao;
    String matricula;
    String empregador;
    String email_firebase;
    View headerView;
    TextView convenioHeader;
    TextView cnpjHeader;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser user;
    RequestQueue requestQueue;
    FirebaseAuth mAuth;
    String UID_;
    String newToken;
    String categoria;
    public Map<String, String> params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityTelaContainerConvenioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome2ActivityMenuConv.toolbar);

        drawer = binding.drawerLayoutConvenio;
        navigationView = binding.navView;

        navigationView.getMenu().findItem(R.id.nav_sair).setOnMenuItemClickListener(menuItem -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Deseja sair?");
            builder.setCancelable(false);
            // Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    Intent intent = new Intent(getApplicationContext(), ActivityLoginConvenio.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                    //Toast.makeText(Home2ActivityMenu.this, "Cancelado", Toast.LENGTH_SHORT).show();
                }
            });
            builder.create().show();
            return true;
        });
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_lancamento, R.id.nav_meusdadosconv, R.id.nav_minhasvendas, R.id.nav_minhalocalizacao,R.id.nav_tabelarecebimentos)
                .setOpenableLayout(drawer)
                .build();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home2_activity_menu_conv);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Intent intent  = getIntent();
        cod_convenio  = intent.getStringExtra("cod_convenio");
        razaosocial   = intent.getStringExtra("razaosocial");
        nome_fantasia = intent.getStringExtra("nome_fantasia");
        cnpj          = intent.getStringExtra("cnpj");
        cpf           = intent.getStringExtra("cpf");
        email         = intent.getStringExtra("email");
        senha         = intent.getStringExtra("senha");
        cartao        = intent.getStringExtra("cartao");
        categoria     = intent.getStringExtra("id_categoria");
        headerView = navigationView.getHeaderView(0);

        convenioHeader = headerView.findViewById(R.id.tvConvenioHeader);
        convenioHeader.setText(razaosocial);

        cnpjHeader = headerView.findViewById(R.id.tvCnpjHeader);
        String cartao_;
        if(cnpj != null && !cnpj.equals("")){
            cartao_ = "CNPJ : " + cnpj;
        }else{
            cartao_ = "CPF : " + cpf;
        }
        cnpjHeader.setText(cartao_);

        inicializarFirebase();


        Locale BRAZIL = new Locale("pt","BR");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",BRAZIL);
        Date data = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        String data_completa = dateFormat.format(data_atual);
        newToken = FirebaseMessaging.getInstance().getToken().toString();
        Map<String, Object> docData = new HashMap<>();
        docData.put("codigo",cod_convenio);
        docData.put("cpf",cpf);
        docData.put("cnpj",cnpj);
        docData.put("email",email);
        docData.put("data_criado", data_completa);
        docData.put("token_convenio",newToken);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        get_Token();
    }
    private void inicializarFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }
    public void get_Token(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        //System.out.println("Fetching FCM registration token failed");
                        return;
                    }
                    // Get new FCM registration token
                    newToken = task.getResult();
                    if(mAuth.getCurrentUser() != null){
                        // Check if user is signed in (non-null) and update UI accordingly.
                        FirebaseUser currentUser = mAuth.getCurrentUser();
                        if(!email.equals("")) {
                            entrar_por_email_senhah(email, senha);
                        }else{
                            updateUIh(currentUser);
                        }
                        //newToken = FirebaseMessaging.getInstance().getToken().toString();
                        Locale BRAZIL = new Locale("pt","BR");
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",BRAZIL);
                        Date data = new Date();
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(data);
                        Date data_atual = cal.getTime();
                        String data_completa = dateFormat.format(data_atual);
                        Map<String, Object> docData = new HashMap<>();
                        docData.put("cartao",cartao);
                        docData.put("cpf",cpf);
                        docData.put("email",email);
                        docData.put("data_criado", data_completa);
                        docData.put("token",newToken);
                        mAuth = FirebaseAuth.getInstance();

                        UID_ = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                        firebaseFirestore.collection("usuario_convenio")
                                .document(UID_)
                                .set(docData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d(TAG, "DocumentSnapshot successfully written!");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w(TAG, "Error writing document", e);
                                    }
                                });
                        atualiza_token_convenioh(cod_convenio,newToken);
                    }
                    // Log and toast
                    //Toast.makeText(getContext(), "Seu dispositivo token registrado é" + newToken   , Toast.LENGTH_SHORT).show();
                });
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home2_activity_menu_conv);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
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
