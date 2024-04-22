package br.com.qrcred.convenio;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
/*import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;*/
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;
//import com.google.firebase.database.annotations.NotNull;
//import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.com.qrcred.Menu2MainActivity;
import br.com.qrcred.convenio.model.CEP;
import br.com.qrcred.convenio.model.SimpleCallback;

import br.com.qrcred.Categorias_class;
import br.com.qrcred.GeraCpfCnpj;
import br.com.qrcred.R;
import br.com.qrcred.convenio.service.CEPService;

public class CadConveniosActivity extends AppCompatActivity {

    private Button btnBuscar;
    public Button btnGravarConvenio;
    private RadioButton optJuridica;
    private RadioButton optFisica;
    private RadioGroup optGrupoEmpresa;
    private TextInputEditText rs;
    private TextInputEditText nf;
    private EditText cep;
    private EditText tel;
    private EditText cel;
    private EditText cnpj;
    private EditText cpf;
    private EditText email;
    private TextInputEditText responsavel;
    private TextInputEditText endereco;
    private TextInputEditText complemento;
    private TextView numero;
    private TextInputEditText bairro;
    private Spinner spinnercidades;
    private Spinner spinnerestados;
    private Spinner spinnercategorias;
    private ArrayAdapter<String> adapter_ci;
    private ArrayAdapter<StringWithTag> adapter_ca;
    private ArrayAdapter<String> adapter_es;
    public ArrayList<Categorias_class> listCategorias;
    public String xNome;
    RequestQueue requestQueue;
    public TextInputLayout textInputLayoutRS_,textInputLayoutNF_,textInputLayoutCEP_,textInputLayoutENDERECO_,textInputLayoutNUMERO_,textInputLayoutBAIRRO_,textInputLayoutCELULAR_,textInputLayoutCNPJ_,textInputLayoutCPF_,textInputLayoutEMAIL_;
    Animation animShake;
    //public Vibrator vib;
    public final GeraCpfCnpj geraCpfCnpj = new GeraCpfCnpj();
    private Map<String, String> params;
    public Date dataHoraAtual;
    private String dataHoraAtualx;
    private String valTipoGrupoEmpresa;
    private int xCod_categoria_int;
    private String xCod_categoria_string;
    private String cidade_escolhida;
    private String estado_escolhido;
    public String usuario_send_mail;
    public String senha_send_mail;
    public String mensagem_email;
    //public AdView mAdView;
    public String email_destino;

    //public FirebaseFirestore firebaseFirestore;
    public boolean isMetered;
    public ProgressBar progressBar2;
    public CardView cardView;
    public TextView textViewConsulta;
    //private ArrayList<CEP> arrayCEPs;

    //Tag para o LOG
    //private static final String TAG = "logCEP";

    @SuppressLint({"SimpleDateFormat", "MissingPermission", "NonConstantResourceId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_convenios);

        //btnBuscar         = findViewById(R.id.btnConsultaCep);
        btnGravarConvenio = findViewById(R.id.btnGravarConvenio);
        rs                = findViewById(R.id.edRazaoSocial);
        nf                = findViewById(R.id.edNomeFantasia);
        cep               = findViewById(R.id.edCep);
        endereco          = findViewById(R.id.edEndereco);
        complemento       = findViewById(R.id.edComplemento);
        numero            = findViewById(R.id.edNumero);
        bairro            = findViewById(R.id.edBairro);
        spinnercategorias = findViewById(R.id.spCategoria);
        tel               = findViewById(R.id.edTelefone);
        cel               = findViewById(R.id.edCelular);
        responsavel       = findViewById(R.id.edNomeResponsavel);
        cnpj              = findViewById(R.id.edCnpj);
        cpf               = findViewById(R.id.edCpf);
        email             = findViewById(R.id.edEmail);
        optJuridica       = findViewById(R.id.optJuridica);
        optFisica         = findViewById(R.id.optFisica);
        optGrupoEmpresa   = findViewById(R.id.tipoGrupo);

        progressBar2       = findViewById(R.id.progressbarbotaoentrar);
        cardView         = findViewById(R.id.ButaoCardEntrar);
        textViewConsulta   = findViewById(R.id.textoEntrar);

        textInputLayoutRS_       = findViewById(R.id.textInputLayoutRS);
        textInputLayoutNF_       = findViewById(R.id.textInputLayoutNF);
        textInputLayoutCEP_      = findViewById(R.id.textInputLayoutCEP);
        textInputLayoutENDERECO_ = findViewById(R.id.textInputLayoutENDERECO);
        textInputLayoutNUMERO_   = findViewById(R.id.textInputLayoutNUMERO);
        textInputLayoutBAIRRO_   = findViewById(R.id.textInputLayoutBAIRRO);

        textInputLayoutCELULAR_  = findViewById(R.id.textInputLayoutCELULAR);
        textInputLayoutCNPJ_     = findViewById(R.id.textInputLayoutCNPJ);
        textInputLayoutCPF_      = findViewById(R.id.textInputLayoutCPF);
        textInputLayoutEMAIL_    = findViewById(R.id.textInputLayoutEMAIL);

        SimpleMaskFormatter smfcep = new SimpleMaskFormatter("NN.NNN-NNN");
        MaskTextWatcher mtcep = new MaskTextWatcher(cep, smfcep);
        cep.addTextChangedListener(mtcep);

        SimpleMaskFormatter smfcel = new SimpleMaskFormatter("(NN)N NNNN-NNNN");
        MaskTextWatcher mtcel = new MaskTextWatcher(cel, smfcel);
        cel.addTextChangedListener(mtcel);

        SimpleMaskFormatter smftel = new SimpleMaskFormatter("(NN) NNNN-NNNN");
        MaskTextWatcher mttel = new MaskTextWatcher(tel, smftel);
        tel.addTextChangedListener(mttel);

        SimpleMaskFormatter smfcpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtcpf = new MaskTextWatcher(cpf, smfcpf);
        cpf.addTextChangedListener(mtcpf);

        SimpleMaskFormatter smfcnpj = new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN");
        MaskTextWatcher mtcnpj = new MaskTextWatcher(cnpj, smfcnpj);
        cnpj.addTextChangedListener(mtcnpj);

        // define o evento de clique
        //this.btnBuscar.setOnClickListener(this);
        Toolbar toolbar = findViewById(R.id.toolbar_cad_conv);
        setSupportActionBar(toolbar);
        if (getSupportActionBar( ) != null) {
            getSupportActionBar( ).setDisplayHomeAsUpEnabled(true);
            ActionBar ab = getSupportActionBar();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_black_24dp, null);
            //drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_IN));
            ab.setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_theme_light_onPrimary));
        getSupportActionBar( ).setTitle("Cadastro de convenio");

        spinnerestados = findViewById(R.id.spEstados);
        spinnercidades = findViewById(R.id.spCidade);
        ArrayList<String> items_estados = getEstados("cidades.json");

        adapter_es = new ArrayAdapter<>
                (this, R.layout.spinner_cidades, items_estados);
        spinnerestados.setAdapter(adapter_es);
        spinnerestados.setOnItemSelectedListener(new CadConveniosActivity.MyOnItemSelectedListener());
        //spinnerestados.setSelection(11);
        //getCidades("cidades.json", adapter_es.getPosition(estado_escolhido));
        /* PEGANDO ID SPINNER ESTADOS E POPULANDO SPINNER CIDADES */
        spinnerestados.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    Object item = parent.getItemAtPosition(pos);
                    estado_escolhido =  item.toString();
                    new CadConveniosActivity.MyOnItemSelectedListener();
                    getCidades("cidades.json", adapter_es.getPosition(estado_escolhido));
                    spinnerestados.setSelection(adapter_es.getPosition(estado_escolhido));
                    //prints the text in spinner item.
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
        });




        spinnerestados.setPrompt("Escolha o estado");
        spinnercidades.setPrompt("Escolha a cidade");
        rs.addTextChangedListener(new TextWatcher( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String s=editable.toString();
                if(!s.equals(s.toUpperCase()))
                {
                    s=s.toUpperCase();
                    rs.setText(s);
                    rs.setSelection(rs.length()); //fix reverse texting
                }
            }
        });
        nf.addTextChangedListener(new TextWatcher( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String s=editable.toString();
                if(!s.equals(s.toUpperCase()))
                {
                    s=s.toUpperCase();
                    nf.setText(s);
                    nf.setSelection(nf.length()); //fix reverse texting
                }
            }
        });
        endereco.addTextChangedListener(new TextWatcher( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String s=editable.toString();
                if(!s.equals(s.toUpperCase()))
                {
                    s=s.toUpperCase();
                    endereco.setText(s);
                    endereco.setSelection(endereco.length()); //fix reverse texting
                }
            }
        });
        bairro.addTextChangedListener(new TextWatcher( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                String s=editable.toString();
                if(!s.equals(s.toUpperCase()))
                {
                    s=s.toUpperCase();
                    bairro.setText(s);
                    bairro.setSelection(bairro.length()); //fix reverse texting
                }
            }
        });
        btnGravarConvenio.setEnabled(true);
        try {
            preenche_categorias();
        } catch (Exception e) { // Capturando uma exceção genérica para demonstração
            handleException(e);
        }
        animShake = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        //vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        btnGravarConvenio.setOnClickListener(view -> submitForm());
        dataHoraAtual = new Date();
        dataHoraAtualx = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);
        //valTipoGrupoEmpresa = "2";//2 = juridica
        switch (optGrupoEmpresa.getCheckedRadioButtonId()){
            case R.id.optFisica:
                valTipoGrupoEmpresa = "1";
                break;
            case R.id.optJuridica:
                valTipoGrupoEmpresa = "2";
                break;
        }
        optGrupoEmpresa.setOnCheckedChangeListener((group, checkedId) -> {
            View radiobuttonn = optGrupoEmpresa.findViewById(checkedId);
            int index = optGrupoEmpresa.indexOfChild(radiobuttonn);

            switch (index){
                case 1:
                    valTipoGrupoEmpresa = "1"; //fisica
                    break;
                case 0:
                    valTipoGrupoEmpresa = "2"; //juridica
                    break;
            }
        });
        spinnercategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener( ) {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                StringWithTag swt = (StringWithTag) parent.getItemAtPosition(position);
                xCod_categoria_string = swt.tag.toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinnercidades.setOnItemSelectedListener(
            new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                    Object item = parent.getItemAtPosition(pos);
                    cidade_escolhida =  item.toString();     //prints the text in spinner item.
                }
                public void onNothingSelected(AdapterView<?> parent) {
                }
        });
        //firebaseFirestore = FirebaseFirestore.getInstance();
        AlertDialog.Builder builder = new AlertDialog.Builder(CadConveniosActivity.this);
        builder.setTitle("Atenção")
                .setMessage("Para se conveniar ao cartão QrCred, " +
                            "é necessario fazer um cadastro." +
                            "Os dados aqui coletados são:" +
                            "\n" +
                            "\n" +
                            "Razão Social, Nome Fantasia, Cep, Endereço completo, Tel Fixo, Celular, CNPJ, CPF e Email." +
                            "\n" +
                            "\n" +
                            "Todos os dados citados acima serão armzenados em nosso banco de dados e " +
                            " usados única e exclusivamente para funcionalidades " +
                            "do Aplicativo QrCred. Jamais serão compartilhados, conforme a lei de privacidade e " +
                            "segurança de dados." +
                            "\n" +
                            "\n" +
                            "Voce permite coletar seus dados?")
                .setCancelable(false)
                .setPositiveButton("Sim", (dialog, which) -> {
                })
                .setNegativeButton("Não", (dialog, which) -> {
                    Intent intentx;
                    intentx = new Intent(CadConveniosActivity.this, Menu2MainActivity.class);
                    startActivity(intentx);
                });
        //Creating dialog box
        AlertDialog dialog  = builder.create();
        dialog.show();
        cardView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buscar_cep();
                    }
                });
    }
    private void handleException(Exception e) {
        runOnUiThread(() -> {
            Toast.makeText(CadConveniosActivity.this, "Ocorreu um erro: " + e.getMessage(), Toast.LENGTH_LONG).show();
            // Ou mostre um AlertDialog aqui
        });
    }
    public void buscar_cep(){
        progressBar2.setVisibility(View.VISIBLE);
        textViewConsulta.setText("");
        this.bairro.setText("");
        this.complemento.setText("");
        //this.cidade.setText("");
        this.endereco.setText("");
        //this.UF.setText("");
        // cep
        String cepx = this.cep.getText().toString().replace(".","");

        // verifica se o CEP é válido
        Pattern pattern = Pattern.compile("^[0-9]{5}-[0-9]{3}$");
        Matcher matcher = pattern.matcher(cepx);

        if (matcher.find()) {
            CEPService service = new CEPService(CadConveniosActivity.this);

            service.getCEP(cepx, new SimpleCallback<>() {

                @Override
                public void onResponse(CEP response) {
                    CEP cepz = response;
                    if(cepz.getLogradouro() != null) {
                        getCidades("cidades.json", adapter_es.getPosition(cepz.getUf()));
                        spinnerestados.setSelection(adapter_es.getPosition(cepz.getUf()));

                        endereco.setText(cepz.getLogradouro());
                        bairro.setText(cepz.getBairro());
                        complemento.setText(cepz.getComplemento());
                        cidade_escolhida = cepz.getLocalidade();
                        estado_escolhido = cepz.getUf();
                        int spinnerPosition = adapter_es.getPosition(cepz.getUf());
                        spinnerestados.setSelection(spinnerPosition);
                        int spinnerPosition2 = adapter_ci.getPosition(cepz.getLocalidade());
                        spinnercidades.setSelection(spinnerPosition2);
                        //progressBar.setVisibility(View.INVISIBLE);

                        progressBar2.setVisibility(View.INVISIBLE);
                        textViewConsulta.setText("Consulta Cep");
                        textInputLayoutNUMERO_.requestFocus();
                    }else{
                        new AestheticDialog.Builder(CadConveniosActivity.this, DialogStyle.FLAT, DialogType.INFO)
                                .setTitle("Atenção!")
                                .setMessage("Endereço não encontrado, verifique se o CEP está correto!")
                                .setCancelable(false).
                                setGravity(Gravity.CENTER)
                                .setAnimation(DialogAnimation.SHRINK)
                                .setOnClickListener(new OnDialogClickListener() {
                                    @Override
                                    public void onClick(@NonNull AestheticDialog.Builder builder) {
                                        progressBar2.setVisibility(View.INVISIBLE);
                                        textViewConsulta.setText("Consulta Cep");
                                        cep.requestFocus();
                                        builder.dismiss();
                                    }
                                }).show();
                    }
                }

                @Override
                public void onError(String error) {
                    toast("erro onError: " + error);
                    progressBar2.setVisibility(View.INVISIBLE);
                    textViewConsulta.setText("Consulta Cep");
                }
            });
        } else {
            progressBar2.setVisibility(View.INVISIBLE);
            textViewConsulta.setText("Consulta Cep");
            new AestheticDialog.Builder(CadConveniosActivity.this, DialogStyle.FLAT, DialogType.ERROR)
                    .setTitle("Atenção!").setMessage("Favor informar um CEP válido!").setCancelable(false).setGravity(Gravity.CENTER).setAnimation(DialogAnimation.SHRINK).show();
        }

    }
    public void getCidades(String NomeArquivo, int Estado){
        JSONObject jsonObject;
        ArrayList<String> cList = new ArrayList<>();
        try {
            InputStream is = getResources( ).getAssets().open(NomeArquivo);
            int size = is.available( );
            byte[] data = new byte[size];
            is.read(data);
            is.close( );
            String json = new String(data, StandardCharsets.UTF_8);
            jsonObject = new JSONObject(json);

            JSONArray estados = jsonObject.getJSONArray("estados");
            JSONObject jb1 = estados.getJSONObject(Estado);
            JSONArray jsonArray = jb1.getJSONArray("cidades");
            cList.clear();
            for(int s=0;s<jsonArray.length();s++) {
                cList.add(jsonArray.get(s).toString());
            }
            adapter_ci = new ArrayAdapter<>
                    (this, R.layout.spinner_cidades, cList);
            spinnercidades.setAdapter(adapter_ci);

            spinnercidades.setSelection(adapter_ci.getPosition(cidade_escolhida));
            //if (Estado == 10) {
            //    spinnercidades.setSelection(834);
            //    cidade_escolhida = cList.get(834);
            //}
            estado_escolhido = estados.getJSONObject(Estado).get("sigla").toString();
        }
        catch (IOException | JSONException e){e.printStackTrace();}
    }
    public ArrayList<String> getEstados(String NomeArquivo){
        JSONObject jsonObject;
        ArrayList<String> cList = new ArrayList<>();
        try {
            InputStream is = getResources( ).getAssets().open(NomeArquivo);
            int size = is.available( );
            byte[] data = new byte[size];
            is.read(data);
            is.close( );
            String json = new String(data, StandardCharsets.UTF_8);
            jsonObject = new JSONObject(json);
            JSONArray estados = jsonObject.getJSONArray("estados");
            for(int i=0;i<estados.length();i++)
            {
                JSONObject jb1 = estados.getJSONObject(i);
                cList.add(jb1.getString("sigla"));
            }
        }
        catch (IOException | JSONException e){e.printStackTrace();}
        return cList;
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    private void submitForm() {
        if(!checkRS()){
            rs.setAnimation(animShake);
            rs.startAnimation(animShake);
            //vib.vibrate(120);
            return;
        }
        if(!checkNF()){
            nf.setAnimation(animShake);
            nf.startAnimation(animShake);
            //vib.vibrate(120);
            return;
        }
        if(!checkCEP()){
            cep.setAnimation(animShake);
            cep.startAnimation(animShake);
            //vib.vibrate(120);
            return;
        }
        if(!checkENDERECO()){
            endereco.setAnimation(animShake);
            endereco.startAnimation(animShake);
            //vib.vibrate(120);
            return;
        }
        if(!checkNUMERO()){
            numero.setAnimation(animShake);
            numero.startAnimation(animShake);
            //vib.vibrate(120);
            return;
        }
        if(!checkBAIRRO()){
            bairro.setAnimation(animShake);
            bairro.startAnimation(animShake);
            //vib.vibrate(120);
            return;
        }
        if(!checkCELULAR()){
            cel.setAnimation(animShake);
            cel.startAnimation(animShake);
            //vib.vibrate(120);
            return;
        }
        if( !checkCNPJ() ){
            cnpj.setAnimation(animShake);
            cnpj.startAnimation(animShake);
            //vib.vibrate(120);
            return;
        }
        if( !checkCPF() ){
            cpf.setAnimation(animShake);
            cpf.startAnimation(animShake);
            //vib.vibrate(120);
            return;
        }
        if(!checkEMAIL()){
            email.setAnimation(animShake);
            email.startAnimation(animShake);
            //vib.vibrate(120);
            return;
        }
        textInputLayoutRS_.setErrorEnabled(false);
        textInputLayoutNF_.setErrorEnabled(false);
        textInputLayoutCEP_.setErrorEnabled(false);
        textInputLayoutENDERECO_.setErrorEnabled(false);
        textInputLayoutNUMERO_.setErrorEnabled(false);
        textInputLayoutBAIRRO_.setErrorEnabled(false);
        textInputLayoutCELULAR_.setErrorEnabled(false);
        textInputLayoutCPF_.setErrorEnabled(false);
        textInputLayoutCNPJ_.setErrorEnabled(false);
        textInputLayoutEMAIL_.setErrorEnabled(false);
        grava();
        btnGravarConvenio.setEnabled(false);
    }
    private boolean checkRS(){
        if(Objects.requireNonNull(rs.getText()).toString().trim().isEmpty()){
            textInputLayoutRS_.setErrorEnabled(true);
            textInputLayoutRS_.setError("Favor informar a razão social");
            rs.setError("Preenchimento obrigatório");
            requestFocus();
            rs.requestFocus();
            return false;
        }
        textInputLayoutRS_.setErrorEnabled(false);
        return true;
    }
    private boolean checkNF(){
        if(Objects.requireNonNull(nf.getText()).toString().trim().isEmpty()){
            textInputLayoutNF_.setErrorEnabled(true);
            textInputLayoutNF_.setError("Favor informar o nome fantasia");
            nf.setError("Preenchimento obrigatório");
            requestFocus();
            nf.requestFocus();
            return false;
        }
        textInputLayoutNF_.setErrorEnabled(false);
        return true;
    }
    private boolean checkCEP(){
        if(cep.getText().toString().trim().isEmpty()){
            textInputLayoutCEP_.setErrorEnabled(true);
            textInputLayoutCEP_.setError("Favor informar o CEP");
            cep.setError("Preenchimento obrigatório");
            requestFocus();
            cep.requestFocus();
            return false;
        }
        textInputLayoutCEP_.setErrorEnabled(false);
        return true;
    }
    private boolean checkENDERECO(){
        if(Objects.requireNonNull(endereco.getText()).toString().trim().isEmpty()){
            textInputLayoutENDERECO_.setErrorEnabled(true);
            textInputLayoutENDERECO_.setError("Favor informar o endereço");
            endereco.setError("Preenchimento obrigatório");
            requestFocus();
            endereco.requestFocus();
            return false;
        }
        textInputLayoutENDERECO_.setErrorEnabled(false);
        return true;
    }
    private boolean checkNUMERO(){
        if(numero.getText().toString().trim().isEmpty()){
            textInputLayoutNUMERO_.setErrorEnabled(true);
            textInputLayoutNUMERO_.setError("Favor informar o número");
            numero.setError("Preenchimento obrigatório");
            requestFocus();
            numero.requestFocus();
            return false;
        }
        textInputLayoutNUMERO_.setErrorEnabled(false);
        return true;
    }
    private boolean checkBAIRRO(){
        if(Objects.requireNonNull(bairro.getText()).toString().trim().isEmpty()){
            textInputLayoutBAIRRO_.setErrorEnabled(true);
            textInputLayoutBAIRRO_.setError("Favor informar o bairro");
            bairro.setError("Preenchimento obrigatório");
            requestFocus();
            bairro.requestFocus();
            return false;
        }
        textInputLayoutBAIRRO_.setErrorEnabled(false);
        return true;
    }
    private boolean checkCELULAR(){
        if(cel.getText().toString().trim().isEmpty()){
            textInputLayoutCELULAR_.setErrorEnabled(true);
            textInputLayoutCELULAR_.setError("Favor informar o celular");
            cel.setError("Preenchimento obrigatório");
            requestFocus();
            cel.requestFocus();
            return false;
        }
        textInputLayoutCELULAR_.setErrorEnabled(false);
        return true;
    }
    private boolean checkCNPJ(){
        String cnpj_ = cnpj.getText().toString().trim();
        //String cpf_ = cpf.getText().toString().trim();

        if (optJuridica.isChecked()){

            if( cnpj_.isEmpty() ){
                textInputLayoutCNPJ_.setErrorEnabled(true);
                textInputLayoutCNPJ_.setError("Favor informar o CNPJ");
                cnpj.setError("Preenchimento obrigatório");
                requestFocus();
                cnpj.requestFocus();
                return false;
            } else if (!geraCpfCnpj.isCNPJ(cnpj_)) {
                textInputLayoutCNPJ_.setErrorEnabled(true);
                textInputLayoutCNPJ_.setError("Favor informar um CNPJ válido");
                cnpj.setError("CNPJ INVÁLIDO");
                requestFocus();
                cnpj.requestFocus();
                return false;
            }
        }
        textInputLayoutCNPJ_.setErrorEnabled(false);
        return true;
    }
    private boolean checkCPF(){
        String cpf_ = cpf.getText().toString().trim();
        if (optFisica.isChecked()) {
            if (cpf_.isEmpty()) {
                textInputLayoutCPF_.setErrorEnabled(true);
                textInputLayoutCPF_.setError("Favor informar o CPF");
                cpf.setError("Preenchimento obrigatório");
                requestFocus();
                cpf.requestFocus();
                return false;
            } else if (!geraCpfCnpj.isCPF(cpf_)) {
                textInputLayoutCPF_.setErrorEnabled(true);
                textInputLayoutCPF_.setError("Favor informar o CPF válido");
                cpf.setError("CPF INVÁLIDO");
                requestFocus();
                cpf.requestFocus();
                return false;
            }
        }
        textInputLayoutCPF_.setErrorEnabled(false);
        return true;
    }
    private boolean checkEMAIL(){
        String email_ = email.getText().toString().trim();
        if(email_.isEmpty() || !isValidEmail(email_)){
            textInputLayoutEMAIL_.setErrorEnabled(true);
            textInputLayoutEMAIL_.setError("Favor informa um E-MAIL válido");
            email.setError("Preenchimento obrigatório");
            requestFocus();
            email.requestFocus();
            return false;
        }
        textInputLayoutEMAIL_.setErrorEnabled(false);
        return true;
    }
    private void requestFocus(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }
    private static boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void preenche_categorias() throws CertificateException {
        String urlm        = getResources().getString(R.string.HOST) + "categorias_class_app.php";
        listCategorias     = new ArrayList<>();
        xCod_categoria_int = 0;
        xNome              = "";
        requestQueue       = Volley.newRequestQueue(this);

        HashMap<String,Integer> mCategorias = new HashMap<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlm,
                response -> {
                    try {
                        JSONArray arr = new JSONArray(response);
                        mCategorias.put("ESCOLHA A CATEGORIA",0);

                        for(int i=1;i<arr.length();i++){
                               //constroi a lista de uma categoria
                                xNome           = arr.getJSONObject(i).getString("nome");
                                xCod_categoria_int  = Integer.parseInt(arr.getJSONObject(i).getString("codigo"));
                                mCategorias.put(xNome,xCod_categoria_int);
                        }
                        // CRIA UM SPINN COM 2 COLUNAS ID INT E VALUE STRING
                        List<StringWithTag> cList = new ArrayList<>();
                        TreeMap<String,Integer> treeMap = new TreeMap<>(mCategorias);

                        Set<Map.Entry<String,Integer>> entrySet = treeMap.entrySet();
                        /*
                         * Convert the entry set to ArrayList using constructor
                         */
                        List<Map.Entry<String,Integer>> listEntries = new ArrayList<Map.Entry<String,Integer>>( entrySet );

                        for (Map.Entry<String,Integer> entry : listEntries) {
                            Integer key = entry.getValue();
                            String value = entry.getKey();
                            /* Build the StringWithTag List using these keys and values. */
                            cList.add(new StringWithTag(value, key));
                        }

                        adapter_ca = new ArrayAdapter<>
                                (CadConveniosActivity.this, R.layout.spinner_cidades, cList);
                        spinnercategorias.setAdapter(adapter_ca);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        showErrorDialog("Erro ao processar categorias.");
                    }
                    // Display the first 500 characters of the response string.
                    //textView.setText("Response is: "+ response.substring(0,500));
                }, error -> Toast.makeText(CadConveniosActivity.this, "Error: "+error.getMessage(),Toast.LENGTH_LONG).show());
        requestQueue.add(stringRequest);
    }
    private void showErrorDialog(String message) {
        runOnUiThread(() -> {
            new AlertDialog.Builder(CadConveniosActivity.this)
                    .setTitle("Erro")
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        });
    }
    public class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {
        public void onItemSelected(AdapterView<?> parent,
                                   View view, int pos, long id) {
            getCidades("cidades.json",pos);
        }
        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

    private void toast(String msg){
        Toast.makeText(getBaseContext(),msg,Toast.LENGTH_LONG).show();
    }
    public void grava() {
        String urlm = getResources( ).getString(R.string.HOST) + "grava_convenio.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,urlm,
                response -> {
                    JSONObject campos;
                    try {
                        campos = new JSONObject(response);
                        String situacao_cad = campos.getString("situacao");
                        if(situacao_cad.equals("2")){
                            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                            dlg.setTitle("Aviso");
                            dlg.setMessage("JÁ EXISTE ESTE CPF ( "+campos.getString("cpf")+" ) CADASTRADO, INFORME OUTRO OU ENTRE EM CONTATO COM 35 99812-0032 PARA REVALIDAR SUA SENHA! SE VOCE JÁ É CONVENIADO, NÃO PRESCISA CADASTRAR, BASTA UTILIZAR O USUARIO E A SENHA QUE VOCE USA NO SITE PARA ENTRAR NO APP");
                            dlg.setPositiveButton("VOLTAR", (dialog, which) -> dialog.cancel()).show();
                            btnGravarConvenio.setEnabled(true);
                        }else if(situacao_cad.equals("3")) {
                            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
                            dlg.setTitle("Aviso");
                            dlg.setMessage("JÁ EXISTE ESTE CNPJ ( "+campos.getString("cnpj")+" ) CADASTRADO, INFORME OUTRO OU ENTRE EM CONTATO COM 35 99812-0032 PARA REVALIDAR SUA SENHA! SE VOCE JÁ É CONVENIADO, NÃO PRESCISA CADASTRAR, BASTA UTILIZAR O USUARIO E A SENHA QUE VOCE USA NO SITE PARA ENTRAR NO APP");
                            dlg.setPositiveButton("VOLTAR", (dialog, which) -> dialog.cancel()).show();
                            btnGravarConvenio.setEnabled(true);
                        }else {
                            usuario_send_mail = campos.getString("usuario");
                            senha_send_mail = campos.getString("senha");
                            email_destino = campos.getString("email");
                            SendEmail(usuario_send_mail,senha_send_mail,email_destino);
                            //criar_contrato();
                            Intent intent = new Intent(CadConveniosActivity.this, ConfirmaCadConvenioActivity.class);
                            intent.putExtra("cod_convenio", campos.getString("cod_convenio"));
                            intent.putExtra("cpf", campos.getString("cpf"));
                            intent.putExtra("cnpj", campos.getString("cnpj"));
                            intent.putExtra("email", campos.getString("email"));
                            intent.putExtra("celular", campos.getString("cel"));
                            intent.putExtra("usuario", campos.getString("usuario"));
                            intent.putExtra("senha", campos.getString("senha"));
                            startActivity(intent);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
                    Toast.makeText(CadConveniosActivity.this, "Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
                    //progressBar.setVisibility(View.GONE);
                }) {
            @Override
            public Map<String, String> getParams() {
                params = new HashMap<>();
                params.put("C_razaosocial", Objects.requireNonNull(rs.getText()).toString());
                params.put("C_nomefantasia", Objects.requireNonNull(nf.getText()).toString());
                params.put("C_endereco", Objects.requireNonNull(endereco.getText()).toString());
                params.put("C_numero",  numero.getText().toString());
                params.put("C_bairro", Objects.requireNonNull(bairro.getText()).toString());
                params.put("C_cidade", cidade_escolhida);
                params.put("C_uf", estado_escolhido);
                params.put("C_cep", cep.getText().toString().replace(".",""));
                params.put("C_tel1", tel.getText().toString().replace(" ",""));
                params.put("C_tel2", "");
                params.put("C_cel", cel.getText().toString().replace(" ",""));
                params.put("C_tipo", "");
                params.put("C_contato", Objects.requireNonNull(responsavel.getText()).toString());
                params.put("C_prolabore", "0");
                params.put("C_prolabore2", "4");
                params.put("C_cnpj", cnpj.getText().toString());
                params.put("C_cpf", cpf.getText().toString());
                params.put("C_Inscestadual", "");
                params.put("C_categoria", xCod_categoria_string);
                params.put("C_categoriarecibo", "");
                params.put("C_parcelamento", "");
                params.put("C_registro", "");
                params.put("C_datacadastro", dataHoraAtualx);
                params.put("C_email", email.getText().toString());
                params.put("C_email2", "");
                params.put("C_inscmunicipal", "");
                params.put("C_tipoempresa", valTipoGrupoEmpresa);
                params.put("C_cobranca", "true");
                params.put("C_desativado", "false");
                params.put("C_app", "true");

                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private static class StringWithTag {
        public final String string;
        public final Object tag;
        public StringWithTag(String string, Object tag) {
            this.string = string;
            this.tag = tag;
        }
        //@NotNull
        @NonNull
        @Override
        public String toString() {
            return string;
        }
    }
    private void email_html(String usuario_x, String senha_x){
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
        "<h1>Bem Vindo ao App QrCred!</h1>"+
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
                "<p>Segue os dados para acesso ao App QrCred.</p>"+
        "</td>"+
        "</tr>"+
        "<tr>"+
        "<td class='esd-block-text es-p20t es-p30r es-p30l es-m-txt-l' align='left'>"+
        "<p>Usuario :&nbsp;<h2>" + usuario_x + "</h2><br>"+
        "Senha :&nbsp;<h2>"+ senha_x + "</h2></p>"+
        "</td>"+
        "</tr>"+
        "<tr>"+
        "<td class='esd-block-text es-p20t es-p40b es-p30r es-p30l es-m-txt-l' align='left'>"+
        "<p>Atenciosamente,</p>"+
        "<p>QrCred Tecnologia</p>"+
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
    public void SendEmail(String usuario_x, String senha_x, String Email_destino){

        try {
            email_html(usuario_x,senha_x);
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

            mimeMessage.setSubject("Dados login App QrCred");


            mimeMessage.setContent(mensagem_email,"text/html");

            Thread thread = new Thread(() -> {
                try {
                    Transport.send(mimeMessage);
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            });
            thread.start();

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
