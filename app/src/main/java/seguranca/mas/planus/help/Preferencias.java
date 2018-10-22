package seguranca.mas.planus.help;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by wende on 02/03/2018.
 */

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private String NOME_ARQUIVO = "planus.preferencias";
    private int MODE = 0;
    private SharedPreferences.Editor editor;
    private String CHAVE_NOME = "nome";
    private String CHAVE_TELEFONE = "telefone";
    private String CHAVE_TOKEN = "token";
    private String CHAVE_EMAIL= "email";
    private String CHAVE_IDdoUsuario= "IdDoUsuario";
    private String CHAVE_TITULO_TELA= "TituloDaTela";
    private String CHAVE_DA_OCORRENCIA= "idDaOcorrencia";


    public Preferencias(Context P_contexto)
{
    contexto = P_contexto;
    preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
    editor = preferences.edit();
}

public void salvarPreferenciaUsuarios(String token, String nome, String telefone)
{
    editor.putString(CHAVE_NOME,nome);
    editor.putString(CHAVE_TELEFONE,telefone);
    editor.putString(CHAVE_TOKEN,token);

    editor.commit();
}


    public void SalvarTituloDaTela(String titulo) {
        editor.putString(CHAVE_TITULO_TELA,titulo);
        editor.commit();
    }
    public void SalvarDadosIdDoUsuarioLocal(String IdDoUsuario){
        editor.putString(CHAVE_IDdoUsuario,IdDoUsuario);
        editor.commit();
    }
    public void SalvarDadosNomeLocal(String nome){
        editor.putString(CHAVE_NOME,nome);
        editor.commit();
    }
    public void SalvarDadosEmailLocal(String email) {
        editor.putString(CHAVE_EMAIL,email);
        editor.commit();
    }

    public void SalvarIdDaOcorrenciaLocal(String ocorrencia) {
        editor.putString(CHAVE_DA_OCORRENCIA,ocorrencia);
        editor.commit();
    }

    public String RecuperarIdDaOcorrencia(){
        return preferences.getString(CHAVE_DA_OCORRENCIA,null);

    }

    public String RecuperarTituloDaTela(){
        return preferences.getString(CHAVE_TITULO_TELA,null);

    }
    public String RecuperarUsuarioLogado(){
        return preferences.getString(CHAVE_NOME,null);

    }

    public String RecuperarEmailDoUsuarioLogado(){
        return preferences.getString(CHAVE_EMAIL,null);
    }

    public String RecuperarIdDoUsuarioLogado(){
        return preferences.getString(CHAVE_IDdoUsuario,null);
    }

    public HashMap<String,String> getPreferencias(){

    HashMap<String,String> usuarios = new HashMap<>();

    usuarios.put(CHAVE_NOME,preferences.getString(CHAVE_NOME,null));
    usuarios.put(CHAVE_TELEFONE,preferences.getString(CHAVE_TELEFONE,null));
    usuarios.put(CHAVE_TOKEN,preferences.getString(CHAVE_TOKEN,null));
    usuarios.put(CHAVE_EMAIL,preferences.getString(CHAVE_EMAIL,null));
    return usuarios;
}


}
