package br.com.qrcred.convenio;

import static com.mikepenz.iconics.Iconics.getApplicationContext;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.cert.CertificateException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import br.com.qrcred.MainActivityFotoCupom;
import br.com.qrcred.R;

public class HomeConvenioActivity extends Fragment {
    // VARIAVEIS -------------------------------

    TextView tvRazaosocial;
    TextView tvNomeFantasia;
    TextView tvEndereco;
    TextView tvBairro;
    TextView tvCidade;
    TextView tvCnpjoucpf;
    TextView tvAssociado;
    TextView tvSaldo;
    String nomefantasia;
    String endereco;
    String numero;
    String bairro;
    String cidade;
    String estado;
    String cep;
    String cnpj;
    String cpf;
    String cel;
    String tel;
    String email;
    TextView tvValorParcela;
    String cod_convenio;
    String pass;
    EditText edCartao;
    EditText edescricaocompra;
    EditText edTotalDigitado;
    EditText edSenha;

    Button btnLocalizaAssociado;
    Button btnGravar;
    Button btnLocaliza;
    String cartao;
    String nome_associado_string;
    public String Mes_Corrente = "";
    RequestQueue requestQueue2;
    RequestQueue requestQueue3;
    public double total = 0.0;
    public String totalx;
    public String  limite;
    public double  limitex;
    public double saldo;
    String valor_exibir;
    String total2;
    public String parcelas;
    public Integer parcelasx;
    public String qtde_parcelas_selecionado = "1";
    public Double Val_parcela_double;
    public String Val_parcela_string;
    public Float Val_parcela_float;
    public BigDecimal total_digitado;
    //public BigDecimal total_digitado_x;
    public int n_parcelas;
    Spinner spinner;
    //private IntentIntegrator qrScan;
    String nome_associado;
    String matricula;
    String empregador;
    public String valor_parcela;
    String celular;
    String nome_fantasia;
    String razaosocial;
    //String key_lan;
    String latitude;
    String longitude;
    public String categoria;
    String contato;
    String senha;
    String aceito_termo;
    String token_associado;
    public String primeiro_mes;
    private ProgressBar mprogressBar;
    public Map<String, String> params2;
    //AccountHeader headerResult;
    //Drawer result;
    FirebaseFirestore firebaseFirestore;
    String registrolan_parcel1;
    Context mContext;
    ActivityResultLauncher<Intent> abreActivity;

    public HomeConvenioActivity() {
        // Required empty public constructor
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_home_convenio, container, false);
        // associando controles ------------------------------------------
        tvRazaosocial        = v.findViewById(R.id.tvRazaoSocial);
        tvNomeFantasia       = v.findViewById(R.id.tvNomeFantasia);
        tvEndereco           = v.findViewById(R.id.tvEndereco);
        tvBairro             = v.findViewById(R.id.tvBairro);
        tvCidade             = v.findViewById(R.id.tvCidade);
        tvCnpjoucpf          = v.findViewById(R.id.tvCnpjCpf);
        edCartao             = v.findViewById(R.id.edCartao);
        btnLocalizaAssociado = v.findViewById(R.id.btnLerCartao);
        tvAssociado          = v.findViewById(R.id.tvAssociado);
        tvSaldo              = v.findViewById(R.id.tvSaldo);
        edTotalDigitado      = v.findViewById(R.id.Edtotal_digitado);
        spinner              = v.findViewById(R.id.spinnerParcelas);
        tvValorParcela       = v.findViewById(R.id.tvValorParcela);
        edSenha              = v.findViewById(R.id.edSenha);
        mprogressBar         = v.findViewById(R.id.progressBar_conv);
        btnGravar            = v.findViewById(R.id.btnRetornar);
        btnLocaliza          = v.findViewById(R.id.btnLocaliza);
        edescricaocompra     = v.findViewById(R.id.tvescricaocompra);

        //scrollTelaVendax      = findViewById(R.id.scrollTelaVenda);
        btnGravar.setEnabled(false);
        //********* progressbar ************
        RelativeLayout layout = v.findViewById(R.id.relativelayout_home_convenio);  //specify here Root layout Id
        mprogressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleLarge);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        layout.addView(mprogressBar, params);
        mprogressBar.setVisibility(View.GONE);

        //***********************************
        final Intent intent = requireActivity().getIntent( );
        //recebendo valores ----------------------------------------------------------
        cod_convenio  = intent.getStringExtra("cod_convenio");
        tvRazaosocial.setText(HtmlCompat.fromHtml(Objects.requireNonNull(intent.getStringExtra("razaosocial")), HtmlCompat.FROM_HTML_MODE_LEGACY));
        razaosocial = intent.getStringExtra("razaosocial");
        tvNomeFantasia.setText(HtmlCompat.fromHtml(Objects.requireNonNull(intent.getStringExtra("nome_fantasia")), HtmlCompat.FROM_HTML_MODE_LEGACY));
        nomefantasia = tvNomeFantasia.getText().toString();
        nome_fantasia = intent.getStringExtra("nome_fantasia");
        tvEndereco.setText(intent.getStringExtra("endereco")+","+intent.getStringExtra("numero"));
        endereco = intent.getStringExtra("endereco");
        tvBairro.setText(intent.getStringExtra("bairro"));
        bairro = intent.getStringExtra("bairro");
        estado = intent.getStringExtra("estado");
        tvCidade.setText(intent.getStringExtra("cidade")+"-"+estado);
        cidade = intent.getStringExtra("cidade");
        cnpj = intent.getStringExtra("cnpj");
        cpf = intent.getStringExtra("cpf");
        parcelas = intent.getStringExtra("parcelas");
        latitude = intent.getStringExtra("latitude");
        longitude = intent.getStringExtra("longitude");
        contato = intent.getStringExtra("contato");
        if(cnpj != null && cnpj.equals("")){
            tvCnpjoucpf.setText("CPF: "+intent.getStringExtra("cpf"));
        }else{
            tvCnpjoucpf.setText("CNPJ: "+intent.getStringExtra("cnpj"));
        }
        numero = intent.getStringExtra("numero");
        estado = intent.getStringExtra("estado");
        cep = intent.getStringExtra("cep");
        cel = intent.getStringExtra("cel");
        tel = intent.getStringExtra("tel");
        email = intent.getStringExtra("email");
        categoria = intent.getStringExtra("id_categoria");
        senha = intent.getStringExtra("senha");
        aceito_termo = intent.getStringExtra("aceito_termo");
        // adicionando toolbar ---------------------------------------------------
        /*Toolbar toolbar = v.findViewById(R.id.toolbar_home_convenio);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        Objects.requireNonNull(getSupportActionBar()).setTitle("QRCRED");*/

        edTotalDigitado.setEnabled(false);
        edSenha.setEnabled(false);
        spinner.setEnabled(false);

        //digitando o numero do cartao ----------------------------------------------------------
        edCartao.addTextChangedListener(new TextWatcher( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                //ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 50);
                //toneG.startTone(ToneGenerator.TONE_CDMA_CONFIRM , 50);
                //vibrate();
                if (editable.toString().length() == 10){

                    mprogressBar.setVisibility(View.VISIBLE);

                    cartao = editable.toString();

                    try {
                        Consulta_Associado();
                    } catch (CertificateException e) {
                        e.printStackTrace();
                    }

                    //Toast.makeText(getBaseContext( ), "VERIFICAR", Toast.LENGTH_LONG).show( );
                }else{
                    tvAssociado.setText("");
                    tvSaldo.setText("");

                }
            }
        });
        btnLocaliza.setOnClickListener(z -> {
            mprogressBar.setVisibility(View.VISIBLE);

            cartao = edCartao.getText().toString();
            if (cartao.length() > 0){
                try {
                    Consulta_Associado();
                } catch (CertificateException e) {
                    e.printStackTrace();
                }
            }else {
                mprogressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Informe o número do cartão.", Toast.LENGTH_LONG).show( );
            }
        });
        // formatando total digitado ------------------------------------------------------------
        Locale mLocale = new Locale("pt", "BR");
        edTotalDigitado.addTextChangedListener(new MoneyTextWatcher(edTotalDigitado, mLocale));
        edCartao.requestFocus();
        edTotalDigitado.setEnabled(false);
        // preenchendo spinn parcelas -----------------------------------------------------------
        if(parcelas != null){
            parcelasx = Integer.parseInt(parcelas);
        }else{
            parcelasx = 1;
        }
        ArrayList<String> listParcelas = new ArrayList<>();
        listParcelas.add("QUANTIDADE PARCELAS");
        for (int i = 1;i <= parcelasx;i++){
            listParcelas.add(String.valueOf(i));
        }
        // tratando valores ao selecionar quantidade de parcelas ----------------------------------------
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(), R.layout.linha_spiner_parcela, listParcelas){

            @Override
            public boolean isEnabled(int position){

                // Disabilita a primeira posição (hint)
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {

                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;

                if(position == 0){

                    // Deixa o hint com a cor cinza ( efeito de desabilitado)
                    tv.setTextColor(Color.GRAY);

                }else {
                    tv.setTextColor(Color.BLACK);
                }

                return view;
            }
        };
        adapter.setDropDownViewResource(R.layout.linha_spiner_parcela_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (pos > 0) {
                    Object item = parent.getItemAtPosition(pos);
                    n_parcelas = Integer.parseInt(item.toString( ));//convert obj em int
                    qtde_parcelas_selecionado = item.toString( );
                    if (total_digitado != null) {
                        BigDecimal tota_digt = new BigDecimal(String.valueOf(total_digitado));
                        double total_digt = tota_digt.doubleValue( );
                        if (n_parcelas > 1 && total_digt > 0) {
                            Val_parcela_double = total_digt / n_parcelas;
                            NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                            String valor_parcel = currency.format(Val_parcela_double);
                            Val_parcela_string = String.format(java.util.Locale.US,"%.2f", Val_parcela_double);
                            Val_parcela_float = Float.parseFloat(Val_parcela_string);
                            valor_parcela = valor_parcel;
                            tvValorParcela.setText("Valor Parcela : " + valor_parcel);
                            if (Val_parcela_float > saldo) {
                                Toast.makeText(getContext(), "O valor da parcela não pode ser maior que o saldo.", Toast.LENGTH_LONG).show( );
                                tvValorParcela.setText("Valor Parcela : ");
                                edTotalDigitado.setText("0");
                                spinner.setSelection(0);
                            }
                        } else {
                            if (total_digt > saldo) {
                                //edTotalDigitado.setText("Digite o total :");
                                spinner.setSelection(0);
                                tvValorParcela.setText("Valor Parcela : ");
                                edTotalDigitado.setText("0");
                                Toast.makeText(getContext(), "O total não pode ser maior que o saldo!", Toast.LENGTH_LONG).show( );
                            }
                            if (total_digt == 0) {
                                spinner.setSelection(0);
                                Toast.makeText(getContext(), "Informe o valor total", Toast.LENGTH_LONG).show( );
                            } else if (n_parcelas == 1) {
                                tvValorParcela.setText("Valor Parcela : ");
                            }
                        }
                    }
                }
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // verificando a senha e se o valor total não atrapassa o saldo
        edSenha.addTextChangedListener(new TextWatcher( ) {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (total_digitado == null) return;
                BigDecimal tota_digt = new BigDecimal(String.valueOf(total_digitado));
                double total_digt = tota_digt.doubleValue( );
                if (qtde_parcelas_selecionado.equals("1")){
                    if( total_digt > saldo ){
                        edTotalDigitado.setText("0");
                        spinner.setSelection(0);
                        edTotalDigitado.setEnabled(true);
                        edTotalDigitado.requestFocus();
                        edSenha.setText("");
                        AlertDialog.Builder dlg = new AlertDialog.Builder(requireContext());
                        dlg.setTitle("Aviso");
                        dlg.setMessage(" total não pode ser maior que o saldo!");
                        dlg.setNeutralButton("OK",null);
                        dlg.show();
                    }else if(total_digt == 0){
                        edTotalDigitado.requestFocus();
                        AlertDialog.Builder dlg = new AlertDialog.Builder(requireContext());
                        dlg.setTitle("Aviso");
                        dlg.setMessage("O total tem q ser maior que zero!");
                        dlg.setNeutralButton("OK",null);
                        dlg.show();
                    }
                }else{
                    NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                    String valor_parcel = currency.format(Val_parcela_double);
                    Val_parcela_string = String.format(java.util.Locale.US,"%.2f", Val_parcela_double);
                    Val_parcela_float = Float.parseFloat(Val_parcela_string);
                    valor_parcela = valor_parcel;
                    if (Val_parcela_float > saldo){

                        AlertDialog.Builder dlg = new AlertDialog.Builder(requireContext());
                        dlg.setTitle("Aviso");
                        dlg.setMessage("O valor da parcela é maior que o saldo!");
                        dlg.setNeutralButton("OK",null);
                        dlg.show();
                        spinner.setSelection(0);
                        tvValorParcela.setText("Valor Parcela : ");
                        edTotalDigitado.setText("0");
                        edSenha.setText("");
                        btnGravar.setEnabled(false);
                        //edSenha.setEnabled(false);

                    }
                }
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 200);
                toneG.startTone(ToneGenerator.TONE_PROP_BEEP , 400);
                //toneG.startTone(ToneGenerator.TONE_CDMA_EMERGENCY_RINGBACK, 400);
                if (editable.length() == 6){
                    btnGravar.setEnabled(true);
                    pass = editable.toString();
                    SenhaAssociado();
                }else{
                    btnGravar.setEnabled(false);
                }
            }
        });
        btnLocalizaAssociado.setOnClickListener(view -> {
            Intent intent1 = new Intent(getContext(), Scanner.class);
            abreActivity.launch(intent1);
        });
        abreActivity = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result ->{
                    if(result.getResultCode() == 1) {
                        assert result.getData() != null;
                        edCartao.setText(result.getData().getStringExtra("cartao"));
                    }
                }
        );
        btnGravar.setOnClickListener(view -> {
            ProximaTela();
            btnGravar.setEnabled(false);
        });

        //HomeConvenioActivity.this.setSupportActionBar(toolbar);

        edCartao.setText("");
        edCartao.setSelection(0);
        edCartao.setFocusableInTouchMode(true);
        edCartao.requestFocus();

        caputra_mescorrente();


        return v;
    }
    public void Consulta_Associado() throws CertificateException {
        String urlm = getResources().getString(R.string.HOST) + "localizaasapp.php";
        requestQueue2 = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlm,
                response -> {
                    JSONObject jsonResponse;
                    try {
                        jsonResponse = new JSONObject(response);
                        String Obj_nome = jsonResponse.getString("nome");
                        if(!Obj_nome.equals("login incorreto")){
                            matricula       = jsonResponse.getString("matricula");
                            nome_associado  = jsonResponse.getString("nome");
                            empregador      = jsonResponse.getString("empregador");
                            celular         = jsonResponse.getString("cel");
                            limite          = jsonResponse.getString("limite");
                            token_associado = jsonResponse.getString("token_associado");
                            tvAssociado.setText(Obj_nome);
                            nome_associado_string = Obj_nome;
                            caputra_mescorrente();
                        }else{
                            mprogressBar.setVisibility(View.GONE);
                            AlertDialog.Builder dlg = new AlertDialog.Builder(getContext());
                            dlg.setTitle("Aviso");
                            dlg.setMessage("Cartão não encontrado.");
                            dlg.setNeutralButton("OK",null);
                            dlg.show();
                            edCartao.requestFocus();
                            tvAssociado.setText("CARTÃO NÃO ENCONTRADO");
                            mprogressBar.setVisibility(View.GONE);
                            //edTotalDigitado.setText("");
                            edTotalDigitado.setEnabled(false);
                            spinner.setSelection(0);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace( );
                    }
                },
                volleyError -> {
                    String message = null; // error message, show it in toast or dialog, whatever you want
                    if (volleyError instanceof NetworkError || volleyError instanceof AuthFailureError || volleyError instanceof TimeoutError) {
                        mprogressBar.setVisibility(View.GONE);
                        message = "O sinal do celular pode estar fraco ou não está conectado a Internet, tente novamente";
                    } else if (volleyError instanceof ServerError) {
                        mprogressBar.setVisibility(View.GONE);
                        message = "Servidor indisponivel. tente dentro de alguns minutos";
                    }  else if (volleyError instanceof ParseError) {
                        mprogressBar.setVisibility(View.GONE);
                        message = "Erro gerado! por favor, tente mais tarde";
                    }
                    mprogressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), message,Toast.LENGTH_LONG).show();

                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> postMap = new HashMap<>();
                postMap.put("cartaodigitado", cartao);
                return postMap;
            }
        };
        requestQueue2.add(stringRequest);
    }
    public void caputra_mescorrente() {
        //progressBar.setVisibility(View.VISIBLE);
        String urlm = getResources().getString(R.string.HOST) + "meses_corrente_app.php";
        requestQueue3 = Volley.newRequestQueue(requireContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlm,
                response -> {
                    try {
                        JSONArray arr = new JSONArray(response);
                        for(int i=0;i<arr.length();i++){
                            Mes_Corrente = arr.getJSONObject(i).getString("abreviacao");
                            //tvMes.setText(Mes_Corrente);
                        }
                        //****************************************************************************
                        String url2 = getResources().getString(R.string.HOST) + "conta_app.php";
                        requestQueue2 = Volley.newRequestQueue(requireContext());
                        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url2,
                                this::CalculaTotal,
                                volleyError -> {
                                    String message = null; // error message, show it in toast or dialog, whatever you want
                                    if (volleyError instanceof NetworkError || volleyError instanceof AuthFailureError || volleyError instanceof NoConnectionError || volleyError instanceof TimeoutError) {
                                        message = "Sua internet pode estar lenta ou não está conectado a Internet";
                                    } else if (volleyError instanceof ServerError) {
                                        message = "Servidor indisponivel. por favor tente dentro de alguns minutos";
                                    } else if (volleyError instanceof ParseError) {
                                        message = "Erro gerado! por favor, tente mais tarde";
                                    }
                                    //Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                    mprogressBar.setVisibility(View.GONE);
                                }) {
                            @Override
                            public Map<String, String> getParams() {
                                params2 = new HashMap<>();
                                params2.put("matricula", matricula);
                                params2.put("empregador", empregador);
                                params2.put("mes",Mes_Corrente);
                                return params2;
                            }
                        };
                        requestQueue2.add(stringRequest2);
                        //****************************************************************************
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, volleyError -> {
                    String message = null; // error message, show it in toast or dialog, whatever you want
                    if (volleyError instanceof NetworkError || volleyError instanceof AuthFailureError || volleyError instanceof TimeoutError) {
                        message = "Sua internet pode estar lenta ou não está conectado a Internet";
                    } else if (volleyError instanceof ServerError) {
                        message = "Servidor indisponivel. tente dentro de alguns minutos";
                    }  else if (volleyError instanceof ParseError) {
                        message = "Erro gerado! por favor, tente mais tarde";
                    }
                    Toast.makeText(getContext(), message,Toast.LENGTH_LONG).show();
                    mprogressBar.setVisibility(View.GONE);
                });
        requestQueue3.add(stringRequest);
    }
    private void CalculaTotal(String resposta){
        try {
            total = 0;
            JSONArray jsonarrayconta = new JSONArray(resposta);
            for(int i=0;i<jsonarrayconta.length();i++){
                totalx = jsonarrayconta.getJSONObject(i).getString("valor");
                total += Double.parseDouble(totalx);
            }
            NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            valor_exibir = currency.format(total);
            total2 = valor_exibir;

            limitex = Double.parseDouble(limite);
            saldo = limitex - total;

            String saldoxxx = "Saldo : " + currency.format(saldo);
            tvSaldo.setText(saldoxxx);
            edTotalDigitado.setEnabled(true);
            edTotalDigitado.requestFocus();
            edSenha.setEnabled(true);
            spinner.setEnabled(true);

            mprogressBar.setVisibility(View.GONE);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public class MoneyTextWatcher implements TextWatcher {
        private final WeakReference<EditText> editTextWeakReference;
        private final Locale locale;
        public MoneyTextWatcher(EditText editText, Locale locale) {
            this.editTextWeakReference = new WeakReference<>(editText);
            this.locale = locale != null ? locale : Locale.getDefault();
        }
        public MoneyTextWatcher(EditText editText) {
            this.editTextWeakReference = new WeakReference<>(editText);
            this.locale = Locale.getDefault();
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }
        @Override
        public void afterTextChanged(Editable editable) {
            EditText editText = editTextWeakReference.get();
            if (editText.getText().toString().equals("")) return;
            editText.removeTextChangedListener(this);
            //calcula a parcela digitando o total ------------------------------------------
            BigDecimal total_digitado_b = parseToBigDecimal(editable.toString(),locale);

            BigDecimal total_digitado_xy = new BigDecimal(String.valueOf(total_digitado_b));
            double total_digitado_xyz = total_digitado_xy.doubleValue();
            if(total_digitado_xyz > 0 && n_parcelas > 1 ){
                Val_parcela_double = total_digitado_xyz / n_parcelas;
                NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                String valor_parcel = currency.format(Val_parcela_double);
                valor_parcela = valor_parcel;
                tvValorParcela.setText("Valor Parcela : "+valor_parcel);
                /*if(Val_parcela_double > saldo){
                    AlertDialog.Builder dlg = new AlertDialog.Builder(HomeConvenioActivity.this);
                    dlg.setTitle("Aviso");
                    dlg.setMessage("O valor da parcela não pode ser maior que o saldo.");
                    dlg.setNeutralButton("OK",null);
                    dlg.show();
                    tvValorParcela.setText("Valor Parcela : ");
                    edTotalDigitado.setText("0");
                    //spinner.setSelection(0);
                }*/
            }else if(total_digitado_xyz > 0 && n_parcelas == 0 ){
                valor_parcela = "";
                tvValorParcela.setText("Valor Parcela : ");
                edTotalDigitado.setText("0");
                n_parcelas = 0;
                qtde_parcelas_selecionado="1";
                spinner.setSelection(0);
            }else{
                valor_parcela = "";
                tvValorParcela.setText("Valor Parcela : ");
                edTotalDigitado.setText("0");
                n_parcelas = 0;
                qtde_parcelas_selecionado="1";
                spinner.setSelection(0);
            }
            BigDecimal parsed = parseToBigDecimal(editable.toString(), locale);
            String formatted = NumberFormat.getCurrencyInstance(locale).format(parsed);
            total_digitado = parsed;
            editText.setText(formatted);
            editText.setSelection(formatted.length());
            editText.addTextChangedListener(this);
        }

        private BigDecimal parseToBigDecimal(String value, Locale locale) {
            String replaceable = String.format("[%s,.\\s]", Objects.requireNonNull(NumberFormat.getCurrencyInstance(locale).getCurrency()).getSymbol());

            String cleanString = value.replaceAll(replaceable, "");
            if(!cleanString.equals("")){
                return new BigDecimal(cleanString).setScale(
                        2, RoundingMode.FLOOR).divide(new BigDecimal(100), RoundingMode.FLOOR
                );
            }else{
                return null;
            }
        }
    }
    public void setCodCartao(String cartao){
        edCartao.setText(cartao);
    }
    public void ProximaTela(){
        Intent intent2 = new Intent(getApplicationContext(), MainActivityFotoCupom.class);
        intent2.putExtra("nome",nome_associado_string);
        intent2.putExtra("matricula",matricula);
        intent2.putExtra("cod_convenio",cod_convenio);
        intent2.putExtra("nome_fantasia",nome_fantasia);
        intent2.putExtra("razaosocial",razaosocial);
        intent2.putExtra("celular",celular);
        intent2.putExtra("valor_pedido",total_digitado.toString());
        intent2.putExtra("endereco", endereco);
        intent2.putExtra("bairro", bairro);
        intent2.putExtra("cidade", cidade);
        intent2.putExtra("cnpj", cnpj);
        intent2.putExtra("cpf", cpf);
        intent2.putExtra("mes_corrente", Mes_Corrente);
        intent2.putExtra("primeiro_mes", Mes_Corrente);
        intent2.putExtra("parcelas", parcelas);
        //intent2.putExtra("lancamento", Obj_registrolan);
        //intent2.putExtra("uid", conta_app.getuid());
        intent2.putExtra("token_associado", token_associado);
        intent2.putExtra("email", email);
        intent2.putExtra("senha", senha);
        //intent2.putExtra("datacad", Obj_datacad);
        //intent2.putExtra("hora", Obj_hora);
        intent2.putExtra("nparcelas",parcelas);
        intent2.putExtra("nparcelas_selecionada",qtde_parcelas_selecionado);
        intent2.putExtra("codcarteira",cartao);
        intent2.putExtra("id_categoria",categoria);
        intent2.putExtra("pass",pass);
        intent2.putExtra("nome_associado",nome_associado);
        intent2.putExtra("empregador",empregador);
        if(Val_parcela_string == null){
            intent2.putExtra("Val_parcela_float","");
        }else{
            intent2.putExtra("Val_parcela_float",Val_parcela_string);
        }
        intent2.putExtra("descricao",edescricaocompra.getText().toString());



        startActivity(intent2);
        //requireActivity().finish();
    }
    public void SenhaAssociado(){
        mprogressBar.setVisibility(View.VISIBLE);
        String urlm = getResources().getString(R.string.HOST) + "consulta_pass_assoc.php";
        requestQueue2 = Volley.newRequestQueue(requireActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlm,
                response -> {
                    JSONObject jsonResponse;
                    try {
                        if(!response.equals("")) {
                            jsonResponse = new JSONObject(response);
                            String Obj_situacao = jsonResponse.getString("situacao");
                            if (Obj_situacao.equals("certo")) {
                                btnGravar.setEnabled(true);
                                btnGravar.requestFocus();
                                //GravaVenda();
                                //Toast.makeText(HomeConvenioActivity.this, "Senha certa!", Toast.LENGTH_LONG).show( );
                            } else {
                                AlertDialog.Builder dlg = new AlertDialog.Builder(requireContext());
                                dlg.setTitle("Aviso");
                                dlg.setMessage("Senha Errada.");
                                dlg.setNeutralButton("OK",null);
                                dlg.show();
                                edSenha.setText("");
                                edSenha.requestFocus();
                                //Toast.makeText(HomeConvenioActivity.this, "Senha errada!", Toast.LENGTH_LONG).show( );
                            }
                        }else{
                            AlertDialog.Builder dlg = new AlertDialog.Builder(requireContext());
                            dlg.setTitle("Aviso");
                            dlg.setMessage("Resposta vazia, avise o administrador do sistema!");
                            dlg.setNeutralButton("OK",null);
                            dlg.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace( );
                    }
                },
                volleyError -> {
                    String message = null; // error message, show it in toast or dialog, whatever you want
                    if (volleyError instanceof NetworkError || volleyError instanceof AuthFailureError || volleyError instanceof TimeoutError) {
                        message = "A internet está lenta ou não está conectado a Internet";
                    } else if (volleyError instanceof ServerError) {
                        message = "Servidor indisponivel. tente dentro de alguns minutos";
                    }  else if (volleyError instanceof ParseError) {
                        message = "Erro gerado! por favor, tente mais tarde";
                    }
                    Toast.makeText(getContext(), message,Toast.LENGTH_LONG).show();
                    mprogressBar.setVisibility(View.GONE);
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> postMap = new HashMap<>();
                postMap.put("pass", pass);
                postMap.put("matricula", matricula);
                postMap.put("empregador", empregador);
                return postMap;
            }
        };
        requestQueue2.add(stringRequest);
        mprogressBar.setVisibility(View.GONE);
    }


}
