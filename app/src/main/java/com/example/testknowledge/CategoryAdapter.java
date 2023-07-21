package com.example.testknowledge;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

public class CategoryAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public CategoryAdapter(Context context, FragmentManager fm){
        super(fm);
        mContext = context;
    }



    @Override
    public Fragment getItem(int position){
        if(position == 0){
            return new NumbersFragment();
        }else if(position == 1){
            return new FamilyMembersFragment();
        }else if(position == 2){
            return new ColorsFragment();
        }else{
            return new PhrasesFragment();
        }
    }

    // return total numbers of pages.
    @Override
    public int getCount(){return 4;}

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(position == 0){
            return "Numbers";
        }else if(position == 1){
            return "Family Members";
        }else if(position == 2){
            return "Colors";
        }else{
            return "Phrases";
        }
    }


}
