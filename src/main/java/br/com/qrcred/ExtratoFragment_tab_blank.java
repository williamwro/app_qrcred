package br.com.qrcred;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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

import org.json.JSONArray;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;

public class ExtratoFragment_tab_blank extends Fragment {


    public double total = 0.0;
    public String totalx;
    String valor_exibir;
    String total2;
    String total3;
    String matricula;
    String empregador;
    String mes;
    TextView Soma_mes;
    public String  limite;
    private Map<String, String> params;
    RequestQueue requestQueue;
    RequestQueue requestQueue2;
    ProgressBar progressBar;
    public String Mes_Corrente = "";
    public String[] array_combo;
    ArrayList<conta> lista = new ArrayList<>();
    private RecyclerAdapter.RecycleViewClickListner listnerx;
    String hora;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.activity_extrato_fragment_tab_blank,container,false);

        Soma_mes    = view.findViewById(R.id.textViewTotalExtrato);
        progressBar = view.findViewById(R.id.progressBarHome);
        progressBar.setVisibility(View.GONE);

        Bundle bundle = getArguments();
        if (bundle != null) {
            mes = bundle.getString("position");
            matricula = bundle.getString("matricula");
            empregador = bundle.getString("empregador");
        }

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Configure aqui sua view, por exemplo, configurar um RecyclerView ou definir listeners
        BuscaContaMes(mes, matricula, empregador);
    }
    public void BuscaContaMes(String mes, String matricula, String empregador){
        progressBar.setVisibility(View.VISIBLE);
        //String item = parent.getItemAtPosition(position).toString();
        //****************************************************************************
        String url2 = getResources().getString(R.string.HOST) + "conta_app.php";
        requestQueue2 = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url2,
                this::carregarList,
                volleyError -> {
                    String message = null; // error message, show it in toast or dialog, whatever you want
                    if (volleyError instanceof NetworkError || volleyError instanceof AuthFailureError || volleyError instanceof NoConnectionError || volleyError instanceof TimeoutError) {
                        message = "Sua internet pode estar lenta ou não está conectado a Internet";
                    } else if (volleyError instanceof ServerError) {
                        message = "Servidor indisponivel. por favor tente dentro de alguns minutos";
                    } else if (volleyError instanceof ParseError) {
                        message = "Erro gerado! por favor, tente mais tarde";
                    }
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }) {
            @Override
            public Map<String, String> getParams() {
                params = new HashMap<>();
                params.put("matricula", matricula);
                params.put("empregador", empregador);
                params.put("mes",mes);
                return params;
            }
        };
        requestQueue2.add(stringRequest2);
        //****************************************************************************
    }

    private void carregarList(String resposta){

        try {
            total = 0;
            hora = "";
            JSONArray jsonarrayconta = new JSONArray(resposta);
            for(int i=0;i<jsonarrayconta.length();i++){

                totalx = jsonarrayconta.getJSONObject(i).getString("valor");
                total += Double.parseDouble(totalx);

                hora = jsonarrayconta.getJSONObject(i).getString("hora");
                if (hora.length() >= 5) {
                    hora = hora.substring(0, 5);
                } else {
                    // A string `hora` é muito curta ou nula, trate de acordo
                    // Por exemplo, você pode definir `hora` como uma string vazia ou algum valor padrão
                    hora = ""; // Ou algum valor padrão adequado
                }
                conta m = new conta(jsonarrayconta.getJSONObject(i).getString("associado"),
                                    jsonarrayconta.getJSONObject(i).getString("nome"),
                                    jsonarrayconta.getJSONObject(i).getString("razaosocial"),
                                    jsonarrayconta.getJSONObject(i).getString("valor"),
                                    jsonarrayconta.getJSONObject(i).getString("mes"),
                                    jsonarrayconta.getJSONObject(i).getString("parcela"),
                                    jsonarrayconta.getJSONObject(i).getString("dia"),
                                    hora,
                                    jsonarrayconta.getJSONObject(i).getString("uri_cupom"),
                                    jsonarrayconta.getJSONObject(i).getString("nomefantasia"),
                                    jsonarrayconta.getJSONObject(i).getString("cnpj")
                );
                lista.add(m);
            }


            NumberFormat currency = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            valor_exibir = currency.format(total);
            total2 = valor_exibir;
            total3 = "Total : "+total2;
            Soma_mes.setText(total3);

            setOnClickListner();
            View view = getView();
            if (view != null) {
                RecyclerView recyclerView = getView().findViewById(R.id.mrecyclerView);
                RecyclerAdapter adapter = new RecyclerAdapter(getContext(),lista,listnerx);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                //ListaConta.setAdapter(arrayAdapter);
                progressBar.setVisibility(View.GONE);
                recyclerView.setClickable(true);
            } else {
                // A view do fragment ainda não está disponível neste ponto
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setOnClickListner() {
        listnerx = new RecyclerAdapter.RecycleViewClickListner() {
            @Override
            public void onClick(View v, int position) {
                Intent intentx;
                intentx = new Intent(getContext(), MostraCuponActivity.class);
                intentx.putExtra("uri_cupon", lista.get(position).getUri_cupom());
                intentx.putExtra("razaosocial", lista.get(position).getRazaoSocial());
                intentx.putExtra("nomefantasia", lista.get(position).getNomeFantasia());
                intentx.putExtra("data", lista.get(position).getDia());
                intentx.putExtra("hora", lista.get(position).getHora());
                intentx.putExtra("valor", lista.get(position).getValor());
                startActivity(intentx);
            }
        };
    }
}
