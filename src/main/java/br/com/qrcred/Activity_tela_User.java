package br.com.qrcred;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Activity_tela_User extends AppCompatActivity {
    public static RoomDB myAppDatabase;
    public int id_cartao;
    public String n_cartao = "";
    public String rotulo_associado = "";

    public String Mes_Corrente = "";
    public List<Usuario_local> usuario_locals;
    private MyAdapterUser.RecycleViewClickListner listnerx;
    public FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_user);

        floatingActionButton = findViewById(R.id.floatingActionButton);
        // toolbar
        Toolbar toolbar = findViewById(R.id.toolbar_menu_user);
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
        getSupportActionBar().setTitle("Login usuário");

        myAppDatabase = RoomDB.getInstance(getApplicationContext());
        usuario_locals = myAppDatabase.usuario_localDao().getAll();
        for (Usuario_local usr : usuario_locals) {
            id_cartao = usr.getID();
            n_cartao = usr.getCartao();
            rotulo_associado = usr.getNomeassociado();
            Mes_Corrente = usr.getMescorrente();
        }

        if (n_cartao.equals("")) {
            Intent intent = new Intent(Activity_tela_User.this, MainActivity.class);
            intent.putExtra("cartao", n_cartao);
            intent.putExtra("id_cartao", id_cartao);
            intent.putExtra("rotulo_associado", rotulo_associado);
            intent.putExtra("Mes_Corrente", Mes_Corrente);
            intent.putExtra("novo", "true");
            startActivity(intent);
        } else {

            setOnClickListner();
            RecyclerView recyclerView = findViewById(R.id.RecyclerViewUser);
            MyAdapterUser adapter = new MyAdapterUser(Activity_tela_User.this,usuario_locals,listnerx);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(Activity_tela_User.this));
            recyclerView.setClickable(true);
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentx;
                intentx = new Intent(Activity_tela_User.this, MainActivity.class);
                intentx.putExtra("cartao", "");
                intentx.putExtra("novo", "true");
                startActivity(intentx);
            }
        });

    }
    private void setOnClickListner() {
        listnerx = (v, position) -> {
            Intent intentx;
            intentx = new Intent(Activity_tela_User.this, MainActivity.class);
            intentx.putExtra("cartao", usuario_locals.get(position).getCartao());
            intentx.putExtra("user", usuario_locals.get(position).getNomeassociado());
            intentx.putExtra("novo", "false");
            startActivity(intentx);
        };
    }
    @Override
    public boolean onSupportNavigateUp() {
        if(isTaskRoot()){
            finish();
            return false;
        }else{
            Intent intent = new Intent(Activity_tela_User.this, TelaMenuActivity.class);
            startActivity(intent);
            return true;
        }
    }
}
