package br.com.qrcred;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import br.com.qrcred.convenio.ContaConvenioAdapter;

public class ExtratoFragment_tab_blank_conv extends Fragment {


    public double total = 0.0;
    public String totalx;
    String valor_exibir;
    String total2;
    String total3;
    Integer codconvenio;
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

    private ContaConvenioAdapter.RecycleViewClickListner listnerx;
    String hora;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (ViewGroup) inflater.inflate(R.layout.activity_extrato_fragment_tab_blank,container,false);

        Soma_mes    = view.findViewById(R.id.textViewTotalExtrato);
        progressBar = view.findViewById(R.id.progressBarHome);
        progressBar.setVisibility(View.GONE);

        Bundle bundle = getArguments();
        assert bundle != null;

        mes = bundle.getString("position");
        codconvenio = bundle.getInt("codconvenio");


        BuscaContaMes(mes,codconvenio.toString());

        return view;
    }

    public void BuscaContaMes(String mes, String codconvenio){
        progressBar.setVisibility(View.VISIBLE);
        //String item = parent.getItemAtPosition(position).toString();
        //****************************************************************************
        String url2 = getResources().getString(R.string.HOST) + "conta_app_conv.php";
        requestQueue2 = Volley.newRequestQueue(requireContext());
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
                params.put("codconvenio", codconvenio);
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
                hora = hora.substring(0,5);
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
            RecyclerView recyclerView = requireView().findViewById(R.id.mrecyclerView);
            ContaConvenioAdapter adapter = new ContaConvenioAdapter(getContext(),lista,listnerx);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            //ListaConta.setAdapter(arrayAdapter);
            progressBar.setVisibility(View.GONE);
            recyclerView.setClickable(true);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setOnClickListner() {
        listnerx = new ContaConvenioAdapter.RecycleViewClickListner() {
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

