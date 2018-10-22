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

import seguranca.mas.planus.model.Contatos;
import seguranca.mas.planus.model.R;

/**
 * Created by wende on 26/08/2018.
 */

public class AdapterDeContatos extends ArrayAdapter<Contatos> {

    private ArrayList<Contatos> contatos;
    private Context contexto;

    public AdapterDeContatos(@NonNull Context context, @NonNull ArrayList<Contatos> objects) {
        super(context, 0, objects);
        this.contatos = objects;
        this.contexto = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if(contatos != null){

            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.fragmento_dados_dos_contatos,parent,false);

            ImageView ContatoFoto  = (ImageView) view.findViewById(R.id.MostraFotoDoContato);
            TextView  ContatoNome  = (TextView) view.findViewById(R.id.MostraNomeDoContatos);
            TextView  ContatoEmail = (TextView) view.findViewById(R.id.MostraEmailDoContato);
            TextView  ContatoLocalizacao = (TextView) view.findViewById(R.id.MostraLocalizacaoDoContato);


            Contatos contato = contatos.get(position);

            ContatoNome.setText(contato.getNome());
            ContatoEmail.setText(contato.getEmail());
            ContatoLocalizacao.setText("Rua Melo Viana, Centro");

            ContatoFoto.setImageResource(R.drawable.ic_action_user);

        }

        return view;

    }
}
