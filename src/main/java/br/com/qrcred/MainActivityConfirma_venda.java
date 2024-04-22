package br.com.qrcred;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.Manifest;
import android.annotation.SuppressLint;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Bundle;

import android.os.VibratorManager;

import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.auth.api.signin.GoogleSignIn;
//mport com.google.android.gms.auth.api.signin.GoogleSignInClient;

import com.google.firebase.messaging.FirebaseMessaging;

import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import br.com.qrcred.service.APIService;
import br.com.qrcred.service.Client;
import br.com.qrcred.service.Data;
import br.com.qrcred.service.MyResponse;
import br.com.qrcred.service.NotificationSender;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.Objects;
import java.util.Random;


public class MainActivityConfirma_venda extends AppCompatActivity {
    TextView tv_Associado;
    TextView tv_ValorExibir;
    TextView tv_RazaoSocial;
    TextView tv_CnpjCpf;
    TextView tv_Endereco;
    TextView tv_Bairro;
    TextView tv_Cidade;
    TextView tv_Parcela;
    TextView tv_Parcela_rotulo;
    TextView tv_Registro;
    TextView tv_Data;
    TextView tv_Hora;
    TextView tv_Mes_desconto;
    TextView tv_Valo_parcela;
    ImageView T_view_background;
    ImageView T_img_share;
    ImageView T_img_print;
    String nome;
    String nome_fantasia;
    String celular;
    String valor_pedido;
    Double valor_parcela_aux;
    Double n_parcela_aux;
    Button btnRetorna;
    Button btnPrint;
    String cod_convenio;
    String razaosocial;
    String endereco;
    String bairro;
    String cidade;
    String cnpj;
    String cpf;
    String parcelas;
    String nparcelas;
    String lancamento;
    String uid;
    String token_associado;
    String email;
    String senha;
    String cartao;
    String categoria;
    String primeiro_mes;
    String mes_corrente;
    private static final int SOLICITAR_PERMISSAO = 1;
    private static final int PERMISSION_SEND_SMS = 1;
    public APIService apiService;
    private TextView info;
    private final Random random = new Random();
    public String datacad;
    public String hora;
    public String valor_pedidox;
    public String valor_pedito_formatado;
    public String valor_parcela_formatado;
    Context mContext;
    String T_m_cel;
    String data_formatada;
    String data_formatada_aux;
    Button btnEnviaSms;
    ConstraintLayout container;

    //private static String serverKey = "AAAAhJ7OrTg:APA91bHqBruVAvQIUOkOXOG8ydDXXD6Y0uelukhUNA992ObnET3w_1n-4AjznbhTQyO1geWlksMqBYo6h4tc-8i3gZK2EWcJuQxFHFW7MFFnNZXn6uv-kuIO59nh2L8FTmDsgKer-1ic";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
   // private GoogleSignInClient client;
    String[] required_permissions = new String[]{
            Manifest.permission.SEND_SMS
    };
    boolean is_store_image_permitted = false;

    String TAG = "Permission";
    @SuppressLint({"SetTextI18n", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_confirma_venda);



        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 200);
        //toneG.startTone(ToneGenerator.TONE_PROP_BEEP2 , 400);
        toneG.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK, 1200);

        Date data = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        Date data_atual = cal.getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat_hora = new SimpleDateFormat("HH:mm");

        String dataFormatada = formataData.format(data);
        String horaatual = dateFormat_hora.format(data_atual);



        tv_Associado = findViewById(R.id.tvAssociado);
        tv_RazaoSocial = findViewById(R.id.tvConvenio);
        tv_CnpjCpf = findViewById(R.id.tvCnpjCpf);
        tv_Endereco = findViewById(R.id.tvEndereco);
        tv_Bairro = findViewById(R.id.tvBairro);
        tv_Cidade = findViewById(R.id.tvCidade);
        tv_Parcela = findViewById(R.id.tvParcela);
        tv_Parcela_rotulo = findViewById(R.id.tvParcelaRotulo);
        tv_Registro = findViewById(R.id.tvRegistro);
        tv_Data = findViewById(R.id.tvData);
        tv_Hora = findViewById(R.id.tvHora);
        tv_ValorExibir = findViewById(R.id.tvValor);
        tv_Mes_desconto = findViewById(R.id.tvMesDesconto);
        tv_Valo_parcela = findViewById(R.id.tvValoParcela);

        btnRetorna = findViewById(R.id.btnRetornaHome);

        //T_img_share = findViewById(R.id.img_share);
        T_img_print = findViewById(R.id.img_print);
        //btnPrint =  findViewById(R.id.btnImprimir);

        btnEnviaSms = findViewById(R.id.btnEnviaSms);

        /*btnEnviarFotoCupon = findViewById(R.id.btnEnviarfotoCupom);*/
        final Intent intent = getIntent();
        tv_Cidade.setText("");
        tv_Associado.setText(intent.getStringExtra("nome"));
        tv_RazaoSocial.setText(intent.getStringExtra("razaosocial"));
        tv_CnpjCpf.setText(intent.getStringExtra("cnpj"));
        tv_Endereco.setText(intent.getStringExtra("endereco"));
        tv_Bairro.setText(intent.getStringExtra("bairro"));
        tv_Cidade.setText(intent.getStringExtra("cidade"));
        String nparc = intent.getStringExtra("nparcelas");
        if(Objects.equals(nparc, "1")) {
            tv_Parcela_rotulo.setText("Parcela:");
            tv_Mes_desconto.setText(intent.getStringExtra("mes"));
        }else{
            tv_Parcela_rotulo.setText("Parcelas:");
            tv_Mes_desconto.setText(intent.getStringExtra("primeiro_mes"));
        }
        tv_Parcela.setText(intent.getStringExtra("nparcelas"));


        tv_Registro.setText(intent.getStringExtra("lancamento"));
        data_formatada = intent.getStringExtra("datacad");
        assert data_formatada != null;
        data_formatada_aux = data_formatada.substring(8, 10) + "/" + data_formatada.substring(5, 7) + "/" + data_formatada.substring(0, 4);
        tv_Data.setText(data_formatada_aux);
        tv_Hora.setText(intent.getStringExtra("hora"));

        T_view_background = findViewById(R.id.view_Background);
        container = findViewById(R.id.container_cd);


        celular = intent.getStringExtra("celular");
        cod_convenio = intent.getStringExtra("cod_convenio");
        razaosocial = intent.getStringExtra("razaosocial");

        nome_fantasia = intent.getStringExtra("nome_fantasia");
        endereco = intent.getStringExtra("endereco");
        bairro = intent.getStringExtra("bairro");
        cidade = intent.getStringExtra("cidade") + "-MG";
        cnpj = intent.getStringExtra("cnpj");
        cpf = intent.getStringExtra("cpf");
        parcelas = intent.getStringExtra("parcelas");
        lancamento = intent.getStringExtra("lancamento");
        uid = intent.getStringExtra("uid");
        token_associado = intent.getStringExtra("token_associado");
        email = intent.getStringExtra("email");
        senha = intent.getStringExtra("senha");
        nome = intent.getStringExtra("nome");
        datacad = intent.getStringExtra("datacad");
        hora = intent.getStringExtra("hora");
        nparcelas = intent.getStringExtra("nparcelas");
        cartao = intent.getStringExtra("cartao");
        categoria = intent.getStringExtra("id_categoria");
        primeiro_mes = intent.getStringExtra("id_categoria");
        mes_corrente = intent.getStringExtra("mes_corrente");
        shakeItBaby();
        celular = celular.replace("(", "");
        celular = celular.replace(")", "");
        valor_pedido = intent.getStringExtra("valor_pedido");
        Double valor_pedido2 = Double.parseDouble(valor_pedido);
        tv_ValorExibir.setText(valor_pedido);

        if (nome_fantasia.length() > 32) {
            nome_fantasia = nome_fantasia.substring(0, 32);
        }
        if (celular != null && !celular.equals("")){
            if(celular.length() == 12) {
                celular = "(" + celular.substring(0, 2) + ")" + celular.charAt(3) + " " + celular.substring(3, 12);
            }
        }
        //n_cel.setText(celular);

        NumberFormat value = NumberFormat.getInstance(new Locale("pt", "BR"));
        NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        n_parcela_aux = Double.parseDouble(nparcelas);
        valor_parcela_aux = valor_pedido2 / n_parcela_aux;

        valor_parcela_formatado = currency.format(valor_parcela_aux);
        valor_pedito_formatado = currency.format(valor_pedido2);

        tv_ValorExibir.setText(valor_pedito_formatado);

        tv_Valo_parcela.setText(valor_parcela_formatado);

        btnRetorna.setOnClickListener(view -> {
            Intent intent12 = new Intent(MainActivityConfirma_venda.this, TelaContainerConvenio.class);
            intent12.putExtra("cod_convenio",cod_convenio);
            intent12.putExtra("nome_fantasia",nome_fantasia);
            intent12.putExtra("razaosocial",razaosocial);
            intent12.putExtra("endereco", endereco);
            intent12.putExtra("bairro", bairro);
            intent12.putExtra("cidade", cidade);
            intent12.putExtra("cnpj", cnpj);
            intent12.putExtra("cpf", cpf);
            intent12.putExtra("parcelas", parcelas);
            intent12.putExtra("token_associado", token_associado);
            intent12.putExtra("email", email);
            intent12.putExtra("celular", celular);
            intent12.putExtra("senha", senha);
            intent12.putExtra("id_categoria", categoria);
            intent12.putExtra("mes_corrente", mes_corrente);
            startActivity(intent12);
        });


        btnEnviaSms.setOnClickListener(view -> {
                sendSms();
        });


        FirebaseMessaging.getInstance().getToken()
            .addOnCompleteListener(task -> {
                if (!task.isSuccessful()) {
                    System.out.println("Fetching FCM registration token failed");
                    return;
                }
                String token = task.getResult();
            });

        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        String parts = "Convenio QRCRED:"+" "+"Compra realizada em "+razaosocial+", Total : "+valor_pedito_formatado+" em "+data_formatada_aux+" as "+hora;
        sendPushNotifications(token_associado,"Comprovante QRCRED",parts);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    public void sendPushNotifications(String usertoken, String title, String message){
        Data data = new Data(title,message);
        NotificationSender sender = new NotificationSender(data, usertoken);
        apiService.sendNotification(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(@NotNull Call<MyResponse> call, @NotNull Response<MyResponse> response) {
                if (response.code() == 200) {
                    assert response.body() != null;
                    if (response.body().success != 1) {
                        //Toast.makeText(MainActivityConfirma_venda.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<MyResponse> call, @NotNull Throwable t) {
            }
        });
    }
    private final ActivityResultLauncher<String> request_permission_launcher_storage_images =
        registerForActivityResult(new ActivityResultContracts.RequestPermission(),
            isGranted ->{
                if(isGranted){
                    sendSms();
                }else{
                    Log.d(TAG, required_permissions[0]+ " Not Granted");
                    is_store_image_permitted = false;
                }
            });

    private void sendSms() {
        String parts = "Convenio QRCRED:"+" "+"Compra realizada em "+razaosocial+", Total : "+valor_pedito_formatado+" em "+data_formatada_aux+" as "+hora;
        composeMmsMessage(parts);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "Não vai funcionar!!!", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void shakeItBaby() {
        //vibrate phone
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            VibratorManager vibratorManager = (VibratorManager) mContext.getSystemService(Context.VIBRATOR_MANAGER_SERVICE);
            vibratorManager.getDefaultVibrator();
        } else {
            //Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
            //vibrator.vibrate(300);
        }
    }
    public void composeMmsMessage(String message) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("smsto:"));  // This ensures only SMS apps respond
        intent.putExtra("sms_body", message);
        //intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
