package seguranca.mas.planus.control;

/**
 * Created by wende on 16/03/2018.
 */

public class SequenciaLogica {

    private int numeroAnterior;
    private int numeroProximo;
    private int numeroAtual;
    private int numero = 1;

    public SequenciaLogica(){}


    public int  SequenciaLogica()
    {

        return numero;
    }


    public int getNumeroAnterior() {
        return numeroAnterior;
    }

    public void setNumeroAnterior(int numeroAnterior) {
        this.numeroAnterior = numeroAnterior;
    }

    public int getNumeroProximo() {
        return numeroProximo;
    }

    public void setNumeroProximo(int numeroProximo) {
        this.numeroProximo = numeroProximo;
    }

    public int getNumeroAtual() {
        return numeroAtual;
    }

    public void setNumeroAtual(int numeroAtual) {
        this.numeroAtual = numeroAtual;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }





}
