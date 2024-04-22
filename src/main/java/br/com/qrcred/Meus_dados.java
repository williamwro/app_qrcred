package br.com.qrcred;

import android.annotation.SuppressLint;
import android.content.Intent;

import androidx.annotation.NonNull;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.onurkaganaldemir.ktoastlib.KToast;
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
import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.qrcred.convenio.model.CEP;
import br.com.qrcred.convenio.model.SimpleCallback;
import br.com.qrcred.convenio.service.CEPService;
/*import br.com.makecard.viacep.ViaCEP;
import br.com.makecard.viacep.ViaCEPException;*/


public class Meus_dados extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public Meus_dados() {
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
    public static Meus_dados newInstance(String param1, String param2) {
        Meus_dados fragment = new Meus_dados();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    TextView Associado;
    EditText EmailAss;
    EditText celAss;
    EditText cpfAss;
    EditText cepAss;
    EditText enderecoAss;
    EditText numeroAss;
    EditText bairroAss;
    Switch switchWhatzapcel;
    String estado_celzap;
    Button btnBuscar;
    public String Email_S;
    public String cel_S;
    public String cpf_S;
    public String matricula_S;
    String matricula;
    public String nome_s;
    public String cartao_s;
    public String limite_s;
    String cartao;
    String senha;
    String resultado_gravacao;
    String empregador;
    Button btnGravar;
    public int spinnerPosition2_x;
    private ArrayAdapter<String> adapter_ci;
    private ArrayAdapter<Meus_dados.StringWithTag> adapter_ca;
    public ArrayAdapter<String> adapter_es;
    RequestQueue requestQueue;
    RequestQueue requestQueuebusca;
    public Spinner spinnercidadesAss;
    public Spinner spinnerestadosAss;
    final GeraCpfCnpj geraCpfCnpj = new GeraCpfCnpj();
    private static final String TAG = MainActivity.class.getSimpleName();
    public FirebaseAuth mAuth;
    private Map<String, String> params;
    public String cidade_escolhida;
    public String estado_escolhido;
    public boolean internet_ok;
    FirebaseUser user;
    AuthCredential credential;

    public String email;
    public ProgressBar progressBar2;
    public CardView cardView;
    public TextView textViewConsulta;
    String novo;
    String rotulo_associado;
   // public ViaCEP findcep;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        builder.setTitle("Atenção")
                .setMessage("Tela de atualização " +
                        "\nOs dados aqui coletados são: " +
                        "\n" +
                        "\n" +
                        "Nome,Endereço completo,Cep,Celular,CPF e Email." +
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
                    //@Override
                    //public void onClick(DialogInterface dialog, int which) {
                   // }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                   // @Override
                   // public void onClick(DialogInterface dialog, int which) {
                   //     Intent intentx;
                   //     intentx = new Intent(requireActivity(), MainActivity.class);
                   //     startActivity(intentx);
                    }
                });
        //Creating dialog box
        //AlertDialog dialog  = builder.create();
        //dialog.show();*/


    }
    @SuppressLint({"ClickableViewAccessibility", "CutPasteId"})
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_meus_dados, container, false);



        btnGravar         = v.findViewById(R.id.btnAtualizar);
        //Associado         = v.findViewById(R.id.tvNomeAssociado);
        EmailAss          = v.findViewById(R.id.edEmailAss);
        celAss            = v.findViewById(R.id.edCelularAss);
        cpfAss            = v.findViewById(R.id.edCPFAss);
        cepAss            = v.findViewById(R.id.edCepAss);
        enderecoAss       = v.findViewById(R.id.edEnderecoAss);
        numeroAss         = v.findViewById(R.id.edNumeroAss);
        bairroAss         = v.findViewById(R.id.edBairroAss);
        spinnerestadosAss = v.findViewById(R.id.spEstadosAss);
        spinnercidadesAss = v.findViewById(R.id.spCidadeAss);
        switchWhatzapcel  = v.findViewById(R.id.switchWhatzapcel);
        //btnBuscar         = v.findViewById(R.id.btnConsultaCepAss);

        progressBar2       = v.findViewById(R.id.progressbarbotaoentrar);
        cardView           = v.findViewById(R.id.ButaoCardEntrar);
        textViewConsulta   = v.findViewById(R.id.textoEntrar);

        SimpleMaskFormatter smfcep = new SimpleMaskFormatter("NN.NNN-NNN");
        MaskTextWatcher mtcep = new MaskTextWatcher(cepAss, smfcep);
        cepAss.addTextChangedListener(mtcep);

        SimpleMaskFormatter smfcel = new SimpleMaskFormatter("(NN)N NNNN-NNNN");
        MaskTextWatcher mtcel = new MaskTextWatcher(celAss, smfcel);
        celAss.addTextChangedListener(mtcel);

        SimpleMaskFormatter smfcpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtcpf = new MaskTextWatcher(cpfAss, smfcpf);
        cpfAss.addTextChangedListener(mtcpf);
        // POPULA ESTADOS
        ArrayList<String> items_estados = getEstados("cidades.json");
        adapter_es = new ArrayAdapter<String>
                (getActivity(), R.layout.spinner_estados, items_estados);
        spinnerestadosAss.setAdapter(adapter_es);

        /* PEGANDO ID SPINNER ESTADOS E POPULANDO SPINNER CIDADES */
        spinnerestadosAss.setOnItemSelectedListener(new Meus_dados.MyOnItemSelectedListener());
        //spinnerestadosAss.setSelection(10);
        spinnerestadosAss.setPrompt("Escolha o estado");
        spinnercidadesAss.setPrompt("Escolha a cidade");

        Intent intent = requireActivity().getIntent();
        cartao           = intent.getStringExtra("cartao");
        senha            = intent.getStringExtra("senha");
        email            = intent.getStringExtra("email");
        rotulo_associado = intent.getStringExtra("rotulo_associado");
        novo             = intent.getStringExtra("novo");

        mAuth = FirebaseAuth.getInstance();
        user  = FirebaseAuth.getInstance().getCurrentUser();
        // Get auth credentials from the user for re-authentication
        if(!email.equals("")) {
            entrar_por_email_senha(email, senha);
            credential = EmailAuthProvider.getCredential(email, senha);
        }

        spinnercidadesAss.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                cidade_escolhida =  item.toString();     //prints the text in spinner item.
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerestadosAss.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                estado_escolhido =  item.toString();
                new Meus_dados.MyOnItemSelectedListener();
                getCidades("cidades.json", adapter_es.getPosition(estado_escolhido));
                spinnerestadosAss.setSelection(adapter_es.getPosition(estado_escolhido));
                //prints the text in spinner item.
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        EmailAss.addTextChangedListener(new TextWatcher( ) {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                Email_S = EmailAss.getText().toString();
            }
        });
        celAss.addTextChangedListener(new TextWatcher( ) {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                cel_S = celAss.getText().toString();
            }
        });
        cpfAss.addTextChangedListener(new TextWatcher( ) {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                cpf_S = cpfAss.getText().toString();
                cpf_S = cpf_S.replace(".","");
                cpf_S = cpf_S.replace("-","");
                if (cpf_S.length() == 11) {
                    if (! geraCpfCnpj.isCPF(cpf_S)) {
                        cpfAss.setText("");
                        Toast.makeText(getContext(), "CPF INVÁLIDO!", Toast.LENGTH_LONG).show( );
                    }
                }
            }
        });
        btnGravar.setOnClickListener(v1 -> {
            try {
                grava();
            } catch (CertificateException e) {
                e.printStackTrace();
            }
            if(!Email_S.equals("")) {
                //----------------Code for Changing Email Address----------\\
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String novoEmail = EmailAss.getText().toString();
                    // Adicione aqui a validação do formato do novo email
                    user.verifyBeforeUpdateEmail(novoEmail).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                        }
                    }).addOnFailureListener(e -> {
                        // Trata erros, como e-mail mal formatado, e-mail já em uso, etc.
                        Log.e("Erro e-mail verificação", Objects.requireNonNull(e.getMessage()));
                    });
                } else {
                    // Usuário não está logado, direcione para a tela de login
                }
            }
        });
        consulta_dados();
        cardView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        buscar_cep();
                    }
                });
        return v;
    }
    public void buscar_cep(){
        progressBar2.setVisibility(View.VISIBLE);
        textViewConsulta.setText("");
        bairroAss.setText("");
        //this.cidade.setText("");
        enderecoAss.setText("");
        //this.UF.setText("");
        // cep
        String cep = cepAss.getText().toString().replace(".","");
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
                        enderecoAss.setText(cepz.getLogradouro());
                        bairroAss.setText(cepz.getBairro());
                        cidade_escolhida = cepz.getLocalidade();
                        estado_escolhido = cepz.getUf();
                        int spinnerPosition = adapter_es.getPosition(cepz.getUf());
                        spinnerestadosAss.setSelection(spinnerPosition);
                        int spinnerPosition2 = adapter_ci.getPosition(cepz.getLocalidade());
                        spinnerPosition2_x = adapter_ci.getPosition(cepz.getLocalidade());
                        spinnercidadesAss.setSelection(spinnerPosition2);
                        progressBar2.setVisibility(View.INVISIBLE);
                        textViewConsulta.setText("Consulta Cep");
                        numeroAss.requestFocus();
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
                                        cepAss.requestFocus();
                                        builder.dismiss();
                                    }
                                }).show();
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
    public void onResume() {
        super.onResume();
    }
    public void grava() throws CertificateException {
        String urlm = getResources( ).getString(R.string.HOST) + "atualiza_associado_app.php";
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
                                    Intent intent = new Intent(getActivity(), MainActivity.class);
                                    intent.putExtra("user", rotulo_associado);
                                    intent.putExtra("cartao", cartao);
                                    intent.putExtra("novo", novo);
                                    startActivity(intent);
                                    //FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                                    //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    //fragmentTransaction.replace(R.id.nav_host_fragment_content_home2_activity_menu, new SaldoFragment(),null).commit();
                                    //fragmentTransaction.addToBackStack(String.valueOf(new SaldoFragment()));

                                    builder.dismiss();
                                }
                            })
                            .show();
                }, error -> {
                    Toast.makeText(getContext(), "Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
                }) {
            @Override
            public Map<String, String> getParams() {
                params = new HashMap<>();
                params.put("codigo", matricula_S);
                params.put("empregador", empregador);
                params.put("email", Email_S);
                params.put("cel", cel_S.replace(" ",""));
                params.put("cep", cepAss.getText().toString().replace(".",""));
                params.put("endereco", enderecoAss.getText().toString());
                params.put("numero", numeroAss.getText().toString());
                params.put("bairro", bairroAss.getText().toString());
                params.put("cidade", cidade_escolhida);
                params.put("estado", estado_escolhido);
                params.put("cpf", cpfAss.getText().toString());
                params.put("celzap", estado_celzap);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(requireActivity());
        requestQueue.add(stringRequest);
    }
    public void busca_associado() {
        String urlm = getResources( ).getString(R.string.HOST) + "localiza_associado_app_3.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                urlm,
                response -> {
                    //Log.d("TAG passou", response);
                    JSONObject campos;
                    try {
                        campos = new JSONObject(response);
                        matricula_S      = campos.getString("matricula");
                        nome_s           = campos.getString("nome");
                        cartao_s         = campos.getString("cod_cart");
                        limite_s         = campos.getString("limite");
                        cpf_S            = campos.getString("cpf");
                        Email_S          = campos.getString("email");
                        cel_S            = campos.getString("cel");
                        cidade_escolhida = campos.getString("cidade");
                        estado_escolhido = campos.getString("uf");
                    } catch (JSONException e) {
                        e.printStackTrace( );
                    }
                }, error -> {
                    Toast.makeText(getContext(), "Error: "+error.getMessage(),Toast.LENGTH_LONG).show();

                }) {
            @Override
            public Map<String, String> getParams() {
                params = new HashMap<>();
                params.put("codigo", matricula_S);
                params.put("empregador", empregador);
                return params;
            }

        };
        requestQueuebusca = Volley.newRequestQueue(getContext());
        requestQueuebusca.add(stringRequest);
        //AppController.getInstance().addToRequestQueue(jsonArrayRequest);

    }
    public void consulta_dados(){
        String urlm = getResources().getString(R.string.HOST) + "localiza_associado_app_2.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlm,
                response -> {
                    JSONObject campos;
                    try {
                        campos = new JSONObject(response);

                        String resultado = campos.getString("situacao");

                        if (resultado.equals("1")) {

                            //Associado.setText(campos.getString("nome"));
                            nome_s = campos.getString("nome");
                            String email = campos.getString("email");
                            if(!email.equals("null") && !email.equals("")){
                                EmailAss.setText(campos.getString("email"));
                            }else{
                                EmailAss.setText("");
                            }
                            matricula = campos.getString("matricula");

                            empregador = campos.getString("empregador");
                            celAss.setText(campos.getString("cel"));
                            cpfAss.setText(campos.getString("cpf"));
                            cpfAss.setEnabled(campos.getString("cpf").equals(""));
                            cepAss.setText(campos.getString("cep"));
                            enderecoAss.setText(campos.getString("endereco"));
                            numeroAss.setText(campos.getString("numero"));
                            bairroAss.setText(campos.getString("bairro"));
                            cidade_escolhida = campos.getString("cidade");
                            estado_escolhido = campos.getString("uf");
                            cartao_s = campos.getString("cod_cart");
                            senha = campos.getString("senha");
                            if(!estado_escolhido.equals("null") && !estado_escolhido.equals("")) {
                                getCidades("cidades.json", adapter_es.getPosition(estado_escolhido));
                                spinnerestadosAss.setSelection(adapter_es.getPosition(estado_escolhido));
                            }else{
                                spinnerestadosAss.setSelection(adapter_es.getPosition("MG"));
                                getCidades("cidades.json", adapter_es.getPosition("MG"));

                            }
                            estado_celzap = campos.getString("celwatzap");
                            if(Objects.requireNonNull(estado_celzap).equalsIgnoreCase("true")){
                                estado_celzap = "true";
                                switchWhatzapcel.setChecked(true);
                            }else{
                                estado_celzap = "false";
                                switchWhatzapcel.setChecked(false);
                            }
                            Email_S = EmailAss.getText().toString();
                            cel_S =  celAss.getText().toString();
                            cpf_S = cpfAss.getText().toString();
                            matricula_S = campos.getString("matricula");
                            limite_s = campos.getString("limite");
                            switchWhatzapcel.setOnCheckedChangeListener((buttonView, isChecked) -> {
                                if(isChecked) {
                                    estado_celzap = "true";
                                }else{
                                    estado_celzap = "false";
                                }
                            });

                        } else if (resultado.equals("6")) {
                            KToast.warningToast(requireActivity(), "Senha errada.", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                        } else if (resultado.equals("2") || resultado.equals("3")) {
                            KToast.warningToast(requireActivity(), "Cartão não encontrado.", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                        } else if (resultado.equals("0")) {
                            KToast.errorToast(requireActivity(), "Cartão bloqueado.", Gravity.BOTTOM, KToast.LENGTH_AUTO);
                        }
                    }
                    catch (JSONException e) {
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
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getParams() {
                params = new HashMap<>();
                params.put("cartao", cartao);
                params.put("senha", senha);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(requireContext());
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
            adapter_ci = new ArrayAdapter<String>
                    (getActivity(), R.layout.spinner_cidades, cListc);
            spinnercidadesAss.setAdapter(adapter_ci);

            spinnercidadesAss.setSelection(adapter_ci.getPosition(cidade_escolhida));

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
