package seguranca.mas.planus.Fragments;

import android.content.DialogInterface;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import seguranca.mas.planus.control.controllerCadastrarOcorrencia;
import seguranca.mas.planus.help.Preferencias;
import seguranca.mas.planus.model.Base64Criptografia;
import seguranca.mas.planus.model.Ocorrencias;
import seguranca.mas.planus.model.R;


/**
 * Created by wende on 29/09/2018.
 */

public class FragmentCadastroDeOcorrencia extends Fragment {

    public TextView respostaUm;
    public TextView respostaDois;
    private Toolbar toolbar;
    private Button btnRespostaSim;
    private Button btnRespostaNao;
    private Button btnEu;
    private Button btnComercio;
    private Button btnTerceiro;
    private Button btnArmaFogo;
    private Button btnArmaBranca;
    private Button btnArmaNaoDemonstrada;
    private Button btn10Min;
    private Button btn20Min;
    private Button btn30Min;
    private Button btn40Min;
    private Button btn60Min;
    private Button btnFulgaAPe;
    private Button btnFulgaDeCarro;
    private Button btnFulgaDeMoto;
    private Button btnCaracteristicaSim;
    private Button btnCaracteristicaNao;
    private String CaracteristicaSim;
    private Button btnFotoSim;
    private Button btnFotoNao;

    AlertDialog.Builder builder;
    private boolean Clicou = false;
    private String Resposta001;
    private String Resposta002;
    private String Resposta003;
    private String Resposta004;
    private String Resposta005;
    private String Resposta006;
    private String idDaOcorrencia;
    private String idOcorrenciaLocal;
    private String TipoDaOcorrencia;
    private Ocorrencias ocorrencias;
    private String DataOcorrencia;


    public FragmentCadastroDeOcorrencia(){}
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_fragment_roubo, container, false);

        ocorrencias = new Ocorrencias();

        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);

        btnRespostaSim = (Button) view.findViewById(R.id.btnResposta001);
        btnRespostaNao = (Button) view.findViewById(R.id.btnRespostaNao);
        //btnEu           = (Button) view.findViewById(R.id.btnRespostaEu);
        btnComercio = (Button) view.findViewById(R.id.btnRespostaComercio);
        btnTerceiro = (Button) view.findViewById(R.id.btnSofreuORouboTerceiro);
        btnArmaFogo = (Button) view.findViewById(R.id.btnArmaDeFogo);
        btnArmaBranca = (Button) view.findViewById(R.id.btnArmaBranca);
        btnArmaNaoDemonstrada = (Button) view.findViewById(R.id.btnArmaNaoDemonstrada);
        btn10Min = (Button) view.findViewById(R.id.btnTempoAte10);
        btn20Min = (Button) view.findViewById(R.id.btnTempoDe10a20);
        btn30Min = (Button) view.findViewById(R.id.btnTempoDe20a30);
        btn40Min = (Button) view.findViewById(R.id.btnTempoDe30a40);
        btn60Min = (Button) view.findViewById(R.id.btnTempoDe40a60);
        btnFulgaAPe = (Button) view.findViewById(R.id.btnFulgaAPe);
        btnFulgaDeCarro= (Button) view.findViewById(R.id.btnFulgaDeCarro);
        btnFulgaDeMoto = (Button) view.findViewById(R.id.btnFulgaMoto);
        btnCaracteristicaSim = (Button)view. findViewById(R.id.btnCaracterisitcasSim);
        btnCaracteristicaNao = (Button) view.findViewById(R.id.btnCaracteristicasNao);
        btnFotoSim = (Button) view.findViewById(R.id.btnImagensSim);
        btnFotoNao = (Button) view.findViewById(R.id.btnImagensNao);

        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT-03:00"));

        String ano = String.valueOf(c.get(Calendar.YEAR));
        String mes = String.valueOf(c.get(Calendar.MONTH)+1);
        String dia = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        String hora = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String minuto = String.valueOf(c.get(Calendar.MINUTE));
        //String semana = String.valueOf(c.get(Calendar.));

        DataOcorrencia = dia+"-"+mes+"-"+ano+"-"+hora+":"+minuto;

        // idDaOcorrencia = ano+mes+dia+hora+minuto;
        TipoDaOcorrencia = "Ocorrencia de Roubo";

        btnRespostaSim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnRespostaSim.setBackgroundColor(Color.parseColor("#866e01"));
                btnRespostaNao.setBackgroundColor(Color.parseColor("#ffffff"));
                btnRespostaNao.setEnabled(true);

                Preferencias preferencias = new Preferencias(getActivity());

                Resposta001  = preferencias.RecuperarUsuarioLogado();
                idOcorrenciaLocal =  preferencias.RecuperarEmailDoUsuarioLogado();

                idOcorrenciaLocal = Base64Criptografia.SetDadosParaCriptografar(idOcorrenciaLocal);

                ocorrencias.setNomeIdentificacao(Resposta001);
                ocorrencias.setIdOcorrencia(idOcorrenciaLocal);
                ocorrencias.setTipoDaOcorrencia(TipoDaOcorrencia);

            }
        });

        btnRespostaNao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                btnRespostaNao.setBackgroundColor(Color.parseColor("#866e01"));
                btnRespostaSim.setBackgroundColor(Color.parseColor("#ffffff"));
                btnRespostaSim.setEnabled(true);

                Resposta001 = "Usuário não identificado";

                Preferencias preferencias = new Preferencias(getActivity());

                String nome =  preferencias.RecuperarEmailDoUsuarioLogado();
                String email = Base64Criptografia.SetDadosParaCriptografar(nome);

                idOcorrenciaLocal = Base64Criptografia.SetDadosParaCriptografar(email);

                ocorrencias.setNomeIdentificacao(Resposta001);
                ocorrencias.setIdOcorrencia(idDaOcorrencia);
                ocorrencias.setTipoDaOcorrencia(TipoDaOcorrencia);

            }
        });

        /*
        btnEu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnEu.setBackgroundColor(Color.parseColor("#866e01"));
                btnComercio.setBackgroundColor(Color.parseColor("#ffffff"));
                btnTerceiro.setBackgroundColor(Color.parseColor("#ffffff"));

                Preferencias preferencias = new Preferencias(getActivity());

                String eu = preferencias.RecuperarUsuarioLogado();

                ocorrencias.setNomeDaVitima(eu);

            }
        });
        */

        btnComercio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                btnComercio.setBackgroundColor(Color.parseColor("#866e01"));
               // btnEu.setBackgroundColor(Color.parseColor("#ffffff"));
                btnTerceiro.setBackgroundColor(Color.parseColor("#ffffff"));

                ocorrencias.setNomeDaVitima("Comércio");
            }
        });
        btnTerceiro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnTerceiro.setBackgroundColor(Color.parseColor("#866e01"));
                btnComercio.setBackgroundColor(Color.parseColor("#ffffff"));
               // btnEu.setBackgroundColor(Color.parseColor("#ffffff"));

                ocorrencias.setNomeDaVitima("Terceiro");

            }
        });

        btnArmaFogo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnArmaFogo.setBackgroundColor(Color.parseColor("#866e01"));
                btnArmaBranca.setBackgroundColor(Color.parseColor("#ffffff"));
                btnArmaNaoDemonstrada.setBackgroundColor(Color.parseColor("#ffffff"));

                ocorrencias.setTipoDeViolencia("Arma de Fogo");
            }
        });
        btnArmaBranca.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnArmaBranca.setBackgroundColor(Color.parseColor("#866e01"));
                btnArmaFogo.setBackgroundColor(Color.parseColor("#ffffff"));
                btnArmaNaoDemonstrada.setBackgroundColor(Color.parseColor("#ffffff"));

                ocorrencias.setTipoDeViolencia("Arma Branca");
            }
        });
        btnArmaNaoDemonstrada.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnArmaNaoDemonstrada.setBackgroundColor(Color.parseColor("#866e01"));
                btnArmaFogo.setBackgroundColor(Color.parseColor("#ffffff"));
                btnArmaBranca.setBackgroundColor(Color.parseColor("#ffffff"));

                ocorrencias.setTipoDeViolencia("Arma não Demonstrada");
            }
        });

        btn10Min.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btn10Min.setBackgroundColor(Color.parseColor("#866e01"));
                btn20Min.setBackgroundColor(Color.parseColor("#ffffff"));
                btn30Min.setBackgroundColor(Color.parseColor("#ffffff"));
                btn40Min.setBackgroundColor(Color.parseColor("#ffffff"));
                btn60Min.setBackgroundColor(Color.parseColor("#ffffff"));

                ocorrencias.setTempoDoOcorrido("10 Minutos");
            }
        });
        btn20Min.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btn20Min.setBackgroundColor(Color.parseColor("#866e01"));
                btn10Min.setBackgroundColor(Color.parseColor("#ffffff"));
                btn30Min.setBackgroundColor(Color.parseColor("#ffffff"));
                btn40Min.setBackgroundColor(Color.parseColor("#ffffff"));
                btn60Min.setBackgroundColor(Color.parseColor("#ffffff"));

                ocorrencias.setTempoDoOcorrido("20 Minutos");
            }
        });
        btn30Min.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btn30Min.setBackgroundColor(Color.parseColor("#866e01"));
                btn10Min.setBackgroundColor(Color.parseColor("#ffffff"));
                btn20Min.setBackgroundColor(Color.parseColor("#ffffff"));
                btn40Min.setBackgroundColor(Color.parseColor("#ffffff"));
                btn60Min.setBackgroundColor(Color.parseColor("#ffffff"));

                ocorrencias.setTempoDoOcorrido("30 Minutos");
            }
        });
        btn40Min.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btn40Min.setBackgroundColor(Color.parseColor("#866e01"));
                btn10Min.setBackgroundColor(Color.parseColor("#ffffff"));
                btn20Min.setBackgroundColor(Color.parseColor("#ffffff"));
                btn30Min.setBackgroundColor(Color.parseColor("#ffffff"));
                btn60Min.setBackgroundColor(Color.parseColor("#ffffff"));

                ocorrencias.setTempoDoOcorrido("40 Minutos");

            }
        });
        btn60Min.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btn60Min.setBackgroundColor(Color.parseColor("#866e01"));
                btn10Min.setBackgroundColor(Color.parseColor("#ffffff"));
                btn20Min.setBackgroundColor(Color.parseColor("#ffffff"));
                btn30Min.setBackgroundColor(Color.parseColor("#ffffff"));
                btn40Min.setBackgroundColor(Color.parseColor("#ffffff"));

                ocorrencias.setTempoDoOcorrido("60 Minutos");
            }
        });

        btnFulgaAPe.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnFulgaAPe.setBackgroundColor(Color.parseColor("#866e01"));
                btnFulgaDeCarro.setBackgroundColor(Color.parseColor("#ffffff"));
                btnFulgaDeMoto.setBackgroundColor(Color.parseColor("#ffffff"));

                ocorrencias.setTipoDaFulga("Fulga A Pe");

            }
        });
        btnFulgaDeCarro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                btnFulgaDeCarro.setBackgroundColor(Color.parseColor("#866e01"));
                btnFulgaAPe.setBackgroundColor(Color.parseColor("#ffffff"));
                btnFulgaDeMoto.setBackgroundColor(Color.parseColor("#ffffff"));

                ocorrencias.setTipoDaFulga("Fulga de carro");
            }
        });
        btnFulgaDeMoto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnFulgaDeMoto.setBackgroundColor(Color.parseColor("#866e01"));
                btnFulgaAPe.setBackgroundColor(Color.parseColor("#ffffff"));
                btnFulgaDeCarro.setBackgroundColor(Color.parseColor("#ffffff"));

                ocorrencias.setTipoDaFulga("Fulga de Moto");
            }
        });

        btnCaracteristicaSim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnCaracteristicaSim.setBackgroundColor(Color.parseColor("#866e01"));
                btnCaracteristicaNao.setBackgroundColor(Color.parseColor("#ffffff"));

                String detalhes = CaracteristicasDoSuspeito();

                ocorrencias.setCaracteristicasDoSuspeito(detalhes);

            }
        });
        btnCaracteristicaNao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnCaracteristicaNao.setBackgroundColor(Color.parseColor("#866e01"));
                btnCaracteristicaSim.setBackgroundColor(Color.parseColor("#ffffff"));

                ocorrencias.setCaracteristicasDoSuspeito("Caracteristicas não informadas");


            }
        });

        btnFotoSim.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnFotoSim.setBackgroundColor(Color.parseColor("#866e01"));
                btnFotoNao.setBackgroundColor(Color.parseColor("#ffffff"));

            }
        });

        btnFotoNao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                btnFotoNao.setBackgroundColor(Color.parseColor("#866e01"));
                btnFotoSim.setBackgroundColor(Color.parseColor("#ffffff"));
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Enviado sua ocorrência Sr. " +Resposta001, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                try{

                    ocorrencias.setDataOcorrencia(DataOcorrencia);

                    controllerCadastrarOcorrencia.SalvarDados(ocorrencias);

                    Preferencias preferencias = new Preferencias(getActivity());
                    preferencias.SalvarIdDaOcorrenciaLocal(idOcorrenciaLocal);

                }catch (Exception e){
                   Toast.makeText(getActivity(), "Ocorreu uma falha no registro "+e, Toast.LENGTH_SHORT).show();
                }

                   Toast.makeText(getActivity(), "Ocorrência Registrada Com Sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private String CaracteristicasDoSuspeito(){

        builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Características");

        final EditText caracteristicas = new EditText(getActivity());
        caracteristicas.setHint("Informe Detalhes ");

        builder.setView(caracteristicas);

        builder.setCancelable(false);

        builder.setPositiveButton("Registrar", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                CaracteristicaSim = caracteristicas.getText().toString();

                if (CaracteristicaSim.isEmpty()) {

                    Toast.makeText(getActivity(), "Favor informar detalhes.", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getActivity(), "Obrigado, Registrado", Toast.LENGTH_SHORT).show();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        return CaracteristicaSim;


    }


}
