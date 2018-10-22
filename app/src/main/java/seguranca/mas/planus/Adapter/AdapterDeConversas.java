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

import seguranca.mas.planus.model.Conversas;
import seguranca.mas.planus.model.Mensagem;
import seguranca.mas.planus.model.Ocorrencias;
import seguranca.mas.planus.model.R;


/**
 * Created by wende on 02/10/2018.
 */

public class AdapterDeConversas extends ArrayAdapter<Conversas> {


    private ArrayList<Conversas> conversas;
    private Context contexto;


    public AdapterDeConversas(Context context, @NonNull ArrayList<Conversas> objects){
        super(context,0,objects);
        this.conversas = objects;
        this.contexto = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if(conversas != null){

            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.fragment_fragment_conversas,parent,false);

            ImageView FotoDaConversa       = (ImageView) view.findViewById(R.id.MostraFotoDoContatoConversa);
            TextView  NomeContatoConversa  = (TextView) view.findViewById(R.id.MostraNomeDoContatosConversas);
            TextView  MensagemConversa     = (TextView) view.findViewById(R.id.MostraConversasDoContato);

            Conversas conversa = conversas.get(position);

            FotoDaConversa.setImageResource(R.drawable.ic_action_user);

            NomeContatoConversa.setText(conversa.getNome());
            MensagemConversa.setText(conversa.getMensagem());

        }

        return view;

    }


}
