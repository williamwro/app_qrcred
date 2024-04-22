package br.com.qrcred;

import static android.app.PendingIntent.getActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.text.HtmlCompat;

import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.Objects;


public class Convenios_detalhes extends AppCompatActivity {
    String nome_c;
    String endereco_c;
    String numero_c;
    String bairro_c;
    String telefone_c;
    String celular_c;
    String categoria_c;
    String latitude_c;
    String longitude_c;

    ImageButton imageButtonmapa, calltel, callcel;

    private FusedLocationProviderClient mfusedLocationProviderClient;

    private int MY_PERMISSIONS_REQUEST_READ_CONTACTS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.convenios_detalhes);

        imageButtonmapa = findViewById(R.id.imageButtonLocation);
        calltel = findViewById(R.id.CallTel);
        callcel = findViewById(R.id.CallCel);

        Intent intent = getIntent( );


        nome_c = intent.getStringExtra("nome_c");
        endereco_c = intent.getStringExtra("endereco_c");
        numero_c = intent.getStringExtra("numero_c");
        bairro_c = intent.getStringExtra("bairro_c");
        telefone_c = intent.getStringExtra("telefone_c");
        celular_c = intent.getStringExtra("celular_c");
        categoria_c = intent.getStringExtra("categoria_c");
        latitude_c = intent.getStringExtra("latitude_c");
        longitude_c = intent.getStringExtra("longitude_c");

        TextView tvNomeConvenio = findViewById(R.id.NOME_CONVENIO);
        TextView tvEnderecoConvenio = findViewById(R.id.ENDERECO_CONVENIO);
        TextView tvBairroConvenio = findViewById(R.id.BAIRRO_CONVENIO);
        TextView tvTelefoneConvenio = findViewById(R.id.TELEFONE_CONVENIO);
        TextView tvCelularConvenio = findViewById(R.id.CELULAR_CONVENIO);
        TextView tvCategoriaConvenio = findViewById(R.id.CATEGORIA_CONVENIO);

        endereco_c  = endereco_c + ", " + numero_c;

        tvNomeConvenio.setText(HtmlCompat.fromHtml(Objects.requireNonNull(nome_c), HtmlCompat.FROM_HTML_MODE_LEGACY));
        tvEnderecoConvenio.setText(HtmlCompat.fromHtml(Objects.requireNonNull(endereco_c), HtmlCompat.FROM_HTML_MODE_LEGACY));
        tvBairroConvenio.setText(HtmlCompat.fromHtml(Objects.requireNonNull(bairro_c), HtmlCompat.FROM_HTML_MODE_LEGACY));
        tvTelefoneConvenio.setText(telefone_c);
        tvCelularConvenio.setText(celular_c);
        tvCategoriaConvenio.setText(HtmlCompat.fromHtml(Objects.requireNonNull(categoria_c), HtmlCompat.FROM_HTML_MODE_LEGACY));

        final androidx.fragment.app
                .FragmentManager mFragmentManager
                = getSupportFragmentManager();
        final androidx.fragment.app
                .FragmentTransaction mFragmentTransaction
                = mFragmentManager.beginTransaction();
        final MapFragment mFragment
                = new MapFragment();

        imageButtonmapa.setOnClickListener(v -> {


            Intent intent1 = new Intent(getBaseContext(), MapsActivity.class);
            intent1.putExtra("nome_c", nome_c);
            intent1.putExtra("categoria_c", categoria_c);
            intent1.putExtra("latitude_c", latitude_c);
            intent1.putExtra("longitude_c", longitude_c);
            startActivity(intent1);
          /*  Bundle mBundle = new Bundle();
            mBundle.putString("latitude_c", latitude_c);
            mBundle.putString("longitude_c", longitude_c);
            mFragment.setArguments(mBundle);
            mFragmentTransaction.add(R.id.frame_layout, mFragment).commit();*/


        });
        calltel.setOnClickListener(v -> {
            String numero0 = telefone_c;
            if(numero0.length() > 0 ) {
                numero0 = telefone_c.substring(4, 13);
                Uri uri = Uri.parse("tel:" + numero0);
                Intent intent12 = new Intent(Intent.ACTION_CALL, uri);
                if (ActivityCompat.checkSelfPermission(Convenios_detalhes.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Convenios_detalhes.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                startActivity(intent12);
            }
        });

        callcel.setOnClickListener(v -> {
            String numero = celular_c;
            if(numero.length() > 0 ){

                numero = celular_c.substring(4, 13);

                if (numero.length() == 9) {
                    numero = "9" + numero;
                }
                Uri uri = Uri.parse("tel:" + numero);
                Intent intent13 = new Intent(Intent.ACTION_CALL, uri);
                if (ActivityCompat.checkSelfPermission(Convenios_detalhes.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Convenios_detalhes.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                startActivity(intent13);
            }

        });
        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        Localiza_cliente();
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
        //toolbar.setBackground(getResources().getDrawable(R.color.colorAccent));
        //toolbar.setTitleTextColor(0xFFFFFFFF);
        toolbar.setTitle("QRCRED");
        toolbar.setSubtitle("Informação do convenio");


    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, ConveniosFragment.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem

                break;
            default:break;
        }
        return true;
    }*/
    private void Localiza_cliente() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Convenios_detalhes.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            return;
        }
        mfusedLocationProviderClient.getLastLocation( )
                .addOnSuccessListener(this, location -> {

                });
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}

