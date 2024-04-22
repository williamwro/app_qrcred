package br.com.qrcred.convenio;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.link.DefaultLinkHandler;

import java.util.HashMap;
import java.util.Map;

import br.com.qrcred.R;

public class MainActivityContrato extends AppCompatActivity {
    //implements LinkHandler
    PDFView pdfcontrato;
    String cod_convenio;
    String razaosocial;
    String cnpj;
    String cpf;
    String cep;
    String endereco;
    String numero;
    String bairro;
    String cidade;
    String estado;
    CheckBox aSwitch;
    String aceito_termo;
    private Map<String, String> params;
    RequestQueue requestQueue;
    private static final String TAG = DefaultLinkHandler.class.getSimpleName();
    public String url = "http://qrcred.makecard.com.br/Adm/pages/convenio/contrato_estabelecimento_app.php?";
    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_contrato);
        //pdfcontrato = findViewById(R.id.contratopdfView);
        aSwitch =findViewById(R.id.swAceito);
        final Intent intent = getIntent( );
        //recebendo valores ----------------------------------------------------------
        cod_convenio  = intent.getStringExtra("codconvenio");
        razaosocial = intent.getStringExtra("razaosocial");
        cnpj = intent.getStringExtra("cnpj");
        cpf = intent.getStringExtra("cpf");
        cep = intent.getStringExtra("cep");
        endereco = intent.getStringExtra("endereco");
        numero = intent.getStringExtra("numero");
        bairro = intent.getStringExtra("bairro");
        cidade = intent.getStringExtra("cidade");
        estado = intent.getStringExtra("estado");
        aceito_termo = intent.getStringExtra("aceito_termo");
        //url = "http://qrcred.makecard.com.br/Adm/pages/convenio/contratos/"+cod_convenio+"/"+razaosocial+".pdf";
        //pdfcontrato.fromSource(url).load();
        //handleUri(url);

        WebView webView = findViewById(R.id.wv_contrato);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.loadUrl("file:///android_asset/contrato.html");
        webView.addJavascriptInterface(this,"Contrato");
        webView.loadUrl(url+"razaosocial="+razaosocial+"&cnpj="+cnpj+"&cpf="+cpf+"&cep="+cep+"&endereco="+endereco+"&numero="+numero+"&bairro="+bairro+"&cidade="+cidade+"&estado="+estado);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient());

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
        //toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_theme_light_onPrimary));
        getSupportActionBar( ).setTitle("Contrato");
        if(aceito_termo.equals("true")){
            aceito_termo = "true";
            aSwitch.setChecked(true);
            aSwitch.setEnabled(false);
        }else{
            aceito_termo = "false";
            aSwitch.setChecked(false);
            //aSwitch.setEnabled(true);
        }
        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked) {
                aceito_termo = "true";
            }else{
                aceito_termo = "false";
            }
            grava();
        });
    }
    public void grava() {
        String urlm = getResources( ).getString(R.string.HOST) + "atualiza_convenio_termo.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                urlm,
                response -> {
                    //Log.i("Script", "SUCESS: "+response);
                    //Toast.makeText(Meus_dados.this, "Atualizado com sucesso!",Toast.LENGTH_LONG).show();
                    //onSupportNavigateUp();
                }, error -> {
            Toast.makeText(MainActivityContrato.this, "Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
        }) {
            @Override
            public Map<String, String> getParams() {
                params = new HashMap<>();
                params.put("codigo", cod_convenio);
                params.put("estado_termo", aceito_termo);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
    /*@Override
    public void handleLinkEvent(LinkTapEvent event) {
        String uri = event.getLink().getUri();
        Integer page = event.getLink().getDestPageIdx();
        if (uri != null && !uri.isEmpty()) {
            handleUri(uri);
        } else if (page != null) {
            handlePage(page);
        }
    }
    private void handleUri(String uri) {
        Uri parsedUri = Uri.parse(uri);
        Intent intent = new Intent(Intent.ACTION_VIEW, parsedUri);
        Context context = pdfcontrato.getContext();
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Log.w(TAG, "No activity found for URI: " + uri);
        }
    }
    private void handlePage(int page) {
        pdfcontrato.jumpTo(page);
    }*/
}
