package seguranca.mas.planus.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import seguranca.mas.planus.Configuracao.ConfiguracaoFireBase;
import seguranca.mas.planus.model.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentRecuperaUsuarioLogado extends Fragment {

    private FirebaseAuth firebaseAuth;
    private String idDoUsuarioLogado;
    private String emailDoUsuarioLogado;
    private String nomeDoUsuarioLogado;
    private TextView RecebeUsuarioLogado;
    private TextView RecebeEmailLogado;


    public FragmentRecuperaUsuarioLogado( ) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.nav_header_menu_principal_lateral, container, false);

        firebaseAuth = ConfiguracaoFireBase.getFirebaseAuth();

        if(firebaseAuth.getCurrentUser() != null)
        {
            //setContentView(R.layout.nav_header_menu_principal_lateral);

            RecebeUsuarioLogado  = (TextView) view.findViewById(R.id.txtUsuarioLogado);
            RecebeEmailLogado    = (TextView) view.findViewById(R.id.txtEmailLogado);

            idDoUsuarioLogado    = firebaseAuth.getCurrentUser().getUid();
            // nomeDoUsuarioLogado  =  firebaseAuth.getCurrentUser().getDisplayName();
            emailDoUsuarioLogado = firebaseAuth.getCurrentUser().getEmail();

            RecebeUsuarioLogado.setText("Nome: "+emailDoUsuarioLogado);
            RecebeEmailLogado.setText("E-mail: "+idDoUsuarioLogado);

        }
        return view;

    }

}
