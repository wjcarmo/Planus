package seguranca.mas.planus.Activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import seguranca.mas.planus.Adapter.TabAdapter;
import seguranca.mas.planus.help.SlidingTabLayout;
import seguranca.mas.planus.model.R;


public class   MainActivity extends AppCompatActivity {

    private Button btnRoubar;
    private Button btnSim;
    private Button btnNao;
    private Boolean resposta = true;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar_principal);
        toolbar.setTitle("Segurança");
       // toolbar.setTitleTextColor(6666666);
        setSupportActionBar(toolbar);

        slidingTabLayout = (SlidingTabLayout)  findViewById(R.id.stl_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.corBalaoAmarelo));

        viewPager = (ViewPager) findViewById(R.id.vp_Pagina);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());

        viewPager.setAdapter(tabAdapter);
        slidingTabLayout.setViewPager(viewPager);



/*
        btnRoubar = (Button) findViewById(R.id.btnSimIdentificar);

        btnRoubar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.app.FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                if(resposta)
                {
                    FragmentoInformarRoubo fragmentoInformarRoubo = new FragmentoInformarRoubo();

                    fragmentTransaction.add(R.id.ContainerFragmento , fragmentoInformarRoubo);
                    fragmentTransaction.commit();
                    btnRoubar.setText("Nâo");
                    resposta = false;

                }else{
                    FragmentUm fragmentoNaoInformar = new FragmentUm();

                    fragmentTransaction.add(R.id.ContainerFragmento , fragmentoNaoInformar);
                    fragmentTransaction.commit();
                    btnRoubar.setText("Sim");
                    resposta = true;


                }
            }
        });

        */


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal_lateral, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Sair) {




            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
