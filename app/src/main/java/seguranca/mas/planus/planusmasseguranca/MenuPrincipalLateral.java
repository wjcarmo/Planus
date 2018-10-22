package seguranca.mas.planus.planusmasseguranca;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import seguranca.mas.planus.Activity.InformarRoubo;
import seguranca.mas.planus.Activity.MapsActivity;
import seguranca.mas.planus.Activity.MenuCentralActivity;
import seguranca.mas.planus.Activity.TelaInicialActivity;
import seguranca.mas.planus.Adapter.TabAdapter;
import seguranca.mas.planus.Adapter.TabAdapterMensagem;
import seguranca.mas.planus.Configuracao.ConfiguracaoFireBase;
import seguranca.mas.planus.Fragments.FragmentoListaDeOcorrencia;
import seguranca.mas.planus.Fragments.FragmentoInformarRoubo;
import seguranca.mas.planus.help.Preferencias;
import seguranca.mas.planus.help.SlidingTabLayout;
import seguranca.mas.planus.model.Base64Criptografia;
import seguranca.mas.planus.model.Contatos;
import seguranca.mas.planus.model.R;
import seguranca.mas.planus.model.Usuarios;

public class MenuPrincipalLateral extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseAuth firebaseAuth;
    private FragmentManager fragmentManager;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    AlertDialog.Builder builder;
    private Boolean resposta = true;
    private String idDoUsuarioLogado;
    private String emailDoUsuarioLogado;
    public String emailDoContato;
    private String idDoContato;
    private String nomeDoContato;
    private TextView RecebeUsuarioLogado;
    private TextView RecebeEmailLogado;
    private TextView RecebeTituloDaTela;
    Usuarios usuLogado;
    Fragment fragment = null;
    private DatabaseReference BancoDeUsuarios;
    private String idContato;
    private TextView RecebeMenus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_lateral);

        CarregaTabs("Planus - Ocorrências ");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Alerta de Ocorrência ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

       */


    }

    @Override
    public void onBackPressed( ) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_principal_lateral, menu);


        RecuperarUsuarioLogadoLocal();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Sair) {
            SignOutUsuario();
            return true;
        }
        if (id == R.id.action_Adicionar) {
            AdicionandoContatos();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        Preferencias preferencias = new Preferencias(MenuPrincipalLateral.this);
        String titulo = preferencias.RecuperarTituloDaTela();

        int id = item.getItemId();

            if (id == R.id.nav_Roubo) {

                AbreCadastroDeOcorrencias();

            } else if (id == R.id.nav_gallery) {

            } else if (id == R.id.nav_slideshow) {

            } else if (id == R.id.nav_manage) {
                ChamaFragmentMaps();

            } else if (id == R.id.nav_share) {
                CarregaTelefonesUteis();
            } else if (id == R.id.cerco) {
                PassaLocalizacao();
            }else if (id == R.id.nav_send) {
                String titulomensagem = "  Comunicação  ";
                CarregaMensagens(titulomensagem);
            }

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            return true;
        }


    public void AbrirOcorrencia(){

    builder = new AlertDialog.Builder(MenuPrincipalLateral.this);
    builder.setTitle("Ocorrência");
    builder.setMessage("Você Gostaria de se Identificar ? ");
    final EditText idIdentidade = new EditText(MenuPrincipalLateral.this);
    builder.setView(idIdentidade);
    builder.setCancelable(false);

    builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String nome = idIdentidade.getText().toString();
                if(nome.isEmpty()){

                    Toast.makeText(MenuPrincipalLateral.this,"Favor informar seu Nome.",Toast.LENGTH_SHORT).show();


                }else{
                    //verificar se o usuario ja esta cadastrado no app


                }

                android.app.FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                FragmentoInformarRoubo fragmentoInformarRoubo = new FragmentoInformarRoubo();

               // fragmentTransaction.add(R.id.ContexRoubo, fragmentoInformarRoubo);
                fragmentTransaction.commit();

                resposta = false;


            }
        }).setNegativeButton("Nâo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
    dialog.show();

}

    public void MontaMenu(){}

    private void VerificaUsuarioLogado() {
        firebaseAuth = ConfiguracaoFireBase.getFirebaseAuth();
        if(firebaseAuth.getCurrentUser() != null)
        {

            Intent intent = new Intent(MenuPrincipalLateral.this, MapsActivity.class);
            startActivity(intent);
            //AbrirMenuPrincipal();
        }

    }
    private void AbrirMenuPrincipal() {
        Intent intent = new Intent(MenuPrincipalLateral.this, MenuCentralActivity.class);
        startActivity(intent);

    }
    private void AdicionandoContatos(){

        builder = new AlertDialog.Builder(MenuPrincipalLateral.this);
        builder.setTitle("Ocorrência");
        //builder.setMessage("Você Gostaria de se Identificar ? ");

        final EditText idIdentidade = new EditText(MenuPrincipalLateral.this);
        idIdentidade.setHint("E-mail");

        builder.setView(idIdentidade);

        builder.setCancelable(false);

        builder.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                 emailDoContato = idIdentidade.getText().toString();
                 nomeDoContato = "Nome do Contato";

                if(emailDoContato.isEmpty()){

                    Toast.makeText(MenuPrincipalLateral.this,"Favor informar seu Email.",Toast.LENGTH_SHORT).show();

                }else{

                    //verificar se o usuario ja esta cadastrado no app
                    Preferencias preferencias =  new Preferencias(MenuPrincipalLateral.this);
                    idDoUsuarioLogado = preferencias.RecuperarIdDoUsuarioLogado();

                    idDoContato = Base64Criptografia.SetDadosParaCriptografar(emailDoContato);

                    BancoDeUsuarios = ConfiguracaoFireBase.getReferencia();

                    BancoDeUsuarios = BancoDeUsuarios.child("usuarios").child(idDoContato);

                        BancoDeUsuarios.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                if(dataSnapshot.getValue()!= null)
                                {

                                    Preferencias preferencias =  new Preferencias(MenuPrincipalLateral.this);
                                    idDoUsuarioLogado = preferencias.RecuperarIdDoUsuarioLogado();

                                    //Recupera dados do contato adicionado
                                    Usuarios usuarioContato = dataSnapshot.getValue(Usuarios.class);
                                    //recupera o id do usuario do banco de dados

                                    BancoDeUsuarios = ConfiguracaoFireBase.getReferencia();
                                    BancoDeUsuarios = BancoDeUsuarios.child("Contatos")
                                            .child(idDoUsuarioLogado)
                                            .child(idDoContato);

                                    Contatos contatos = new Contatos();

                                    contatos.setEmail(usuarioContato.getEmail());
                                    contatos.setNome(usuarioContato.getNome());

                                    BancoDeUsuarios.setValue(contatos);

                                }else{
                                    Toast.makeText(MenuPrincipalLateral.this,"Usuário não possui cadastro.",Toast.LENGTH_LONG).show();

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                }

                android.app.FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                FragmentoInformarRoubo fragmentoInformarRoubo = new FragmentoInformarRoubo();

                // fragmentTransaction.add(R.id.ContexRoubo, fragmentoInformarRoubo);
                fragmentTransaction.commit();

                resposta = false;


            }
        }).setNegativeButton("Nâo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void AbreTrocaDeMensagem(){
        Intent intent = new Intent(MenuPrincipalLateral.this, TrocaDeMensagemActivity.class);
        startActivity(intent);
    }

    private void AbreCadastroDeOcorrencias(){
        Intent intent = new Intent(MenuPrincipalLateral.this, InformarRoubo.class);
        startActivity(intent);
    }
    private void ChamaFragmentMaps()    {

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MapsActivity fragmentOcorrencias = new MapsActivity();

         fragmentTransaction.add(R.id.ContainerCentralMap , fragmentOcorrencias);
        fragmentTransaction.commitAllowingStateLoss();


    }
    private void SignOutUsuario(){
        firebaseAuth = ConfiguracaoFireBase.getFirebaseAuth();
        if(firebaseAuth.getCurrentUser() != null)
        {
            firebaseAuth.signOut();
            Intent intent = new Intent(MenuPrincipalLateral.this, TelaInicialActivity.class);
            startActivity(intent);
            finish();
        }


    }

    private void CarregaTelefonesUteis(){

            Intent intent = new Intent(MenuPrincipalLateral.this, ActivityTelefoneUteis.class);
            startActivity(intent);
           }


    public void CarregaTabs(String tituloNaBarra){

        toolbar = (Toolbar) findViewById(R.id.toolbar_principal);
        toolbar.setTitle(tituloNaBarra);
       // toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.corFundoDeBotoes));

        setSupportActionBar(toolbar);

        slidingTabLayout = (SlidingTabLayout)  findViewById(R.id.stl_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.corBalaoAmarelo));

        viewPager = (ViewPager) findViewById(R.id.vp_Pagina);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());

        viewPager.setAdapter(tabAdapter);
        slidingTabLayout.setViewPager(viewPager);
    }

    private void PassaLocalizacao(){
        Intent intent = new Intent(MenuPrincipalLateral.this, ActivityCercaEletronica.class);
        startActivity(intent);
    }
    private void CarregaOcorrenciasFragment()    {

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentoListaDeOcorrencia fragmentOcorrencias = new FragmentoListaDeOcorrencia();

        fragmentTransaction.add(R.id.ContainerCentralMap , fragmentOcorrencias);
        fragmentTransaction.commitAllowingStateLoss();


    }

    public void RecuperarUsuarioLogadoLocal(){

        RecebeUsuarioLogado  = (TextView) findViewById(R.id.txtUsuarioLogado);
        RecebeEmailLogado    = (TextView) findViewById(R.id.txtEmailLogado);
        RecebeTituloDaTela    = (TextView) findViewById(R.id.txtTituloDaTela);

        Preferencias preferencias = new Preferencias(MenuPrincipalLateral.this);

        String nome  = preferencias.RecuperarUsuarioLogado();
        String email = preferencias.RecuperarEmailDoUsuarioLogado();
        String titulo = preferencias.RecuperarTituloDaTela();

        RecebeEmailLogado.setText(email);
        RecebeUsuarioLogado.setText(nome);
        RecebeTituloDaTela.setText(titulo);


    }


    public void CarregaOcorrencias(String tituloNaBarra){

        toolbar = (Toolbar) findViewById(R.id.toolbar_principal);
        toolbar.setTitle(tituloNaBarra);
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.corFundoDeBotoes));

        setSupportActionBar(toolbar);

        slidingTabLayout = (SlidingTabLayout)  findViewById(R.id.stl_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.corBalaoAmarelo));

        viewPager = (ViewPager) findViewById(R.id.vp_Pagina);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager());

        viewPager.setAdapter(tabAdapter);
        slidingTabLayout.setViewPager(viewPager);
    }

    public void CarregaMensagens(String tituloNaBarra){

        toolbar = (Toolbar) findViewById(R.id.toolbar_principal);
        toolbar.setTitle(tituloNaBarra);


        setSupportActionBar(toolbar);

        slidingTabLayout = (SlidingTabLayout)  findViewById(R.id.stl_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this,R.color.corBalaoAmarelo));

        viewPager = (ViewPager) findViewById(R.id.vp_Pagina);

        TabAdapterMensagem tabAdapter = new TabAdapterMensagem(getSupportFragmentManager());

        viewPager.setAdapter(tabAdapter);
        slidingTabLayout.setViewPager(viewPager);
    }

}
