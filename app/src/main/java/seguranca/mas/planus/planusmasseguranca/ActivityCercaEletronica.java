package seguranca.mas.planus.planusmasseguranca;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import seguranca.mas.planus.Fragments.FragmentoInformarRoubo;
import seguranca.mas.planus.help.Preferencias;
import seguranca.mas.planus.model.R;

public class ActivityCercaEletronica extends AppCompatActivity {

    private GoogleMap mMap;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cerca_eletronica);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    public void  showRoute( View view  ){

        String hubMain = "Rua melo viana 187, centro Nova Lima, Brasil";

        hubMain = Uri.encode( hubMain );

        Uri navigation = Uri.parse("google.navigation:q=$hubMain");

        Intent intent = new Intent( Intent.ACTION_VIEW, navigation );

        intent.setPackage( "com.google.android.apps.maps" );

        if( intent.resolveActivity( getPackageManager()) != null ){

            String dirAction   = "dir_action=navigate";
            String destination = "destination=$hubMain";

            navigation =  Uri.parse("https://www.google.com/maps/dir/?api=1&$dirAction&$destination");

             intent = new Intent( Intent.ACTION_VIEW, navigation );
        }

        if( intent.resolveActivity( getPackageManager() ) != null ){

            startActivity( intent );
        }
        else{

            Toast.makeText(this, getString( R.string.apps_needed_info ), Toast.LENGTH_LONG ).show();
        }
    }

    public void DetalhesProximos(View view){

        // Search for restaurants nearby
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=Lanchonetes");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);

    }

    public void BuscaPorReferenciasProximas(View view){

        Uri gmmIntentUri = Uri.parse("geo:19.986690,-43.844808?q=Supermercado");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void BuscarPorRuaDeReferencia(View view){

        // Create a Uri from an intent string. Use the result to create an Intent.
        Uri gmmIntentUri = Uri.parse("google.streetview:cbll=19.983313,-43.849810");

        // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        // Make the Intent explicit by setting the Google Maps package
        mapIntent.setPackage("com.google.android.apps.maps");

        // Attempt to start an activity that can handle the Intent
        startActivity(mapIntent);
    }

    public void onMapReady(GoogleMap googleMap,View view) {


        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng cidade = new LatLng(-19.9855600, -43.8466700);

        MarkerOptions marcar = new MarkerOptions();

        marcar.position(cidade);

        marcar.title("Nova Lima");

        mMap.addMarker(marcar);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(cidade));

    }

    private void RecebeIdentificacao(View view){

        builder = new AlertDialog.Builder(ActivityCercaEletronica.this);
        builder.setTitle("Ocorrência");
        //builder.setMessage("Você Gostaria de se Identificar ? ");

        final EditText idIdentidade = new EditText(ActivityCercaEletronica.this);

        idIdentidade.setHint("Estabelecimento");

        builder.setView(idIdentidade);

        builder.setCancelable(false);

        builder.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String emailDoContato = idIdentidade.getText().toString();

                if(emailDoContato.isEmpty()){

                    Toast.makeText(ActivityCercaEletronica.this,"Informe o Nome do Estabelecimento.",Toast.LENGTH_SHORT).show();

                }else{

                    //verificar se o usuario ja esta cadastrado no app
                    Preferencias preferencias =  new Preferencias(ActivityCercaEletronica.this);

                }

                android.app.FragmentManager fragmentManager = getFragmentManager();
                android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                FragmentoInformarRoubo fragmentoInformarRoubo = new FragmentoInformarRoubo();

                // fragmentTransaction.add(R.id.ContexRoubo, fragmentoInformarRoubo);
                fragmentTransaction.commit();


            }
        }).setNegativeButton("Nâo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

}
