package br.com.app.blocodenotas.controller.ui.editor

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabsAdapter(fm: FragmentManager): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val listFragments: MutableList<Fragment> = ArrayList()
    private val listFragmentsTitle: MutableList<CharSequence> = ArrayList()

    fun add(frag: Fragment?, title: CharSequence?) {
        listFragments.add(frag!!)
        listFragmentsTitle.add(title!!)
    }

    override fun getCount(): Int  =  listFragments.size

    override fun getItem(position: Int): Fragment = listFragments[position]

    override fun getPageTitle(position: Int): CharSequence? = listFragmentsTitle[position]

}