package seguranca.mas.planus.help;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wende on 03/03/2018.
 */

public class Permissao {

    public static boolean validaPermissoes(int requisicaoId, Activity activity, String[] permissao)
    {
            if(Build.VERSION.SDK_INT >= 23)
            {
                List<String> lstPermissoes = new ArrayList<String>();

                /**
                 *Percorrendo a o array de permissoes para verificar se todas as permissoes ja foram concedidas
                 */

                for(String permisso : permissao)
                {
                    boolean P = ContextCompat.checkSelfPermission(activity,permisso) == PackageManager.PERMISSION_GRANTED;
                    if(!P )
                    {
                        lstPermissoes.add(permisso);
                    }
                }

                /*
                * Caso a lista esteja vazia nao e necessario solicitar novamente permissoes
                * */
                if(lstPermissoes.isEmpty())
                {
                    return true;
                }

                String[] novasPermissoes = new String[lstPermissoes.size()];
                lstPermissoes.toArray(novasPermissoes);

                //Solicita a permissao
                ActivityCompat.requestPermissions(activity,novasPermissoes,requisicaoId);

            }

        return true;

    }
}
