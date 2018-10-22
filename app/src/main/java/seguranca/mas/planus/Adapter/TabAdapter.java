package seguranca.mas.planus.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import seguranca.mas.planus.Fragments.FragmentCadastroDeOcorrencia;
import seguranca.mas.planus.Fragments.FragmentMap;
import seguranca.mas.planus.Fragments.FragmentoListaDeOcorrencia;

/**
 * Created by wende on 30/07/2018.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

private String[]  TabAbas = {"REGISTRAR","OCORRÊNCIAS","TRÂNSITO"};

    public TabAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;

        switch (position){

            case 0: fragment = new FragmentCadastroDeOcorrencia();
            break;
            case 1: fragment = new FragmentoListaDeOcorrencia();
            break;
            case 2: fragment = new FragmentMap();
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
