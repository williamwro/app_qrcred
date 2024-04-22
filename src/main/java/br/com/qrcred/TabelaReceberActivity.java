package br.com.qrcred;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TabelaReceberActivity extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tabela_receber, container, false);
        return v;
    }
  /*      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabela_receber);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar( ) != null) {
            getSupportActionBar( ).setDisplayHomeAsUpEnabled(true);
            ActionBar ab = getSupportActionBar();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_black_24dp, null);
            ab.setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        getSupportActionBar( ).setTitle("Makecard");
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }*/
}
