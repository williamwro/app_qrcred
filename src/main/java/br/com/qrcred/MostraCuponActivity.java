package br.com.qrcred;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.navigation.Navigation;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class MostraCuponActivity extends AppCompatActivity {
    String uri_cupon;
    PhotoViewAttacher mAttacher;
    Toolbar mtoolbar;
    ImageView imgCupom;
    TextView razaosocial;
    TextView nomefantasia;
    TextView data;
    TextView hora;
    TextView valor;
    private static final int SOLICITAR_PERMISSAO = 1;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent( );

        setContentView(R.layout.activity_mostra_cupon);
        mtoolbar = findViewById(R.id.aToolbar);
        razaosocial = findViewById(R.id.tvRazaoSocial_comprovante);
        nomefantasia = findViewById(R.id.tvNomeFantasia_comprovante);
        data = findViewById(R.id.tvData_comprovante);
        hora = findViewById(R.id.tvHora_comprovante);
        valor = findViewById(R.id.tvValor_comprovante);

        //mtoolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mtoolbar);
        //setTitle("Comprovante digital");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Comprovante digital");
        //getSupportActionBar().setIcon(R.drawable.ic_arrow_back_black_24dp);


        if (getSupportActionBar( ) != null) {
            getSupportActionBar( ).setDisplayHomeAsUpEnabled(true);
            getSupportActionBar( ).setDisplayShowHomeEnabled(true);
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();
        imgCupom = findViewById(R.id.imVMostraCupom);
        uri_cupon = intent.getStringExtra("uri_cupon");
        razaosocial.setText(intent.getStringExtra("razaosocial"));
        nomefantasia.setText(intent.getStringExtra("nomefantasia"));
        data.setText(intent.getStringExtra("data"));
        hora.setText(intent.getStringExtra("hora"));
        String valorx = "R$ "+intent.getStringExtra("valor");
        valor.setText(valorx);

        //imgCupom.animate().rotation(90).start();
        if (!uri_cupon.equals("")){
            Glide.with(this).load(uri_cupon).apply(options).into(imgCupom);
            mAttacher = new PhotoViewAttacher(imgCupom);
        }
        //shakeItBaby();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_comprovante,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            //onBackPressed();
        }else{
            checarPermissao();
        }
        //switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            //case android.R.id.home:
          //      onBackPressed();
           //     return true;
           //case R.id.compartilhar:
                //checarPermissao();
           //     break;
        //}
        return super.onOptionsItemSelected(item);
    }

    //---------------------- COMPARTILHA IMAGEM --------------------------
    private void compartilhar() {
        if (imgCupom.getDrawable() != null) {
            BitmapDrawable drawable = (BitmapDrawable) imgCupom.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

            // Salva o bitmap no diretório de arquivos compartilhados
            File imageFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Comprovante digital.jpg");
            try (FileOutputStream fos = new FileOutputStream(imageFile)) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            // Obtém a URI para o arquivo usando FileProvider
            Uri uri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", imageFile);

            // Cria a intent de compartilhamento
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            startActivity(Intent.createChooser(intent, "Compartilhar comprovante"));
        } else {
            Toast.makeText(getBaseContext(), "Não possui imagem ainda para compartilhar", Toast.LENGTH_LONG).show();
        }
    }
    private void checarPermissao(){
            int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionCheck != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, SOLICITAR_PERMISSAO);
            }else{
                compartilhar();
            }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }



}


