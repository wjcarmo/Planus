package seguranca.mas.planus.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Random;

import seguranca.mas.planus.Configuracao.ConfiguracaoFireBase;
import seguranca.mas.planus.help.Permissao;
import seguranca.mas.planus.help.Preferencias;
import seguranca.mas.planus.model.R;
import seguranca.mas.planus.model.Pessoas;

public class MensagemActivity extends AppCompatActivity {

    private ImageView iconeInicial;
    private Button btnCadastrar;
    private EditText txtNome;
    private EditText txtArea;
    private EditText txtIdPais;
    private EditText txtTelefone;
    private String chave;

    private DatabaseReference contatosRefencia = ConfiguracaoFireBase.getReferencia().child("Contatos");

    private String[] permissaoNecessaria = new String[]{
            Manifest.permission.SEND_SMS
          };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensagem);

        Permissao.validaPermissoes(1,this,permissaoNecessaria);

        final Pessoas pessoa = new Pessoas();

        txtNome     = (EditText) findViewById(R.id.txtCadEmail);
        txtIdPais   = (EditText) findViewById(R.id.txtidPais);
        txtArea     = (EditText) findViewById(R.id.txtIdArea);
        txtTelefone = (EditText) findViewById(R.id.txtIdTelefone);

        SimpleMaskFormatter maskPais     = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter maskArea     = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter maskTelefone = new SimpleMaskFormatter("NNNNN-NNNN");

        MaskTextWatcher mskPais = new MaskTextWatcher(txtIdPais, maskPais);
        MaskTextWatcher mskArea = new MaskTextWatcher(txtArea, maskArea);
        MaskTextWatcher mskTelefone = new MaskTextWatcher(txtTelefone, maskTelefone);

        txtIdPais.addTextChangedListener(mskPais);
        txtArea.addTextChangedListener(mskArea);
        txtTelefone.addTextChangedListener(mskTelefone);


        btnCadastrar = (Button) findViewById(R.id.btnCadastraAqui);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!txtNome.equals(""))
                {
                    String token = String.valueOf(gerarToken());
                    String sms = "Plunus Segurança - Código de Validação: "+token;

                    //SALVANDO OS DADOS LOCAL
                    String area     = txtArea.getText().toString();
                    String pais     = txtIdPais.getText().toString();
                    String telefone = txtTelefone.getText().toString();
                    String nome     = txtNome.getText().toString();

                    pais = pais.replace("+","");
                    telefone = telefone.replace("-","");
                    nome = nome.replace("","".trim());

                    chave = token + pais + area + telefone;

                    String chaveSemFormatacao = chave.replace("+","");
                    chaveSemFormatacao = chaveSemFormatacao.replace("-","");

                    Preferencias preferencias = new Preferencias(MensagemActivity.this);

                    preferencias.salvarPreferenciaUsuarios(token,nome,telefone);

                    HashMap<String,String> usuarios = preferencias.getPreferencias();

                    String  telefoneSms = telefone.replace("-","");

                    telefoneSms = pais+area+telefoneSms;

                    //telefoneSms = "5554";

                    Log.i("TELEFONE", "TEL: "+telefoneSms);

                    boolean envio = enviarSms(telefoneSms,sms);

                    if(envio)
                    {
                        Intent intent = new Intent(MensagemActivity.this, ValidadorActivity.class);
                        startActivity( intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(MensagemActivity.this,"Problema no envio do SMS",Toast.LENGTH_SHORT).show();

                    }

                   // Log.i("TELEFONE", "TEL: "+telefoneSms+sms);

                    pessoa.setId(token);
                    pessoa.setNome(nome);
                    pessoa.setIdArea(area);
                    pessoa.setIdPais(pais);
                    pessoa.setIdTelefone(telefone);

                    contatosRefencia.child(chave).setValue(pessoa);

                    Toast.makeText(MensagemActivity.this,"Dados Gravados com Sucesso...",Toast.LENGTH_SHORT).show();

                    txtNome.setText("");
                    txtIdPais.setText("");
                    txtArea.setText("");
                    txtTelefone.setText("");



                }
                else
                {
                    Toast.makeText(MensagemActivity.this,"Favor informar todos os campos..",Toast.LENGTH_SHORT).show();
                    onRestart();

                }



            }
        });


    }

    private int gerarToken() {

        Random numero = new Random();
        int numeroToken = numero.nextInt(9999-1000) +1000;
        //String token = String.valueOf(numeroToken);
        return numeroToken;
    }

    private boolean enviarSms(String telefone, String mensagem)
    {
        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone,null,mensagem,null,null);
            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public void onRequestPermissionsResult(int reqCod, String[] permissoes, int[] grandResults)
    {
        super.onRequestPermissionsResult(reqCod,permissoes,grandResults);

      for (int resultados : grandResults)
      {
          if(resultados == PackageManager.PERMISSION_DENIED)
          {
              AlertaValidacaoDePermissoes();
          }
      }
    }

    public void AlertaValidacaoDePermissoes()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Validação de Permissões");
        builder.setMessage("Para usar este aplicativo, é necessário permitir os acessos solicitados");

        builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
