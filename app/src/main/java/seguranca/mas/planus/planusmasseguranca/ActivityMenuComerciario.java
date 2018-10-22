package seguranca.mas.planus.planusmasseguranca;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import seguranca.mas.planus.Activity.ActivityConversas;
import seguranca.mas.planus.Activity.MenuCentralActivity;
import seguranca.mas.planus.Adapter.TabAdapter;
import seguranca.mas.planus.help.Preferencias;
import seguranca.mas.planus.help.SlidingTabLayout;
import seguranca.mas.planus.model.Base64Criptografia;
import seguranca.mas.planus.model.Contatos;
import seguranca.mas.planus.model.R;


public class ActivityMenuComerciario extends AppCompatActivity {

    private String TituloDaTela;
    private Toolbar toolbar;
    private ImageView btnOcorrencia;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private TextView txtTituloDaTela;
    private String tituloDaTela;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_comerciario);

        toolbar = (Toolbar) findViewById(R.id.toolbar_principal);

        toolbar.setTitle("Planus - Menu Comerciário");
        //TituloDaTela   = "Planus - Menu Comerciário";

        toolbar.setNavigationIcon(R.drawable.iconeplanus);
        setSupportActionBar(toolbar);

        btnOcorrencia   = (ImageView) findViewById(R.id.BtnImgOcorrencia);
        txtTituloDaTela = (TextView)  findViewById(R.id.txtOcorrencia);

            btnOcorrencia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ActivityMenuComerciario.this, MenuPrincipalLateral.class);
                    startActivity(intent);

                }
            });


    }

}
