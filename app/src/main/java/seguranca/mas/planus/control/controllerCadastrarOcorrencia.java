package seguranca.mas.planus.control;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import seguranca.mas.planus.Configuracao.ConfiguracaoFireBase;
import seguranca.mas.planus.model.Ocorrencias;

/**
 * Created by wende on 02/09/2018.
 */

public  class   controllerCadastrarOcorrencia {


    private DatabaseReference DataUsuarios;
    private FirebaseAuth firebaseAuth;
    private static DatabaseReference banco;
    static Ocorrencias objtOcorrencias;

    public controllerCadastrarOcorrencia( ){}

    public static void  SalvarDados(Ocorrencias ocorrencias) {
        objtOcorrencias = new Ocorrencias();
        banco = ConfiguracaoFireBase.getReferencia();
        banco.child("Ocorrencias").child(ocorrencias.getIdOcorrencia()).setValue(ocorrencias);
    }

}
