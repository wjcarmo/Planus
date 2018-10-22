package seguranca.mas.planus.planusmasseguranca;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import seguranca.mas.planus.model.R;

public class ActivityAvisoSemInternet extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aviso_sem_internet);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Informações da Internet");
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.corFundoDeBotoes));
        toolbar.setNavigationIcon(R.drawable.iconeplanus);
        setSupportActionBar(toolbar);

    }
}
