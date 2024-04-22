package br.com.qrcred;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;

import java.util.ArrayList;

import java.util.List;

import java.util.Objects;

public class ExtratoFragment extends Fragment {
    public String Mes_Corrente = "";
    public String[] array_combo;
    public String matricula = "";
    public String empregador = "";
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapter adapter;
    public OnFragmentInteractionListener mListener;
    RequestQueue requestQueue;
    public String[] data;
    public String[] tabTitle;
    public String[] periodo;
    public Integer posicao_tab=0;
    public static RoomDB myAppDatabase;
    public List<Usuario_local> usuario_locals;
    public ExtratoFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_extrato, container, false);

        Intent intent    = requireActivity().getIntent();

        matricula        = intent.getStringExtra("matricula");
        empregador       = intent.getStringExtra("empregador");


        tabLayout        = v.findViewById(R.id.tabLayout);
        viewPager2       = v.findViewById(R.id.viewpager2);

        myAppDatabase = RoomDB.getInstance(getContext());
        usuario_locals = myAppDatabase.usuario_localDao().getAll();
        for(Usuario_local usr : usuario_locals){
            Mes_Corrente = usr.getMescorrente();
        }
        busca_meses(v);

        return v;
    }
    private void busca_meses(View v){
        //retorna todos os meses ta tabela
        String urlm = getResources().getString(R.string.HOST) + "meses_conta_app.php";
        requestQueue = Volley.newRequestQueue(v.getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlm,
                this::carregarSpinner,
                volleyError -> {
                String message = null; // error message, show it in toast or dialog, whatever you want
                if (volleyError instanceof NetworkError || volleyError instanceof AuthFailureError || volleyError instanceof TimeoutError) {
                    message = "Sua internet pode estar lenta ou não está conectado a Internet";
                } else if (volleyError instanceof ServerError) {
                    message = "Servidor indisponivel. por favor tente dentro de alguns minutos";
                } else if (volleyError instanceof ParseError) {
                    message = "Erro gerado! por favor, tente mais tarde";
                }
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
        }
        );
        requestQueue.add(stringRequest);
    }
    private void carregarSpinner(String reposta){

        ArrayList<meses_db> lista = new ArrayList<>();
        try {
            JSONArray jsonarraymeses = new JSONArray(reposta);

            for(int i=0;i<jsonarraymeses.length();i++){
                meses_db m = new meses_db(jsonarraymeses.getJSONObject(i).getString("abreviacao"),
                                          jsonarraymeses.getJSONObject(i).getString("data"),
                                          jsonarraymeses.getJSONObject(i).getString("completo"),
                                          jsonarraymeses.getJSONObject(i).getString("periodo"));
                lista.add(m);
                //construi um novo array para passar para a função SetSpinnerSelection e setar o mes corrente
                if (i == 0){
                    array_combo = new String[jsonarraymeses.length()];
                    tabTitle = new String[jsonarraymeses.length()];
                    data = new String[jsonarraymeses.length()];
                    periodo = new String[jsonarraymeses.length()];
                }
                array_combo[i] = jsonarraymeses.getJSONObject(i).getString("abreviacao");
                data[i] = jsonarraymeses.getJSONObject(i).getString("abreviacao");
                periodo[i] = jsonarraymeses.getJSONObject(i).getString("periodo");
                if(Objects.equals(Mes_Corrente, jsonarraymeses.getJSONObject(i).getString("abreviacao"))){
                    posicao_tab = i;
                }
                tabTitle[i] = jsonarraymeses.getJSONObject(i).getString("abreviacao");
                tabLayout.addTab(tabLayout.newTab().setText(jsonarraymeses.getJSONObject(i).getString("abreviacao")+"<br/>"+jsonarraymeses.getJSONObject(i).getString("periodo")));
            }

            adapter = new ViewPagerAdapter(requireActivity(),data,empregador,matricula);
            viewPager2.setAdapter(adapter);

            new TabLayoutMediator(tabLayout, viewPager2,
                    (tab, position) -> tab.setText(data[position]+" "+periodo[position])).attach();

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager2.setCurrentItem(tab.getPosition());
                }
                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                }
                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                }
            });
            viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageSelected(int position) {
                    super.onPageSelected(position);
                    tabLayout.selectTab(tabLayout.getTabAt(position));
                }
            });
            selectPage(posicao_tab);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    void selectPage(int pageIndex){
        tabLayout.setScrollPosition(pageIndex,0f,true);
        viewPager2.setCurrentItem(pageIndex);
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
