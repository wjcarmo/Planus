package seguranca.mas.planus.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.HashMap;

import seguranca.mas.planus.help.Preferencias;
import seguranca.mas.planus.model.R;

public class ValidadorActivity extends AppCompatActivity {

    private EditText txtValidar;
    private Button btnValidar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validador);

        txtValidar = (EditText) findViewById(R.id.txtValidar);

        btnValidar = (Button) findViewById(R.id.btnValidar);

        SimpleMaskFormatter maskToken = new SimpleMaskFormatter("NNNN");
        MaskTextWatcher mskToken = new MaskTextWatcher(txtValidar, maskToken);

        txtValidar.addTextChangedListener(mskToken);

        btnValidar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //recuperar dados do usuario
                Preferencias preferencias = new Preferencias(ValidadorActivity.this);
                HashMap<String,String> usuarios = preferencias.getPreferencias();

                String tokenGerado = usuarios.get("token");
                String tokenDigitado = txtValidar.getText().toString();

                if(tokenGerado.equals(tokenDigitado))
                {
                    Toast.makeText(ValidadorActivity.this,"Token validado com sucesso.",Toast.LENGTH_SHORT).show();
                    startActivity( new Intent(ValidadorActivity.this, PainelDeMensagensActivity.class));
                }
                else
                {
                    Toast.makeText(ValidadorActivity.this,"Token Inválido.",Toast.LENGTH_SHORT).show();
                }





            }
        });



    }
}
