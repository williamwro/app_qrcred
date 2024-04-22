package br.com.qrcred;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;


import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Activities that contain this fragment must implement the
 * {@link //QrcodeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link //QrcodeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SaldoFragment extends Fragment{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public String mParam1;
    public String mParam2;
    public String Mes_Corrente = "";
    public String nome_associado_string;
    public String num_cartao;
    public String  limite;
    public double  limitex;
    public double saldo;
    TextView TextViewsaldo;
    public double total = 0.0;
    public String totalx;
    ProgressBar progressBar;
    public RequestQueue requestQueue;
    public RequestQueue requestQueue2;
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


    public ProgressBar mprogressBar;
    //ActionBarDrawerToggle drawerToggle;
    private Map<String, String> params;
    public Double latitude;
    public Double longitude;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    public FusedLocationProviderClient client;
    public SaldoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments( ) != null) {
            mParam1 = getArguments( ).getString(ARG_PARAM1);
            mParam2 = getArguments( ).getString(ARG_PARAM2);
        }

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

        if(email.equals("")){
            new AestheticDialog.Builder(requireActivity(), DialogStyle.FLAT, DialogType.ERROR)
                    .setTitle("Atenção!").setMessage("O seu E-mail não está cadastrado, favor atualizar seus dados no meu -> Meus Dados, deste App!").setCancelable(false).setGravity(Gravity.CENTER).setAnimation(DialogAnimation.SHRINK).show();
        }


    }

    @SuppressLint({"ClickableViewAccessibility", "CutPasteId"})
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_saldo, container, false);

        captura_mescorrente(v);

        ExtendedFloatingActionButton fab = v.findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Snackbar.make(view, "Atualizado", Snackbar.ANIMATION_MODE_SLIDE)
                    .setAction("Action", null).show();
            //View vx = getView();
            captura_mescorrente(Objects.requireNonNull(v));
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
        progressBar             = v2.findViewById(R.id.progressBarSaldo);

        num_cartao              = intent.getStringExtra("cartao");
        progressBar.setVisibility(View.GONE);
        limite                  = intent.getStringExtra("limite");
        String urlm = getResources().getString(R.string.HOST) + "meses_corrente_app.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlm,
                response -> {
                    try {
                        JSONArray arr = new JSONArray(response);
                        for(int i=0;i<arr.length();i++){
                            Mes_Corrente = arr.getJSONObject(i).getString("abreviacao");
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

            TextViewsaldo    = requireActivity().findViewById(R.id.textsaldo);
            String saldoxxx = currency.format(saldo);
            TextViewsaldo.setText(saldoxxx);
            //progressBar.setVisibility(View.GONE);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
    }

}
