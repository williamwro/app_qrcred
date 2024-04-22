package br.com.qrcred;

import static br.com.qrcred.AppController.TAG;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import br.com.qrcred.databinding.ActivityHome2MenuBinding;

public class Home2ActivityMenu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHome2MenuBinding binding;
    //****************** NOVO ***************
    String senha;
    String cartao;
    String nome_divisao;
    String nome_associado_string;
    DrawerLayout drawer;
    NavigationView navigationView;
    TextView cartaoHeader;
    TextView convenioHeader;
    String Mes_Corrente;
    View headerView;
    TextView navUsername;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String email_firebase;
    FirebaseFirestore firebaseFirestore;
    String newToken;
    String UID_;
    String email;
    String matricula;
    String cpf;
    String empregador;
    private Map<String, String> params;

    public RequestQueue requestQueue;

    //****************** NOVO ***************
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHome2MenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarHome2ActivityMenu.toolbar);

        OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();

        drawer = binding.drawerLayout;
        navigationView = binding.navView;

        navigationView.getMenu().findItem(R.id.nav_sair_assoc).setOnMenuItemClickListener(menuItem -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Deseja sair?");
            builder.setCancelable(false);
            // Add the buttons
            builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    // User clicked OK button
                    Intent intent = new Intent(getApplicationContext(), Activity_tela_User.class);
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
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_saldo_assoc, R.id.nav_extrato_assoc, R.id.nav_convenios_assoc, R.id.nav_sair_assoc,R.id.nav_qrcode_assoc,R.id.nav_dados_assoc,R.id.nav_antecipacao_assoc)
                .setOpenableLayout(drawer)
                .build();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home2_activity_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //****************** NOVO ***************
        Intent intent           = getIntent();

        nome_associado_string   = intent.getStringExtra("nome");
        cartao                  = intent.getStringExtra("cartao");
        nome_divisao            = intent.getStringExtra("nome_divisao");
        Mes_Corrente            = intent.getStringExtra("Mes_Corrente");
        email                   = intent.getStringExtra("email");
        matricula               = intent.getStringExtra("matricula");
        cpf                     = intent.getStringExtra("cpf");
        empregador              = intent.getStringExtra("empregador");
        senha                   = intent.getStringExtra("senha");

        headerView = navigationView.getHeaderView(0);

        navUsername = (TextView) headerView.findViewById(R.id.tvAssociadoHeader);
        navUsername.setText(nome_associado_string);

        cartaoHeader = (TextView) headerView.findViewById(R.id.tvCartaoHeader);
        String cartao_ = "Cartão : " + cartao;
        cartaoHeader.setText(cartao_);

        convenioHeader = (TextView) headerView.findViewById(R.id.tvNomeConvenioHeader);
        String convenio_ = "Convenio : " + nome_divisao;
        convenioHeader.setText(convenio_);
        //****************** NOVO ***************

        firebaseFirestore = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

        get_Token();

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                } else {
                    // Se o drawer não estiver aberto, chama o comportamento padrão de onBackPressed
                }
            }
        };
        getOnBackPressedDispatcher().addCallback(this, callback);
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
                            entrar_por_email_senha(email, senha);
                        }else{
                            updateUI(currentUser);
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
                        firebaseFirestore.collection("usuario_cartao")
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
                        atualiza_token_associado(matricula,empregador,newToken);
                    }
                    // Log and toast
                    //Toast.makeText(getContext(), "Seu dispositivo token registrado é" + newToken   , Toast.LENGTH_SHORT).show();
                });
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home2_activity_menu);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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
    private void entrar_por_email_senha(String email, String password){
        if(!email.equals("null") && !password.equals("null")){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (task.isSuccessful()) {
                                updateUI(user);
                            }else {
                                updateUI(null);
                            }
                        }
                    });
        }
    }
    public void atualiza_token_associado(String matricula,String empregador, String token) {
        String urlm = getResources( ).getString(R.string.HOST) + "associado_atualiza_token.php";
        requestQueue = Volley.newRequestQueue(Home2ActivityMenu.this);
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
}
