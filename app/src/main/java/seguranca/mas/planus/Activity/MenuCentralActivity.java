package seguranca.mas.planus.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import seguranca.mas.planus.Configuracao.ConfiguracaoFireBase;
import seguranca.mas.planus.help.Preferencias;
import seguranca.mas.planus.model.R;
import seguranca.mas.planus.planusmasseguranca.ActivityMenuComerciario;
import seguranca.mas.planus.planusmasseguranca.MenuPrincipalLateral;

public class MenuCentralActivity extends AppCompatActivity {

    private ImageView btnMensagem;
    private FirebaseAuth firebaseAuth;
    private ImageView btnSair;
    private ImageView btnRoubo;
    private ImageView btnComerciario;
    private TextView txtTituloDaTela;
    private TextView txtTituloDaTelaUtilidadePublica;
    private String TituloDaTela;
    private Toolbar toolbar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_central);

        toolbar = (Toolbar) findViewById(R.id.toolbar_principal);

        toolbar.setTitle("Planus - Menu Principal");

        toolbar.setNavigationIcon(R.drawable.iconeplanus);
        setSupportActionBar(toolbar);

        //btnSair         = (ImageView) findViewById(R.id.btnSair);
        btnRoubo        = (ImageView) findViewById(R.id.btnInformarRoubo);
        btnComerciario  = (ImageView)  findViewById(R.id.BtnComerciario);
        txtTituloDaTela = (TextView)  findViewById(R.id.txtComerciario);
        txtTituloDaTelaUtilidadePublica = (TextView)  findViewById(R.id.TxtUtilidadePublica);



        btnComerciario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Preferencias preferencias = new Preferencias(MenuCentralActivity.this);
                preferencias.SalvarTituloDaTela(txtTituloDaTela.getText().toString());

                startActivity( new Intent(MenuCentralActivity.this, ActivityMenuComerciario.class));
            }
        });


        btnRoubo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Preferencias preferencias = new Preferencias(MenuCentralActivity.this);
                preferencias.SalvarTituloDaTela(txtTituloDaTelaUtilidadePublica.getText().toString());

                startActivity( new Intent(MenuCentralActivity.this, MenuPrincipalLateral.class));
            }
        });


    }

    private void SairDoApp()
    {
        firebaseAuth = ConfiguracaoFireBase.getFirebaseAuth();
        firebaseAuth.signOut();

        Intent intent = new Intent(MenuCentralActivity.this, TelaInicialActivity.class);
        startActivity(intent);

    }


}
