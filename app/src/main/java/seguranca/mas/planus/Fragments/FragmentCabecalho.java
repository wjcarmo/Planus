package seguranca.mas.planus.Fragments;


import android.os.Bundle;
import android.app.Fragment;
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
public class FragmentCabecalho extends Fragment {

    TextView txtUsuarioLogado;
    TextView txtEmailLogado;
    String recebeUsuarioLogado;
    String recebeEmailLogado;

    private FirebaseAuth firebaseAuth;

    public FragmentCabecalho( ) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.nav_header_menu_principal_lateral, container, false);

        //VerificaUsuarioLogado();

        txtUsuarioLogado = (TextView) view.findViewById(R.id.txtUsuarioLogado);
        txtEmailLogado   = (TextView) view.findViewById(R.id.txtEmailLogado);

        txtUsuarioLogado.setText(recebeUsuarioLogado);
        txtEmailLogado.setText(recebeEmailLogado);


        return view;

    }

    private void VerificaUsuarioLogado()
    {

        firebaseAuth = ConfiguracaoFireBase.getFirebaseAuth();
        if(firebaseAuth.getCurrentUser() != null)
        {
            recebeUsuarioLogado = firebaseAuth.getCurrentUser().getEmail();
            recebeEmailLogado   = firebaseAuth.getCurrentUser().getDisplayName();
        }
    }

}
