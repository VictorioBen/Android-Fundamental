package com.workspace.githubappconsumer2.adapter


import android.content.Context
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.workspace.githubappconsumer2.model.UserModel


class ViewPagerAdapter(fm: FragmentManager,private val context: Context,  private val listUser: MutableList<UserModel>? = mutableListOf()): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragments = mutableListOf<Fragment>()
    private val titles = mutableListOf<String>()



    override fun getItem(position: Int): Fragment = fragments[position]


    override fun getCount(): Int {
        return fragments.size
    }
    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
      return  titles[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragments.add(fragment)
        titles.add(title)
        notifyDataSetChanged()
    }








}