package seguranca.mas.planus.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import seguranca.mas.planus.model.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentUm extends Fragment {


    private TextView txtCerca;
    private FragmentManager fragmentManager;

    public FragmentUm( ) {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.activity_painel_de_mensagens_direito, container, false);

        txtCerca = (TextView)  view.findViewById(R.id.txtMensagem);

        return view;
    }

}
