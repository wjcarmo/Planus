package seguranca.mas.planus.planusmasseguranca;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seguranca.mas.planus.Activity.MapsActivity;
import seguranca.mas.planus.Fragments.FragmentoInformarRoubo;
import seguranca.mas.planus.help.Preferencias;
import seguranca.mas.planus.model.R;

public class TrocaDeMensagemActivity extends AppCompatActivity  implements OnMapReadyCallback {

    private Toolbar toolbar;
    private FragmentManager fragmentManager;

    private MapView MapViewCerco;
    private GoogleMap mMap;

    private static final String MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey";
    static final LatLng KYIV = new LatLng(-19.9855600, -43.8466700);
    private int mMapWidth = 600;
    private int mMapHeight = 800;
    private ImageView mImageView;

    AlertDialog.Builder builder;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troca_de_mensagem);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detalhes da Ocorrência");
        toolbar.setNavigationIcon(R.drawable.iconeplanus);
        setSupportActionBar(toolbar);

        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }

        MapViewCerco = (MapView) findViewById(R.id.mapViewCerco);
        MapViewCerco.onCreate(mapViewBundle);

        GoogleMapOptions options = new GoogleMapOptions()
                .compassEnabled(false)
                .mapToolbarEnabled(false)
                .camera(CameraPosition.fromLatLngZoom(KYIV,15))
                .liteMode(true);

        MapViewCerco.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                // draw dashed path here:
                List<LatLng> sourcePoints = new ArrayList<>();

                sourcePoints.add(new LatLng(50.440311, 30.523730));
                sourcePoints.add(new LatLng(50.460411, 30.523930));

                PolylineOptions polyLineOptions = new PolylineOptions();
                polyLineOptions.addAll(sourcePoints);
                polyLineOptions.width(10);
                polyLineOptions.color(Color.RED);
                Polyline polyline = googleMap.addPolyline(polyLineOptions);

                List<PatternItem> pattern = Arrays.<PatternItem>asList(new Dash(30), new Gap(20));
                polyline.setPattern(pattern);

                // set map size in pixels and initiate image loading
                MapViewCerco.measure(View.MeasureSpec.makeMeasureSpec(mMapWidth, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(mMapHeight, View.MeasureSpec.EXACTLY));
                MapViewCerco.layout(0, 0, mMapWidth, mMapHeight);

                googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                    @Override
                    public void onMapLoaded() {

                        MapViewCerco.setDrawingCacheEnabled(true);
                        MapViewCerco.measure(View.MeasureSpec.makeMeasureSpec(mMapWidth, View.MeasureSpec.EXACTLY),
                                View.MeasureSpec.makeMeasureSpec(mMapHeight, View.MeasureSpec.EXACTLY));
                        //MapViewCerco.layout(0, 0, mMapWidth, mMapHeight);
                        MapViewCerco.buildDrawingCache(true);
                        Log.i("DEBUG", "onMapLoaded");
                        //Bitmap b is your "static map"
                        Bitmap b = Bitmap.createBitmap(MapViewCerco.getDrawingCache());
                        MapViewCerco.setDrawingCacheEnabled(false);
                        mImageView.setImageBitmap(b);

                    }
                });

            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle);
        }

        MapViewCerco.onSaveInstanceState(mapViewBundle);
    }
    @Override
    protected void onResume() {
        super.onResume();
        MapViewCerco.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapViewCerco.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        MapViewCerco.onStop();
    }
    @Override
    protected void onPause() {
        MapViewCerco.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        MapViewCerco.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        MapViewCerco.onLowMemory();
    }

    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng cidade = new LatLng(-19.9855600, -43.8466700);

        MarkerOptions marcar = new MarkerOptions();

        marcar.position(cidade);

        marcar.title("Nova Lima");

        mMap.addMarker(marcar);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(cidade));


        mMap = googleMap;
        mMap.setMinZoomPreference(12);
        LatLng ny = new LatLng(40.7143528, -74.0059731);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ny));
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
/*
    public void onMapReady(GoogleMap googleMap, View view) {


        mMap = googleMap;

        mMap.getUiSettings().setZoomControlsEnabled(true);
        // Add a marker in Sydney and move the camera
        LatLng cidade = new LatLng(-19.9855600, -43.8466700);


        MarkerOptions marcar = new MarkerOptions();

        marcar.position(cidade);

        marcar.title("Nova Lima");

        mMap.addMarker(marcar);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(cidade));

    }*/

    private void RecebeIdentificacao(View view){

        builder = new AlertDialog.Builder(TrocaDeMensagemActivity.this);
        builder.setTitle("Ocorrência");
        //builder.setMessage("Você Gostaria de se Identificar ? ");

        final EditText idIdentidade = new EditText(TrocaDeMensagemActivity.this);

        idIdentidade.setHint("Estabelecimento");

        builder.setView(idIdentidade);

        builder.setCancelable(false);

        builder.setPositiveButton("Cadastrar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String emailDoContato = idIdentidade.getText().toString();

                if(emailDoContato.isEmpty()){

                    Toast.makeText(TrocaDeMensagemActivity.this,"Informe o Nome do Estabelecimento.",Toast.LENGTH_SHORT).show();

                }else{

                    //verificar se o usuario ja esta cadastrado no app
                    Preferencias preferencias =  new Preferencias(TrocaDeMensagemActivity.this);

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
    private void ChamaFragmentMaps()    {

        fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        MapsActivity fragmentOcorrencias = new MapsActivity();

        fragmentTransaction.add(R.id.ContainerCentralMap , fragmentOcorrencias);
        fragmentTransaction.commitAllowingStateLoss();


    }
}
