package com.binar.secondhand.screen.daftar_jual.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.binar.secondhand.screen.daftar_jual.screen.DiminatiFragment
import com.binar.secondhand.screen.daftar_jual.screen.SellerProducFragment
import com.binar.secondhand.screen.daftar_jual.screen.TerjualFragment

class SectionPagerAdapter(fm: FragmentActivity) :
    FragmentStateAdapter(fm) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = SellerProducFragment()

            }
            1 -> {
                fragment = DiminatiFragment()
            }

            2 -> {
                fragment = TerjualFragment()
            }
        }
        return fragment as Fragment
    }
}