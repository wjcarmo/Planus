package seguranca.mas.planus.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import seguranca.mas.planus.model.R;

public class ChamadaActivity extends AppCompatActivity {

    private static int TempoExecutado = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chamada);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent home = new Intent(ChamadaActivity.this,TelaInicialActivity.class);
                startActivity(home);
                finish();

            }
        },TempoExecutado);


    }
}
