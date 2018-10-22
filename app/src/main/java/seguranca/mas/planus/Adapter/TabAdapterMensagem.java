package seguranca.mas.planus.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import seguranca.mas.planus.Fragments.FragmentConversas;
import seguranca.mas.planus.Fragments.FragmentDeContatos;
import seguranca.mas.planus.Fragments.FragmentoListaDeOcorrencia;

/**
 * Created by wende on 25/09/2018.
 */

public class TabAdapterMensagem extends FragmentStatePagerAdapter {

    private String[]  TabAbas = {"ASSOCIADOS","MENSAGENS"};

    public TabAdapterMensagem(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position){

            case 0: fragment = new FragmentDeContatos();
                break;
            case 1: fragment = new FragmentConversas();
                break;

        }
        return fragment;
    }

    @Override
    public int getCount( ) {
        return TabAbas.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TabAbas[position];
    }
}
