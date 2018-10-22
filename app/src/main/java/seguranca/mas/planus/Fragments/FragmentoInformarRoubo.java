package seguranca.mas.planus.Fragments;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import seguranca.mas.planus.model.R;

/**
 * Created by wende on 16/07/2018.
 */

public class FragmentoInformarRoubo extends Fragment {

private TextView TextoNaTela;


    public  FragmentoInformarRoubo(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmento_dados_das_ocorrencias, container, false);

        // Inflate the layout for this fragment
        return view;


    }

}
