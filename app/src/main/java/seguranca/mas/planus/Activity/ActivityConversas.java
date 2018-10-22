package seguranca.mas.planus.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import seguranca.mas.planus.Adapter.AdapterDeMensagem;
import seguranca.mas.planus.Configuracao.ConfiguracaoFireBase;
import seguranca.mas.planus.help.Preferencias;
import seguranca.mas.planus.model.Base64Criptografia;
import seguranca.mas.planus.model.Conversas;
import seguranca.mas.planus.model.Mensagem;
import seguranca.mas.planus.model.R;
import seguranca.mas.planus.planusmasseguranca.MenuPrincipalLateral;


public class ActivityConversas extends AppCompatActivity {

    private String nomeDoContatoDestinado;
    private String idDoContatoDestinado;
    private String idDoContatoDeRemetente;
    private String nomeDoContatoRemetente;
    private ImageButton btnEnviarConversa;
    private EditText txtMensagem;
    private DatabaseReference banco;
    private ListView listaDeContatos;
    private ArrayList<Mensagem> arrayDeMensagem;
    private ArrayAdapter<Mensagem> adapterDeMensagem;
    private ValueEventListener valueEventListenerMensagem;
    private String nomeUsuarioLogado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversas);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarConversas);
        btnEnviarConversa = (ImageButton) findViewById(R.id.btnEnviarConversa) ;
        txtMensagem = (EditText) findViewById(R.id.txtConversas)  ;
        listaDeContatos = (ListView) findViewById(R.id.lv_Conversas) ;


        //String email = Base64Criptografia.SetDadosParaCriptografar( Usu.getEmail());

        Preferencias preferencias = new Preferencias(ActivityConversas.this);
        idDoContatoDeRemetente = preferencias.RecuperarIdDoUsuarioLogado();
        nomeUsuarioLogado  = preferencias.RecuperarUsuarioLogado();

        Bundle extra =  getIntent().getExtras();

        if(extra != null){

            String emailDoContatoDestinado = extra.getString("email");
            nomeDoContatoDestinado = extra.getString("nome");
            idDoContatoDestinado = Base64Criptografia.SetDadosParaCriptografar( emailDoContatoDestinado);
        }

        toolbar.setTitle(nomeDoContatoDestinado);
        toolbar.setNavigationIcon(R.drawable.iconeplanus);
        setSupportActionBar(toolbar);

        arrayDeMensagem = new ArrayList<>();

        /*
        adapterDeMensagem = new ArrayAdapter(
                ActivityConversas.this,
                android.R.layout.simple_list_item_1, arrayDeMensagem );
        listaDeContatos.setAdapter(adapterDeMensagem);
        */

        adapterDeMensagem = new AdapterDeMensagem(ActivityConversas.this,arrayDeMensagem);
        listaDeContatos.setAdapter(adapterDeMensagem);

        //Recuperar as mensagem do firebase
        banco = ConfiguracaoFireBase.getReferencia()
                .child("Mensagem")
                .child(idDoContatoDeRemetente)
                .child(idDoContatoDestinado);
        //Cria listener para as mensagens
        valueEventListenerMensagem = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                arrayDeMensagem.clear();

                for(DataSnapshot mensagens: dataSnapshot.getChildren()){
                    Mensagem mensagem = mensagens.getValue(Mensagem.class);
                    arrayDeMensagem.add(mensagem);
                }
                adapterDeMensagem.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        banco.addValueEventListener(valueEventListenerMensagem);


        btnEnviarConversa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mensagem = txtMensagem.getText().toString();

                if(mensagem.isEmpty())
                {
                    Toast.makeText(ActivityConversas.this,"Digite sua Mensagem",Toast.LENGTH_LONG).show();
                }else{

                    Mensagem objMensagem = new Mensagem();

                    objMensagem.setIdDoUsuario(idDoContatoDeRemetente);
                    objMensagem.setMensagem(mensagem);
                    objMensagem.setNomeDoDestinatario(nomeDoContatoDestinado);

                    //Salvando mensagem no remetente
                    Boolean retornoMensagemRemetente = SalvarMensagem(idDoContatoDeRemetente,idDoContatoDestinado,objMensagem);

                    if(!retornoMensagemRemetente)
                    {
                        Toast.makeText(ActivityConversas.this,
                                "Problemas ao Salvar a Mensagem, tente novamente",
                                Toast.LENGTH_LONG).show();
                    }else{
                        //Salvando mensagem no destinatario
                        Boolean retornoMensagemDestinatario =  SalvarMensagem(idDoContatoDestinado,idDoContatoDeRemetente,objMensagem);
                        if(!retornoMensagemDestinatario)
                        {
                            Toast.makeText(ActivityConversas.this,
                                    "Problemas ao entregar a Mensagem ao destinatario, tente novamente",
                                    Toast.LENGTH_LONG).show();
                        }
                    }

                    Conversas conversa = new Conversas();

                    conversa.setIdUsuario(idDoContatoDestinado);
                    conversa.setNome(nomeDoContatoDestinado);
                    conversa.setMensagem(mensagem);

                    Boolean enviada = SalvarConversas(idDoContatoDeRemetente,idDoContatoDestinado,conversa);
                    if(!enviada)
                    {
                        Toast.makeText(ActivityConversas.this,
                                "Problemas ao Enviar a Mensagem, tente novamente",
                                Toast.LENGTH_LONG).show();
                    }else{

                        //Salvando mensagem no destinatario
                        Conversas conversaDestinatario = new Conversas();

                        conversaDestinatario.setIdUsuario(idDoContatoDeRemetente);
                        conversaDestinatario.setNome(nomeUsuarioLogado);
                        conversaDestinatario.setMensagem(mensagem);

                        Boolean enviadaDestinatario = SalvarConversas(idDoContatoDestinado,idDoContatoDeRemetente,conversaDestinatario);
                        if(!enviadaDestinatario)
                        {
                            Toast.makeText(ActivityConversas.this,
                                    "Problemas ao Enviar a Mensagem ao Destinatário",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                    txtMensagem.setText("");

                }

            }
        });
    }

    private boolean SalvarMensagem(String remetente, String destinatario, Mensagem mensagem){
        try{

            banco = ConfiguracaoFireBase.getReferencia().child("Mensagem");
            banco.child(remetente)
                    .child(destinatario)
                    .push()
                    .setValue(mensagem);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    private boolean SalvarConversas(String remetente, String destinatario, Conversas conversas){
        try{

            banco = ConfiguracaoFireBase.getReferencia().child("Conversas");
            banco.child(remetente)
                    .child(destinatario)
                    .setValue(conversas);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void onStop( ) {
        super.onStop();
        banco.removeEventListener(valueEventListenerMensagem);
    }
}
