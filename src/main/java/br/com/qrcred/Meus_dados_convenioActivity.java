package br.com.qrcred;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;
import com.thecode.aestheticdialogs.OnDialogClickListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.qrcred.convenio.ActivityLoginConvenio;
import br.com.qrcred.convenio.model.CEP;
import br.com.qrcred.convenio.model.SimpleCallback;
import br.com.qrcred.convenio.service.CEPService;
import br.com.qrcred.viacep.ViaCEP;

public class Meus_dados_convenioActivity extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Meus_dados_convenioActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DadosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Meus_dados_convenioActivity newInstance(String param1, String param2) {
        Meus_dados_convenioActivity fragment = new Meus_dados_convenioActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    EditText nomefantasiacom;
    EditText razaosocialcom;
    EditText cepcom;
    EditText enderecocom;
    EditText numerocom;
    EditText bairrocom;
    EditText cnpjcom;
    EditText cpfcom;
    EditText emailcom;
    EditText telcom;
    EditText celcom;
    EditText contato;
    String nomefantasiacom_s;
    String razaosocialcom_s;
    String cepcom_s;
    String enderecocom_s;
    String numerocom_s;
    String bairrocom_s;
    String cnpjcom_s;
    String cpfcom_s;
    String emailcom_s;
    String telcom_s;
    String celcom_s;
    String codconvenio;
    String contato_s;
    String cidade_s;
    String uf_s;
    private Map<String, String> params;
    RequestQueue requestQueue;
    final GeraCpfCnpj geraCpfCnpj = new GeraCpfCnpj();
    public ArrayAdapter<String> adapter_ci_com;
    private ArrayAdapter<StringWithTag> adapter_ca;
    private StringWithTag aux;
    public ArrayAdapter<String> adapter_es_com;
    public Spinner spinnercidadesCom;
    public Spinner spinnerestadosCom;
    public String cidade_escolhida;
    public String estado_escolhido;
    Button btnBuscar;
    Button btnGravarConv;
    public int spinnerPosition2_x;
    public Spinner spinnercategorias;
    public String xCod_categoria_string;
    public ArrayList<Categorias_class> listCategorias;
    private int xCod_categoria_int;
    public String xNome;
    String cod_categoria_S;
    Integer cod_categoria_I;
    String nome_categoria;
    String email;
    String senha;
    Animation animShake;
    private Vibrator vib;
    public TextInputLayout textInputLayoutEMAIL_;
    public boolean internet_ok;
    public ViaCEP findcep;
    public FirebaseAuth mAuth;
    FirebaseUser user;
    AuthCredential credential;
    public ProgressBar progressBar2;
    public CardView cardView;
    public TextView textViewConsulta;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        /*AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Atenção")
                .setMessage("Para se conveniar ao cartão Makecard, " +
                        "é necessario fazer um cadastro." +
                        "Os dados aqui coletados são:" +
                        "\n" +
                        "\n" +
                        "Razão Social, Nome Fantasia, Cep, Endereço completo, Tel Fixo, Celular, CNPJ, CPF, Email e Nome para contato" +
                        "\n" +
                        "\n" +
                        "Todos os dados citados acima serão armzenados em nosso banco de dados e " +
                        " usados única e exclusivamente para funcionalidades " +
                        "do Aplicativo Makecard. Jamais serão compartilhados, conforme a lei de privacidade e " +
                        "segurança de dados." +
                        "\n" +
                        "\n" +
                        "Voce permite coletar seus dados?")
                .setCancelable(false)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intentx;
                        intentx = new Intent(requireActivity(), HomeConvenioActivity.class);
                        startActivity(intentx);
                    }
                });
        //Creating dialog box
        AlertDialog dialog  = builder.create();
        dialog.show();*/
    }
    @SuppressLint({"ClickableViewAccessibility", "CutPasteId"})
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_meus_dados_convenio, container, false);

       /* // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            ActionBar ab = getSupportActionBar();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_black_24dp, null);
            //drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_IN));
            ab.setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        getSupportActionBar().setTitle("Meus dados");*/

        //ASSOCIA OS CONTROLES
        nomefantasiacom   = v.findViewById(R.id.EdNomeFantasia);
        razaosocialcom    = v.findViewById(R.id.EdRazaoSocialCom);
        cepcom            = v.findViewById(R.id.edCepCom);
        enderecocom       = v.findViewById(R.id.EdEnderecoCom);
        numerocom         = v.findViewById(R.id.EdNumeroCom);
        bairrocom         = v.findViewById(R.id.EdBairroCom);
        cnpjcom           = v.findViewById(R.id.EdCnpjCom);
        cpfcom            = v.findViewById(R.id.EdCpfCom);
        emailcom          = v.findViewById(R.id.EdEmailCom);
        telcom            = v.findViewById(R.id.EdTelCom);
        celcom            = v.findViewById(R.id.EdCelCom);
        //btnBuscar         = v.findViewById(R.id.btnConsultaCepCom);
        btnGravarConv     = v.findViewById(R.id.btnGravarConv);
        contato           = v.findViewById(R.id.EdNomeContato);
        spinnercategorias = v.findViewById(R.id.spCategoriaConv);
        spinnerestadosCom = v.findViewById(R.id.spEstadosCom);
        spinnercidadesCom = v.findViewById(R.id.spCidadeCom);
        textInputLayoutEMAIL_  = v.findViewById(R.id.textInputLayoutEMAIL);

        progressBar2       = v.findViewById(R.id.progressbarbotaoentrar);
        cardView           = v.findViewById(R.id.ButaoCardEntrar);
        textViewConsulta   = v.findViewById(R.id.textoEntrar);

        ArrayList<String> items_estados = getEstados("cidades.json");
        adapter_es_com = new ArrayAdapter<String>
                (getActivity(), R.layout.spinner_estados, items_estados);
        spinnerestadosCom.setAdapter(adapter_es_com);

        SimpleMaskFormatter smfcep = new SimpleMaskFormatter("NN.NNN-NNN");
        MaskTextWatcher mtcep = new MaskTextWatcher(cepcom, smfcep);
        cepcom.addTextChangedListener(mtcep);

        SimpleMaskFormatter smfcel = new SimpleMaskFormatter("(NN)N NNNN-NNNN");
        MaskTextWatcher mtcel = new MaskTextWatcher(celcom, smfcel);
        celcom.addTextChangedListener(mtcel);

        SimpleMaskFormatter smftel = new SimpleMaskFormatter("(NN) NNNN-NNNN");
        MaskTextWatcher mttel = new MaskTextWatcher(telcom, smftel);
        telcom.addTextChangedListener(mttel);

        SimpleMaskFormatter smfcpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtcpf = new MaskTextWatcher(cpfcom, smfcpf);
        cpfcom.addTextChangedListener(mtcpf);

        SimpleMaskFormatter smfcnpj = new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN");
        MaskTextWatcher mtcnpj = new MaskTextWatcher(cnpjcom, smfcnpj);
        cnpjcom.addTextChangedListener(mtcnpj);

        Intent intent = requireActivity().getIntent();
        codconvenio       = intent.getStringExtra("cod_convenio");
        nomefantasiacom_s = intent.getStringExtra("nome_fantasia");
        razaosocialcom_s  = intent.getStringExtra("razaosocial");
        cepcom_s          = intent.getStringExtra("cep");
        enderecocom_s     = intent.getStringExtra("endereco");
        numerocom_s       = intent.getStringExtra("numero");
        bairrocom_s       = intent.getStringExtra("bairro");
        cnpjcom_s         = intent.getStringExtra("cnpj");
        cpfcom_s          = intent.getStringExtra("cpf");

        emailcom_s        = intent.getStringExtra("email");
        telcom_s          = intent.getStringExtra("tel");
        celcom_s          = intent.getStringExtra("cel");
        contato_s         = intent.getStringExtra("contato");
        cidade_s          = intent.getStringExtra("cidade");
        uf_s              = intent.getStringExtra("estado");
        cod_categoria_S   = intent.getStringExtra("id_categoria");
        senha             = intent.getStringExtra("senha");
        cod_categoria_I   = Integer.parseInt(cod_categoria_S);

        //ATRIBUI OS VALORES

        nomefantasiacom.setText(nomefantasiacom_s);
        nomefantasiacom.setEnabled(nomefantasiacom_s.equals(""));
        razaosocialcom.setText(razaosocialcom_s);
        razaosocialcom.setEnabled(razaosocialcom_s.equals(""));
        enderecocom.setText(enderecocom_s);
        numerocom.setText(numerocom_s);
        bairrocom.setText(bairrocom_s);
        contato.setText(contato_s);

        if(!emailcom_s.equals("null")){
            emailcom.setText(emailcom_s);
        }else{
            emailcom.setText("");
        }
        cnpjcom.setText(cnpjcom_s);
        cnpjcom.setEnabled(cnpjcom_s.equals(""));
        cpfcom.setText(cpfcom_s);
        cpfcom.setEnabled(cpfcom_s.equals(""));
        cepcom.setText(cepcom_s);
        telcom.setText(telcom_s);
        celcom.setText(celcom_s);
        //cod_categoria_S = intent.getStringExtra("id_categoria");
        //cod_categoria_I = Integer.parseInt(cod_categoria_S);
        cidade_escolhida = cidade_s;
        estado_escolhido = uf_s;

        /* PEGANDO ID SPINNER ESTADOS E POPULANDO SPINNER CIDADES */
        spinnerestadosCom.setOnItemSelectedListener(new Meus_dados_convenioActivity.MyOnItemSelectedListener());
        //spinnerestadosCom.setSelection(10);
        spinnerestadosCom.setPrompt("Escolha o estado");
        spinnercidadesCom.setPrompt("Escolha a cidade");

        spinnerestadosCom.setSelection(adapter_es_com.getPosition(estado_escolhido));

        getCidades("cidades.json",adapter_es_com.getPosition(estado_escolhido));

        spinnercidadesCom.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        Object item = parent.getItemAtPosition(pos);
                        cidade_escolhida =  item.toString();     //prints the text in spinner item.
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        spinnerestadosCom.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        Object item = parent.getItemAtPosition(pos);
                        estado_escolhido =  item.toString();//prints the text in spinner item.
                        new Meus_dados_convenioActivity.MyOnItemSelectedListener();
                        getCidades("cidades.json", adapter_es_com.getPosition(estado_escolhido));
                        spinnerestadosCom.setSelection(adapter_es_com.getPosition(estado_escolhido));
                    }
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
        spinnercategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Object item = adapterView.getItemAtPosition(i);
                cod_categoria_I =  i;//prints the text in spinner item.
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        animShake = AnimationUtils.loadAnimation(getContext(),R.anim.shake);
        //vib = (Vibrator) getActivity(Context.VIBRATOR_SERVICE);
        nomefantasiacom_s = nomefantasiacom.getText().toString();
        razaosocialcom_s = razaosocialcom.getText().toString();
        cepcom_s = cepcom.getText().toString();
        enderecocom_s = enderecocom.getText().toString();
        numerocom_s = numerocom.getText().toString();
        bairrocom_s = bairrocom.getText().toString();
        cnpjcom_s = cnpjcom.getText().toString();
        cpfcom_s = cpfcom.getText().toString();

        emailcom_s = emailcom.getText().toString();
        telcom_s = telcom.getText().toString();
        celcom_s = celcom.getText().toString();
        preenche_categorias();

        emailcom.addTextChangedListener(new TextWatcher( ) {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                emailcom_s = emailcom.getText().toString();
            }
        });
        celcom.addTextChangedListener(new TextWatcher( ) {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                celcom_s = celcom.getText().toString();
            }
        });
        cpfcom.addTextChangedListener(new TextWatcher( ) {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                cpfcom_s = cpfcom.getText().toString();
                cpfcom_s = cpfcom_s.replace(".","");
                cpfcom_s = cpfcom_s.replace("-","");
                cpfcom_s = cpfcom_s.replace("_","");
                if (cpfcom_s.length() == 11) {
                    if (! geraCpfCnpj.isCPF(cpfcom_s)) {
                        cpfcom.setText("");
                        Toast.makeText(getContext(), "CPF INVÁLIDO!", Toast.LENGTH_LONG).show( );
                    }
                }
            }
        });

        mAuth = FirebaseAuth.getInstance();
        user  = FirebaseAuth.getInstance().getCurrentUser();
        if(!emailcom_s.equals("")) {
            entrar_por_email_senha(emailcom_s, senha);
            credential = EmailAuthProvider.getCredential(emailcom_s, senha);
        }

        btnGravarConv.setOnClickListener(view ->{

            submitForm();
            if(!emailcom_s.equals("")) {
                //----------------Code for Changing Email Address----------\\
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    user.verifyBeforeUpdateEmail(emailcom.getText().toString()).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            //Log.d("resultado", "atualizado.");
                            //direciona();
                        }
                    }).addOnFailureListener(e -> {
                        // Trata erros, como e-mail mal formatado, e-mail já em uso, etc.
                        Log.e("Erro e-mail verificação", Objects.requireNonNull(e.getMessage()));
                    });
                } else {
                    // Handle the case where there is no currently signed-in user
                    // For example, prompt the user to sign in or register
                    Log.e("Erro", "Nenhum usuário conectado para atualizar o e-mail.");
                }
            }
        });
        cardView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buscar_cep();
                    }
                });
        nomefantasiacom.setEnabled(false);
        razaosocialcom.setEnabled(false);

        return v;
    }
    public void buscar_cep(){
        progressBar2.setVisibility(View.VISIBLE);
        textViewConsulta.setText("");
        bairrocom.setText("");
        //this.cidade.setText("");
        enderecocom.setText("");
        //this.UF.setText("");
        // cep
        String cep = cepcom.getText().toString().replace(".", "");
        // verifica se o CEP é válido
        Pattern pattern = Pattern.compile("^[0-9]{5}-[0-9]{3}$");
        Matcher matcher = pattern.matcher(cep);
        if (matcher.find()) {
            CEPService service = new CEPService(getContext());
            service.getCEP(cep, new SimpleCallback<>() {

                @Override
                public void onResponse(CEP response) {
                    CEP cepz = response;
                    if(cepz.getLogradouro() != null) {
                        enderecocom.setText(cepz.getLogradouro());
                        bairrocom.setText(cepz.getBairro());
                        cidade_escolhida = cepz.getLocalidade();
                        estado_escolhido = cepz.getUf();
                        int spinnerPosition = adapter_es_com.getPosition(cepz.getUf());
                        spinnerestadosCom.setSelection(spinnerPosition);
                        int spinnerPosition2 = adapter_ci_com.getPosition(cepz.getLocalidade());
                        spinnercidadesCom.setSelection(spinnerPosition2);
                        spinnerPosition2_x = adapter_ci_com.getPosition(cepz.getLocalidade());
                        progressBar2.setVisibility(View.INVISIBLE);
                        textViewConsulta.setText("Consulta Cep");
                        numerocom.requestFocus();
                    }else{
                        new AestheticDialog.Builder(requireActivity(), DialogStyle.FLAT, DialogType.INFO)
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
                                        cepcom.requestFocus();
                                        builder.dismiss();
                                    }
                                })
                                .show();
                    }
                }

                @Override
                public void onError(String error) {
                    progressBar2.setVisibility(View.INVISIBLE);
                    textViewConsulta.setText("Consulta Cep");
                }
            });
        } else {
            progressBar2.setVisibility(View.INVISIBLE);
            textViewConsulta.setText("Consulta Cep");
            new AestheticDialog.Builder(requireActivity(), DialogStyle.FLAT, DialogType.ERROR)
                    .setTitle("Atenção!").setMessage("Favor informar um CEP válido!").setCancelable(false).setGravity(Gravity.CENTER).setAnimation(DialogAnimation.SHRINK).show();

        }
    }



    /* public boolean onSupportNavigateUp() {
         onBackPressed();
         return true;
     }
     private void requestFocus(View view){
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
     }*/
    private boolean checkEMAIL(){
        String email_ = emailcom.getText().toString().trim();
        if(email_.isEmpty() || !isValidEmail(email_)){
            textInputLayoutEMAIL_.setErrorEnabled(true);
            textInputLayoutEMAIL_.setError("Favor informa um E-MAIL válido");
            emailcom.setError("Preenchimento obrigatório");
            //requestFocus(emailcom);
            emailcom.requestFocus();
            return false;
        }
        textInputLayoutEMAIL_.setErrorEnabled(false);
        return true;
    }
    private static boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void submitForm() {
        if(!checkEMAIL()){
            emailcom.setAnimation(animShake);
            emailcom.startAnimation(animShake);
            //vib.vibrate(120);
            return;
        }
        textInputLayoutEMAIL_.setErrorEnabled(false);
        try {
            grava();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }
    public void onResume() {
        super.onResume();
    }
    public void grava() throws CertificateException {
        String urlm = getResources( ).getString(R.string.HOST) + "atualiza_convenio_app.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
            urlm,
            response -> {
                Log.i("Script", "SUCESS: "+response);
                new AestheticDialog.Builder(requireActivity(), DialogStyle.FLAT, DialogType.SUCCESS)
                        .setTitle("Parabéns!")
                        .setMessage("Dados atualizados com sucesso. Para visualizar os dados atualizados é necessário sair do App e entrar novamente,")
                        .setCancelable(false).
                        setGravity(Gravity.CENTER)
                        .setAnimation(DialogAnimation.SHRINK)
                        .setOnClickListener(new OnDialogClickListener() {
                            @Override
                            public void onClick(@NonNull AestheticDialog.Builder builder) {
                                Intent intent = new Intent(getActivity(), ActivityLoginConvenio.class);
                                startActivity(intent);
                            }
                        })

                        .show();

                //onSupportNavigateUp();
            }, error -> {
                Toast.makeText(getContext(), "Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
                //progressBar.setVisibility(View.GONE);
            }) {
                @Override
                public Map<String, String> getParams() {
                    params = new HashMap<>();
                    params.put("razaosocial", razaosocialcom.getText().toString());
                    params.put("nomefantasia", nomefantasiacom.getText().toString());
                    params.put("endereco", enderecocom.getText().toString());
                    params.put("numero", numerocom.getText().toString());
                    params.put("bairro", bairrocom.getText().toString());
                    params.put("cidade", cidade_escolhida);
                    params.put("estado", estado_escolhido);
                    params.put("cep", cepcom.getText().toString());
                    params.put("tel", telcom.getText().toString().replace(" ",""));
                    params.put("cel", celcom.getText().toString().replace(" ",""));
                    params.put("contato", contato.getText().toString());
                    params.put("cnpj", cnpjcom.getText().toString());
                    params.put("cpf", cpfcom.getText().toString());
                    params.put("email", emailcom.getText().toString());
                    params.put("codigo", codconvenio);
                    params.put("id_categoria", cod_categoria_I.toString());
                    return params;
               }
        };
        requestQueue = Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);
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
    public ArrayList<String> getCidades(String NomeArquivo, int Estado){
        JSONObject jsonObject;
        ArrayList<String> cListc = new ArrayList<>();
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
            cListc.clear();
            for(int s=0;s<jsonArray.length();s++) {
                cListc.add(jsonArray.get(s).toString());
            }
            adapter_ci_com = new ArrayAdapter<String>
                    (getActivity(), R.layout.spinner_cidades, cListc);
            spinnercidadesCom.setAdapter(adapter_ci_com);

            spinnercidadesCom.setSelection(adapter_ci_com.getPosition(cidade_escolhida));

        }
        catch (IOException e){e.printStackTrace();}
        catch (JSONException je){je.printStackTrace();}
        return cListc;
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
        catch (IOException e){e.printStackTrace();}
        catch (JSONException je){je.printStackTrace();}
        return cList;
    }
    private static class StringWithTag {
        public final String string;
        public final Object tag;
        public StringWithTag(String string, Object tag) {
            this.string = string;
            this.tag = tag;
        }
        @NotNull
        @Override
        public String toString() {
            return string;
        }
    }

   private void preenche_categorias() {
        String urlm        = getResources().getString(R.string.HOST) + "categorias_class_app.php";
        listCategorias     = new ArrayList<Categorias_class>();
        xCod_categoria_int = 0;
        xNome              = "";
        requestQueue       = Volley.newRequestQueue(requireContext());

        HashMap<Integer, String> mCategorias = new HashMap<Integer, String>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlm,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray arr = new JSONArray(response);
                            mCategorias.put(0,"ESCOLHA A CATEGORIA");

                            for(int i=0;i<arr.length();i++){
                                //constroi a lista de uma categoria
                                xNome           = arr.getJSONObject(i).getString("nome");
                                xCod_categoria_int  = Integer.parseInt(arr.getJSONObject(i).getString("codigo"));
                                if (xCod_categoria_int == cod_categoria_I){
                                    nome_categoria = xNome;
                                    aux = new StringWithTag( String.valueOf(xCod_categoria_int),xNome);
                                }
                                mCategorias.put(xCod_categoria_int,xNome);
                            }
                            // CRIA UM SPINN COM 2 COLUNAS ID INT E VALUE STRING
                            List<StringWithTag> cList = new ArrayList<>();

                            for (Map.Entry<Integer, String> entry : mCategorias.entrySet()) {
                                Integer key = entry.getKey();
                                String value = entry.getValue();
                                //* Build the StringWithTag List using these keys and values. *//*
                                cList.add(new Meus_dados_convenioActivity.StringWithTag(value, key));
                            }
                            adapter_ca = new  ArrayAdapter<Meus_dados_convenioActivity.StringWithTag>
                                    (requireContext(),R.layout.spinner_cidades,cList);
                            spinnercategorias.setAdapter(adapter_ca);
                            cod_categoria_I = Integer.valueOf(cod_categoria_S);

                            spinnercategorias.setSelection(cod_categoria_I);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        // Display the first 500 characters of the response string.
                        //textView.setText("Response is: "+ response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //textView.setText("That didn't work!");
            }
        });
        requestQueue.add(stringRequest);
    }
    public static boolean isNetworkAvailableconvenio(Context context) {
        if(context == null)  return false;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    }  else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                        return true;
                    }
                }
            }
        }
        Log.i("update_statut","Network is available : FALSE ");
        return false;
    }
    public void entrar_por_email_senha(String email, String password){
        if(!email.equals("null") && !password.equals("null")){
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
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
}
