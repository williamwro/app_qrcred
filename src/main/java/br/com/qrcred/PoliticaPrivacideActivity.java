package br.com.qrcred;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class PoliticaPrivacideActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_politica_privacide);

        WebView webView = findViewById(R.id.wv_content);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/politica_privacidade.html");
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            ActionBar ab = getSupportActionBar();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_black_24dp, null);
            //drawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_IN));
            ab.setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        //toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_theme_light_onPrimary));
        getSupportActionBar( ).setTitle("Política de privacidade");
    }

    @Override
    public boolean onSupportNavigateUp() {
        if(isTaskRoot()){
            finish();
            return false;
        }else{
            Intent intent = new Intent(PoliticaPrivacideActivity.this, TelaMenuActivity.class);
            startActivity(intent);
            return true;
        }
    }
}
