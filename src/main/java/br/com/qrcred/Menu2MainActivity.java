package br.com.qrcred;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import br.com.qrcred.convenio.ActivityLoginConvenio;
import br.com.qrcred.convenio.CadConveniosActivity;

public class Menu2MainActivity extends AppCompatActivity {
    Button btnSouCadastrado;
    Button btnNaoSouCadastrado;
    Button textViewManualConvenio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu2_main);

        btnSouCadastrado = findViewById(R.id.buttonSouCadastrado);
        btnNaoSouCadastrado = findViewById(R.id.buttonNaoSouCadastrado);
        textViewManualConvenio = findViewById(R.id.buttonManual);

        Toolbar toolbar = findViewById(R.id.toolbarMenu2);
        setSupportActionBar(toolbar);
        if (getSupportActionBar( ) != null) {
            getSupportActionBar( ).setDisplayHomeAsUpEnabled(true);
            ActionBar ab = getSupportActionBar();
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_arrow_back_black_24dp, null);
            ab.setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_theme_light_onPrimary));
        getSupportActionBar( ).setTitle("Menu estabelecimento");
        btnSouCadastrado.setOnClickListener(view -> {
           /* AlertDialog.Builder alertDlg = new AlertDialog.Builder(this);
            alertDlg.setMessage("Os dados coletados neste App, como numero de cartao, numero de celular, cpf e nome são de uso exclusivo do App. Voce permite coletar os seus dados somente para o uso do App?");
            alertDlg.setCancelable(false);
            alertDlg.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {*/

                    Intent intent = new Intent(Menu2MainActivity.this, ActivityLoginConvenio.class);
                    startActivity(intent);

      /*          }
            });
            alertDlg.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(Menu2MainActivity.this, "É necessario aceitar os dados para utilizar o App.", Toast.LENGTH_LONG).show();
                }
            });
            alertDlg.create().show();*/
        });
        btnNaoSouCadastrado.setOnClickListener(view -> {
            Intent intent2 = new Intent(Menu2MainActivity.this, CadConveniosActivity.class);
            startActivity(intent2);
        });
        textViewManualConvenio.setOnClickListener(v -> {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://youtu.be/JtI0TiB7ATU"));
            Menu2MainActivity.this.startActivity(webIntent);
        });
    }
    @Override
    public boolean onSupportNavigateUp() {
        Intent intent2 = new Intent(Menu2MainActivity.this, TelaMenuActivity.class);
        startActivity(intent2);
        return true;
    }
}
