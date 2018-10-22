package seguranca.mas.planus.Configuracao;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by wende on 07/07/2018.
 */

public final  class ConfiguracaoFireBase {

    private static DatabaseReference referencia;
    private static FirebaseAuth firebaseAuth;

    public static DatabaseReference getReferencia()
    {
       if(referencia == null)
       {
           referencia = FirebaseDatabase.getInstance().getReference();
       }
        return referencia;
    }
    public static FirebaseAuth getFirebaseAuth()
    {
        if(firebaseAuth == null)
        {
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }




}
