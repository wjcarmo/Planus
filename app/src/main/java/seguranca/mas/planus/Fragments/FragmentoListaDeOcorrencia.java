package seguranca.mas.planus.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import seguranca.mas.planus.Adapter.AdapterOcorrencias;
import seguranca.mas.planus.Configuracao.ConfiguracaoFireBase;
import seguranca.mas.planus.help.Preferencias;
import seguranca.mas.planus.model.Ocorrencias;
import seguranca.mas.planus.model.R;
import seguranca.mas.planus.planusmasseguranca.TrocaDeMensagemActivity;
import seguranca.mas.planus.planusmasseguranca.UploadDeFotosActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentoListaDeOcorrencia extends Fragment {

    private TextView TxtOcorrencia;
    private ListView ListViewDeOcorrencias;
    private ArrayAdapter adapterOcorrencias;
    private ArrayList<Ocorrencias> ListaDeOcorrencias;
    private DatabaseReference BancoDeDados;
    Fragment fragment = null;

    public FragmentoListaDeOcorrencia( ) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ListaDeOcorrencias = new ArrayList<>();

        View view = inflater.inflate(R.layout.listadeocorrencias, container, false);
        ListViewDeOcorrencias = (ListView)  view.findViewById(R.id.lv_Ocorrencias);

        adapterOcorrencias = new AdapterOcorrencias(getActivity(),ListaDeOcorrencias);

        ListViewDeOcorrencias.setAdapter(adapterOcorrencias);


        Preferencias preferencias =  new Preferencias(getActivity());
        String  idDoUsuarioLogado = preferencias.RecuperarIdDoUsuarioLogado();


        BancoDeDados = ConfiguracaoFireBase.getReferencia()
                .child("Ocorrencias");
               // .child(idDoUsuarioLogado);

        BancoDeDados.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ListaDeOcorrencias.clear();

                for(DataSnapshot dados : dataSnapshot.getChildren() )
                {
                    Ocorrencias ocorrencia = dados.getValue(Ocorrencias.class);
                    ListaDeOcorrencias.add(ocorrencia);
                }
                adapterOcorrencias.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ListViewDeOcorrencias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                Intent intent = new Intent(getActivity(), UploadDeFotosActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
