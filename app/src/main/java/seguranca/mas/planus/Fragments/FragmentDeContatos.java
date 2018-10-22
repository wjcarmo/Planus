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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import seguranca.mas.planus.Adapter.AdapterDeContatos;
import seguranca.mas.planus.Configuracao.ConfiguracaoFireBase;
import seguranca.mas.planus.help.Preferencias;
import seguranca.mas.planus.model.Contatos;
import seguranca.mas.planus.model.R;
import seguranca.mas.planus.Activity.ActivityConversas;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDeContatos extends Fragment {

    private ListView ListViewDeContatos;
    private ArrayAdapter adapterDeContatos;
    private ArrayList<Contatos> ListaContatos;
    private DatabaseReference BancoDeDados;
    Fragment fragment = null;

    public FragmentDeContatos( ) {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        ListaContatos = new ArrayList<>();

        View view = inflater.inflate(R.layout.lista_de_contatos, container, false);

        ListViewDeContatos = (ListView)  view.findViewById(R.id.lv_Contatos);

        adapterDeContatos = new AdapterDeContatos(getActivity(),ListaContatos);
        ListViewDeContatos.setAdapter(adapterDeContatos);

        Preferencias preferencias =  new Preferencias(getActivity());
        String  idDoUsuarioLogado = preferencias.RecuperarIdDoUsuarioLogado();


        BancoDeDados = ConfiguracaoFireBase.getReferencia()
                .child("Contatos")
                .child(idDoUsuarioLogado);

        BancoDeDados.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ListaContatos.clear();

                for(DataSnapshot dados : dataSnapshot.getChildren() )
                {
                    Contatos contatos = dados.getValue(Contatos.class);
                    ListaContatos.add(contatos);
                }
                adapterDeContatos.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        ListViewDeContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(getActivity(), ActivityConversas.class);

                //Recuperar os dados a serem passados
                Contatos contato = ListaContatos.get(position);

                //enviar dados para conversa
                intent.putExtra("nome",contato.getNome());
                intent.putExtra("email",contato.getEmail());

                startActivity(intent);

            }
        });

        return view;
    }

}