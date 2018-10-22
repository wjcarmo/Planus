package seguranca.mas.planus.Activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import seguranca.mas.planus.Configuracao.ConfiguracaoFireBase;
import seguranca.mas.planus.help.Preferencias;
import seguranca.mas.planus.model.Base64Criptografia;
import seguranca.mas.planus.model.R;
import seguranca.mas.planus.model.Usuarios;
import seguranca.mas.planus.planusmasseguranca.ActivityAvisoSemInternet;

public class TelaInicialActivity extends AppCompatActivity {

    private ImageView iconeInicial;
    private Button btnEntrar;
    private TextView txtCadastreAqui;
    private EditText txtSenha;
    private EditText txtUsuario;
    private Usuarios Usu;
    private FirebaseAuth firebaseAuth;
    private  DatabaseReference banco;
    private ValueEventListener valueEventListenerUsuarios;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        //Verifica se o usuario ja esta logado.
        VerificaUsuarioLogado();

        txtUsuario = (EditText) findViewById(R.id.txtEmail);
        txtSenha   = (EditText) findViewById(R.id.txtCadSenha);

        iconeInicial = (ImageView) findViewById(R.id.imgTelaInicial);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Usu = new Usuarios();
                Usu.setEmail(txtUsuario.getText().toString());
                Usu.setSenha(txtSenha.getText().toString());

                VerificarInternet();

            }
        });

        txtCadastreAqui = (TextView) findViewById(R.id.txtCadastrarAqui);
        txtCadastreAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = cm.getActiveNetworkInfo();

                if(networkInfo != null && networkInfo.isConnectedOrConnecting()){

                    startActivity( new Intent(TelaInicialActivity.this, CadastroDeUsuariosActivity.class));
                }else{
                    startActivity( new Intent(TelaInicialActivity.this, ActivityAvisoSemInternet.class));
                }

            }
        });

    }

    private void ValidarLogin(){

        firebaseAuth = ConfiguracaoFireBase.getFirebaseAuth();

        firebaseAuth.signInWithEmailAndPassword(
                Usu.getEmail(),
                Usu.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    email = Base64Criptografia.SetDadosParaCriptografar( Usu.getEmail());

                    banco = ConfiguracaoFireBase.getReferencia()
                            .child("usuarios")
                            .child(email);
                    valueEventListenerUsuarios = new ValueEventListener() {

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Usuarios usuarioRecuperado = dataSnapshot.getValue(Usuarios.class);

                            Preferencias preferencias = new Preferencias(TelaInicialActivity.this);

                            preferencias.SalvarDadosEmailLocal(Usu.getEmail());
                            preferencias.SalvarDadosIdDoUsuarioLocal(email);
                            preferencias.SalvarDadosNomeLocal(usuarioRecuperado.getNome());
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    };
                    banco.addListenerForSingleValueEvent(valueEventListenerUsuarios);

                    AbrirMenuPrincipal();
                }else{
                    Toast.makeText(TelaInicialActivity.this,"O Usuário deve estar cadastrado",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private void VerificaUsuarioLogado() {
        super.onStart();
        firebaseAuth = ConfiguracaoFireBase.getFirebaseAuth();
        if(firebaseAuth.getCurrentUser() != null)
        {
            AbrirMenuPrincipal();
        }
    }

    private void AbrirMenuPrincipal() {
        Intent intent = new Intent(TelaInicialActivity.this, MenuCentralActivity.class);
        startActivity(intent);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //Toast.makeText(getApplicationContext(),"onRestart - Metodo Chamado Tela 2 ",Toast.LENGTH_LONG).show();
    }

    public void PesquisarUsuario() {
        Usuarios usu = new Usuarios();

        banco =  ConfiguracaoFireBase.getReferencia();

        HashMap<String,String> usuarios;


       // banco.updateChildren(usuarios,usu);

    }

public void VerificarInternet(){

    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = cm.getActiveNetworkInfo();

    if(networkInfo != null && networkInfo.isConnectedOrConnecting()){
        ValidarLogin();
    }else{
        startActivity( new Intent(TelaInicialActivity.this, ActivityAvisoSemInternet.class));
    }

}
}
