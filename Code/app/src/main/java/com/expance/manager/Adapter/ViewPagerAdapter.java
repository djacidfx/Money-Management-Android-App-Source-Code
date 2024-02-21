package com.expance.manager.Adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.expance.manager.Calendar;
import com.expance.manager.Statistic;
import com.expance.manager.Transaction;
import com.expance.manager.Wallet;

/* loaded from: classes3.dex */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return 4;
    }

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm, 1);
    }

    @Override // androidx.fragment.app.FragmentStatePagerAdapter
    public Fragment getItem(int position) {
        if (position != 0) {
            if (position != 1) {
                if (position != 2) {
                    if (position == 3) {
                        return new Wallet();
                    }
                    return new Fragment();
                }
                return new Statistic();
            }
            return new Calendar();
        }
        return new Transaction();
    }
}
