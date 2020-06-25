package com.wecompli.screeen.fragment.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager

import com.google.android.material.tabs.TabLayout
import com.wecompli.R
import com.wecompli.screeen.home.HomeActivity
import com.wecompli.screeen.startcheck.StartCheckFragment
import com.wecompli.utils.customtablayout.CustomTabLayout
import com.wecompli.utils.sheardpreference.AppSheardPreference
import com.wecompli.utils.sheardpreference.PreferenceConstent

import java.util.ArrayList

class DashBordFragment : Fragment() {
    internal var appSheardPreference: AppSheardPreference?=null
    internal var homeActivity: HomeActivity?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeActivity = activity as HomeActivity?
        appSheardPreference = AppSheardPreference(homeActivity!!)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        // Setting ViewPager for each Tabs
        val viewPager = view.findViewById<View>(R.id.viewpager) as ViewPager
        setupViewPager(viewPager)
        // Set Tabs inside Toolbar
        val tabs = view.findViewById<View>(R.id.tabs) as CustomTabLayout
        tabs.setupWithViewPager(viewPager)
        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
                if (tab.position == 0) {
                    appSheardPreference!!.setvalue_in_preference(PreferenceConstent.selecttab,
                        homeActivity!!.resources.getString(R.string.daily))
                    //  homeActivity.loadStartCheckfragment();

                } else if (tab.position == 1) {
                    appSheardPreference!!.setvalue_in_preference(PreferenceConstent.selecttab,
                        homeActivity!!.resources.getString(R.string.weekly))
                    //  homeActivity.loadStartCheckfragment();

                    // Toast.makeText(getActivity(),"1",Toast.LENGTH_LONG).show();

                } else if (tab.position == 2) {
                    appSheardPreference!!.setvalue_in_preference(PreferenceConstent.selecttab, homeActivity!!.resources.getString(R.string.monthly))
                    //  homeActivity.loadStartCheckfragment();

                } else if (tab.position == 3) {

                    appSheardPreference!!.setvalue_in_preference(PreferenceConstent.selecttab, homeActivity!!.resources.getString(R.string.quaterly))
                    //  homeActivity.loadStartCheckfragment();

                } else if (tab.position == 4) {
                    appSheardPreference!!.setvalue_in_preference(PreferenceConstent.selecttab, homeActivity!!.resources.getString(R.string.halfyearly))
                    //  homeActivity.loadStartCheckfragment();


                } else if (tab.position == 5) {
                    appSheardPreference!!.setvalue_in_preference(PreferenceConstent.selecttab, homeActivity!!.resources.getString(R.string.yearly)
                    )
                    //  homeActivity.loadStartCheckfragment();

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })
        return view

    }

    // Add Fragments to Tabs
    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = Adapter(childFragmentManager)
        adapter.addFragment(StartCheckFragment(), homeActivity!!.resources.getString(R.string.daily))
        adapter.addFragment(StartCheckFragment(), homeActivity!!.resources.getString(R.string.weekly))
        adapter.addFragment(StartCheckFragment(), homeActivity!!.resources.getString(R.string.monthly))
        adapter.addFragment(StartCheckFragment(), homeActivity!!.resources.getString(R.string.quaterly))
        adapter.addFragment(StartCheckFragment(), homeActivity!!.resources.getString(R.string.halfyearly))
        adapter.addFragment(StartCheckFragment(), homeActivity!!.resources.getString(R.string.set_date))
        viewPager.adapter = adapter
        appSheardPreference!!.setvalue_in_preference(PreferenceConstent.selecttab, homeActivity!!.resources.getString(R.string.daily))

    }

    internal class Adapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }
    }


}




