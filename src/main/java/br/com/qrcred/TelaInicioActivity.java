package br.com.qrcred;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ProgressBar;

public class TelaInicioActivity extends AppCompatActivity {
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fabric.with(this, new Crashlytics());

       //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_telainicio);

        progressBar = findViewById(R.id.progressBar);



        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), TelaMenuActivity.class);
                progressBar.setVisibility(View.INVISIBLE);
                startActivity(intent);

            }
        },3000);

    }

}
