package com.example.usman.noteapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Usman on 1/25/2018.
 */
public class NoteTabs extends FragmentStatePagerAdapter {

    int tabsCount;

    public NoteTabs(FragmentManager fragmentManager, int tabsCount) {
        super(fragmentManager);
        this.tabsCount = tabsCount;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AllNoteFragment t1 = new AllNoteFragment();
                return t1;
            case 1:
                AddNoteFragment t2 = new AddNoteFragment();
                return t2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabsCount;
    }
}

