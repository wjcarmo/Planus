package seguranca.mas.planus.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import seguranca.mas.planus.model.Ocorrencias;
import seguranca.mas.planus.model.R;

/**
 * Created by wende on 02/09/2018.
 */

public class AdapterOcorrencias extends ArrayAdapter<Ocorrencias> {

    private ArrayList<Ocorrencias> ocorrencias;
    private Context contexto;

    public AdapterOcorrencias(@NonNull Context context, @NonNull ArrayList<Ocorrencias> objects) {
        super(context, 0, objects);
        this.ocorrencias = objects;
        this.contexto = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if(ocorrencias != null){

            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.fragmento_dados_das_ocorrencias,parent,false);

            ImageView FotoDoSuspeito  = (ImageView) view.findViewById(R.id.MostraIconeDeGuarda);
            TextView  TipoDoRoubo  = (TextView) view.findViewById(R.id.MostraTipodDoRoubo);
            TextView  QuemSofreuRoubo = (TextView) view.findViewById(R.id.MostraQuemSofreuRoubo);
            TextView  TipoDaArma = (TextView) view.findViewById(R.id.MostraTipoDeViolencia);
            TextView  TempoDoOcorrido = (TextView) view.findViewById(R.id.MostraTempoOcorrido);
            TextView  TipoDaFulga = (TextView) view.findViewById(R.id.MostraTipoDaFulga);
            TextView  Caracterisitcas = (TextView) view.findViewById(R.id.MostraCaracteristicas);
            TextView  DataDaOcorrencia = (TextView) view.findViewById(R.id.MostraDataOcorrencia);


            Ocorrencias ocorrencia = ocorrencias.get(position);

            FotoDoSuspeito.setImageResource(R.drawable.ladraoum);

            TipoDoRoubo.setText(ocorrencia.getTipoDaOcorrencia());

            QuemSofreuRoubo.setText("Nome da Vitima....: "+ocorrencia.getNomeDaVitima());
                 TipoDaArma.setText("Tipo da Arma......: "+ocorrencia.getTipoDeViolencia());
            TempoDoOcorrido.setText("Tempo do Ocorrido.: "+ocorrencia.getTempoDoOcorrido());
                TipoDaFulga.setText("Tipo da Fulga.....: "+ocorrencia.getTipoDaFulga());
            Caracterisitcas.setText("Detalhes Suspeito.: "+ocorrencia.getCaracteristicasDoSuspeito());
           DataDaOcorrencia.setText("Data do Ocorrido..: "+ocorrencia.getDataOcorrencia());
        }

        return view;

    }
}
