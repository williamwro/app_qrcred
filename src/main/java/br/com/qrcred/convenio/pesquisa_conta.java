package br.com.qrcred.convenio;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
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
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import br.com.qrcred.ConveniosFragment;
import br.com.qrcred.ExpandableAdapter;
import br.com.qrcred.ExtratoFragment;
import br.com.qrcred.R;
import br.com.qrcred.RoomDB;
import br.com.qrcred.Usuario_local;
import br.com.qrcred.ViewPagerAdapter_Conv;
import br.com.qrcred.conta;
import br.com.qrcred.meses_db;

/**
 *
 * Activities that contain this fragment must implement the
 * {@link ExtratoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExtratoFragment#//newInstance} factory method to
 * create an instance of this fragment.
 */
public class pesquisa_conta extends Fragment{

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public ConveniosFragment.OnFragmentInteractionListener mListener;

    public ExpandableAdapter variavelAdapter;
    //private PhotoView mImageView;
    RequestQueue requestQueue;
    RequestQueue requestQueue2;
    private Map<String, String> params;

    private EditText editPalavra;
    private RecyclerView recycleViewPesquisa;
    private DatabaseReference databaseReferenceConta;
    private FirebaseDatabase firebaseDatabase;
    public String Mes_Corrente;
    RequestQueue requestQueue3;
    public Integer codconvenio;
    //public String mes_corrente;
    public String[] array_combo;
    Spinner spinner;
    Query query;
    Button btnBuscar;
    String palavra;
    ProgressBar progressBar;
    public double total = 0.0;
    public String totalx;
    String valor_exibir;
    String total2;
    String total3;
    TextView Soma_mes;
    RecyclerView ListaMes;
    ArrayAdapter<conta> arrayAdapter;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    public String[] data;
    public String[] tabTitle;
    public String[] periodo;
    public Integer posicao_tab=0;
    ViewPagerAdapter_Conv adapter;

    public pesquisa_conta() {
        // Required empty public constructor
    }

    public static pesquisa_conta newInstance(String param1, String param2) {
        pesquisa_conta fragment = new pesquisa_conta();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ContaConvenioAdapter mAdapter;
    ArrayList<conta> lista;
    //private Object conta;
    public SearchView searchView;

    private Map<String, String> params2;
    public static RoomDB myAppDatabase;
    public List<Usuario_local> usuario_locals;
    String hora;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_pesquisa_conta, container, false);

        Intent intent    = getActivity().getIntent();

        tabLayout        = v.findViewById(R.id.tabLayout);
        viewPager2       = v.findViewById(R.id.viewpager2);

        codconvenio      = Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("cod_convenio")));
         Mes_Corrente     = intent.getStringExtra("mes_corrente");

        busca_meses();

        return v;
    }
    private void busca_meses(){
        String urlm = getResources().getString(R.string.HOST) + "meses_conta_app.php";
        requestQueue = Volley.newRequestQueue(requireContext());
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
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
            //progressBar.setVisibility(View.GONE);
        }
        );
        requestQueue.add(stringRequest);
    }
    private void carregarSpinner(String reposta){
        ArrayList<meses_db> listames = new ArrayList<>();
        try {
            JSONArray jsonarraymeses = new JSONArray(reposta);
            for(int i=0;i<jsonarraymeses.length();i++){
                meses_db m = new meses_db(jsonarraymeses.getJSONObject(i).getString("abreviacao"),
                                          jsonarraymeses.getJSONObject(i).getString("data"),
                                          jsonarraymeses.getJSONObject(i).getString("completo"),
                                          jsonarraymeses.getJSONObject(i).getString("periodo"));
                listames.add(m);
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
            adapter = new ViewPagerAdapter_Conv(requireActivity(),data,codconvenio);
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

    @Override
    public void onResume() {
        super.onResume();

    }


}
