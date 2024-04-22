package br.com.qrcred;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class ViewPagerAdapter_Conv extends FragmentStateAdapter {
   private final String[] data;
   private final Integer codconvenio;

   public ViewPagerAdapter_Conv(FragmentActivity fragmentActivity, String[] data, Integer codconvenio) {
      super(fragmentActivity);
      this.data = data;
      this.codconvenio = codconvenio;
   }


   @NonNull
   @Override public Fragment createFragment(int position) {
      ExtratoFragment_tab_blank_conv fragment = new ExtratoFragment_tab_blank_conv();
      Bundle arguments = new Bundle();
      arguments.putString("position",data[position]);
      arguments.putInt("codconvenio",codconvenio);
      fragment.setArguments(arguments);
      return fragment;
   }

   @Override
   public int getItemCount() {
      return data.length;
   }
}
