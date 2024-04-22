package br.com.qrcred;

import static com.mikepenz.iconics.typeface.IconicsHolder.getApplicationContext;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.gson.JsonObject;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AntecipacaoFragment extends Fragment {
    public String Mes_Corrente = "";
    // TESTE UPLOAD 22/04/2024
    public String nome_associado_string;
    String email;
    String cel;
    String cpf;
    String matricula;
    String empregador;
    String valor_exibir;
    String total2;
    String cep;
    String endereco;
    String numero;
    String bairro;
    String cidade;
    String estado;
    String celzap;
    String senha;
    String cartao;
    String cod_situacao2;
    public String num_cartao;
    public String  limite;
    public double  limitex;
    public double saldo;
    public String porcentagem;
    TextView tvSaldo;
    TextView tvTaxa;
    TextView tvValorDescontar;
    public double ValorDescontar_Double;
    public String ValorDescontar_String;
    public RequestQueue requestQueue;
    public RequestQueue requestQueue2;
    public double total = 0.0;
    public String totalx;
    private Map<String, String> params;
    EditText edTotalDigitado;
    EditText Edchave_pix;
    public double porcentagem_doble;
    public double result_porcentagem;
    public String result_porcentagem_string;
    Button btnGravar;
    EditText edSenha;
    String nome_divisao;
    String val_digitado;
    String email_receber_pedidos;
    public AntecipacaoFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent           = requireActivity().getIntent();
        matricula               = intent.getStringExtra("matricula");
        nome_associado_string   = intent.getStringExtra("nome");
        cep                     = intent.getStringExtra("cep");
        endereco                = intent.getStringExtra("endereco");
        numero                  = intent.getStringExtra("numero");
        bairro                  = intent.getStringExtra("bairro");
        cidade                  = intent.getStringExtra("cidade");
        estado                  = intent.getStringExtra("estado");
        celzap                  = intent.getStringExtra("celzap");
        email                   = intent.getStringExtra("email");
        cpf                     = intent.getStringExtra("cpf");
        cel                     = intent.getStringExtra("cel");
        empregador              = intent.getStringExtra("empregador");
        matricula               = intent.getStringExtra("matricula");
        senha                   = intent.getStringExtra("senha");
        cartao                  = intent.getStringExtra("cartao");
        cod_situacao2           = intent.getStringExtra("cod_situacao2");
        Mes_Corrente            = intent.getStringExtra("Mes_Corrente");
        nome_divisao            = intent.getStringExtra("nome_divisao");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_antecipacao, container, false);

        edTotalDigitado      = v.findViewById(R.id.Edtotal_digitado);
        tvSaldo              = v.findViewById(R.id.tvSaldoAntecipacao);
        tvTaxa               = v.findViewById(R.id.tvTaxa);
        tvValorDescontar     = v.findViewById(R.id.tvValorDescontar);
        btnGravar            = v.findViewById(R.id.btnRetornar);
        edSenha              = v.findViewById(R.id.edSenha);
        Edchave_pix          = v.findViewById(R.id.Edchave_pix);
        captura_mescorrente(v);
        btnGravar.setEnabled(false);
        // formatando total digitado ------------------------------------------------------------
        Locale mLocale = new Locale("pt", "BR");
        edTotalDigitado.addTextChangedListener(new AntecipacaoFragment.MoneyTextWatcher(edTotalDigitado, mLocale));
        edTotalDigitado.requestFocus();
        btnGravar.setOnClickListener(view -> {
            val_digitado = edTotalDigitado.getText().toString();
            if(!val_digitado.equals("")){
                if(!Edchave_pix.getText().toString().equals("")){
                    grava();
                    btnGravar.setEnabled(false);
                }else{
                    new AestheticDialog.Builder(requireActivity(), DialogStyle.FLAT, DialogType.ERROR)
                            .setTitle("Atenção!").setMessage("Digite o pix para receber o valor!").setCancelable(false).setGravity(Gravity.CENTER).setAnimation(DialogAnimation.SHRINK).show();
                    Edchave_pix.requestFocus();
                }
            }else{
                new AestheticDialog.Builder(requireActivity(), DialogStyle.FLAT, DialogType.ERROR)
                        .setTitle("Atenção!").setMessage("Digite o valor a resgatar!").setCancelable(false).setGravity(Gravity.CENTER).setAnimation(DialogAnimation.SHRINK).show();
                edTotalDigitado.requestFocus();
            }

        });
        return v;
    }
    public void captura_mescorrente(View v2) {
        Intent intent           = requireActivity().getIntent();
        matricula               = intent.getStringExtra("matricula");
        nome_associado_string   = intent.getStringExtra("nome");
        empregador              = intent.getStringExtra("empregador");
        Mes_Corrente            = intent.getStringExtra("Mes_Corrente");
        email                   = intent.getStringExtra("email");
        cel                     = intent.getStringExtra("cel");
        cpf                     = intent.getStringExtra("cpf");
        num_cartao              = intent.getStringExtra("cartao");
        limite                  = intent.getStringExtra("limite");
        String urlm = getResources().getString(R.string.HOST) + "meses_corrente_app.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlm,
                response -> {
                    try {
                        JSONArray arr = new JSONArray(response);
                        for(int i=0;i<arr.length();i++){
                            Mes_Corrente = arr.getJSONObject(i).getString("abreviacao");
                            porcentagem = arr.getJSONObject(i).getString("porcentagem");
                            email_receber_pedidos = arr.getJSONObject(i).getString("email");
                            //****************************************************************************
                            requestQueue2 = Volley.newRequestQueue(v2.getContext());
                            String url = getResources().getString(R.string.HOST) + "conta_app.php";
                            StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url,
                                    this::CalculaTotal,
                                    volleyError -> {
                                        String message = null; // error message, show it in toast or dialog, whatever you want
                                        if (volleyError instanceof NetworkError || volleyError instanceof AuthFailureError || volleyError instanceof TimeoutError) {
                                            //mprogressBar.setVisibility(View.GONE);
                                            message = "O sinal do celular pode estar fraco ou não está conectado a Internet, tente novamente";
                                        } else if (volleyError instanceof ServerError) {
                                            //mprogressBar.setVisibility(View.GONE);
                                            message = "Servidor indisponivel. tente dentro de alguns minutos";
                                        }  else if (volleyError instanceof ParseError) {
                                            //mprogressBar.setVisibility(View.GONE);
                                            message = "Erro gerado! por favor, tente mais tarde";
                                        }
                                        //mprogressBar.setVisibility(View.GONE);
                                        Toast.makeText(getContext(), message,Toast.LENGTH_LONG).show();
                                    }){
                                @Override
                                public Map<String, String> getParams() {
                                    params = new HashMap<>();
                                    params.put("matricula", matricula);
                                    params.put("empregador", empregador);
                                    params.put("mes",Mes_Corrente);
                                    return params;
                                }
                            };
                            requestQueue2.add(stringRequest2);
                            //****************************************************************************
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error ->{
            //progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(), error.getMessage(),Toast.LENGTH_LONG).show();
        });
        requestQueue = Volley.newRequestQueue(v2.getContext());
        requestQueue.add(stringRequest);
    }
    public void CalculaTotal(String resposta){
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

            //tvSaldo    = requireActivity().findViewById(R.id.tvSaldoAntecipacao);
            String saldoxxx = currency.format(saldo);
            tvSaldo.setText(saldoxxx);
            edTotalDigitado.requestFocus();
            //progressBar.setVisibility(View.GONE);

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
            if(total_digitado_xyz > 0 && total_digitado_xyz < saldo){
                // CALCULA VALOR PORCENTAGEM

                    BigDecimal porcentagem_doble_d = new BigDecimal(String.valueOf(porcentagem));
                    porcentagem_doble = porcentagem_doble_d.doubleValue();
                    result_porcentagem = total_digitado_xyz * porcentagem_doble / 100;
                    NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                    result_porcentagem_string = currency.format(result_porcentagem);
                    tvTaxa.setText(result_porcentagem_string);

                // CALCULA VALOR PORCENTAGEM
                // CALCULA TOTAL A DESCONTAR
                    ValorDescontar_Double = total_digitado_xyz + result_porcentagem;
                    NumberFormat currency2 = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
                    ValorDescontar_String = currency2.format(ValorDescontar_Double);
                    tvValorDescontar.setText(ValorDescontar_String);
                // CALCULA TOTAL A DESCONTAR
                btnGravar.setEnabled(true);
            }else if(total_digitado_xyz == 0  ){
                tvTaxa.setText("");
                tvValorDescontar.setText("");
                btnGravar.setEnabled(false);
            }else if(total_digitado_xyz > saldo && saldo != 0.0){
                new AestheticDialog.Builder(requireActivity(), DialogStyle.FLAT, DialogType.ERROR)
                        .setTitle("Atenção!").setMessage("O valor informado é maior que o saldo!").setCancelable(false).setGravity(Gravity.CENTER).setAnimation(DialogAnimation.SHRINK).show();

                tvTaxa.setText("");
                tvValorDescontar.setText("");
                edTotalDigitado.requestFocus();
                edTotalDigitado.setText("");
                editText.setText("");
                btnGravar.setEnabled(false);

            }
            BigDecimal parsed = parseToBigDecimal(editable.toString(), locale);
            String formatted = NumberFormat.getCurrencyInstance(locale).format(parsed);
            //total_digitado = parsed;
            edTotalDigitado.setText("0");
            editText.setText(formatted);
            editText.setSelection(formatted.length());
            editText.addTextChangedListener(this);
            // Limpeza do texto
            if (total_digitado_xyz > saldo) {
                edTotalDigitado.setText("");
                editText.setText("");
            }
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
    private void grava(){
        String urlm = getResources().getString(R.string.HOST) + "grava_antecipacao_app.php";
        requestQueue2 = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlm,
                response -> {
                    JSONArray jsonResponsepar;
                    JSONObject jsonResponse;
                    try {

                        jsonResponse = new JSONObject(response);


                        String retorno     = jsonResponse.getString("success");
                        String mensagem = jsonResponse.getString("message");
                        if(retorno.equals("false") && mensagem.equals("Erro: Senha incorreta.")){
                            new AestheticDialog.Builder(requireActivity(), DialogStyle.FLAT, DialogType.ERROR)
                                    .setTitle("Atenção!").setMessage("Senha incorreta!").setCancelable(false).setGravity(Gravity.CENTER).setAnimation(DialogAnimation.SHRINK).show();
                            edSenha.requestFocus();
                            btnGravar.setEnabled(true);
                        }else {
                            edTotalDigitado.setText("");
                            tvTaxa.setText("");
                            tvValorDescontar.setText("");
                            Edchave_pix.setText("");

                            Intent intent2 = new Intent(getApplicationContext(), AntecipacaoTelaFinal.class);
                            intent2.putExtra("matricula", matricula);
                            intent2.putExtra("nome", nome_associado_string);
                            intent2.putExtra("cep", cep);
                            intent2.putExtra("endereco", endereco);
                            intent2.putExtra("numero", numero);
                            intent2.putExtra("bairro", bairro);
                            intent2.putExtra("cidade", cidade);
                            intent2.putExtra("estado", estado);
                            intent2.putExtra("celzap", celzap);
                            intent2.putExtra("email", email);
                            intent2.putExtra("cpf", cpf);
                            intent2.putExtra("cel", cel);
                            intent2.putExtra("empregador", empregador);
                            intent2.putExtra("matricula", matricula);
                            intent2.putExtra("senha", senha);
                            intent2.putExtra("cartao", cartao);
                            intent2.putExtra("cod_situacao2", cod_situacao2);
                            intent2.putExtra("Mes_Corrente", Mes_Corrente);
                            intent2.putExtra("nome_divisao", nome_divisao);
                            intent2.putExtra("limite", limite);
                            intent2.putExtra("valor_pedido", val_digitado);
                            intent2.putExtra("taxa", tvTaxa.getText().toString().replace(",", "."));
                            intent2.putExtra("valor_descontar", ValorDescontar_String.replace(",", "."));
                            intent2.putExtra("email_receber_pedidos", email_receber_pedidos);
                            startActivity(intent2);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace( );
                    }
                },
                volleyError -> {
                    String message = null; // error message, show it in toast or dialog, whatever you want
                    if (volleyError instanceof NetworkError || volleyError instanceof AuthFailureError || volleyError instanceof TimeoutError) {
                        //mprogressBar.setVisibility(View.GONE);
                        message = "O sinal da internet pode estar fraco ou não está conectado a Internet, tente novamente";
                        //btnSalvarFotoCupon.setEnabled(true);
                    } else if (volleyError instanceof ServerError) {
                        //mprogressBar.setVisibility(View.GONE);
                        message = "Servidor indisponivel. tente dentro de alguns minutos";
                        //btnSalvarFotoCupon.setEnabled(true);
                    }  else if (volleyError instanceof ParseError) {
                        //mprogressBar.setVisibility(View.GONE);
                        message = "Erro gerado! por favor, tente mais tarde";
                        //btnSalvarFotoCupon.setEnabled(true);
                    }
                    Toast.makeText(getApplicationContext(), message,Toast.LENGTH_LONG).show();
                    //mprogressBar.setVisibility(View.GONE);
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> postMap = new HashMap<>();
                postMap.put("matricula", matricula);
                postMap.put("pass", edSenha.getText().toString());
                postMap.put("empregador", empregador);
                postMap.put("valor_pedido", edTotalDigitado.getText().toString().replace(",",".").replace("R$","").trim());
                postMap.put("taxa", tvTaxa.getText().toString().replace(",",".").replace("R$","").trim());
                postMap.put("valor_descontar", ValorDescontar_String.replace(",",".").replace("R$","").trim());
                postMap.put("mes_corrente", Mes_Corrente);
                postMap.put("chave_pix", Edchave_pix.getText().toString());

                return postMap;
            }
        };
        requestQueue2.add(stringRequest);

    }
}
