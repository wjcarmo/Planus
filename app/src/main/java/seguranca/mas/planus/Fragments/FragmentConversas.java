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

import seguranca.mas.planus.Activity.ActivityConversas;
import seguranca.mas.planus.Adapter.AdapterDeContatos;
import seguranca.mas.planus.Adapter.AdapterDeConversas;
import seguranca.mas.planus.Configuracao.ConfiguracaoFireBase;
import seguranca.mas.planus.help.Preferencias;
import seguranca.mas.planus.model.Base64Criptografia;
import seguranca.mas.planus.model.Contatos;
import seguranca.mas.planus.model.Conversas;
import seguranca.mas.planus.model.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentConversas extends Fragment {


    private ListView ListaViewConversas;
    private ArrayAdapter adapterDeConversas;
    private ArrayList<Conversas> ListaConversas;
    private DatabaseReference BancoDeDados;
    private ValueEventListener valueEventListener;
    Fragment fragment = null;


    public FragmentConversas( ) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ListaConversas = new ArrayList<>();

        View view = inflater.inflate(R.layout.listasdeconversas, container, false);

        ListaViewConversas = (ListView)  view.findViewById(R.id.lst_Conversas);

        adapterDeConversas = new AdapterDeConversas(getActivity(),ListaConversas);
        ListaViewConversas.setAdapter(adapterDeConversas);

        Preferencias preferencias =  new Preferencias(getActivity());
        String  idDoUsuarioLogado = preferencias.RecuperarIdDoUsuarioLogado();

        BancoDeDados = ConfiguracaoFireBase.getReferencia()
                .child("Conversas")
                .child(idDoUsuarioLogado);

        valueEventListener = (new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                ListaConversas.clear();

                for(DataSnapshot dados : dataSnapshot.getChildren() )
                {
                    Conversas convesas = dados.getValue(Conversas.class);

                    ListaConversas.add(convesas);
                }
                adapterDeConversas.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        ListaViewConversas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Intent intent = new Intent(getActivity(), ActivityConversas.class);

                //Recuperar os dados a serem passados
                Conversas conversas = ListaConversas.get(position);

                //enviar dados para conversa
                String email = Base64Criptografia.getDadosDescriptografado(conversas.getIdUsuario());
                intent.putExtra("nome",conversas.getNome());
                intent.putExtra("email",email);

                startActivity(intent);

            }
        });


        return view;
    }

    @Override
    public void onStart( ) {
        super.onStart();
        BancoDeDados.addValueEventListener(valueEventListener);
    }

    @Override
    public void onStop( ) {
        super.onStop();
        BancoDeDados.removeEventListener(valueEventListener);

    }
}
