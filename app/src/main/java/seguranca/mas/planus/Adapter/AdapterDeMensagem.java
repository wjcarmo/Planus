package seguranca.mas.planus.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import seguranca.mas.planus.help.Preferencias;
import seguranca.mas.planus.model.Contatos;
import seguranca.mas.planus.model.Mensagem;
import seguranca.mas.planus.model.R;

/**
 * Created by wende on 18/09/2018.
 */

public class AdapterDeMensagem extends ArrayAdapter<Mensagem> {


    private ArrayList<Mensagem> ArrayDeMensagems;
    private Context contexto;

    public AdapterDeMensagem(@NonNull Context context, @NonNull ArrayList<Mensagem> objects) {
        super(context, 0, objects);
        this.ArrayDeMensagems = objects;
        this.contexto = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = null;

        if (ArrayDeMensagems != null) {

            Preferencias preferencias =  new Preferencias(contexto);
            String  idDoRemetente = preferencias.RecuperarIdDoUsuarioLogado();

            LayoutInflater inflater = (LayoutInflater) contexto.getSystemService(contexto.LAYOUT_INFLATER_SERVICE);

            Mensagem mensagem = ArrayDeMensagems.get(position);

            if(idDoRemetente.equals(mensagem.getIdDoUsuario())){
                view = inflater.inflate(R.layout.activity_painel_de_mensagens_direito,parent,false);
            }else{
                view = inflater.inflate(R.layout.activity_painel_de_mensagens_esquerdo,parent,false);
            }

            //recuperar os elementos
            TextView MensagemRemetente  = (TextView) view.findViewById(R.id.txtMensagem);

            MensagemRemetente.setText(mensagem.getMensagem());
        }

        return view;
    }

}
