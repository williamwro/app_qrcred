package br.com.qrcred;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;

import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.BuildConfig;
import com.squareup.picasso.Picasso;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class MainActivityFotoCupom extends AppCompatActivity {

    private static final String FILE_TAG = "";
    FirebaseStorage mStorage;
    //StorageReference mStorage;
    public ImageView mImageView;
    //Button btnCaputarFotoCupon;
    Button btnSalvarFotoCupon;
    Button btnCompartilhar; 
    Button btnTirarFoto;
    Button btnRetornarHome;
    //String currentPhotoPath;
    //private int permissionCheck;
    public DevicePolicyManager mDevicePolicyManager;
    private String mCurrentPhotoPath;
    //private Button lockTaskButton;

    public  Uri photoURI_AUX;
    public String lancamento;
    RequestQueue requestQueue;
    public String lancamento_parcel_1;
    public static final String EXTRA_FILEPATH =
            "com.google.codelabs.cosu.EXTRA_FILEPATH";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar mprogressBar;

    public String Obj_nome;
    public String cod_convenio;
    public String nome_fantasia;
    public String razaosocial;
    public String celular;
    public String Obj_valorpedido;
    public String endereco;
    public String bairro;
    public String cidade;
    public String cnpj;
    public String cpf;
    public String parcelas;
    public String nparcelas;
    public String Obj_registrolan;
    public String uid;
    public String token_associado;
    public String email;
    public String senha;
    public String descricao;
    public String datacad;
    public String hora;
    public String cartao;
    public String mes_corrente;
    public String primeiro_mes;
    public String categoria;
    //private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    //PreviewView previewView;
    //private ImageCapture imageCapture;
    //private ProgressBar progressBar;
    private ProgressBar pgFoto;
    //MaterialDialog materialDialog;
    public Bitmap bitmap;
    RequestQueue requestQueue2;
    FirebaseFirestore firebaseFirestore;
    String matricula;
    String pass;
    String empregador;
    String total_digitado;
    String Val_parcela_float;
    String nparcelas_selecionada;
    public String uri_retornado="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_foto_cupom);

        mImageView = findViewById(R.id.imageViewCupom);
        btnSalvarFotoCupon = findViewById(R.id.btnSalvarFotoCupon);
        //btnCompartilhar  = findViewById(R.id.btnCompartilhar);
        btnTirarFoto       = findViewById(R.id.btnTirarFoto);
        pgFoto             = findViewById(R.id.pbFoto);
        btnRetornarHome    = findViewById(R.id.btnRetornarHome);
        mprogressBar       = findViewById(R.id.progressBar_conv2);

        final Intent intent = getIntent( );
        matricula       = intent.getStringExtra("matricula");
        Obj_nome        = intent.getStringExtra("nome");
        cod_convenio    = intent.getStringExtra("cod_convenio");
        nome_fantasia   = intent.getStringExtra("nome_fantasia");
        razaosocial     = intent.getStringExtra("razaosocial");
        celular         = intent.getStringExtra("celular");
        Obj_valorpedido = intent.getStringExtra("valor_pedido");
        endereco        = intent.getStringExtra("endereco");
        bairro          = intent.getStringExtra("bairro");
        cidade          = intent.getStringExtra("cidade");
        cnpj            = intent.getStringExtra("cnpj");
        cpf             = intent.getStringExtra("cpf");
        parcelas        = intent.getStringExtra("parcelas");
        nparcelas       = intent.getStringExtra("nparcelas");
        token_associado = intent.getStringExtra("token_associado");
        email           = intent.getStringExtra("email");
        senha           = intent.getStringExtra("senha");

        cartao          = intent.getStringExtra("codcarteira");
        mes_corrente    = intent.getStringExtra("mes_corrente");
        primeiro_mes    = intent.getStringExtra("primeiro_mes");
        if(primeiro_mes == null){
            primeiro_mes = "";
        }else{

        }
        categoria       = intent.getStringExtra("id_categoria");
        pass            = intent.getStringExtra("pass");
        empregador          = intent.getStringExtra("empregador");
        Val_parcela_float   = intent.getStringExtra("Val_parcela_float");
        nparcelas_selecionada = intent.getStringExtra("nparcelas_selecionada");
        descricao             = intent.getStringExtra("descricao");
        //Picasso.setSingletonInstance(new Picasso.Builder(this).build());

        mStorage = FirebaseStorage.getInstance( );
        //inicializarFirebase();
        mDevicePolicyManager = (DevicePolicyManager)
                getSystemService(Context.DEVICE_POLICY_SERVICE);

        pgFoto.setMax(100);


        btnRetornarHome.setOnClickListener(view ->{
            retorna_tela();
        });
        requestQueue2 = Volley.newRequestQueue(this);
        firebaseFirestore = FirebaseFirestore.getInstance();
        btnSalvarFotoCupon.setOnClickListener(view -> {

            mprogressBar.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
            params.addRule(RelativeLayout.CENTER_IN_PARENT);
            // error aqui
            //layout.addView(progressBar, params);

            //ProgressBar finalProgressBar = progressBar;

            StorageReference ref = mStorage.getReference().child("cumpons/"+ UUID.randomUUID().toString());

            // progressBar.setTitle("Gravando..., aguarde.");
            //progressDialog.show();
            btnSalvarFotoCupon.setEnabled(false);
            btnTirarFoto.setEnabled(false);
            btnRetornarHome.setEnabled(false);
            if(photoURI_AUX != null) {
                new MaterialAlertDialogBuilder(this)
                        .setTitle("Pergunta sobre backup")
                        .setMessage("Para manter esse foto gravada como backup em nosso banco de dados externo, é necessario aceitar. Voce aceita fazer o bakcup desta foto?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Trate o clique para "Sim"
                                ref.putFile(photoURI_AUX).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            //progressDialog.dismiss();
                                            //finalProgressBar.setVisibility(View.VISIBLE);
                                            //Toast.makeText(MainActivityFotoCupom.this, "Enviado com sucesso!", Toast.LENGTH_SHORT).show();
                                            ref.getDownloadUrl().addOnSuccessListener(uri -> {
                                                uri_retornado = uri.toString();
                                                // grava uid da imagem do cupon da transacao
                                                grava();

                                            });

                                        }
                                    }
                                }).addOnFailureListener(e -> {
                                    //finalProgressBar.setVisibility(View.GONE);
                                    //progressDialog.dismiss();
                                    Toast.makeText(MainActivityFotoCupom.this, "Data de validade da pasta storage no firebase venceu,contact o administrador para atualizar", Toast.LENGTH_SHORT).show();
                                    btnSalvarFotoCupon.setEnabled(true);
                                    btnRetornarHome.setEnabled(true);
                                    btnSalvarFotoCupon.setEnabled(true);
                                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                        double progress = (100.0 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                                        int pg = (int) progress;
                                        pgFoto.setProgress(pg);
                                    }
                                });
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // Trate o clique para "Não"
                                grava();
                            }
                        })
                        .show();

            }else{
                grava();
                /*AestheticDialog.Builder dialog1 =
                        new AestheticDialog.Builder(MainActivityFotoCupom.this,
                                DialogStyle.FLAT, DialogType.INFO);
                dialog1.setTitle("Atenção!");
                dialog1.setMessage("CAPTURE A FOTO DO COMPROVANTE DE VENDA COM VALOR E ASSINATURA, PARA EFETIVAR A OPERACAO !");
                dialog1.setCancelable(false);
                dialog1.setDarkMode(true);
                dialog1.setGravity(Gravity.CENTER);
                dialog1.setAnimation(DialogAnimation.SHRINK);
                dialog1.setOnClickListener(new OnDialogClickListener() {
                    @Override
                    public void onClick(@NonNull AestheticDialog.Builder builder) {

                        getPermissions();
                    }
                });
                dialog1.show();*/

                // salvar foto e gravar dados

            }
        });
        // foto
       /* AestheticDialog.Builder dialog1 =
                new AestheticDialog.Builder(MainActivityFotoCupom.this,
                        DialogStyle.FLAT, DialogType.INFO);
        dialog1.setTitle("Atenção!");
        dialog1.setMessage("CAPTURE A FOTO DO COMPROVANTE DE VENDA COM VALOR E ASSINATURA, PARA EFETIVAR A OPERACAO !");
        dialog1.setCancelable(false);
        dialog1.setDarkMode(false);
        dialog1.setGravity(Gravity.CENTER);
        dialog1.setAnimation(DialogAnimation.SHRINK);
        dialog1.setOnClickListener(new OnDialogClickListener() {
            @Override
            public void onClick(@NonNull AestheticDialog.Builder builder) {

                getPermissions();
                dialog1.dismiss();
            }
        });
        dialog1.show();*/
        // foto


        //btnCompartilhar.setOnClickListener(v -> checarPermissao());
        btnTirarFoto.setOnClickListener(v -> requestCameraPermission());

        //BitmapDrawable drawable = (BitmapDrawable) mImageView.getDrawable();
        //  bitmap = drawable.getBitmap();
    }
    private void grava(){
        String urlm = getResources().getString(R.string.HOST) + "grava_venda_app.php";
        requestQueue2 = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlm,
                response -> {
                    JSONArray jsonResponsepar;
                    JSONObject jsonResponse;
                    try {

                        jsonResponse = new JSONObject(response);

                        Conta_app conta_app = new Conta_app();

                        String Obj_situacao     = jsonResponse.getString("situacao");
                        String Obj_registrolan  = jsonResponse.getString("registrolan");
                        String Obj_matricula    = jsonResponse.getString("matricula");
                        String Obj_nome         = jsonResponse.getString("nome");
                        String Obj_nparcelas    = jsonResponse.getString("nparcelas");
                        String Obj_mes_seq      = jsonResponse.getString("mes_seq");
                        primeiro_mes            = jsonResponse.getString("primeiro_mes");
                        String Obj_razaosocial  = jsonResponse.getString("razaosocial");
                        String Obj_nomefantasia = jsonResponse.getString("nomefantasia");
                        String Obj_codcarteira  = jsonResponse.getString("codcarteira");
                        String Obj_valorpedido  = jsonResponse.getString("valorpedido");
                        String Obj_endereco     = jsonResponse.getString("endereco");
                        String Obj_bairro       = jsonResponse.getString("bairro");
                        String Obj_parcela_conv = jsonResponse.getString("parcela_conv");
                        String Obj_datacad      = jsonResponse.getString("datacad");
                        String Obj_hora         = jsonResponse.getString("hora");
                        String Obj_cod_convenio = jsonResponse.getString("cod_convenio");
                        String Obj_primeiro_mes = jsonResponse.getString("primeiro_mes");
                        String Obj_pede_senha   = jsonResponse.getString("pede_senha");
                        String Obj_id_categoria = jsonResponse.getString("id_categoria");

                        if (!Obj_nparcelas.equals("") && !Obj_nparcelas.equals("1")){

                            jsonResponsepar = jsonResponse.getJSONArray("parcelas");
                            Integer qtde_parc = jsonResponsepar.length( ) - 1;
                            for (int i = 0; i < qtde_parc; i++) {
                                if (i>0) {

                                    JSONObject post = jsonResponsepar.optJSONObject(i);

                                    String numero = post.getString("numero");
                                    String valor_parcela = post.getString("valor_parcela");
                                    String registrolan = post.getString("registrolan");
                                    String mes_seq = post.getString("mes_seq");

                                    conta_app.setuid(UUID.randomUUID( ).toString());
                                    conta_app.setcodcarteira(Obj_codcarteira);
                                    conta_app.setcodconvenio(Obj_cod_convenio);
                                    conta_app.setdatacad(Obj_datacad);
                                    conta_app.sethora(Obj_hora);
                                    conta_app.setmatricula(Obj_matricula);
                                    conta_app.setmes_seq(mes_seq);
                                    conta_app.setnome(Obj_nome);
                                    conta_app.setnumeroparcelas(Obj_nparcelas);
                                    conta_app.setrazaosocial(Obj_razaosocial);
                                    conta_app.setregistrolan(registrolan);
                                    conta_app.setvalor_pedido(valor_parcela);
                                    conta_app.setnparcela(String.valueOf(i));
                                    //databaseReference.child("CONTA").child(conta_app.getuid()).setValue(conta_app);
                                    firebaseFirestore.collection("conta")
                                            .add(conta_app)
                                            .addOnSuccessListener(documentReference -> Log.w("TAG", "Adcionado com ID: " + documentReference.getId()))
                                            .addOnFailureListener(e -> Log.w("TAG", "Error adding documet", e));
                                    //conta_app.setid_lan_firebase(UUID.randomUUID( ).toString());
                                    //reff.push().setValue(conta_app);//GRAVA FIREBASE
                                    //reff.child("CONTA").child(conta_app.getuid()).setValue(conta_app);
                                }
                            }

                        }else{
                            conta_app.setuid(UUID.randomUUID( ).toString());
                            conta_app.setcodcarteira(Obj_codcarteira);
                            conta_app.setcodconvenio(Obj_cod_convenio);
                            conta_app.setdatacad(Obj_datacad);
                            conta_app.sethora(Obj_hora);
                            conta_app.setmatricula(Obj_matricula);
                            conta_app.setmes_seq(Obj_mes_seq);
                            conta_app.setnome(Obj_nome);
                            conta_app.setnumeroparcelas(Obj_nparcelas);
                            conta_app.setrazaosocial(Obj_razaosocial);
                            conta_app.setregistrolan(Obj_registrolan);
                            conta_app.setvalor_pedido(Obj_valorpedido);
                            //databaseReference.child("CONTA").child(conta_app.getuid()).setValue(conta_app);
                            firebaseFirestore.collection("conta")
                                    .add(conta_app).addOnSuccessListener(documentReference -> Log.w("TAG", "Adcionado com ID: " + documentReference.getId()))
                                    .addOnFailureListener(e -> Log.w("TAG", "Error adding documet", e));
                        }
                        //Toast.makeText(HomeConvenioActivity.this, "GRAVADO COM SUCESSO!",Toast.LENGTH_LONG).show();
                        //Intent intent = new Intent(HomeConvenioActivity.this, MainActivityConfirma_venda.class);
                        Intent intent2 = new Intent(getApplicationContext(), MainActivityConfirma_venda.class);
                        intent2.putExtra("nome",Obj_nome);
                        intent2.putExtra("cod_convenio",cod_convenio);
                        intent2.putExtra("nome_fantasia",nome_fantasia);
                        intent2.putExtra("razaosocial",razaosocial);
                        intent2.putExtra("celular",celular);
                        intent2.putExtra("valor_pedido",Obj_valorpedido);
                        intent2.putExtra("endereco", endereco);
                        intent2.putExtra("bairro", bairro);
                        intent2.putExtra("cidade", cidade);
                        intent2.putExtra("cnpj", cnpj);
                        intent2.putExtra("cpf", cpf);
                        intent2.putExtra("mes", Obj_mes_seq);
                        intent2.putExtra("primeiro_mes", primeiro_mes);
                        intent2.putExtra("parcelas", parcelas);
                        intent2.putExtra("lancamento", Obj_registrolan);
                        intent2.putExtra("uid", conta_app.getuid());
                        intent2.putExtra("token_associado", token_associado);
                        intent2.putExtra("email", email);
                        intent2.putExtra("senha", senha);
                        intent2.putExtra("datacad", Obj_datacad);
                        intent2.putExtra("hora", Obj_hora);
                        intent2.putExtra("nparcelas",Obj_nparcelas);
                        intent2.putExtra("codcarteira",Obj_codcarteira);
                        intent2.putExtra("id_categoria",Obj_id_categoria);
                        intent2.putExtra("mes_corrente",mes_corrente);

                        startActivity(intent2);
                        //requireActivity().finish();


                    } catch (JSONException e) {
                        e.printStackTrace( );
                    }
                },
                volleyError -> {
                    String message = null; // error message, show it in toast or dialog, whatever you want
                    if (volleyError instanceof NetworkError || volleyError instanceof AuthFailureError || volleyError instanceof TimeoutError) {
                        mprogressBar.setVisibility(View.GONE);
                        message = "O sinal da internet pode estar fraco ou não está conectado a Internet, tente novamente";
                        btnSalvarFotoCupon.setEnabled(true);
                    } else if (volleyError instanceof ServerError) {
                        mprogressBar.setVisibility(View.GONE);
                        message = "Servidor indisponivel. tente dentro de alguns minutos";
                        btnSalvarFotoCupon.setEnabled(true);
                    }  else if (volleyError instanceof ParseError) {
                        mprogressBar.setVisibility(View.GONE);
                        message = "Erro gerado! por favor, tente mais tarde";
                        btnSalvarFotoCupon.setEnabled(true);
                    }
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();
                    mprogressBar.setVisibility(View.GONE);
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> postMap = new HashMap<>();
                postMap.put("cod_convenio", cod_convenio);
                postMap.put("matricula", matricula);
                postMap.put("pass", pass);
                postMap.put("nome", Obj_nome);
                postMap.put("cartao", cartao);
                postMap.put("empregador", empregador);
                postMap.put("valor_pedido", Obj_valorpedido.replace(",","."));
                postMap.put("valor_parcela", Val_parcela_float.replace(",","."));
                postMap.put("mes_corrente", mes_corrente);
                postMap.put("primeiro_mes", primeiro_mes);
                postMap.put("qtde_parcelas", nparcelas_selecionada);
                postMap.put("uri_cupom", uri_retornado);
                postMap.put("descricao", descricao);

                return postMap;
            }
        };
        requestQueue2.add(stringRequest);
        mprogressBar.setVisibility(View.GONE);
    }
    private void retorna_tela() {

            Intent intent12 = new Intent(this, TelaContainerConvenio.class);
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
            startActivity(intent12);

    }
    private void inicializarFirebase() {
         FirebaseApp.initializeApp(MainActivityFotoCupom.this);
         firebaseDatabase =  FirebaseDatabase.getInstance();
         databaseReference = firebaseDatabase.getReference();
     }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date( ));
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",   /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath( );
        return image;
    }
    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager( )) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile( );

            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(Objects.requireNonNull(getApplicationContext()),
                        BuildConfig.APPLICATION_ID + ".provider", photoFile);
                mImageView.setImageURI(photoURI);//zoom da imagem
                photoURI_AUX = photoURI;
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                //startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                someActivityResultLauncher.launch(takePictureIntent);
            }
        }
    }
    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        File imgFile = new File(mCurrentPhotoPath);
                        if (imgFile.exists()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            mImageView.setImageBitmap(myBitmap);
                            Picasso.get()
                                    .load(imgFile)
                                    .rotate(360)
                                    .into(mImageView);
                        }
                    }else{
                        photoURI_AUX = null;
                    }
                }
            });
    //---------------------- COMPARTILHA IMAGEM --------------------------
    private void shareImage(Bitmap bitmapi){
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        Uri bmpUri;
        String textToShare="Share Tutorial";
        bmpUri=saveImage(bitmapi,getApplicationContext());
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        share.putExtra(Intent.EXTRA_STREAM,bmpUri);
        share.putExtra(Intent.EXTRA_SUBJECT, "New App");
        share.putExtra(Intent.EXTRA_TEXT, textToShare);



        startActivity(Intent.createChooser(share,"Compartilhar comprovante"));
    }
    private static Uri saveImage(Bitmap image, Context context){
        File imagesFolder=new File(context.getCacheDir(), "images");
        Uri uri=null;
        try{
            imagesFolder.mkdir();
            File file=new File(imagesFolder,"share_images.jpg");

            FileOutputStream stream=new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.JPEG, 90, stream);
            stream.flush();
            stream.close();
            uri= FileProvider.getUriForFile(Objects.requireNonNull(context.getApplicationContext()),
                    "br.com.qrcred"+".provider",file);
        }
        catch ( IOException e){
            Log.d("TAG","Exception"+e.getMessage());
        }
        return uri;
    }
 /*   public void checarPermissao(){
        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SOLICITAR_PERMISSAO);
        }else{
            compartilhar();
        }
    }*/
 private void requestCameraPermission() {
     if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
         // Aqui você pode explicar ao usuário por que precisa da permissão, antes de solicitá-la novamente.
         Toast.makeText(this, "A câmera é necessária para capturar imagens", Toast.LENGTH_LONG).show();
     }

     // Solicita a permissão
     ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
 }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {// Código de solicitação de permissão da câmera
            // Se a solicitação for cancelada, os arrays de resultado estarão vazios.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissão concedida, você pode realizar a operação dependente da permissão de câmera
                dispatchTakePictureIntent();
            } else {
                // Permissão negada, desabilite a funcionalidade que depende desta permissão.
                Toast.makeText(this, "Permissão de câmera negada", Toast.LENGTH_SHORT).show();
            }
            // Outros 'case' para checar outras permissões que este app possa solicitar.
        }
    }
    private void checarPermissao(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }else{
            //shareImage(bitmap);
        }
    }
}
