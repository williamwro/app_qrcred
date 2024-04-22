package br.com.qrcred;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

//import com.loopj.android.http.AsyncHttpResponseHandler;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Activities that contain this fragment must implement the
 * {@link ConveniosFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConveniosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConveniosFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public OnFragmentInteractionListener mListener;

    public ExpandableAdapter variavelAdapter;

    public int xCodigo;
    public String xCategoria;
    public String xNfantasia;
    public String xEndereco;
    public String xNumero;
    public String xBairro;
    public String xTelefone;
    public String xCelular;
    public String xLatitude;
    public String xLongitude;
    public Context mContext;
    private SearchView searchView = null;
    public SearchView.OnQueryTextListener queryTextListener;
    public ArrayList<Categorias> listCategorias;
    public ArrayList<Convenios_categorias> listConvenios;
    ExpandableListView expandableListView;
    RequestQueue requestQueue;
    ProgressBar progressBar;
    String Mes_Corrente;

    public ConveniosFragment() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConveniosFragment.
     */

    public static ConveniosFragment newInstance(String param1, String param2) {
        ConveniosFragment fragment = new ConveniosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_convenios, container, false);
        Requisicao_volley(v);

        requireActivity().addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
                inflater.inflate(R.menu.search_menu, menu);
                final MenuItem searchItem = menu.findItem(R.id.busca_convenio);
                final SearchManager searchManager = (SearchManager) requireActivity().getSystemService(Context.SEARCH_SERVICE);

                if (searchItem != null) {
                    searchView = (SearchView) searchItem.getActionView();
                }
                if (searchView != null) {
                    searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().getComponentName()));

                    queryTextListener = new SearchView.OnQueryTextListener() {
                        @Override
                        public boolean onQueryTextChange(String newText) {
                            //Log.i("onQueryTextChange", newText);
                            variavelAdapter.FilterData(newText);
                            if (newText.equals("")){
                                colapseALL();
                            }else{
                                expandALL();
                            }
                            return false;
                        }
                        @Override
                        public boolean onQueryTextSubmit(String query) {
                            //Log.i("onQueryTextSubmit", query);
                            variavelAdapter.FilterData(query);
                            if (query.equals("")){
                                colapseALL();
                            }else{
                                expandALL();
                            }
                            return false;
                        }
                    };
                    searchView.setOnQueryTextListener(queryTextListener);
                }

            }
            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                // Handle option Menu Here
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
    public boolean onClose(){
        variavelAdapter.FilterData("");
        expandALL();
        return false;
    }
    private void expandALL(){
        int count = variavelAdapter.getGroupCount();
        for(int i = 0; i < count; i++){
            expandableListView.expandGroup(i);
        }
    }
    private void colapseALL(){
        int count = variavelAdapter.getGroupCount();
        for(int i = 0; i < count; i++){
            expandableListView.collapseGroup(i);
        }
    }
    public void Requisicao_volley(View v) {
        listCategorias = new ArrayList<>();
        listConvenios  = new ArrayList<>();
        xCodigo        = 0;
        xCategoria     = "";
        xNfantasia     = "";
        xEndereco      = "";
        xNumero        = "";
        xBairro        = "";
        xTelefone      = "";
        xCelular       = "";
        xLatitude      = "";
        xLongitude     = "";

        expandableListView = v.findViewById(R.id.lvExp);
        progressBar = v.findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        String urlm = getResources().getString(R.string.HOST) + "convenio_categorias_app.php";
        requestQueue = Volley.newRequestQueue(v.getContext());
        expandableListView.setOnChildClickListener((parent, v1, groupPosition, childPosition, id) -> {

            Object object1 = variavelAdapter.getChild(groupPosition, childPosition);
            Object object2 = variavelAdapter.getGroup(groupPosition);
            //Toast.makeText(mContext, "" + v.g etTag(childPosition) +" "+ childPosition + " " + id ,Toast.LENGTH_LONG).show();

            Intent intent = new Intent(mContext, Convenios_detalhes.class);

            intent.putExtra("nome_c",((Convenios_categorias) object1).getNOMEFANTASIA());
            intent.putExtra("endereco_c",((Convenios_categorias) object1).getENDERECO());
            intent.putExtra("numero_c",((Convenios_categorias) object1).getNUMERO());
            intent.putExtra("bairro_c",((Convenios_categorias) object1).getBAIRRO());
            intent.putExtra("telefone_c",((Convenios_categorias) object1).getTELEFONE());
            intent.putExtra("celular_c",((Convenios_categorias) object1).getCELULAR());
            intent.putExtra("categoria_c",((Categorias) object2).getNome());
            intent.putExtra("latitude_c",((Convenios_categorias) object1).getLATITUDE());
            intent.putExtra("longitude_c",((Convenios_categorias) object1).getLONGITUDE());

            startActivity(intent);

            return false;
        });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlm,
                response -> {
                    try {
                        JSONArray arr = new JSONArray(response);
                        xCodigo    = arr.getJSONObject(0).getInt("codigo");
                        xCategoria = arr.getJSONObject(0).getString("nome_categoria");
                        xNfantasia = arr.getJSONObject(0).getString("nomefantasia");
                        xEndereco  = arr.getJSONObject(0).getString("endereco");
                        xNumero    = arr.getJSONObject(0).getString("numero");
                        xEndereco  = arr.getJSONObject(0).getString("endereco");
                        xBairro    = arr.getJSONObject(0).getString("bairro");
                        xTelefone  = arr.getJSONObject(0).getString("telefone");
                        xCelular   = arr.getJSONObject(0).getString("cel");
                        xLatitude  = arr.getJSONObject(0).getString("latitude");
                        xLongitude = arr.getJSONObject(0).getString("longitude");
                        //adciona a primeira categoria
                        ArrayList<Convenios_categorias> auxList = new ArrayList<>();
                        Categorias categorias3 = new Categorias(xCategoria,auxList);
                        //listCategorias.add(categorias3);
                        for(int i=0;i<arr.length();i++){

                            if (xCategoria.equals(arr.getJSONObject(i).getString("nome_categoria"))){

                                //constroi a lista de uma categoria
                                xCodigo    = arr.getJSONObject(i).getInt("codigo");
                                xNfantasia = arr.getJSONObject(i).getString("nomefantasia");
                                xEndereco  = arr.getJSONObject(i).getString("endereco");
                                xNumero    = arr.getJSONObject(i).getString("numero");
                                xBairro    = arr.getJSONObject(i).getString("bairro");
                                xTelefone  = arr.getJSONObject(i).getString("telefone");
                                xCelular   = arr.getJSONObject(i).getString("cel");
                                xLatitude  = arr.getJSONObject(i).getString("latitude");
                                xLongitude = arr.getJSONObject(i).getString("longitude");
                                Convenios_categorias convenios_categorias1 = new Convenios_categorias(xNfantasia,xEndereco,xBairro,xTelefone,xCelular,xCodigo,xLatitude,xLongitude,xNumero);
                                auxList.add(convenios_categorias1);
                                //auxList.add(xEndereco);

                            }else{
                                //termina a categoria
                                Categorias categorias = new Categorias(xCategoria,auxList);
                                listCategorias.add(categorias);
                                auxList = new ArrayList<>();
                                xCodigo    = arr.getJSONObject(i).getInt("codigo");
                                xCategoria = arr.getJSONObject(i).getString("nome_categoria");
                                xNfantasia = arr.getJSONObject(i).getString("nomefantasia");
                                xEndereco  = arr.getJSONObject(i).getString("endereco");
                                xNumero    = arr.getJSONObject(i).getString("numero");
                                xBairro    = arr.getJSONObject(i).getString("bairro");
                                xTelefone  = arr.getJSONObject(i).getString("telefone");
                                xCelular   = arr.getJSONObject(i).getString("cel");
                                xLatitude  = arr.getJSONObject(i).getString("latitude");
                                xLongitude = arr.getJSONObject(i).getString("longitude");
                                //adiciona a proxima categoria
                                Convenios_categorias convenios_categorias2 = new Convenios_categorias(xNfantasia,xEndereco,xBairro,xTelefone,xCelular,xCodigo,xLatitude,xLongitude,xNumero);

                                auxList.add(convenios_categorias2);
                                if ((i + 1) == arr.length()){
                                    Categorias categorias2 = new Categorias(xCategoria,auxList);
                                    listCategorias.add(categorias2);
                                }

                            }
                        }
                        variavelAdapter = new ExpandableAdapter(getActivity(),listCategorias);
                        expandableListView.setAdapter(variavelAdapter);
                        //expandALL();
                        progressBar.setVisibility(View.GONE);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    // Display the first 500 characters of the response string.
                    //textView.setText("Response is: "+ response.substring(0,500));
                }, error -> {
                    //textView.setText("That didn't work!");
                    progressBar.setVisibility(View.GONE);
                });
        requestQueue.add(stringRequest);
    }
}
