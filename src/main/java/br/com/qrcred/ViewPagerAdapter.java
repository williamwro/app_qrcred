package br.com.qrcred;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
//import androidx.work.impl.model.PreferenceDao_Impl;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private final String[] data;
    final private String empregador;
    private final String matricula;

    public ViewPagerAdapter(FragmentActivity fragmentActivity,String[] data,String empregador,String matricula) {
        super(fragmentActivity);
        this.data = data;
        this.matricula = matricula;
        this.empregador = empregador;
    }


    @NonNull @Override public Fragment createFragment(int position) {
        ExtratoFragment_tab_blank fragment = new ExtratoFragment_tab_blank();
        Bundle arguments = new Bundle();
        arguments.putString("position",data[position]);
        arguments.putString("matricula",matricula);
        arguments.putString("empregador",empregador);
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return data.length;
    }
}
