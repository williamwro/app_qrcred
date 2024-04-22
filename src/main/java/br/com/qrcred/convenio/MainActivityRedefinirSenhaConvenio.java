package br.com.qrcred.convenio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

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
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.qrcred.Convenio_local;
import br.com.qrcred.R;
import br.com.qrcred.RoomDBConvenio;

public class MainActivityRedefinirSenhaConvenio extends AppCompatActivity {
    public static FirebaseAuth mAuth;
    public String mensagem_email;
    private Button btnRedefinir;
    private Button btnConfirmarEmail;
    private Button btnAutenticar;

    private EditText editEmailRedefinir;
    private EditText editTitular;
    RequestQueue requestQueue;
    public String password;
    public String email_app;
    private Map<String, String> params;

    public String cod_convenio;

    public String empregador;
    public String razao_social;
    public String email_digitado;
    String newToken;
    public FirebaseUser mUser;
    public static RoomDBConvenio myAppDatabase_Conv;
    public List<Convenio_local> usuario_locals_conv;
    public String senha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_redefinir_senha_convenio);

        btnRedefinir = findViewById(R.id.btnConvenioRecriarSenha);
        editEmailRedefinir = findViewById(R.id.editEmailRedefinirConv);
        btnConfirmarEmail = findViewById(R.id.btnConfirmarEmailConv);
        btnAutenticar = findViewById(R.id.btnAutenticarConv);
        editTitular = findViewById(R.id.editTitularConv);
        final Intent intent = getIntent( );
        Toolbar toolbar = findViewById(R.id.toolbar_menu);
        setSupportActionBar(toolbar);
        btnAutenticar.setEnabled(false);
        btnRedefinir.setEnabled(false);
        editTitular.setEnabled(false);

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
        getSupportActionBar().setTitle("QRCRED");
        //setSupportActionBar(binding.toolbar);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        btnConfirmarEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                busca_senha(intent.getStringExtra("usuario"));
            }
        });

        btnAutenticar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //busca_senha(intent.getStringExtra("cartao"));
                entrar_por_email_senha(email_app,password);
                reautenticar(mUser,email_app);
                btnAutenticar.setEnabled(false);
                btnRedefinir.setEnabled(true);
            }
        });

        btnRedefinir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                int randomNum = random.nextInt((999999 - 111111) + 1) + 111111;
                String novasenha = String.valueOf(randomNum);
                String email_destino = Objects.requireNonNull(editEmailRedefinir.getText()).toString().trim();

                redefinirSenha(mUser,novasenha,email_destino,cod_convenio);

            }
        });
        myAppDatabase_Conv = RoomDBConvenio.getInstance(getApplicationContext());
        usuario_locals_conv = myAppDatabase_Conv.convenio_localDao().getAll();
        for (Convenio_local usr : usuario_locals_conv) {
            senha = usr.getSenha_convenio();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void busca_senha(String usuario) {
        String urlm = getResources().getString(R.string.HOST) + "localiza_senha_convenio_app.php";
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlm,
                response -> {
                    JSONObject campos;
                    try {
                        campos = new JSONObject(response);
                        String existe_convenio;
                        existe_convenio = campos.getString("existe_convenio");
                        if(existe_convenio.equals("true")) {
                            email_app = campos.getString("email");
                            password = campos.getString("password");
                            cod_convenio = campos.getString("cod_convenio");
                            razao_social = campos.getString("razaosocial");
                            if (editEmailRedefinir.getText().toString().trim().equals(email_app.trim())) {
                                editTitular.setText(razao_social);
                                btnConfirmarEmail.setEnabled(false);
                                btnAutenticar.setEnabled(true);
                                editEmailRedefinir.setEnabled(false);
                                AestheticDialog.Builder dialog1 =
                                        new AestheticDialog.Builder(MainActivityRedefinirSenhaConvenio.this,
                                                DialogStyle.FLAT, DialogType.SUCCESS);
                                dialog1.setTitle("Atenção!");
                                dialog1.setMessage("E-mail confirmado com sucesso!");
                                dialog1.setCancelable(false);
                                dialog1.setDarkMode(true);
                                dialog1.setGravity(Gravity.CENTER);
                                dialog1.setAnimation(DialogAnimation.SHRINK);
                                dialog1.setOnClickListener(new OnDialogClickListener() {
                                    @Override
                                    public void onClick(@NonNull AestheticDialog.Builder builder) {

                                        AestheticDialog.Builder dialog1 =
                                                new AestheticDialog.Builder(MainActivityRedefinirSenhaConvenio.this,
                                                        DialogStyle.RAINBOW, DialogType.INFO);
                                        dialog1.setTitle("Atenção!");
                                        dialog1.setMessage("Click no botão Confirmar o nome do convenio!");
                                        dialog1.setCancelable(false);
                                        dialog1.setDarkMode(true);
                                        dialog1.setGravity(Gravity.CENTER);
                                        dialog1.setAnimation(DialogAnimation.SHRINK);
                                        dialog1.show();
                                        builder.dismiss();

                                    }
                                });
                                dialog1.show();
                            } else {
                                AestheticDialog.Builder dialog1 =
                                        new AestheticDialog.Builder(MainActivityRedefinirSenhaConvenio.this,
                                                DialogStyle.RAINBOW, DialogType.ERROR);
                                dialog1.setTitle("Atenção!");
                                dialog1.setMessage("O e-mail digitado não é igual ao e-mail cadastrado!");
                                dialog1.setCancelable(false);
                                dialog1.setDarkMode(true);
                                dialog1.setGravity(Gravity.CENTER);
                                dialog1.setAnimation(DialogAnimation.SHRINK);
                                dialog1.show();
                            }
                        }else{
                            AestheticDialog.Builder dialog1 =
                                    new AestheticDialog.Builder(MainActivityRedefinirSenhaConvenio.this,
                                            DialogStyle.FLAT, DialogType.ERROR);
                            dialog1.setTitle("Atenção!");
                            dialog1.setMessage("O usuário está incorreto !!!");
                            dialog1.setCancelable(false);
                            dialog1.setDarkMode(true);
                            dialog1.setGravity(Gravity.CENTER);
                            dialog1.setAnimation(DialogAnimation.SHRINK);
                            dialog1.setOnClickListener(new OnDialogClickListener() {
                                @Override
                                public void onClick(@NonNull AestheticDialog.Builder builder) {
                                    Intent intent = new Intent(MainActivityRedefinirSenhaConvenio.this, ActivityLoginConvenio.class);
                                    startActivity(intent);
                                }
                            });
                            dialog1.show();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
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
            Toast.makeText(MainActivityRedefinirSenhaConvenio.this, message, Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getParams() {
                params = new HashMap<>();
                params.put("usuario", usuario);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void reautenticar(FirebaseUser firebaseUser, String email_app) {
        if(senha != null) {
            entrar_por_email_senha(email_app, senha);
            AuthCredential credential = EmailAuthProvider.getCredential(email_app, password);
            firebaseUser.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        AestheticDialog.Builder dialog1 =
                                new AestheticDialog.Builder(MainActivityRedefinirSenhaConvenio.this,
                                        DialogStyle.FLAT, DialogType.SUCCESS);
                        dialog1.setTitle("Atenção!");
                        dialog1.setMessage("O nome do convenio foi comfirmado com sucesso!");
                        dialog1.setCancelable(false);
                        dialog1.setDarkMode(true);
                        dialog1.setGravity(Gravity.CENTER);
                        dialog1.setAnimation(DialogAnimation.SHRINK);
                        dialog1.setOnClickListener(new OnDialogClickListener() {
                            @Override
                            public void onClick(@NonNull AestheticDialog.Builder builder) {
                                AestheticDialog.Builder dialog1 =
                                        new AestheticDialog.Builder(MainActivityRedefinirSenhaConvenio.this,
                                                DialogStyle.RAINBOW, DialogType.INFO);
                                dialog1.setTitle("Atenção!");
                                dialog1.setMessage("Click no botão Redefinir a senha!");
                                dialog1.setCancelable(false);
                                dialog1.setDarkMode(true);
                                dialog1.setGravity(Gravity.CENTER);
                                dialog1.setAnimation(DialogAnimation.SHRINK);
                                dialog1.show();
                                builder.dismiss();
                            }
                        });
                        dialog1.show();
                    }
                }

            });
        }else{

            AestheticDialog.Builder dialog1 =
                    new AestheticDialog.Builder(MainActivityRedefinirSenhaConvenio.this,
                            DialogStyle.FLAT, DialogType.ERROR);
            dialog1.setTitle("Atenção!");
            dialog1.setMessage("Identificanos que voce nunca fez login do app QrCred neste celular, é necessário que se faça o primeiro login, antes de redefinir a senha.");
            dialog1.setCancelable(false);
            dialog1.setDarkMode(true);
            dialog1.setGravity(Gravity.CENTER);
            dialog1.setAnimation(DialogAnimation.SHRINK);
            dialog1.setOnClickListener(new OnDialogClickListener() {
                @Override
                public void onClick(@NonNull AestheticDialog.Builder builder) {
                    Intent intent = new Intent(MainActivityRedefinirSenhaConvenio.this, ActivityLoginConvenio.class);
                    startActivity(intent);
                }
            });
            dialog1.show();
        }
    }
    private void redefinirSenha(FirebaseUser firebaseUser,String novasenha,String emaildestino,String matricula) {
        firebaseUser.updatePassword(novasenha).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    atualiza_senha_convenio(matricula,novasenha);

                    AestheticDialog.Builder dialog1 =
                            new AestheticDialog.Builder(MainActivityRedefinirSenhaConvenio.this,
                                    DialogStyle.FLAT, DialogType.SUCCESS);
                    dialog1.setTitle("Parabens!");
                    dialog1.setMessage("A nova senha foi encaminhada no e-mail cadastrado: "+emaildestino+" !!!");
                    dialog1.setCancelable(false);
                    dialog1.setDarkMode(true);
                    dialog1.setGravity(Gravity.CENTER);
                    dialog1.setAnimation(DialogAnimation.SHRINK);
                    dialog1.setOnClickListener(new OnDialogClickListener() {
                        @Override
                        public void onClick(@NonNull AestheticDialog.Builder builder) {
                            Intent intent = new Intent(MainActivityRedefinirSenhaConvenio.this, ActivityLoginConvenio.class);
                            startActivity(intent);
                        }
                    });
                    dialog1.show();

                    SendEmail(novasenha, emaildestino);
                }else{
                    try{
                        throw task.getException();
                    }catch (Exception e){
                        Toast.makeText(MainActivityRedefinirSenhaConvenio.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    public void atualiza_senha_convenio(String cod_convenio,String senha) {
        String urlm = getResources( ).getString(R.string.HOST) + "atualiza_senha_app_convenio.php";
        requestQueue = Volley.newRequestQueue(MainActivityRedefinirSenhaConvenio.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,urlm,
                response -> {
                }, error -> {
        }) {
            @Override
            public Map<String, String> getParams() {
                params = new HashMap<>();
                params.put("senha", senha);
                params.put("cod_convenio", cod_convenio);
                return params;
            }
        };

        requestQueue.add(stringRequest);
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
                    .addOnCompleteListener(task -> {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (!task.isSuccessful()) {
                            updateUI(user);
                        }
                    });
        }
    }
    public void atualiza_token_associado(String matricula,String empregador, String token) {
        String urlm = getResources( ).getString(R.string.HOST) + "associado_atualiza_token.php";
        requestQueue = Volley.newRequestQueue(MainActivityRedefinirSenhaConvenio.this);
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
    public void SendEmail(String senha_x, String Email_destino){

        try {
            email_html(senha_x);
            String stringSenderEmail = "qrcredq@gmail.com";
            String stringPasswordSenderEmail = "vsmn dlbl acsz zukc";

            String stringHost = "smtp.gmail.com";

            Properties properties = System.getProperties();

            properties.put("mail.smtp.host", stringHost);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            javax.mail.Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(stringSenderEmail, stringPasswordSenderEmail);
                }
            });

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(Email_destino));

            mimeMessage.setSubject("Nova senha login App QrCred");


            mimeMessage.setContent(mensagem_email,"text/html");

            Thread thread = new Thread(() -> {
                try {
                    Transport.send(mimeMessage);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            thread.start();
            //Toast.makeText(MainActivityRedefinirSenhaConvenio.this, "Email enviado com sucesso", Toast.LENGTH_LONG).show();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private void email_html(String senha_x){
        mensagem_email = "<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Transitional//EN' 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd'>"+
                "<html xmlns='http://www.w3.org/1999/xhtml' xmlns:o='urn:schemas-microsoft-com:office:office'>"+
                "<head>"+
                "<meta charset='UTF-8'>"+
                "<meta content='width=device-width, initial-scale=1' name='viewport'>"+
                "<meta name='x-apple-disable-message-reformatting'>"+
                "<meta http-equiv='X-UA-Compatible' content='IE=edge'>"+
                "<meta content='telephone=no' name='format-detection'>"+
                "<title></title>"+
                "<!--[if (mso 16)]>"+
                "<style type='text/css'>"+
                "a {text-decoration: none;}"+
                "</style>"+
                "<![endif]-->"+
                "<!--[if gte mso 9]><style>sup { font-size: 100% !important; }</style><![endif]-->"+
                "<!--[if gte mso 9]>"+
                "<xml>"+
                "<o:OfficeDocumentSettings>"+
                "<o:AllowPNG></o:AllowPNG>"+
                "<o:PixelsPerInch>96</o:PixelsPerInch>"+
                "</o:OfficeDocumentSettings>"+
                "</xml>"+
                "<![endif]-->"+
                "<!--[if !mso]><!-- -->"+
                "<link href='https://fonts.googleapis.com/css?family=Lato:400,400i,700,700i' rel='stylesheet'>"+
                "<!--<![endif]-->"+
                "</head>"+

                "<body>"+
                "<div class='es-wrapper-color'>"+
                "<!--[if gte mso 9]>"+
                "<v:background xmlns:v='urn:schemas-microsoft-com:vml' fill='t'>"+
                "<v:fill type='tile' color='#f4f4f4'></v:fill>"+
                "</v:background>"+
                "<![endif]-->"+
                "<table class='es-wrapper' width='100%' cellspacing='0' cellpadding='0'>"+
                "<tbody>"+
                "<tr class='gmail-fix' height='0'>"+
                "<td>"+
                "<table width='600' cellspacing='0' cellpadding='0' border='0' align='center'>"+
                "<tbody>"+
                "<tr>"+
                "<td cellpadding='0' cellspacing='0' border='0' style='line-height: 1px; min-width: 600px;' height='0'><img src='' style='display: block; max-height: 0px; min-height: 0px; min-width: 600px; width: 600px;' alt width='600' height='1'></td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "<tr>"+
                "<td class='esd-email-paddings' valign='top'>"+
                "<table class='es-header esd-header-popover' cellspacing='0' cellpadding='0' align='center'>"+
                "<tbody>"+
                "<tr>"+
                "<td esd-custom-block-id='6339' align='center' bgcolor='#999999' style='background-color: #999999;'>"+
                "<table class='es-header-body' width='600' cellspacing='0' cellpadding='0' align='center'>"+
                "<tbody>"+
                "<tr>"+
                "<td class='esd-structure es-p20t es-p10b es-p10r es-p10l' align='left' bgcolor='#999999' style='background-color: #999999;'>"+
                "<table width='100%' cellspacing='0' cellpadding='0'>"+
                "<tbody>"+
                "<tr>"+
                "<td class='esd-container-frame' width='580' valign='top' align='center'>"+
                "<table width='100%' cellspacing='0' cellpadding='0'>"+
                " <tbody>"+
                "<tr>"+
                "<td align='center' class='esd-block-image' style='font-size: 0px;'><a target='_blank'><img class='adapt-img' src='' alt style='display: block;' width='200'></a></td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "<table class='es-content' cellspacing='0' cellpadding='0' align='center'>"+
                "<tbody>"+
                "<tr>"+
                "<td style='background-color: #999999;' esd-custom-block-id='6340' bgcolor='#999999' align='center'>"+
                "<table class='es-content-body' style='background-color: transparent;' width='600' cellspacing='0' cellpadding='0' align='center'>"+
                "<tbody>"+
                "<tr>"+
                "<td class='esd-structure' align='left'>"+
                "<table width='100%' cellspacing='0' cellpadding='0'>"+
                "<tbody>"+
                "<tr>"+
                "<td class='esd-container-frame' width='600' valign='top' align='center'>"+
                "<table style='background-color: #ffffff; border-radius: 4px; border-collapse: separate;' width='100%' cellspacing='0' cellpadding='0' bgcolor='#ffffff'>"+
                "<tbody>"+
                "<tr>"+
                "<td class='esd-block-text es-p35t es-p5b es-p30r es-p30l' align='center'>"+
                "<h1>Senha de acesso ao App QrCred, redefinida com sucesso!</h1>"+
                "</td>"+
                "</tr>"+
                "<tr>"+
                "<td class='esd-block-spacer es-p5t es-p5b es-p20r es-p20l' bgcolor='#ffffff' align='center' style='font-size:0'>"+
                "<table width='100%' height='100%' cellspacing='0' cellpadding='0' border='0'>"+
                "<tbody>"+
                "<tr>"+
                "<td style='border-bottom: 1px solid #ffffff; background: rgba(0, 0, 0, 0) none repeat scroll 0% 0%; height: 1px; width: 100%; margin: 0px;'></td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "<table class='es-content esd-footer-popover' cellspacing='0' cellpadding='0' align='center'>"+
                "<tbody>"+
                "<tr>"+
                "<td align='center'>"+
                "<table class='es-content-body' style='background-color: transparent;' width='600' cellspacing='0' cellpadding='0' align='center'>"+
                "<tbody>"+
                "<tr>"+
                "<td class='esd-structure' align='left'>"+
                "<table width='100%' cellspacing='0' cellpadding='0'>"+
                "<tbody>"+
                "<tr>"+
                "<td class='esd-container-frame' width='600' valign='top' align='center'>"+
                "<table style='border-radius: 4px; border-collapse: separate; background-color: #ffffff;' width='100%' cellspacing='0' cellpadding='0' bgcolor='#ffffff'>"+
                "<tbody>"+
                "<tr>"+
                "<td class='esd-block-text es-p20t es-p20b es-p30r es-p30l es-m-txt-l' bgcolor='#ffffff' align='left'>"+
                "<p>Segue a nova senha para acesso ao App e site QrCred.</p>"+
                "</td>"+
                "</tr>"+
                "<tr>"+
                "<td class='esd-block-text es-p20t es-p30r es-p30l es-m-txt-l' align='left'>"+
                "Senha :&nbsp;<h2>"+ senha_x + "</h2></p>"+
                "</td>"+
                "</tr>"+
                "<tr>"+
                "<td class='esd-block-text es-p20t es-p40b es-p30r es-p30l es-m-txt-l' align='left'>"+
                "<p>Atenciosamente,</p>"+
                "<p>QrCred</p>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</td>"+
                "</tr>"+
                "</tbody>"+
                "</table>"+
                "</div>"+
                "</body>"+

                "</html>";
    }
}


