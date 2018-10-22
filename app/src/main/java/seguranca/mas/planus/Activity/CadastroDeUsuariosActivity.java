package seguranca.mas.planus.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import seguranca.mas.planus.Configuracao.ConfiguracaoFireBase;
import seguranca.mas.planus.help.Preferencias;
import seguranca.mas.planus.model.Base64Criptografia;
import seguranca.mas.planus.model.*;
import seguranca.mas.planus.model.Usuarios;

public class CadastroDeUsuariosActivity extends AppCompatActivity {

    EditText RecebeNome;
    EditText RecebeSenha;
    EditText RecebeEmail;
    Button btnCadastrar;
    private DatabaseReference DataUsuarios;
    private FirebaseAuth firebaseAuth;
    Usuarios usu;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_de_usuarios);

        RecebeNome     = (EditText) findViewById(R.id.txtCadNome);
        RecebeEmail    = (EditText) findViewById(R.id.txtCadEmail);
        RecebeSenha    = (EditText) findViewById(R.id.txtCadSenha);

        btnCadastrar   = (Button) findViewById(R.id.btnCadastraAqui);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                usu = new Usuarios();

                usu.setNome(RecebeNome.getText().toString());
                usu.setEmail(RecebeEmail.getText().toString());
                usu.setSenha(RecebeSenha.getText().toString());
                CadastrarUsuarios();

            }
        });

    }

        public void CadastrarUsuarios( )    {

        firebaseAuth = ConfiguracaoFireBase.getFirebaseAuth();

        firebaseAuth.createUserWithEmailAndPassword(usu.getEmail(),usu.getSenha()).addOnCompleteListener(CadastroDeUsuariosActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(CadastroDeUsuariosActivity.this,"Dados Gravados com Sucesso...",Toast.LENGTH_SHORT).show();

                    String email = Base64Criptografia.SetDadosParaCriptografar(usu.getEmail());

                    Preferencias preferencias = new Preferencias(CadastroDeUsuariosActivity.this);

                    preferencias.SalvarDadosNomeLocal(usu.getNome());
                    preferencias.SalvarDadosEmailLocal(usu.getEmail());
                    preferencias.SalvarDadosIdDoUsuarioLocal(email);

                    usu.setMatricula(email);
                    usu.setEmail(usu.getEmail());

                    usu.SalvarDados();


                    abrirLoginUsuario();

                }else{

                    String mensagem = "";

                    try {
                        throw  task.getException();

                    } catch (FirebaseAuthWeakPasswordException e) {
                        mensagem = "Digite uma senha mais forte com letra e números";

                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        mensagem = "Digite um e-mails válido";

                    } catch (FirebaseAuthUserCollisionException e) {
                        mensagem = "E-mail já esta em uso no APP";

                    } catch (Exception e) {
                        mensagem = "Erro ao efetuar o cadastro, tente novamente";
                        e.printStackTrace();
                    }

                    Toast.makeText(CadastroDeUsuariosActivity.this,"Erro: "+mensagem,Toast.LENGTH_SHORT).show();
                }
            }
        });

        }

        public void PesquisaUsuario()  {
            DataUsuarios.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }

        public void abrirLoginUsuario(){
             Intent intent = new Intent(CadastroDeUsuariosActivity.this, MenuCentralActivity.class);
             startActivity(intent);
             finish();
        }




}
