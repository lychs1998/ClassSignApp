package red.clf.app.afinal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by HP on 2018/11/18.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragmentList;
    private ArrayList<String> listTitle;

    public MyPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragmentList, ArrayList<String> listTitle) {
        super(fm);
        this.fragmentList=fragmentList;
        this.listTitle=listTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return listTitle.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle.get(position);
    }
}
