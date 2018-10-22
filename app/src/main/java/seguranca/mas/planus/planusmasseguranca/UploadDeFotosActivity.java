package seguranca.mas.planus.planusmasseguranca;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import seguranca.mas.planus.Configuracao.ConfiguracaoFireBase;
import seguranca.mas.planus.model.R;

public class UploadDeFotosActivity extends AppCompatActivity {

    private static final String TAG = "UploadDeFotosActivity";
    private ImageView FotoDoSuspeito;
    private EditText txtNomeDaFoto;
    private ImageButton btnAnterior;
    private ImageButton btnProximo;
    private Button btnCarregarFoto;
    private final static int mWidth = 512;
    private final static int mLength = 512;
    private ArrayList<String> caminhoDaFoto;
    private int array_position;
    private StorageReference mstorageReference;
    private FirebaseAuth auth;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_de_fotos);


        FotoDoSuspeito = (ImageView) findViewById(R.id.imagenDoSuspeito);

        btnCarregarFoto = (Button) findViewById(R.id.btnCarregarFoto);
        btnProximo      = (ImageButton) findViewById(R.id.imageBtnNext);
        btnAnterior     = (ImageButton) findViewById(R.id.imageBtnBack);
        txtNomeDaFoto   = (EditText) findViewById(R.id.txtNomeDaFoto);

        caminhoDaFoto = new ArrayList<>();
        progressDialog = new ProgressDialog(UploadDeFotosActivity.this);


        VerificaUsuarioLogado();

        //checkFilePermissions();
        adicionarArquivo();

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(array_position > 0){
                    array_position = array_position - 1;

                }
            }
        });

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(array_position < caminhoDaFoto.size() -1){
                    array_position = array_position + 1;

                }
            }
        });


        btnCarregarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setMessage("Carregando Foto...");
                progressDialog.show();
                CarregaFotoArmazenada();


               //FirebaseStorage stoge = FirebaseStorage.getInstance();

                String name = "FotoWenderson";

              // Uri url = Uri.fromFile(new File(caminhoDaFoto.get(array_position)));

                 //imagens/users/W2bjU3aEosRfORJODvW8qPrsncK2
                 // gs://planusseguranca.appspot.com/imagens/users/W2bjU3aEosRfORJODvW8qPrsncK2/FotoWenderson.png

               // StorageReference storageReference = stoge.getReferenceFromUrl("gs://planusseguranca.appspot.com/").child("imagens/users/" + "W2bjU3aEosRfORJODvW8qPrsncK2" + "/" +name + ".png");

               // StorageReference storageReference = mstorageReference.child("imagens/users" + "W2bjU3aEosRfORJODvW8qPrsncK2" + "/" +name + ".png");

                 //       storageReference.putFile(url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                //            @Override
                  //          public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                      //          toastMessage("Carregado com sucesso..");
                        //        progressDialog.dismiss();

               //             }
                 //       }).addOnFailureListener(new OnFailureListener() {
                   //         @Override
                     //       public void onFailure(@NonNull Exception e) {
                       //         toastMessage("Carregado falhou...");
                         //       progressDialog.dismiss();
                           // }
                       // });
                    }

            });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Foto Enviada com Sucesso!!!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private void VerificaUsuarioLogado() {
        super.onStart();
        firebaseAuth = ConfiguracaoFireBase.getFirebaseAuth();
        if(firebaseAuth.getCurrentUser() != null)
        {
            mstorageReference = FirebaseStorage.getInstance().getReference();
        }
    }

    public void CarregarImagem(View v) {

       // Picasso.with(v.getContext()).load(txtUrl.getText().toString()).into(imvImagem);
        /*
        *Picasso.with(Aqui vem o contexto).load(URL da imagem).into(ImageView responsável pelo render);
        *
        * */
    }

    private void adicionarArquivo( ) {
        Log.d(TAG,"Adicionando o arquivo da foto");
        String caminho = System.getenv("EXTERNAL_STORAGE");
        caminhoDaFoto.add(caminho+ "/Pictures/Portal/image1.jpg");
        CarregaFotoArmazenada();
    }

    private void CarregaFotoArmazenada( ) {

        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);

       /*
        String foto = caminhoDaFoto.get(array_position);
        File  f = new File(foto,"");
        try {

           Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            FotoDoSuspeito.setImageBitmap(b);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        */
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkFilePermissions( ) {

        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){

            int permissionCheck = UploadDeFotosActivity.this.checkSelfPermission("Manifest.permission.READ_EXTERNAL_STORAGE");
            permissionCheck += UploadDeFotosActivity.this.checkSelfPermission("Manifest.permission.WRITE_EXTERNAL_STORAGE");

            if(permissionCheck !=0){
                this.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE},0);
            }else{
                Log.d(TAG, "checkFilePermissions, PERSMISSION" );
            }
        }

    }

    private void toastMessage(String message){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }


}
